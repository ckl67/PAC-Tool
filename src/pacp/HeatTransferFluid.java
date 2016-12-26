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

public class HeatTransferFluid {

	private String name;
	private double T;
	private double P; 
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	
	public HeatTransferFluid() {
		this.name = "Fluide caloporteur";
		this.T  = 0; 	// Heat Transfer Fluid Temperature in °C	
		this.P = 0;		// Heat Transfer Fluid Pressure in °C
	}
	
	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	/**
	 * Return the JSON data
	 * @return : JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getJsonObject() {
		JSONObject ObjComp = new JSONObject();  
		ObjComp.put("Name", this.name);
		ObjComp.put("T", this.T);	
		ObjComp.put("P", this.P);	
		return ObjComp ;
	}
	
	/**
	 * Set Class with the element coming from a the JSON object
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.name = (String) jsonObj.get("Name");
		this.T = (double) jsonObj.get("T");
		this.P = (double) jsonObj.get("P");
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getT() {
		return T;
	}
	public void setT(double T) {
		this.T = T;
	}
	public double getP() {
		return P;
	}
	public void setP(double P) {
		this.P = P;
	}
}
