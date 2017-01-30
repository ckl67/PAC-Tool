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
package pacp;

import org.json.simple.JSONObject;

public class MeasurePoints {
	
	/* ----------------------------------------
	 *  	STATIC GLOBAL VAR PUBLIC
	 * ---------------------------------------- */	
	private String name;
	private int x;
	private int y;
	private String definition;
	private double value;
	private String unity;
	private int groupHpBp; 
	private boolean chosen;
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public MeasurePoints(String name, int x, int y,String definition, String unity, int groupHpBp) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.definition = definition;
		this.value = 0;
		this.unity = unity;
		this.groupHpBp = groupHpBp;	//HP or BP or Heat Source or Distribution Source
		this.chosen = false;
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
		jsonObj.put("Name", this.name);
		jsonObj.put("X", this.x);	
		jsonObj.put("Y", this.y);	
		jsonObj.put("Definition", this.definition);	
		jsonObj.put("Value", this.value);	
		jsonObj.put("Unity", this.unity);	
		jsonObj.put("GroupHpOrBp", this.groupHpBp);	
		return jsonObj ;
	}
	
	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.name = (String) jsonObj.get("Name");
		this.x = ((Number) jsonObj.get("X")).intValue();
		this.y = ((Number) jsonObj.get("Y")).intValue();
		this.definition = (String) jsonObj.get("Definition");
		this.value = ((Number) jsonObj.get("Value")).doubleValue();
		this.unity = (String) jsonObj.get("Unity");
		this.groupHpBp = ((Number) jsonObj.get("GroupHpOrBp")).intValue();
	}

	
	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------
	
	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getDefinition() {
		return definition;
	}
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getUnity() {
		return unity;
	}

	public void setUnity(String unity) {
		this.unity = unity;
	}

	public int getGroupHpBp() {
		return groupHpBp;
	}

	public boolean isChosen() {
		return chosen;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

}
