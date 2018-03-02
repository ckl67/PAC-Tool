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

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
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
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import refrigerant.Refrigerant;

// ===================================================================================================================
// ===================================================================================================================
//												JPANEL Display
// ===================================================================================================================
// ===================================================================================================================

public class EnthalpyPanel extends JPanel {

	private static final long serialVersionUID = 1L;	
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	/* -----------------------------
		      Instance Variables
	 * ----------------------------*/
	private Refrigerant refrigerant;		// Class Refrigerant with definition 
	private EnthalpyBkgImg enthalpyBkgImg;	// Class RefrigerantBkgdImg with location of Background image

	private double xHmin;  					//  Refrigerant Minimum of the range of values displayed.
	private double xHmax;    				//  Refrigerant Maximum of the range of value displayed.

	private double yPmin;  					// Pressure Minimum of the range of Pressure value
	private double yPmax;     				// Pressure Maximum of the range of Pressure value. 

	private double log10_yPmin; 			// Pressure Minimum of the range of values displayed. --> Math.log10(0.01) = -1
	private double log10_yPmax;     		// Pressure Maximum of the range of value displayed. --> Math.log10(100) = 2

	private BufferedImage bufBkgdImg;		// Refrigerant Image Background

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

	private double gridUnitX;				// Grid Refrigerant Step
	private double gridUnitY;				// Grid Pressure Step, will be modified following the progression 

	private double curveFollowerX;			// Curve follower

	private double curveFollowerY;	

	private List<ElDraw> eDrawL;			// Draw elements: lines/points/...

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public EnthalpyPanel(Refrigerant vRefrigerant, EnthalpyBkgImg vimgRefrigerantBg, List<ElDraw> veDrawL) {
		super();

		logger.info("PanelRefrigerant");

		this.refrigerant = vRefrigerant;
		this.enthalpyBkgImg = vimgRefrigerantBg;
		this.xHmin = refrigerant.getxHmin();  				
		this.xHmax = refrigerant.getxHmax();    				
		this.yPmin = refrigerant.getyPmin();  
		this.yPmax = refrigerant.getyPmax();    
		this.log10_yPmin = Math.log10(yPmin);
		this.log10_yPmax = Math.log10(yPmax);

		this.bufBkgdImg = openRefrigerantImageFile();

		this.alphaBlurBkgdImg=0.5f;

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
				} 
				/*
				else {
					if ((evt.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {			
						ElDraw edraw = new ElDraw("Test", EloElDraw.PointLogP, Color.RED,getHoXm(xMouse),Math.log10(getPoYm(yMouse)));
						eDrawL.add(edraw);
					}
				}
				 */
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
					repaint();
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
				repaint();
			}
		} );

	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Load the RefrigerantImageFile
	 */
	public BufferedImage openRefrigerantImageFile() {
		BufferedImage image=null;
		
		try {
			File file = new File(enthalpyBkgImg.getRefrigerantImageFile());
			logger.info("Read File: {}", enthalpyBkgImg.getRefrigerantImageFile());

			image = ImageIO.read(file);	
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace(); 
		}
		return image;
	}
	
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
	 * @param EloElDraw
	 * @param H 
	 * @param Log(P) 
	 * @return
	 */
	public int getIdNearest(List<ElDraw> eDrawL, EloElDraw eloElDraw, double pH, double pP) {
		int id=-1;
		double zoneH = 2;
		double zoneP = 2;

		for(int i=0;i<eDrawL.size();i++) {
			if ( (eDrawL.get(i).getElDrawObj() == eloElDraw) && (eDrawL.get(i).isMovable()) ){
				double H = eDrawL.get(i).getX1();
				double P = eDrawL.get(i).getY1();
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
	 * Compute Refrigerant H / mouse coordinate
	 * @param x
	 * @return H in double
	 */
	public double getHoXm(int x) {
		double xh;
		xh = (xHmin-xHmax-2*marginx)/(2*zoom) + x/zoomx + (xHmin+xHmax)/2 - offset.x ; 
		return xh;
	}

	/**
	 * Compute mouse coordinate / Refrigerant H
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

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

		// -----------------------------------
		// Apply a translation so that the drawing
		// coordinates on the display matches the Panel
		// -----------------------------------
		zoomx = getWidth()/(xHmax-xHmin+2*marginx)*zoom;
		zoomy = getHeight()/(log10_yPmax-log10_yPmin+2*log10_marginy)*zoom;

		AffineTransform g2AfT = new AffineTransform();
		AffineTransform sg2AfT = g2.getTransform();

		g2AfT.translate(getWidth()/2,getHeight()/2);
		g2AfT.scale(zoomx, -zoomy);
		g2AfT.translate(offset.x-(xHmax+xHmin)/2, -offset.y/mvoYf-(log10_yPmax+log10_yPmin)/2);
		g2.transform(g2AfT);

		// -----------------------------------
		// Image
		// Background	--> Panel
		// -----------------------------------		

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaBlurBkgdImg));
		g2.drawImage(bufBkgdImg, 
				enthalpyBkgImg.getRefCurveH1x(),enthalpyBkgImg.getRefCurveP2yLog(),enthalpyBkgImg.getRefCurveH2x(),-enthalpyBkgImg.getRefCurveP1yLog(),
				enthalpyBkgImg.getiBgH1x(),enthalpyBkgImg.getiBgP2y(),enthalpyBkgImg.getiBgH2x(),enthalpyBkgImg.getiBgP1y(),
				this);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));

		// -----------------------------------
		// Base font + Scaled font 
		// -----------------------------------
		Font font = new Font(null, Font.PLAIN, 1);
		Font fontReal = font.deriveFont(AffineTransform.getScaleInstance(1/zoomx, -1/zoomy));
		FontMetrics metrics;

		// -----------------------------------
		// Grid + Text
		// -----------------------------------

		// Refrigerant
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
		g2.drawString(refrigerant.getRfgName(), 
				(float)((xHmax-marginx - metrics.getStringBounds(refrigerant.getRfgName(),g2).getWidth())), 
				(float)(log10_yPmax+log10_marginy/2));

		// Temperature
		fontReal = fontReal.deriveFont(Font.PLAIN, 10.0f);
		g2.setFont(fontReal);
		metrics = g.getFontMetrics(fontReal);		
		g2.setColor(Color.red);
		for (int y = (int)refrigerant.getTSatFromP(yPmin).getTLiquid(); 
				 y <= (int)refrigerant.getTSatFromP(yPmax).getTLiquid(); 
				y= (int)(y+gridUnitY)) {
			if (y < 60) 
				gridUnitY = 10;
			else
				gridUnitY = 20;

			String s = String.format("%d",y);

			double xd = 10 + xHmin - metrics.getStringBounds(s,g2).getWidth()/2; 
			double log10_y = Math.log10(refrigerant.getTSatFromP(y).getTLiquid()); 
			g2.drawString(s, (float)(xd), (float)(log10_y));

			g2.draw( new Line2D.Double(xHmin-2,log10_y,xHmin+2,log10_y));
		}
		g2.drawString("T(�C)", (float)(10 + xHmin - metrics.getStringBounds("T(�C)",g2).getWidth()/2), (float)(Math.log10(yposmax+gridUnitY)));

		// -----------------------------------
		// Curve
		// -----------------------------------		
		g2.setStroke(new BasicStroke((float) 0));
		g2.setColor(Color.RED);
		for(int i=1;i<refrigerant.getSatTableSize();i++) {
			g2.draw( new Line2D.Double(refrigerant.getHSat_Liquid(i-1),Math.log10(refrigerant.getPSat_Liquid(i-1)),refrigerant.getHSat_Liquid(i),Math.log10(refrigerant.getPSat_Liquid(i))));			 
			g2.draw( new Line2D.Double(refrigerant.getHSat_Gas(i-1),Math.log10(refrigerant.getPSat_Gas(i-1)),refrigerant.getHSat_Gas(i),Math.log10(refrigerant.getPSat_Gas(i))));
		}

		// -----------------------------------
		// Follow the graph based on Eclipse
		// The Ellipse2D class define an ellipse that is defined by a framing rectangle
		// -----------------------------------
		if (curveFollowerY>0) {
			float Rm = 4;

			g2.setStroke(new BasicStroke(0.03f));
			g2.setPaint(Color.BLUE);
			double H0 = curveFollowerX - Rm/zoomx;
			double widthH = (2*Rm)/zoomx;
			double P0 = (Math.log10(curveFollowerY)*zoomy+Math.log(10)-Rm)/zoomy;
			double heightP = (2*Rm)/zoomy;
			g2.fill (new Ellipse2D.Double(H0, P0, widthH, heightP));
		}

		// ===================================
		// REVERT RESTORE ORIGINAL TRANSFORM
		// ===================================
		g2.setTransform(sg2AfT);

		// -----------------------------------
		// Draw All Elements
		// -----------------------------------
		for(int i=0;i<eDrawL.size();i++) {

			switch (eDrawL.get(i).getElDrawObj()) {
			case LINE_HORZ: 
				g2.setStroke(new BasicStroke((float)(2)));
				g2.setPaint(Color.BLUE);
				int linexmin = getXmoH(refrigerant.getxHmin());
				int linexmax = getXmoH(refrigerant.getxHmax());
				int liney = getYmoP(eDrawL.get(i).getY1());
				g2.draw( new Line2D.Double(linexmin,liney,linexmax,liney));
				break;
			default:
				break;
			}
		}


		for(int i=0;i<eDrawL.size();i++) {
			int Rm = 5;

			switch (eDrawL.get(i).getElDrawObj()) {
			case POINT_TXT_ABV: case POINT_TXT_BLV:  
				// Circle
				int pointxm = getXmoH(eDrawL.get(i).getX1())-Rm;
				int pointym = getYmoP(eDrawL.get(i).getY1())-Rm;
				int widthH = (2*Rm);
				int heightP = (2*Rm);

				if (eDrawL.get(i).isMovable())
					g2.setColor(Color.GREEN);
				else
					g2.setColor(Color.RED);

				g2.setStroke(new BasicStroke((float)(2)));
				g2.draw (new Ellipse2D.Double(pointxm, pointym, widthH, heightP));

				// Road Sign
				if (eDrawL.get(i).getElDrawObj() == EloElDraw.POINT_TXT_ABV) {
					// Road Sign above
					g2.setColor(Color.BLUE);
					g2.drawRoundRect(pointxm-5, pointym-20, 20, 15, 5, 5);
					g2.setColor(Color.WHITE);
					g2.fillRoundRect(pointxm-5, pointym-20, 20, 15, 5, 5);

					font = new Font("Courier", Font.PLAIN, 10);
					g2.setFont(font);
					g2.setColor(Color.BLUE);  
					String s = String.format("%s",eDrawL.get(i).getEnsembleName());
					g2.drawString(s, pointxm, pointym-10);
				} else {
					// Road Sign below
					g2.setColor(Color.BLUE);
					g2.drawRoundRect(pointxm-5, pointym+20, 20, 15, 5, 5);
					g2.setColor(Color.WHITE);
					g2.fillRoundRect(pointxm-5, pointym+20, 20, 15, 5, 5);

					font = new Font("Courier", Font.PLAIN, 10);
					g2.setFont(font);
					g2.setColor(Color.BLUE);  
					String s = String.format("%s",eDrawL.get(i).getEnsembleName());
					g2.drawString(s, pointxm, pointym+30);
				}

				break;
			default:
				break;
			}
		}

		// TO DOOOOOOOOOOOOOOOOOOOOOOOOO

		//WinRefrigerant.updateAllTextField();

	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public void setxHmin(double xHmin) {
		this.xHmin = xHmin;
	}

	public void setxHmax(double xHmax) {
		this.xHmax = xHmax;
	}

	public void setyPmin(double yPmin) {
		this.yPmin = yPmin;
	}

	public void setyPmax(double yPmax) {
		this.yPmax = yPmax;
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

	public void setBufBkgdImg(BufferedImage bufBkgdImg) {
		this.bufBkgdImg = bufBkgdImg;
	}
	
}
