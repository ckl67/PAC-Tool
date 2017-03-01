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

	private Pac pac;
	private Enthalpy enthalpy;
	private WinPacToolConfig winPacToolConfig;
	private List<ElDraw> eDrawL;
	private MeasureCollection measureCollection;
	private COP cop;
	
	PacToolVar() {
		pac = new Pac();								// Contains Pac Features
		enthalpy = new Enthalpy();						// Enthalpy Features
		winPacToolConfig = new WinPacToolConfig();		// GUI Configuration
		cop = new COP();								// COP Compute
		eDrawL = new ArrayList<ElDraw>();				// Draw Elements
		measureCollection = new MeasureCollection();	// Measure Collection
	}

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
	
}
