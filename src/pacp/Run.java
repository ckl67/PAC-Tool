/*
 * - PAC-Tool - 
 * Tool for understanding basics and computation of PAC (Pompe � Chaleur)
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
 * MAIN RUN
 */
public class Run {

	// -------------------------------------------------------
	// 						STATIC GLOBAL VAR
	// -------------------------------------------------------
	public static final String PACTool_Version = "Version Alpha 0.3";

	// -------------------------------------------------------
	// 							MAIN
	// -------------------------------------------------------
	public static void main(String[] args){
		
		Pac pac = new Pac();					// Pac
		Ccop cop = new Ccop();					// Compute COP measure
		Enthalpy enthalpy = new Enthalpy();		// Enthalpy Feature
		
		WinPrime window = new WinPrime(pac, cop, enthalpy);
		window.WinPrimeVisible();
	}
}
