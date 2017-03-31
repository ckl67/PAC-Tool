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

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import enthalpy.Enthalpy;
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
			int n = p.ordinal(); 		// p = T1,T2,... n = 0 , 1, 
			MeasurePoint m = measurePointL.get(n);  

			switch (m.getMeasureObject()) {
			case T1 : case T6 : case T8 :	// Points intersection with P0
				m.setMT( m.getValue() );
				m.setMP( enthalpy.convT2P( m.getValue() ) );
				// Check if point has been chosen and if P > 0 then only P can be considered as completed 
				if (m.getMeasureChoiceStatus() == MeasureChoiceStatus.Chosen ) {
					if ( measurePointL.get(MeasureObject._P0).getValue() > 0 ) {
						m.setMP0PK( measurePointL.get(MeasureObject._P0).getValue()  );

						if (m.getMeasureObject().equals(MeasureObject.T1)) {
							double Hsat0 = enthalpy.matchP2HvaporSat( m.getMP());
							double Psat0 = m.getMP();
							double P0PK0 = m.getMP0PK();
							double Hmpiso0 = enthalpy.CompHmatchPSatWithP0PK(Hsat0, Psat0, P0PK0); 
							m.setMH(Hmpiso0);
							m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);
						}

						if (m.getMeasureObject().equals(MeasureObject.T6)) {


						}

						if (m.getMeasureObject().equals(MeasureObject.T8)) {
							double Hsat0 = enthalpy.matchP2HvaporSat( m.getMP());
							double Psat0 = m.getMP();
							double P0PK0 = m.getMP0PK();
							double Hmpiso0 = enthalpy.CompHmatchPSatWithP0PK(Hsat0, Psat0, P0PK0); 
							m.setMH(Hmpiso0);
							m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);


							// T1 is Over heated of T8 (Will be updated only if T1 = 0) :: T1 = T8 + OH
							//			if (measurePointL.get(MeasureObject.T1.ordinal()).getValue() == 0) {
							double ohT = measurePointL.get(MeasureObject.T8.ordinal()).getMT() + 
									Math.round(pac.getCurrentCompressor().getOverheated());
							logger.info("Computation of T1 --> (T8={} + Compressor over head={}) = {}",measurePointL.get(MeasureObject.T8.ordinal()).getMT(),Math.round(pac.getCurrentCompressor().getOverheated()),ohT);

							MeasurePoint m1 = measurePointL.get(MeasureObject.T1.ordinal());
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

							//			} 

						}
					}
				}
				break;
			case T2 : case T5 :				// Points intersection with PK
				m.setMT( m.getValue() );
				m.setMP( enthalpy.convT2P( m.getValue() ) );
				// Check if point has been chosen and if P > 0 then only P can be considered as completed 
				if (m.getMeasureChoiceStatus() == MeasureChoiceStatus.Chosen) {
					if ( measurePointL.get(MeasureObject._PK).getValue() > 0 ) {
						m.setMP0PK( measurePointL.get(MeasureObject._PK).getValue()  );

						if (m.getMeasureObject().equals(MeasureObject.T2)) {
							double Hsat1 = enthalpy.matchP2HvaporSat( m.getMP());
							double Psat1 = m.getMP();
							double P0PK1 = m.getMP0PK();
							double Hmpiso1 = enthalpy.CompHmatchPSatWithP0PK(Hsat1, Psat1, P0PK1); 
							m.setMH(Hmpiso1);
							m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);
						}

						if (m.getMeasureObject().equals(MeasureObject.T5)) {
							double Hsat = enthalpy.matchP2HliquidSat( m.getMP());
							m.setMH(Hsat);
							m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);

							// T6 will be computed only if T6 = 0
							if (measurePointL.get(MeasureObject.T6.ordinal()).getValue() == 0) {

								// Compute T6
								MeasurePoint m6 = measurePointL.get(MeasureObject.T6.ordinal());
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
				if (m.getValue() > 0 ) {
					// P must be > 0
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


						// T5 is under cooling of T4 (Will be updated only if T5 = 0) :: T5 = T4 - UC
						if (measurePointL.get(MeasureObject.T5.ordinal()).getValue() == 0) {
							double ucT = measurePointL.get(MeasureObject.P4.ordinal()).getMT() - 
									Math.round(pac.getCurrentCompressor().getUnderCooling());
							logger.info("Computation of T5 --> (T4={}  Under Colling ={}) = {}",measurePointL.get(MeasureObject.P4.ordinal()).getMT(),Math.round(pac.getCurrentCompressor().getUnderCooling()),ucT);

							MeasurePoint m5 = measurePointL.get(MeasureObject.T5.ordinal());
							m5.setMT(ucT);
							m5.setValue(Math.round(ucT*100)/100.0);
							m5.setMP( enthalpy.convT2P( m5.getMT()));
							m5.setMP0PK( measurePointL.get(MeasureObject._PK).getValue()  );

							double Hsatm5 = enthalpy.matchP2HliquidSat( m5.getMP());
							m5.setMH(Hsatm5);
							m5.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);
						}


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
	public static double cop_carnot_froid() {
		double result = 0;

		//	if ((TK-T0) != 0.0) {
		//		result = (T0+273)/(TK-T0);
		//	}
		return result;		
	}

	/**
	 *  Compute Cop Carnot Chaud
	 * @return : TK/(TK-T0)
	 */
	public static double cop_carnot_chaud() {
		double result = 0;

		//if ((TK-T0) != 0.0) {
		//	result = (TK+273)/(TK-T0);
		//}
		return result;		
	}

	/**
	 * Compute Cop Froid
	 * @return : Q0/W = (H1-H3)/(H2-H1)
	 */
	public static double cop_froid() {
		double result = 0;

		//if ((H2-H1) != 0.0) {
		//	result = (H1-H3)/(H2-H1);
		//}
		return result;		
	}

	/**
	 * Compute Cop Chaud
	 * @return : QK/W = (H2-H3)/(H2-H1)
	 */
	public static  double cop_chaud() {
		double result = 0;

		//if ((H2-H1) != 0.0) {
		//	result = (H2-H3)/(H2-H1);
		//}
		return result;		
	}


}
