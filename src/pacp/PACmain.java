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

/**
 * Some Helps 
 * ==========
 * Adding external Jar
 		Create a folder called lib in your project folder.
		copy to this folder all the jar files you need.
		Refresh your project in eclipse.
		Select all the jar files, then right click on one of them and select Build Path -> Add to Build Path
 *
 */

public class PacMain {

	public static final String PACTool_Version = "Version Alpha 0.1";

	// Pac
	private static Pac pac = new Pac();

	// Compute COP measure
	private static Ccop cop = new Ccop();
	
	// Conf Enthalpy Image
	private static ConfEnthalpy confEnthalpy = new ConfEnthalpy();
	
	// ========================================================================================
	//                                 		MAIN
	// ========================================================================================
	public static void main(String[] args){
		WinPrime window = new WinPrime(pac, cop, confEnthalpy);
		window.WinPrimeVisible();
	}

}
