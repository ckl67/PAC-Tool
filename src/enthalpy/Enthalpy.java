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
package enthalpy;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONObject;

import computation.Misc;

public class Enthalpy {

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------

	/* ----------------
	 * Diagram Enthalpy  
	 * ----------------*/
	private String nameRefrigerant;			// Name (R22/..)
	private double xHmin;  					//  Enthalpy Minimum of the range of values displayed.
	private double xHmax;    				//  Enthalpy Maximum of the range of value displayed.

	private double yPmin;  					// Pressure Minimum of the range of Pressure value
	private double yPmax;     				// Pressure Maximum of the range of Pressure value. 

	/* -----------------------------
	   Diagram Pressure-Temperature
	 * ----------------------------*/
	private String fileNameTP;						// Data file with : Temperature / Pressure relation
	private List<Point2D.Double> listTP;			// Coordinate Temperature / Pressure : List
	private double deltaP;							// Delta pressure absolute / relative

	/* -----------------------------
	   Diagram Saturation Curve:
	   Temperature --> Enthalpy  (Liquid / Vapor)  
	 * ----------------------------*/
	private String fileNameSAT;
	private List<Point2D.Double> listSatHlP;			// Coordinate Temperature / H : List
	private List<Point2D.Double> listSatHvP;			// Coordinate Temperature / H : List
		
	private double angleHmatchPSatWithP0PK;				// Angle approximation for H Matching PSat with P0PK

	/* -----------------------------
	   Enthalpy Image
	 * ----------------------------*/
	private EnthalpyBkgdImg enthalpyBkgdImg;

	// -------------------------------------------------------
	//	LOCAL VARIABLES
	// -------------------------------------------------------
	
	private double hSatMin = 1000;					// H Saturation Zone [hSatMin - hSatMax] --> Computed by reading the file !
	private double hSatMax = 0;						// H Saturation Zone [hSatMin - hSatMax] --> Computed by reading the file !
	private double hSatErrLoc = 0;					// H saturation Error Location. --> Computed by reading the file !
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	/**
	 * Constructor will instance by default the R22 Enthalpy object
	 * All instances variables can be modified afterwards in case 
	 * another Refrigerant is used.
	 */
	public Enthalpy() {
		/* ----------------
		 * Diagram Enthalpy  
		 * ----------------*/
		this.nameRefrigerant = "R22";
		this.xHmin = 140;  				
		this.xHmax = 520;    				

		this.yPmin = 0.5;  
		this.yPmax = 60;    

		/* -----------------------------
		   Diagram Pressure-Temperature
		 * ----------------------------*/
		this.fileNameTP = "D:/Users/kluges1/workspace/pac-tool/ressources/R22/P2T_R22.txt";
		this.listTP = new ArrayList<Point2D.Double>();
		this.deltaP = 0.0;
		this.loadPTFile();

		/* -----------------------------
		   Diagram Saturation Curve:
		   Temperature --> Enthalpy  (Liquid / Vapor)  
		   		let h the value to search in the list
				let h0,h1 the nearest H found in the list, 
				if h0 and h1 are to faraway of h, it means that the p zone was too narrow 
		 * ----------------------------*/
		this.fileNameSAT = "D:/Users/kluges1/workspace/pac-tool/ressources/R22/SaturationCurve_R22.txt";
		this.listSatHlP = new ArrayList<Point2D.Double>();
		this.listSatHvP = new ArrayList<Point2D.Double>();
		this.loadHlvPSatFile();

		this.angleHmatchPSatWithP0PK = -30; 	// In degree
		
		/* -----------------------------
		   Enthalpy Image
		 * ----------------------------*/
		this.enthalpyBkgdImg = new EnthalpyBkgdImg();
		
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Read Data file containing: Pressure /Temperature relation (P. relative) 
	 * Will fill the : listTP (T,P) list
	 */
	public void loadPTFile() {
		File file = new File (fileNameTP);

		Scanner sken = null;
		try {
			sken = new Scanner (file);
		} catch (FileNotFoundException e) {
			System.out.println("loadPTFile");
			System.out.println(e.getMessage());
			System.out.println(e.toString());
			e.printStackTrace();
		}

		while (sken.hasNext () ){
			String first = sken.nextLine ();
			if (!first.startsWith("#") ) {
				String[] val = first.split ("\t");
				double temp = Double.parseDouble(val [0].replace(",", "."));
				double press = Double.parseDouble(val [1].replace(",", "."));

				listTP.add(new Point2D.Double(temp, press));
			}
		}
	}

	// -------------------------------------------------------
	/**
	 * Read Data file containing: 
	 * Temperature [degree C] / Enthalpy (kJ/kg) Liquid / Enthalpy (kJ/kg) Vapor
	 * Will fill the : setlistSatHlP (H,P) / setlistSatHvP (H,P) list
	 */
	public void loadHlvPSatFile() {
		File file = new File (fileNameSAT);
		Scanner sken = null;
		try {
			sken = new Scanner (file);
		} catch (FileNotFoundException e) {
			System.out.println("loadHlvPSatFile");
			System.out.println(e.getMessage());
			System.out.println(e.toString());
			e.printStackTrace();
		}

		while (sken.hasNext () ){
			String first = sken.nextLine ();
			if (!first.startsWith("#") ) {
				String[] val = first.split ("\t");
				double temp = Double.parseDouble(val [0].replace(",", "."));
				double enthL = Double.parseDouble(val [1].replace(",", "."));
				double enthV = Double.parseDouble(val [2].replace(",", "."));

				listSatHlP.add(new Point2D.Double(enthL,convT2P(temp)));
				listSatHvP.add(new Point2D.Double(enthV,convT2P(temp)));

				//	if ( Math.abs(enthL-hSatErrLoc>))
				if (enthL < hSatMin )
					hSatMin = enthL;
				if (enthV < hSatMin )
					hSatMin = enthV;
				if (enthL > hSatMax )
					hSatMax = enthL; 
				if (enthV > hSatMax )
					hSatMax = enthV; 
			}
		}
		List<Double> linL = new ArrayList<Double>();
		for(int i=0;i<listSatHlP.size();i++)
			linL.add(getSatHl(i));
		List<Double> linV = new ArrayList<Double>();
		for(int i=0;i<listSatHlP.size();i++)
			linV.add(getSatHv(i));

		hSatErrLoc = Misc.maxIntervalL(linL);
		if (hSatErrLoc < Misc.maxIntervalL(linV))
			hSatErrLoc = Misc.maxIntervalL(linV);
		// Security margin linked to the fact that at the limit of the list, 
		// the chosen element can be at n-1 position (due to end of list)
		hSatErrLoc = hSatErrLoc*2;
	}

	// -------------------------------------------------------

	/**
	 * Match Pressure to H vapor Saturation curve
	 * Correspond to intersection between P and Saturation H vapor
	 * @param pressure
	 * @return
	 */
	public double  matchP2HvaporSat(double press ) {
		double ho=0;
		double min = Double.MAX_VALUE;
		int id=0;
		if (listSatHvP.size() != 0) {
			for(int n = 0; n < listSatHvP.size(); n++){
				double diff = Math.abs(getSatP(n) - press);
				if (diff < min) {
					min = diff;
					id = n;
				}
			}
			//System.out.println(press + " " + getSatP(id) + " " + getSatHv(id));
			if (id == listSatHvP.size()-1) {
				id = id-1;
			} 

			double x,y0,y1,x0,x1;
			x  = press;
			x0 = getSatP(id);
			x1 = getSatP(id+1);
			y0 = getSatHv(id);
			y1 = getSatHv(id+1);
			ho = (x-x0)*(y1-y0)/(x1-x0)+ y0;
		}
		return ho;
	}

	// -------------------------------------------------------

	/**
	 * Match Pressure to H Liquid Saturation curve
	 * Correspond to intersection between P and Saturation H Liquid
	 * @param pressure
	 * @return
	 */
	public double  matchP2HliquidSat(double press ) {
		double ho=0;
		double min = Double.MAX_VALUE;
		int id=0;
		if (listSatHlP.size() != 0) {
			for(int n = 0; n < listSatHlP.size(); n++){
				double diff = Math.abs(getSatP(n) - press);
				if (diff < min) {
					min = diff;
					id = n;
				}
			}
			//System.out.println(press + " " + getSatP(id) + " " + getSatHv(id));
			if (id == listSatHlP.size()-1) {
				id = id-1;
			} 

			double x,y0,y1,x0,x1;
			x  = press;
			x0 = getSatP(id);
			x1 = getSatP(id+1);
			y0 = getSatHl(id);
			y1 = getSatHl(id+1);
			ho = (x-x0)*(y1-y0)/(x1-x0)+ y0;
		}
		return ho;
	}

	/**
	 * Compute H, Matching PSat (T-Isotherm) with P0PK 
	 * In all the cases the result must be positive !!
	 * If not: Error to address and result = Hsat !!
	 * @param Hsatv
	 * @param Psat
	 * @param P0PK = P0 or PK
	 * @return H0HK
	 */
	public double CompHmatchPSatWithP0PK(double Hsat, double Psat, double P0PK) {
		double H0HK;
		
		if (Psat > P0PK) {
			H0HK = Hsat + (Psat-P0PK) / Math.abs(Math.tan(angleHmatchPSatWithP0PK*Math.PI/180));
		}
		else {
			H0HK = Hsat;
			System.out.println("Error CompHmatchPSatWithP0PK");
		}

		System.out.println("Psat= " + Psat);
		System.out.println("P0PK= " + P0PK);
		System.out.println("Hsat= " + Hsat);
		System.out.println("H0HK=" + H0HK);

		return H0HK;
	}

	// -------------------------------------------------------

	/**
	 * Convert Temperature(°C) to Pressure (bar) 
	 * @param temp: temperature (°C)
	 * @return : Pressure (bar absolute)
	 */
	public double convT2P(double temp){
		double presso=0;
		double min = Double.MAX_VALUE;
		int id=0;
		if (listTP.size() != 0) {
			for(int n = 0; n < listTP.size(); n++){
				double diff = Math.abs(getT(n) - temp);
				if (diff < min) {
					min = diff;
					id = n;
				}
			}
			if (id == listTP.size()-1) {
				id = id-1;
			} 

			double x,y0,y1,x0,x1;
			x  = temp;
			x0 = getT(id);
			x1 = getT(id+1);
			y0 = getP(id);
			y1 = getP(id+1);
			presso = (x-x0)*(y1-y0)/(x1-x0)+ y0;
		}
		return presso+deltaP;
	}

	// -------------------------------------------------------

	/**
	 * Convert Pressure (bar) to Temperature(°C)  
	 * @param : Pressure (absolute) press
	 * @return : Temperature (°C)
	 */
	public double convP2T(double press){
		double tempo=0;
		double min = Double.MAX_VALUE;
		int id=0;
		double pressi = press - deltaP; 
		if (listTP.size() != 0) {
			for(int n = 0; n < listTP.size(); n++){
				double diff = Math.abs(getP(n) - pressi);
				if (diff < min) {
					min = diff;
					id = n;
				}
			}
			if (id == listTP.size()-1) {
				id = id-1;
			} 

			double x,y0,y1,x0,x1;
			x  = pressi;
			x0 = getP(id);
			x1 = getP(id+1);
			y0 = getT(id);
			y1 = getT(id+1);
			tempo = (x-x0)*(y1-y0)/(x1-x0)+ y0;
		}
		return tempo;
	}

	// -------------------------------------------------------
	/**
	 * Return T from List listTP
	 * @param id
	 * @return T
	 */
	public double getT(int id) {
		return listTP.get(id).getX();
	}

	// -------------------------------------------------------
	/**
	 * Return P from List listTP
	 * @param id
	 * @return P
	 */
	public double getP(int id) {
		return listTP.get(id).getY();
	}

	// -------------------------------------------------------
	/**
	 * Return      P  from List listSatHlP
	 *  	 	   P  from List listSatHvP
	 *  It is the same value as for both ! 
	 * @param id 
	 * @return P
	 */
	public double getSatP(int id) {
		return listSatHlP.get(id).getY();
	}

	// -------------------------------------------------------
	/**
	 * Return Enthalpy Vapor from List listSatHlP
	 * @param id
	 * @return H
	 */
	public double getSatHv(int id) {
		return listSatHvP.get(id).getX();
	}

	// -------------------------------------------------------
	/**
	 * Return Enthalpy Liquid from List listSatHlP
	 * @param id
	 * @return H Liquid
	 */
	public double getSatHl(int id) {
		return listSatHlP.get(id).getX();
	}

	/**
	 * Conversion Saturation Enthalpy value to Pressure
	 *   The table H can decrease/increase , so we have to take into account supplementary parameters 
	 *     Zone of pressure
	 *     Zone of H
	 *   Zone of pressure is computed based on : 
	 *   	Current pressure=pNear and pDelta --> 
	 *   	Zone of pressure = pNear +- pDelta
	 *   Zone of H is computed based on  hSatErrLoc
	 * @param pNear
	 * @param pDelta
	 * @return pressure in bar !
	 */
	public double  convSatH2P(double h, double pNear ) {
		double po=-1000;
		double min = 1000;
		double diff;
		double pDelta;
		double pmin; 
		double pmax;
		int idlx=-1;
		int idvx=-1;
		double x = 0, y0 = 0,y1 = 0,x0 = 0,x1 = 0;

		if (pNear < 5) 
			pDelta = 2;
		else if ((pNear>=5) && (pNear<10) )
			pDelta = 5;
		else if ((pNear>=10) && (pNear<20) )
			pDelta = 7;
		else if ((pNear>=20) && (pNear<40) )
			pDelta = 10;
		else
			pDelta = 20;

		pmin = ((pNear-pDelta)>0) ? (pNear-pDelta): 0.0;
		pmax = (pNear+pDelta);

		//System.out.println("-------------------------------------------------------");	
		//System.out.println("      h="+h);	
		//System.out.println("     "+ pmin +" < " + "  pNear= "+pNear + " < "+ pmax );

		if ((h>= hSatMin) && (h <= hSatMax)) {

			for(int i=0;i<listSatHlP.size();i++) {
				if ((getSatP(i) < pmax) && (getSatP(i) > pmin)) {

					diff = Math.abs(getSatHl(i)-h);
					if (diff < min) {
						min = diff;
						idlx = i;
						idvx = -1;
					}

					diff = Math.abs(getSatHv(i)-h);
					if (diff < min) {
						min = diff;
						idvx = i;
						idlx = -1;
					}
				}
			}
			// Avoid to get outside the list
			if (idlx >= listSatHlP.size()-1) 
				idlx = idlx-1;
			if (idvx >= listSatHlP.size()-1) 
				idvx = idvx-1;
			//System.out.println("      idlx="+idlx+"   idvx="+idvx);

			// We check that the x0 and x1 are in the H Zone (Liquid) 
			if (idlx != -1) {
				x  = h;
				x0 = getSatHl(idlx);
				x1 = getSatHl(idlx+1);
				//System.out.println("     |h("+h+") - x0("+x0+")|="+ (Math.abs(h-x0)+ "<-->" + hSatErrLoc ));
				//System.out.println("     |h("+h+") - x1("+x1+")|="+ (Math.abs(h-x1)+ "<-->" + hSatErrLoc ));
				if ( (Math.abs(h-x0) > hSatErrLoc || Math.abs(h-x1) > hSatErrLoc ) ) {
					idlx = -1;
				}
			}
			// We check that the x0 and x1 are in the H Zone (Vapor) 
			if (idvx != -1) {
				x  = h;
				x0 = getSatHv(idvx);
				x1 = getSatHv(idvx+1);
				//System.out.println("     |h("+h+") - x0("+x0+")|="+ (Math.abs(h-x0)+ "<-->" + hSatErrLoc ));
				//System.out.println("     |h("+h+") - x1("+x1+")|="+ (Math.abs(h-x1)+ "<-->" + hSatErrLoc ));
				if ( (Math.abs(h-x0) > hSatErrLoc || Math.abs(h-x1) > hSatErrLoc ) ) {
					idvx = -1;
				}
			}
			//System.out.println("      H Zone Checked");
			//System.out.println("      idlx="+idlx+"   idvx="+idvx);
			if (idlx != -1) {
				// Zone Liquid
				//System.out.println("       Liquid");
				y0 = getSatP(idlx);
				y1 = getSatP(idlx+1);
				//System.out.println("     "+"h("+idlx+")="+x0 +" < " + h + " < "+"h("+idlx+"+1"+")="+ x1);
				//System.out.println("     "+"x="+x+"  x0="+x0+"  x1="+x1+"  y0="+y0+"  y1="+y1);
				po = (x-x0)*(y1-y0)/(x1-x0)+ y0;
				//System.out.println("     "+y0+" < " + po + " < "+ y1);
			} else 	if (idvx != -1) {
				// Zone Vapor
				//System.out.println("      Vapor");
				y0 = getSatP(idvx);
				y1 = getSatP(idvx+1);	
				//System.out.println("     "+"h("+idvx+")="+x0+" < " + h + " < "+"h("+idvx+"+1"+")="+ x1);
				//System.out.println("     "+"x="+x+"  x0="+x0+"  x1="+x1+"  y0="+y0+"  y1="+y1);
				po = (x-x0)*(y1-y0)/(x1-x0)+ y0;
				//System.out.println("     "+y0+" < " + po + " < "+ y1);
			} else {
				// Zone Vapor
				//System.out.println("      Error");
				//System.out.println("     "+po );
			}
		}
		return po;
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
		jsonObj.put("NameRefrigerant", this.nameRefrigerant);
		jsonObj.put("xHmin", this.xHmin);	
		jsonObj.put("xHmax", this.xHmax);	
		jsonObj.put("yPmin", this.yPmin);	
		jsonObj.put("yPmax", this.yPmax);	
		jsonObj.put("FileNameTP", this.fileNameTP);	
		jsonObj.put("DeltaP", this.deltaP);	
		jsonObj.put("FileNameSAT", this.fileNameSAT);	
		jsonObj.put("AngleHmatchPSatWithP0PK", this.angleHmatchPSatWithP0PK);	
		jsonObj.put("EnthalpyBkgdImg", this.enthalpyBkgdImg.getJsonObject());	
		return jsonObj ;
	}

	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.nameRefrigerant = (String) jsonObj.get("NameRefrigerant");
		this.xHmin = ((Number) jsonObj.get("xHmin")).doubleValue();
		this.xHmax = ((Number) jsonObj.get("xHmax")).doubleValue();
		this.yPmin = ((Number) jsonObj.get("yPmin")).doubleValue();
		this.yPmax = ((Number) jsonObj.get("yPmax")).doubleValue();
		this.fileNameTP = (String) jsonObj.get("FileNameTP");
		this.deltaP = ((Number) jsonObj.get("DeltaP")).doubleValue();
		this.fileNameSAT = (String) jsonObj.get("FileNameSAT");
		this.angleHmatchPSatWithP0PK = ((Number) jsonObj.get("AngleHmatchPSatWithP0PK")).doubleValue();

		JSONObject jsonObjEImg = (JSONObject) jsonObj.get("EnthalpyBkgdImg");
		this.enthalpyBkgdImg.setJsonObject(jsonObjEImg); 
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public String getFileNameTP() {
		return fileNameTP;
	}

	public void setFileNameTP(String fileNameTP) {
		this.fileNameTP = fileNameTP;
	}

	public List<Point2D.Double> getlistTP() {
		return listTP;
	}

	public void setlistTP(List<Point2D.Double> listTP) {
		this.listTP = listTP;
	}

	public String getFileNameSAT() {
		return fileNameSAT;
	}

	public void setFileNameSAT(String fileNameSAT) {
		this.fileNameSAT = fileNameSAT;
	}

	public List<Point2D.Double> getlistSatHlP() {
		return listSatHlP;
	}

	public void setlistSatHlP(List<Point2D.Double> listSatHlP) {
		this.listSatHlP = listSatHlP;
	}

	public List<Point2D.Double> getlistSatHvP() {
		return listSatHvP;
	}

	public void setlistSatHvP(List<Point2D.Double> listSatHvP) {
		this.listSatHvP = listSatHvP;
	}

	public String getNameRefrigerant() {
		return nameRefrigerant;
	}

	public void setNameRefrigerant(String nameRefrigerant) {
		this.nameRefrigerant = nameRefrigerant;
	}

	public EnthalpyBkgdImg getEnthalpyBkgImage() {
		return enthalpyBkgdImg;
	}

	public void setEnthalpyBkgImage(EnthalpyBkgdImg enthalpyBkgdImg) {
		this.enthalpyBkgdImg = enthalpyBkgdImg;
	}

	public double getxHmin() {
		return xHmin;
	}

	public void setxHmin(double xHmin) {
		this.xHmin = xHmin;
	}

	public double getxHmax() {
		return xHmax;
	}

	public void setxHmax(double xHmax) {
		this.xHmax = xHmax;
	}

	public double getyPmin() {
		return yPmin;
	}

	public void setyPmin(double yPmin) {
		this.yPmin = yPmin;
	}

	public double getyPmax() {
		return yPmax;
	}

	public void setyPmax(double yPmax) {
		this.yPmax = yPmax;
	}

	public double getDeltaP() {
		return deltaP;
	}

	public void setDeltaP(double deltaP) {
		this.deltaP = deltaP;
	}

}
