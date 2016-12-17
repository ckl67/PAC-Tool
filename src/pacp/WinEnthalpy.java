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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class WinEnthalpy {

	public static PanelEnthalpie panelEnthalpyDrawArea;

	private ConfEnthalpy confEnthalpy;
	private JFrame frmDiagrammeEnthalpique;

	private JLabel lblMouseCoordinate;
	private JLabel lblEnthalpyCoord;
	private JLabel lblPressureCoord;
	private JLabel lblTempCoord;


	/**
	 * Create the application.
	 */
	public WinEnthalpy(ConfEnthalpy vconfEnthalpy) {
		confEnthalpy = vconfEnthalpy;
		initialize();
	}


	public void WinEnthalpieVisible() {
		frmDiagrammeEnthalpique.setVisible(true);
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDiagrammeEnthalpique = new JFrame();
		frmDiagrammeEnthalpique.setTitle("Diagramme Enthalpique");
		frmDiagrammeEnthalpique.setIconImage(Toolkit.getDefaultToolkit().getImage(WinEnthalpy.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frmDiagrammeEnthalpique.setBounds(100, 100, 800, 500);
		frmDiagrammeEnthalpique.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmDiagrammeEnthalpique.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelEnthalpyBottom = new JPanel();
		frmDiagrammeEnthalpique.getContentPane().add(panelEnthalpyBottom, BorderLayout.SOUTH);

		lblMouseCoordinate = new JLabel("Mouse Coordinate");
		lblMouseCoordinate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMouseCoordinate.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent m) {
				String s = "x: %d   y: %d";
				lblMouseCoordinate.setText(String.format("("+s+")", m.getX(), m.getY()));

				double result = panelEnthalpyDrawArea.getHoX(m.getX());
				lblEnthalpyCoord.setText(String.format("H=%.2f kJ/kg",result));

				result = panelEnthalpyDrawArea.getPoY(m.getY());
				lblPressureCoord.setText(String.format("P=%.2f bar",result));

				result = panelEnthalpyDrawArea.getToP(result);
				lblTempCoord.setText(String.format("T=%.2f °C",result));	

			}
		};
		panelEnthalpyBottom.setLayout(new GridLayout(0, 4, 0, 0));

		lblEnthalpyCoord = new JLabel("Enthalpy Coordinate");
		lblEnthalpyCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnthalpyCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.add(lblEnthalpyCoord);

		lblPressureCoord = new JLabel("Pressure Coordinate");
		lblPressureCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblPressureCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.add(lblPressureCoord);

		lblTempCoord = new JLabel("Temperature Coordinate");
		lblTempCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.add(lblTempCoord);

		panelEnthalpyBottom.add(lblMouseCoordinate);

		JPanel panelEnthalpyRight = new JPanel();
		panelEnthalpyRight.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		frmDiagrammeEnthalpique.getContentPane().add(panelEnthalpyRight, BorderLayout.EAST);
		panelEnthalpyRight.setLayout(new GridLayout(0, 1, 0, 0));

		panelEnthalpyDrawArea = new PanelEnthalpie(confEnthalpy, ma);	
		panelEnthalpyDrawArea.setBackground(Color.WHITE);
		panelEnthalpyDrawArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		panelEnthalpyDrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		frmDiagrammeEnthalpique.getContentPane().add(panelEnthalpyDrawArea, BorderLayout.CENTER);

		JPanel panelHight = new JPanel();
		panelEnthalpyRight.add(panelHight);
		panelHight.setLayout(new BoxLayout(panelHight, BoxLayout.Y_AXIS));

		JButton btnH0 = new JButton("H0");
		btnH0.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnH0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		panelHight.add(btnH0);

		JButton H1 = new JButton("H1");
		H1.setMaximumSize(new Dimension(60, 23));
		H1.setAlignmentX(Component.CENTER_ALIGNMENT);
		H1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		panelHight.add(H1);

		JPanel panel = new JPanel();
		panelEnthalpyRight.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JButton btnPressureTemp = new JButton("Pres/Temp");
		btnPressureTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinPressTemp window = new WinPressTemp(confEnthalpy);
				window.WinPressTempVisible();
			}
		});
		panel.add(btnPressureTemp, BorderLayout.SOUTH);

		JPanel panelBottom = new JPanel();
		panelEnthalpyRight.add(panelBottom);
		panelBottom.setLayout(new BorderLayout(0, 0));

		JButton btnClear = new JButton("Effacer");
		panelBottom.add(btnClear, BorderLayout.SOUTH);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelEnthalpyDrawArea.clean();
			}
		});
	}

	// ===================================================================================================================
	//												JPANEL Display
	// ===================================================================================================================
	
	public class PanelEnthalpie extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {
		
		private static final long serialVersionUID = 1L;	
		private ConfEnthalpy confEnthalpy;
		private BufferedImage image;
		private Point xymouse = new Point();
		private Point offset = new Point();
		private Point dragStart = new Point();
		private double zoom = 1;

		/**
		 * Constructor for GEnthalpie class
		 */
		public PanelEnthalpie(ConfEnthalpy vconfEnthalpy, MouseAdapter ma) {
			confEnthalpy = vconfEnthalpy;
			openEnthalpyImageFile();
			setBackground(Color.WHITE);

			addMouseWheelListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);
			addMouseMotionListener(ma);
		}

		@Override
		public void paintComponent(Graphics g)	{
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();

			AffineTransform t = new AffineTransform();
			// First Zoom, then translate to be sure to not pollute the offset with zoom operation 
			t.scale(zoom, zoom);
			t.translate(offset.x, offset.y);
			g2d.drawImage(image, t, null);
		}

		// ========================================================================================
		//                            SETTER & GETTER & VISIBLE FUNCTIONS
		// ========================================================================================
		// EnthalpyImageFile
		public void openEnthalpyImageFile() {
			try {
				File file = new File(confEnthalpy.getEnthalpyImageFile());
				image = ImageIO.read(file);	
			} catch (IOException e) {
				System.out.println("Image non trouvée !");
				e.printStackTrace(); 
			}
		}
			
		// Clean
		public void clean() {
			repaint();
		}

		// Compute Entalpy H / mouse coordinate
		public double getHoX(int x) {
			//  H = iHOrigine  + (iHFinal-iHOrigine ) * (X/zoom-offset.x -mHOrigine)  / (mHFinal-mHOrigine)
			double xh;
			xh = confEnthalpy.getiHOrigine() + (confEnthalpy.getiHFinal()-confEnthalpy.getiHOrigine()) * (x/zoom - offset.x - confEnthalpy.getmHOrigine())/(double)(confEnthalpy.getmHFinal()-confEnthalpy.getmHOrigine()) ;
			return xh;
		}

		public int getXoH(double h) {
			//  X = zoom * ([(H -  iHOrigine) / (iHFinal-iHOrigine )  * (mHFinal-mHOrigine) ] + offset.x +mHOrigine)
			double xd;
			xd = zoom * (((h -  confEnthalpy.getiHOrigine()) / (confEnthalpy.getiHFinal()-confEnthalpy.getiHOrigine() )  * (confEnthalpy.getmHFinal()-confEnthalpy.getmHOrigine()) ) + offset.x +confEnthalpy.getmHOrigine());
			return (int)xd;
		}
		
		// Compute Temperature T / mouse coordinate
		public double getToP(double P) {
			double T;
			T = P/zoom;
			return T;
		}

		public double getPoT(double P) {
			double T;
			T = P/zoom;		
			return T;
		}
		
		// Compute Pression P / mouse coordinate
		public double getPoY(int y) {
			// yP = mPOrigine  - y/zoom
			// yP = Log(iPOrigine)  + (Log(iPFinal)-Log(iPOrigine) ) * (Y/zoom-offset.y -mPOrigine)  / (mPFinal-mPOrigine)
			// yP = exp(yP*ln(10)
			double yP;
		//	yP = confEnthalpy.getmPOrigine()  - (double)y/zoom;
			yP = Math.log10(confEnthalpy.getiPOrigine())  + (Math.log10(confEnthalpy.getiPFinal())-Math.log10(confEnthalpy.getiPOrigine()) ) * (y/zoom-offset.y -confEnthalpy.getmPOrigine())  / (confEnthalpy.getmPFinal()-confEnthalpy.getmPOrigine());
			yP = Math.exp(yP*Math.log(10));			
			return yP;
		}
		
		
		// ===============================================================================================================
		// 													EVENT LISTNER
		// ===============================================================================================================
		@Override
		public void mousePressed(MouseEvent evt) {
			int xMouse = evt.getX();
			int yMouse = evt.getY();
			
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			if ((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
				dragStart.x = xMouse-offset.x;
				dragStart.y = yMouse-offset.y;
			}

			if ( confEnthalpy.islocateOrigineH()) {
				confEnthalpy.setmHOrigine(xMouse/zoom - offset.x);
				confEnthalpy.setlocateOrigineH(false);
			}

			if ( confEnthalpy.islocateFinalH()) {
				confEnthalpy.setmHFinal(xMouse/zoom - offset.x);
				confEnthalpy.setlocateFinalH(false);
			}
				
			if ( confEnthalpy.islocateOrigineP()) {
				confEnthalpy.setmPOrigine(yMouse/zoom - offset.y);
				confEnthalpy.setlocateOrigineP(false);
			}

			if ( confEnthalpy.islocateFinalP()) {
				confEnthalpy.setmPFinal(yMouse/zoom - offset.y);
				confEnthalpy.setlocateFinalP(false);
			}
					
			Graphics g = getGraphics();
			Graphics2D g2 = (Graphics2D)g;
			
			//Point
			g2.setColor(Color.RED);
			g2.fillOval( xMouse-5, yMouse-5, 10, 10 );
			g2.setColor(Color.BLACK);
			g2.drawOval( xMouse-5, yMouse-5, 10, 10 );
			g2.setColor(Color.RED);
			g2.fillOval( xMouse-5, yMouse-5, 10, 10 );
			g2.setColor(Color.BLACK);
			g2.drawOval( xMouse-5, yMouse-5, 10, 10 );	
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
			if ( confEnthalpy.islocateOrigineH()) {
				this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			}
			if ( confEnthalpy.islocateFinalH()) {
				this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));			
			}
			if ( confEnthalpy.islocateOrigineP()) {
				this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			}
			if ( confEnthalpy.islocateFinalP()) {
				this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));			
			}
			xymouse.x= evt.getX();
			xymouse.y= evt.getY();					    
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

	};


}
