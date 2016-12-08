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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

public class PacCommon {

	// ========================================================================================
	/**
	 * Convert Degre in Farenheit
	 * @param degre : Value in Degre
	 * @return : Value in Farenheit
	 */
	public static double degre2farenheit(double degre){
		double faren;
		faren = degre * 1.8 + 32.0;
		return faren;	
	}

	// ========================================================================================
	/**
	 * Convert Farenheit in Degre
	 * @param faren : Value in Farenheit
	 * @return : Value in Degre
	 */
	public static double farenheit2degre(double faren){
		double degre;	
		degre = (faren- 32.0)/1.8;
		return degre;	
	}

	// ========================================================================================
	/**
	 * Convert (BUT) British Thermal Unit in Watt 
	 * 1btu/hr = 0,29307107 watt
	 * @param btu
	 * @return watt
	 */
	public static double buthr2watt(double btu) {
		double watt;
		watt = btu * 0.29307107;
		return watt;	
	}

	// ========================================================================================
	/**
	 * Convert Watt in (BUT) British Thermal Unit  
	 * 1btu/hr = 0,29307107 watt
	 * @param watt
	 * @return btu
	 */
	public static double watt2btuhr(double watt) {
		double btu;
		btu = watt / 0.29307107;
		return btu;	
	}

	// ========================================================================================
	/**
	 * Convert pound to Kg
	 * @param Pound = lbs
	 * @return kg
	 */
	public static double pound2kg(double lbs) {
		double kg;
		kg = lbs / 2.20462262185 / 3600;
		return kg;
	}

	// ========================================================================================
	/**
	 * Convert Kg to Pound
	 * @param kg
	 * @return Pound
	 */
	public static double kg2pound(double kg) {
		double lbs;
		lbs = kg * 2.20462262185 * 3600;
		return lbs;
	}

	// ========================================================================================
	/**
	 * Compute Cos(Phi)
	 * @param Power, Voltage, Current
	 * @return Cos(Phi)
	 */
	public static double cosphi(double p, double u, double i) {
		double cosphi;
		cosphi = p/(u*i);
		return cosphi;
	}
	
	// ========================================================================================
	/**
	 * Center a Window
	 * @param frame
	 */
	public static void centreWindow(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}

}
