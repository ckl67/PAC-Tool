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
package pacp;

import org.json.simple.JSONObject;

public class Condenser {

	private String name;
	private double deltaT;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	Condenser() {
		this.name = "Condenseur";
		this.deltaT  = 0; 	// Condenser Delta temperature in �C	
	}
	
	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Function Transfer
	 * @param Refrigerant
	 * @return Refrigerant
	 */
	public Refrigerant transfer(Refrigerant vinGas) {
		vinGas.setT(  vinGas.getT() + deltaT );
		return vinGas;
	}

	/**
	 * Return the JSON data
	 * @return : JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getJsonObject() {
		JSONObject ObjComp = new JSONObject();  
		ObjComp.put("Name", this.name);
		ObjComp.put("DeltaT", this.deltaT);	
		return ObjComp ;
	}
	
	/**
	 * Set Class with the element coming from a the JSON object
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.name = (String) jsonObj.get("Name");
		this.deltaT = (double) jsonObj.get("DeltaT");
	}

	
	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	// Setter & Getter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getDeltaT() {
		return deltaT;
	}

	public void setDeltaT(double deltaT) {
		this.deltaT = deltaT;
	}

}
