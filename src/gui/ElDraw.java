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
import org.apache.logging.log4j.LogManager;

import computation.MeasureChoiceStatus;
import computation.MeasureCollection;
import computation.MeasureObject;
import computation.MeasurePoint;


// ===================================================================================================================
//										DEFINITION OF THE DRAW ELEMENTS
// ===================================================================================================================

public class ElDraw {

	private static final Logger logger = LogManager.getLogger(ElDraw.class.getName());

	private ElDrawObject 	elDrawObj;  	// type of draw : Line, Point,..
	private String 			ensembleName;	// Several ElDraw objects can belong to same ensemble
	private boolean 		movable;		// Can the point be moved
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
	 * 
	 * @param vensembleName
	 * @param velDrawObj
	 * @param vcolor
	 * @param y
	 */
	public ElDraw( String vensembleName, ElDrawObject velDrawObj, boolean vmovale, Color vcolor , double xy) {
		this.ensembleName = vensembleName;
		this.elDrawObj = velDrawObj;
		this.movable = vmovale;
		this.color = vcolor;
		if (velDrawObj.equals(ElDrawObject.LineP) ) {  
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
	 *  	ElDraw(T4,Line,Red,x1=2,y1=1)
	 * 
	 * @param vensembleName
	 * @param velDrawObj
	 * @param vcolor
	 * @param x1
	 * @param y1
	 */
	public ElDraw( String vensembleName, ElDrawObject velDrawObj, boolean vmovale, Color vcolor , double x1, double y1) {
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
	public ElDraw( String vensembleName, ElDrawObject velDrawObj,  boolean vmovale, Color vcolor , double x1, double y1, double x2, double y2) {
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
	// 							METHOD
	// -------------------------------------------------------

	public static List<ElDraw> createElDrawFrom(MeasureCollection measureCollection, List<ElDraw> eDrawL) {
		boolean onshot = true;

		for (MeasureObject p : MeasureObject.values()) {
			int n = p.ordinal(); 		// p = T1,T2,... n = 0 , 1, 
			List<MeasurePoint> measureL = measureCollection.getMeasurePL();
			MeasurePoint m = measureL.get(n);  

			// ----------------------------------
			// Will now create the Draw Element: 
			// 	Draw elements (ElDraw) must be 
			//		* simple elements to be drawn quickly
			//			g2.draw( new Line2D.Double(x1,y1,x2,y2);
			//		* associable
			//			Behind a same reference (= Measured Point) several simple draw elements
			//			to draw or to delete
			//			g2.drawString(s,x,y);
			//			g2.draw( new Ellipse2D.Double(x1,y1);
			//			g2.draw( new Line2D.Double(x1,y1,x2,y2);
			//		* Movable
			//			The element can be moved and receive a new value Hreal
			//
			//  Also
			// 		Although P3 and P4 are existing Measured Object, only P3 will be retained to create PK
			//  Conclusion
			//  	eDrawL 
			//			(P0,Line,get_x1,get_y1,get_x2,get_y2)
			//			(P0,Text,get_x1,get_y1)
			//			(P0,DottedLine,get_x1,get_y1,get_x2,get_y2
			//			(P0,Point,get_x1,get_y1)
			//		Move
			//			Only for points ! 
			//			If point is moved we set : _ChosenHreal
			// ----------------------------------
			if ( (m.getMeasureChoiceStatus().equals(MeasureChoiceStatus.ChosenHaprox)) || 
					(m.getMeasureChoiceStatus().equals(MeasureChoiceStatus.ChosenP0PK))) {
			
				switch (m.getMeasureObject()) {
				case T1 : case T6 : case T8 :	// Points intersection with P0
					logger.info("Point={} H={} P={}", m.getMeasureObject(),m.getMH(),m.getMP0PK());
					eDrawL.add(new ElDraw(p.name(),ElDrawObject.PointP0_HP,true,Color.BLACK,m.getMH(),m.getMP0PK()));
					break;
				case T2 : case T5 :				// Points intersection with PK
					logger.info("Point={} H={} P={}", m.getMeasureObject(),m.getMH(),m.getMP0PK());
					eDrawL.add(new ElDraw(p.name(),ElDrawObject.PointPK_HP,true,Color.BLACK,m.getMH(),m.getMP0PK()));
					break;
				case P3 : case P4 : 			// Points PK (P3 and P4) 
					logger.info("Point={} H={} P={}", m.getMeasureObject(),m.getMH(),m.getMP0PK());
					eDrawL.add(new ElDraw(p.name(),ElDrawObject.PointPK_HP,false,Color.BLACK,m.getMH(),m.getMP0PK()));
					if (onshot) {
						onshot = false;
						logger.info("Line={} P={}", m.getMeasureObject(),m.getMP0PK());
						eDrawL.add(new ElDraw("PK",ElDrawObject.LineP,false,Color.BLACK,m.getMP0PK()));											
					}
					break;			
				case P7 :						// Point P0 (P7)
					logger.info("Point={} H={} P={}", m.getMeasureObject(),m.getMH(),m.getMP0PK());
					eDrawL.add(new ElDraw(p.name(),ElDrawObject.PointP0_HP,false,Color.BLACK,m.getMH(),m.getMP0PK()));
					logger.info("Line={} P={}", m.getMeasureObject(),m.getMP0PK());
					eDrawL.add(new ElDraw("PK",ElDrawObject.LineP,false,Color.BLACK,m.getMP0PK()));											
					break;			
				default:
					break;

				}
			}

		}
		return eDrawL;

	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public void setX1(double x) {
		this.x1 = x;
	}

	public ElDrawObject getElDrawObj() {
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
}

