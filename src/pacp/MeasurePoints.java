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
	private int group; 
	

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public MeasurePoints(String name, int x, int y,String definition,double value, String unity, int group) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.definition = definition;
		this.value = value;
		this.unity = unity;
		this.group = group;	//HP or BP
	}
	
	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	// -------------------------------------------------------
	// 							JSON
	// -------------------------------------------------------

	/**
	 * Return the JSON data
	 * @return : JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getJsonObject() {
		JSONObject ObjComp = new JSONObject();  
		ObjComp.put("Name", this.name);
		ObjComp.put("X", this.x);	
		ObjComp.put("Y", this.y);	
		ObjComp.put("Definition", this.definition);	
		ObjComp.put("Value", this.value);	
		ObjComp.put("Unity", this.unity);	
		ObjComp.put("Group", this.group);	
		return ObjComp ;
	}
	
	/**
	 * Set Class with the element coming from a the JSON object
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.name = (String) jsonObj.get("Name");
		this.x = (int) jsonObj.get("X");
		this.y = (int) jsonObj.get("Y");
		this.definition = (String) jsonObj.get("Definition");
		this.value = (double) jsonObj.get("Value");
		this.unity = (String) jsonObj.get("Unity");
		this.group = (int) jsonObj.get("Group");
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

	public int getGroup() {
		return group;
	}

}
