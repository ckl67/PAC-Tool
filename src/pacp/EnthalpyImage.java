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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EnthalpyImage {
	
	private String enthalpyImageFile;								// Enthalpy image file (.png)

	private Point2D.Double mOrigineH = new Point2D.Double();  		// Coordinate mouse (hOrigine,hFinal)
	private Point iOrigineH = new Point();						    // Coordinate Reference for (hOrigine,hFinal) in Enthalpy image
	private boolean locateOrigineH;									// Image Reference for H Requested :  Origin / Final
	private boolean locateFinalH;

	private Point2D.Double mOrigineP = new Point2D.Double();  		// Coordinate mouse (POrigine,PFinal)
	private Point iOrigineP = new Point();						    // Coordinate Reference for (POrigine,PFinal) Pressure in image
	private boolean locateOrigineP;									// Image Reference for P Requested :  Origin / Final
	private boolean locateFinalP;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public EnthalpyImage() {
		
		// Image
		setEnthalpyImageFile("D:/Users/kluges1/workspace/pac-tool/ressources/R22/R22 couleur A4.png");

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

}
