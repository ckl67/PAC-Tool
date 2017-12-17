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
package pac;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import coolant.Coolant;
import refrigerant.Refrigerant;

public class Pac {

	private static final Logger logger = LogManager.getLogger(Pac.class.getName());

	// --------------------------------------------------------------------
	// Instance variables
	// --------------------------------------------------------------------
	private Compressor compressor;
	private Condenser condenser;
	private Dehydrator dehydrator;
	private ExpansionValve expansionValve;
	private Evaporator evaporator;

	private Circulator circulatorSrc;
	private HeatSrcDistrCircuit heatSrcCircuit;

	private Circulator circulatorDistr;
	private HeatSrcDistrCircuit heatDistrCircuit;

	private Refrigerant refrigerant;

	private Coolant coolantDistr;
	private Coolant coolantSrc;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Pac() {

		compressor = new Compressor();
		condenser = new Condenser();
		dehydrator = new Dehydrator();
		expansionValve = new ExpansionValve();
		evaporator = new Evaporator();

		circulatorSrc = new Circulator();
		heatSrcCircuit = new HeatSrcDistrCircuit();

		circulatorDistr = new Circulator();
		heatDistrCircuit = new HeatSrcDistrCircuit();

		refrigerant = new Refrigerant();

		coolantDistr = new Coolant();
		coolantSrc = new Coolant();

	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Will simulate a complete PAC cycle (Gaz/Heat Source and Distribution)
	 * 
	 * @param GasInjected in :  COMPRESSOR, CONDENSER, EXPANSIONVALVE or EVAPORATOR
	 * 				The : Coolant will always be injected in the circulator !!
	 */
	public void PacCycle(PacGasInjected GasInjected) {

		switch (GasInjected) {

		case COMPRESSOR :
			refrigerant = compressor.transfer(refrigerant);
			refrigerant = condenser.transfer(refrigerant);
			refrigerant = dehydrator.transfer(refrigerant);
			refrigerant = expansionValve.transfer(refrigerant);
			refrigerant = evaporator.transfer(refrigerant);
			break;
		case CONDENSER : 
			refrigerant = condenser.transfer(refrigerant);
			refrigerant = dehydrator.transfer(refrigerant);
			refrigerant = expansionValve.transfer(refrigerant);
			refrigerant = evaporator.transfer(refrigerant);
			refrigerant = compressor.transfer(refrigerant);
			break;
		case EXPANSIONVALVE : 
			refrigerant = expansionValve.transfer(refrigerant);
			refrigerant = evaporator.transfer(refrigerant);
			refrigerant = compressor.transfer(refrigerant);
			refrigerant = condenser.transfer(refrigerant);
			refrigerant = dehydrator.transfer(refrigerant);
			break;
		default:
			refrigerant = evaporator.transfer(refrigerant);
			refrigerant = compressor.transfer(refrigerant);
			refrigerant = condenser.transfer(refrigerant);
			refrigerant = dehydrator.transfer(refrigerant);
			refrigerant = expansionValve.transfer(refrigerant);
			break;
		}
		// Cycle Heat Source 
		coolantSrc=circulatorSrc.transfer(coolantSrc);
		coolantSrc=heatSrcCircuit.transfer(coolantSrc);

		// Cycle Heat Distribution
		coolantDistr =circulatorDistr.transfer(coolantDistr);
		coolantDistr =heatDistrCircuit.transfer(coolantDistr);	
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

		jsonObj.put("Compressor", this.compressor.getJsonObject());
		jsonObj.put("Condenser", this.condenser.getJsonObject());
		jsonObj.put("Dehydrator", this.dehydrator.getJsonObject());
		jsonObj.put("ExpansionValve", this.expansionValve.getJsonObject());
		jsonObj.put("Evaporator", this.evaporator.getJsonObject());

		jsonObj.put("CirculatorSrc", this.circulatorSrc.getJsonObject());
		jsonObj.put("HeatSrcCircuit", this.heatSrcCircuit.getJsonObject());

		jsonObj.put("CirculatorDistr", this.circulatorDistr.getJsonObject());
		jsonObj.put("HeatDistrCircuit", this.heatDistrCircuit.getJsonObject());

		jsonObj.put("Refrigerant", this.refrigerant.getJsonObject());

		jsonObj.put("CoolantDistr", this.coolantDistr.getJsonObject());
		jsonObj.put("CoolantSrc", this.coolantSrc.getJsonObject());

		logger.info("jsonObj {}",jsonObj);

		return jsonObj;
	}


	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {

		logger.info("jsonObj {}",jsonObj);
		compressor.setJsonObject((JSONObject)(jsonObj.get("Compressor")));
		condenser.setJsonObject((JSONObject)(jsonObj.get("Condenser")));
		dehydrator.setJsonObject((JSONObject)(jsonObj.get("Dehydrator")));
		expansionValve.setJsonObject((JSONObject)(jsonObj.get("ExpansionValve")));
		evaporator.setJsonObject((JSONObject)(jsonObj.get("Evaporator")));

		circulatorSrc.setJsonObject((JSONObject)(jsonObj.get("CirculatorSrc")));
		heatSrcCircuit.setJsonObject((JSONObject)(jsonObj.get("HeatSrcCircuit")));

		circulatorDistr.setJsonObject((JSONObject)(jsonObj.get("CirculatorDistr")));
		heatDistrCircuit.setJsonObject((JSONObject)(jsonObj.get("HeatDistrCircuit")));

		refrigerant.setJsonObject((JSONObject)(jsonObj.get("Refrigerant")));
		coolantDistr.setJsonObject((JSONObject)(jsonObj.get("CoolantDistr")));
		coolantSrc.setJsonObject((JSONObject)(jsonObj.get("CoolantSrc")));


	}
	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public Compressor getCompressor() {
		return compressor;
	}

	public Condenser getCondenser() {
		return condenser;
	}

	public Dehydrator getDehydrator() {
		return dehydrator;
	}

	public ExpansionValve getExpansionValve() {
		return expansionValve;
	}

	public Evaporator getEvaporator() {
		return evaporator;
	}

	public Circulator getCirculatorSrc() {
		return circulatorSrc;
	}

	public HeatSrcDistrCircuit getHeatSrcCircuit() {
		return heatSrcCircuit;
	}

	public Circulator getCirculatorDistr() {
		return circulatorDistr;
	}

	public HeatSrcDistrCircuit getHeatDistrCircuit() {
		return heatDistrCircuit;
	}

	public Refrigerant getRefrigerant() {
		return refrigerant;
	}

	public Coolant getCoolantDistr() {
		return coolantDistr;
	}

	public Coolant getCoolantSrc() {
		return coolantSrc;
	}



}
