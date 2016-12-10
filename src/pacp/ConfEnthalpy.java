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

public class ConfEnthalpy {

	private String enthalpyImageFile;
	
	private Point2D.Double mOrigineH = new Point2D.Double();  		// Coordinate mouse (hOrigine,hFinal)
	private Point iOrigineH = new Point();						    // Coordinate Reference for (hOrigine,hFinal) in Enthalpy image
	private boolean locateOrigineH;
	private boolean locateFinalH;
	
	private Point2D.Double mOrigineP = new Point2D.Double();  		// Coordinate mouse (POrigine,PFinal)
	private Point iOrigineP = new Point();						    // Coordinate Reference for (POrigine,PFinal) Pression in image
	private boolean locateOrigineP;
	private boolean locateFinalP;
	
	public ConfEnthalpy() {
		setEnthalpyImageFile("D:/Users/kluges1/workspace/pac-tool/src/pacp/images/diagrammes enthalpique/R22.png");

		// Enthalpy
		setiHOrigine(140);
		setiHFinal(240);
		setmHOrigine(85.0);
		setmHFinal(570.0);
		locateOrigineH=false;
		locateFinalH=false;
		
		// Pression
		setiPOrigine(1);
		setiPFinal(10);
		setmPOrigine(1000.0);
		setmPFinal(516.0);
		locateOrigineP=false;
		locateFinalP=false;
		
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
}
