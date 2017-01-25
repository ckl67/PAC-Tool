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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

// ===================================================================================================================
//										DEFINITION OF THE DRAW ELEMENTS
// ===================================================================================================================

public class ElDraw {

	/*
	 *  	STATIC GLOBAL VAR
	 */
	public static final int _Point 			= 0;
	public static final int _PointYLog 		= 1;
	public static final int _Line 			= 2;
	public static final int _LineHorzInf 	= 3;
	public static final int _LineHorzInfBP 	= 4;
	public static final int _LineHorzInfHP 	= 5;

	/* 	
	 * 		INSTANCE VAR
	 */
	private int type;  		// type of draw : Line, Point,..
	private double x1,y1;   // Coordinate  
	private double x2,y2;	// Coordinate
	private Color color;
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	// Line Horizontal Infinite
	public ElDraw(int type, double y1, double xmin, double xmax) {
		this.type = type;
		this.y1=y1;
		this.x2=xmin;
		this.y2=xmax;
		this.color = Color.BLACK;
	}

	// Point
	public ElDraw(int type, double x1, double y1) {
		this.type = type;
		this.x1=x1;
		this.y1=y1;
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

	public static void drawElDrawItem(Graphics2D g2, ElDraw elDraw, double zoom){
		float rectWidth = 6;
		float rectHeight = 0.08f;

		g2.setPaint(elDraw.getColor());
		g2.setStroke(new BasicStroke(0.015f));

        if (elDraw.type == _LineHorzInfHP)
        	g2.draw( new Line2D.Double(elDraw.getX2(),elDraw.getY1(),elDraw.getY2(),elDraw.getY1()));			 	
        if (elDraw.type == _LineHorzInfBP)
        	g2.draw( new Line2D.Double(elDraw.getX2(),elDraw.getY1(),elDraw.getY2(),elDraw.getY1()));			 	
        if (elDraw.type == _PointYLog)
            g2.fill (new Ellipse2D.Double(elDraw.getX1()-rectWidth/zoom/2, elDraw.getY1()-rectHeight/zoom/2, rectWidth/zoom, rectHeight/zoom));
	}
		

	// -------------------------------------------------------
	// 						METHOD OTHERS
	// -------------------------------------------------------
	
	public static void pointYLog(Graphics2D g2, double x,double y, Color color){
		//Point
		float rectWidth = 6;
		float rectHeight = 0.08f;
		
        g2.setPaint(color);
        g2.fill (new Ellipse2D.Double(x-rectWidth/2, y-rectHeight/2, rectWidth, rectHeight));
	}

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

