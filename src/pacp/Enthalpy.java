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

import java.awt.Point;
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
	private String enthalpyImageFile;								// Enthalpy image file (.png)

	private Point2D.Double mOrigineH = new Point2D.Double();  		// Coordinate mouse (hOrigine,hFinal)
	private Point iOrigineH = new Point();						    // Coordinate Reference for (hOrigine,hFinal) in Enthalpy image
	private boolean locateOrigineH;									// Image Reference for H Requested :  Origin / Final
	private boolean locateFinalH;

	private Point2D.Double mOrigineP = new Point2D.Double();  		// Coordinate mouse (POrigine,PFinal)
	private Point iOrigineP = new Point();						    // Coordinate Reference for (POrigine,PFinal) Pressure in image
	private boolean locateOrigineP;									// Image Reference for P Requested :  Origin / Final
	private boolean locateFinalP;

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
	private String fileSat;
	private List<Point2D.Double> listSatHlP;			// Coordinate Temperature / H : List
	private List<Point2D.Double> listSatHvP;			// Coordinate Temperature / H : List

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Enthalpy() {
		/* ----------------
		 * Diagram Enthalpy  
		 * ----------------*/
		// Name Refrigerant
		setNameRefrigerant("R22");
		// Image
		setEnthalpyImageFile("D:/Users/kluges1/workspace/pac-tool/ressources/R22.png");

		// -- H
		setiHOrigine(140);
		setiHFinal(240);
		setmHOrigine(85.0);
		setmHFinal(570.0);
		locateOrigineH=false;
		locateFinalH=false;

		// -- P
		setiPOrigine(1);
		setiPFinal(10);
		setmPOrigine(1000.0);
		setmPFinal(516.0);
		locateOrigineP=false;
		locateFinalP=false;

		/* -----------------------------
		   Diagram Pressure-Temperature
		 * ----------------------------*/
		setFileTP("D:/Users/kluges1/workspace/pac-tool/ressources/P2T_R22.txt");
		setlistTP(new ArrayList<Point2D.Double>());
		deltaP = 0.0;

		/* -----------------------------
		   Diagram Saturation Curve:
		   Temperature --> Enthalpy  (Liquid / Vapor)  
		 * ----------------------------*/
		setFileSat("D:/Users/kluges1/workspace/pac-tool/ressources/SaturationCurve_R22.txt");
		setlistSatHlP(new ArrayList<Point2D.Double>());
		setlistSatHvP(new ArrayList<Point2D.Double>());
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
			}
		}
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
	 * @param h
	 * @return
	 */
	public double  convSatH2P(double h) {
		double p=0;
		int idlx=0;
		int idvx=0;
		double x = 0, y0 = 0,y1 = 0,x0 = 0,x1 = 0;

		for(int i=0;i<listSatHlP.size();i++) {
			if (h < getSatHl(listSatHlP.size()-1)) {
				// Zone Liquid
				if(h >= getSatHl(i) ){
					idlx = i;
				}
			} else {
				// Zone Vapor
				if(h <= getSatHv(i) ){
					idvx = i;
				}
			}
		}

		if (idlx >= listSatHlP.size()-1) 
			idlx = idlx-1;
		if (idvx >= listSatHlP.size()-1) 
			idvx = idvx-1;

		if (h < getSatHl(listSatHlP.size()-1)) {
			// Zone Liquid
				System.out.println("Liquid");
				x  = h;
				x0 = getSatHl(idlx);
				x1 = getSatHl(idlx+1);
				y0 = getSatP(idlx);
				y1 = getSatP(idlx+1);	
				p = (x-x0)*(y1-y0)/(x1-x0)+ y0;
				System.out.println(x0+" < " + h + " < "+ x1);
				System.out.println(y0+" < " + p + " < "+ y1);
				System.out.println();
		} else {
			// Zone Vapor
			System.out.println("Vapor");
			x  = h;
			x0 = getSatHv(idvx);
			x1 = getSatHv(idvx+1);
			y0 = getSatP(idvx);
			y1 = getSatP(idvx+1);	
			p = (x-x0)*(y1-y0)/(x1-x0)+ y0;
			System.out.println(x0+" < " + h + " < "+ x1);
			System.out.println(y0+" < " + p + " < "+ y1);
			System.out.println();
		}
		return p;
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

	public String getEnthalpyImageFile() {
		return enthalpyImageFile;
	}

	public void setEnthalpyImageFile(String enthalpyImageFile) {
		this.enthalpyImageFile = enthalpyImageFile;
	}

	public double getmHOrigine() {
		return mOrigineH.x;
	}

	public double getmHFinal() {
		return mOrigineH.y;
	}

	public void setmHOrigine(double hOrigine) {
		this.mOrigineH.x = hOrigine;
	}
	public void setmHFinal(double hFinal) {
		this.mOrigineH.y = hFinal;
	}

	public int getiHOrigine() {
		return iOrigineH.x;
	}

	public int getiHFinal() {
		return iOrigineH.y;
	}

	public void setiHOrigine(int hOrigine) {
		this.iOrigineH.x = hOrigine;
	}

	public void setiHFinal(int hFinal) {
		this.iOrigineH.y = hFinal;
	}

	public boolean islocateFinalH() {
		return locateFinalH;
	}

	public void setlocateFinalH(boolean setFinalH) {
		this.locateFinalH = setFinalH;
	}

	public boolean islocateOrigineH() {
		return locateOrigineH;
	}

	public void setlocateOrigineH(boolean setOrigineH) {
		this.locateOrigineH = setOrigineH;
	}

	public double getmPOrigine() {
		return mOrigineP.x;
	}

	public double getmPFinal() {
		return mOrigineP.y;
	}

	public void setmPOrigine(double POrigine) {
		this.mOrigineP.x = POrigine;
	}
	public void setmPFinal(double PFinal) {
		this.mOrigineP.y = PFinal;
	}

	public int getiPOrigine() {
		return iOrigineP.x;
	}

	public int getiPFinal() {
		return iOrigineP.y;
	}

	public void setiPOrigine(int POrigine) {
		this.iOrigineP.x = POrigine;
	}

	public void setiPFinal(int PFinal) {
		this.iOrigineP.y = PFinal;
	}

	public boolean islocateFinalP() {
		return locateFinalP;
	}

	public void setlocateFinalP(boolean setFinalP) {
		this.locateFinalP = setFinalP;
	}

	public boolean islocateOrigineP() {
		return locateOrigineP;
	}

	public void setlocateOrigineP(boolean setOrigineP) {
		this.locateOrigineP = setOrigineP;
	}

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

}
