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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;

public class EnthalpyBkgdImg {
	
	// Enthalpy image file (.png)
	private String enthalpyImageFile;		

	// Reference points chosen on the Curve
	private int refCurveH1x; 
	private int refCurveH2x; 
	private int refCurveP1y;	 
	private int refCurveP2y;     

	// Zone to consider from the Background Image related to the points above
	// Outside of the zone nothing --> be large !! 
	private int iBgH1x;
	private int iBgH2x;
	private int iBgP1y;
	private int iBgP2y;
		
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public EnthalpyBkgdImg() {
		
		// Image
		setEnthalpyImageFile("D:/Users/kluges1/workspace/pac-tool/ressources/R22/R22 couleur A4.png");

		// Reference points chosen on the Curve
		this.refCurveH1x = 140; 
		this.refCurveH2x = 520; 
		this.refCurveP1y = 1;	//Log(1) 
		this.refCurveP2y = 100;    //Log(100) 

		// Zone to consider from the Background Image related to the points above
		// Outside of the zone nothing --> be large !! 
		this.iBgH1x = 153;
		this.iBgH2x = 2791;
		this.iBgP1y = 1472;
		this.iBgP2y = 83;
	}
	
	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	/**
	 * Load the EnthalpyImageFile
	 */
	public BufferedImage openEnthalpyImageFile() {
		BufferedImage image=null;
		try {
			File file = new File(getEnthalpyImageFile());
			image = ImageIO.read(file);	
		} catch (IOException e) {
			System.out.println("Image non trouvée !");
			e.printStackTrace(); 
		}
		return image;
	}
	
	/**
	 * Return the JSON data
	 * @return : JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getJsonObject() {
		JSONObject ObjComp = new JSONObject();  
		ObjComp.put("EnthalpyImageFile", this.enthalpyImageFile);
		ObjComp.put("refCurveH1x", this.refCurveH1x);	
		ObjComp.put("refCurveH2x", this.refCurveH2x);	
		ObjComp.put("refCurveP1y", this.refCurveP1y);	
		ObjComp.put("refCurveP2y", this.refCurveP2y);	
		ObjComp.put("iBgH1x", this.iBgH1x);	
		ObjComp.put("iBgH2x", this.iBgH2x);	
		ObjComp.put("iBgP1y", this.iBgP1y);	
		ObjComp.put("iBgP2y", this.iBgP2y);	
		return ObjComp ;
	}
	
	/**
	 * Set Class with the element coming from a the JSON object
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.enthalpyImageFile = (String) jsonObj.get("enthalpyImageFile");
		this.refCurveH1x = (int) jsonObj.get("refCurveH1x");
		this.refCurveH2x = (int) jsonObj.get("refCurveH2x");
		this.refCurveP1y = (int) jsonObj.get("refCurveP1y");
		this.refCurveP2y = (int) jsonObj.get("refCurveP2y");
		this.iBgH1x = (int) jsonObj.get("iBgH1x");
		this.iBgH2x = (int) jsonObj.get("iBgH2x");
		this.iBgP1y = (int) jsonObj.get("iBgP1y");
		this.iBgP2y = (int) jsonObj.get("iBgP2y");
	}

	
	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public int getRefCurveH1x() {
		return refCurveH1x;
	}

	public void setRefCurveH1x(int refCurveH1x) {
		this.refCurveH1x = refCurveH1x;
	}

	public int getRefCurveH2x() {
		return refCurveH2x;
	}

	public void setRefCurveH2x(int refCurveH2x) {
		this.refCurveH2x = refCurveH2x;
	}

	public int getRefCurveP1y() {
		return refCurveP1y;
	}

	public int getRefCurveP1yLog() {
		return (int)Math.log10(refCurveP1y);
	}

	public void setRefCurveP1y(int refCurveP1y) {
		this.refCurveP1y = refCurveP1y;
	}

	public int getRefCurveP2y() {
		return refCurveP2y;
	}

	public int getRefCurveP2yLog() {
		return (int)Math.log10(refCurveP2y);
	}

	public void setRefCurveP2y(int refCurveP2y) {
		this.refCurveP2y = refCurveP2y;
	}

	public int getiBgH1x() {
		return iBgH1x;
	}

	public void setiBgH1x(int iBgH1x) {
		this.iBgH1x = iBgH1x;
	}

	public int getiBgH2x() {
		return iBgH2x;
	}

	public void setiBgH2x(int iBgH2x) {
		this.iBgH2x = iBgH2x;
	}

	public int getiBgP1y() {
		return iBgP1y;
	}

	public void setiBgP1y(int iBgP1y) {
		this.iBgP1y = iBgP1y;
	}

	public int getiBgP2y() {
		return iBgP2y;
	}

	public void setiBgP2y(int iBgP2y) {
		this.iBgP2y = iBgP2y;
	}

	public String getEnthalpyImageFile() {
		return enthalpyImageFile;
	}

	public void setEnthalpyImageFile(String enthalpyImageFile) {
		this.enthalpyImageFile = enthalpyImageFile;
	}

}