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
import gui.pac.CirculatorDistrWin;
import gui.pac.CirculatorSrcWin;
import gui.pac.CompressorWin;
import log4j.Log4j2Config;
import mpoints.EloMeasureResult;
import mpoints.MeasurePoint;
import mpoints.MeasureResult;
import pac.Pac;
import javax.imageio.ImageIO;


public class PanelPacTool extends JPanel implements MouseListener,  MouseMotionListener {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	// --------------------------------------------------------------------
	// Objective is to display the coordinate for the points to match
	// --------------------------------------------------------------------
	private static boolean HELP_FIND_LOCATION = false;	

	/* 	----------------------------------------
	 * 		INSTANCE VAR
	 * ----------------------------------------*/

	private Pac pac;
	private List<MeasurePoint> lMeasurePoints;
	private List<MeasureResult> lMeasureResults;
	private List<EnthalpyElDraw> lEnthalpyElDraw;	

	private CompressorWin compressorWin;
	private CirculatorDistrWin circulatorDistrWin;
	private CirculatorSrcWin circulatorSrcWin;
	private GuiConfig guiConfig;
	private EnthalpyWin enthalpyWin;

	private int bgImgWidth;
	private  int bgImgHeight;

	// -------------------------------------------------------
	//	LOCAL VARIABLES
	// -------------------------------------------------------
	private String imgURL;

	private BufferedImage img;
	private JTextField textField;
	private JTextField textFieldCOP;
	private JTextField textFieldUnity;
	private JButton btnReset;
	private JButton btnReCompute;

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
				Log4j2Config log4j2Config = new Log4j2Config();
				logger.info("Read the Appenders Declared");
				logger.info("  All declared Appenders = {} ",log4j2Config.getAllDeclaredAppenders());
				
				logger.info("Read the Appenders Activated in the Logger");
				logger.info("  Is Logger Console active --> {}", log4j2Config.isLoggerConsole());
				logger.info("  Is Logger File active --> {}", log4j2Config.isLoggerLogFile());
				
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setLayout(new BorderLayout(0, 0));

				PanelPacTool panel1 = new PanelPacTool(new PacToolVar(log4j2Config));		
				frame.getContentPane().add(panel1, BorderLayout.CENTER);

				frame.setBounds(100, 10, panel1.getBgImgWidth()+7, panel1.getBgImgHeight()+49);

				frame.setVisible(true);
			}
		});
	}

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	/**
	 * Create the application.
	 */
	public PanelPacTool(PacToolVar vpacToolVar) {
		lMeasurePoints = vpacToolVar.getlMeasurePoints();
		pac = vpacToolVar.getPac();
		lMeasureResults = vpacToolVar.getlMeasureResults();
		compressorWin = vpacToolVar.getCompressorWin();
		circulatorSrcWin = vpacToolVar.getCirculatorSrcWin();
		circulatorDistrWin = vpacToolVar.getCirculatorDistrWin();
		guiConfig = vpacToolVar.getGuiConfig();
		enthalpyWin = vpacToolVar.getEnthalpyWin();
		
		textFieldCOP = vpacToolVar.getPanelPacToolTextFieldCOP();
		
		imgURL = "/gui/images/Cycle.png";

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
	 * Will return the nearest element id of lMeasurePoints 
	 * @param pX
	 * @param pY
	 * @return
	 */
	public int getIdNearest(int pX, int pY ) {
		int id=-1;
		int zoneX = 5;
		int zoneY = 5;

		for(int i=0;i<lMeasurePoints.size();i++) {
			int X = lMeasurePoints.get(i).getMPObject().getXm();
			int Y = lMeasurePoints.get(i).getMPObject().getYm(); 		
			if (  (Math.abs(pX-X) < zoneX)  && (Math.abs(pY-Y) < zoneY) ) {
				id = i;
			}
		}
		return id;
	}

	/**
	 * Will return the element id of lMeasurePoints for a name 
	 * @param name
	 * @return
	 */
	public int getIdForElem(String name) {
		int id=-1;
		for(int i=0;i<lMeasurePoints.size();i++) {
			if  (lMeasurePoints.get(i).getMPObject().equals(name))
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

		btnReset = new JButton("Reset");
		btnReset.setBounds(24, 292, 61, 23);
		btnReset.setFocusable(false);

		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("Reset to Zero the measure points List");

			}
		});

		btnReCompute = new JButton("Re-Compute");
		btnReCompute.setBounds(24, 30, 93, 23);
		btnReCompute.setFocusable(false);

		btnReCompute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


			}
		});

		textFieldCOP = new JTextField();
		textFieldCOP.setEditable(false);
		textFieldCOP.setBackground(Color.LIGHT_GRAY);
		textFieldCOP.setText("COP");
		textFieldCOP.setBounds(800, 200, 78, 20);
		textFieldCOP.setFont(new Font("Tahoma", Font.BOLD, 11));
		textFieldCOP.setHorizontalAlignment(SwingConstants.CENTER);


		textField = new JTextField();
		textField.setBounds(134, 6, 6, 20);
		textField.setForeground(Color.BLACK);
		textField.setBackground(Color.YELLOW);
		textField.setFont(new Font("Tahoma", Font.BOLD, 11));
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setVisible(false);

		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int id = getIdNearest((int)textField.getX(),(int)textField.getY());
				double inValue = Double.valueOf(textField.getText());

				lMeasurePoints.get(id).setValue(inValue, pac, lMeasurePoints);
				logger.trace("New values added {}",String.format("%.2f", inValue));

				// Fill the list of Measure Results
				for (EloMeasureResult p : EloMeasureResult.values()) {
					lMeasureResults.get(p.id()).setValue(lMeasurePoints,pac);
				}

				// Compute the Draw element based on lMeasurePoints
				for (EloEnthalpyElDraw p : EloEnthalpyElDraw.values()) {
					lEnthalpyElDraw.get(p.ordinal()).set(lMeasurePoints);
				}

				logger.trace("Repaint and Complete enthalpyWin");
				enthalpyWin.repaint();
				enthalpyWin.updateAllTextField();

				;
				
				logger.trace("COP ={}", lMeasureResults.get(EloMeasureResult.COP.id()));
				textFieldCOP.setText(String.valueOf(lMeasureResults.get(EloMeasureResult.COP.id())));

				textField.setVisible(false);
				textFieldUnity.setVisible(false);
			}
		});

		textFieldUnity = new JTextField();
		textFieldUnity.setBounds(145, 6, 6, 20);
		textFieldUnity.setForeground(Color.BLACK);
		textFieldUnity.setBackground(Color.YELLOW);
		textFieldUnity.setFont(new Font("Tahoma", Font.BOLD, 11));
		textFieldUnity.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldUnity.setVisible(false);
		setLayout(null);

		add(textField);
		add(textFieldCOP);
		add(textFieldUnity);
		add(btnReset);
		add(btnReCompute);

	}



	// -------------------------------------------------------
	// 						PAINT 
	// -------------------------------------------------------
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;         

		// Background image
		g2d.drawImage(img,0, 0, this);

		if (pointMatched) {
			int x = lMeasurePoints.get(pointMatched_id).getMPObject().getXm();
			int y = lMeasurePoints.get(pointMatched_id).getMPObject().getYm();

			// Create Circle
			Point2D center = new Point2D.Float(x, y);
			float[] dist = {0.0f, 1.0f};
			Color[] colors = {Color.YELLOW, Color.RED};
			int radius = 10;
			RadialGradientPaint p =  new RadialGradientPaint(center, radius, dist, colors);
			g2d.setPaint(p);
			g2d.fill(new Ellipse2D.Double(x-radius, y-radius, 2*radius, 2*radius));

			// Definition Text 
			String defTxt = lMeasurePoints.get(pointMatched_id).getMPObject().getDefinition(guiConfig.getLanguage());
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
					logger.trace("mousePressed choice: {} ",item);
					compressorWin.setVisible(true);
					break;
				case "CirculatorSource":
					logger.trace("mousePressed choice: {} ",item);
					circulatorSrcWin.setVisible(true);
					break;
				case "CirculatorDistribution":
					logger.trace("mousePressed choice: {} ",item);				
					circulatorDistrWin.setVisible(true);
					break;
				default:
					break;
				}

			} else {

				int id = getIdNearest(evt.getX(),evt.getY());
				if (id >= 0 ) {
					textField.setBounds(evt.getX(), evt.getY(), 80, 20);
					textField.setText(String.valueOf(lMeasurePoints.get(pointMatched_id).getValue()));
					textField.setVisible(true);

					textFieldUnity.setBounds(evt.getX()+80, evt.getY(), 30, 20);
					textFieldUnity.setText(lMeasurePoints.get(pointMatched_id).getMPObject().getUnity());
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

	public void updateCOP(double vCOP) {
		textFieldCOP.setText(String.valueOf(vCOP));
	}
}
