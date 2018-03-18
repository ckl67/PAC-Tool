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
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
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

public class EnthalpyPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = 1L;	
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	/* -----------------------------
		      Instance Variables
	 * ----------------------------*/
	private Refrigerant refrigerant;		// Class Refrigerant to get Hmin, Hmax, ...

	private EnthalpyBkgImg enthalpyBkgImg;	// Class RefrigerantBkgdImg for Background image
	private BufferedImage bufBkgdImg;		// Refrigerant Image Background
	private float alphaBlurBkgdImg;			// Blur the Background image  

	private Point offset;					// Supplementary Offset
	private Point dragStart;				// Start point for Offset computation

	private double fHzoom;					// Zoom factor relative to the figure to display
	private double fPzoom;					// and the panel width

	private int xMargin;					// Supplementary margin on both sides of the display relative to the scale used !!
	private int yMargin;

	private double gridUnitH;				// Grid Refrigerant Step
	private double gridUnitP;				// Grid Pressure Step, will be modified following the progression 

	private double curveFollowerH;			// Curve follower
	private double curveFollowerP;	

	private double mvoYf;					// Move Y factor: Move the mouse of 50 pixels, will corresponds to 0.5 

	private List<EnthalpyElDraw> lEnthalpyElDraw;			// Draw elements: lines/points/...

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public EnthalpyPanel(Refrigerant vRefrigerant, EnthalpyBkgImg vimgRefrigerantBg, List<EnthalpyElDraw> vlEnthalpyElDraw) {
		super();

		this.refrigerant = vRefrigerant;

		logger.info(" Hmin={} Hmax={} Pmin={} Pmax={} ",refrigerant.getHmin(), refrigerant.getHmax(), refrigerant.getPmin(),refrigerant.getPmax() );

		this.enthalpyBkgImg = vimgRefrigerantBg;
		this.bufBkgdImg = openRefrigerantImageFile();
		this.alphaBlurBkgdImg=0.5f;

		this.offset = new Point(0,0);		
		this.dragStart = new Point(0,0);	

		this.fHzoom = 1.0;
		this.fPzoom	= 1.0;					

		this.xMargin = 50;
		this.yMargin = 50;

		this.gridUnitH = 20;	
		this.gridUnitP = 1;	 

		this.curveFollowerH=0.0;
		this.curveFollowerP=0.0;

		this.lEnthalpyElDraw = vlEnthalpyElDraw;

		this.addMouseListener(this);
		this.addMouseMotionListener(this);      
		this.addMouseWheelListener(this);

	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Compute PLog of P:   getLogP_P(5.0)
	 * @param P Pressure
	 * @return Log(P) 
	 */
	public double getLogP_P(double p) {
		return Math.log10(p);
	}


	/**
	 * Compute P of PLog
	 * @param plog
	 * @return p
	 */
	public double getP_LogP(double plog) {
		return Math.exp(plog*Math.log(10));		
	}

	/**
	 * Compute Enthalpy H / mouse coordinate
	 * @param xm
	 * @return H in double
	 */
	public double getHoXm(int xm) {
		double h=0;
		double Hmin = refrigerant.getHmin()*fHzoom;
		double Hmax = refrigerant.getHmax()*fHzoom;
		int xmin = xMargin;
		int xmax = this.getWidth() - xMargin;
		if ((xm>=xmin) || (xm<=xmax))
			h = ((xm -xmin - offset.x )*(double)(Hmax -Hmin)/(double)(xmax-xmin) + Hmin);
		else
			h = 0;
		return(h);
	}

	/**
	 * Compute mouse coordinate / Enthalpy H
	 * @param h
	 * @return xm position in integer
	 */
	public int getXmoH(double h) {
		double Hmin = refrigerant.getHmin()*fHzoom;
		double Hmax = refrigerant.getHmax()*fHzoom;
		double xd;
		int x;
		int xmin = (int) (xMargin);
		int xmax = (int) (this.getWidth() - (xMargin));

		xd = (h-Hmin)*(xmax-xmin)/(Hmax -Hmin) + xmin +offset.x;
		x = (int)(xd);
		return(x);
	}

	/**
	 * Compute Pressure P / mouse coordinate
	 * @param ym
	 * @return P in double
	 */
	public double getPoYm(int ym) {
		double p=0;
		double Pmin = refrigerant.getPmin()*fPzoom;
		double Pmax = refrigerant.getPmax()*fPzoom;
		int ymin = (int)0;
		int ymax = this.getHeight() - 2*yMargin;
		int y    = -ym + this.getHeight() - yMargin + offset.y ;

		if ((y>=ymin) || (y<=ymax))
			p = ((double)(y -ymin  )*(Pmax -Pmin)/(double)(ymax-ymin) + Pmin);
		else
			p = 0.0;
		return(p);

	}

	/**
	 * Compute Log(P) / mouse coordinate
	 * @param ym
	 * @return log(P) in double
	 */
	public double getLogPoYm(int ym) {
		double p=0;
		double logPmin = getLogP_P(refrigerant.getPmin())*fPzoom;
		double logPmax = getLogP_P(refrigerant.getPmax())*fPzoom;
		int ymin = (int)0;
		int ymax = this.getHeight() - 2*yMargin;
		int y    = -ym + this.getHeight() - yMargin + offset.y ;

		if ((y>=ymin) || (y<=ymax))
			p = ((double)(y -ymin  )*(logPmax -logPmin)/(double)(ymax-ymin) + logPmin);
		else
			p = 0.0;
		return(p);

	}


	/**
	 * Compute mouse coordinate / Pressure P
	 * @param P
	 * @return ym position in integer
	 */
	public int getYmoP(double p) {
		double Pmin = refrigerant.getPmin()*fPzoom;
		double Pmax = refrigerant.getPmax()*fPzoom;
		int ymin = 0;
		int ymax = this.getHeight() - 2*yMargin;
		double yd;
		int ym;

		yd = this.getHeight() - yMargin - (p-Pmin)*(ymax-ymin)/(Pmax -Pmin) - ymin + offset.y ;
		ym = (int)(yd);
		return(ym);
	}


	/**
	 * Compute mouse coordinate / LogP = Log(Pressure)
	 * @param logP
	 * @return ym position in integer
	 */
	public int getYmoLogP(double logP) {
		double logPmin = getLogP_P(refrigerant.getPmin())*fPzoom;
		double logPmax = getLogP_P(refrigerant.getPmax())*fPzoom;
		int ymin = 0;
		int ymax = this.getHeight() - 2*yMargin;
		double yd;
		int ym;

		yd = this.getHeight() - yMargin - (logP-logPmin)*(ymax-ymin)/(logPmax -logPmin) - ymin + offset.y ;
		ym = (int)(yd);
		return(ym);
	}


	/**
	 * Compute mouse coordinate / Log(P) = Log(Pressure)
	 * @param logP
	 * @return ym position in integer
	 */
	public int getYmoLog(double P) {
		double logP = getLogP_P(P);
		double logPmin = getLogP_P(refrigerant.getPmin())*fPzoom;
		double logPmax = getLogP_P(refrigerant.getPmax())*fPzoom;
		int ymin = 0;
		int ymax = this.getHeight() - 2*yMargin;
		double yd;
		int ym;

		yd = this.getHeight() - yMargin - (logP-logPmin)*(ymax-ymin)/(logPmax -logPmin) - ymin + offset.y ;
		ym = (int)(yd);
		return(ym);
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		// Move Curve
		if ((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
			offset.x = (evt.getX() - dragStart.x);
			offset.y = (evt.getY() - dragStart.y);
			repaint();
		}

	}

	@Override
	public void mousePressed(MouseEvent evt) {
		int xMouse = evt.getX();
		int yMouse = evt.getY();

		// Move Curve
		if ((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
			dragStart.x = xMouse-offset.x;
			dragStart.y = yMouse-offset.y;
		} 
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent evt) {
		// Zoom
		fHzoom += evt.getPreciseWheelRotation() * .03;
		fPzoom += evt.getPreciseWheelRotation() * .03;

		if (fHzoom < 0) fHzoom = 0.0;
		if (fPzoom < 0) fPzoom = 0.0;

		//gridUnitH = (int)( gridUnitH*fHzoom/10.0 )*10;
		//if (gridUnitH<1) gridUnitH = 1;
		logger.info(" fHzoom={}  gridUnitH ={} ",fHzoom, gridUnitH );
		logger.info(" fPzoom={}  gridUnitP ={} ",fPzoom, gridUnitP );

		repaint();
	}


	// -------------------------------------------------------
	// 						PAINT 
	// -------------------------------------------------------
	@Override
	public void paintComponent(Graphics g)	{
		Graphics2D g2 = (Graphics2D) g.create();

		double Hmin = refrigerant.getHmin();
		double Hmax = refrigerant.getHmax();
		double Pmin = refrigerant.getPmin();
		double Pmax = refrigerant.getPmax();

		super.paintComponent(g);
		setBackground(Color.WHITE);

		Font font;
		FontMetrics metrics;
		String s;
		
		font = new Font(null, Font.BOLD, 18);
		metrics= g.getFontMetrics(font);
		g2.setFont(font);
		g2.setColor(Color.BLUE);	
		s = String.format("%s",refrigerant.getRfgName());
		g2.drawString(s, this.getWidth() - 4*xMargin, yMargin/2);

		
		// -----------------------------------
		// Grid + Text
		// -----------------------------------
		font = new Font(null, Font.PLAIN, 10);
		metrics= g.getFontMetrics(font);
		g2.setFont(font);

		g2.setColor(Color.lightGray);	

		for (double h =  Hmin; h <= Hmax; h=(h+gridUnitH)) {
			g2.draw( new Line2D.Double(getXmoH(h),getYmoLog(Pmin),getXmoH(h),getYmoLog(Pmax)));
			
			s = String.format("%d",(int)h);
			int xd = (int) (getXmoH(h) - metrics.getStringBounds(s,g2).getWidth()/2); 
			g2.drawString(s, xd, this.getHeight() - yMargin/2);

		}

		double p;
		for (double pl = Pmin; pl < Pmax; pl= (pl+gridUnitP)) {
			if (pl>=1)
				p = (int)pl;
			else
				p = pl;

			// Check that the difference between 2 ym horizontal lines are > 2
			if ( getYmoLog(p) -  getYmoLog(p+gridUnitP) >= 2) 
			{
				// logger.info("(paintComponent):: p={} log(p)={} ym={}",p,getLogP_P(p),getYmoLog(p));

				if (p>=1)
					s = String.format("%.0f",p);
				else
					s = String.format("%.1f",p);
				int xd = (int) (xMargin/2 - metrics.getStringBounds(s,g2).getWidth()/2); 
				int yd = (int) (getYmoLog(p) - 2 + metrics.getStringBounds(s,g2).getHeight()/2);
				
				if (p<10) {	
					g2.draw( new Line2D.Double(getXmoH(Hmin),getYmoLog(p),getXmoH(Hmax),getYmoLog(p)));
					g2.drawString(s, xd, yd);
				} else {
					if (p%10 == 0) {
						g2.draw( new Line2D.Double(getXmoH(Hmin),getYmoLog(p),getXmoH(Hmax),getYmoLog(p)));
						g2.drawString(s, xd, yd);
					}
				}
			}
		}


	}

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
		lEnthalpyElDraw.clear();
		repaint();
	}

	/**
	 * Center the Image
	 */
	public void centerImg() {
		fHzoom =  1.0;
		fPzoom =  1.0;
		offset.x = 0;
		offset.y= 0;
		this.gridUnitH = 20;	
		this.gridUnitP = 1;	 

		repaint();
	}

	/**
	 * Will return the nearest element id of EnthalpyElDraw
	 * @param EnthalpyElDraw
	 * @param EloEnthalpyElDraw
	 * @param H 
	 * @param Log(P) 
	 * @return
	 */
	public int getIdNearest(List<EnthalpyElDraw> lEnthalpyElDraw, EloEnthalpyElDraw enthElDrawObject, double pH, double pP) {
		int id=-1;
		double zoneH = 2;
		double zoneP = 2;

		for(int i=0;i<lEnthalpyElDraw.size();i++) {
			if ( (lEnthalpyElDraw.get(i).getEnthElDrawObject() == enthElDrawObject) && (lEnthalpyElDraw.get(i).isMovable()) ){

				for(int j=0;j<lEnthalpyElDraw.get(i).getlElDraw().size();j++) {

					double H = lEnthalpyElDraw.get(i).getlElDraw().get(j).getX1();
					double P = lEnthalpyElDraw.get(i).getlElDraw().get(j).getY1();

					//logger.trace("(getIdNearest):: picked pP={}  Point P={}  Zoom={}", pP, P, zoom);

					if ( ( pH < H+zoneH/fHzoom) && ( pH > H-zoneH/fHzoom) && (pP < P+zoneP/fPzoom) && ( pP > P-zoneP/fPzoom) ) {
						id = i;
						logger.trace("(getIdNearest):: id={}",id);
					}
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

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------



	public float getAlphaBlurBkgdImg() {
		return alphaBlurBkgdImg;
	}

	public void setAlphaBlurBkgdImg(float imageAlphaBlur) {
		this.alphaBlurBkgdImg = imageAlphaBlur;
	}

	public void setCurveFollowerH(double h) {
		this.curveFollowerH = h;
	}

	public void setCurveFollowerP(double p) {
		this.curveFollowerP = p;
	}

	public void setBufBkgdImg(BufferedImage bufBkgdImg) {
		this.bufBkgdImg = bufBkgdImg;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
