/*
 * - PAC-Tool - 
 * Tool for understanding basics and computation of PAC (Pompe � Chaleur)
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
package mpoints;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import refrigerant.PSat;
import refrigerant.Refrigerant;

public class MeasurePoint {

	// -------------------------------------------------------
	// 					CONSTANT
	// -------------------------------------------------------
	private static final Logger logger = LogManager.getLogger(MeasurePoint.class.getName());

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private EloMeasurePoint mPObject;					// P1,P2,P3,... + Definition
	private double value;								// Pressure, Temperature or ..
	private double P;									// Pressure (can be different than P0 or PK)
	private double T;									// Temperature
	private double H;									// Value Enthalpy approximation or real=moved manually
	private double P0PK;								// Value P0 or PK Pressure
	private EloMeasurePointSelection mPObjectSelection;	// Measure Point to be considered Selected, Not Selected, .. 

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	// Create 1 MeasurePoint with the object belonging to enumeration
	// Exemple
	//			MeasurePoint pm_P1 = new MeasurePoint("P1")
	//			pm_P1.setValue(18.34)
	public MeasurePoint(EloMeasurePoint vmPObject ) {
		this.mPObject = vmPObject;
		this.value = 0.0;
		this.mPObjectSelection = EloMeasurePointSelection.NotChosen;
		this.P = 0.0;
		this.T = 0.0;
		this.H = 0.0;
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
		this.mPObjectSelection = EloMeasurePointSelection.NotChosen;
		this.P = 0;
		this.T = 0;
		this.H = 0;
		this.P0PK = 0;
	}

	public void setValue(double value,Refrigerant refrigerant, ArrayList<MeasurePoint> lMeasurePoints ) {
		this.value = value;

		for (EloMeasurePoint p : EloMeasurePoint.values()) {

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
				if (mPObjectSelection == EloMeasurePointSelection.Chosen ) {
					this.T= value;
					PSat psat = refrigerant.getPSatFromT(value); 
					this.P = psat.getPGas();
					// if P0 > 0 then only H can be computed
					if ( measurePointL.get(EloMeasurePoint._P0_ID).getValue() > 0 ) {
						m.setMP0PK( measurePointL.get(EloMeasurePoint._P0_ID).getValue() );
						double Hmpiso0 = enthalpy.CompHmatchPSatWithP0PK(m); 
						m.setMH(Hmpiso0);
						m.setMeasureChoiceStatus(EloMeasurePointSelection.ChosenHaprox);
					}
				}
				break;
			default:
				break;
			}

			}
		}	
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
		jsonObj.put("MeasurePoint", this.mPObject);
		jsonObj.put("Value", this.value);	
		jsonObj.put("MeasurePointChoiceStatus", this.mPObjectSelection);	
		jsonObj.put("P", this.P);	
		jsonObj.put("T", this.T);	
		jsonObj.put("H", this.H);
		jsonObj.put("P0PK", this.P0PK);	
		return jsonObj ;
	}

	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.mPObject = (EloMeasurePoint) jsonObj.get("MeasurePoint");
		this.value = ((Number) jsonObj.get("Value")).doubleValue();
		this.mPObjectSelection = (EloMeasurePointSelection) jsonObj.get("MeasurePointChoiceStatus");
		this.P = ((Number) jsonObj.get("P")).intValue();
		this.T = ((Number) jsonObj.get("T")).intValue();
		this.H = ((Number) jsonObj.get("H")).intValue();
		this.P0PK = ((Number) jsonObj.get("P0PK")).intValue();
	}


	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public EloMeasurePoint getMPObject() {
		return mPObject;
	}

	public double getValue() {
		return value;
	}
	public double getMP_P() {
		return P;
	}

	public double getMP_T() {
		return T;
	}	

	public double getMP_H() {
		return H;
	}	

	public double getMP_P0PK() {
		return P0PK;
	}

	public EloMeasurePointSelection getMP_ChoiceStatus() {
		return mPObjectSelection;
	}

	public void setMP_ChoiceStatus(EloMeasurePointSelection mPObjectSelection) {
		this.mPObjectSelection = mPObjectSelection;
	}

}
