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
package pac;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Circulator {

	private static final Logger logger = LogManager.getLogger(Pac.class.getName());

	// --------------------------------------------------------------------
	// Instance variables
	// --------------------------------------------------------------------
	private String name;
	private double voltage;
	ArrayList<Double> currentL = new ArrayList<Double>();
	ArrayList<Integer> rotatePerMinutesL = new ArrayList<Integer>();
	ArrayList<Integer> powerL = new ArrayList<Integer>();

	private int idFeature;	
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Circulator () {
		logger.trace("Circulator() --> New Circulator default");
		this.name = "DAB A 80/180XM";
		this.voltage = 220;

		this.idFeature = 0;
		
		//setNbListFeatures(3);
		this.rotatePerMinutesL.add(1620);
		this.powerL.add(190);
		this.currentL.add(0.91);

		this.rotatePerMinutesL.add(2420);
		this.powerL.add(226);
		this.currentL.add(0.98);

		this.rotatePerMinutesL.add(2710);
		this.powerL.add(236);
		this.currentL.add(1.0);		
	}
	
	public Circulator (	String vname, double vvoltage) {
		logger.trace("Circulator({},{}) --> New Circulator with features all set to 0 ", vname, vvoltage);
		this.name = vname;
		this.voltage = vvoltage;

		this.idFeature = 0;

		this.rotatePerMinutesL.add(0);
		this.powerL.add(0);
		this.currentL.add(0.0);

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
		vinFluid.setT(vinFluid.getT()+ 0.0);
		vinFluid.setP(vinFluid.getP()+ 0.0);
		return vinFluid;
	}

	/**
	 * Add New Circulator features
	 * @param vcurrent
	 * @param vrotatePerMinutes
	 * @param vpower
	 */
	public void addFeatures(double vcurrent, int vrotatePerMinutes, int vpower ) {
		rotatePerMinutesL.add(vrotatePerMinutes);
		powerL.add(vpower);
		currentL.add(vcurrent);
	}

	/**
	 * Modify Circulator features at position id
	 * @param position
	 * @param vcurrent
	 * @param vrotatePerMinutes
	 * @param vpower
	 */
	public void modifyFeatures(int position, double vcurrent, int vrotatePerMinutes, int vpower ) {
		logger.trace("modifyFeatures(position={},vcurrent={},vrotatePerMinutes={},vpower={})",position,vcurrent,vrotatePerMinutes,vpower);
		rotatePerMinutesL.remove(position);
		powerL.remove(position);
		currentL.remove(position);
		
		rotatePerMinutesL.add(position,vrotatePerMinutes);
		powerL.add(position,vpower);
		currentL.add(position,vcurrent);
	}

	/**
	 * Clear the Feature List
	 */
	public void clearFeatures() {
		rotatePerMinutesL.clear();
		powerL.clear();
		currentL.clear();
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
		jsonObj.put("idFeature", this.idFeature);

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
		this.voltage = ((Number)jsonObj.get("Voltage")).doubleValue();
		this.idFeature = ((Number)jsonObj.get("idFeature")).intValue();

		// Clear all Features
		clearFeatures();

		// Fill all feature with jsonObj
		JSONArray ObjFeatureL = (JSONArray) jsonObj.get("Features");
		for(int i=0; i< ObjFeatureL.size();i++) {
			JSONObject jsonObjectL2 = (JSONObject) ObjFeatureL.get(i);
			this.rotatePerMinutesL.add( ((Number)jsonObjectL2.get("RotatePerMinutes")).intValue());
			this.powerL.add( ((Number)jsonObjectL2.get("Power")).intValue());
			this.currentL.add( ((Number)jsonObjectL2.get("Current")).doubleValue());
		}
	}
	
	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public String getName() {
		return name;
	}

	public double getVoltage() {
		return voltage;
	}

	public int getNbOfFeatures() {
		logger.trace("getNbFeaturesNb()--> Nb. of Features = {}",currentL.size());
		return currentL.size();
	}
	
	public void selectActiveFeature(int id) {
		logger.trace("selectActiveFeature({}) --> Select Active feature {}",id, id);
		idFeature = id;
	}

	public int getActiveFeatureId() {
		logger.trace("getActiveFeatureId() --> Active feature = {}", idFeature);
		return idFeature;
	}

	public Double getActiveFeatureCurrent() {
		logger.trace("getActiveFeatureCurrent()--> id={} Current={}",idFeature,currentL.get(idFeature));
		return currentL.get(idFeature);
	}

	public int getActiveFeatureRotatePerMinutes() {
		logger.trace("getActiveFeatureRotatePerMinutes()--> id={} Rotate per Minutes={}",idFeature,rotatePerMinutesL.get(idFeature));
		return rotatePerMinutesL.get(idFeature);
	}

	public int getActiveFeaturePower() {
		logger.trace("getActiveFeaturePower()--> id={} Power={}",idFeature,powerL.get(idFeature));
		return powerL.get(idFeature);
	}

	public Double getCurrent(int id) {
		return currentL.get(id);
	}

	public int getRotatePerMinutes(int id) {
		return rotatePerMinutesL.get(id);
	}

	public int getPower(int id) {
		return powerL.get(id);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}

}
