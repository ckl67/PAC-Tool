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

public class Pac{
	private Compressor compressor;
	private Condenser condenser;
	private Dehydrator dehydrator;
	private ExpansionValve expansionValve;
	private Evaporator evaporator;
	private Refrigerant fluidRefri;
	private Circulator circulatorS;
	private HeatSrcDistrCircuit circuitS;
	private HeatTransferFluid fluidCaloS;
	private Circulator circulatorD;
	private HeatSrcDistrCircuit circuitD;
	private HeatTransferFluid fluidCaloD;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Pac() {

		this.compressor = new Compressor();
		this.condenser = new Condenser();
		this.dehydrator = new Dehydrator();
		this.expansionValve = new ExpansionValve();
		this.evaporator = new Evaporator();
		this.fluidRefri = new Refrigerant();

		this.circulatorS = new Circulator();
		this.circuitS = new HeatSrcDistrCircuit();
		this.fluidCaloS = new HeatTransferFluid();

		this.circulatorD = new Circulator();
		this.circuitD = new HeatSrcDistrCircuit();
		this.fluidCaloD = new HeatTransferFluid();	
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Will simulate a complete PAC cycle (Gaz/Heat Source and Distribution)
	 * 
	 * @param GazInjected in :  _COMPRESSOR,_CONDENSER, _EXPANSIONVALVE or _EVAPORATOR
	 * 				The : HeatTransferFluid will always be injected in the circulatuer !! 
	 */
	public void PacCycle(int GazInject) {

		// Cycle Gaz
		if (GazInject == Misc._COMPRESSOR) {
			fluidRefri = compressor.transfer(fluidRefri);
			fluidRefri = condenser.transfer(fluidRefri);
			fluidRefri = dehydrator.transfer(fluidRefri);
			fluidRefri = expansionValve.transfer(fluidRefri);
			fluidRefri = evaporator.transfer(fluidRefri);
		} else if (GazInject == Misc._CONDENSER) {
			fluidRefri = condenser.transfer(fluidRefri);
			fluidRefri = dehydrator.transfer(fluidRefri);
			fluidRefri = expansionValve.transfer(fluidRefri);
			fluidRefri = evaporator.transfer(fluidRefri);
			fluidRefri = compressor.transfer(fluidRefri);
		} else if (GazInject == Misc._EXPANSIONVALVE) {
			fluidRefri = expansionValve.transfer(fluidRefri);
			fluidRefri = evaporator.transfer(fluidRefri);
			fluidRefri = compressor.transfer(fluidRefri);
			fluidRefri = condenser.transfer(fluidRefri);
			fluidRefri = dehydrator.transfer(fluidRefri);
		} else {
			fluidRefri = evaporator.transfer(fluidRefri);
			fluidRefri = compressor.transfer(fluidRefri);
			fluidRefri = condenser.transfer(fluidRefri);
			fluidRefri = dehydrator.transfer(fluidRefri);
			fluidRefri = expansionValve.transfer(fluidRefri);	
		}	

		// Cycle Heat Source (Home)
		fluidCaloS = circulatorS.transfer(fluidCaloS);
		fluidCaloS = circuitS.transfer(fluidCaloS);

		// Cycle Heat Distribution (Captage)
		fluidCaloD = circulatorD.transfer(fluidCaloD);
		fluidCaloD = circuitD.transfer(fluidCaloD);	
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

		JSONObject jsonObjCompressor = new JSONObject();
		jsonObjCompressor = compressor.getJsonObject();
		JSONObject jsonObjCondenser = new JSONObject();
		jsonObjCondenser = condenser.getJsonObject();
		JSONObject jsonObjDehydrator = new JSONObject();
		jsonObjDehydrator = dehydrator.getJsonObject();
		JSONObject jsonObjEvaporator = new JSONObject();
		jsonObjEvaporator = evaporator.getJsonObject();
		JSONObject jsonObjExpansionValve = new JSONObject();
		jsonObjExpansionValve = expansionValve.getJsonObject();
		JSONObject jsonObjfluidRefri = new JSONObject();
		jsonObjfluidRefri = fluidRefri.getJsonObject();
		JSONObject jsonObjcirculatorS = new JSONObject();
		jsonObjcirculatorS = circulatorS.getJsonObject();
		JSONObject jsonObjcircuitS = new JSONObject();
		jsonObjcircuitS = circuitS.getJsonObject();
		JSONObject jsonObjfluidCaloS = new JSONObject();
		jsonObjfluidCaloS = fluidCaloS.getJsonObject();
		JSONObject jsonObjcirculatorD = new JSONObject();
		jsonObjcirculatorD = circulatorD.getJsonObject();
		JSONObject jsonObjcircuitD = new JSONObject();
		jsonObjcircuitD = circuitD.getJsonObject();
		JSONObject jsonObjfluidCaloD = new JSONObject();
		jsonObjfluidCaloD = fluidCaloD.getJsonObject();

		jsonObj.put("Compressor", jsonObjCompressor);
		jsonObj.put("Condenser", jsonObjCondenser);
		jsonObj.put("Dehydrator", jsonObjDehydrator);
		jsonObj.put("Evaporator", jsonObjEvaporator);
		jsonObj.put("ExpansionValve", jsonObjExpansionValve);
		jsonObj.put("FluidRefri", jsonObjfluidRefri);

		jsonObj.put("CirculatorS",jsonObjcirculatorS);
		jsonObj.put("CircuitS",jsonObjcircuitS);
		jsonObj.put("FluidCaloS",jsonObjfluidCaloS);

		jsonObj.put("CirculatorD",jsonObjcirculatorD);
		jsonObj.put("CircuitD",jsonObjcircuitD);
		jsonObj.put("FluidCaloD",jsonObjfluidCaloD);

		return jsonObj;
	}

	
	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		
		JSONObject jsonObjCompressor = (JSONObject) jsonObj.get("Compressor");
		this.compressor.setJsonObject(jsonObjCompressor);
		JSONObject jsonObjCondenser = (JSONObject) jsonObj.get("Condenser");
		this.condenser.setJsonObject(jsonObjCondenser);
		JSONObject jsonObjDehydrator = (JSONObject) jsonObj.get("Dehydrator");
		this.dehydrator.setJsonObject(jsonObjDehydrator);
		JSONObject jsonObjEvaporator = (JSONObject) jsonObj.get("Evaporator");
		this.evaporator.setJsonObject(jsonObjEvaporator);
		JSONObject jsonObjExpansionValve = (JSONObject) jsonObj.get("ExpansionValve");
		this.expansionValve.setJsonObject(jsonObjExpansionValve);
		JSONObject jsonObjFluidRefri = (JSONObject) jsonObj.get("FluidRefri");
		this.fluidRefri.setJsonObject(jsonObjFluidRefri);

		JSONObject jsonObjCirculatorS = (JSONObject) jsonObj.get("CirculatorS");
		this.circulatorS.setJsonObject(jsonObjCirculatorS);
		JSONObject jsonObjCircuitS = (JSONObject) jsonObj.get("CircuitS");
		this.circuitS.setJsonObject(jsonObjCircuitS);
		JSONObject jsonObjHeatTransferFluidS = (JSONObject) jsonObj.get("FluidCaloS");
		this.fluidCaloS.setJsonObject(jsonObjHeatTransferFluidS);
		
		JSONObject jsonObjCirculatorD = (JSONObject) jsonObj.get("CirculatorD");
		this.circulatorD.setJsonObject(jsonObjCirculatorD);
		JSONObject jsonObjCircuitD = (JSONObject) jsonObj.get("CircuitD");
		this.circuitD.setJsonObject(jsonObjCircuitD);
		JSONObject jsonObjHeatTransferFluidD = (JSONObject) jsonObj.get("FluidCaloD");
		this.fluidCaloD.setJsonObject(jsonObjHeatTransferFluidD);

	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------
	public Compressor getCompressor() {
		return compressor;
	}

	public void setCompressor(Compressor compressor) {
		this.compressor = compressor;
	}

	public Condenser getCondenser() {
		return condenser;
	}

	public void setCondenser(Condenser condenser) {
		this.condenser = condenser;
	}

	public ExpansionValve getExpansionValve() {
		return expansionValve;
	}

	public void setExpansionValve(ExpansionValve expansionValve) {
		this.expansionValve = expansionValve;
	}

	public Evaporator getEvaporator() {
		return evaporator;
	}

	public void setEvaporator(Evaporator evaporator) {
		this.evaporator = evaporator;
	}

	public Refrigerant getFluidRefri() {
		return fluidRefri;
	}

	public void setFluidRefri(Refrigerant fluidRefri) {
		this.fluidRefri = fluidRefri;
	}

	public Circulator getCirculatorS() {
		return circulatorS;
	}

	public void setCirculatorS(Circulator circulatorS) {
		this.circulatorS = circulatorS;
	}

	public HeatSrcDistrCircuit getCircuitS() {
		return circuitS;
	}

	public void setCircuitS(HeatSrcDistrCircuit circuitS) {
		this.circuitS = circuitS;
	}

	public HeatTransferFluid getFluidCaloS() {
		return fluidCaloS;
	}

	public void setFluidCaloS(HeatTransferFluid fluidCaloS) {
		this.fluidCaloS = fluidCaloS;
	}

	public Circulator getCirculatorD() {
		return circulatorD;
	}

	public void setCirculatorD(Circulator circulatorD) {
		this.circulatorD = circulatorD;
	}

	public HeatSrcDistrCircuit getCircuitD() {
		return circuitD;
	}

	public void setCircuitD(HeatSrcDistrCircuit circuitD) {
		this.circuitD = circuitD;
	}

	public HeatTransferFluid getFluidCaloD() {
		return fluidCaloD;
	}

	public void setFluidCaloD(HeatTransferFluid fluidCaloD) {
		this.fluidCaloD = fluidCaloD;
	}

	public Dehydrator getDehydrator() {
		return dehydrator;
	}

	public void setDehydrator(Dehydrator dehydrator) {
		this.dehydrator = dehydrator;
	}

}
