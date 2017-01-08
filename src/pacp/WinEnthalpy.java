/*
 * - PAC-Tool - 
 * Tool for understanding basics and computation of PAC (Pompe � Chaleur)
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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class WinEnthalpy {
	/*
	 *  	STATIC GLOBAL VAR
	 */
	public static PanelEnthalpie panelEnthalpyDrawArea;

	/* 	
	 * 		INSTANCE VAR
	 */
	private Enthalpy enthalpy;
	private EnthalpyImage enthalpyImage;
	
	private JFrame frame;
	private JLabel lblMouseCoordinate;
	private JLabel lblEnthalpyCoord;
	private JLabel lblPressureCoord;
	private JLabel lblTempCoord;
	private JLabel lblFollower;
	private JRadioButton rdbtnSaturation;
	private JRadioButton rdbtnNothin;
	private JTextField textPHP;
	private JTextField textPBP;

	// Draw elements: lines/points/...
	private List<ElDraw> eDrawL = new ArrayList<ElDraw>();
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	/**
	 * Launch the application for local test
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinEnthalpy window = new WinEnthalpy(new Enthalpy());
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WinEnthalpy(Enthalpy vconfEnthalpy) {
		enthalpy = vconfEnthalpy;
		enthalpyImage = enthalpy.getEnthalpyImage();
		enthalpy.loadPTFile();
		enthalpy.loadSatFile();
		initialize();
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	/**
	 * Get the frame visible
	 */
	public void WinEnthalpieVisible() {
		frame.setVisible(true);
	}

	// -------------------------------------------------------
	// 				 GENERATED BY WIN BUILDER
	//	Except special functions : paintComponent, @Override Mouse events, ..
	// -------------------------------------------------------
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Diagramme Enthalpique");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WinEnthalpy.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelEnthalpyBottom = new JPanel();
		frame.getContentPane().add(panelEnthalpyBottom, BorderLayout.SOUTH);

		lblMouseCoordinate = new JLabel("Mouse Coordinate");
		lblMouseCoordinate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMouseCoordinate.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		panelEnthalpyBottom.setLayout(new GridLayout(0, 5, 0, 0));

		lblEnthalpyCoord = new JLabel("Enthalpy Coordinate");
		lblEnthalpyCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnthalpyCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.add(lblEnthalpyCoord);

		lblPressureCoord = new JLabel("Pressure Coordinate");
		lblPressureCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblPressureCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.add(lblPressureCoord);

		lblTempCoord = new JLabel("Temperature Coordinate");
		lblTempCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblTempCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.add(lblTempCoord);

		lblFollower = new JLabel("Follower");
		lblFollower.setHorizontalAlignment(SwingConstants.CENTER);
		lblFollower.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.add(lblFollower);

		panelEnthalpyBottom.add(lblMouseCoordinate);

		JPanel panelEnthalpyRight = new JPanel();
		panelEnthalpyRight.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(panelEnthalpyRight, BorderLayout.EAST);
		panelEnthalpyRight.setLayout(new GridLayout(0, 1, 0, 0));

		// ----------------------------------------
		// Panel Draw Enthalpy 
		// ----------------------------------------

		panelEnthalpyDrawArea = new PanelEnthalpie(enthalpy);	
		panelEnthalpyDrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		panelEnthalpyDrawArea.setBackground(Color.WHITE);

		JSlider slider = new JSlider();
		slider.setBackground(Color.WHITE);
		slider.setFocusable(false);
		slider.setValue((int)(panelEnthalpyDrawArea.getImageAlphaBlure()*100));

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int v = slider.getValue();
				panelEnthalpyDrawArea.setImageAlphaBlure((float) v / slider.getMaximum());
				panelEnthalpyDrawArea.repaint();
			}
		});
		panelEnthalpyDrawArea.add(slider, BorderLayout.NORTH);

		frame.getContentPane().add(panelEnthalpyDrawArea, BorderLayout.CENTER);

		// Command 
		// ----------------------------------------
		JPanel panelHight = new JPanel();
		panelEnthalpyRight.add(panelHight);
		panelHight.setLayout(new BorderLayout(0, 0));

		JPanel panelHight_Hight = new JPanel();
		panelHight.add(panelHight_Hight, BorderLayout.NORTH);
		panelHight_Hight.setLayout(new BoxLayout(panelHight_Hight, BoxLayout.Y_AXIS));

		JLabel lblPHP = new JLabel("P. HP = PK");
		lblPHP.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPHP.setHorizontalAlignment(SwingConstants.LEFT);
		panelHight_Hight.add(lblPHP);

		textPHP = new JTextField();
		textPHP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Remove the PK element
				for(int i=0;i<eDrawL.size();i++) {
					if (eDrawL.get(i).getType() == ElDraw._LineHorzInfHP)
						eDrawL.remove(i);
				}

				double PK = Double.parseDouble(textPHP.getText());
				ElDraw edraw = new ElDraw(ElDraw._LineHorzInfHP,Math.log10(PK),panelEnthalpyDrawArea.getXmin(),panelEnthalpyDrawArea.getXmax());
				eDrawL.add(edraw);
				panelEnthalpyDrawArea.repaint();
			}
		});
		textPHP.setHorizontalAlignment(SwingConstants.RIGHT);
		textPHP.setText("0");
		panelHight_Hight.add(textPHP);
		textPHP.setColumns(10);

		JLabel lblPBP = new JLabel("P. BP = P0");
		lblPBP.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelHight_Hight.add(lblPBP);

		textPBP = new JTextField();
		textPBP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Remove the P0 element
				for(int i=0;i<eDrawL.size();i++) {
					if (eDrawL.get(i).getType() == ElDraw._LineHorzInfBP)
						eDrawL.remove(i);
				}

				double P0 = Double.parseDouble(textPBP.getText());
				ElDraw edraw = new ElDraw(ElDraw._LineHorzInfBP,Math.log10(P0),panelEnthalpyDrawArea.getXmin(),panelEnthalpyDrawArea.getXmax());
				eDrawL.add(edraw);
				panelEnthalpyDrawArea.repaint();

			}
		});
		textPBP.setHorizontalAlignment(SwingConstants.RIGHT);
		textPBP.setText("0");
		panelHight_Hight.add(textPBP);
		textPBP.setColumns(10);

		// ----------------------------------------
		JPanel panelMiddle1 = new JPanel();
		panelEnthalpyRight.add(panelMiddle1);
		panelMiddle1.setLayout(new BorderLayout(0, 0));

		JPanel panelMiddle1_Center = new JPanel();
		panelMiddle1.add(panelMiddle1_Center, BorderLayout.CENTER);
		panelMiddle1_Center.setLayout(new BoxLayout(panelMiddle1_Center, BoxLayout.Y_AXIS));

		JButton btnPressureTemp = new JButton("Pr.-Tp.");
		btnPressureTemp.setMaximumSize(new Dimension(85, 23));
		btnPressureTemp.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPressureTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinPressTemp window = new WinPressTemp(enthalpy);
				window.WinPressTempVisible();
			}
		});
		panelMiddle1_Center.add(btnPressureTemp);

		// ----------------------------------------
		JPanel panelMiddle2 = new JPanel();
		panelEnthalpyRight.add(panelMiddle2);
		panelMiddle2.setLayout(new BorderLayout(0, 0));

		JPanel panelMiddle2_Center = new JPanel();
		panelMiddle2.add(panelMiddle2_Center, BorderLayout.CENTER);
		panelMiddle2_Center.setLayout(new BoxLayout(panelMiddle2_Center, BoxLayout.Y_AXIS));

		rdbtnSaturation = new JRadioButton("Saturation");
		rdbtnSaturation.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelMiddle2_Center.add(rdbtnSaturation);

		rdbtnNothin = new JRadioButton("Nothing");
		rdbtnNothin.setSelected(true);
		rdbtnNothin.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelMiddle2_Center.add(rdbtnNothin);

		ButtonGroup btGroupPosFollower = new ButtonGroup();
		btGroupPosFollower.add(rdbtnSaturation);
		btGroupPosFollower.add(rdbtnNothin);

		// ----------------------------------------
		JPanel panelBottom = new JPanel();
		panelEnthalpyRight.add(panelBottom);
		panelBottom.setLayout(new BorderLayout(0, 0));

		JPanel panelBottom_Bottom = new JPanel();
		panelBottom.add(panelBottom_Bottom, BorderLayout.SOUTH);
		panelBottom_Bottom.setLayout(new BoxLayout(panelBottom_Bottom, BoxLayout.Y_AXIS));

		JButton btnResetZoom = new JButton("Centrer");
		btnResetZoom.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnResetZoom.setMaximumSize(new Dimension(85, 23));
		btnResetZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelEnthalpyDrawArea.centerImg();
				panelEnthalpyDrawArea.repaint();			
			}
		});
		panelBottom_Bottom.add(btnResetZoom);

		JButton btnClear = new JButton("Effacer");
		btnClear.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnClear.setMaximumSize(new Dimension(85, 23));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelEnthalpyDrawArea.clean();
			}
		});
		panelBottom_Bottom.add(btnClear);
	}


	// ===================================================================================================================
	//												JPANEL Display
	// ===================================================================================================================

	public class PanelEnthalpie extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {

		private static final long serialVersionUID = 1L;	

		/* -----------------------------
  		      Instance Variables
		 * ----------------------------*/
		private Enthalpy enthalpy;

		private BufferedImage imgEnthBg;				// Image Background
		private float imageAlphaBlure=0;

		private Point offset = new Point(0,0);		// Supplementary Offset
		private double mvoYf = 100.0;				// Move Y factor: Move the mouse of 50 pixels, will corresponds to 0.5 
		private Point dragStart = new Point(0,0);	// Start point for Offset computation
		private double zoom = 1;					// Supplementary Zoom Factor
		private double zoomx=1,zoomy=1;					// Zoom factor relative to the figure to display and the panel width

		// Enthalpy
		private double xmin = 140;  				// Minimum of the range of values displayed.
		private double xmax = 520;    				// Maximum of the range of value displayed.

		// Pressure 
		private double ymin = 0.1;  						// Minimum of the range of Pressure value
		private double ymax = 60;     						// Maximum of the range of Pressure value. 
		private double log10_ymin = Math.log10(ymin);  		// Minimum of the range of values displayed. --> Math.log10(0.01) = -1
		private double log10_ymax = Math.log10(ymax);     	// Maximum of the range of value displayed. --> Math.log10(100) = 2

		// Supplementary margin on both sides of the display relative to the scale used !!
		private double marginx = 20;
		private double marginy = 3;
		private double log10_marginy = Math.log10(marginy);

		// Grid
		private double gridUnitX = 20;	// Enthalpy Step
		private double gridUnitY = 1;	// Pressure Step, will be modified following the progression 

		// Curve follower
		private double curveFollowerX=0;
		private double curveFollowerY=0;

		// -------------------------------------------------------
		// 						CONSTRUCTOR
		// -------------------------------------------------------
		public PanelEnthalpie(Enthalpy vconfEnthalpy) {
			enthalpy = vconfEnthalpy;
			imgEnthBg = enthalpy.getEnthalpyImage().openEnthalpyImageFile();
			setBackground(Color.WHITE);

			addMouseWheelListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);
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
		 *  Set The image Blurring
		 * @param alpha
		 */
		public void setImageAlphaBlure(float alpha) {
			imageAlphaBlure = alpha;
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
		 * @return y position in int
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
			//AffineTransform origTransform = g2.getTransform();
			
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
			// -----------------------------------		
			g2.drawImage(imgEnthBg, 
					140,2,540,-4,
					140,0,imgEnthBg.getWidth(),imgEnthBg.getHeight(),
					null);

			float[] scales = { 1f, 1f, 1f, imageAlphaBlure };
			float[] offsets = new float[4];
			RescaleOp rop = new RescaleOp(scales, offsets, null);

			/* Draw the image, applying the filter */
			g2.drawImage(imgEnthBg, rop, -1, -10);

			//g2.setTransform(origTransform); // Restore transform

			
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
				ElDraw.drawElDrawItem(g2, eDrawL.get(i));
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

		@Override
		public void mousePressed(MouseEvent evt) {
			int xMouse = evt.getX();
			int yMouse = evt.getY();

			if (enthalpyImage.islocateOrigineH()) {
				
			}
			if ((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
				dragStart.x = xMouse-offset.x;
				dragStart.y = yMouse-offset.y;
			} else {
				ElDraw edraw = new ElDraw(ElDraw._PointYLog,getHoXm(xMouse),Math.log10(getPoYm(yMouse)));
				eDrawL.add(edraw);
				this.repaint();
			}
		}

		@Override
		public void mouseDragged(MouseEvent evt) {
			if ((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
				offset.x = (evt.getX() - dragStart.x);
				offset.y = (evt.getY() - dragStart.y);
				this.repaint();
			}
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent evt) {
			zoom -= evt.getPreciseWheelRotation() * .03;
			if (zoom < 0) zoom = 0;
			this.repaint();
		}

		@Override
		public void mouseMoved(MouseEvent evt) {

			lblMouseCoordinate.setText(String.format("(x: %d y: %d)", evt.getX(), evt.getY()));

			double hResult = getHoXm(evt.getX());
			lblEnthalpyCoord.setText(String.format("H=%.2f kJ/kg",hResult));

			double pResult = getPoYm(evt.getY());
			lblPressureCoord.setText(String.format("P=%.2f bar",pResult));

			double tRresult = enthalpy.convP2T(pResult);
			lblTempCoord.setText(String.format("T=%.2f �C",tRresult));	

			try {
				if (WinPressTemp.panelTempPressDrawArea.isVisible()) {
					WinPressTemp.panelTempPressDrawArea.spotTempPressFollower(tRresult,pResult);
				}
			} catch (NullPointerException e) {
				// Not present ==> Do nothing !
			}

			if (rdbtnSaturation.isSelected()) {
				double pSat = enthalpy.convSatH2P(hResult,pResult);
				curveFollowerX = hResult;
				curveFollowerY = pSat;
				if (pSat > 0 )
					lblFollower.setText(String.format("PSat=%.2f bar",pSat));
				else
					lblFollower.setText(String.format("----------"));			
			} else {
				lblFollower.setText(String.format("----------"));
			}
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



		public double getXmin() {
			return xmin;
		}

		public double getXmax() {
			return xmax;
		}

		public float getImageAlphaBlure() {
			return imageAlphaBlure;
		}

		public void setImageAlphaBlureBlurring(float imageAlphaBlure) {
			this.imageAlphaBlure = imageAlphaBlure;
		}

	}
}
