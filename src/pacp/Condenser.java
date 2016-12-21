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

public class Condenser {

	private String name;
	private double inputT;
	private double outputT; 
	
	// Constructor
	Condenser() {
		this.name = "Condenseur";
		this.inputT  = 0; 	// Condenser Input temperature in °C	
		this.outputT = 0;	// Condenser Output Temperature in °C
	}
	
	Condenser(double temp) {
		this.name = "Condenseur";
		this.inputT  = temp; 	// Condenser Input temperature in °C	
		this.outputT = temp;	// Condenser Output Temperature in °C
	}

	// Setter & Getter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getInputT() {
		return inputT;
	}
	public void setInputT(double inputT) {
		this.inputT = inputT;
	}
	public double getOutputT() {
		return outputT;
	}
	public void setOutputT(double outputT) {
		this.outputT = outputT;
	}
}
