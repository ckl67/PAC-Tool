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

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;

public class WinMeasurePoints {

	// --------------------------------------------------------------------
	// Objective is to display the coordinate for the points to match
	// --------------------------------------------------------------------
	private static boolean HELP_FIND_LOCATION = false;	

	/* 	----------------------------------------
	 * 		INSTANCE VAR
	 * ----------------------------------------*/

	private String imgURL;
	private List<Measure> measurePL;
	private List<ElDraw> eDrawL;
	private WinEnthalpy winEnth;

	// -------------------------------------------------------
	//	LOCAL VARIABLES
	// -------------------------------------------------------
	private JFrame frame;
	private PPanel panel;
	private BufferedImage img;
	private JTextField textField;
	private JTextField textFieldUnity;
	

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	/**
	 * Launch the application for local test
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				List<ElDraw> eDrawL1 = new ArrayList<ElDraw>();
				List<Measure> measurePL1;
				measurePL1 = new ArrayList<Measure>(); 
		        for (MeasurePoint p : MeasurePoint.values())
		        	measurePL1.add(new Measure(p));

				try {
					WinMeasurePoints window = new WinMeasurePoints(eDrawL1,measurePL1,null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param measurePL 
	 */
	public WinMeasurePoints(List<ElDraw> veDrawL, List<Measure> vmeasurePL, WinEnthalpy vwinEnth ) {

		this.imgURL = "/pacp/images/Cycle.png";
		this.measurePL = vmeasurePL;
		this.eDrawL = veDrawL;
		this.winEnth = vwinEnth;

		initialize();
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Get the frame visible
	 */
	public void WinMeasurePointsVisible() {
		frame.setVisible(true);
	}

	/**
	 * Will return the nearest element id of measurePL 
	 * @param pX
	 * @param pY
	 * @return
	 */
	public int getIdNearest(int pX, int pY ) {
		int id=-1;
		int zoneX = 5;
		int zoneY = 5;

		for(int i=0;i<measurePL.size();i++) {
			int X = measurePL.get(i).getMeasurePoint().getXm();
			int Y = measurePL.get(i).getMeasurePoint().getYm(); 		
			if ( ( pX < (X+zoneX)) && ( pX > (X-zoneX)) && (pY < (Y+zoneY)) && ( pY > (Y-zoneY)) ) {
				id = i;
			}
		}
		return id;
	}

	/**
	 * Will return the element id of measurePL for a name 
	 * @param name
	 * @return
	 */
	public int getIdForElem(String name) {
		int id=-1;
		for(int i=0;i<measurePL.size();i++) {
			if  (measurePL.get(i).getMeasurePoint().equals(name))
				id=i;
		}
		return id;	
	}


	/**
	 * Will update and compute H,P,T for the Measure Point List
	 * If P0 and PK have been updated then function will return 2
	 * If P0 or PK points will be updated then function will return 1
	 * If no P0 or PK points will be updated the function will return 0
	 * @param mpL
	 */
	private int updateAllMeasureListElem () {
		int result=0;

		for (MeasurePoint p : MeasurePoint.values()) {
			// p = T1,T2,...
			// n = 0 , 1, 
			// m = mpL.get(n) 
			int n = p.ordinal();
			Measure m = measurePL.get(n); 
			switch (m.getMeasurePoint()) {
			case T1 : case T6 : case T8 :	// Intersection with P0
				m.setMT( m.getValue() );
				m.setMP( m.convT2P( m.getValue() ) );
				m.setMP0PK( measurePL.get(MeasurePoint._P0).getValue()  );
				double Hsatv0 = m.matchP2HvSat( m.getMP());
				double Psat0 = m.getMP();
				double P0PK0 = m.getMP0PK();
				double Hmpiso0 = m.CompHmatchPSatWithP0PK(Hsatv0, Psat0, P0PK0); 
				m.setMH(Hmpiso0);
				break;
			case T2 : case T5 :				// Intersection with PK
				m.setMT( m.getValue() );
				m.setMP( m.convT2P( m.getValue() ) );
				m.setMP0PK( measurePL.get(MeasurePoint._PK).getValue()  );
				m.setMH( m.matchP2HvSat( m.getMP() ));
				double Hsatv1 = m.matchP2HvSat( m.getMP());
				double Psat1 = m.getMP();
				double P0PK1 = m.getMP0PK();
				double Hmpiso1 = m.CompHmatchPSatWithP0PK(Hsatv1, Psat1, P0PK1); 
				m.setMH(Hmpiso1);
				break;
			case P3 : case P4 : case P7 :
				m.setMP( m.getValue() );
				m.setMT( m.convP2T( m.getValue() ) );
				m.setMP0PK( m.getValue()  );
				break;			
			default:
				break;
			}
			System.out.println(p + " value=" + m.getValue() + " T=" + m.getMT() + " --> P=" + m.getMP() + "  :: P0 or PK = " + m.getMP0PK() + "  Hsat =" + m.getMH() );

		}


		return result;
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

		// One issue faced on image extension .PNG expected .png
		// ImageIO#read. throws an illegal argument exception for a null parameter; 
		// if the String representing the path to the image has even the tiniest error in, 
		// the URL passed to read() will be null. 
		try {
			img = ImageIO.read(frame.getContentPane().getClass().getResource(imgURL));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int imgWidth = img.getWidth();
		int imgHeight = img.getHeight();

		frame.setTitle("Points de Mesure");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WinEnthalpy.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		frame.setSize(imgWidth+10, imgHeight+40);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		panel = new PPanel();
		panel.setBounds(0, 0, imgWidth, imgHeight);
		panel.setBackground(Color.WHITE);

		JButton btnNewButton = new JButton("Reset");
		btnNewButton.setFocusable(false);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Reset");
				System.out.println("Clear measurePL");
				for (int n = 0; n < measurePL.size(); n++ ) {
					measurePL.get(n).setChosen(false);
					measurePL.get(n).setValue(0.0);
				}

			}
		});

		panel.add(btnNewButton);

		textField = new JTextField();
		textField.setForeground(Color.BLACK);
		textField.setBackground(Color.YELLOW);
		textField.setFont(new Font("Tahoma", Font.BOLD, 11));
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setVisible(false);

		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int id = getIdNearest((int)textField.getX(),(int)textField.getY());
				double inValue = Double.valueOf(textField.getText());

				// HP1 = HP2
				if (id == MeasurePoint._PK1_ID)
					measurePL.get(MeasurePoint._PK2_ID).setValue(inValue);
				if (id == MeasurePoint._PK2_ID)
					measurePL.get(MeasurePoint._PK1_ID).setValue(inValue);

				// Affect Value to measurePL
				measurePL.get(id).setValue(inValue);
				measurePL.get(id).setChosen(true);

				updateAllMeasureListElem();
				
				if (winEnth != null) 
					winEnth.updateAllTextField();


				textField.setVisible(false);
				textFieldUnity.setVisible(false);
			}
		});

		textFieldUnity = new JTextField();
		textFieldUnity.setForeground(Color.BLACK);
		textFieldUnity.setBackground(Color.YELLOW);
		textFieldUnity.setFont(new Font("Tahoma", Font.BOLD, 11));
		textFieldUnity.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldUnity.setVisible(false);

		panel.add(textField);
		panel.add(textFieldUnity);
		frame.getContentPane().add(panel);

	}

	// ===================================================================================================================
	// ===================================================================================================================
	//												JPANEL Display
	// ===================================================================================================================
	// ===================================================================================================================

	public class PPanel extends JPanel implements MouseListener,  MouseMotionListener  { 

		private static final long serialVersionUID = 1L;
		private boolean pointMatched;
		private int pointMatched_id;

		// -------------------------------------------------------
		// 						CONSTRUCTOR
		// -------------------------------------------------------
		public PPanel(){
			pointMatched = false;
			pointMatched_id = -1;

			addMouseListener(this);
			addMouseMotionListener(this);
	
		}

		// -------------------------------------------------------
		// 							METHOD
		// -------------------------------------------------------


		// -------------------------------------------------------
		// 						PAINT 
		// -------------------------------------------------------
		public void paintComponent(Graphics g){
			Graphics2D g2d = (Graphics2D)g;         

			// Background image
			g2d.drawImage(img,0, 0, this);

			if (pointMatched) {
				int x = measurePL.get(pointMatched_id).getMeasurePoint().getXm();
				int y = measurePL.get(pointMatched_id).getMeasurePoint().getYm();

				// Create Cercle
				Point2D center = new Point2D.Float(x, y);
				float[] dist = {0.0f, 1.0f};
				Color[] colors = {Color.YELLOW, Color.RED};
				int radius = 10;
				RadialGradientPaint p =  new RadialGradientPaint(center, radius, dist, colors);
				g2d.setPaint(p);
				g2d.fill(new Ellipse2D.Double(x-radius, y-radius, 2*radius, 2*radius));

				// Definition Text 
				String defTxt = measurePL.get(pointMatched_id).getMeasurePoint().getDefinition();
				Font font = new Font(null, Font.PLAIN, 15);
				g2d.setFont(font);

				int widthFRRmax = 0;
				int heightFRRmax = 0;
				for (String line : defTxt.split("\n")) {
					Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(line, g2d);
					if ( (int)bounds.getWidth() > widthFRRmax )
						widthFRRmax = (int)bounds.getWidth();
					heightFRRmax = heightFRRmax + (int)bounds.getHeight();
				}

				// Background Text zone 
				g2d.setColor(Color.YELLOW);
				g2d.fillRoundRect(x-10, y+20, widthFRRmax+20, heightFRRmax+15,10,10);

				// Write Text in the zone
				g2d.setColor(Color.BLACK);	
				int yl = y+20;
				for (String line : defTxt.split("\n")) {
					yl = yl + g2d.getFontMetrics().getHeight();
					g2d.drawString(line, x, yl);
				}

			}
		}

		// -------------------------------------------------------
		// 						EVENT LISTNER
		// -------------------------------------------------------
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
		public void mousePressed(MouseEvent evt) {

			if (HELP_FIND_LOCATION)
			{
				System.out.println(evt.getX()+","+ evt.getY());
			}
			else {

				int id = getIdNearest(evt.getX(),evt.getY());
				if (id >= 0 ) {
					textField.setBounds(evt.getX(), evt.getY(), 80, 20);
					textField.setText(String.valueOf(measurePL.get(pointMatched_id).getValue()));
					textField.setVisible(true);

					textFieldUnity.setBounds(evt.getX()+80, evt.getY(), 30, 20);
					textFieldUnity.setText(measurePL.get(pointMatched_id).getMeasurePoint().getUnity());
					textFieldUnity.setVisible(true);
				} else {
					textField.setVisible(false);			
					textFieldUnity.setVisible(false);			
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent evt) {
			int id = getIdNearest(evt.getX(),evt.getY());
			if (id >= 0 ) {
				// Point found and to draw
				pointMatched = true;
				pointMatched_id = id;
			} else {
				pointMatched = false;
			}
			if (!HELP_FIND_LOCATION)
				this.repaint();
		}               
	}

}
