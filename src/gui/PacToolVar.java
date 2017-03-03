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

import java.util.ArrayList;
import java.util.List;
import computation.COP;
import computation.MeasureCollection;
import enthalpy.Enthalpy;
import pac.Pac;

public class PacToolVar {

	// By creating all the instances here, assure that 
	//   only 1 instance will be created
	//   all data are loaded once
	//   no problem of missing variables 
	
	private Pac pac;
	private Enthalpy enthalpy;
	private COP cop;

	private MeasureCollection measureCollection;
	private List<ElDraw> eDrawL;

	private WinPacToolConfig winPacToolConfig;
	private WinCompressor winCompressor;
	
	private WinEnthalpy winEnthalpy;
	private WinConfEnthalpy winConfEnthalpy;
	
	private WinPressTemp winPressTemp;

	private WinMeasureTable winMeasureTable;
	
	private WinAbout winAbout;
	private WinDefinition winDefinition;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	PacToolVar() {
		pac = new Pac();								// Contains Pac Features
		enthalpy = new Enthalpy();						// Enthalpy Features
		winPacToolConfig = new WinPacToolConfig();		// GUI Configuration
		cop = new COP();								// COP Compute
		eDrawL = new ArrayList<ElDraw>();				// Draw Elements
		measureCollection = new MeasureCollection();	// Measure Collection

		winPressTemp = new WinPressTemp(enthalpy);

		winCompressor = new WinCompressor(pac, winPacToolConfig);
		winEnthalpy = new WinEnthalpy(pac, enthalpy, measureCollection, eDrawL,winPressTemp);
		winConfEnthalpy = new WinConfEnthalpy(winEnthalpy);


		winMeasureTable = new WinMeasureTable(measureCollection);
		
		winAbout = new WinAbout();
		winDefinition = new WinDefinition();
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public Pac getPac() {
		return pac;
	}

	public Enthalpy getEnthalpy() {
		return enthalpy;
	}

	public WinPacToolConfig getWinPacToolConfig() {
		return winPacToolConfig;
	}

	public List<ElDraw> geteDrawL() {
		return eDrawL;
	}

	public MeasureCollection getMeasureCollection() {
		return measureCollection;
	}

	public COP getCop() {
		return cop;
	}
	
	public WinCompressor getWinCompressor() {
		return winCompressor;
	}
	
	public WinEnthalpy getWinEnthalpy() {
		return winEnthalpy;
	}

	public WinConfEnthalpy getWinConfEnthalpy() {
		return winConfEnthalpy;
	}

	public WinAbout getWinAbout() {
		return winAbout;
	}

	public WinDefinition getWinDefinition() {
		return winDefinition;
	}

	public WinMeasureTable getWinMeasureTable() {
		return winMeasureTable;
	}
	public WinPressTemp getWinPressTemp() {
		return winPressTemp;
	}

}
