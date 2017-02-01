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

import java.awt.Color;

// ===================================================================================================================
//										DEFINITION OF THE DRAW ELEMENTS
// ===================================================================================================================

public class ElDraw {

	/*
	 *  	STATIC GLOBAL VAR
	 */
	public static final int _Point 				= 0;
	public static final int _PointMeasure 		= 1;
	public static final int _Line 				= 2;
	public static final int _LineHorzInf 		= 3;
	public static final int _LineHorzInfBP 		= 4;
	public static final int _LineHorzInfHP 		= 5;
	public static final int _LineTemp			= 6;

	/* 	
	 * 		INSTANCE VAR
	 */
	private int type;  				// type of draw : Line, Point,..
	private double x1,y1;   		// Coordinate  
	private double x2,y2;			// Coordinate
	private Color color;
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	// Line Horizontal Infinite
	public ElDraw(int type, double y1) {
		this.type = type;
		this.x1=0.0;
		this.y1=y1;
		this.x2=0.0;
		this.y2=y1;
		this.color = Color.BLACK;
	}

	// Point
	public ElDraw(int type, double x1, double y1) {
		this.type = type;
		this.x1=x1;
		this.y1=y1;
		this.x2=0.0;
		this.y2=0.0;
		this.color = Color.RED;
	}

	// Normal line
	public ElDraw(int type, double x1, double y1, double x2, double y2) {
		this.type = type;
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
		this.color = Color.RED;
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------


	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public int getType() {
		return type;
	}

	public double getX1() {
		return x1;
	}

	public double getY1() {
		return y1;
	}

	public double getX2() {
		return x2;
	}

	public double getY2() {
		return y2;
	}

	public Color getColor() {
		return color;
	}

}

