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
package measurePoint;

import org.json.simple.JSONObject;

public class MeasurePoint {

	// -------------------------------------------------------
	// 					CONSTANT
	// -------------------------------------------------------
	//private static final Logger logger = LogManager.getLogger(SatCurve.class.getName());

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------

	private double value;										// Pressure, Temperature or ..
	private EloMeasurePointSelection eloMeasurePointSelection;	// MeasurePoint to be considered 

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public MeasurePoint() {
		this.value = 0.0;
		this.eloMeasurePointSelection = EloMeasurePointSelection.NotChosen;
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	/**
	 * Clear the measure with all its elements
	 */
	public void clearMeasure() {
		this.value = 0;
		this.eloMeasurePointSelection = EloMeasurePointSelection.NotChosen;
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
		jsonObj.put("Value", this.value);	
		jsonObj.put("MeasureChoiceStatus", this.eloMeasurePointSelection);	
		return jsonObj ;
	}

	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.value = ((Number) jsonObj.get("Value")).doubleValue();
		this.eloMeasurePointSelection = (EloMeasurePointSelection) jsonObj.get("MeasureChoiceStatus");
	}


	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public EloMeasurePointSelection selected() {
		return eloMeasurePointSelection;
	}

	public void setMeasureChoiceStatus(EloMeasurePointSelection eloMeasurePointSelection) {
		this.eloMeasurePointSelection = eloMeasurePointSelection;
	}
	
}
