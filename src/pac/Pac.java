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

import coolant.HeatTransferFluid;
import refrigerant.Refrigerant;

public class Pac {

	private static final Logger logger = LogManager.getLogger(Pac.class.getName());

	// --------------------------------------------------------------------
	// Define
	// --------------------------------------------------------------------
	private final int _COMP_=0;
	private final int _COND_=1;
	private final int _DEHY_=2;
	private final int _EPVA_=3;
	private final int _EVAP_=4;
	private final int _CRCLS_=5;
	private final int _CIRTS_=6;
	private final int _CRCLD_=7;
	private final int _CIRTD_=8;

	// --------------------------------------------------------------------
	// Instance variables
	// --------------------------------------------------------------------
	private List<Circulator> circulatorSrcL = new ArrayList<Circulator>();
	private List<Circulator> circulatorDistrL = new ArrayList<Circulator>();
	private List<Compressor> compressorL = new ArrayList<Compressor>();
	private List<Condenser> condenserL = new ArrayList<Condenser>();
	private List<Dehydrator> dehydratorL = new ArrayList<Dehydrator>();
	private List<Evaporator> evaporatorL = new ArrayList<Evaporator>();
	private List<ExpansionValve> expansionValveL = new ArrayList<ExpansionValve>();
	private List<HeatSrcDistrCircuit> circuitSrcL = new ArrayList<HeatSrcDistrCircuit>();
	private List<HeatSrcDistrCircuit> circuitDistrL = new ArrayList<HeatSrcDistrCircuit>();
	
	private HeatTransferFluid coolantHome = new HeatTransferFluid();
	private HeatTransferFluid coolantYard = new HeatTransferFluid();

	private Refrigerant rfg = new Refrigerant();

	private int[] id = new int[9];

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Pac() {
		
		compressorL.add(new Compressor());
		condenserL.add(new Condenser());
		dehydratorL.add(new Dehydrator());
		expansionValveL.add(new ExpansionValve());
		evaporatorL.add(new Evaporator());

		circulatorSrcL.add(new Circulator());
		circuitSrcL.add(new HeatSrcDistrCircuit());

		circulatorDistrL.add(new Circulator());
		circuitDistrL.add(new HeatSrcDistrCircuit());

		id[_COMP_]=0;
		id[_COND_]=0;
		id[_DEHY_]=0;
		id[_EPVA_]=0;
		id[_EVAP_]=0;
		id[_CRCLS_]=0;
		id[_CIRTS_]=0;
		id[_CRCLD_]=0;
		id[_CIRTD_]=0;

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
	 *          id[0] = 2 <==> id[_COMP_] = 2;
	 */
	public void PacCycle(PacGasInjected GasInjected) {

		// Cycle Gas
		// compressorL.get(5)
		// id[COMP] = 5
		// compressorL.get(id[COMP])

		
		switch (GasInjected) {
		
		
		case COMPRESSOR : 
			rfg = compressorL.get(id[_COMP_]).transfer(rfg);
			rfg = condenserL.get(id[_COND_]).transfer(rfg);
			rfg = dehydratorL.get(id[_DEHY_]).transfer(rfg);
			rfg = expansionValveL.get(id[_EPVA_]).transfer(rfg);
			rfg = evaporatorL.get(id[_EVAP_]).transfer(rfg);
			break;
		case CONDENSER : 
			rfg = condenserL.get(id[_COND_]).transfer(rfg);
			rfg = dehydratorL.get(id[_DEHY_]).transfer(rfg);
			rfg = expansionValveL.get(id[_EPVA_]).transfer(rfg);
			rfg = evaporatorL.get(id[_EVAP_]).transfer(rfg);
			rfg = compressorL.get(id[_COMP_]).transfer(rfg);
			break;
		case EXPANSIONVALVE : 
			rfg = expansionValveL.get(id[_EPVA_]).transfer(rfg);
			rfg = evaporatorL.get(id[_EVAP_]).transfer(rfg);
			rfg = compressorL.get(id[_COMP_]).transfer(rfg);
			rfg = condenserL.get(id[_COND_]).transfer(rfg);
			rfg = dehydratorL.get(id[_DEHY_]).transfer(rfg);
			break;
		default:
			rfg = evaporatorL.get(id[_EVAP_]).transfer(rfg);
			rfg = compressorL.get(id[_COMP_]).transfer(rfg);
			rfg = condenserL.get(id[_COND_]).transfer(rfg);
			rfg = dehydratorL.get(id[_DEHY_]).transfer(rfg);
			rfg = expansionValveL.get(id[_EPVA_]).transfer(rfg);
			break;
		}
		// Cycle Heat Source 
		coolantYard=circulatorSrcL.get(id[_CRCLS_]).transfer(coolantYard);
		coolantYard=circuitSrcL.get(id[_CIRTS_]).transfer(coolantYard);

		// Cycle Heat Distribution
		coolantHome =circulatorDistrL.get(id[_CRCLD_]).transfer(coolantHome);
		coolantHome =circuitDistrL.get(id[_CIRTD_]).transfer(coolantHome);	
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

		JSONObject Obj = new JSONObject();  
		for (int i=0; i<id.length;i++) {
			Obj.put(i, id[i]);
		}
		jsonObj.put("ItemID", Obj);

		return jsonObj;
	}


	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {

		logger.info("jsonObj {}",jsonObj);
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

		JSONObject Obj = (JSONObject) jsonObj.get("ItemID");
		for(int i=0; i< ObjL.size();i++) {
			//JSONObject jsonObjectL = (JSONObject) ObjL.get(i);
			id[i]= (int) ((Number) Obj.get(i)).doubleValue();
		}


	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	/**
	 * selectCurrentCompressor
	 * @param i
	 */
	public void selectCurrentCompressor(int i) {
		id[_COMP_] = i;
		logger.info("Select Current Compressor N°{}",id[_COMP_]);
	}

	/**
	 * getCurrentCompressor
	 * @return Compressor
	 */
	// id[0] = 2 <==> id[_COMP_] = 2;
	public Compressor getCurrentCompressor() {
		logger.info("Get Current Compressor N°{}",id[_COMP_]);
		return compressorL.get(id[_COMP_]);
	}

	/**
	 * getCompressorNb
	 * @return Compressor
	 */
	//id[0] = 2 <==> id[_COMP_] = 2;
	public Compressor getCompressorNb(int nb) {
		logger.info("Get Compressor N°{}",nb);
		return compressorL.get(nb);
	}

	/**
	 * getNbOfCompressorNb
	 * @return
	 */
	public int getNbOfCompressorNb() {
		return compressorL.size();
	}

	/**
	 * addNewCompressor
	 * @param position
	 */
	public void addNewCompressor(int position) {
		logger.trace("Add New Compressor at position {} of a Total of {}",position,compressorL.size());
		compressorL.add(position, new Compressor());	
	}

	/**
	 * removeCompressor
	 * @param position
	 */
	public void removeCompressor(int position) {
		logger.trace("Remove Compressor at position = {} of a Total of {}",position,compressorL.size());
		compressorL.remove(position);	
	}

	// -------------------------------------------------------
		
	/**
	 * selectCurrentCirculatorSrc
	 * @param i
	 */
	public void selectCurrentCirculatorSrc(int i) {
		id[_CRCLS_] = i;
		logger.info("Select Current CirculatorSrc N°{}",id[_CRCLS_]);
	}

	
	/**
	 * getCurrentCirculatorSrc
	 * @return CirculatorSrc
	 */
	// id[0] = 2 <==> id[_CRCLS_] = 2;
	public Circulator getCurrentCirculatorSrc() {
		logger.info("Get Current CirculatorSrc N°{}",id[_CRCLS_]);
		return circulatorSrcL.get(id[_CRCLS_]);
	}
	
	/**
	 * getCirculatorSrcNb
	 * @return CirculatorSrc
	 */
	//id[0] = 2 <==> id[_COMP_] = 2;
	public Circulator getCirculatorSrcNb(int nb) {
		logger.info("Get CirculatorSrc N°{}",nb);
		return circulatorSrcL.get(nb);
	}
	
	/**
	 * getNbOfCirculatorSrcNb
	 * @return
	 */
	public int getNbOfCirculatorSrcNb() {
		return circulatorSrcL.size();
	}

	/**
	 * addNewCirculatorSrc
	 * @param position
	 */
	public void addNewCirculatorSrc(int position) {
		logger.trace("Add New CirculatorSrc at position {} of a Total of {}",position,circulatorSrcL.size());
		circulatorSrcL.add(position, new Circulator("New",220));	
	}

	/**
	 * removeCirculatorSrc
	 * @param position
	 */
	public void removeCirculatorSrc(int position) {
		logger.trace("Remove CirculatorSrc position ={} of a Total of {}",position,circulatorSrcL.size());
		circulatorSrcL.remove(position);	
	}
	

	// -------------------------------------------------------

	// -------------------------------------------------------
	
	/**
	 * selectCurrentCirculatorDistr
	 * @param i
	 */
	public void selectCurrentCirculatorDistr(int i) {
		id[_CRCLS_] = i;
		logger.info("Select Current CirculatorDistr N°{}",id[_CRCLS_]);
	}

	
	/**
	 * getCurrentCirculatorDistr
	 * @return CirculatorDistr
	 */
	// id[0] = 2 <==> id[_CRCLS_] = 2;
	public Circulator getCurrentCirculatorDistr() {
		logger.info("Get Current CirculatorDistr N°{}",id[_CRCLS_]);
		return circulatorDistrL.get(id[_CRCLS_]);
	}
	
	/**
	 * getCirculatorDistrNb
	 * @return CirculatorDistr
	 */
	//id[0] = 2 <==> id[_COMP_] = 2;
	public Circulator getCirculatorDistrNb(int nb) {
		logger.info("Get CirculatorDistr N°{}",nb);
		return circulatorDistrL.get(nb);
	}
	
	/**
	 * getNbOfCirculatorDistrNb
	 * @return
	 */
	public int getNbOfCirculatorDistrNb() {
		return circulatorDistrL.size();
	}

	/**
	 * addNewCirculatorDistr
	 * @param position
	 */
	public void addNewCirculatorDistr(int position) {
		logger.trace("Add New CirculatorDistr at position {} of a Total of {}",position,circulatorDistrL.size());
		circulatorDistrL.add(position, new Circulator("New",220));	
	}

	/**
	 * removeCirculatorDistr
	 * @param position
	 */
	public void removeCirculatorDistr(int position) {
		logger.trace("Remove CirculatorDistr position ={} of a Total of {}",position,circulatorDistrL.size());
		circulatorDistrL.remove(position);	
	}
	

	
	public void selectCurrentCondenser(int i) {
		id[_COND_] = i;
		logger.info("Choice Condenser N°{}",i);
	}

	public void selectCurrenteDehydrator(int i) {
		id[_DEHY_] = i;
		logger.info("Choice Dehydrator N°{}",i);
	}

	public void selectCurrenteExpansionValve(int i) {
		id[_EPVA_] = i;
		logger.info("Choice Expansion Valve N°{}",i);
	}

	public void selectCurrenteEvaporator(int i) {
		id[_EVAP_] = i;
		logger.info("Choice Evaporator N°{}",i);
	}


	public void selectCurrenteCircuitSrc(int i) {
		id[_CIRTS_] = i;
		logger.info("Choice Circuit Source N°{}",i);
	}

	public void selectCurrenteCirculatorDistr(int i) {
		id[_CRCLD_] = i;
		logger.info("Choice Circulator Distribution N°{}",i);
	}

	public void selectCurrenteCircuitDistr(int i) {
		id[_CIRTD_] = i;
		logger.info("Choice Circuit Distribution N°{}",i);
	}


	// -------------------------------------------------------

	public int[] getPacComponentId() {
		
		for (int i=0; i<id.length;i++) {
			logger.info(" [{}] = {}",i,id[i]);
		}
		return id;
	}


	public Condenser getCurrentCondenser() {
		return condenserL.get(id[_COND_]);
	}

	public Dehydrator getCurrentDehydrator() {
		return dehydratorL.get(id[_DEHY_]);
	}

	public ExpansionValve getCurrentExpansionValve() {
		return expansionValveL.get(id[_EPVA_]);
	}

	public Evaporator getCurrentEvaporator() {
		return evaporatorL.get(id[_EVAP_]);
	}

	public HeatSrcDistrCircuit getCurrentCircuitSrc() {
		return circuitSrcL.get(id[_CIRTS_]);
	}

	public HeatSrcDistrCircuit getCurrentCircuitDistr() {
		return circuitDistrL.get(id[_CIRTD_]);
	}

	// ----------------------------------------------------------------------
	
	public Refrigerant getCurrentFluidRefri() {
		return rfg;
	}

	public HeatTransferFluid getCurrentFluidCaloSrc() {
		return coolantYard;
	}

	public HeatTransferFluid getCurrentFluidCaloDistr() {
		return coolantHome;
	}

	
}
