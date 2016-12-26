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
	 * @param GazInjected in :  _COMPRESSOR,_CONDENSER,
	 * 							_EXPANSIONVALVE or _EVAPORATOR
	 * 				The : HeatTransferFluid will always be injected in the circulatuer !! 
	 */
	public void PacCycle(int GazInject) {

		// Cycle Gaz
		if (GazInject == Misc._COMPRESSOR) {
			fluidRefri = compressor.transfer(fluidRefri);
			fluidRefri = condenser.transfer(fluidRefri);
			fluidRefri = expansionValve.transfer(fluidRefri);
			fluidRefri = evaporator.transfer(fluidRefri);
		} else if (GazInject == Misc._CONDENSER) {
			fluidRefri = condenser.transfer(fluidRefri);
			fluidRefri = expansionValve.transfer(fluidRefri);
			fluidRefri = evaporator.transfer(fluidRefri);
			fluidRefri = compressor.transfer(fluidRefri);
		} else if (GazInject == Misc._EXPANSIONVALVE) {
			fluidRefri = expansionValve.transfer(fluidRefri);
			fluidRefri = evaporator.transfer(fluidRefri);
			fluidRefri = compressor.transfer(fluidRefri);
			fluidRefri = condenser.transfer(fluidRefri);
		} else {
			fluidRefri = evaporator.transfer(fluidRefri);
			fluidRefri = compressor.transfer(fluidRefri);
			fluidRefri = condenser.transfer(fluidRefri);
			fluidRefri = expansionValve.transfer(fluidRefri);	
		}	

		// Cycle Heat Source (Home)
		fluidCaloS = circulatorS.transfer(fluidCaloS);
		fluidCaloS = circuitS.transfer(fluidCaloS);

		// Cycle Heat Distribution (Captage)
		fluidCaloD = circulatorD.transfer(fluidCaloD);
		fluidCaloD = circuitD.transfer(fluidCaloD);	
	}


	/**
	 * Set Class with the element coming from a the JSON object
	 * @param jsonObj : JSON Object
	 * 			
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.compressor = (Compressor) jsonObj.get("Compressor");
		this.condenser = (Condenser) jsonObj.get("Condenser");
		this.evaporator = (Evaporator) jsonObj.get("Evaporator");
		this.expansionValve = (ExpansionValve) jsonObj.get("ExpansionValve");
		this.fluidRefri = (Refrigerant) jsonObj.get("fluidRefri");

		this.circulatorS = (Circulator) jsonObj.get("circulatorS");
		this.circuitS = (HeatSrcDistrCircuit) jsonObj.get("circuitS");
		this.fluidCaloS = (HeatTransferFluid) jsonObj.get("fluidCaloS");

		this.circulatorD = (Circulator) jsonObj.get("circulatorD");
		this.circuitD = (HeatSrcDistrCircuit) jsonObj.get("circuitD");
		this.fluidCaloD = (HeatTransferFluid) jsonObj.get("fluidCaloD");
	}

	/**
	 * Return (after to have fill the class), the Circulator's JSON data
	 * @return : JSONObject
	 *      {"Features":[{"Current":0.91,"Power":190},{"Current".....
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getJsonObject() {
		JSONObject obj = new JSONObject();

		JSONObject jsonObjCompressor = new JSONObject();
		jsonObjCompressor = compressor.getJsonObject();
		JSONObject jsonObjCondenser = new JSONObject();
		jsonObjCondenser = condenser.getJsonObject();
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

		obj.put("Compressor", jsonObjCompressor);
		obj.put("Condenser", jsonObjCondenser);
		obj.put("Evaporator", jsonObjEvaporator);
		obj.put("ExpansionValve", jsonObjExpansionValve);
		obj.put("FluidRefri", jsonObjfluidRefri);

		obj.put("CirculatorS",jsonObjcirculatorS);
		obj.put("CircuitS",jsonObjcircuitS);
		obj.put("FluidCaloS",jsonObjfluidCaloS);

		obj.put("CirculatorD",jsonObjcirculatorD);
		obj.put("CircuitD",jsonObjcircuitD);
		obj.put("FluidCaloD",jsonObjfluidCaloD);

		return obj;
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



}
