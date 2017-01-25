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
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;

public class WinMeasurePoints {
	// --------------------------------------------------------------------
	// CONSTANT
	// --------------------------------------------------------------------
	// Measure Point
	// Used for Measure Points, it helps to define if the collected points 
	// belongs to HP , BP, ..
	// --------------------------------------------------------------------
	public static int _GROUP_BP = 1;
	public static int _GROUP_HP = 2;
	public static int _GROUP_HEAT = 3;
	public static int _GROUP_SOURCE = 4;

	public static int _HP1_ID = 2;
	public static int _HP2_ID = 3;
	public static int _BP_ID = 6;

	// --------------------------------------------------------------------
	// WIN Builder
	// --------------------------------------------------------------------
	private JFrame frame;
	private BufferedImage img;
	private JTextField textField;
	private JTextField textFieldUnity;

	private WinEnthalpy winEnthalpy;
	/* 	----------------------------------------
	 * 		INSTANCE VAR
	 * ----------------------------------------*/
	private boolean HELP_FIND_LOCATION=false;	// Objective is to display the coordinate for the points to match
	private String imgPath = "/pacp/images/Cycle.png";
	private List<MeasurePoints> measurePL;

	private List<ElDraw> eDrawL;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	/**
	 * Launch the application for local test
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Enthalpy enthalpy1 = new Enthalpy();
				List<ElDraw> eDrawL1 = new ArrayList<ElDraw>();
				List<MeasurePoints> measurePL1;
				measurePL1 = new ArrayList<MeasurePoints>(); 
				measurePL1.add(new MeasurePoints("T1",515,90,"Température des gaz BP\n après surchauffe interne\n et avant compression",0,"°C",WinMeasurePoints._GROUP_BP));
				measurePL1.add(new MeasurePoints("T2",546,90,"Température des gaz HP\n en fin de compression\n (Cloche du compresseur)",0,"°C",WinMeasurePoints._GROUP_HP));
				measurePL1.add(new MeasurePoints("P3",582,135,"Température du début de condensation\n (Mesure HP Manifod)",0,"Bar",WinMeasurePoints._GROUP_HP));
				measurePL1.add(new MeasurePoints("P4",583,203,"Température de fin de condensation\n (Mesure HP Manifod)",0,"Bar",WinMeasurePoints._GROUP_HP));
				measurePL1.add(new MeasurePoints("T5",512,247,"Température des gaz HP\n après sous refroidissement",0,"°C",WinMeasurePoints._GROUP_HP));
				measurePL1.add(new MeasurePoints("T6",433,248,"Température sortie Détendeur / Capillaire",0,"°C",WinMeasurePoints._GROUP_BP));
				measurePL1.add(new MeasurePoints("P7",371,135,"Température évaporation\n (Mesure BP Manifold)",0,"Bar",WinMeasurePoints._GROUP_BP ));
				measurePL1.add(new MeasurePoints("T8",479,89, "Température des gaz HP\naprès surchauffe externe",0,"°C",WinMeasurePoints._GROUP_BP));
				measurePL1.add(new MeasurePoints("TMi",663,57,"Température Retour Eau Chauffage",0,"°C",WinMeasurePoints._GROUP_HEAT));
				measurePL1.add(new MeasurePoints("TMo",663,282,"Température Départ Eau Chauffage",0,"°C",WinMeasurePoints._GROUP_HEAT));
				measurePL1.add(new MeasurePoints("TCi",321,281,"Température Retour Eau Captage",0,"°C",WinMeasurePoints._GROUP_SOURCE));
				measurePL1.add(new MeasurePoints("TCo",321,57,"Température Départ Eau Captage",0,"°C",WinMeasurePoints._GROUP_SOURCE));
				WinEnthalpy winEnthalpy = new WinEnthalpy(enthalpy1, eDrawL1, measurePL1);
				try {
					WinMeasurePoints window = new WinMeasurePoints(winEnthalpy,eDrawL1,measurePL1);
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
	public WinMeasurePoints(WinEnthalpy windowEnthalpy, List<ElDraw> veDrawL, List<MeasurePoints> vmeasurePL) {
		winEnthalpy = windowEnthalpy;
		eDrawL = veDrawL;
		measurePL = vmeasurePL;

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
			int X = measurePL.get(i).getX();
			int Y = measurePL.get(i).getY(); 		
			if ( ( pX < (X+zoneX)) && ( pX > (X-zoneX)) && (pY < (Y+zoneY)) && ( pY > (Y-zoneY)) ) {
				id = i;
			}
		}
		return id;
	}

	public int getIdForElem(String name) {
		int id=-1;
		for(int i=0;i<measurePL.size();i++) {
			if  (measurePL.get(i).getName().equals(name))
				id=i;
		}
		return id;	
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


		try {
			img = ImageIO.read(frame.getContentPane().getClass().getResource(imgPath));
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

		PPanel panel = new PPanel();
		panel.setBounds(0, 0, imgWidth, imgHeight);
		panel.setBackground(Color.WHITE);

		textField = new JTextField();
		textField.setForeground(Color.BLACK);
		textField.setBackground(Color.YELLOW);
		textField.setFont(new Font("Tahoma", Font.BOLD, 11));
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setVisible(false);

		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id = getIdNearest((int)textField.getX(),(int)textField.getY());
				if (id == _HP1_ID)
					measurePL.get(_HP2_ID).setValue(Double.valueOf(textField.getText()));
				if (id == _HP2_ID)
					measurePL.get(_HP2_ID).setValue(Double.valueOf(textField.getText()));

				measurePL.get(id).setValue(Double.valueOf(textField.getText()));
				// Type of Group of selected point, determine pressure and T


				double pv = 0;
				if (measurePL.get(id).getGroupHpBp() == _GROUP_HP) {
					pv =measurePL.get(_HP1_ID).getValue();
				} else if (measurePL.get(id).getGroupHpBp() == _GROUP_BP) {
					pv =measurePL.get(_BP_ID).getValue();				
				} else {
					pv = -1;
					System.out.println("no HP or BP");
				}
				double tv = measurePL.get(id).getValue();

				// The paint (panel Enthalpy) procedure MUST check if the measurePL 
				// can be added to draw element add to display element to : ElDraw


				if ( pv > 0 ) {
					if ( (id ==_HP1_ID) || (id == _HP2_ID) ) {
						if (WinEnthalpy.panelEnthalpyDrawArea.isVisible())
							winEnthalpy.actionNewValueHP(pv);
					} else if ( (id ==_BP_ID) ) {
						if (WinEnthalpy.panelEnthalpyDrawArea.isVisible())
							winEnthalpy.actionNewValueBP(pv);
					} else if ( (id !=_HP1_ID) && (id != _HP2_ID) && (id != _BP_ID) ) {
						// TO CORRECT
						double hv = tv;
						ElDraw edraw = new ElDraw(ElDraw._PointYLog,hv,Math.log10(pv));
						eDrawL.add(edraw);
						if (WinEnthalpy.panelEnthalpyDrawArea.isVisible())
							WinEnthalpy.panelEnthalpyDrawArea.repaint();
					}
				}

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
				int x = measurePL.get(pointMatched_id).getX();
				int y = measurePL.get(pointMatched_id).getY();

				// Create Cercle
				Point2D center = new Point2D.Float(x, y);
				float[] dist = {0.0f, 1.0f};
				Color[] colors = {Color.YELLOW, Color.RED};
				int radius = 10;
				RadialGradientPaint p =  new RadialGradientPaint(center, radius, dist, colors);
				g2d.setPaint(p);
				g2d.fill(new Ellipse2D.Double(x-radius, y-radius, 2*radius, 2*radius));

				// Definition Text 
				String defTxt = measurePL.get(pointMatched_id).getDefinition();
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
					textFieldUnity.setText(measurePL.get(pointMatched_id).getUnity());
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
