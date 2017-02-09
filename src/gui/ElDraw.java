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

import computation.MeasureChoiceStatus;
import computation.MeasureCollection;
import computation.MeasureObject;
import computation.MeasurePoint;

// ===================================================================================================================
//										DEFINITION OF THE DRAW ELEMENTS
// ===================================================================================================================

public class ElDraw {

	private ElDrawObject 	elDrawObj;  	// type of draw : Line, Point,..
	private String 			ensembleName;	// Several ElDraw objects can belong to same ensemble 
	private Color 			color;			// Color
	private double 			x1,y1;   		// Coordinate  
	private double 			x2,y2;			// Coordinate
	private double 			xmin,ymin;		// Coordinate
	private double 			xmax,ymax;		// Coordinate

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
	public ElDraw( String vensembleName, ElDrawObject velDrawObj, Color vcolor , double xy) {
		this.ensembleName = vensembleName;
		this.elDrawObj = velDrawObj;
		this.color = vcolor;
		if (velDrawObj.equals(ElDrawObject.LineHorzInfBP) || velDrawObj.equals(ElDrawObject.LineHorzInfHP) ) {  
			// Line Horizontal
			this.x1=xmin;
			this.y1=xy;
			this.x2=xmax;
			this.y2=xy;
		} else {
			// Line Vertical 
			this.x1=xy;
			this.y1=ymin;
			this.x2=xy;
			this.y2=ymax;
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
	public ElDraw( String vensembleName, ElDrawObject velDrawObj, Color vcolor , double x1, double y1) {
		this.ensembleName = vensembleName;
		this.elDrawObj = velDrawObj;
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
	public ElDraw( String vensembleName, ElDrawObject velDrawObj, Color vcolor , double x1, double y1, double x2, double y2) {
		this.ensembleName = vensembleName;
		this.elDrawObj = velDrawObj;
		this.color = vcolor;
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public static List<ElDraw> createElDrawFrom(MeasureCollection measureCollection, List<ElDraw> veDrawL) {
		
		List<ElDraw> eDrawL = veDrawL;
		
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
				System.out.println(p + 
						" Choice Status =" + m.getMeasureChoiceStatus() + 
						" value=" + m.getValue() + 
						" T=" + m.getMT() + 
						" --> P=" + m.getMP() + 
						" ==> P0 or PK =" + m.getMP0PK() +
						" Hsat(Approx) =" + m.getMHaprox() + 
						" Hsat(Real) =" + m.getMHreal());

				eDrawL.add(new ElDraw(p.name(),ElDrawObject.PointMeasureLogP,Color.GREEN,m.getMHaprox(),Math.log10(m.getMP())));
			}

		}
		return eDrawL;
		
	}
	
	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

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

	public void setXmin(double xmin) {
		this.xmin = xmin;
	}

	public void setYmin(double ymin) {
		this.ymin = ymin;
	}

	public void setXmax(double xmax) {
		this.xmax = xmax;
	}

	public void setYmax(double ymax) {
		this.ymax = ymax;
	}

}

