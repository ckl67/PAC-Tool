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

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Circulator {

	private String name;
	private double voltage;
	ArrayList<Double> currentL = new ArrayList<Double>();
	ArrayList<Integer> rotatePerMinutesL = new ArrayList<Integer>();
	ArrayList<Integer> powerL = new ArrayList<Integer>();

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Circulator () {
		name = "DAB A 80/180XM";
		voltage = 220;

		//setNbListFeatures(3);
		rotatePerMinutesL.add(1620);
		powerL.add(190);
		currentL.add(0.91);

		rotatePerMinutesL.add(2420);
		powerL.add(226);
		currentL.add(0.98);

		rotatePerMinutesL.add(2710);
		powerL.add(236);
		currentL.add(1.0);		
	}
	
	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Simulate the circulator
	 * @param HeatTransferFluid: vinFluid 
	 * @return: HeatTransferFluid
	 */
	public HeatTransferFluid transfer(HeatTransferFluid vinFluid) {
		
		return vinFluid;
	}

	/**
	 * Add New Circulator features
	 * @param vcurrent
	 * @param vrotatePerMinutes
	 * @param vpower
	 */
	public void addFeatures(double vcurrent, int vrotatePerMinutes, int vpower ) {
		this.rotatePerMinutesL.add(vrotatePerMinutes);
		this.powerL.add(vpower);
		this.currentL.add(vcurrent);
	}

	/**
	 * Clear the Feature List
	 */
	public void clearFeatures() {
		this.rotatePerMinutesL.clear();
		this.powerL.clear();
		this.currentL.clear();
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
		JSONArray ObjFeatureL = new JSONArray();

		jsonObj.put("Name", this.name);
		jsonObj.put("Voltage", this.voltage);

		for(int i=0; i< currentL.size();i++) {
			JSONObject ObjFeature = new JSONObject();  
			ObjFeature.put("Current", this.currentL.get(i));
			ObjFeature.put("Power", this.powerL.get(i));
			ObjFeature.put("RotatePerMinutes", this.rotatePerMinutesL.get(i));
			ObjFeatureL.add(ObjFeature);
		}
		jsonObj.put("Features", ObjFeatureL);
		return jsonObj;
	}

	/**
	 * Set the JSON d<ata, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.name = (String) jsonObj.get("Name");
		this.voltage = (double)jsonObj.get("Voltage");

		// Clear all Features
		this.clearFeatures();

		// Fill all feature with jsonObj
		JSONArray ObjFeatureL = (JSONArray) jsonObj.get("Features");
		for(int i=0; i< ObjFeatureL.size();i++) {
			JSONObject jsonObjectL2 = (JSONObject) ObjFeatureL.get(i);
			this.rotatePerMinutesL.add((int)jsonObjectL2.get("RotatePerMinutes"));
			this.powerL.add((int)jsonObjectL2.get("Power"));
			this.currentL.add((double)jsonObjectL2.get("Current"));
		}
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

	public double getVoltage() {
		return voltage;
	}

	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}

	public ArrayList<Double> getCurrentL() {
		return currentL;
	}

	public ArrayList<Integer> getRotatePerMinutesL() {
		return rotatePerMinutesL;
	}

	public ArrayList<Integer> getPowerL() {
		return powerL;
	}

}
