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

import java.util.HashMap;

import org.json.simple.JSONObject;

public class PrimeConfig {


	private boolean unitCompBTU;		// checkoxBTU
	private boolean unitCompPound;		// chckbxPound
	private boolean unitCompFaren;		// checkoxFaren

	private HashMap<String, String> hmapTranslate = new HashMap<String, String>();
	private HashMap<String, String> hmapTranslate_fr = new HashMap<String, String>();

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public PrimeConfig() {
		unitCompBTU = true;
		unitCompPound = true;
		unitCompFaren = true;

		//By default in English
		hmapTranslate.put("Capacity", 		"Capacity: ");
		hmapTranslate.put("Power", 			"Power: ");
		hmapTranslate.put("Current", 		"Current: ");
		hmapTranslate.put("EER", 			"EER: ");
		hmapTranslate.put("Voltage", 		"Voltage: ");
		hmapTranslate.put("Mass flow", 		"Mass flow: ");
		hmapTranslate.put("Evap", 			"Evap: ");
		hmapTranslate.put("RG", 			"RG: ");
		hmapTranslate.put("Cond", 			"Cond: ");
		hmapTranslate.put("Liq", 			"Liq: ");
		hmapTranslate.put("Under cooling", 	"Under cooling: ");
		hmapTranslate.put("Overheated", 	"Overheated: ");

		//By French
		hmapTranslate_fr.put("Capacity", 	"Capacité: ");
		hmapTranslate_fr.put("Capacity",	"P. Frigo.: ");
		hmapTranslate_fr.put("Power", 		"P. Absorb.: ");
		hmapTranslate_fr.put("Current", 	"Courant: ");
		hmapTranslate_fr.put("EER", 		"COP Froid: ");
		hmapTranslate_fr.put("Voltage", 	"Tension: ");
		hmapTranslate_fr.put("Mass flow", 	"Débit Massique: ");
		hmapTranslate_fr.put("Evap", 		"T0. Evap.: ");
		hmapTranslate_fr.put("RG", 			"T. Asp. Comp.: ");
		hmapTranslate_fr.put("Cond", 		"TK. Cond.: ");
		hmapTranslate_fr.put("Liq", 		"T. Détent.: ");
		hmapTranslate.put("Under cooling", 	"S/Refroidissement: ");
		hmapTranslate.put("Overheated", 	"Surchauffe: ");

	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public String TranslateText(String key, String dic) {
		String result = "Wrong Key in TranslateText method";

		// By default English
		String out = hmapTranslate.get(key);

		// French : revert to default if not found
		if (dic.equals("fr")) {
			String outfr = hmapTranslate_fr.get(key);
			if (outfr != null)
				out = outfr; 
		}

		if (out != null)
			result = out;
		
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



}
