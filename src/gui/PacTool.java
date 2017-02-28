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
package gui;

import computation.COP;
import enthalpy.Enthalpy;
import pac.Pac;

/**
 * MAIN RUN
 */
public class PacTool {

	// -------------------------------------------------------
	// 							MAIN
	// -------------------------------------------------------
	public static void main(String[] args){
		
		Pac pac = new Pac();										// Contains Pac Features
		Enthalpy enthalpy = new Enthalpy();							// Enthalpy Features
		WinPacToolConfig winPacToolConfig = new WinPacToolConfig();	// GUI Configuration
		COP cop = new COP();										// COP Compute
		
		WinPacTool winPacTool = new WinPacTool(pac, enthalpy, winPacToolConfig); 
		winPacTool.setVisible(true);
	}
}
