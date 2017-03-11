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

public enum ItemCoord {
	Compressor(490,65,50,50),
	Circulator(795,45,20,20);

	// --------------------------------------------------------------------
	// DEFINITION OF VARIABLES USED IN ENUMERATION
	// --------------------------------------------------------------------

	private int xm;
	private int ym;
	private int width;
	private int heigh;
	
	// -------------------------------------------------------
	// 					CONSTRUCTOR
	// -------------------------------------------------------
	ItemCoord(int vxm, int vym, int vwidth, int vheigh) {
		this.xm = vxm;
		this.ym = vym;
		this.width = vwidth;
		this.heigh = vheigh;
		
	}

	public int getXm() {
		return xm;
	}

	public int getYm() {
		return ym;
	}

	public int getWidth() {
		return width;
	}

	public int getHeigh() {
		return heigh;
	}
	
}
