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

import org.json.simple.JSONObject;

public class MeasurePoint {
	private MeasureObject measureObject;	
	private double value;					// Pressure or Temperature,..
	private MeasureChoiceStatus measureChoiceStatus;	// MeasurePoint to be considered (chosen or Hreal or H)
	private double P;						// Pressure
	private double T;						// Temperature
	private double H;					// Value Enthalpy approximation computed by Matching PSat (T-Isotherm) with P0PK
	private double P0PK;					// Value P0 or PK Pressure

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public MeasurePoint(MeasureObject vmeasurePoint) {
		this.measureObject = vmeasurePoint;
		this.value = 0.0;
		this.measureChoiceStatus = MeasureChoiceStatus.NotChosen;
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
		this.measureChoiceStatus = MeasureChoiceStatus.NotChosen;
		this.P = 0;
		this.T = 0;
		this.H = 0;
		this.P0PK = 0;
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
		jsonObj.put("MeasureChoiceStatus", this.measureChoiceStatus);	
		jsonObj.put("P", this.P);	
		jsonObj.put("T", this.T);	
		jsonObj.put("H", this.H);	
		return jsonObj ;
	}

	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.measureObject = (MeasureObject) jsonObj.get("MeasureObject");
		this.value = ((Number) jsonObj.get("Value")).doubleValue();
		this.measureChoiceStatus = (MeasureChoiceStatus) jsonObj.get("MeasureChoiceStatus");
		this.P = ((Number) jsonObj.get("P")).intValue();
		this.T = ((Number) jsonObj.get("T")).intValue();
		this.H = ((Number) jsonObj.get("H")).intValue();
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

	public MeasureChoiceStatus getMeasureChoiceStatus() {
		return measureChoiceStatus;
	}

	public void setMeasureChoiceStatus(MeasureChoiceStatus measureChoiceStatus) {
		this.measureChoiceStatus = measureChoiceStatus;
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
	
	public double getMH() {
		return H;
	}	

	public void setMH(double h) {
		H = h;
	}


	public double getMP0PK() {
		return P0PK;
	}

	public void setMP0PK(double p0pk) {
		P0PK = p0pk;
	}
	
}
