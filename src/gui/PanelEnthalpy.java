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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.List;

import javax.swing.JPanel;

import enthalpy.Enthalpy;
import enthalpy.EnthalpyBkgdImg;

// ===================================================================================================================
// ===================================================================================================================
//												JPANEL Display
// ===================================================================================================================
// ===================================================================================================================

public class PanelEnthalpy extends JPanel {

	private static final long serialVersionUID = 1L;	

	/* -----------------------------
		      Instance Variables
	 * ----------------------------*/
	private Enthalpy enthalpy;				// Class Enthalpy with definition 
	private EnthalpyBkgdImg enthalpyBkgdImg;// Class EnthalpyBkgdImg with location of Background image

	private double xHmin;  					//  Enthalpy Minimum of the range of values displayed.
	private double xHmax;    				//  Enthalpy Maximum of the range of value displayed.

	private double yPmin;  					// Pressure Minimum of the range of Pressure value
	private double yPmax;     				// Pressure Maximum of the range of Pressure value. 

	private double log10_yPmin; 			// Pressure Minimum of the range of values displayed. --> Math.log10(0.01) = -1
	private double log10_yPmax;     		// Pressure Maximum of the range of value displayed. --> Math.log10(100) = 2

	private BufferedImage bufBkgdImg;		// Enthalpy Image Background
	private float alphaBlurBkgdImg;			// Blur the Background image  

	private Point offset;					// Supplementary Offset
	private double mvoYf;					// Move Y factor: Move the mouse of 50 pixels, will corresponds to 0.5 
	private Point dragStart;				// Start point for Offset computation
	private double zoom;					// Supplementary Zoom Factor

	private double zoomx;
	private double zoomy;					// Zoom factor relative to the figure to display and the panel width

	private double marginx;					// Supplementary margin on both sides of the display relative to the scale used !!
	private double marginy;
	private double log10_marginy;

	private double gridUnitX;				// Grid Enthalpy Step
	private double gridUnitY;				// Grid Pressure Step, will be modified following the progression 

	private double curveFollowerX;			// Curve follower

	private double curveFollowerY;	

	private List<ElDraw> eDrawL;			// Draw elements: lines/points/...

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public PanelEnthalpy(Enthalpy vconfEnthalpy, List<ElDraw> veDrawL) {
		super();

		this.enthalpy = vconfEnthalpy;
		this.enthalpyBkgdImg = enthalpy.getEnthalpyBkgImage();
		this.xHmin = enthalpy.getxHmin();  				
		this.xHmax = enthalpy.getxHmax();    				
		this.yPmin = enthalpy.getyPmin();  
		this.yPmax = enthalpy.getyPmax();    
		this.log10_yPmin = Math.log10(yPmin);
		this.log10_yPmax = Math.log10(yPmax);

		this.bufBkgdImg = enthalpyBkgdImg.openEnthalpyImageFile();

		this.alphaBlurBkgdImg=0;

		this.offset = new Point(0,0);		
		this.mvoYf = 100.0;				 
		this.dragStart = new Point(0,0);	
		this.zoom = 1;					
		this.zoomx=1;
		this.zoomy=1;					

		this.marginx = 20;
		this.marginy = 3;
		this.log10_marginy = Math.log10(marginy);

		this.gridUnitX = 20;	
		this.gridUnitY = 1;	 

		this.curveFollowerX=0;
		this.curveFollowerY=0;

		this.eDrawL = veDrawL;

		// ----------------------
		// Inherit from Jpanel
		// ----------------------
		setBackground(Color.WHITE);

		//	EVENT LISTNER
		this.addMouseListener(new MouseAdapter() {
			// -------- Mouse Click pressed ! --------
			@Override
			public void mousePressed(MouseEvent evt) {
				int xMouse = evt.getX();
				int yMouse = evt.getY();

				// Move Curve
				if ((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
					dragStart.x = xMouse-offset.x;
					dragStart.y = yMouse-offset.y;
				} else {
					if ((evt.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {			
						ElDraw edraw = new ElDraw("Test", ElDrawObject.PointLogP, Color.RED,getHoXm(xMouse),Math.log10(getPoYm(yMouse)));
						eDrawL.add(edraw);
					}
				}
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			// -------- Mouse moved ! --------
			@Override
			public void mouseDragged(MouseEvent evt) {

				// Move Curve
				if ((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
					offset.x = (evt.getX() - dragStart.x);
					offset.y = (evt.getY() - dragStart.y);
					//repaint();
				}

			}
		} );


		this.addMouseWheelListener(new MouseWheelListener() {
			// -------- Mouse Wheel -------
			@Override
			public void mouseWheelMoved(MouseWheelEvent evt) {
				// Zoom
				zoom -= evt.getPreciseWheelRotation() * .03;
				if (zoom < 0) zoom = 0;
				//repaint();
			}
		} );

	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/** 
	 * Clean the screen + Clear the list of the draw elements
	 */
	public void clean() {
		eDrawL.clear();
		repaint();
	}

	/**
	 * Center the Image
	 */
	public void centerImg() {
		zoom =  1;
		offset.x = 0;
		offset.y= 0;
		repaint();
	}

	/**
	 * Will return the nearest element id of elDraw
	 * @param eDrawL
	 * @param ElDrawObject
	 * @param H 
	 * @param Log(P) 
	 * @return
	 */
	public int getIdNearest(List<ElDraw> eDrawL, ElDrawObject elDrawObject, double pH, double pP) {
		int id=-1;
		double zoneH = 2;
		double zoneP = 2;

		for(int i=0;i<eDrawL.size();i++) {
			if ( eDrawL.get(i).getElDrawObj() == elDrawObject) {
				double H = eDrawL.get(i).getX1();
				double Plog = eDrawL.get(i).getY1();
				double P = Math.exp( Plog  * Math.log(10));
				//System.out.println("picked pP = " + pP );
				//System.out.println("Point P  = " + P );
				//System.out.println("Zoom  = " + zoom );

				if ( ( pH < H+zoneH/zoom) && ( pH > H-zoneH/zoom) && (pP < P+zoneP/zoom) && ( pP > P-zoneP/zoom) ) {
					id = i;
				}
			}
		}
		return id;
	}

	/**
	 *  Set The image Blurring
	 * @param alpha
	 */
	public void setImageAlphaBlure(float alpha) {
		alphaBlurBkgdImg = alpha;
	}

	/**
	 * Compute Enthalpy H / mouse coordinate
	 * @param x
	 * @return H in double
	 */
	public double getHoXm(int x) {
		double xh;
		xh = (xHmin-xHmax-2*marginx)/(2*zoom) + x/zoomx + (xHmin+xHmax)/2 - offset.x ; 
		return xh;
	}

	/**
	 * Compute mouse coordinate / Enthalpy H
	 * @param h
	 * @return x position in integer
	 */
	public int getXmoH(double h) {
		double xm;
		xm = -((xHmin+xHmax-2*h-2*offset.x)*zoomx*zoom+(xHmin-xHmax-2*marginx)*zoomx)/(2*zoom);
		return (int)xm;
	}

	/**
	 * Compute Pressure P / mouse coordinate
	 * @param y
	 * @return P in double
	 */
	public double getPoYm(int y) {
		double yP;
		yP= (log10_yPmax-log10_yPmin)/(2*zoom)+log10_marginy/zoom-y/zoomy+(log10_yPmin+log10_yPmax)/2 + offset.y/mvoYf;
		yP = Math.exp(yP*Math.log(10));			
		return yP;
	}

	/**
	 * Compute mouse coordinate / Pressure P
	 * @param p
	 * @return y position in integer
	 */
	public int getYmoP(double p) {
		double ym;	
		ym=-(log10_yPmin*zoomy)/(2*zoom)+(log10_yPmax*zoomy)/(2*zoom)+(log10_marginy*zoomy)/zoom-(Math.log(p)*zoomy)/Math.log(10)+
				(offset.y*zoomy)/mvoYf+(log10_yPmin*zoomy)/2+(log10_yPmax*zoomy)/2;
		return (int)ym;
	}

	// -------------------------------------------------------
	// 						PAINT 
	// -------------------------------------------------------
	@Override
	public void paintComponent(Graphics g)	{
		Graphics2D g2 = (Graphics2D) g.create();

		super.paintComponent(g);
		setBackground(Color.WHITE);

		// -----------------------------------
		// Apply a translation so that the drawing
		// coordinates on the display matches the Panel
		// -----------------------------------
		zoomx = getWidth()/(xHmax-xHmin+2*marginx)*zoom;
		zoomy = getHeight()/(log10_yPmax-log10_yPmin+2*log10_marginy)*zoom;
		g2.translate(getWidth()/2,getHeight()/2);
		g2.scale(zoomx, -zoomy);
		g2.translate(offset.x-(xHmax+xHmin)/2, -offset.y/mvoYf-(log10_yPmax+log10_yPmin)/2);

		// -----------------------------------
		// Image
		// Background	--> Panel
		// -----------------------------------		

		g2.drawImage(bufBkgdImg, 
				enthalpyBkgdImg.getRefCurveH1x(),enthalpyBkgdImg.getRefCurveP2yLog(),enthalpyBkgdImg.getRefCurveH2x(),-enthalpyBkgdImg.getRefCurveP1yLog(),
				enthalpyBkgdImg.getiBgH1x(),enthalpyBkgdImg.getiBgP2y(),enthalpyBkgdImg.getiBgH2x(),enthalpyBkgdImg.getiBgP1y(),
				null);

		float[] scales = { 1f, 1f, 1f, alphaBlurBkgdImg };
		float[] offsets = new float[4];
		RescaleOp rop = new RescaleOp(scales, offsets, null);

		/* Draw the image, applying the filter */
		g2.drawImage(bufBkgdImg, rop, -1, -10);

		// -----------------------------------
		// Base font + Scaled font 
		// -----------------------------------
		Font font = new Font(null, Font.PLAIN, 1);
		Font fontReal = font.deriveFont(AffineTransform.getScaleInstance(1/zoomx, -1/zoomy));
		FontMetrics metrics;

		// -----------------------------------
		// Grid + Text
		// -----------------------------------

		// Enthalpy
		fontReal = fontReal.deriveFont(Font.PLAIN, 12.0f);
		g2.setFont(fontReal);
		metrics = g.getFontMetrics(fontReal);		

		g2.setStroke(new BasicStroke((float)0));
		int xposmax=0;
		for (int x = (int) xHmin; x <= (int)xHmax; x=(int)(x+gridUnitX)) {
			g2.setColor(Color.lightGray);	
			g2.draw( new Line2D.Double(x,log10_yPmin,x,log10_yPmax));	

			g2.setColor(Color.blue);
			String s = String.format("%d",x);
			int xd = (int) (x - metrics.getStringBounds(s,g2).getWidth()/2); 
			g2.drawString(s, (float)xd, (float)(log10_yPmin-log10_marginy/2));
			xposmax = x;
		}

		fontReal = fontReal.deriveFont(Font.PLAIN, 10.0f);
		g2.setFont(fontReal);
		metrics = g.getFontMetrics(fontReal);		
		g2.drawString("H(kJ/kg)", 
				(float) (xposmax-metrics.getStringBounds("H(kJ/kg)",g2).getWidth()/2), 
				(float) (log10_yPmin-log10_marginy+0.05 ));

		// Pressure
		fontReal = fontReal.deriveFont(Font.PLAIN, 10.0f);
		g2.setFont(fontReal);
		metrics = g.getFontMetrics(fontReal);		
		int yposmax=0;

		g2.setStroke(new BasicStroke(0));
		for (int y = (int) yPmin; y <= (int)yPmax; y= (int)(y+gridUnitY)) {
			if (y < 6) 
				gridUnitY = 1;
			else if ((y>=6) && (y<10) )
				gridUnitY = 2;
			else if ((y>=10) && (y<30) )
				gridUnitY = 5;
			else if ((y>=30) && (y<50) )
				gridUnitY = 10;
			else
				gridUnitY = 20;

			double log10_y = Math.log10(y); 
			g2.setColor(Color.lightGray);	
			g2.draw( new Line2D.Double(xHmin-2,log10_y,xHmax,log10_y));

			g2.setColor(Color.blue);
			String s = String.format("%d",y);
			double xd = xHmin-marginx/2 - metrics.getStringBounds(s,g2).getWidth()/2; 
			g2.setColor(Color.blue);
			g2.drawString(s, (float)(xd), (float)(log10_y));
			yposmax = y;
		}
		g2.drawString("P(bar)", 
				(float)(xHmin-marginx/2 - metrics.getStringBounds("P(bar)",g2).getWidth()/2), 
				(float)(Math.log10(yposmax+gridUnitY)));

		// -----------------------------------
		// Other Text, Abscissa /Ordinate
		// -----------------------------------
		g2.setStroke(new BasicStroke(0));

		// Title
		fontReal = fontReal.deriveFont(Font.BOLD, 16.0f);
		g2.setFont(fontReal);
		metrics = g.getFontMetrics(fontReal);		
		g2.drawString(enthalpy.getNameRefrigerant(), 
				(float)((xHmax-marginx - metrics.getStringBounds(enthalpy.getNameRefrigerant(),g2).getWidth())), 
				(float)(log10_yPmax+log10_marginy/2));

		// Temperature
		fontReal = fontReal.deriveFont(Font.PLAIN, 10.0f);
		g2.setFont(fontReal);
		metrics = g.getFontMetrics(fontReal);		
		g2.setColor(Color.red);
		for (int y = (int) enthalpy.convP2T(yPmin); y <= (int)enthalpy.convP2T(yPmax); y= (int)(y+gridUnitY)) {
			if (y < 60) 
				gridUnitY = 10;
			else
				gridUnitY = 20;

			String s = String.format("%d",y);

			double xd = 10 + xHmin - metrics.getStringBounds(s,g2).getWidth()/2; 
			double log10_y = Math.log10(enthalpy.convT2P(y)); 
			g2.drawString(s, (float)(xd), (float)(log10_y));

			g2.draw( new Line2D.Double(xHmin-2,log10_y,xHmin+2,log10_y));
		}
		g2.drawString("T(°C)", (float)(10 + xHmin - metrics.getStringBounds("T(°C)",g2).getWidth()/2), (float)(Math.log10(yposmax+gridUnitY)));

		// -----------------------------------
		// Curve
		// -----------------------------------		
		g2.setStroke(new BasicStroke((float) 0));
		g2.setColor(Color.red);
		for(int i=1;i<enthalpy.getlistSatHlP().size();i++) {
			g2.draw( new Line2D.Double(enthalpy.getSatHl(i-1),Math.log10(enthalpy.getSatP(i-1)),enthalpy.getSatHl(i),Math.log10(enthalpy.getSatP(i))));			 
			g2.draw( new Line2D.Double(enthalpy.getSatHv(i-1),Math.log10(enthalpy.getSatP(i-1)),enthalpy.getSatHv(i),Math.log10(enthalpy.getSatP(i))));
		}

		// -----------------------------------
		// Draw All Elements
		// -----------------------------------
		for(int i=0;i<eDrawL.size();i++) {

			switch (eDrawL.get(i).getElDrawObj()) {
			case LineHorzHPLogP: case LineHorzBPLogP:
				g2.setStroke(new BasicStroke((float)(0.02/zoom)));
				g2.setPaint(Color.BLUE);
				g2.draw( new Line2D.Double(enthalpy.getxHmin(),eDrawL.get(i).getY1(),enthalpy.getxHmax(),eDrawL.get(i).getY1()));
				break;
			default:
				break;
			}
		}

		float Rm = 5;

		for(int i=0;i<eDrawL.size();i++) {

			switch (eDrawL.get(i).getElDrawObj()) {
			case PointLogP: case PointHPLogP: case PointBPLogP:  
				double H0 = eDrawL.get(i).getX1() - Rm/zoomx;
				double widthH = (2*Rm)/zoomx;
				double P0 = (eDrawL.get(i).getY1()*zoomy+Math.log(10)-Rm)/zoomy;
				double heightP = (2*Rm)/zoomy;

				g2.setStroke(new BasicStroke((float)(0.02/zoom)));
				g2.setColor(Color.RED);
				//g2.setColor(eDrawL.get(i).getColor());
				g2.draw (new Ellipse2D.Double(H0, P0, widthH, heightP));
				g2.setColor(Color.BLACK);
				g2.fill (new Ellipse2D.Double(H0, P0, widthH, heightP));

				break;
			default:
				break;
			}

			//System.out.println(eDrawL.get(i).getType() + "  " + eDrawL.get(i).getY1());
		}

		// -----------------------------------
		// Follow the graph based on Eclipse
		// The Ellipse2D class define an ellipse that is defined by a framing rectangle
		// -----------------------------------
		if (curveFollowerY>0) {
			g2.setStroke(new BasicStroke(0.03f));
			g2.setPaint(Color.BLUE);
			double H0 = curveFollowerX - Rm/zoomx;
			double widthH = (2*Rm)/zoomx;
			double P0 = (Math.log10(curveFollowerY)*zoomy+Math.log(10)-Rm)/zoomy;
			double heightP = (2*Rm)/zoomy;
			g2.fill (new Ellipse2D.Double(H0, P0, widthH, heightP));
		}
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public double getXmin() {
		return xHmin;
	}

	public double getXmax() {
		return xHmax;
	}

	public float getAlphaBlurBkgdImg() {
		return alphaBlurBkgdImg;
	}

	public void setAlphaBlurBkgdImg(float imageAlphaBlur) {
		this.alphaBlurBkgdImg = imageAlphaBlur;
	}

	public void setCurveFollowerX(double curveFollowerX) {
		this.curveFollowerX = curveFollowerX;
	}

	public void setCurveFollowerY(double curveFollowerY) {
		this.curveFollowerY = curveFollowerY;
	}

}
