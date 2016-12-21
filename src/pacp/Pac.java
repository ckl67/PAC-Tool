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

public class Pac {

	private Compressor compressor;
	private Condenser condenser;
	private ExpansionValve expansionValve;
	private Evaporator evaporator;
	private Refrigerant fluidRefrigerant;

	private Circulator circulator;
	private HeatSource circuit;
	private HeatTransferFluid transferFluid;

	private Circulator circulatorDistribution;
	private HeatDistribution circuitHeatDistribution;
	private HeatTransferFluid fluidCaloDistribution;


	// Constructor
	public Pac() {
		setCompressor(new Compressor());
		setCirculator(new Circulator());
	}

	public Pac(int input) {
		
		setCompressor(new Compressor());
		setCirculator(new Circulator());
	}

	// ========================================================================================================	

	// Getter and Setter
	public Compressor getCompressor() {
		return compressor;
	}

	public void setCompressor(Compressor compressor) {
		this.compressor = compressor;
	}

	public Circulator getCirculator() {
		return circulator;
	}

	public void setCirculator(Circulator circulator) {
		this.circulator = circulator;
	}

	public Condenser getCondenser() {
		return condenser;
	}

	public void setCondenser(Condenser condenser) {
		this.condenser = condenser;
	}

	public Evaporator getEvaporator() {
		return evaporator;
	}

	public void setEvaporator(Evaporator evaporator) {
		this.evaporator = evaporator;
	}

}
