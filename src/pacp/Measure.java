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

import org.json.simple.JSONObject;

public class Measure extends Enthalpy {

	private MeasurePoint measurePoint;		// The Measure Point.	
	private double value;					// Can be Pressure, Temperature,..
	private boolean chosen;					// Indicate if the Measure point has to be considered 
	private double P;						// Pressure
	private double T;						// Pressure
	private double H;						// Enthalpy
	private double P0PK;					// Corresponds to P0 or PK Pressure

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Measure(MeasurePoint vmeasurePointE) {
		super();								// --> Calls Enthalpy constructor
		this.measurePoint = vmeasurePointE;
		this.value = 0.0;
		this.chosen = false;
		this.P = 0.0;
		this.T = 0.0;
		this.H = 0.0;
		this.P0PK = 0.0;
	}

	// -------------------------------------------------------
	// 							TEST
	// -------------------------------------------------------

	public static void main(String[] args) {

		List<Measure> measurePL1 = new ArrayList<Measure>(); 
		for (MeasurePoint p : MeasurePoint.values())
			measurePL1.add(new Measure(p));

		Measure ma = measurePL1.get(0);

		if (ma.getMeasurePoint().equals(MeasurePoint.T1)) {
			System.out.println(ma.getMeasurePoint());				// = T1 from MeasurePoint.T1 (First element of array)
			System.out.println(ma.getMeasurePoint().toString());	// = "T1"
			System.out.println(ma.getMeasurePoint().getDefinition());
		}

		Measure mb = measurePL1.get(MeasurePoint.T2.ordinal());
		mb.setValue(12);
		System.out.println(mb.getMeasurePoint());
		System.out.println(mb.getValue() + " " + mb.getMeasurePoint().getUnity());
		System.out.println(mb.getMP() + " bars");

		Measure mc = measurePL1.get(MeasurePoint._P0);
		System.out.println(mc.getMeasurePoint());
		System.out.println(mc.getValue());
		System.out.println(mc.getMP());
		
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------




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
		jsonObj.put("MeasurePoint", this.measurePoint);
		jsonObj.put("Value", this.value);	
		jsonObj.put("Chosen", this.chosen);	
		jsonObj.put("P", this.P);	
		jsonObj.put("T", this.T);	
		jsonObj.put("H", this.H);	
		return jsonObj ;
	}

	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.measurePoint = (MeasurePoint) jsonObj.get("MeasurePoint");
		this.value = ((Number) jsonObj.get("Value")).doubleValue();
		this.chosen = ((Boolean) jsonObj.get("Chosen")).booleanValue();
		this.P = ((Number) jsonObj.get("P")).intValue();
		this.T = ((Number) jsonObj.get("T")).intValue();
		this.H = ((Number) jsonObj.get("H")).intValue();
	}


	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public MeasurePoint getMeasurePoint() {
		return measurePoint;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean isChosen() {
		return chosen;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

	public double getMP() {
		return P;
	}

	public void setMP(double p) {
		P = p;
	}

	public double getMT() {
		return T;
	}	

	public void setMT(double t) {
		T = t;
	}
	
	public double getMH() {
		return H;
	}	

	public void setMH(double h) {
		H = h;
	}

	public double getMP0PK() {
		return P0PK;
	}

	public void setMP0PK(double p0pk) {
		P0PK = p0pk;
	}
	
}
