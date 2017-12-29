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

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import pac.Pac;
import refrigerant.HSat;
import refrigerant.PSat;
import refrigerant.Refrigerant;

public class MeasurePoint {

	// -------------------------------------------------------
	// 					CONSTANT
	// -------------------------------------------------------
	//private static final Logger logger = LogManager.getLogger(MeasurePoint.class.getName());
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private EloMeasurePoint mPObject;					// P1,P2,P3,... + Definition
	private double value;								// Pressure, Temperature or ..
	private double P;									// Pressure (can be different than P0 or PK)
	private double T;									// Temperature
	private double H;									// Value Enthalpy approximation or real=moved manually
	private EloMeasurePointSelection mPObjectSelection;	// Measure Point to be considered Selected, Not Selected, in reference to the section picture 
	//private EloMeasurePointCompleted mPobjetCompleted;	// Measure Point to be considered computation completed  

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
		this.P = 0.0;
		this.T = 0.0;
		this.H = 0.0;
		this.mPObjectSelection = EloMeasurePointSelection.NotSelected;
		//this.mPobjetCompleted = EloMeasurePointCompleted.NotComputed;
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	/**
	 * Clear the measure with all its elements
	 */
	public void clearMeasure() {
		this.value = 0;
		this.P = 0;
		this.T = 0;
		this.H = 0;
		this.mPObjectSelection = EloMeasurePointSelection.NotSelected;
		//this.mPobjetCompleted = EloMeasurePointCompleted.NotComputed;
	}

	public void setValue(double value,Pac pac, List<MeasurePoint> lMeasurePoints ) {
		logger.trace("{} setValue --> value={}", this.mPObject,  value );
		Refrigerant refrigerant = pac.getRefrigerant(); 
		
		PSat psat;
		HSat hSat;
		
		this.value = value;
		this.mPObjectSelection = EloMeasurePointSelection.Selected;

		for (EloMeasurePoint p : EloMeasurePoint.values()) {

			switch (p) {

			case P1 :
				//  BP gas temperature after internal overheating and before compression
				// 	Should never happen as P1_T = P8_T + overheating, 
				// 	but here, the user has chosen to enter P1
				//  Value Entered = Temperature
				/*
				|         X |                               XX          /
				|        XX |             (PR0)               X         /
				|       XX  +---------------------------------+---+---+
				|       X  (6)                               (7) (8)  (1)
				|      XX                                     X
				 */
				this.mPObjectSelection = EloMeasurePointSelection.Selected;
				this.T= value;
				psat = refrigerant.getPSatFromT(value); 
				this.P = psat.getPLiquid();

				// if PR0 > 0 then only H can be computed, and consider
				if (getMP_P0PK(lMeasurePoints) > 0.0) {
					hSat = refrigerant.getHSatFromT(T);
					this.H = hSat.getHLiquid();
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
				this.mPObjectSelection = EloMeasurePointSelection.Selected;
				this.T= value;
				psat = refrigerant.getPSatFromT(value); 
				this.P = psat.getPLiquid();
				// if PK > 0 then only H can be computed
				if (getMP_P0PK(lMeasurePoints) > 0.0) {
					hSat = refrigerant.getHSatFromT(T);
					this.H = hSat.getHLiquid();
				}
				break;


			default:
				break;
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
		jsonObj.put("P", this.P);	
		jsonObj.put("T", this.T);	
		jsonObj.put("H", this.H);
		jsonObj.put("MeasurePointSelection", this.mPObjectSelection);	
		//jsonObj.put("MeasurePointCompleted", this.mPobjetCompleted);	
		return jsonObj ;
	}

	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.mPObject = (EloMeasurePoint) jsonObj.get("MeasurePoint");
		this.value = ((Number) jsonObj.get("Value")).doubleValue();
		this.P = ((Number) jsonObj.get("P")).intValue();
		this.T = ((Number) jsonObj.get("T")).intValue();
		this.H = ((Number) jsonObj.get("H")).intValue();
		this.mPObjectSelection = (EloMeasurePointSelection) jsonObj.get("MeasurePointSelection");
		//this.mPobjetCompleted = (EloMeasurePointCompleted) jsonObj.get("MeasurePointCompleted");
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

	public void setP(double p) {
		P = p;
	}

	public void setT(double t) {
		T = t;
	}

	public void setH(double h) {
		H = h;
	}

	// Will return PR0 or PRK depending of the current object. 
	// The information will be read from Group of EloMeasurePoint
	// Example: P1 belongs to GROUP_BP
	// So the function will return PR0, but only if PR0 has been computed
	// if not -1;
	public double getMP_P0PK( List<MeasurePoint> lMeasurePoints) {
		double out = -1.0;

		double PR0,PRK = 0;

		int idPR0 = EloMeasurePoint._PR0id;
		int idPRK = EloMeasurePoint._PRKid;

		// Check of PR0 has still be computed through the lMeasurePoints
		if (lMeasurePoints.get(idPR0).getMP_P() > 0 )
			PR0 = lMeasurePoints.get(idPR0).getMP_P();
		else
			PR0 = -1.0;


		// Check of PRK has still be computed through the lMeasurePoints
		if (lMeasurePoints.get(idPRK).getMP_P()>0)
			PRK = lMeasurePoints.get(idPRK).getMP_P();
		else
			PRK = -1.0;

		switch (this.mPObject) {

		case P1: case P6: case P7: case P8:
			out = PR0;
			logger.trace("PR0 = {}",out);
			break;
		case P2 : case P3 : case P4 : case P5 :
			out = PRK;
			logger.trace("PRK = {}",out);
			break;
		default:
			logger.trace("PR0/PRK = {}",out);
			out = -1.0;
			break;
		}

		return out;
	}


	public EloMeasurePointSelection getMP_ChoiceStatus() {
		return mPObjectSelection;
	}

	public void setMP_ChoiceStatus(EloMeasurePointSelection mPObjectSelection) {
		this.mPObjectSelection = mPObjectSelection;
	}

}
