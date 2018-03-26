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
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ElDraw {

	// -------------------------------------------------------
	// 					CONSTANT
	// -------------------------------------------------------

	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------

	private EloElDraw 		elDrawObj;  	// type of draw : Line, Point,..
	private Color 			color;			// Color
	private double 			x1,y1;   		// Is the real value, H, P,.. (not draw coordinate in integer) 
	private double 			x2,y2;			// Is the real value, H, P,.. (not draw coordinate in integer)

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	/**
	 * Line Horizontal Infinite
	 *  	ElDraw(LINE_HORZ,Red,xy=10)
	 * 
	 * @param velDrawObj
	 * @param vcolor
	 * @param xy
	 */
	public ElDraw( EloElDraw velDrawObj, Color vcolor , double xy) {
		logger.trace("(ElDraw):: Create={}", velDrawObj.name());
		this.elDrawObj = velDrawObj;
		this.color = vcolor;
		if (velDrawObj.equals(EloElDraw.LINE_HORZ) ) {  
			// Line Horizontal
			this.x1=0;	// Will be defined during the drawing
			this.y1=xy;
			this.x2=0;	// Will be defined during the drawing
			this.y2=xy;
		} else {
			// Line Vertical 
			this.x1=xy;
			this.y1=0;	// Will be defined during the drawing
			this.x2=xy;
			this.y2=0;	// Will be defined during the drawing
		}
	}

	/**
	 * Point
	 *  	ElDraw(POINT,Red,x1=2,y1=1)
	 * 
	 * @param velDrawObj
	 * @param vcolor
	 * @param x1
	 * @param y1
	 */
	public ElDraw( EloElDraw velDrawObj, Color vcolor , double x1, double y1) {
		logger.trace("(ElDraw):: Create={}", velDrawObj.name());
		this.elDrawObj = velDrawObj;
		this.color = vcolor;
		this.x1=x1;
		this.y1=y1;
		this.x2=0.0;
		this.y2=0.0;
	}

	/**
	 * Normal line
	 * 	   	ElDraw(LINE,Red,x1=2,y1=1,x2=4,y2=8)
	 *
	 * @param velDrawObj
	 * @param vcolor
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public ElDraw(EloElDraw velDrawObj, Color vcolor , double x1, double y1, double x2, double y2) {
		logger.trace("(ElDraw):: Create={}", velDrawObj.name());
		this.elDrawObj = velDrawObj;
		this.color = vcolor;
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------


	public EloElDraw getElDrawObj() {
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

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}

}

