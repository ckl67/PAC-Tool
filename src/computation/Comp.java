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

// Online tool used to draw this picture:
// http://asciiflow.com/
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

		logger.info("updateAllMeasurePoints()");
		for (MeasureObject p : MeasureObject.values()) {
			int n = p.ordinal(); 		// p = P1,P2,... n = 0 , 1, 
			MeasurePoint m = measurePointL.get(n);  

			switch (p) {
			case P1 : case P6 : case P8 :	// Points intersection with P0
				m.setMT( m.getValue() );
				m.setMP( enthalpy.convT2P( m.getValue() ) );
				// p can be P1,P6,P8, we have to Check if point has been chosen and if P > 0 
				// then only H can be computed  
				if (m.getMeasureChoiceStatus() == MeasureChoiceStatus.Chosen ) {

					if ( measurePointL.get(MeasureObject._P0).getValue() > 0 ) {
						m.setMP0PK( measurePointL.get(MeasureObject._P0).getValue()  );

						// ------------ P1--------------
						if (m.getMeasureObject().equals(MeasureObject.P1)) {
							double Hsat0 = enthalpy.matchP2HvaporSat( m.getMP());
							double Psat0 = m.getMP();
							double P0PK0 = m.getMP0PK();
							double Hmpiso0 = enthalpy.CompHmatchPSatWithP0PK(Hsat0, Psat0, P0PK0); 
							m.setMH(Hmpiso0);
							m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);
						}

						// ------------ P6--------------
						if (m.getMeasureObject().equals(MeasureObject.P6)) {


						}

						// ------------ P8--------------
						if (m.getMeasureObject().equals(MeasureObject.P8)) {
							double Hsat0 = enthalpy.matchP2HvaporSat( m.getMP());
							double Psat0 = m.getMP();
							double P0PK0 = m.getMP0PK();
							double Hmpiso0 = enthalpy.CompHmatchPSatWithP0PK(Hsat0, Psat0, P0PK0); 
							m.setMH(Hmpiso0);
							m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);


							// T1 is Over heated of T8 --> T1 = T8 + OH
							double ohT = measurePointL.get(MeasureObject.P8.ordinal()).getMT() + 
									Math.round(pac.getCurrentCompressor().getOverheated());
							logger.info("Computation of T1 --> (T8={} + Compressor over head={}) = {}",measurePointL.get(MeasureObject.P8.ordinal()).getMT(),Math.round(pac.getCurrentCompressor().getOverheated()),ohT);

							MeasurePoint m1 = measurePointL.get(MeasureObject.P1.ordinal());
							m1.setValue(ohT);
							m1.setMT(ohT);
							m1.setMP( enthalpy.convT2P( m1.getMT()));
							m1.setMP0PK( measurePointL.get(MeasureObject._P0).getValue()  );

							Hsat0 = enthalpy.matchP2HvaporSat( m1.getMP());
							Psat0 = m1.getMP();
							P0PK0 = m1.getMP0PK();
							Hmpiso0 = enthalpy.CompHmatchPSatWithP0PK(Hsat0, Psat0, P0PK0); 
							m1.setMH(Hmpiso0);
							m1.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);
						}
					}
				}
				break;
			case P2 : case P5 :				// Points intersection with PK
				m.setMT( m.getValue() );
				m.setMP( enthalpy.convT2P( m.getValue() ) );
				// p can be P2,P5, we have to Check if point has been chosen and if P > 0 
				// then only H can be computed  
				if (m.getMeasureChoiceStatus() == MeasureChoiceStatus.Chosen) {
					if ( measurePointL.get(MeasureObject._PK).getValue() > 0 ) {
						m.setMP0PK( measurePointL.get(MeasureObject._PK).getValue()  );

						// ------------ P2--------------
						if (m.getMeasureObject().equals(MeasureObject.P2)) {
							double Hsat1 = enthalpy.matchP2HvaporSat( m.getMP());
							double Psat1 = m.getMP();
							double P0PK1 = m.getMP0PK();
							double Hmpiso1 = enthalpy.CompHmatchPSatWithP0PK(Hsat1, Psat1, P0PK1); 
							m.setMH(Hmpiso1);
							m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);
						}

						// ------------ P5--------------
						if (m.getMeasureObject().equals(MeasureObject.P5)) {
							double Hsat = enthalpy.matchP2HliquidSat( m.getMP());
							m.setMH(Hsat);
							m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);

							// P6 will be computed only if P6 = 0
							if (measurePointL.get(MeasureObject.P6.ordinal()).getValue() == 0) {

								// Compute P6
								MeasurePoint m6 = measurePointL.get(MeasureObject.P6.ordinal());
								m6.setMP0PK( measurePointL.get(MeasureObject._P0).getValue());
								m6.setMH(Hsat);
								//m6.setMT();
								m6.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);
							}
						}
					}
				}
				break;

			case P3 : case P4 : case P7 :	// Line P0 or PK All point have to be computed
				// P must be > 0
				if (m.getValue() > 0 ) {

					m.setMP( m.getValue() );
					m.setMT( enthalpy.convP2T( m.getValue() ) );
					m.setMP0PK( m.getValue()  );

					// if P3 == _PK_GAS_ID, we have also to Fill P4
					if (m.getMeasureObject().equals(MeasureObject.P3)) {
						double Hsat = enthalpy.matchP2HvaporSat( m.getMP());
						m.setMH(Hsat);
					}

					// P4 == _PK_LIQUID_ID
					if (m.getMeasureObject().equals(MeasureObject.P4)) {
						double Hsat = enthalpy.matchP2HliquidSat( m.getMP());
						m.setMH(Hsat);


						// T5 is under cooling of T4 :: T5 = T4 - UC
						double ucT = measurePointL.get(MeasureObject.P4.ordinal()).getMT() - 
								Math.round(pac.getCurrentCompressor().getUnderCooling());
						logger.info("Computation of T5 --> (T4={}  Under Colling ={}) = {}",measurePointL.get(MeasureObject.P4.ordinal()).getMT(),Math.round(pac.getCurrentCompressor().getUnderCooling()),ucT);

						MeasurePoint m5 = measurePointL.get(MeasureObject.P5.ordinal());
						m5.setMT(ucT);
						m5.setValue(Math.round(ucT*100)/100.0);
						m5.setMP( enthalpy.convT2P( m5.getMT()));
						m5.setMP0PK( measurePointL.get(MeasureObject._PK).getValue()  );

						double Hsatm5 = enthalpy.matchP2HliquidSat( m5.getMP());
						m5.setMH(Hsatm5);
						m5.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);
						//}


					}

					// P7 == _P0_GAS_ID
					if (m.getMeasureObject().equals(MeasureObject.P7)) {
						double Hsat = enthalpy.matchP2HvaporSat( m.getMP());
						m.setMH(Hsat);
					}
					m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenP0PK);
				}
				break;			
			default:
				break;
			}

			if ( (m.getMeasureChoiceStatus().equals(MeasureChoiceStatus.ChosenHaprox)) || 
					(m.getMeasureChoiceStatus().equals(MeasureChoiceStatus.ChosenP0PK))) {
				logger.info(
						"Point = {} Choice Status = {} value= {} T={} --> P={} ==> P0 or PK ={} H ={} ",
						p, m.getMeasureChoiceStatus(),m.getValue(),m.getMT(),m.getMP(),m.getMP0PK(),m.getMH()						
						);
			}
		}
	}



	/**
	 * Compute Cop Carnot Froid
	 * @return : T0/(TK-T0)
	 */
	public static double cop_carnot_froid(List<MeasurePoint> measurePointL) {
		double result = 0;

		double T0 = measurePointL.get(MeasureObject._P0).getMT();
		double TK = measurePointL.get(MeasureObject._PK).getMT();

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

		double T0 = measurePointL.get(MeasureObject._P0).getMT();
		double TK = measurePointL.get(MeasureObject._PK).getMT();

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

		double travailCompresseur = measurePointL.get(MeasureObject.P2.id()).getMH() - 
				measurePointL.get(MeasureObject.P1.id()).getMH();
		
		double puissanceCalorifique = measurePointL.get(MeasureObject.P2.id()).getMH() - 
				measurePointL.get(MeasureObject.P1.id()).getMH();
		
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
		
		return COP;		
	}




}
