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

import org.json.simple.JSONObject;

import computation.Misc;

public class Compressor {
	private String name;
	private double evap;
	private double cond;
	private double rg;
	private double liq;
	private double capacity;
	private double power;
	private double current;
	private double massFlow;
	private double voltage;

	private double deltaP;	
	private double deltaT;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public Compressor() {
		this.name = "ZR40K3-PFG";
		this.evap =  Math.round(Misc.farenheit2degre(45)*100.0)/100.0;
		this.cond = Math.round(Misc.farenheit2degre(130)*100.0)/100.0;
		this.rg = Math.round(Misc.farenheit2degre(65)*100.0)/100.0;
		this.liq = Math.round(Misc.farenheit2degre(115)*100.0)/100.0;
		this.capacity = Math.round(Misc.btuhr2watt(33300)*100.0)/100.0;
		this.power = 3000;
		this.current = 14.7;
		this.massFlow = Math.round(Misc.pound2kg(488)*1000.0)/1000.0;		
		this.voltage = 220;	
		this.deltaT = 0;
		this.deltaP = 0;
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Compressor will increase the Pressure and Temperature
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
		jsonObj.put("Evap", this.evap);	
		jsonObj.put("Cond", this.cond);	
		jsonObj.put("RG", this.rg);	
		jsonObj.put("Liq", this.liq);	
		jsonObj.put("Capacity", this.capacity);	
		jsonObj.put("Power", this.power);	
		jsonObj.put("Current", this.current);	
		jsonObj.put("MassFlow", this.massFlow);	
		jsonObj.put("Voltage", this.voltage);	
		return jsonObj ;
	}

	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.name = (String) jsonObj.get("Name");
		this.evap = ((Number) jsonObj.get("Evap")).doubleValue();
		this.cond = ((Number) jsonObj.get("Cond")).doubleValue();
		this.rg = ((Number) jsonObj.get("RG")).doubleValue();
		this.liq = ((Number) jsonObj.get("Liq")).doubleValue();
		this.capacity = ((Number) jsonObj.get("Capacity")).doubleValue();
		this.power = ((Number) jsonObj.get("Power")).doubleValue();
		this.current = ((Number) jsonObj.get("Current")).doubleValue();
		this.massFlow = ((Number) jsonObj.get("MassFlow")).doubleValue();
		this.voltage = ((Number) jsonObj.get("Voltage")).doubleValue();
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public void setName(String v) {
		this.name = v;
	}

	public void setEvap(double v) {
		this.evap = v;
	}

	public void setCond(double v) {
		this.cond = v;
	}

	public void setRG(double v) {
		this.rg = v;
	}

	public void setLiq(double v) {
		this.liq = v;
	}

	public void setCapacity(double v) {
		this.capacity = v;
	}

	public void setPower(double v) {
		this.power = v;
	}

	public void setCurrent(double v) {
		this.current = v;
	}

	public void setMassFlow(double v) {
		this.massFlow = v;
	}

	public void setVoltage(double v) {
		this.voltage = v;
	}

	public String getName() {
		return name;
	}

	public double getEvap() {
		return evap;		
	}

	public double getCond() {
		return cond;		
	}

	public double getRG() {
		return rg;		
	}

	public double getLiq() {
		return liq;		
	}

	public double getCapacity() {
		return capacity;		
	}

	public double getPower() {
		return power;		
	}

	public double getCurrent() {
		return current;		
	}

	public double getMassFlow() {
		return massFlow;		
	}

	public double getVoltage() {
		return voltage;		
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

	public double getOverheated() {
		double tmp = rg - evap;
		return tmp;
	}
	
	public double getUnderCooling() {
		double tmp = cond - liq;
		return tmp;
	}
	
	
	
}
