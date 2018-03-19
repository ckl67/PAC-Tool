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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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

	private int xMargin;					// Supplementary margin on both sides of the display relative to the scale used !!
	private int yMargin;

	private double Hzoom;					// Zoom factor H
	private double Pzoom;					// Zoom factor P

	private double HgridUnit;				// Grid H unity
	private double PgridUnit;				// Grid P unity

	private double HcurveFollower;			// Curve follower
	private double PcurveFollower;	

	private List<EnthalpyElDraw> lEnthalpyElDraw;			// Draw elements: lines/points/...

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public EnthalpyPanel(Refrigerant vRefrigerant, EnthalpyBkgImg vimgRefrigerantBg, List<EnthalpyElDraw> vlEnthalpyElDraw) {
		super();

		this.refrigerant = vRefrigerant;

		logger.trace("(EnthalpyPanel):: Gas={} Hmin={} Hmax={} Pmin={} Pmax={} ",refrigerant.getRfgName(),refrigerant.getHmin(), refrigerant.getHmax(), refrigerant.getPmin(),refrigerant.getPmax() );

		this.enthalpyBkgImg = vimgRefrigerantBg;
		this.bufBkgdImg = openRefrigerantImageFile();
		this.alphaBlurBkgdImg=0.5f;

		this.offset = new Point(0,0);		
		this.dragStart = new Point(0,0);	

		this.Hzoom = 1.0;
		this.Pzoom	= 1.0;					

		this.xMargin = 50;
		this.yMargin = 50;

		this.HgridUnit = 20;	
		this.PgridUnit = 1;	 

		this.HcurveFollower=0.0;
		this.PcurveFollower=0.0;

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
		double Hmin = refrigerant.getHmin()*Hzoom;
		double Hmax = refrigerant.getHmax()*Hzoom;
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
		double Hmin = refrigerant.getHmin()*Hzoom;
		double Hmax = refrigerant.getHmax()*Hzoom;
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
		double Pmin = refrigerant.getPmin()*Pzoom;
		double Pmax = refrigerant.getPmax()*Pzoom;
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
		double logPmin = getLogP_P(refrigerant.getPmin())*Pzoom;
		double logPmax = getLogP_P(refrigerant.getPmax())*Pzoom;
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
		double Pmin = refrigerant.getPmin()*Pzoom;
		double Pmax = refrigerant.getPmax()*Pzoom;
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
		double logPmin = getLogP_P(refrigerant.getPmin())*Pzoom;
		double logPmax = getLogP_P(refrigerant.getPmax())*Pzoom;
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
		double logPmin = getLogP_P(refrigerant.getPmin())*Pzoom;
		double logPmax = getLogP_P(refrigerant.getPmax())*Pzoom;
		int ymin = 0;
		int ymax = this.getHeight() - 2*yMargin;
		double yd;
		int ym;

		yd = this.getHeight() - yMargin - (logP-logPmin)*(ymax-ymin)/(logPmax -logPmin) - ymin + offset.y ;
		ym = (int)(yd);
		return(ym);
	}

	// -------------------------------------------------------
	// 					MOUSE
	// -------------------------------------------------------

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
		Hzoom += evt.getPreciseWheelRotation() * .04;
		Pzoom += evt.getPreciseWheelRotation() * .04;

		if (Hzoom < 0) Hzoom = 0.0;
		if (Pzoom < 0) Pzoom = 0.0;

		logger.info(" Hzoom={}  HgridUnit ={} ",Hzoom, HgridUnit );
		logger.info(" Pzoom={}  PgridUnit ={} ",Pzoom, PgridUnit );

		repaint();
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
		int xd,yd;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// -----------------------------------
		// Font
		// -----------------------------------
		font = new Font(null, Font.BOLD, 18);
		metrics= g.getFontMetrics(font);
		g2.setFont(font);

		// -----------------------------------
		// GAS name
		// -----------------------------------
		g2.setColor(Color.BLUE);	
		s = String.format("%s",refrigerant.getRfgName());
		xd = (int) (this.getWidth() - xMargin - metrics.getStringBounds(s,g2).getWidth());
		yd = yMargin/2;
		g2.drawString(s, xd, yd);

		// -----------------------------------
		// Font
		// -----------------------------------
		font = new Font(null, Font.PLAIN, 10);
		metrics= g.getFontMetrics(font);
		g2.setFont(font);

		// -----------------------------------
		// Grid H
		// -----------------------------------
		for (double h =  Hmin; h <= Hmax; h=(h+HgridUnit)) {
			g2.setColor(Color.lightGray);	
			g2.draw( new Line2D.Double(getXmoH(h),getYmoLog(Pmin),getXmoH(h),getYmoLog(Pmax)));

			g2.setColor(Color.black);	
			s = String.format("%4d",(int)h);
			xd = (int) (getXmoH(h) - metrics.getStringBounds(s,g2).getWidth()/2); 
			yd = getYmoLog(Pmin) + yMargin/2;
			g2.drawString(s, xd, yd);
		}

		g2.setColor(Color.black);	
		s = String.format("%s","H(kJ/kg)");
		xd = (int) (getXmoH(Hmax)  + metrics.getStringBounds(s,g2).getWidth()/2); 
		yd = getYmoLog(Pmin)+ yMargin/2;
		g2.drawString(s, xd, yd);


		// -----------------------------------
		// Grid P
		// -----------------------------------
		double p;
		for (double pl = Pmin; pl < Pmax; pl= (pl+PgridUnit)) {
			if (pl>=1)
				p = (int)pl;
			else
				p = pl;

			// Check that the difference between 2 ym horizontal lines are > 2
			if ( getYmoLog(p) -  getYmoLog(p+PgridUnit) >= 2) 
			{
				// logger.info("(paintComponent):: p={} log(p)={} ym={}",p,getLogP_P(p),getYmoLog(p));

				if (p>=1)
					s = String.format("%4.0f",p);
				else
					s = String.format("%4.1f",p);

				xd = (int) (getXmoH(Hmin) - xMargin/2 - metrics.getStringBounds(s,g2).getWidth()/2); 
				yd = (int) (getYmoLog(p) - 2 + metrics.getStringBounds(s,g2).getHeight()/2);

				if (p<10) {	
					g2.setColor(Color.lightGray);	
					g2.draw( new Line2D.Double(getXmoH(Hmin)-4,getYmoLog(p),getXmoH(Hmax),getYmoLog(p)));

					g2.setColor(Color.black);	
					g2.drawString(s, xd, yd);
				} else {
					if (p%10 == 0) {
						g2.setColor(Color.lightGray);	
						g2.draw( new Line2D.Double(getXmoH(Hmin)-4,getYmoLog(p),getXmoH(Hmax),getYmoLog(p)));

						g2.setColor(Color.black);	
						g2.drawString(s, xd, yd);
					}
				}
			}
		}

		g2.setColor(Color.black);	
		s = String.format("%s","P(bar)");
		xd = (int) (getXmoH(Hmin) - xMargin/2 - metrics.getStringBounds(s,g2).getWidth()/2); 
		yd = (int) (getYmoLog(Pmax) - yMargin/2 + metrics.getStringBounds(s,g2).getHeight()/2);
		g2.drawString(s, xd, yd);


		// -----------------------------------
		// Grid T Liquid
		// -----------------------------------
		g2.setColor(Color.orange);	

		double TminL = (int)(refrigerant.getTSatFromP(Pmin).getTLiquid())/10*10;
		double TmaxL = refrigerant.getTSatFromP(Pmax).getTLiquid();


		//logger.info("(paintComponent):: TminL={} TmaxL={} ",TminL,TmaxL);

		for (double tl = TminL; tl < TmaxL; tl= (tl+10*PgridUnit)) {

			double pl = refrigerant.getPSatFromT(tl).getPLiquid();
			g2.draw( new Line2D.Double(getXmoH(Hmin)-4,getYmoLog(pl),getXmoH(Hmin)+4,getYmoLog(pl)));

			String sl = String.format("%4.0f",tl);
			xd = (int) (getXmoH(Hmin) + xMargin/2 - metrics.getStringBounds(s,g2).getWidth()/2); 
			yd = (int) (getYmoLog(pl) - 2 + metrics.getStringBounds(s,g2).getHeight()/2);
			g2.drawString(sl, xd, yd);
			//logger.info("(paintComponent):: pl={} t={} s={} ",pl,tl,sl);
		}

		s = String.format("%s","T(°C)");
		xd = (int) (getXmoH(Hmin) + xMargin/2 - metrics.getStringBounds(s,g2).getWidth()/2); 
		yd = (int) (getYmoLog(Pmax) - yMargin/2 + metrics.getStringBounds(s,g2).getHeight()/2);
		g2.drawString(s, xd, yd);

		// -----------------------------------
		// Grid T Gas
		// -----------------------------------
		double TminG = (int)(refrigerant.getTSatFromP(Pmin).getTGas())/10*10;
		double TmaxG = refrigerant.getTSatFromP(Pmax).getTGas();

		for (double tg = TminG; tg < TmaxG; tg= (tg+10*PgridUnit)) {

			double pg = refrigerant.getPSatFromT(tg).getPGas();
			g2.draw( new Line2D.Double(getXmoH(Hmax)-4,getYmoLog(pg),getXmoH(Hmax)+4,getYmoLog(pg)));

			String sg = String.format("%4.0f",tg);
			xd = (int) (getXmoH(Hmax) + xMargin/2 - metrics.getStringBounds(s,g2).getWidth()/2); 
			yd = (int) (getYmoLog(pg) - 2 + metrics.getStringBounds(s,g2).getHeight()/2);
			g2.drawString(sg, xd, yd);
		}

		s = String.format("%s","T(°C)");
		xd = (int) (getXmoH(Hmax) + xMargin/2 - metrics.getStringBounds(s,g2).getWidth()/2); 
		yd = (int) (getYmoLog(Pmax) - yMargin/2 + metrics.getStringBounds(s,g2).getHeight()/2);
		g2.drawString(s, xd, yd);


		// -----------------------------------
		// Curve
		// -----------------------------------		
		g2.setStroke(new BasicStroke((float) 1));
		g2.setColor(Color.RED);
		for(int i=1;i<refrigerant.getSatTableSize();i++) {
			
			g2.draw( new Line2D.Double(
					getXmoH(refrigerant.getHSat_Liquid(i-1)),
					getYmoLog(refrigerant.getPSat_Liquid(i-1)),
					getXmoH(refrigerant.getHSat_Liquid(i)),
					getYmoLog(refrigerant.getPSat_Liquid(i))
					));		
			
			g2.draw( new Line2D.Double(
					getXmoH(refrigerant.getHSat_Gas(i-1)),
					getYmoLog(refrigerant.getPSat_Gas(i-1)),
					getXmoH(refrigerant.getHSat_Gas(i)),
					getYmoLog(refrigerant.getPSat_Gas(i))
					));
		}
		
		
		// -----------------------------------
		// Draw All Elements
		// -----------------------------------
		for(int k=0;k<lEnthalpyElDraw.size();k++) {

			List<ElDraw> lElDraw = lEnthalpyElDraw.get(k).getlElDraw();

			for(int j=0;j<lElDraw.size();j++) {

				switch (lElDraw.get(j).getElDrawObj()) {
				case LINE_HORZ: 
					g2.setStroke(new BasicStroke((float)(2)));
					g2.setPaint(lElDraw.get(j).getColor());

					//g2.setPaint(Color.BLUE);
					int linexmin = getXmoH(refrigerant.getHmin());
					int linexmax = getXmoH(refrigerant.getHmax());
					int liney = getYmoLog(lElDraw.get(j).getY1());
					g2.draw( new Line2D.Double(linexmin,liney,linexmax,liney));
					break;

				case LINE:
					g2.setStroke(new BasicStroke(0.5f));
					g2.setPaint(lElDraw.get(j).getColor());
					g2.draw( new Line2D.Double(
							getXmoH(lElDraw.get(j).getX1()),
							getYmoLog(lElDraw.get(j).getY1()),
							getXmoH(lElDraw.get(j).getX2()),
							getYmoLog(lElDraw.get(j).getY2())
							)
							);
					//logger.trace("H1={} P1={}     H2={} P2={}  ",lElDraw.get(j).getX1(),lElDraw.get(j).getY1(),lElDraw.get(j).getX2(),lElDraw.get(j).getY2());
					break;

				case LINE_DASHED:
					g2.setStroke(
							new BasicStroke(
									0.2f,							// Width
									BasicStroke.CAP_BUTT,			// End cap
									BasicStroke.JOIN_MITER,			// Join style
									10.0f,							// Miter limit
									new float[] {16.0f,20.0f},		// Dash pattern
									0.0f							// Dash phase
									)
							);
					g2.setPaint(lElDraw.get(j).getColor());
					g2.draw( new Line2D.Double(
							getXmoH(lElDraw.get(j).getX1()),
							getYmoLog(lElDraw.get(j).getY1()),
							getXmoH(lElDraw.get(j).getX2()),
							getYmoLog(lElDraw.get(j).getY2())
							)
							);
					//logger.trace("H1={} P1={}     H2={} P2={}  ",lElDraw.get(j).getX1(),lElDraw.get(j).getY1(),lElDraw.get(j).getX2(),lElDraw.get(j).getY2());
					break;
				default:
					break;
				}
			}
		}


		for(int k=0;k<lEnthalpyElDraw.size();k++) {
			int Rm = 5;

			List<ElDraw> lElDraw = lEnthalpyElDraw.get(k).getlElDraw();

			for(int j=0;j<lElDraw.size();j++) {

				switch (lElDraw.get(j).getElDrawObj()) {
				case POINT:  
					// Circle
					int pointxm = getXmoH(lElDraw.get(j).getX1())-Rm;
					int pointym = getYmoLog(lElDraw.get(j).getY1())-Rm;
					int widthH = (2*Rm);
					int heightP = (2*Rm);

					g2.setColor(lElDraw.get(j).getColor());

					g2.setStroke(new BasicStroke((float)(2)));
					g2.draw (new Ellipse2D.Double(pointxm, pointym, widthH, heightP));

					// Road Sign
					if (lEnthalpyElDraw.get(k).isTextDisplayPositionAbove()) {
						// Road Sign above
						g2.setColor(Color.BLUE);
						g2.drawRoundRect(pointxm-5, pointym-20, 20, 15, 5, 5);
						g2.setColor(Color.WHITE);
						g2.fillRoundRect(pointxm-5, pointym-20, 20, 15, 5, 5);

						font = new Font("Courier", Font.PLAIN, 10);
						g2.setFont(font);
						g2.setColor(Color.BLUE);  
						s = String.format("%s",lEnthalpyElDraw.get(k).getTextDisplay());
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
						s = String.format("%s",lEnthalpyElDraw.get(k).getTextDisplay());
						g2.drawString(s, pointxm, pointym+30);
					}

					break;
				default:
					break;
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
		Hzoom =  1.0;
		Pzoom =  1.0;
		offset.x = 0;
		offset.y= 0;
		this.HgridUnit = 20;	
		this.PgridUnit = 1;	 

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

					if ( ( pH < H+zoneH/Hzoom) && ( pH > H-zoneH/Hzoom) && (pP < P+zoneP/Pzoom) && ( pP > P-zoneP/Pzoom) ) {
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

	public void setHCurveFollower(double h) {
		this.HcurveFollower = h;
	}

	public void setPCurveFollower(double p) {
		this.PcurveFollower = p;
	}

	public void setBufBkgdImg(BufferedImage bufBkgdImg) {
		this.bufBkgdImg = bufBkgdImg;
	}


}
