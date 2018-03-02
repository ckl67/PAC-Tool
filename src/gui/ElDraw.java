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
import java.util.List;

import org.apache.logging.log4j.Logger;

import mpoints.EloMeasurePoint;
import mpoints.MeasurePoint;

import org.apache.logging.log4j.LogManager;

// ===================================================================================================================
//										DEFINITION OF THE DRAW ELEMENTS
// ===================================================================================================================

public class ElDraw {

	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());


	private EloElDraw 		elDrawObj;  	// type of draw : Line, Point,..
	private String 			ensembleName;	// Several ElDraw objects can belong to same ensemble
	private boolean 		movable;		// Can the point be moved
	private Color 			color;			// Color
	private double 			x1,y1;   		// Coordinate  (final Coordinates, to avoid re-computation)
	private double 			x2,y2;			// Coordinate  (final Coordinates, to avoid re-computation)

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	/**
	 * Line Horizontal Infinite
	 *  	ElDraw(P0,Line,Red,y=10)
	 *  or composed of several draw elements 
	 *  	ElDraw(T4,Line,Red,y=10)
	 *  	ElDraw(T4,Line,Red,x1=2,y1=1)
	 * 
	 * @param vensembleName
	 * @param velDrawObj
	 * @param vcolor
	 * @param y
	 */
	public ElDraw( String vensembleName, EloElDraw velDrawObj, boolean vmovale, Color vcolor , double xy) {
		logger.info("(ElDraw):: Create={}", velDrawObj.name());
		this.ensembleName = vensembleName;
		this.elDrawObj = velDrawObj;
		this.movable = vmovale;
		this.color = vcolor;
		if (velDrawObj.equals(EloElDraw.LINE_HORZ) ) {  
			// Line Horizontal
			this.x1=0;	// Will be define during the drawing
			this.y1=xy;
			this.x2=0;	// Will be define during the drawing
			this.y2=xy;
		} else {
			// Line Vertical 
			this.x1=xy;
			this.y1=0;	// Will be define during the drawing
			this.x2=xy;
			this.y2=0;	// Will be define during the drawing
		}
	}

	/**
	 * Point
	 *  	ElDraw(T4,Point,Red,x1=2,y1=1)
	 * 
	 * @param vensembleName
	 * @param velDrawObj
	 * @param vcolor
	 * @param x1
	 * @param y1
	 */
	public ElDraw( String vensembleName, EloElDraw velDrawObj, boolean vmovale, Color vcolor , double x1, double y1) {
		logger.info("(ElDraw):: Create={}", velDrawObj.name());
		this.ensembleName = vensembleName;
		this.elDrawObj = velDrawObj;
		this.movable = vmovale;
		this.color = vcolor;
		this.x1=x1;
		this.y1=y1;
		this.x2=0.0;
		this.y2=0.0;
	}

	/**
	 * Normal line
	 * 	   	ElDraw(T4,DotedLine,Red,x1=2,y1=1,x2=4,y2=8)
	 *
	 * @param vensembleName
	 * @param velDrawObj
	 * @param vcolor
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public ElDraw( String vensembleName, EloElDraw velDrawObj,  boolean vmovale, Color vcolor , double x1, double y1, double x2, double y2) {
		logger.info("(ElDraw):: Create={}", velDrawObj.name());
		this.ensembleName = vensembleName;
		this.elDrawObj = velDrawObj;
		this.movable = vmovale;	
		this.color = vcolor;
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
	}

	// -------------------------------------------------------
	// 			METHOD -- FUNCTION STATIC !!
	// -------------------------------------------------------

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
	public static List<ElDraw> createElDrawFrom(List<MeasurePoint> lMeasurePoints, List<ElDraw> leDraw) {
		// boolean onshot = true;

		for (EloEnthalpyElDraw p : EloEnthalpyElDraw.values()) {
			MeasurePoint m;
			switch (p) {
			case P1:
				m = lMeasurePoints.get(EloMeasurePoint.P1.id()); 
				logger.info("Point={} H={} P={}", p.name(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));
				leDraw.add(new ElDraw(p.name(),	EloElDraw.POINT_TXT_BLV,true,Color.BLACK,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
				break;
			case P2:
				m = lMeasurePoints.get(EloMeasurePoint.P2.id()); 
				logger.info("Point={} H={} P={}", p.name(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));
				leDraw.add(new ElDraw(p.name(),	EloElDraw.POINT_TXT_ABV,true,Color.BLACK,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
				break;
			case P3:
				m = lMeasurePoints.get(EloMeasurePoint.P3.id()); 
				logger.info("Point={} H={} P={}", p.name(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));
				leDraw.add(new ElDraw(p.name(),	EloElDraw.POINT_TXT_ABV,false,Color.BLACK,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
				break;
			case P4:
				m = lMeasurePoints.get(EloMeasurePoint.P4.id()); 
				logger.info("Point={} H={} P={}", p.name(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));
				leDraw.add(new ElDraw(p.name(),	EloElDraw.POINT_TXT_ABV,false,Color.BLACK,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
				break;
			case P5:
				m = lMeasurePoints.get(EloMeasurePoint.P4.id()); 
				logger.info("Point={} H={} P={}", p.name(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));
				leDraw.add(new ElDraw(p.name(),	EloElDraw.POINT_TXT_ABV,true,Color.BLACK,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
				break;
			case P6:
				m = lMeasurePoints.get(EloMeasurePoint.P6.id()); 
				logger.info("Point={} H={} P={}", p.name(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));
				leDraw.add(new ElDraw(p.name(),	EloElDraw.POINT_TXT_BLV,true,Color.BLACK,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
				break;
			case P7:
				m = lMeasurePoints.get(EloMeasurePoint.P7.id()); 
				logger.info("Point={} H={} P={}", p.name(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));
				leDraw.add(new ElDraw(p.name(),	EloElDraw.POINT_TXT_BLV,false,Color.BLACK,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
				break;
			case P8:
				m = lMeasurePoints.get(EloMeasurePoint.P8.id()); 
				logger.info("Point={} H={} P={}", p.name(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));
				leDraw.add(new ElDraw(p.name(),	EloElDraw.POINT_TXT_BLV,true,Color.BLACK,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
				break;
			case P0:
				m = lMeasurePoints.get(EloMeasurePoint.P7.id()); 
				leDraw.add(new ElDraw(p.name(),	EloElDraw.LINE_HORZ,false,Color.BLACK,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
			case PK:
				m = lMeasurePoints.get(EloMeasurePoint.P3.id()); 
				leDraw.add(new ElDraw(p.name(),	EloElDraw.LINE_HORZ,false,Color.BLACK,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));

			default:
				break;
			}
		}

		return leDraw;
		
	}
		
	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------


	public EloElDraw getElDrawObj() {
		return elDrawObj;
	}

	public String getEnsembleName() {
		return ensembleName;
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

	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
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

