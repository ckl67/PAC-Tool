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
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Pac{
	private List<Compressor> compressorL = new ArrayList<Compressor>();
	private List<Condenser> condenserL = new ArrayList<Condenser>();
	private List<Dehydrator> dehydratorL = new ArrayList<Dehydrator>();
	private List<ExpansionValve> expansionValveL = new ArrayList<ExpansionValve>();
	private List<Evaporator> evaporatorL = new ArrayList<Evaporator>();
	private List<Refrigerant> fluidRefriL = new ArrayList<Refrigerant>();
	private List<Circulator> circulatorSrcL = new ArrayList<Circulator>();
	private List<HeatSrcDistrCircuit> circuitSrcL = new ArrayList<HeatSrcDistrCircuit>();
	private List<HeatTransferFluid> fluidCaloSrcL = new ArrayList<HeatTransferFluid>();
	private List<Circulator> circulatorDistrL = new ArrayList<Circulator>();
	private List<HeatSrcDistrCircuit> circuitDistrL = new ArrayList<HeatSrcDistrCircuit>();
	private List<HeatTransferFluid> fluidCaloDistrL = new ArrayList<HeatTransferFluid>();

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Pac() {
		compressorL.add(new Compressor());
		condenserL.add(new Condenser());
		dehydratorL.add(new Dehydrator());
		expansionValveL.add(new ExpansionValve());
		evaporatorL.add(new Evaporator());
		fluidRefriL.add(new Refrigerant());

		circulatorSrcL.add(new Circulator());
		circuitSrcL.add(new HeatSrcDistrCircuit());
		fluidCaloSrcL.add(new HeatTransferFluid());

		circulatorDistrL.add(new Circulator());
		circuitDistrL.add(new HeatSrcDistrCircuit());
		fluidCaloDistrL.add(new HeatTransferFluid());	
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Will simulate a complete PAC cycle (Gaz/Heat Source and Distribution)
	 * 
	 * @param GazInjected in :  _COMPRESSOR,_CONDENSER, _EXPANSIONVALVE or _EVAPORATOR
	 * 				The : HeatTransferFluid will always be injected in the circulator !!
	 * @itemFor : Correspond to the item element index to simulate:
	 * 			Example: In case we want to test with the N°2 compressor:  itemFor[Misc._COMP] = 2;
	 */
	public void PacCycle(int GazInject, int[] itemFor) {

		// Cycle Gaze
		if (GazInject == Misc._COMPRESSOR) {
			fluidRefriL.set(itemFor[Misc._FLFRG],compressorL.get(itemFor[Misc._COMP]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],condenserL.get(itemFor[Misc._COND]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],dehydratorL.get(itemFor[Misc._DEHY]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],expansionValveL.get(itemFor[Misc._EPVA]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],evaporatorL.get(itemFor[Misc._EVAP]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
		} else if (GazInject == Misc._CONDENSER) {
			fluidRefriL.set(itemFor[Misc._FLFRG],condenserL.get(itemFor[Misc._COND]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],dehydratorL.get(itemFor[Misc._DEHY]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],expansionValveL.get(itemFor[Misc._EPVA]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],evaporatorL.get(itemFor[Misc._EVAP]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],compressorL.get(itemFor[Misc._COMP]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
		} else if (GazInject == Misc._EXPANSIONVALVE) {
			fluidRefriL.set(itemFor[Misc._FLFRG],expansionValveL.get(itemFor[Misc._EPVA]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],evaporatorL.get(itemFor[Misc._EVAP]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],compressorL.get(itemFor[Misc._COMP]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],condenserL.get(itemFor[Misc._COND]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],dehydratorL.get(itemFor[Misc._DEHY]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
		} else {
			fluidRefriL.set(itemFor[Misc._FLFRG],evaporatorL.get(itemFor[Misc._EVAP]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],compressorL.get(itemFor[Misc._COMP]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],condenserL.get(itemFor[Misc._COND]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],dehydratorL.get(itemFor[Misc._DEHY]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));
			fluidRefriL.set(itemFor[Misc._FLFRG],expansionValveL.get(itemFor[Misc._EPVA]).transfer(fluidRefriL.get(itemFor[Misc._FLFRG])));	
		}	

		// Cycle Heat Source 
		fluidCaloSrcL.set(itemFor[Misc._FLCAS],circulatorSrcL.get(itemFor[Misc._CRCLS]).transfer(fluidCaloSrcL.get(itemFor[Misc._FLCAS])));
		fluidCaloSrcL.set(itemFor[Misc._FLCAS],circuitSrcL.get(itemFor[Misc._CIRTS]).transfer(fluidCaloSrcL.get(itemFor[Misc._FLCAS])));

		// Cycle Heat Distribution
		fluidCaloDistrL.set(itemFor[Misc._FLCAD],circulatorDistrL.get(itemFor[Misc._CRCLD]).transfer(fluidCaloDistrL.get(itemFor[Misc._FLCAD])));
		fluidCaloDistrL.set(itemFor[Misc._FLCAD],circuitDistrL.get(itemFor[Misc._CIRTD]).transfer(fluidCaloDistrL.get(itemFor[Misc._FLCAD])));	

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

		JSONArray ObjCompressorL = new JSONArray();
		for(int i=0; i< compressorL.size();i++) {
			JSONObject Obj = new JSONObject();  
			Obj.put("Compressor", this.compressorL.get(i).getJsonObject());
			ObjCompressorL.add(Obj);
		}
		jsonObj.put("CompressorL", ObjCompressorL);

		JSONArray ObjCondenserL = new JSONArray();
		for(int i=0; i< condenserL.size();i++) {
			JSONObject Obj = new JSONObject();  
			Obj.put("Condenser", this.condenserL.get(i).getJsonObject());
			ObjCondenserL.add(Obj);
		}
		jsonObj.put("CondenserL", ObjCondenserL);
		
		JSONArray ObjDehydratorL = new JSONArray();
		for(int i=0; i< dehydratorL.size();i++) {
			JSONObject Obj = new JSONObject();  
			Obj.put("Dehydrator", this.dehydratorL.get(i).getJsonObject());
			ObjDehydratorL.add(Obj);
		}
		jsonObj.put("DehydratorL", ObjDehydratorL);

		JSONArray ObjEvaporatorL = new JSONArray();
		for(int i=0; i< evaporatorL.size();i++) {
			JSONObject Obj = new JSONObject();  
			Obj.put("Evaporator", this.evaporatorL.get(i).getJsonObject());
			ObjEvaporatorL.add(Obj);
		}
		jsonObj.put("EvaporatorL", ObjEvaporatorL);

		JSONArray ObjExpansionValveL = new JSONArray();
		for(int i=0; i< expansionValveL.size();i++) {
			JSONObject Obj = new JSONObject();  
			Obj.put("ExpansionValve", this.expansionValveL.get(i).getJsonObject());
			ObjExpansionValveL.add(Obj);
		}
		jsonObj.put("ExpansionValveL", ObjExpansionValveL);

		JSONArray ObjFluidRefriL = new JSONArray();
		for(int i=0; i< fluidRefriL.size();i++) {
			JSONObject Obj = new JSONObject();  
			Obj.put("FluidRefri", this.fluidRefriL.get(i).getJsonObject());
			ObjFluidRefriL.add(Obj);
		}
		jsonObj.put("FluidRefriL", ObjFluidRefriL);

		JSONArray ObjCirculatorSrcL = new JSONArray();
		for(int i=0; i< circulatorSrcL.size();i++) {
			JSONObject Obj = new JSONObject();  
			Obj.put("CirculatorSrc", this.circulatorSrcL.get(i).getJsonObject());
			ObjCirculatorSrcL.add(Obj);
		}
		jsonObj.put("CirculatorSrcL", ObjCirculatorSrcL);

		JSONArray ObjCircuitSrcL = new JSONArray();
		for(int i=0; i< circuitSrcL.size();i++) {
			JSONObject Obj = new JSONObject();  
			Obj.put("CircuitSrc", this.circuitSrcL.get(i).getJsonObject());
			ObjCircuitSrcL.add(Obj);
		}
		jsonObj.put("CircuitSrcL", ObjCircuitSrcL);

		JSONArray ObjFluidCaloSrcL = new JSONArray();
		for(int i=0; i< fluidCaloSrcL.size();i++) {
			JSONObject Obj = new JSONObject();  
			Obj.put("FluidCaloSrc", this.fluidCaloSrcL.get(i).getJsonObject());
			ObjFluidCaloSrcL.add(Obj);
		}
		jsonObj.put("FluidCaloSrcL", ObjFluidCaloSrcL);

		JSONArray ObjCirculatorDistrL = new JSONArray();
		for(int i=0; i< circulatorDistrL.size();i++) {
			JSONObject Obj = new JSONObject();  
			Obj.put("CirculatorDistr", this.circulatorDistrL.get(i).getJsonObject());
			ObjCirculatorDistrL.add(Obj);
		}
		jsonObj.put("CirculatorDistrL", ObjCirculatorDistrL);

		JSONArray ObjCircuitDistrL = new JSONArray();
		for(int i=0; i< circuitDistrL.size();i++) {
			JSONObject Obj = new JSONObject();  
			Obj.put("CircuitDistr", this.circuitDistrL.get(i).getJsonObject());
			ObjCircuitDistrL.add(Obj);
		}
		jsonObj.put("CircuitDistrL", ObjCircuitDistrL);

		JSONArray ObjFluidCaloDistrL = new JSONArray();
		for(int i=0; i< fluidCaloDistrL.size();i++) {
			JSONObject Obj = new JSONObject();  
			Obj.put("FluidCaloDistr", this.fluidCaloDistrL.get(i).getJsonObject());
			ObjFluidCaloDistrL.add(Obj);
		}
		jsonObj.put("FluidCaloDistrL", ObjFluidCaloDistrL);
		
		return jsonObj;
	}


	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {

		JSONArray ObjL;
		ObjL = (JSONArray) jsonObj.get("CompressorL");
		compressorL.clear();
		for(int i=0; i< ObjL.size();i++) {
			JSONObject jsonObjectL = (JSONObject) ObjL.get(i);
			compressorL.add(i, new Compressor());
			compressorL.get(i).setJsonObject((JSONObject)(jsonObjectL.get("Compressor")));
		}

		ObjL = (JSONArray) jsonObj.get("CondenserL");
		condenserL.clear();
		for(int i=0; i< ObjL.size();i++) {
			JSONObject jsonObjectL = (JSONObject) ObjL.get(i);
			condenserL.add(i, new Condenser());
			condenserL.get(i).setJsonObject((JSONObject)(jsonObjectL.get("Condenser")));
		}

		ObjL = (JSONArray) jsonObj.get("DehydratorL");
		dehydratorL.clear();
		for(int i=0; i< ObjL.size();i++) {
			JSONObject jsonObjectL = (JSONObject) ObjL.get(i);
			dehydratorL.add(i, new Dehydrator());
			dehydratorL.get(i).setJsonObject((JSONObject)(jsonObjectL.get("Dehydrator")));
		}

		ObjL = (JSONArray) jsonObj.get("EvaporatorL");
		evaporatorL.clear();
		for(int i=0; i< ObjL.size();i++) {
			JSONObject jsonObjectL = (JSONObject) ObjL.get(i);
			evaporatorL.add(i, new Evaporator());
			evaporatorL.get(i).setJsonObject((JSONObject)(jsonObjectL.get("Evaporator")));
		}

		ObjL = (JSONArray) jsonObj.get("ExpansionValveL");
		expansionValveL.clear();
		for(int i=0; i< ObjL.size();i++) {
			JSONObject jsonObjectL = (JSONObject) ObjL.get(i);
			expansionValveL.add(i, new ExpansionValve());
			expansionValveL.get(i).setJsonObject((JSONObject)(jsonObjectL.get("ExpansionValve")));
		}

		ObjL = (JSONArray) jsonObj.get("FluidRefriL");
		fluidRefriL.clear();
		for(int i=0; i< ObjL.size();i++) {
			JSONObject jsonObjectL = (JSONObject) ObjL.get(i);
			fluidRefriL.add(i, new Refrigerant());
			fluidRefriL.get(i).setJsonObject((JSONObject)(jsonObjectL.get("FluidRefri")));
		}

		ObjL = (JSONArray) jsonObj.get("CirculatorSrcL");
		circulatorSrcL.clear();
		for(int i=0; i< ObjL.size();i++) {
			JSONObject jsonObjectL = (JSONObject) ObjL.get(i);
			circulatorSrcL.add(i, new Circulator());
			circulatorSrcL.get(i).setJsonObject((JSONObject)(jsonObjectL.get("CirculatorSrc")));
		}

		ObjL = (JSONArray) jsonObj.get("CircuitSrcL");
		circuitSrcL.clear();
		for(int i=0; i< ObjL.size();i++) {
			JSONObject jsonObjectL = (JSONObject) ObjL.get(i);
			circuitSrcL.add(i, new HeatSrcDistrCircuit());
			circuitSrcL.get(i).setJsonObject((JSONObject)(jsonObjectL.get("CircuitSrc")));
		}

		ObjL = (JSONArray) jsonObj.get("FluidCaloSrcL");
		fluidCaloSrcL.clear();
		for(int i=0; i< ObjL.size();i++) {
			JSONObject jsonObjectL = (JSONObject) ObjL.get(i);
			fluidCaloSrcL.add(i, new HeatTransferFluid());
			fluidCaloSrcL.get(i).setJsonObject((JSONObject)(jsonObjectL.get("FluidCaloSrc")));
		}

		ObjL = (JSONArray) jsonObj.get("CirculatorDistrL");
		circulatorDistrL.clear();
		for(int i=0; i< ObjL.size();i++) {
			JSONObject jsonObjectL = (JSONObject) ObjL.get(i);
			circulatorDistrL.add(i, new Circulator());
			circulatorDistrL.get(i).setJsonObject((JSONObject)(jsonObjectL.get("CirculatorDistr")));
		}

		ObjL = (JSONArray) jsonObj.get("CircuitDistrL");
		circuitDistrL.clear();
		for(int i=0; i< ObjL.size();i++) {
			JSONObject jsonObjectL = (JSONObject) ObjL.get(i);
			circuitDistrL.add(i, new HeatSrcDistrCircuit());
			circuitDistrL.get(i).setJsonObject((JSONObject)(jsonObjectL.get("CircuitDistr")));
		}

		ObjL = (JSONArray) jsonObj.get("FluidCaloDistrL");
		fluidCaloDistrL.clear();
		for(int i=0; i< ObjL.size();i++) {
			JSONObject jsonObjectL = (JSONObject) ObjL.get(i);
			fluidCaloDistrL.add(i, new HeatTransferFluid());
			fluidCaloDistrL.get(i).setJsonObject((JSONObject)(jsonObjectL.get("FluidCaloDistr")));
		}

	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------


	public List<Compressor> getCompressorL() {
		return compressorL;
	}

	public List<Condenser> getCondenserL() {
		return condenserL;
	}


	public List<Dehydrator> getDehydratorL() {
		return dehydratorL;
	}

	public List<ExpansionValve> getExpansionValveL() {
		return expansionValveL;
	}

	public List<Evaporator> getEvaporatorL() {
		return evaporatorL;
	}

	public List<Refrigerant> getFluidRefriL() {
		return fluidRefriL;
	}

	public List<Circulator> getCirculatorSrcL() {
		return circulatorSrcL;
	}

	public List<HeatSrcDistrCircuit> getCircuitSrcL() {
		return circuitSrcL;
	}

	public List<HeatTransferFluid> getFluidCaloSrcL() {
		return fluidCaloSrcL;
	}

	public List<Circulator> getCirculatorDistrL() {
		return circulatorDistrL;
	}

	public List<HeatSrcDistrCircuit> getCircuitDistrL() {
		return circuitDistrL;
	}

	public List<HeatTransferFluid> getFluidCaloDistrL() {
		return fluidCaloDistrL;
	}

}
