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

public class MeasurePoints {
	
	private String name;
	private int x;
	private int y;
	private String definition;
	private double value;
	private String unity;
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public MeasurePoints(String name, int x, int y,String definition,double value, String unity) {
		this.name = name;
		this.x = x;
		this.y=y;
		this.definition=definition;
		this.value = value;
		this.unity = unity;
	}
	
	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	
	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------
	
	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getDefinition() {
		return definition;
	}
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}


	public String getUnity() {
		return unity;
	}

	public void setUnity(String unity) {
		this.unity = unity;
	}

}
