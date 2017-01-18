package pacp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.List;

import javax.swing.JPanel;

// ===================================================================================================================
// ===================================================================================================================
//												JPANEL Display
// ===================================================================================================================
// ===================================================================================================================

public class PanelEnthalpy extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;	

	/* -----------------------------
		      Instance Variables
	 * ----------------------------*/
	private Enthalpy enthalpy;				// Class Enthalpy with definition 
	private EnthalpyBkgdImg enthalpyBkgdImg;// Class EnthalpyBkgdImg with location of Background imagz

	private BufferedImage enthBkgdImg;		// Enthalpy Image Background
	private float imageAlphaBlur;			// Blur the Background image  

	private Point offset;					// Supplementary Offset
	private double mvoYf;					// Move Y factor: Move the mouse of 50 pixels, will corresponds to 0.5 
	private Point dragStart;				// Start point for Offset computation
	private double zoom;					// Supplementary Zoom Factor

	private double zoomx;
	private double zoomy;					// Zoom factor relative to the figure to display and the panel width

	private double xmin;  					//  Enthalpy Minimum of the range of values displayed.
	private double xmax;    				//  Enthalpy Maximum of the range of value displayed.

	private double ymin;  					// Pressure Minimum of the range of Pressure value
	private double ymax;     				// Pressure Maximum of the range of Pressure value. 
	private double log10_ymin; 				// Pressure Minimum of the range of values displayed. --> Math.log10(0.01) = -1
	private double log10_ymax;     			// Pressure Maximum of the range of value displayed. --> Math.log10(100) = 2

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
	public PanelEnthalpy(Enthalpy vconfEnthalpy, List<ElDraw> veDrawL, MouseMotionListener ml) {
		enthalpy = vconfEnthalpy;
		enthalpyBkgdImg = enthalpy.getEnthalpyImage();

		enthBkgdImg = enthalpyBkgdImg.openEnthalpyImageFile();
		imageAlphaBlur=0;

		offset = new Point(0,0);		
		mvoYf = 100.0;				 
		dragStart = new Point(0,0);	
		zoom = 1;					
		zoomx=1;
		zoomy=1;					

		xmin = 140;  				
		xmax = 520;    				

		ymin = 0.5;  
		ymax = 60;    
		log10_ymin = Math.log10(ymin);
		log10_ymax = Math.log10(ymax);

		marginx = 20;
		marginy = 3;
		log10_marginy = Math.log10(marginy);

		gridUnitX = 20;	
		gridUnitY = 1;	 

		curveFollowerX=0;
		curveFollowerY=0;

		eDrawL = veDrawL;

		setBackground(Color.WHITE);
		addMouseWheelListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		addMouseMotionListener(ml);
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
	 * @param pH 
	 * @param pP --> Is Pressure (not Log value)
	 * @param zoom
	 * @return
	 */
	public int getIdNearest(List<ElDraw> eDrawL, double pH, double pP, double zoom) {
		int id=-1;
		double zoneH = 2;
		double zoneP = 2;

		for(int i=0;i<eDrawL.size();i++) {

			if ( eDrawL.get(i).getType() == ElDraw._PointYLog) {
				double H = eDrawL.get(i).getX1();
				double P = Math.exp(  eDrawL.get(i).getY1() * Math.log(10)) ;
				if ( ( pH < (H+zoneH)/zoom) && ( pH > (H-zoneH)/zoom) && (pP < (P+zoneP)/zoom) && ( pP > (P-zoneP)/zoom) ) {
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
		imageAlphaBlur = alpha;
	}

	/**
	 * Compute Enthalpy H / mouse coordinate
	 * @param x
	 * @return H in double
	 */
	public double getHoXm(int x) {
		double xh;
		xh = (xmin-xmax-2*marginx)/(2*zoom) + x/zoomx + (xmin+xmax)/2 - offset.x ; 
		return xh;
	}

	/**
	 * Compute mouse coordinate / Enthalpy H
	 * @param h
	 * @return x position in integer
	 */
	public int getXmoH(double h) {
		double xm;
		xm = -((xmin+xmax-2*h-2*offset.x)*zoomx*zoom+(xmin-xmax-2*marginx)*zoomx)/(2*zoom);
		return (int)xm;
	}

	/**
	 * Compute Pressure P / mouse coordinate
	 * @param y
	 * @return P in double
	 */
	public double getPoYm(int y) {
		double yP;
		yP= (log10_ymax-log10_ymin)/(2*zoom)+log10_marginy/zoom-y/zoomy+(log10_ymin+log10_ymax)/2 + offset.y/mvoYf;
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
		ym=-(log10_ymin*zoomy)/(2*zoom)+(log10_ymax*zoomy)/(2*zoom)+(log10_marginy*zoomy)/zoom-(Math.log(p)*zoomy)/Math.log(10)+
				(offset.y*zoomy)/mvoYf+(log10_ymin*zoomy)/2+(log10_ymax*zoomy)/2;
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
		zoomx = getWidth()/(xmax-xmin+2*marginx)*zoom;
		zoomy = getHeight()/(log10_ymax-log10_ymin+2*log10_marginy)*zoom;
		g2.translate(getWidth()/2,getHeight()/2);
		g2.scale(zoomx, -zoomy);
		g2.translate(offset.x-(xmax+xmin)/2, -offset.y/mvoYf-(log10_ymax+log10_ymin)/2);

		// -----------------------------------
		// Image
		// Background	--> Panel
		// -----------------------------------		

		g2.drawImage(enthBkgdImg, 
				enthalpyBkgdImg.getRefCurveH1x(),enthalpyBkgdImg.getRefCurveP2yLog(),enthalpyBkgdImg.getRefCurveH2x(),-enthalpyBkgdImg.getRefCurveP1yLog(),
				enthalpyBkgdImg.getiBgH1x(),enthalpyBkgdImg.getiBgP2y(),enthalpyBkgdImg.getiBgH2x(),enthalpyBkgdImg.getiBgP1y(),
				null);

		float[] scales = { 1f, 1f, 1f, imageAlphaBlur };
		float[] offsets = new float[4];
		RescaleOp rop = new RescaleOp(scales, offsets, null);

		/* Draw the image, applying the filter */
		g2.drawImage(enthBkgdImg, rop, -1, -10);

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
		for (int x = (int) xmin; x <= (int)xmax; x=(int)(x+gridUnitX)) {
			g2.setColor(Color.lightGray);	
			g2.draw( new Line2D.Double(x,log10_ymin,x,log10_ymax));	

			g2.setColor(Color.blue);
			String s = String.format("%d",x);
			int xd = (int) (x - metrics.getStringBounds(s,g2).getWidth()/2); 
			g2.drawString(s, (float)xd, (float)(log10_ymin-log10_marginy/2));
			xposmax = x;
		}

		fontReal = fontReal.deriveFont(Font.PLAIN, 10.0f);
		g2.setFont(fontReal);
		metrics = g.getFontMetrics(fontReal);		
		g2.drawString("H(kJ/kg)", 
				(float) (xposmax-metrics.getStringBounds("H(kJ/kg)",g2).getWidth()/2), 
				(float) (log10_ymin-log10_marginy+0.05 ));

		// Pressure
		fontReal = fontReal.deriveFont(Font.PLAIN, 10.0f);
		g2.setFont(fontReal);
		metrics = g.getFontMetrics(fontReal);		
		int yposmax=0;

		g2.setStroke(new BasicStroke(0));
		for (int y = (int) ymin; y <= (int)ymax; y= (int)(y+gridUnitY)) {
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
			g2.draw( new Line2D.Double(xmin-2,log10_y,xmax,log10_y));

			g2.setColor(Color.blue);
			String s = String.format("%d",y);
			double xd = xmin-marginx/2 - metrics.getStringBounds(s,g2).getWidth()/2; 
			g2.setColor(Color.blue);
			g2.drawString(s, (float)(xd), (float)(log10_y));
			yposmax = y;
		}
		g2.drawString("P(bar)", 
				(float)(xmin-marginx/2 - metrics.getStringBounds("P(bar)",g2).getWidth()/2), 
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
				(float)((xmax-marginx - metrics.getStringBounds(enthalpy.getNameRefrigerant(),g2).getWidth())), 
				(float)(log10_ymax+log10_marginy/2));

		// Temperature
		fontReal = fontReal.deriveFont(Font.PLAIN, 10.0f);
		g2.setFont(fontReal);
		metrics = g.getFontMetrics(fontReal);		
		g2.setColor(Color.red);
		for (int y = (int) enthalpy.convP2T(ymin); y <= (int)enthalpy.convP2T(ymax); y= (int)(y+gridUnitY)) {
			if (y < 60) 
				gridUnitY = 10;
			else
				gridUnitY = 20;

			String s = String.format("%d",y);

			double xd = 10 + xmin - metrics.getStringBounds(s,g2).getWidth()/2; 
			double log10_y = Math.log10(enthalpy.convT2P(y)); 
			g2.drawString(s, (float)(xd), (float)(log10_y));

			g2.draw( new Line2D.Double(xmin-2,log10_y,xmin+2,log10_y));
		}
		g2.drawString("T(�C)", (float)(10 + xmin - metrics.getStringBounds("T(�C)",g2).getWidth()/2), (float)(Math.log10(yposmax+gridUnitY)));


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
			ElDraw.drawElDrawItem(g2, eDrawL.get(i),zoom);
		}

		// -----------------------------------
		// Follow the graph based on Eclipse
		// The Ellipse2D class define an ellipse that is defined by a framing rectangle
		// -----------------------------------
		g2.setStroke(new BasicStroke(0.03f));
		ElDraw.pointYLog(g2,curveFollowerX,Math.log10(curveFollowerY),Color.BLUE);

	}

	// -------------------------------------------------------
	// 						EVENT LISTNER
	// -------------------------------------------------------

	// Mouse pressed : Left / Middle / Right
	@Override
	public void mousePressed(MouseEvent evt) {
		int xMouse = evt.getX();
		int yMouse = evt.getY();

		// Middle
		if ((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
			dragStart.x = xMouse-offset.x;
			dragStart.y = yMouse-offset.y;
		} else {
			if ((evt.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {			
				ElDraw edraw = new ElDraw(ElDraw._PointYLog,getHoXm(xMouse),Math.log10(getPoYm(yMouse)));
				eDrawL.add(edraw);
				this.repaint();
			}
		}
	}

	// Mouse dragged
	@Override
	public void mouseDragged(MouseEvent evt) {
		if ((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
			offset.x = (evt.getX() - dragStart.x);
			offset.y = (evt.getY() - dragStart.y);
			this.repaint();
		}
	}

	// Mouse Wheel moved
	@Override
	public void mouseWheelMoved(MouseWheelEvent evt) {
		zoom -= evt.getPreciseWheelRotation() * .03;
		if (zoom < 0) zoom = 0;
		this.repaint();
	}

	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public double getXmin() {
		return xmin;
	}

	public double getXmax() {
		return xmax;
	}

	public float getImageAlphaBlure() {
		return imageAlphaBlur;
	}

	public void setImageAlphaBlureBlurring(float imageAlphaBlur) {
		this.imageAlphaBlur = imageAlphaBlur;
	}

	public void setCurveFollowerX(double curveFollowerX) {
		this.curveFollowerX = curveFollowerX;
	}

	public void setCurveFollowerY(double curveFollowerY) {
		this.curveFollowerY = curveFollowerY;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
