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

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Pac {
	
	private static final Logger logger = LogManager.getLogger(Pac.class.getName());

	// --------------------------------------------------------------------
	// Instance variables
	// --------------------------------------------------------------------
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
	
	private int[] id = new int[12];


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
		
		id[PacItem.COMP.ordinal()]=0;
		id[PacItem.COND.ordinal()]=0;
		id[PacItem.DEHY.ordinal()]=0;
		id[PacItem.EPVA.ordinal()]=0;
		id[PacItem.EVAP.ordinal()]=0;
		id[PacItem.FLFRG.ordinal()]=0;
		id[PacItem.CRCLS.ordinal()]=0;
		id[PacItem.CIRTS.ordinal()]=0;
		id[PacItem.FLCAS.ordinal()]=0;
		id[PacItem.CRCLD.ordinal()]=0;
		id[PacItem.CIRTD.ordinal()]=0;
		id[PacItem.FLCAD.ordinal()]=0;
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Will simulate a complete PAC cycle (Gaz/Heat Source and Distribution)
	 * 
	 * @param GasInjected in :  COMPRESSOR, CONDENSER, EXPANSIONVALVE or EVAPORATOR
	 * 				The : HeatTransferFluid will always be injected in the circulator !!
	 * @id : Corresponds to the item element index to simulate:
	 * 			Example: In case we want to test with the compressor N°2 
	 *          COMP corresponds to position 0 in the array
	 *          id[0] = 2 <==> id[PacItem.COMP.pos()] = 2;
	 */
	public void PacCycle(PacGasInjected GasInjected) {

		// Cycle Gas
		// compressorL.get(5)
		// id[COMP] = 5
		// compressorL.get(id[COMP])
		
		switch (GasInjected) {
		case COMPRESSOR : 
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    compressorL.get(id[PacItem.COMP.nb()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    condenserL.get(id[PacItem.COND.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    dehydratorL.get(id[PacItem.DEHY.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    expansionValveL.get(id[PacItem.EPVA.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    evaporatorL.get(id[PacItem.EVAP.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			break;
		case CONDENSER : 
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    condenserL.get(id[PacItem.COND.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    dehydratorL.get(id[PacItem.DEHY.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    expansionValveL.get(id[PacItem.EPVA.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    evaporatorL.get(id[PacItem.EVAP.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    compressorL.get(id[PacItem.COMP.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			break;
		case EXPANSIONVALVE : 
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    expansionValveL.get(id[PacItem.EPVA.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    evaporatorL.get(id[PacItem.EVAP.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    compressorL.get(id[PacItem.COMP.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    condenserL.get(id[PacItem.COND.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    dehydratorL.get(id[PacItem.DEHY.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			break;
		default:
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    evaporatorL.get(id[PacItem.EVAP.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    compressorL.get(id[PacItem.COMP.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    condenserL.get(id[PacItem.COND.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    dehydratorL.get(id[PacItem.DEHY.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			fluidRefriL.set(id[PacItem.FLFRG.Pos()],    expansionValveL.get(id[PacItem.EPVA.Pos()]).transfer(fluidRefriL.get(id[PacItem.FLFRG.Pos()])));
			break;
		}
		// Cycle Heat Source 
		fluidCaloSrcL.set(id[PacItem.FLCAS.Pos()],    circulatorSrcL.get(id[PacItem.CRCLS.Pos()]).transfer(fluidCaloSrcL.get(id[PacItem.FLCAS.Pos()])));
		fluidCaloSrcL.set(id[PacItem.FLCAS.Pos()],    circuitSrcL.get(id[PacItem.CIRTS.Pos()]).transfer(fluidCaloSrcL.get(id[PacItem.FLCAS.Pos()])));

		// Cycle Heat Distribution
		fluidCaloDistrL.set(id[PacItem.FLCAD.Pos()],    circulatorDistrL.get(id[PacItem.CRCLD.Pos()]).transfer(fluidCaloDistrL.get(id[PacItem.FLCAD.Pos()])));
		fluidCaloDistrL.set(id[PacItem.FLCAD.Pos()],    circuitDistrL.get(id[PacItem.CIRTD.Pos()]).transfer(fluidCaloDistrL.get(id[PacItem.FLCAD.Pos()])));	
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

		JSONObject Obj = new JSONObject();  
		for (PacItem p : PacItem.values()) {
			Obj.put(p, id[p.ordinal()]);
		}
		jsonObj.put("ItemID", Obj);

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

		JSONObject Obj = (JSONObject) jsonObj.get("ItemID");
		for (PacItem p : PacItem.values()) {
			id[p.ordinal()] = (int) ((Number) Obj.get(p)).doubleValue();
		}

	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	/**
	 * 
	 * @param i
	 */
	public void selectCurrentCompressor(int i) {
		id[PacItem.COMP.ordinal()] = i;
		logger.info("Select Current Compressor N°{}",id[PacItem.COMP.ordinal()]);
	}

	/**
	 * 
	 * @return Compressor
	 */
	// id[0] = 2 <==> id[PacItem.COMP.pos()] = 2;
	public Compressor getCurrentCompressor() {
		logger.info("Get Current Compressor N°{}",id[PacItem.COMP.ordinal()]);
		return compressorL.get(id[PacItem.COMP.ordinal()]);
	}

	/**
	 * 
	 * @return Compressor
	 */
	//id[0] = 2 <==> id[PacItem.COMP.pos()] = 2;
	public Compressor getCompressorNb(int nb) {
		logger.info("Get Compressor N°{}",nb);
		return compressorL.get(nb);
	}

	public int getNbOfCompressorNb() {
		return compressorL.size();
	}
	
	public void addNewCompressor(int position) {
		compressorL.add(position, new Compressor());	
	}
	
	public void removeCompressor(int position) {
		compressorL.remove(position);	
	}
	
	// -------------------------------------------------------
	
	public void choosCondenser(int i) {
		id[PacItem.COND.ordinal()] = i;
		logger.info("Choice Condenser N°{}",i);
	}
	
	public void chooseDehydrator(int i) {
		id[PacItem.DEHY.ordinal()] = i;
		logger.info("Choice Dehydrator N°{}",i);
	}

	public void chooseExpansionValve(int i) {
		id[PacItem.EPVA.ordinal()] = i;
		logger.info("Choice Expansion Valve N°{}",i);
	}

	public void chooseEvaporator(int i) {
		id[PacItem.EVAP.ordinal()] = i;
		logger.info("Choice Evaporator N°{}",i);
	}

	public void chooseFluidRefri(int i) {
		id[PacItem.FLFRG.ordinal()] = i;
		logger.info("Choice Fluid Refrigerant N°{}",i);
	}

	public void chooseCirculatorSrc(int i) {
		id[PacItem.CRCLS.ordinal()] = i;
		logger.info("Choice Circulator Source N°{}",i);
	}

	public void chooseCircuitSrc(int i) {
		id[PacItem.CIRTS.ordinal()] = i;
		logger.info("Choice Circuit Source N°{}",i);
	}

	public void chooseFluidCaloSrc(int i) {
		id[PacItem.FLCAS.ordinal()] = i;
		logger.info("Choice Fluid Caloporter Source N°{}",i);
	}

	public void chooseCirculatorDistr(int i) {
		id[PacItem.CRCLD.ordinal()] = i;
		logger.info("Choice Circulator Distribution N°{}",i);
	}

	public void chooseCircuitDistr(int i) {
		id[PacItem.CIRTD.ordinal()] = i;
		logger.info("Choice Circuit Distribution N°{}",i);
	}

	public void chooseFluidCaloDistr(int i) {
		id[PacItem.FLCAD.ordinal()] = i;
		logger.info("Choice Fluid Caloporter Distribution N°{}",i);
	}

	// -------------------------------------------------------

	public int[] getPacComponentId() {
		for (PacItem p : PacItem.values()) {
			logger.info(" For: {}, Chosen id = {}",p,id[p.ordinal()]);
		}
		return id;
	}
	

	
	public Condenser getCurrentCondenser() {
		return condenserL.get(id[PacItem.COND.ordinal()]);
	}

	public Dehydrator getCurrentDehydrator() {
		return dehydratorL.get(id[PacItem.DEHY.ordinal()]);
	}

	public ExpansionValve getCurrentExpansionValve() {
		return expansionValveL.get(id[PacItem.EPVA.ordinal()]);
	}

	public Evaporator getCurrentEvaporator() {
		return evaporatorL.get(id[PacItem.EVAP.ordinal()]);
	}

	public Refrigerant getCurrentFluidRefri() {
		return fluidRefriL.get(id[PacItem.FLFRG.ordinal()]);
	}

	public Circulator getCurrentCirculatorSrc() {
		return circulatorSrcL.get(id[PacItem.CRCLS.ordinal()]);
	}

	public HeatSrcDistrCircuit getCurrentCircuitSrc() {
		return circuitSrcL.get(id[PacItem.CIRTS.ordinal()]);
	}

	public HeatTransferFluid getCurrentFluidCaloSrc() {
		return fluidCaloSrcL.get(id[PacItem.FLCAS.ordinal()]);
	}

	public Circulator getCurrentCirculatorDistr() {
		return circulatorDistrL.get(id[PacItem.CRCLD.ordinal()]);
	}

	public HeatSrcDistrCircuit getCurrentCircuitDistr() {
		return circuitDistrL.get(id[PacItem.CIRTD.ordinal()]);
	}

	public HeatTransferFluid getCurrentFluidCaloDistr() {
		return fluidCaloDistrL.get(id[PacItem.FLCAD.ordinal()]);
	}


}
