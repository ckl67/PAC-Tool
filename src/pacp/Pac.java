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
	
	protected Scroll scroll;
	private Circulator circulator;
	private Condenser condenser;
	private Evaporator evaporator;
	
	// Constructor
	public Pac() {
		setScroll(new Scroll());
	}

	// Getter and Setter
	public Scroll getScroll() {
		return scroll;
	}

	public void setScroll(Scroll scroll) {
		this.scroll = scroll;
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
