/*
 * - PAC-Tool - 
 * Tool for understanding basics and computation of PAC (Pompe à Chaleur)
 * Copyright (C) 2016 christian.klugesherz@gmail.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (version 2)
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package computation;

/*

			P
			^
			|
			|
			|                     XXXXXXXXXXXXX
			|                  XXXX           XXXXX         (PK)
			|        (5)+-----(4)-----------------(3)--------------------+ (2)
			|           | XXXX                      XX                   /
			|           |XX                          XX                 /
			|           |X                            XX               /
			|          X|                              XX             /
			|         XX|                               X            /
			|         X |                               XX          /
			|        XX |             (P0)               X         /
			|       XX  +---------------------------------+---+---+
			|       X  (6)                               (7) (8)  (1)
			|      XX                                     X
			|      X                                      X
			|
			+------------------------------------------------------------------------> H

 */

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import enthalpy.Enthalpy;
import measurePoint.EloMeasurePointSelection;
import measurePoint.EloMeasurePoint;
import measurePoint.MeasurePoint;
import pac.Compressor;
import pac.Pac;

public class Comp {

	// -------------------------------------------------------
	// 		"Comp" Class is like "Math" Static Functions Class
	// -------------------------------------------------------
	private static final Logger logger = LogManager.getLogger(Comp.class.getName());

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	/**
	 * Will update and compute H,P,T for ALL MeasurePoint Point
	 */
	public static void updateAllMeasurePoints (List<MeasurePoint> measurePointL ,Enthalpy enthalpy, Pac pac ) {

		//int nb = 0;
		logger.info(" Input updateAllMeasurePoints()");
		for (EloMeasurePoint p : EloMeasurePoint.values()) {
			
			//System.out.println(nb++);
			int n = p.id(); 	// p = P1,P2,... --> n = 0 , 1, 

			MeasurePoint m = measurePointL.get(n);  
			// ------------------------------------------
			// Value to be completed for each Points : 
			//		T, P,P0PK,H
			// ------------------------------------------

			switch (p) {
			case P1 :
				// BP gas temperature after internal overheating and before compression
				// 	Should never happen as P1_T = P8_T + overheating, 
				// 	but here, the user has chosen to enter P1
				//  Value Entered = Temperature
				/*
				|         X |                               XX          /
				|        XX |             (P0)               X         /
				|       XX  +---------------------------------+---+---+
				|       X  (6)                               (7) (8)  (1)
				|      XX                                     X
				 */
				if (m.getMeasureChoiceStatus() == EloMeasurePointSelection.Chosen ) {
					m.setMT(m.getValue());
					m.setMP( enthalpy.convT2P( m.getValue() ));
					// if P0 > 0 then only H can be computed
					if ( measurePointL.get(EloMeasurePoint._P0_ID).getValue() > 0 ) {
						m.setMP0PK( measurePointL.get(EloMeasurePoint._P0_ID).getValue() );
						double Hmpiso0 = enthalpy.CompHmatchPSatWithP0PK(m); 
						m.setMH(Hmpiso0);
						m.setMeasureChoiceStatus(EloMeasurePointSelection.ChosenHaprox);
					}
				}
				break;
			case P2 :
				// HP gas temperature at the end of compression (Compressor top Bell)
				//  Value Entered = Temperature
				/*
				|                  XXXX           XXXXX         (PK)
				|        (5)+-----(4)-----------------(3)--------------------+ (2)
				|           | XXXX                      XX                   /
				|           |XX                          XX                 /
				 */
				if (m.getMeasureChoiceStatus() == EloMeasurePointSelection.Chosen ) {
					m.setMT( m.getValue() );
					m.setMP( enthalpy.convT2P( m.getValue()) );
					// if PK > 0 then only H can be computed
					if ( measurePointL.get(EloMeasurePoint._PK_ID).getValue() > 0 ) {
						m.setMP0PK( measurePointL.get(EloMeasurePoint._PK_ID).getValue() );
						double Hmpiso1 = enthalpy.CompHmatchPSatWithP0PK(m); 
						m.setMH(Hmpiso1);
						m.setMeasureChoiceStatus(EloMeasurePointSelection.ChosenHaprox);
					}
				}
				break;
			case P5 :
				// HP gas temperature after cooling
				//  Value Entered = Temperature
				/*
				|                  XXXX           XXXXX         (PK)
				|        (5)+-----(4)-----------------(3)--------------------+ (2)
				|           | XXXX                      XX                   /
				|           |XX                          XX                 /
				 */
				if (m.getMeasureChoiceStatus() == EloMeasurePointSelection.Chosen ) {
					m.setMT( m.getValue() );
					m.setMP( enthalpy.convT2P( m.getValue() ) );
					// if PK > 0 then only H can be computed
					double Hsat = 0;
					if ( measurePointL.get(EloMeasurePoint._PK_ID).getValue() > 0 ) {
						m.setMP0PK( measurePointL.get(EloMeasurePoint._PK_ID).getValue()  );
						Hsat = enthalpy.matchP2HliquidSat( m.getMP());
						m.setMH(Hsat);
						m.setMeasureChoiceStatus(EloMeasurePointSelection.ChosenHaprox);
					}

					// Compute P6 = Output Temperature Regulator / Capillary
					/*
					|        XX |             (P0)               X         /
					|       XX  +---------------------------------+---+---+
					|       X  (6)                               (7) (8)  (1)
					|      XX                                     X
					 */
					// if P0 > 0 then only H can be computed for P6
					if ( measurePointL.get(EloMeasurePoint._P0_ID).getValue() > 0 ) {
						MeasurePoint m6 = measurePointL.get(EloMeasurePoint.P6.id());
						double tmpP = measurePointL.get(EloMeasurePoint._P0_ID).getValue(); 
						m6.setMP(tmpP);
						m6.setMP0PK(tmpP);
						double tmpT = enthalpy.convP2T(tmpP); 
						m6.setValue(Math.round(tmpT*100)/100.0);
						m6.setMT(tmpT);
						m6.setMH(Hsat);
						m6.setMeasureChoiceStatus(EloMeasurePointSelection.ChosenHaprox);
					}
				}
				break;
			case P6 :
				// Output Temperature Regulator / Capillary
				//  Value Entered = Temperature
				/*
				|         X |                               XX          /
				|        XX |             (P0)               X         /
				|       XX  +---------------------------------+---+---+
				|       X  (6)                               (7) (8)  (1)
				|      XX                                     X
				 */
				break;

			case P8 :	
				// HP gas temperature after external heating
				//  Value Entered = Temperature
				/*
				|         X |                               XX          /
				|        XX |             (P0)               X         /
				|       XX  +---------------------------------+---+---+
				|       X  (6)                               (7) (8)  (1)
				|      XX                                     X
				 */
				if (m.getMeasureChoiceStatus() == EloMeasurePointSelection.Chosen ) {
					m.setMT(m.getValue());
					m.setMP( enthalpy.convT2P( m.getValue() ));
					// if P0 > 0 then only H can be computed
					if ( measurePointL.get(EloMeasurePoint._P0_ID).getValue() > 0 ) {
						m.setMP0PK( measurePointL.get(EloMeasurePoint._P0_ID).getValue()  );
						double Hmpiso0 = enthalpy.CompHmatchPSatWithP0PK(m); 
						m.setMH(Hmpiso0);
						m.setMeasureChoiceStatus(EloMeasurePointSelection.ChosenHaprox);
					}

					// Will Also Compute P1 
					// 	=>	BP gas temperature after internal overheating and before compression
					// 	T1 is Over heated of T8 --> T1 = T8 + OH
					double ohT = m.getValue() + Math.round(pac.getCurrentCompressor().getOverheated());
					logger.info("Computation of T1 --> (T8={} + Compressor over head={}) = {}",m.getValue(),Math.round(pac.getCurrentCompressor().getOverheated()),ohT);
					MeasurePoint m1 = measurePointL.get(EloMeasurePoint.P1.id());
					m1.setValue(Math.round(ohT*100)/100.0);
					m1.setMT(ohT);
					m1.setMP( enthalpy.convT2P( m1.getMT()));

					// if P0 > 0 then only H can be computed
					if ( measurePointL.get(EloMeasurePoint._P0_ID).getValue() > 0 ) {
						m1.setMP0PK( measurePointL.get(EloMeasurePoint._P0_ID).getValue()  );
						double Hmpiso0 = enthalpy.CompHmatchPSatWithP0PK(m1); 
						m1.setMH(Hmpiso0);
						m1.setMeasureChoiceStatus(EloMeasurePointSelection.ChosenHaprox);
					}
				}
				break;

			case P3 : 
				// Initial condensation pressure (HP Manifold measurement)
				//  Value Entered = Pressure
				/*
				|                  XXXX           XXXXX         (PK)
				|        (5)+-----(4)-----------------(3)--------------------+ (2)
				|           | XXXX                      XX                   /
				|           |XX                          XX                 /
				 */
				if (m.getMeasureChoiceStatus() == EloMeasurePointSelection.Chosen ) {
					// P must be > 0
					if (m.getValue() > 0 ) {
						m.setMP( m.getValue() );
						m.setMT( enthalpy.convP2T( m.getValue()) );
						m.setMP0PK( m.getValue() );
						double Hsat = enthalpy.matchP2HvaporSat( m.getMP());
						m.setMH(Hsat);
						m.setMeasureChoiceStatus(EloMeasurePointSelection.ChosenHaprox);
					}					
				}
				break;

			case P4 :
				// Condensation end pressure (HP Manifold measurement)	
				//  Value Entered = Pressure
				/*
				|                  XXXX           XXXXX         (PK)
				|        (5)+-----(4)-----------------(3)--------------------+ (2)
				|           | XXXX                      XX                   /
				|           |XX                          XX                 /
				 */
				if (m.getMeasureChoiceStatus() == EloMeasurePointSelection.Chosen ) {
					// P must be > 0
					if (m.getValue() > 0 ) {
						m.setMP( m.getValue() );
						m.setMT( enthalpy.convP2T( m.getValue()) );
						m.setMP0PK( m.getValue() );
						double Hsat = enthalpy.matchP2HliquidSat( m.getMP());
						m.setMH(Hsat);
						m.setMeasureChoiceStatus(EloMeasurePointSelection.ChosenHaprox);

						// T5 is under cooling of T4 
						double ucT = m.getMT() - Math.round(pac.getCurrentCompressor().getUnderCooling());
						logger.info("Computation of T5 --> (T4={}  Under Colling ={}) = {}",m.getMT(),Math.round(pac.getCurrentCompressor().getUnderCooling()),ucT);

						MeasurePoint m5 = measurePointL.get(EloMeasurePoint.P5.id());
						m5.setMT(ucT);
						m5.setValue(Math.round(ucT*100)/100.0);
						m5.setMP( enthalpy.convT2P( m5.getMT()));
						m5.setMP0PK( measurePointL.get(EloMeasurePoint._PK_ID).getValue()  );
						double Hsatm5 = enthalpy.matchP2HliquidSat( m5.getMP());
						m5.setMH(Hsatm5);
						m5.setMeasureChoiceStatus(EloMeasurePointSelection.ChosenHaprox);

						// Compute P6 = Output Temperature Regulator / Capillary
						/*
						|        XX |             (P0)               X         /
						|       XX  +---------------------------------+---+---+
						|       X  (6)                               (7) (8)  (1)
						|      XX                                     X
						 */
						// if P0 > 0 then only H can be computed for P6
						if ( measurePointL.get(EloMeasurePoint._P0_ID).getValue() > 0 ) {
							MeasurePoint m6 = measurePointL.get(EloMeasurePoint.P6.id());
							double tmpP = measurePointL.get(EloMeasurePoint._P0_ID).getValue(); 
							m6.setMP(tmpP);
							m6.setMP0PK(tmpP);
							double tmpT = enthalpy.convP2T(tmpP); 
							m6.setValue(Math.round(tmpT*100)/100.0);
							m6.setMT(tmpT);
							m6.setMH(Hsatm5);
							m6.setMeasureChoiceStatus(EloMeasurePointSelection.ChosenHaprox);
						}
					}
				}
				break;
			case P7 :	
				// Evaporation Pressure (BP Manifold Measurement)
				// P must be > 0
				/*
				|        XX |             (P0)               X         /
				|       XX  +---------------------------------+---+---+
				|       X  (6)                               (7) (8)  (1)
				|      XX                                     X
				 */
				if (m.getValue() > 0 ) {
					m.setMP( m.getValue() );
					m.setMT( enthalpy.convP2T( m.getValue() ) );
					m.setMP0PK( m.getValue()  );
					double Hsat = enthalpy.matchP2HvaporSat( m.getMP());
					m.setMH(Hsat);
					m.setMeasureChoiceStatus(EloMeasurePointSelection.ChosenHaprox);
					
					// Will also compute P6 if P5 is present
					/*
					|                  XXXX           XXXXX         (PK)
					|        (5)+-----(4)-----------------(3)--------------------+ (2)
					|           | XXXX                      XX                   /
					|           |XX                          XX                 /
					 */

					MeasurePoint m5 = measurePointL.get(EloMeasurePoint.P5.id());
					if ( m5.getMH() > 0 ) {
						MeasurePoint m6 = measurePointL.get(EloMeasurePoint.P6.id());
						double tmpP = measurePointL.get(EloMeasurePoint._P0_ID).getValue(); 
						m6.setMP(tmpP);
						m6.setMP0PK(tmpP);
						double tmpT = enthalpy.convP2T(tmpP); 
						m6.setValue(Math.round(tmpT*100)/100.0);
						m6.setMT(tmpT);
						m6.setMH(m5.getMH());
						m6.setMeasureChoiceStatus(EloMeasurePointSelection.ChosenHaprox);
					}
						
				}
				break;		
				
			default:
				break;
			}

			if ( (m.getMeasureChoiceStatus().equals(EloMeasurePointSelection.ChosenHaprox)) || 
					(m.getMeasureChoiceStatus().equals(EloMeasurePointSelection.ChosenP0PK))) {
				logger.info(
						"Point = {} Choice Status = {} value= {} T={} --> P={} ==> P0 or PK ={} H ={} ",
						p, m.getMeasureChoiceStatus(),m.getValue(),m.getMT(),m.getMP(),m.getMP0PK(),m.getMH()						
						);
			}
			
		} // End for (MeasurePointList p : MeasurePointList.values()) {
		logger.info(" Output updateAllMeasurePoints()");

	}


	/**
	 * Compute Cop Carnot Froid
	 * @return : T0/(TK-T0)
	 */
	public static double cop_carnot_froid(List<MeasurePoint> measurePointL) {
		double result = 0;

		double T0 = measurePointL.get(EloMeasurePoint._P0_ID).getMT();
		double TK = measurePointL.get(EloMeasurePoint._PK_ID).getMT();

		if ((TK-T0) != 0.0) {
			result = (T0+273)/(TK-T0);
		}
		return result;		
	}

	/**
	 *  Compute Cop Carnot Chaud
	 * @return : TK/(TK-T0)
	 */
	public static double cop_carnot_chaud(List<MeasurePoint> measurePointL) {
		double result = 0;

		double T0 = measurePointL.get(EloMeasurePoint._P0_ID).getMT();
		double TK = measurePointL.get(EloMeasurePoint._PK_ID).getMT();

		if ((TK-T0) != 0.0) {
			result = (TK+273)/(TK-T0);
		}
		return result;		
	}

	/**
	 * Compute Cop 
	 */
	public static double cop(List<MeasurePoint> measurePointL, Pac pac) {
		double COP = 0;

		double travailCompresseur = measurePointL.get(EloMeasurePoint.P2.id()).getMH() - 
				measurePointL.get(EloMeasurePoint.P1.id()).getMH();

		double puissanceCalorifique = measurePointL.get(EloMeasurePoint.P2.id()).getMH() - 
				measurePointL.get(EloMeasurePoint.P1.id()).getMH();

		double rapportPcalSurTravComp = puissanceCalorifique/travailCompresseur;

		Compressor compressor = pac.getCurrentCompressor();
		double cosPhi = Math.round(
				Misc.cosphi(
						compressor.getPower(), 
						compressor.getVoltage(), 
						compressor.getCurrent())
				);

		double puissanceAbsorbee = compressor.getCurrentMeasure() * 
				compressor.getVoltageMeasure();

		double puissanceUtile = puissanceAbsorbee * cosPhi;

		double puissanceDispoArbreMoteur = puissanceUtile * compressor.getPowerShaftPercent()/100.0;

		double puissanceCalorifiqueVrai =  puissanceDispoArbreMoteur * rapportPcalSurTravComp;

		COP = puissanceCalorifiqueVrai / puissanceAbsorbee;
		System.out.println("COP= "+ COP);

		return COP;		
	}




}
