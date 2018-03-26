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

/*

	P
	^
	|
	|
	|                     XXXXXXXXXXXXX
	|                  XXXX           XXXXX         (PK)
	|        (5)+-----(4)-----------------(3)--------------------+ (2)
	|           | XXXX                      XX                   /
	|           |XX                          XX                 /
	|           |X                            XX               /
	|          X|                              XX             /
	|         XX|                               X            /
	|         X |                               XX          /
	|        XX |             (P0)               X         /
	|       XX  +---------------------------------+---+---+
	|       X  (6)                               (7) (8)  (1)
	|      XX                                     X
	|      X                                      X
	|
	+------------------------------------------------------------------------> H

 */

public enum EloEnthalpyElDraw {
	P1("P1"),
	P2("P2"),
	P3("P3"),
	P4("P4"),
	P5("P5"),
	P6("P6"),
	P7("P7"),
	P8("P8"),
	LINE_P0("P0"),
	LINE_PK("PK"),
	ISOTHERM("Isotherm"),
	ISOBAR("Isobar"),
	;
	
	// --------------------------------------------------------------------
	// DEFINITION OF VARIABLES USED IN ENUMERATION
	// --------------------------------------------------------------------
	private String text;
	
	// -------------------------------------------------------
	// 					CONSTRUCTOR
	// -------------------------------------------------------
	EloEnthalpyElDraw(String vtext){
		setText(vtext);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public int id() {
		return this.ordinal();
	}

	
}
