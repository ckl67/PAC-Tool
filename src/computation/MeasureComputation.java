package computation;

import java.util.List;

import enthalpy.Enthalpy;

public class MeasureComputation {

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	private MeasureComputation() {	} // Prevents instantiation
	/**
	 * Will update and compute H,P,T for ALL Measure Point
	 * If P0 and PK have been updated then function will return 2
	 * If P0 or PK points will be updated then function will return 1
	 * If no P0 or PK points will be updated the function will return 0
	 */
	public static int updateAllMeasureListElem (List<Measure> measureL,Enthalpy enthalpy) {
		int result=0;

		for (MeasurePoint p : MeasurePoint.values()) {
			int n = p.ordinal(); 		// p = T1,T2,... n = 0 , 1, 
			Measure m = measureL.get(n);  

			switch (m.getMeasureObject()) {
			case T1 : case T6 : case T8 :	// Points intersection with P0
				m.setMT( m.getValue() );
				m.setMP( enthalpy.convT2P( m.getValue() ) );
				// Check if point has been chosen and if P > 0 then only P can be considered as completed 
				if (m.getMeasureChoice() == MeasureChoice.Chosen ) {
					if ( measureL.get(MeasurePoint._P0).getValue() > 0 ) {
						m.setMP0PK( measureL.get(MeasurePoint._P0).getValue()  );

						double Hsatv0 = enthalpy.matchP2HvSat( m.getMP());
						double Psat0 = m.getMP();
						double P0PK0 = m.getMP0PK();
						double Hmpiso0 = enthalpy.CompHmatchPSatWithP0PK(Hsatv0, Psat0, P0PK0); 
						m.setMHaprox(Hmpiso0);

						m.setMeasureChoice(MeasureChoice.ChosenHaprox);
					}
				}
				break;
			case T2 : case T5 :				// Points intersection with PK
				m.setMT( m.getValue() );
				m.setMP( enthalpy.convT2P( m.getValue() ) );
				// Check if point has been chosen and if P > 0 then only P can be considered as completed 
				if (m.getMeasureChoice() == MeasureChoice.Chosen) {
					if ( measureL.get(MeasurePoint._PK).getValue() > 0 ) {
						m.setMP0PK( measureL.get(MeasurePoint._PK).getValue()  );

						double Hsatv1 = enthalpy.matchP2HvSat( m.getMP());
						double Psat1 = m.getMP();
						double P0PK1 = m.getMP0PK();
						double Hmpiso1 = enthalpy.CompHmatchPSatWithP0PK(Hsatv1, Psat1, P0PK1); 
						m.setMHaprox(Hmpiso1);

						m.setMeasureChoice(MeasureChoice.ChosenHaprox);
					}
				}
				break;
			case P3 : case P4 : case P7 :	// Line P0 or PK
				if (m.getValue() > 0 ) {
					// P must be > 0
					m.setMP( m.getValue() );
					m.setMT( enthalpy.convP2T( m.getValue() ) );
					m.setMP0PK( m.getValue()  );
					m.setMeasureChoice(MeasureChoice.ChosenP0PK);
				}
				break;			
			default:
				break;
			}

			// ----------------------------------
			// Will now create the Draw Element: 
			// 	Draw elements (ElDraw) must be 
			//		* simple elements to be drawn quickly
			//			g2.draw( new Line2D.Double(x1,y1,x2,y2);
			//		* associable
			//			Behind a same reference (= Measured Point) several simple draw elements
			//			to draw or to delete
			//			g2.drawString(s,x,y);
			//			g2.draw( new Ellipse2D.Double(x1,y1);
			//			g2.draw( new Line2D.Double(x1,y1,x2,y2);
			//		* Movable
			//			The element can be moved and receive a new value Hreal
			//
			//  Also
			// 		Although P3 and P4 are existing Measured Object, only P3 will be retained to create PK
			//  Conclusion
			//  	eDrawL 
			//			(P0,Line,get_x1,get_y1,get_x2,get_y2)
			//			(P0,Text,get_x1,get_y1)
			//			(P0,DottedLine,get_x1,get_y1,get_x2,get_y2
			//			(P0,Point,get_x1,get_y1)
			//		Move
			//			Only for points ! 
			//			If point is moved we set : _ChosenHreal
			// ----------------------------------


			System.out.println(p + 
					" Compleded =" + m.getMeasureChoice() + 
					" value=" + m.getValue() + 
					" T=" + m.getMT() + 
					" --> P=" + m.getMP() + 
					" ==> P0 or PK =" + m.getMP0PK() +
					" Hsat(Approx) =" + m.getMHaprox() + 
					" Hsat(Real) =" + m.getMHreal());


		}
		return result;
	}

}
