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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Enthalpy {


	// Enthalpy
	private String enthalpyImageFile;

	private Point2D.Double mOrigineH = new Point2D.Double();  		// Coordinate mouse (hOrigine,hFinal)
	private Point iOrigineH = new Point();						    // Coordinate Reference for (hOrigine,hFinal) in Enthalpy image
	private boolean locateOrigineH;
	private boolean locateFinalH;

	private Point2D.Double mOrigineP = new Point2D.Double();  		// Coordinate mouse (POrigine,PFinal)
	private Point iOrigineP = new Point();						    // Coordinate Reference for (POrigine,PFinal) Pressure in image
	private boolean locateOrigineP;
	private boolean locateFinalP;

	// Pressure-Temperature
	private String temperaturePressureFile;
	private List<Point2D.Double> listTempPress;
	private double correctionPressure;

	public Enthalpy() {
		setEnthalpyImageFile("D:/Users/kluges1/workspace/pac-tool/ressources/R22.png");

		// Enthalpy
		setiHOrigine(140);
		setiHFinal(240);
		setmHOrigine(85.0);
		setmHFinal(570.0);
		locateOrigineH=false;
		locateFinalH=false;

		// Pressure
		setiPOrigine(1);
		setiPFinal(10);
		setmPOrigine(1000.0);
		setmPFinal(516.0);
		locateOrigineP=false;
		locateFinalP=false;

		// Pressure-Temperature
		setTemperaturePressureFile("D:/Users/kluges1/workspace/pac-tool/ressources/P2T_R22.txt");
		setlistTempPress(new ArrayList<Point2D.Double>());
		correctionPressure = 1.0;

	}

	// --------------------------------------------------------------------------------
	// Image File
	// --------------------------------------------------------------------------------
	public String getEnthalpyImageFile() {
		return enthalpyImageFile;
	}

	public void setEnthalpyImageFile(String enthalpyImageFile) {
		this.enthalpyImageFile = enthalpyImageFile;
	}

	// --------------------------------------------------------------------------------
	// Mouse Position for H :  Origin / Final
	// --------------------------------------------------------------------------------
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

	// --------------------------------------------------------------------------------
	// Image Reference for H :  Origin / Final
	// --------------------------------------------------------------------------------
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

	// --------------------------------------------------------------------------------
	// Image Reference for H Requested :  Origin / Final
	// --------------------------------------------------------------------------------
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

	// --------------------------------------------------------------------------------
	// Mouse Position for P :  Origin / Final
	// --------------------------------------------------------------------------------
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

	// --------------------------------------------------------------------------------
	// Image Reference for P :  Origin / Final
	// --------------------------------------------------------------------------------
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

	// --------------------------------------------------------------------------------
	// Image Reference for P Requested :  Origin / Final
	// --------------------------------------------------------------------------------
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

	// ================================================================================
	//						Temperature / Pressure
	// ================================================================================

	/**
	 * Set or Get the Temperature/Pressure File
	 */
	public String getTemperaturePressureFile() {
		return temperaturePressureFile;
	}

	public void setTemperaturePressureFile(String temperaturePressureFile) {
		this.temperaturePressureFile = temperaturePressureFile;
	}

	// --------------------------------------------------------------------------------

	public List<Point2D.Double> getlistTempPress() {
		return listTempPress;
	}

	public double getTempFromList(int id) {
		return listTempPress.get(id).getX();
	}

	public double getPressFromList(int id) {
		return listTempPress.get(id).getY();
	}

	public void setlistTempPress(List<Point2D.Double> listTempPress) {
		this.listTempPress = listTempPress;
	}

	// Load Pressure Temperature Text file
	public void loadPressureTemperatureFile() {
		Pattern p = Pattern.compile("(-?\\d+(,\\d+)?)");
		BufferedReader buff_in;
		String line;
		double temp=0;
		double press=0;

		int i;

		try {
			buff_in = new BufferedReader(new FileReader(temperaturePressureFile));
			try {
				while ((line = buff_in.readLine()) != null)
				{
					if (!line.startsWith("#") ) {
						Matcher m = p.matcher(line);
						i=0;
						while (m.find()) {
							if (i ==0) 
								temp = Double.parseDouble(m.group().replace(",", "."));
							else 
								press = Double.parseDouble(m.group().replace(",", "."));
							i++;
						}
						listTempPress.add(new Point2D.Double(temp, press+correctionPressure));
					}
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				buff_in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public double getPressFromTemp(double temp){
		double x,presso=0;
		int idx=0;
		if (listTempPress.size() != 0) {
			for(int c = 0; c < listTempPress.size(); c++){
				if(temp >= listTempPress.get(c).getX() ){
					idx = c;
				}
			}
			if (idx == listTempPress.size()-1) {
				idx = idx-1;
			} 

			double y0,y1,x0,x1;
			x  = temp;
			x0 = listTempPress.get(idx).getX();
			x1 = listTempPress.get(idx+1).getX();
			y0 = listTempPress.get(idx).getY();
			y1 = listTempPress.get(idx+1).getY();
			presso = (x-x0)*(y1-y0)/(x1-x0)+ y0;
		}
		return presso+correctionPressure;
	}

	public double getTempFromPress(double press){
		double x,tempo=0;
		int idx=0;
		if (listTempPress.size() != 0) {

			for(int c = 0; c < listTempPress.size(); c++){
				if((press-correctionPressure) >= listTempPress.get(c).getY() ){
					idx = c;
				}
			}
			if (idx == listTempPress.size()-1) {
				idx = idx-1;
			} 

			double y0,y1,x0,x1;
			x  = press;
			x0 = listTempPress.get(idx).getY();
			x1 = listTempPress.get(idx+1).getY();
			y0 = listTempPress.get(idx).getX();
			y1 = listTempPress.get(idx+1).getX();
			tempo = (x-x0)*(y1-y0)/(x1-x0)+ y0;
		}
		return tempo;
	}

}
