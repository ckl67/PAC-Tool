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
package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
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

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import computation.MeasurePoint;
import computation.MeasureTable;
import computation.MeasureChoiceStatus;
import computation.MeasureObject;
import computation.MeasureCollection;
import enthalpy.Enthalpy;
import pac.Pac;

import javax.imageio.ImageIO;

public class PanelMeasurePoints extends JPanel implements MouseListener,  MouseMotionListener {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(PanelMeasurePoints.class.getName());

	// --------------------------------------------------------------------
	// Objective is to display the coordinate for the points to match
	// --------------------------------------------------------------------
	private static boolean HELP_FIND_LOCATION = false;	

	/* 	----------------------------------------
	 * 		INSTANCE VAR
	 * ----------------------------------------*/

	private MeasureCollection measureCollection;
	private List<ElDraw> eDrawL;
	private Enthalpy enthalpy;
	private Pac pac;
	private MeasureTable measureTable;
	private WinCompressor winCompressor;

	private int bgImgWidth;
	private  int bgImgHeight;

	// -------------------------------------------------------
	//	LOCAL VARIABLES
	// -------------------------------------------------------
	private String imgURL;
	private List<MeasurePoint> measurePL;

	private BufferedImage img;
	private JTextField textField;
	private JTextField textFieldUnity;

	private boolean pointMatched;
	private int pointMatched_id;
	// -------------------------------------------------------
	// 						LOCAL TEST
	// -------------------------------------------------------
	/**
	 * Launch the application for local test
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				List<ElDraw> eDrawL1 = new ArrayList<ElDraw>();
				MeasureCollection measureCollection1 = new MeasureCollection();
				Enthalpy enthalpy1 = new Enthalpy();
				Pac pac1 = new Pac();
				WinPacToolConfig winPacToolConfig1 = new WinPacToolConfig();
				
				JFrame frame = new JFrame();
				frame.setBounds(100, 100, 446, 187);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setLayout(new BorderLayout(0, 0));

				PanelMeasurePoints panel1 = new PanelMeasurePoints(eDrawL1,measureCollection1,enthalpy1,new Pac(), new MeasureTable(measureCollection1), new WinCompressor(pac1, winPacToolConfig1) );
				frame.getContentPane().add(panel1, BorderLayout.CENTER);
				frame.setVisible(true);

				frame.setBounds(100, 10, panel1.getBgImgWidth()+8, panel1.getBgImgHeight()+50);
				frame.setContentPane(panel1);

			}
		});
	}

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	/**
	 * Create the application.
	 * @param measurePL 
	 */
	public PanelMeasurePoints(List<ElDraw> veDrawL, MeasureCollection vmeasureCollection, Enthalpy venthalpy, Pac vpac, MeasureTable vmeasureTable, WinCompressor vwinCompressor) {
		measureCollection = vmeasureCollection;
		eDrawL = veDrawL;
		enthalpy = venthalpy;
		pac = vpac;
		measureTable = vmeasureTable;
		winCompressor = vwinCompressor;

		imgURL = "/gui/images/Cycle.png";
		measurePL = measureCollection.getMeasurePL();

		pointMatched = false;
		pointMatched_id = -1;

		addMouseListener(this);
		addMouseMotionListener(this);
		initialize();
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------


	public String getItemName(int pX, int pY ) {
		String item="Empty";

		for (ItemCoord p : ItemCoord.values()) {
			int x1 = p.getXm();
			int x2 = p.getXm() + p.getWidth();
			int y1 = p.getYm();
			int y2 = p.getYm() + p.getHeigh();

			if ( ( pX < (x2)) && ( pX > (x1)) && (pY < (y2)) && ( pY > (y1)) ) {
				item = p.toString();
			}
		}
		return item;
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
			int X = measurePL.get(i).getMeasureObject().getXm();
			int Y = measurePL.get(i).getMeasureObject().getYm(); 		
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
			if  (measurePL.get(i).getMeasureObject().equals(name))
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


		// One issue faced on image extension .PNG expected .png
		// ImageIO#read. throws an illegal argument exception for a null parameter; 
		// if the String representing the path to the image has even the tiniest error in, 
		// the URL passed to read() will be null. 
		try {
			img = ImageIO.read(this.getClass().getResource(imgURL));
		} catch (IOException e) {
			logger.error("Ops!", e);
		}

		bgImgWidth = img.getWidth();
		bgImgHeight = img.getHeight();

		JButton btnNewButton = new JButton("Reset");
		btnNewButton.setFocusable(false);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("Reset to Zero the measure points List");
				for (int n = 0; n < measurePL.size(); n++ ) {
					measurePL.get(n).clearMeasure();
				}
				logger.trace("Remove all Draw elements (eDrawL) (Clear the display)");
				eDrawL.clear();
			}
		});

		add(btnNewButton);

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

				// HP1 = HP2 = PK_GAS = PK_LIQUID
				if (id == MeasureObject._PK_VAPOR_ID)
					measurePL.get(MeasureObject._PK_LIQUID_ID).setValue(inValue);
				if (id == MeasureObject._PK_LIQUID_ID)
					measurePL.get(MeasureObject._PK_VAPOR_ID).setValue(inValue);

				// Affect Value to measurePL
				measurePL.get(id).setValue(inValue);
				// Indicate that the value has been chosen, 
				// but there are some conditions to respect before 
				// that the point can be validated 
				measurePL.get(id).setMeasureChoiceStatus(MeasureChoiceStatus.Chosen);

				logger.trace("New values added {}",String.format("%.2f", inValue));
				logger.info("Update the Measure Collection data ");
				MeasureCollection.updateAllMeasurePoints(measureCollection,enthalpy,pac);

				logger.info("Update MeasureTable");
				measureTable.setAllTableValues();

				logger.trace("Reinitialse the complete Draw elements with the Measure Collection");
				eDrawL.clear();
				eDrawL = ElDraw.createElDrawFrom(measureCollection,eDrawL);

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

		add(textField);
		add(textFieldUnity);

	}



	// -------------------------------------------------------
	// 						PAINT 
	// -------------------------------------------------------
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;         

		// Background image
		g2d.drawImage(img,0, 0, this);

		if (pointMatched) {
			int x = measurePL.get(pointMatched_id).getMeasureObject().getXm();
			int y = measurePL.get(pointMatched_id).getMeasureObject().getYm();

			// Create Circle
			Point2D center = new Point2D.Float(x, y);
			float[] dist = {0.0f, 1.0f};
			Color[] colors = {Color.YELLOW, Color.RED};
			int radius = 10;
			RadialGradientPaint p =  new RadialGradientPaint(center, radius, dist, colors);
			g2d.setPaint(p);
			g2d.fill(new Ellipse2D.Double(x-radius, y-radius, 2*radius, 2*radius));

			// Definition Text 
			String defTxt = measurePL.get(pointMatched_id).getMeasureObject().getDefinition();
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
			// Only print authorized, because used to display the coordinate in the cmd window
			System.out.println(evt.getX()+","+ evt.getY());
		}
		else {

			if (evt.getButton() == MouseEvent.BUTTON3) {
				String item = getItemName(evt.getX(),evt.getY());
				
				switch (item) {
				case "Compressor":
					logger.info("mousePressed choice: {} ",item);
					winCompressor.setVisible(true);
					break;
				case "Circulator":
					logger.info("mousePressed choice: {} ",item);				
					break;
				default:
					break;
				}

			} else {

				int id = getIdNearest(evt.getX(),evt.getY());
				if (id >= 0 ) {
					textField.setBounds(evt.getX(), evt.getY(), 80, 20);
					textField.setText(String.valueOf(measurePL.get(pointMatched_id).getValue()));
					textField.setVisible(true);

					textFieldUnity.setBounds(evt.getX()+80, evt.getY(), 30, 20);
					textFieldUnity.setText(measurePL.get(pointMatched_id).getMeasureObject().getUnity());
					textFieldUnity.setVisible(true);
				} else {
					textField.setVisible(false);			
					textFieldUnity.setVisible(false);			
				}
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

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public int getBgImgWidth() {
		return bgImgWidth;
	}

	public int getBgImgHeight() {
		return bgImgHeight;
	}

}