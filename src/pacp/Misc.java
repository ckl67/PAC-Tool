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
import java.util.List;

public class Misc {

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	private Misc() { }  // Prevents instantiation

	// -------------------------------------------------------
	// 						CONSTANTES (DEFINE)
	// -------------------------------------------------------
	public static final String PACTool_Version = "Version Alpha 0.3";

	
	public static final int _COMPRESSOR = 0;
	public static final int _CONDENSER = 1;
	public static final int _EXPANSIONVALVE = 2;
	public static final int _EVAPORATOR = 3;

	public static final int _INPUT_COMPRESSOR = 0;
	public static final int _INPUT__CONDENSER = 1;
	public static final int _INPUT__EXPANSIONVALVE = 2;
	public static final int _INPUT__EVAPORATOR = 3;

	public static final boolean _DEBUG_STATE =  true;
	public static final int _DEBUG_MODULE_ENTHALPY = 0x01;  //Mask
	
	public static final int _DEBUG_MODE = _DEBUG_MODULE_ENTHALPY;
	
	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

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

	/**
	 * Convert (BTU) British Thermal Unit in Watt 
	 * 1btu/hr = 0,29307107 watt
	 * @param btu
	 * @return watt
	 */
	public static double btuhr2watt(double btu) {
		double watt;
		watt = btu * 0.29307107;
		return watt;	
	}

	/**
	 * Convert Watt in (BTU) British Thermal Unit  
	 * 1btu/hr = 0,29307107 watt
	 * @param watt
	 * @return btu
	 */
	public static double watt2btuhr(double watt) {
		double btu;
		btu = watt / 0.29307107;
		return btu;	
	}

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
	/**
	 * Return The numbers of "Char c" in the sting str:
	 * @param str
	 * @param c
	 * @return: integer 
	 */
	public static int nbCharInString(String str,char c) {
		int res=0;
		if(str==null)
			return res;
		for(int i=0;i<str.length();i++)
			if(c==str.charAt(i))
				res++;
		return res;
	}

	/**
	 * Generate a String of n repeated characters
	 * @param c
	 * @param n
	 * @return
	 */
	public static String genRepeatChar(char c, Integer n) {
	    StringBuilder b = new StringBuilder();
	    for (int x = 0; x < n; x++)
	        b.append(c);
	    return b.toString();
	}
	
	/**
	 * Return the closest value in a list
	 * @param of
	 * @param in
	 * @return
	 */
	public static double closestInL(double of, List<Double> in) {
		double min = Integer.MAX_VALUE;
		double closest = of;
		double diff;
		
		for (double v : in) {
			diff = Math.abs(v - of);
			if (diff < min) {
				min = diff;
				closest = v;
			}
		}
		return closest;
	}

	/**
	 * Compute the maximum interval between 2 consecutive elements in a list.
	 * @param lin
	 * @return
	 */
	public static double maxIntervalL( List<Double> lin) {
		double diff=0;
		
		for(int i=0;i<lin.size()-1;i++) {
			if (diff < Math.abs(lin.get(i) - lin.get(i+1)) ) {
				diff=Math.abs(lin.get(i) - lin.get(i+1));
			}
		}
		return diff;
	}
	
	/** 
	 * Depending of the constant _DEBUG_STATE, _DEBUG_MODULE will display the output in the console
	 * @param s
	 */
	public static void debug(int module, String s ) {
		//if  (_DEBUG_MODE & _DEBUG_MODULE_ENTHALPY) {
		System.out.println(s);
		//}
		
	}

}
