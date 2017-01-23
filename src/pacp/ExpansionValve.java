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

public class ExpansionValve {

	private String name;
	private double deltaT;	// Delta temperature in °C
	private double deltaP;	// Delta pressure in bar = Charge Loss

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public ExpansionValve() {
		setName("Capillaire");
		deltaT = 0;
		deltaP = 0;
	}

	// -------------------------------------------------------
	// 						Methods
	// -------------------------------------------------------
	/**
	 * ExpansionValve will decrease the Pressure and Temperature
	 * @param Refrigerant: inGas
	 * @return : Refrigerant : outGas
	 */
	public Refrigerant transfer(Refrigerant vinGas) {
		vinGas.setT(vinGas.getT()+ deltaT);
		vinGas.setP(vinGas.getP()+ deltaP);
		return vinGas;
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
		jsonObj.put("Name", this.name);
		jsonObj.put("DeltaT", this.deltaT);	
		jsonObj.put("DeltaP", this.deltaP);	
		return jsonObj ;
	}

	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.name = (String) jsonObj.get("Name");
		this.deltaT = ((Number) jsonObj.get("DeltaT")).doubleValue();
		this.deltaP = ((Number) jsonObj.get("DeltaP")).doubleValue();
	}

	// -------------------------------------------------------
	// 				Getter and Setter
	// -------------------------------------------------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDeltaP() {
		return deltaP;
	}

	public void setDeltaP(double deltaP) {
		this.deltaP = deltaP;
	}

	public double getDeltaT() {
		return deltaT;
	}

	public void setDeltaT(double deltaT) {
		this.deltaT = deltaT;
	}


}
