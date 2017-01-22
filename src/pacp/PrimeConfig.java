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

public class PrimeConfig {

	boolean unitBTU;		// checkoxBTU
	boolean unitPound;		// chckbxPound
	boolean unitFaren;		// checkoxFaren
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public PrimeConfig() {
		unitBTU = true;
		unitPound = true;
		unitFaren = true;
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
		jsonObj.put("unitBTU", this.unitBTU);	
		jsonObj.put("unitPound", this.unitPound);
		jsonObj.put("unitFaren", this.unitFaren);
		return jsonObj ;

	}
	
	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.unitBTU = (boolean) jsonObj.get("unitBTU");
		this.unitPound = (boolean) jsonObj.get("unitPound");
		this.unitFaren = (boolean) jsonObj.get("unitFaren");
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public boolean getUnitBTU() {
		return unitBTU;
	}

	public boolean isUnitBTU() {
		return unitBTU;
	}

	public void setUnitBTU(boolean unitBTU) {
		this.unitBTU = unitBTU;
	}

	public boolean getUnitPound() {
		return unitPound;
	}

	public boolean isUnitPound() {
		return unitPound;
	}

	public void setUnitPound(boolean unitPound) {
		this.unitPound = unitPound;
	}

	public boolean getUnitFaren() {
		return unitFaren;
	}

	public boolean isUnitFaren() {
		return unitFaren;
	}

	public void setUnitFaren(boolean unitFaren) {
		this.unitFaren = unitFaren;
	}
	
	
	
}
