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

public class ExpansionValve {

	private String name;
	private double inputP;
	private double outputP; 
	
	ExpansionValve() {
		setName("Capillaire");
		setInputP(0);
		setOutputP(0);
	}

	ExpansionValve(double P) {
		setName("Capillaire");
		setInputP(P);
		setOutputP(P);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getInputP() {
		return inputP;
	}

	public void setInputP(double inputP) {
		this.inputP = inputP;
	}

	public double getOutputP() {
		return outputP;
	}

	public void setOutputP(double outputP) {
		this.outputP = outputP;
	}
}
