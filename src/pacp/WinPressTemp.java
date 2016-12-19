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
	public static PDisplay panelTempPressDrawArea;

	private ConfEnthalpy confEnthalpy;

	private JFrame frmRelationTempraturePression;
	private JLabel lblTemperature;
	private JLabel lblPressure;

	// Supplementary margin on both sides of the display
	private double marginx = 10;
	private double marginy = 5;

	// Grid
	private int gridUnitX = 5;
	private int gridUnitY = 5;

	// Temperature
	private double xmin = -60;  	// Minimum of the range of values displayed.
	private double xmax = 90;     // Maximum of the range of value displayed.

	// Pressure 
	private double ymin = 0.5;  		// Minimum of the range of values displayed.
	private double ymax = 90;     	// Maximum of the range of value displayed.



	// Constructor 
	public WinPressTemp(ConfEnthalpy vconfEnthalpy)  {
		confEnthalpy = vconfEnthalpy;
		initialize();
	}

	public void WinPressTempVisible() {
		frmRelationTempraturePression.setVisible(true);
	}

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
		private double zoomx,zoomy;

		private double posX,posY;
		
		
		// Constructor
		public PDisplay() {
			addMouseListener(this);
			addMouseMotionListener(this);			
		}

		// Paint
		public void paintComponent(Graphics g) {  
			Graphics2D g2 = (Graphics2D)g;
			AffineTransform saveTransform;

			super.paintComponent(g);
			setBackground(Color.WHITE);

			// Apply a translation so that the drawing coordinates on the display are referenced to curve
			zoomx=getWidth()/(xmax-xmin+2*marginx);
			zoomy= getHeight()/(ymax-ymin+2*marginy);
			g2.translate(getWidth()/2,getHeight()/2);
			saveTransform = g2.getTransform();
			g2.scale(zoomx, -zoomy);
			g2.translate(-(xmax+xmin)/2, -(ymax+ymin)/2);

			// -----------------------------------
			// Grid
			// -----------------------------------
			g2.setColor(Color.lightGray);
			g2.setStroke(new BasicStroke(0.05f));

			for (int x = (int) xmin; x <= xmax; x=x+gridUnitX) 
				g2.draw( new Line2D.Double(x,ymin,x,ymax));			

			for (int y = (int) ymin; y <= ymax; y=y+gridUnitY)			
				g2.draw( new Line2D.Double(xmin,y,xmax,y));


			// -----------------------------------
			// Axes
			// -----------------------------------
			g2.setColor(Color.blue);
			g2.setStroke(new BasicStroke(0.5f));

			g2.draw( new Line2D.Double(xmin,0,xmax,0));
			g2.draw( new Line2D.Double(0,ymin,0,ymax));	


			// -----------------------------------
			// Text  Abscissa /Ordinate
			// Be care orientation inverse for Y !!!
			// -----------------------------------
			g2.setTransform(saveTransform);
			g2.scale(zoomx, zoomy);
			g2.translate(-(xmax+xmin)/2, -(ymax+ymin)/2);

			// Text
			Font font = new Font(null, Font.BOLD, 3); 
			g2.setFont(font);
			g2.drawString("T", (int) (xmax+2), (int) (ymin+ymax));
			g2.drawString("P", (int) (0), (int) (ymin)-1);

			// Coordinate
			font = new Font(null, Font.PLAIN, 3); 
			FontMetrics metrics = g.getFontMetrics(font);		
			g2.setFont(font);

			String s;
			int xd;		
			for (int x = (int) xmin; x <= xmax; x=x+2*gridUnitX) {
				s = String.format("%d",x);
				xd = x - metrics.stringWidth(s)/ 2;  			    
				g2.drawString(s, xd, (int) ymax+4);
			}

			for (int y = (int) ymin; y <= ymax; y=y+2*gridUnitY) {
				s = String.format("%d",(int)y);
				g2.drawString(s, (int) (xmin-marginx/2), (int) (-y+ymin+ymax));			
			}

			g2.setTransform(saveTransform);
			g2.scale(zoomx, -zoomy);
			g2.translate(-(xmax+xmin)/2, -(ymax+ymin)/2);

			// -----------------------------------
			// Curve
			// -----------------------------------		
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(0.5f));
			for(int i=1;i<confEnthalpy.getlistTempPress().size();i++) {
				g2.draw( new Line2D.Double(confEnthalpy.getTempFromList(i-1),confEnthalpy.getPressFromList(i-1),confEnthalpy.getTempFromList(i),confEnthalpy.getPressFromList(i)));			 
			}
			
			// Follow the graph
		    g2.setColor(Color.blue);
		    g2.draw( new Ellipse2D.Double(posX-2, posY-2, 2, 2));
		    
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void spotTempPressFollower(double temp, double press) {
			String s;
			s = "T: %.2f°C --> P: %.2fbar";
			lblTemperature.setText(String.format(s, temp,confEnthalpy.getPressFromTemp(temp) ));

			s = "P: %.2fbar --> T: %.2f°C";
			lblPressure.setText(String.format(s,press ,confEnthalpy.getTempFromPress(press) ));
			
			posX=temp;
			posY = confEnthalpy.getPressFromTemp(temp);
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
