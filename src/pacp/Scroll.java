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

public class Scroll {
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
	
	public Scroll() {
			setName("ZR40K3-PFG");
			evap = 45;
			cond = 130;
			rg = 65;
			liq = 115;
			capacity = 33300;
			power = 3000;
			current = 14.7;
			massFlow = 488;		
			voltage = 220;		
	}
	
	// ========================================================================================
	/**
	 * Return the features of Class Scroll() in a JSON format
	 * @return : JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getJsonObject() {
		JSONObject ObjScroll = new JSONObject();  
		ObjScroll.put("Name", this.name);
		ObjScroll.put("Evap", this.evap);	
		ObjScroll.put("Cond", this.cond);	
		ObjScroll.put("RG", this.rg);	
		ObjScroll.put("Liq", this.liq);	
		ObjScroll.put("Capacity", this.capacity);	
		ObjScroll.put("Power", this.power);	
		ObjScroll.put("Current", this.current);	
		ObjScroll.put("MassFlow", this.massFlow);	
		ObjScroll.put("Voltage", this.voltage);	
		return(ObjScroll);
	}
	
	// ========================================================================================
	/**
	 * Set scroll with the element in the JSON object
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.name = (String) jsonObj.get("Name");
		this.evap = (double) jsonObj.get("Evap");
		this.cond = (double) jsonObj.get("Cond");
		this.rg = (double) jsonObj.get("RG");
		this.liq = (double) jsonObj.get("Liq");
		this.capacity = (double) jsonObj.get("Capacity");
		this.power = (double) jsonObj.get("Power");
		this.current = (double) jsonObj.get("Current");
		this.massFlow = (double) jsonObj.get("MassFlow");
		this.voltage = (double) jsonObj.get("Voltage");
	}
	
	
	// ----------------------------------------------------------------------------
	// Setter
	// ----------------------------------------------------------------------------
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
	
	// ----------------------------------------------------------------------------
	// Getter
	// ----------------------------------------------------------------------------
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


}
