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

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Enthalpy {

	/* ----------------
	 * Diagram Enthalpy  
	 * ----------------*/
	private String nameRefrigerant;									// Name (R22/..)

	/* -----------------------------
	   Diagram Pressure-Temperature
	 * ----------------------------*/
	private String fileTP;							// Data file with : Temperature / Pressure relation
	private List<Point2D.Double> listTP;				// Coordinate Temperature / Pressure : List
	private double deltaP;								// Delta pressure absolute / relative

	/* -----------------------------
	   Diagram Saturation Curve:
	   Temperature --> Enthalpy  (Liquid / Vapor)  
	 * ----------------------------*/
	private double hSatMin,hSatMax;						// H Saturation Zone [hSatMin - hSatMax] --> Computed by reading the file !
	private double hSatErrLoc;							// H saturation Error Location. --> Computed by reading the file !
	// let h the value to search in the list
	// let h0,h1 the nearest H found in the list, 
	// if h0 and h1 are to faraway of h, it means that the p zone was too narrow 
	private String fileSat;
	private List<Point2D.Double> listSatHlP;			// Coordinate Temperature / H : List
	private List<Point2D.Double> listSatHvP;			// Coordinate Temperature / H : List

	/* -----------------------------
	   Enthalpy Image
	 * ----------------------------*/
	private EnthalpyBkgdImg enthalpyBkgdImg;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Enthalpy() {
		/* ----------------
		 * Diagram Enthalpy  
		 * ----------------*/
		// Name Refrigerant
		setNameRefrigerant("R22");

		/* -----------------------------
		   Diagram Pressure-Temperature
		 * ----------------------------*/
		setFileTP("D:/Users/kluges1/workspace/pac-tool/ressources/R22/P2T_R22.txt");
		setlistTP(new ArrayList<Point2D.Double>());
		deltaP = 0.0;

		/* -----------------------------
		   Diagram Saturation Curve:
		   Temperature --> Enthalpy  (Liquid / Vapor)  
		 * ----------------------------*/
		hSatMin=1000;
		hSatMax=0;
		hSatErrLoc = 0;
		setFileSat("D:/Users/kluges1/workspace/pac-tool/ressources/R22/SaturationCurve_R22.txt");
		setlistSatHlP(new ArrayList<Point2D.Double>());
		setlistSatHvP(new ArrayList<Point2D.Double>());

		/* -----------------------------
		   Enthalpy Image
		 * ----------------------------*/
		enthalpyBkgdImg = new EnthalpyBkgdImg();

	}


	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	/**
	 * Read Data file containing: 
	 * # Temperature [degre C] / Enthalpy (kJ/kg) Liquid / Enthalpy (kJ/kg) Vapor
	 * Will fill the : setlistSatHlP / setlistSatHvP list
	 */
	public void loadSatFile() {
		File file = new File (fileSat);
		Scanner sken = null;
		try {
			sken = new Scanner (file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
		// Security margin linked to the fact that at the limit of the list, the chosen element can be at n-1 position (due to end of list)
		hSatErrLoc = hSatErrLoc*2;
		//System.out.println(hSatErrLoc);
	}


	/**
	 * Read Data file containing: Pressure /Temperature relation (P. relative) 
	 * Will fill the : listTP list
	 */
	public void loadPTFile() {
		File file = new File (fileTP);

		Scanner sken = null;
		try {
			sken = new Scanner (file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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

	/**
	 * Conversion Saturation Enthalpy value to Pressure
	 *   The table H can decrease/increase , so we have to take into account supplementary parameters 
	 *     Zone of pressure
	 *     Zone of H
	 *   Zone of pressure is computed based on : Current pressure=pNear and pDelta --> Zone of pressure = pNear +- pDelta
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

	/**
	 * Convert Temperature(°C) to Pressure (bar) 
	 * @param temp: temperature (°C)
	 * @return : Pressure (bar absolute)
	 */
	public double convT2P(double temp){
		double x,presso=0;
		int idx=0;
		if (listTP.size() != 0) {
			for(int c = 0; c < listTP.size(); c++){
				if(temp >= getT(c) ){
					idx = c;
				}
			}
			if (idx == listTP.size()-1) {
				idx = idx-1;
			} 

			double y0,y1,x0,x1;
			x  = temp;
			x0 = getT(idx);
			x1 = getT(idx+1);
			y0 = getP(idx);
			y1 = getP(idx+1);
			presso = (x-x0)*(y1-y0)/(x1-x0)+ y0;
		}
		return presso+deltaP;
	}

	/**
	 * Convert Pressure (bar) to Temperature(°C)  
	 * @param : Pressure (absolute) press
	 * @return : Temperature (°C)
	 */
	public double convP2T(double press){
		double x,tempo=0;
		int idx=0;
		double pressi = press - deltaP; 
		if (listTP.size() != 0) {

			for(int c = 0; c < listTP.size(); c++){
				if(pressi >= getP(c) ){
					idx = c;
				}
			}
			if (idx == listTP.size()-1) {
				idx = idx-1;
			} 

			double y0,y1,x0,x1;
			x  = pressi;
			x0 = getP(idx);
			x1 = getP(idx+1);
			y0 = getT(idx);
			y1 = getT(idx+1);
			tempo = (x-x0)*(y1-y0)/(x1-x0)+ y0;
		}
		return tempo;
	}

	/**
	 * Return T from List listTP
	 * @param id
	 * @return
	 */
	public double getT(int id) {
		return listTP.get(id).getX();
	}

	/**
	 * Return P from List listTP
	 * @param id
	 * @return
	 */
	public double getP(int id) {
		return listTP.get(id).getY();
	}

	/**
	 * Return Enthalpy Liquid from List listSatHlP
	 * @param id
	 * @return
	 */
	public double getSatHl(int id) {
		return listSatHlP.get(id).getX();
	}
	/**
	 * Return      P  from List listSatHlP
	 *   same as   P  from List listSatHvP
	 * @param id
	 * @return
	 */
	public double getSatP(int id) {
		return listSatHlP.get(id).getY();
	}

	/**
	 * Return Enthalpy Vapor from List listSatHlP
	 * @param id
	 * @return
	 */
	public double getSatHv(int id) {
		return listSatHvP.get(id).getX();
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public String getFileTP() {
		return fileTP;
	}

	public void setFileTP(String fileTP) {
		this.fileTP = fileTP;
	}

	public List<Point2D.Double> getlistTP() {
		return listTP;
	}

	public void setlistTP(List<Point2D.Double> listTP) {
		this.listTP = listTP;
	}

	public String getFileSat() {
		return fileSat;
	}

	public void setFileSat(String fileSat) {
		this.fileSat = fileSat;
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

	public double gethSatMin() {
		return hSatMin;
	}

	public double gethSatMax() {
		return hSatMax;
	}
	
	public EnthalpyBkgdImg getEnthalpyImage() {
		return enthalpyBkgdImg;
	}

	public void setEnthalpyImage(EnthalpyBkgdImg enthalpyBkgdImg) {
		this.enthalpyBkgdImg = enthalpyBkgdImg;
	}

	
}
