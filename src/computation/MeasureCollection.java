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

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import enthalpy.Enthalpy;

public class MeasureCollection {

	private static final Logger logger = LogManager.getLogger(MeasureCollection.class.getName());
<<<<<<< HEAD
	
=======

>>>>>>> Integer_Points
	private List<MeasurePoint> measurePL;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public MeasureCollection() {
		measurePL = new ArrayList<MeasurePoint>(); 
		for (MeasureObject p : MeasureObject.values())
			measurePL.add(new MeasurePoint(p));
	}

	// -------------------------------------------------------
	// 						METHOD
	// -------------------------------------------------------

	/**
	 * Will update and compute H,P,T for ALL MeasurePoint Point
	 */
	public static void updateAllMeasurePoints (MeasureCollection measureCollection,Enthalpy enthalpy) {

		for (MeasureObject p : MeasureObject.values()) {
			int n = p.ordinal(); 		// p = T1,T2,... n = 0 , 1, 
			List<MeasurePoint> measureL = measureCollection.getMeasurePL();
			MeasurePoint m = measureL.get(n);  

			switch (m.getMeasureObject()) {
			case T1 : case T6 : case T8 :	// Points intersection with P0
				m.setMT( m.getValue() );
				m.setMP( enthalpy.convT2P( m.getValue() ) );
				// Check if point has been chosen and if P > 0 then only P can be considered as completed 
				if (m.getMeasureChoiceStatus() == MeasureChoiceStatus.Chosen ) {
					if ( measureL.get(MeasureObject._P0).getValue() > 0 ) {
						m.setMP0PK( measureL.get(MeasureObject._P0).getValue()  );

						if (m.getMeasureObject().equals(MeasureObject.T1)) {
							double Hsat0 = enthalpy.matchP2HvaporSat( m.getMP());
							double Psat0 = m.getMP();
							double P0PK0 = m.getMP0PK();
							double Hmpiso0 = enthalpy.CompHmatchPSatWithP0PK(Hsat0, Psat0, P0PK0); 
							m.setMH(Hmpiso0);
							m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);
						}

						if (m.getMeasureObject().equals(MeasureObject.T6)) {
							System.out.println("TO BE DEFINED !");
						}

						if (m.getMeasureObject().equals(MeasureObject.T8)) {
							double Hsat0 = enthalpy.matchP2HvaporSat( m.getMP());
							double Psat0 = m.getMP();
							double P0PK0 = m.getMP0PK();
							double Hmpiso0 = enthalpy.CompHmatchPSatWithP0PK(Hsat0, Psat0, P0PK0); 
							m.setMH(Hmpiso0);
							m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);
						}
					}
				}
				break;
			case T2 : case T5 :				// Points intersection with PK
				m.setMT( m.getValue() );
				m.setMP( enthalpy.convT2P( m.getValue() ) );
				// Check if point has been chosen and if P > 0 then only P can be considered as completed 
				if (m.getMeasureChoiceStatus() == MeasureChoiceStatus.Chosen) {
					if ( measureL.get(MeasureObject._PK).getValue() > 0 ) {
						m.setMP0PK( measureL.get(MeasureObject._PK).getValue()  );

						if (m.getMeasureObject().equals(MeasureObject.T2)) {
							double Hsat1 = enthalpy.matchP2HvaporSat( m.getMP());
							double Psat1 = m.getMP();
							double P0PK1 = m.getMP0PK();
							double Hmpiso1 = enthalpy.CompHmatchPSatWithP0PK(Hsat1, Psat1, P0PK1); 
							m.setMH(Hmpiso1);
							m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);
						}

						if (m.getMeasureObject().equals(MeasureObject.T5)) {
							System.out.println("TO BE DEFINED !");
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
<<<<<<< HEAD
			
=======

>>>>>>> Integer_Points
			if ( (m.getMeasureChoiceStatus().equals(MeasureChoiceStatus.ChosenHaprox)) || 
					(m.getMeasureChoiceStatus().equals(MeasureChoiceStatus.ChosenP0PK))) {
				logger.info(
						"Point = {} Choice Status = {} value= {} T={} --> P={} ==> P0 or PK ={} H ={} ",
						p, m.getMeasureChoiceStatus(),m.getValue(),m.getMT(),m.getMP(),m.getMP0PK(),m.getMH()						
						);
			}
		}
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public List<MeasurePoint> getMeasurePL() {
		return measurePL;
	}

}
