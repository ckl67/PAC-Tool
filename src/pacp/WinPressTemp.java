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

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.border.MatteBorder;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WinPressTemp {
	/*
	 *  	STATIC GLOBAL VAR
	 */
	public static PDisplay panelTempPressDrawArea;

	/* 	
	 * 		INSTANCE VAR
	 */
	private Enthalpy enthalpy;

	private JFrame frmRelationTempraturePression;
	private JLabel lblTemperature;
	private JLabel lblPressure;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	/**
	 * Create the application.
	 */
	public WinPressTemp(Enthalpy vconfEnthalpy)  {
		enthalpy = vconfEnthalpy;
		initialize();
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	/**
	 * Get the frame visible
	 */
	public void WinPressTempVisible() {
		frmRelationTempraturePression.setVisible(true);
	}

	// -------------------------------------------------------
	// 				 GENERATED BY WIN BUILDER
	//	Except special functions : paintComponent, @Override Mouse events
	// -------------------------------------------------------
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRelationTempraturePression = new JFrame();
		frmRelationTempraturePression.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
			}
		});
		frmRelationTempraturePression.setIconImage(Toolkit.getDefaultToolkit().getImage(WinPressTemp.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frmRelationTempraturePression.setTitle("Relation Temp\u00E9rature Pression");
		frmRelationTempraturePression.setBounds(100, 100, 450, 400);
		frmRelationTempraturePression.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmRelationTempraturePression.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelValue = new JPanel();
		frmRelationTempraturePression.getContentPane().add(panelValue, BorderLayout.SOUTH);
		panelValue.setLayout(new GridLayout(0, 2, 0, 0));

		lblPressure = new JLabel("P");
		lblPressure.setBorder(new MatteBorder(2, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		lblPressure.setHorizontalAlignment(SwingConstants.CENTER);
		panelValue.add(lblPressure);

		lblTemperature = new JLabel("T");
		lblTemperature.setBorder(new MatteBorder(2, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		lblTemperature.setHorizontalAlignment(SwingConstants.CENTER);
		panelValue.add(lblTemperature);

		panelTempPressDrawArea = new PDisplay();
		frmRelationTempraturePression.getContentPane().add(panelTempPressDrawArea, BorderLayout.CENTER);
	}

	// ===================================================================================================================
	//												JPANEL Display
	// ===================================================================================================================

	public class PDisplay extends JPanel implements MouseListener, MouseMotionListener {
		private static final long serialVersionUID = 1L;
		
		// Supplementary margin on both sides of the display
		private double marginx = 15;
		private double marginy = 10;

		// Grid
		private int gridUnitX = 5;
		private int gridUnitY = 5;

		// Temperature
		private double xmin = -60;  	// Minimum of the range of values displayed.
		private double xmax = 90;     // Maximum of the range of value displayed.

		// Pressure 
		private double ymin = 0.5;  		// Minimum of the range of values displayed.
		private double ymax = 90;     	// Maximum of the range of value displayed.
		
		private double zoomx,zoomy;

		private double posX,posY;
		
		// -------------------------------------------------------
		// 						CONSTRUCTOR
		// -------------------------------------------------------
		public PDisplay() {
			addMouseListener(this);
			addMouseMotionListener(this);			
		}
		
		// -------------------------------------------------------
		// 						PAINT 
		// -------------------------------------------------------

		public void paintComponent(Graphics g) {  
			Graphics2D g2 = (Graphics2D)g;
		
			super.paintComponent(g);
			setBackground(Color.WHITE);
		
			// -----------------------------------
			// Apply a translation so that the drawing 
			// coordinates on the display matches the Panel
			// -----------------------------------
			zoomx=getWidth()/(xmax-xmin+2*marginx);
			zoomy= getHeight()/(ymax-ymin+2*marginy);
			g2.translate(getWidth()/2,getHeight()/2);
			g2.scale(zoomx, -zoomy);
			g2.translate(-(xmax+xmin)/2, -(ymax+ymin)/2);
		
			// -----------------------------------
			// Base font + Scaled font 
			// -----------------------------------
			Font font = new Font(null, Font.PLAIN, 1);
			Font fontReal = font.deriveFont(AffineTransform.getScaleInstance(1/zoomx, -1/zoomy));
			FontMetrics metrics;

			// -----------------------------------
			// Grid
			// -----------------------------------
			g2.setColor(Color.lightGray);
			double pixelWidth = (xmax-xmin+2*marginx)/getWidth();  
			g2.setStroke(new BasicStroke((float) pixelWidth));
			for (int x = (int) xmin; x <= xmax; x=x+gridUnitX) 
				g2.draw( new Line2D.Double(x,ymin,x,ymax));			
		
			double pixelHeight = (ymax-ymin+2*marginy)/getHeight();		
			g2.setStroke(new BasicStroke((float) pixelHeight));
			for (int y = (int) ymin; y <= ymax; y=y+gridUnitY)			
				g2.draw( new Line2D.Double(xmin,y,xmax,y));
		
			// -----------------------------------
			// Axes
			// -----------------------------------
			g2.setColor(Color.blue);
			g2.setStroke(new BasicStroke((float) (2*pixelWidth)));
		
			g2.draw( new Line2D.Double(xmin,0,xmax,0));
			g2.draw( new Line2D.Double(0,ymin,0,ymax));	
		
			// -----------------------------------
			// Text  Abscissa /Ordinate
			// -----------------------------------
			g2.setStroke(new BasicStroke(0));
			
			// Text
			fontReal = fontReal.deriveFont(Font.PLAIN, 12.0f);
			g2.setFont(fontReal);
			metrics = g.getFontMetrics(fontReal);		
			g2.setColor(Color.blue);
			g2.drawString("T", (int) (xmax+metrics.stringWidth("T")), (int) (0));
			g2.drawString("P", (int) (0), (int) (ymax)+metrics.stringWidth("P"));
		
			// Coordinate
			fontReal = fontReal.deriveFont(Font.PLAIN, 12.0f);
			g2.setFont(fontReal);
			metrics = g.getFontMetrics(fontReal);		

			g2.setColor(Color.lightGray);
			String s;
			for (int x = (int) xmin; x <= xmax; x=x+2*gridUnitX) {
				s = String.format("%d",x);
				int xd = x - metrics.stringWidth(s)/ 2;  			    
				g2.drawString(s, xd, (int)-marginy/2);
			}
		
			for (int y = (int) ymin; y <= ymax; y=y+2*gridUnitY) {
				s = String.format("%d",(int)y);
				int yd = y - metrics.stringWidth(s)/ 2;  
				g2.drawString(s, (int) (xmin-marginx/2), (int) (yd));			
			}
		
			// -----------------------------------
			// Curve
			// -----------------------------------		
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(0.5f));
			for(int i=1;i<enthalpy.getlistTP().size();i++) {
				g2.draw( new Line2D.Double(enthalpy.getT(i-1),enthalpy.getP(i-1),enthalpy.getT(i),enthalpy.getP(i)));			 
			}
			
			// Follow the graph based on Eclipse
			// The Ellipse2D class define an ellipse that is defined by a framing rectangle
		    g2.setColor(Color.blue);
		    g2.draw( new Ellipse2D.Double(posX-2, posY-2, 2, 2)); 
		}

		// -------------------------------------------------------
		// 					ALL OVERRIDE METHODS
		// -------------------------------------------------------

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		public void spotTempPressFollower(double temp, double press) {
			String s;
			s = "T: %.2f�C --> P: %.2fbar";
			lblTemperature.setText(String.format(s, temp,enthalpy.convT2P(temp) ));

			s = "P: %.2fbar --> T: %.2f�C";
			lblPressure.setText(String.format(s,press ,enthalpy.convP2T(press) ));
			
			posX=temp;
			posY = enthalpy.convT2P(temp);
			repaint();
		}
		
		@Override
		public void mouseMoved(MouseEvent m) {
			spotTempPressFollower(m.getX()/zoomx+xmin-marginx, -m.getY()/zoomy+marginy+ymax);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
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

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

	}

}
