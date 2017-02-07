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
package pacp;

import java.util.List;

import org.json.simple.JSONObject;

public class Measure extends Enthalpy {
	private MeasureObject measureObject;	
	private double value;				// Pressure or Temperature,..
	private MeasureChoice measureChoice;	// Measure to be considered (chosen or Hreal or Haprox)
	private double P;					// Pressure
	private double T;					// Temperature
	private double Haprox;				// Value Enthalpy approximation computed by Matching PSat (T-Isotherm) with P0PK
	private double Hreal;				// Value Enthalpy real : moved manually to the correct point
	private double P0PK;				// Value P0 or PK Pressure

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Measure(MeasureObject vmeasurePointE) {
		super();								// --> Calls Enthalpy constructor
		this.measureObject = vmeasurePointE;
		this.value = 0.0;
		this.measureChoice = MeasureChoice.NotChosen;
		this.P = 0.0;
		this.T = 0.0;
		this.Haprox = 0.0;
		this.Hreal = 0.0;
		this.P0PK = 0.0;
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	/**
	 * Clear the measure with all its elements
	 */
	public void clearMeasure() {
		this.value = 0;
		this.measureChoice = MeasureChoice.NotChosen;
		this.P = 0;
		this.T = 0;
		this.Haprox = 0;
		this.Hreal = 0;
		this.P0PK = 0;
	}

	/**
	 * Will update and compute H,P,T for ALL Measure Point
	 * If P0 and PK have been updated then function will return 2
	 * If P0 or PK points will be updated then function will return 1
	 * If no P0 or PK points will be updated the function will return 0
	 */
	public static int updateAllMeasureListElem (List<Measure> measureL) {
		int result=0;

		for (MeasureObject p : MeasureObject.values()) {
			int n = p.ordinal(); 		// p = T1,T2,... n = 0 , 1, 
			Measure m = measureL.get(n);  

			switch (m.getMeasureObject()) {
			case T1 : case T6 : case T8 :	// Points intersection with P0
				m.setMT( m.getValue() );
				m.setMP( m.convT2P( m.getValue() ) );
				// Check if point has been chosen and if P > 0 then only P can be considered as completed 
				if (m.getMeasureChoice() == MeasureChoice.Chosen ) {
					if ( measureL.get(MeasureObject._P0).getValue() > 0 ) {
						m.setMP0PK( measureL.get(MeasureObject._P0).getValue()  );

						double Hsatv0 = m.matchP2HvSat( m.getMP());
						double Psat0 = m.getMP();
						double P0PK0 = m.getMP0PK();
						double Hmpiso0 = m.CompHmatchPSatWithP0PK(Hsatv0, Psat0, P0PK0); 
						m.setMHaprox(Hmpiso0);

						m.setMeasureChoice(MeasureChoice.ChosenHaprox);
					}
				}
				break;
			case T2 : case T5 :				// Points intersection with PK
				m.setMT( m.getValue() );
				m.setMP( m.convT2P( m.getValue() ) );
				// Check if point has been chosen and if P > 0 then only P can be considered as completed 
				if (m.getMeasureChoice() == MeasureChoice.Chosen) {
					if ( measureL.get(MeasureObject._PK).getValue() > 0 ) {
						m.setMP0PK( measureL.get(MeasureObject._PK).getValue()  );

						double Hsatv1 = m.matchP2HvSat( m.getMP());
						double Psat1 = m.getMP();
						double P0PK1 = m.getMP0PK();
						double Hmpiso1 = m.CompHmatchPSatWithP0PK(Hsatv1, Psat1, P0PK1); 
						m.setMHaprox(Hmpiso1);

						m.setMeasureChoice(MeasureChoice.ChosenHaprox);
					}
				}
				break;
			case P3 : case P4 : case P7 :	// Line P0 or PK
				if (m.getValue() > 0 ) {
					// P must be > 0
					m.setMP( m.getValue() );
					m.setMT( m.convP2T( m.getValue() ) );
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

	// -------------------------------------------------------
	// 							JSON
	// -------------------------------------------------------
	//	Squiggly brackets {} act as containers  
	//	Names and values are separated by a colon(:) 	--> put
	//  Square brackets[] represents arrays.			--> add
	//  {  "Planet": "Earth" , "Countries": [  { "Name": "India", "Capital": "Delhi"}, { "Name": "France", "Major": "Paris" } ]  }  
	// -------------------------------------------------------

	/**
	 * Construct the JSON data
	 * @return : JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getJsonObject() {
		JSONObject jsonObj = new JSONObject();  
		jsonObj.put("MeasureObject", this.measureObject);
		jsonObj.put("Value", this.value);	
		jsonObj.put("MeasureChoice", this.measureChoice);	
		jsonObj.put("P", this.P);	
		jsonObj.put("T", this.T);	
		jsonObj.put("Haprox", this.Haprox);	
		jsonObj.put("Hreal", this.Hreal);	
		return jsonObj ;
	}

	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.measureObject = (MeasureObject) jsonObj.get("MeasureObject");
		this.value = ((Number) jsonObj.get("Value")).doubleValue();
		this.measureChoice = (MeasureChoice) jsonObj.get("MeasureChoice");
		this.P = ((Number) jsonObj.get("P")).intValue();
		this.T = ((Number) jsonObj.get("T")).intValue();
		this.Haprox = ((Number) jsonObj.get("Haprox")).intValue();
		this.Hreal = ((Number) jsonObj.get("Hreal")).intValue();
	}


	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public MeasureObject getMeasureObject() {
		return measureObject;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public MeasureChoice getMeasureChoice() {
		return measureChoice;
	}

	public void setMeasureChoice(MeasureChoice measureChoice) {
		this.measureChoice = measureChoice;
	}

	public double getMP() {
		return P;
	}

	public void setMP(double p) {
		P = p;
	}

	public double getMT() {
		return T;
	}	

	public void setMT(double t) {
		T = t;
	}
	
	public double getMHaprox() {
		return Haprox;
	}	

	public void setMHaprox(double h) {
		Haprox = h;
	}

	public double getMHreal() {
		return Hreal;
	}	

	public void setMHreal(double h) {
		Hreal = h;
	}

	public double getMP0PK() {
		return P0PK;
	}

	public void setMP0PK(double p0pk) {
		P0PK = p0pk;
	}
	
}
