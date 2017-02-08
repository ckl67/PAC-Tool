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

import java.awt.Color;

import computation.Measure;

// ===================================================================================================================
//										DEFINITION OF THE DRAW ELEMENTS
// ===================================================================================================================

public class ElDraw {

	/* 	
	 * 		INSTANCE VAR
	 */
	private ElDrawObject 	elDrawObj;  	// type of draw : Line, Point,..
	private Measure 		measure;		// Full measured object to display can be composed of several elDrawObj
	private Color 			color;			// Color
	private double 			x1,y1;   		// Coordinate  
	private double 			x2,y2;			// Coordinate

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	/**
	 * Line Horizontal Infinite
	 *  	ElDraw(P0,Line,Red,y=10)
	 *  or composed of several draw elements 
	 *  	ElDraw(T4,Line,Red,y=10)
	 *  	ElDraw(T4,Line,Red,x1=2,y1=1)
	 * @param vmeasureObj
	 * @param velDrawObj
	 * @param vcolor
	 * @param vy1
	 */
	public ElDraw( ElDrawObject velDrawObj, Measure vmeasure, Color vcolor) {
		this.elDrawObj = velDrawObj;
		this.color = vcolor;
		this.measure = vmeasure;
		this.x1=0.0;
		this.y1=y1;
		this.x2=0.0;
		this.y2=y1;
	}

	/**
	 * Point
	 *  ElDraw(T1,Line,Red,y=10)
	 * @param vmeasureObj
	 * @param elDrawObj
	 * @param vcolor
	 * @param x1
	 * @param y1
	 */
	public ElDraw(ElDrawObject elDrawObj, Color vcolor, Measure vmeasure , double x1, double y1) {
		this.elDrawObj = elDrawObj;
		this.color = vcolor;
		this.measure = vmeasure;
		this.x1=x1;
		this.y1=y1;
		this.x2=0.0;
		this.y2=0.0;
	}

	// Normal line
	public ElDraw(ElDrawObject elDrawObj, Color vcolor, Measure vmeasure, double x1, double y1, double x2, double y2) {
		this.elDrawObj = elDrawObj;
		this.color = vcolor;
		this.measure = vmeasure;
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------


	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public Measure getMeasure() {
		return measure;
	}

	public ElDrawObject getElDrawObj() {
		return elDrawObj;
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

