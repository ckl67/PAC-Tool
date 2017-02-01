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

import org.json.simple.JSONObject;

public class Measure extends Enthalpy {
	
	/* ----------------------------------------
	 *  	STATIC GLOBAL VAR PUBLIC
	 * ---------------------------------------- */	
	private MeasurePoint measurePoint;			
	private double value;			// Can be Pressure, Temperature,..
	private boolean chosen;			// Indicate if the Measure point has to be considered 
	private double P;				// Pressure
	private double H;				// Enthalpy
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Measure(MeasurePoint vmeasurePointE) {
		this.measurePoint = vmeasurePointE;
		this.value = 0.0;
		this.chosen = false;
		this.P = 0.0;
		this.H = 0.0;
	}
	
	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

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
		jsonObj.put("MeasurePoint", this.measurePoint);
		jsonObj.put("Value", this.value);	
		jsonObj.put("Chosen", this.chosen);	
		jsonObj.put("P", this.P);	
		jsonObj.put("H", this.H);	
		return jsonObj ;
	}
	
	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.measurePoint = (MeasurePoint) jsonObj.get("MeasurePoint");
		this.value = ((Number) jsonObj.get("Value")).doubleValue();
		this.chosen = ((Boolean) jsonObj.get("Chosen")).booleanValue();
		this.P = ((Number) jsonObj.get("P")).intValue();
		this.H = ((Number) jsonObj.get("H")).intValue();
	}

	
	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------
	
	public MeasurePoint getMeasurePointE() {
		return measurePoint;
	}
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean isChosen() {
		return chosen;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

	public double getP() {
		return P;
	}

	public double getH() {
		return H;
	}
	

}
