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
package gui;

import org.json.simple.JSONObject;

import translation.TLanguage;

public class GuiConfig {

	private TLanguage language;		
	
	private boolean unitCompBTU;		// checkoxBTU
	private boolean unitCompPound;		// chckbxPound
	private boolean unitCompFaren;		// checkoxFaren

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public GuiConfig() {
		language = TLanguage.ENGLICH;
		
		unitCompBTU = true;
		unitCompPound = true;
		unitCompFaren = true;
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
		jsonObj.put("language", this.language.ordinal());	
		jsonObj.put("unitCompBTU", this.unitCompBTU);	
		jsonObj.put("unitCompPound", this.unitCompPound);
		jsonObj.put("unitCompFaren", this.unitCompFaren);
		return jsonObj ;

	}

	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		int language_ordinal;
		
		language_ordinal = ((Number) jsonObj.get("language")).intValue();
		
		   for (TLanguage p : TLanguage.values()) {
			   if (p.ordinal() == language_ordinal)
				   this.language = p;
		   }
			   
		this.unitCompBTU = (boolean) jsonObj.get("unitCompBTU");
		this.unitCompPound = (boolean) jsonObj.get("unitCompPound");
		this.unitCompFaren = (boolean) jsonObj.get("unitCompFaren");
		// To use afterward this principle
		//this.xHmin = ((Number) jsonObj.get("xHmin")).doubleValue();
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public boolean getUnitCompBTU() {
		return unitCompBTU;
	}

	public boolean isUnitCompBTU() {
		return unitCompBTU;
	}

	public void setUnitCompBTU(boolean unitCompBTU) {
		this.unitCompBTU = unitCompBTU;
	}

	public boolean getUnitCompPound() {
		return unitCompPound;
	}

	public boolean isUnitCompPound() {
		return unitCompPound;
	}

	public void setUnitCompPound(boolean unitCompPound) {
		this.unitCompPound = unitCompPound;
	}

	public boolean getUnitCompFaren() {
		return unitCompFaren;
	}

	public boolean isUnitCompFaren() {
		return unitCompFaren;
	}

	public void setUnitCompFaren(boolean unitCompFaren) {
		this.unitCompFaren = unitCompFaren;
	}

	public TLanguage getLanguage() {
		return language;
	}

	public void setLanguage(TLanguage language) {
		this.language = language;
	}



}
