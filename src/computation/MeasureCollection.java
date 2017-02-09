package computation;

import java.util.ArrayList;
import java.util.List;

import enthalpy.Enthalpy;

public class MeasureCollection {

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

						double Hsatv0 = enthalpy.matchP2HvaporSat( m.getMP());
						double Psat0 = m.getMP();
						double P0PK0 = m.getMP0PK();
						double Hmpiso0 = enthalpy.CompHmatchPSatWithP0PK(Hsatv0, Psat0, P0PK0); 
						m.setMHaprox(Hmpiso0);

						m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);
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

						double Hsatv1 = enthalpy.matchP2HvaporSat( m.getMP());
						double Psat1 = m.getMP();
						double P0PK1 = m.getMP0PK();
						double Hmpiso1 = enthalpy.CompHmatchPSatWithP0PK(Hsatv1, Psat1, P0PK1); 
						m.setMHaprox(Hmpiso1);

						m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenHaprox);
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
						double Hsatv1 = enthalpy.matchP2HvaporSat( m.getMP());
						m.setMHaprox(Hsatv1);
					}
					// P4 == _PK_LIQUID_ID
					if (m.getMeasureObject().equals(MeasureObject.P4)) {
						double Hsatv1 = enthalpy.matchP2HliquidSat( m.getMP());
						m.setMHaprox(Hsatv1);
					}
					// P7 == _P0_GAS_ID
					if (m.getMeasureObject().equals(MeasureObject.P7)) {
						double Hsatv1 = enthalpy.matchP2HvaporSat( m.getMP());
						m.setMHaprox(Hsatv1);
					}

					m.setMeasureChoiceStatus(MeasureChoiceStatus.ChosenP0PK);
				}
				break;			
			default:
				break;
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
