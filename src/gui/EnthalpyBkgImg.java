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
package gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

public class EnthalpyBkgImg {
	
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());
	
	private String imgRfgFile;			// Refrigerant image file (.png)		

	// Reference points chosen for H and P
	private double dstH1; 
	private double dstH2; 
	private double dstP2;	 
	private double dstP1;     

	// Zone to consider from the Background Image related to the points above
	// Outside of the zone nothing --> be large !! 
	private int srcx1;
	private int srcx2;
	private int srcy2;
	private int srcy1;
		
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public EnthalpyBkgImg(String fileImgBg) {
		
		// Image
		this.imgRfgFile = fileImgBg; // = "./ressources/R22/R22 couleur A4.png";

		// Reference points chosen on the Curve 
		// (Reference Up Corner Left)
		this.dstH1 = 140; 	// 	H = 140 kJ/kg
		this.dstH2 = 520; 	// 	H = 520	kJ/kg
		this.dstP1 = 0.5;   //	P = 0.5	bar
		this.dstP2 = 50;	//	P = 50 	bar	

		// Zone to consider from the Background Image related to the points above
		// Open with Paint !
		// Outside of the zone nothing --> be large !!
		// (Reference Up Corner Left)
		this.srcx1 = 153;
		this.srcx2 = 2791;
		this.srcy1 = 289;
		this.srcy2 = 1678;
	    
	}
	
	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public void loadNewBkgImg(String fileImgBg) {
		this.imgRfgFile = fileImgBg; // = "./ressources/R22/R22 couleur A4.png";
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
		jsonObj.put("RefrigerantImageFile", this.imgRfgFile);
		jsonObj.put("dstH1", this.dstH1);	
		jsonObj.put("dstH2", this.dstH2);	
		jsonObj.put("dstP1", this.dstP1);	
		jsonObj.put("dstP2", this.dstP2);	
		jsonObj.put("srcx1", this.srcx1);	
		jsonObj.put("srcx2", this.srcx2);	
		jsonObj.put("srcy1", this.srcy1);	
		jsonObj.put("srcy2", this.srcy2);	
		return jsonObj ;
	}
	
	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.imgRfgFile = (String) jsonObj.get("RefrigerantImageFile");
		
		this.dstH1 = ((Number) jsonObj.get("dstH1")).intValue() ;
		this.dstH2 = ((Number) jsonObj.get("dstH2")).intValue() ;
		this.dstP2 = ((Number) jsonObj.get("dstP1")).intValue() ;
		this.dstP1 = ((Number) jsonObj.get("dstP2")).intValue() ;
		this.srcx1 = ((Number) jsonObj.get("srcx1")).intValue() ;
		this.srcx2 = ((Number) jsonObj.get("srcx2")).intValue() ;
		this.srcy2 = ((Number) jsonObj.get("srcy1")).intValue() ;
		this.srcy1 = ((Number) jsonObj.get("srcy2")).intValue() ;
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public Double getDstH1() {
		return dstH1;
	}

	public void setDstH1(Double H1) {
		this.dstH1 = H1;
	}

	public Double getDstH2() {
		return dstH2;
	}

	public void setDstH2(Double H2) {
		this.dstH2 = H2;
	}

	public Double getDstP1() {
		return dstP1;
	}

	public void setDstP1(Double P1) {
		this.dstP1 = P1;
	}

	public Double getDstP2() {
		return dstP2;
	}

	public void setDstP2(Double P2) {
		this.dstP2 = P2;
	}


	public int getSrcx1() {
		return srcx1;
	}

	public void setSrcx1(int x1) {
		this.srcx1 = x1;
	}

	public int getSrcx2() {
		return srcx2;
	}

	public void setSrcx2(int X2) {
		this.srcx2 = X2;
	}

	public int getSrcy1() {
		return srcy1;
	}

	public void setSrcy1(int y1) {
		this.srcy1 = y1;
	}

	public int getSrcy2() {
		return srcy2;
	}

	public void setSrcy2(int y2) {
		this.srcy2 = y2;
	}


	public void setRefrigerantImageFile(String imgRfgFile) {
		this.imgRfgFile = imgRfgFile;
		logger.info("(EnthalpyBkgImg):: setRefrigerantImageFile :: imgRfgFile = {}", imgRfgFile);
	}

	public String getRefrigerantImageFile() {
		logger.info("(EnthalpyBkgImg):: getRefrigerantImageFile :: imgRfgFile = {}", imgRfgFile);
		return imgRfgFile;
	}

}