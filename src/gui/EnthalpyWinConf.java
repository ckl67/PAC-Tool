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

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicArrowButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import mpoints.EloMeasurePoint;
import mpoints.EloMeasureResult;
import mpoints.MeasurePoint;
import mpoints.MeasureResult;
import pac.Pac;
import translation.TLanguage;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EnthalpyWinConf extends JFrame {

	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());
	private static final long serialVersionUID = 1L;

	/* 	----------------------------------------
	 * 		INSTANCE VAR
	 * ----------------------------------------*/
	EnthalpyWin enthalpyWin;

	/* 	----------------------------------------
	 * 		DEDUCED VAR
	 * ----------------------------------------*/
	private EnthalpyBkgImg enthalpyBkgImg;

	/* 	----------------------------------------
	 * 		WIN BUILDER
	 * ----------------------------------------*/
	private JPanel panel;
	private JTextField textFieldEnthalpyFilePath;
	private JTextField textFieldDstH1;
	private JTextField textFieldDstH2;
	private JTextField textFieldDstP1;
	private JTextField textFieldDstP2;

	private JTextField txtFieldSatCurveFile;
	private JTextField textFieldStepH1X;
	private JTextField textFieldStepH2X;
	private JTextField textFieldStepP1Y;
	private JTextField textFieldStepP2Y;
	private JTextField txtFieldIsoThermCurveFile;

	// -------------------------------------------------------
	// 				TEST THE APPLICATION STANDALONE 
	// -------------------------------------------------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {		
					// Force point (".") as decimal separator --> set your Locale
					Locale.setDefault(new Locale("en", "US"));

					// Create the PAC
					Pac pac = new Pac();
					System.out.println(pac.getRefrigerant().getRfgName());

					// Win Pressure Temperature
					PressTempWin pressTempWin = new PressTempWin(pac.getRefrigerant());
					pressTempWin.setVisible(true);

					
					// Set configuration
					GuiConfig guiConfig = new GuiConfig();
					guiConfig.setLanguage(TLanguage.FRENCH);


					// Create the List of measure points
					List<MeasurePoint> lMeasurePoints;
					lMeasurePoints = new ArrayList<MeasurePoint>(); 
					for (EloMeasurePoint p : EloMeasurePoint.values()) {
						lMeasurePoints.add(new MeasurePoint(p));
					}

					//Enter the Cmesure Points
					lMeasurePoints.get(EloMeasurePoint.P3.id()).setValue(15, pac, lMeasurePoints);
					lMeasurePoints.get(EloMeasurePoint.P7.id()).setValue(4, pac, lMeasurePoints);
					lMeasurePoints.get(EloMeasurePoint.P4.id()).setValue(15, pac, lMeasurePoints);
					lMeasurePoints.get(EloMeasurePoint.P1.id()).setValue(0, pac, lMeasurePoints);
					lMeasurePoints.get(EloMeasurePoint.P2.id()).setValue(69, pac, lMeasurePoints);
					lMeasurePoints.get(EloMeasurePoint.P5.id()).setValue(30, pac, lMeasurePoints);
					lMeasurePoints.get(EloMeasurePoint.P6.id()).setValue(-10, pac, lMeasurePoints);
					lMeasurePoints.get(EloMeasurePoint.P8.id()).setValue(0, pac, lMeasurePoints);

					lMeasurePoints.get(EloMeasurePoint.P1.id()).setValue(10, pac, lMeasurePoints);

					// Now we can display
					MeasurePointTableWin measurePointTableWin = new MeasurePointTableWin(lMeasurePoints, guiConfig); 
					measurePointTableWin.setVisible(true);

					
					// Create the List of Measure Results
					List<MeasureResult> lMeasureResults;
					lMeasureResults = new ArrayList<MeasureResult>(); 
					for (EloMeasureResult p : EloMeasureResult.values()) {
						lMeasureResults.add(new MeasureResult(p));
					}

					// Compute the list of Measure Results
					for (EloMeasureResult p : EloMeasureResult.values()) {
						lMeasureResults.get(p.id()).setValue(lMeasurePoints,pac);
					}

					// Now we can display
					MeasureResultTableWin measureResultTableWin = new MeasureResultTableWin(lMeasureResults, guiConfig); 
					measureResultTableWin.setVisible(true);

					// Create an Empty list of Element Enthalpy Draw
					List<EnthalpyElDraw> lEnthalpyElDraw;
					lEnthalpyElDraw = new ArrayList<EnthalpyElDraw>(); 

					for (EloEnthalpyElDraw p : EloEnthalpyElDraw.values()) {
						lEnthalpyElDraw.add(new EnthalpyElDraw(p));
					}

					// Compute the Draw element based on lMeasurePoints
					for (EloEnthalpyElDraw p : EloEnthalpyElDraw.values()) {
						lEnthalpyElDraw.get(p.ordinal()).set(lMeasurePoints);
					}

					// Create Background Image
					// EnthalpyBkgImg enthalpyBkgImg = new EnthalpyBkgImg("./ressources/R407/R407C/R407C couleur A4.png");
					EnthalpyBkgImg enthalpyBkgImg = new EnthalpyBkgImg("./ressources/R22/R22 couleur A4.png");
					enthalpyBkgImg.loadNewEnthalpyBkgImg("./ressources/R22/R22 couleur A4.png");

					// Now we go to create Window image
					EnthalpyWin frame1 = new EnthalpyWin(
							guiConfig,
							pac, 
							lMeasurePoints, 
							lMeasureResults,
							enthalpyBkgImg,
							lEnthalpyElDraw,
							measurePointTableWin,
							measureResultTableWin,
							pressTempWin);

					frame1.setVisible(true);

					EnthalpyWinConf frame2 = new EnthalpyWinConf(frame1);
					frame2.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	/**
	 * Create the application.
	 */
	public EnthalpyWinConf(EnthalpyWin vwinEnthalpy) {
		enthalpyWin = vwinEnthalpy;
		enthalpyBkgImg = vwinEnthalpy.getEnthalpyBkgImg();
		initialize();
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------


	// -------------------------------------------------------
	// 				 GENERATED BY WIN BUILDER
	// -------------------------------------------------------
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			logger.info(e);
		}

		setTitle("Pac-Tool Configuration Diargramme Enthalpique");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(EnthalpyWinConf.class.getResource("/gui/images/PAC-Tool_32.png")));
		setBounds(100, 100, 537, 512);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Enthalpie", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 382, 514, 91);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblHOrigine = new JLabel("New label");
		lblHOrigine.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblHOrigine.setBackground(Color.WHITE);
		lblHOrigine.setIcon(new ImageIcon(EnthalpyWinConf.class.getResource("/gui/images/HOrigLocation.png")));
		lblHOrigine.setBounds(30, 22, 64, 50);
		panel_1.add(lblHOrigine);

		textFieldDstH1 = new JTextField();
		textFieldDstH1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgImg.setDstH1(Double.valueOf(textFieldDstH1.getText()));
				logger.debug("srcx1={}  srcx2={}  srcy1={}  srcy2={} ",enthalpyBkgImg.getSrcx1(),enthalpyBkgImg.getSrcx2(),enthalpyBkgImg.getSrcy1(),enthalpyBkgImg.getSrcy2());
				logger.debug("dstH1={}  dstH2={}  dstP1={}  dstP2={} ",enthalpyBkgImg.getDstH1(),enthalpyBkgImg.getDstH2(),enthalpyBkgImg.getDstP1(),enthalpyBkgImg.getDstP2());
				enthalpyWin.repaint();
			}
		});
		textFieldDstH1.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldDstH1.setText(String.valueOf(enthalpyBkgImg.getDstH1()));
		textFieldDstH1.setToolTipText("");
		textFieldDstH1.setBounds(156, 25, 46, 20);
		panel_1.add(textFieldDstH1);
		textFieldDstH1.setColumns(10);

		JLabel lblHFinal = new JLabel("New label");
		lblHFinal.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblHFinal.setIcon(new ImageIcon(EnthalpyWinConf.class.getResource("/gui/images/HFinalLocation.png")));
		lblHFinal.setBounds(274, 21, 64, 53);
		panel_1.add(lblHFinal);

		textFieldDstH2 = new JTextField();
		textFieldDstH2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enthalpyBkgImg.setDstH2(Double.valueOf(textFieldDstH2.getText()));
				logger.debug("srcx1={}  srcx2={}  srcy1={}  srcy2={} ",enthalpyBkgImg.getSrcx1(),enthalpyBkgImg.getSrcx2(),enthalpyBkgImg.getSrcy1(),enthalpyBkgImg.getSrcy2());
				logger.debug("dstH1={}  dstH2={}  dstP1={}  dstP2={} ",enthalpyBkgImg.getDstH1(),enthalpyBkgImg.getDstH2(),enthalpyBkgImg.getDstP1(),enthalpyBkgImg.getDstP2());
				enthalpyWin.repaint();
			}
		});
		textFieldDstH2.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldDstH2.setText(String.valueOf(enthalpyBkgImg.getDstH2()));
		textFieldDstH2.setBounds(408, 23, 46, 20);
		panel_1.add(textFieldDstH2);
		textFieldDstH2.setColumns(10);

		JLabel lblKjkg0 = new JLabel("kJ/kg");
		lblKjkg0.setBounds(211, 28, 46, 14);
		panel_1.add(lblKjkg0);

		JLabel lblKjkg01 = new JLabel("kJ/kg");
		lblKjkg01.setBounds(469, 26, 35, 14);
		panel_1.add(lblKjkg01);

		BasicArrowButton btnArrH1X_Right = new BasicArrowButton(BasicArrowButton.EAST);
		btnArrH1X_Right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgImg.setSrcx1(enthalpyBkgImg.getSrcx1()- Integer.valueOf(textFieldStepH1X.getText()));
				logger.debug("srcx1={}  srcx2={}  srcy1={}  srcy2={} ",enthalpyBkgImg.getSrcx1(),enthalpyBkgImg.getSrcx2(),enthalpyBkgImg.getSrcy1(),enthalpyBkgImg.getSrcy2());
				logger.debug("dstH1={}  dstH2={}  dstP1={}  dstP2={} ",enthalpyBkgImg.getDstH1(),enthalpyBkgImg.getDstH2(),enthalpyBkgImg.getDstP1(),enthalpyBkgImg.getDstP2());
				enthalpyWin.repaint();
			}
		});
		btnArrH1X_Right.setBounds(172, 52, 30, 20);
		panel_1.add(btnArrH1X_Right);

		BasicArrowButton btnArrH1X_left = new BasicArrowButton(BasicArrowButton.WEST);
		btnArrH1X_left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgImg.setSrcx1(enthalpyBkgImg.getSrcx1()+Integer.valueOf(textFieldStepH1X.getText()));
				logger.debug("srcx1={}  srcx2={}  srcy1={}  srcy2={} ",enthalpyBkgImg.getSrcx1(),enthalpyBkgImg.getSrcx2(),enthalpyBkgImg.getSrcy1(),enthalpyBkgImg.getSrcy2());
				logger.debug("dstH1={}  dstH2={}  dstP1={}  dstP2={} ",enthalpyBkgImg.getDstH1(),enthalpyBkgImg.getDstH2(),enthalpyBkgImg.getDstP1(),enthalpyBkgImg.getDstP2());
				enthalpyWin.repaint();
			}
		});
		btnArrH1X_left.setBounds(112, 52, 30, 20);
		panel_1.add(btnArrH1X_left);

		BasicArrowButton btnArrH2X_Right = new BasicArrowButton(BasicArrowButton.EAST);
		btnArrH2X_Right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgImg.setSrcx2(enthalpyBkgImg.getSrcx2()- Integer.valueOf(textFieldStepH2X.getText()));
				logger.debug("srcx1={}  srcx2={}  srcy1={}  srcy2={} ",enthalpyBkgImg.getSrcx1(),enthalpyBkgImg.getSrcx2(),enthalpyBkgImg.getSrcy1(),enthalpyBkgImg.getSrcy2());
				logger.debug("dstH1={}  dstH2={}  dstP1={}  dstP2={} ",enthalpyBkgImg.getDstH1(),enthalpyBkgImg.getDstH2(),enthalpyBkgImg.getDstP1(),enthalpyBkgImg.getDstP2());
				enthalpyWin.repaint();
			}
		});
		btnArrH2X_Right.setBounds(420, 50, 30, 20);
		panel_1.add(btnArrH2X_Right);

		BasicArrowButton btnArrH2X_left = new BasicArrowButton(BasicArrowButton.WEST);
		btnArrH2X_left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgImg.setSrcx2(enthalpyBkgImg.getSrcx2()+Integer.valueOf(textFieldStepH2X.getText()));
				logger.debug("srcx1={}  srcx2={}  srcy1={}  srcy2={} ",enthalpyBkgImg.getSrcx1(),enthalpyBkgImg.getSrcx2(),enthalpyBkgImg.getSrcy1(),enthalpyBkgImg.getSrcy2());
				logger.debug("dstH1={}  dstH2={}  dstP1={}  dstP2={} ",enthalpyBkgImg.getDstH1(),enthalpyBkgImg.getDstH2(),enthalpyBkgImg.getDstP1(),enthalpyBkgImg.getDstP2());
				enthalpyWin.repaint();
			}
		});
		btnArrH2X_left.setBounds(360, 50, 30, 20);
		panel_1.add(btnArrH2X_left);

		textFieldStepH1X = new JTextField();
		textFieldStepH1X.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldStepH1X.setText("1");
		textFieldStepH1X.setBounds(142, 52, 30, 20);
		panel_1.add(textFieldStepH1X);
		textFieldStepH1X.setColumns(10);

		textFieldStepH2X = new JTextField();
		textFieldStepH2X.setText("1");
		textFieldStepH2X.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldStepH2X.setColumns(10);
		textFieldStepH2X.setBounds(390, 50, 30, 20);
		panel_1.add(textFieldStepH2X);

		JLabel lblMinH = new JLabel("Min H:");
		lblMinH.setBounds(116, 28, 46, 14);
		panel_1.add(lblMinH);

		JLabel lblMaxH = new JLabel("Max H:");
		lblMaxH.setBounds(363, 26, 46, 14);
		panel_1.add(lblMaxH);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Pression", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 280, 514, 91);
		panel.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblPOrigine = new JLabel("New label");
		lblPOrigine.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPOrigine.setBackground(Color.WHITE);
		lblPOrigine.setIcon(new ImageIcon(EnthalpyWinConf.class.getResource("/gui/images/POrigLocation.png")));
		lblPOrigine.setBounds(30, 22, 64, 50);
		panel_2.add(lblPOrigine);

		textFieldDstP1 = new JTextField();
		textFieldDstP1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enthalpyBkgImg.setDstP1(Double.valueOf(textFieldDstP1.getText()));
				logger.debug("srcx1={}  srcx2={}  srcy1={}  srcy2={} ",enthalpyBkgImg.getSrcx1(),enthalpyBkgImg.getSrcx2(),enthalpyBkgImg.getSrcy1(),enthalpyBkgImg.getSrcy2());
				logger.debug("dstH1={}  dstH2={}  dstP1={}  dstP2={} ",enthalpyBkgImg.getDstH1(),enthalpyBkgImg.getDstH2(),enthalpyBkgImg.getDstP1(),enthalpyBkgImg.getDstP2());
				enthalpyWin.repaint();
			}
		});
		textFieldDstP1.setToolTipText("Multiple / Log10 ");
		textFieldDstP1.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldDstP1.setText(String.valueOf(enthalpyBkgImg.getDstP1()));
		textFieldDstP1.setBounds(155, 22, 46, 20);
		panel_2.add(textFieldDstP1);
		textFieldDstP1.setColumns(10);

		JLabel lblPFinal = new JLabel("New label");
		lblPFinal.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPFinal.setIcon(new ImageIcon(EnthalpyWinConf.class.getResource("/gui/images/PFinalLocation.png")));
		lblPFinal.setBounds(275, 22, 64, 50);
		panel_2.add(lblPFinal);

		textFieldDstP2 = new JTextField();
		textFieldDstP2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enthalpyBkgImg.setDstP2(Double.valueOf(textFieldDstP2.getText()));
				logger.debug("srcx1={}  srcx2={}  srcy1={}  srcy2={} ",enthalpyBkgImg.getSrcx1(),enthalpyBkgImg.getSrcx2(),enthalpyBkgImg.getSrcy1(),enthalpyBkgImg.getSrcy2());
				logger.debug("dstH1={}  dstH2={}  dstP1={}  dstP2={} ",enthalpyBkgImg.getDstH1(),enthalpyBkgImg.getDstH2(),enthalpyBkgImg.getDstP1(),enthalpyBkgImg.getDstP2());
				enthalpyWin.repaint();
			}
		});
		textFieldDstP2.setToolTipText("Multiple / Log10 ");
		textFieldDstP2.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldDstP2.setText(String.valueOf(enthalpyBkgImg.getDstP2()));
		textFieldDstP2.setBounds(410, 19, 46, 20);
		panel_2.add(textFieldDstP2);
		textFieldDstP2.setColumns(10);

		JLabel lblBar0 = new JLabel("bar");
		lblBar0.setBounds(208, 25, 57, 14);
		panel_2.add(lblBar0);

		JLabel lblBar01 = new JLabel("bar");
		lblBar01.setBounds(466, 24, 26, 14);
		panel_2.add(lblBar01);

		BasicArrowButton btnArrP1Y_Right = new BasicArrowButton(BasicArrowButton.NORTH);
		btnArrP1Y_Right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgImg.setSrcy1(enthalpyBkgImg.getSrcy1()+ Integer.valueOf(textFieldStepP1Y.getText()));
				logger.debug("srcx1={}  srcx2={}  srcy1={}  srcy2={} ",enthalpyBkgImg.getSrcx1(),enthalpyBkgImg.getSrcx2(),enthalpyBkgImg.getSrcy1(),enthalpyBkgImg.getSrcy2());
				logger.debug("dstH1={}  dstH2={}  dstP1={}  dstP2={} ",enthalpyBkgImg.getDstH1(),enthalpyBkgImg.getDstH2(),enthalpyBkgImg.getDstP1(),enthalpyBkgImg.getDstP2());
				enthalpyWin.repaint();
			}
		});
		btnArrP1Y_Right.setBounds(171, 51, 30, 20);
		panel_2.add(btnArrP1Y_Right);

		BasicArrowButton btnArrP1Y_left = new BasicArrowButton(BasicArrowButton.SOUTH);
		btnArrP1Y_left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				enthalpyBkgImg.setSrcy1(enthalpyBkgImg.getSrcy1()- Integer.valueOf(textFieldStepP1Y.getText()));
				logger.debug("srcx1={}  srcx2={}  srcy1={}  srcy2={} ",enthalpyBkgImg.getSrcx1(),enthalpyBkgImg.getSrcx2(),enthalpyBkgImg.getSrcy1(),enthalpyBkgImg.getSrcy2());
				logger.debug("dstH1={}  dstH2={}  dstP1={}  dstP2={} ",enthalpyBkgImg.getDstH1(),enthalpyBkgImg.getDstH2(),enthalpyBkgImg.getDstP1(),enthalpyBkgImg.getDstP2());
				enthalpyWin.repaint();
			}
		});
		btnArrP1Y_left.setBounds(111, 51, 30, 20);
		panel_2.add(btnArrP1Y_left);

		BasicArrowButton btnArrP2Y_Right = new BasicArrowButton(BasicArrowButton.NORTH);
		btnArrP2Y_Right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgImg.setSrcy2(enthalpyBkgImg.getSrcy2()+ Integer.valueOf(textFieldStepP2Y.getText()));
				logger.debug("srcx1={}  srcx2={}  srcy1={}  srcy2={} ",enthalpyBkgImg.getSrcx1(),enthalpyBkgImg.getSrcx2(),enthalpyBkgImg.getSrcy1(),enthalpyBkgImg.getSrcy2());
				logger.debug("dstH1={}  dstH2={}  dstP1={}  dstP2={} ",enthalpyBkgImg.getDstH1(),enthalpyBkgImg.getDstH2(),enthalpyBkgImg.getDstP1(),enthalpyBkgImg.getDstP2());
				enthalpyWin.repaint();
			}
		});
		btnArrP2Y_Right.setBounds(420, 50, 30, 20);
		panel_2.add(btnArrP2Y_Right);

		BasicArrowButton btnArrP2Y_left = new BasicArrowButton(BasicArrowButton.SOUTH);
		btnArrP2Y_left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgImg.setSrcy2(enthalpyBkgImg.getSrcy2()- Integer.valueOf(textFieldStepP2Y.getText()));
				logger.debug("srcx1={}  srcx2={}  srcy1={}  srcy2={} ",enthalpyBkgImg.getSrcx1(),enthalpyBkgImg.getSrcx2(),enthalpyBkgImg.getSrcy1(),enthalpyBkgImg.getSrcy2());
				logger.debug("dstH1={}  dstH2={}  dstP1={}  dstP2={} ",enthalpyBkgImg.getDstH1(),enthalpyBkgImg.getDstH2(),enthalpyBkgImg.getDstP1(),enthalpyBkgImg.getDstP2());
				enthalpyWin.repaint();
			}
		});
		btnArrP2Y_left.setBounds(360, 50, 30, 20);
		panel_2.add(btnArrP2Y_left);

		textFieldStepP1Y = new JTextField();
		textFieldStepP1Y.setText("1");
		textFieldStepP1Y.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldStepP1Y.setColumns(10);
		textFieldStepP1Y.setBounds(141, 51, 30, 20);
		panel_2.add(textFieldStepP1Y);

		textFieldStepP2Y = new JTextField();
		textFieldStepP2Y.setText("1");
		textFieldStepP2Y.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldStepP2Y.setColumns(10);
		textFieldStepP2Y.setBounds(390, 50, 30, 20);
		panel_2.add(textFieldStepP2Y);

		JLabel lblMaxP = new JLabel("Max P:");
		lblMaxP.setBounds(360, 22, 46, 14);
		panel_2.add(lblMaxP);

		JLabel lblMinP = new JLabel("Min P:");
		lblMinP.setBounds(113, 25, 37, 14);
		panel_2.add(lblMinP);

		JLabel lbllog = new JLabel("/(Log10)");
		lbllog.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lbllog.setBounds(211, 40, 46, 14);
		panel_2.add(lbllog);

		JLabel label = new JLabel("/(Log10)");
		label.setFont(new Font("Tahoma", Font.PLAIN, 9));
		label.setBounds(466, 40, 46, 14);
		panel_2.add(label);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Courbe de Staturation", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(10, 11, 514, 175);
		panel.add(panel_4);

		JLabel lblSatCurve = new JLabel("New label");
		lblSatCurve.setIcon(new ImageIcon(EnthalpyWinConf.class.getResource("/gui/images/SaturationCurve.png")));
		lblSatCurve.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblSatCurve.setBackground(Color.WHITE);
		lblSatCurve.setBounds(10, 20, 95, 50);
		panel_4.add(lblSatCurve);

		txtFieldSatCurveFile = new JTextField();
		txtFieldSatCurveFile.setText(enthalpyWin.getPac().getRefrigerant().getGasFileNameSatCurve());
		txtFieldSatCurveFile.setHorizontalAlignment(SwingConstants.LEFT);
		txtFieldSatCurveFile.setColumns(10);
		txtFieldSatCurveFile.setBounds(126, 35, 273, 20);
		panel_4.add(txtFieldSatCurveFile);

		JButton btnLoadSatCurveFile = new JButton("Saturation");
		btnLoadSatCurveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "Saturation Files", "txt");
				chooser.setFileFilter(filter);
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				int returnVal = chooser.showOpenDialog(panel);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					String filepath=chooser.getSelectedFile().getAbsolutePath();
					logger.info("You chose to open this file: {} ",filepath);
					txtFieldSatCurveFile.setText(filepath);

				}
			}
		} );
		btnLoadSatCurveFile.setBounds(409, 34, 95, 23);
		panel_4.add(btnLoadSatCurveFile);
		
		txtFieldIsoThermCurveFile = new JTextField();
		txtFieldIsoThermCurveFile.setText(enthalpyWin.getPac().getRefrigerant().getGasFileNameIsoThermCurve());
		txtFieldIsoThermCurveFile.setHorizontalAlignment(SwingConstants.LEFT);
		txtFieldIsoThermCurveFile.setColumns(10);
		txtFieldIsoThermCurveFile.setBounds(126, 96, 273, 20);
		panel_4.add(txtFieldIsoThermCurveFile);
		
		JLabel label_1 = new JLabel("New label");
		label_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		label_1.setBackground(Color.WHITE);
		label_1.setBounds(10, 81, 95, 50);
		panel_4.add(label_1);
		
		JButton btnLoadIsoThermCurveFile = new JButton("IsoTherm");
		btnLoadIsoThermCurveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "IsoTherm Files", "txt");
				chooser.setFileFilter(filter);
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				int returnVal = chooser.showOpenDialog(panel);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					String filepath=chooser.getSelectedFile().getAbsolutePath();
					logger.info("You chose to open this file: {} ",filepath);
					txtFieldIsoThermCurveFile.setText(filepath);

				}
			}
		});
		btnLoadIsoThermCurveFile.setBounds(409, 95, 95, 23);
		panel_4.add(btnLoadIsoThermCurveFile);
		
		JButton buttonLoadNewGas = new JButton("Load Refregirant");
		buttonLoadNewGas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (enthalpyWin.getPac().getRefrigerant().loadNewRefrigerant(
						txtFieldSatCurveFile.getText(),
						txtFieldIsoThermCurveFile.getText())) {
					enthalpyWin.repaint();
				} else {
					buttonLoadNewGas.setText("Not Same Refrigerant !");	
				}
			}
		});
		buttonLoadNewGas.setBounds(206, 135, 122, 23);
		panel_4.add(buttonLoadNewGas);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Fichier Image Enthalpie", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(10, 197, 514, 72);
		panel.add(panel_3);

		textFieldEnthalpyFilePath = new JTextField();
		textFieldEnthalpyFilePath.setBounds(41, 31, 353, 20);
		panel_3.add(textFieldEnthalpyFilePath);
		textFieldEnthalpyFilePath.setText(enthalpyBkgImg.getRefrigerantImageFile());
		textFieldEnthalpyFilePath.setColumns(10);

		JButton btnEnthalpyFileChoice = new JButton("Fichier Image");
		btnEnthalpyFileChoice.setBounds(407, 30, 97, 23);
		panel_3.add(btnEnthalpyFileChoice);
		btnEnthalpyFileChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "Enthalpie files", "png");
				chooser.setFileFilter(filter);
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				int returnVal = chooser.showOpenDialog(panel);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					//System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
					String imagepath=chooser.getSelectedFile().getAbsolutePath();

					logger.info("You chose to open this file: {} ",imagepath);
					textFieldEnthalpyFilePath.setText(imagepath);

					// Create Background Image
					// EnthalpyBkgImg enthalpyBkgImg = new EnthalpyBkgImg("./ressources/R407/R407C/R407C couleur A4.png");
					enthalpyWin.getEnthalpyBkgImg().loadNewEnthalpyBkgImg(imagepath);
					enthalpyWin.repaint();
				}
			}
		});
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public void applyConfig() {

		textFieldDstH1.setText(String.valueOf(enthalpyBkgImg.getDstH1()));
		textFieldDstH2.setText(String.valueOf(enthalpyBkgImg.getDstH2()));
		textFieldDstP1.setText(String.valueOf(enthalpyBkgImg.getDstP2()));
		textFieldDstP2.setText(String.valueOf(enthalpyBkgImg.getDstP1()));




	}
}
