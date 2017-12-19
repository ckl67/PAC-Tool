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

import computation.Comp;
import computation.MeasureTable;
import computation.ResultTable;
import enthalpy.Enthalpy;
import enthalpy.EnthalpyBkgdImg;
import measurePoint.MeasurePoint;
import pac.Pac;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

public class WinConfEnthalpy extends JFrame {

	private static final Logger logger = LogManager.getLogger(WinConfEnthalpy.class.getName());
	private static final long serialVersionUID = 1L;

	/* 	----------------------------------------
	 * 		INSTANCE VAR
	 * ----------------------------------------*/
	WinEnthalpy winEnthalpy;
	private Enthalpy enthalpy;		
	private List<MeasurePoint> measurePointL;
	private List<ElDraw> eDrawL;	
	private MeasureTable measureTable;
	private ResultTable resultTable;
	private Pac pac;
	private JTextField panelPacToolTextFieldCOP;

	private PanelEnthalpy panelEnthalpy;
	private EnthalpyBkgdImg enthalpyBkgdImg;

	/* 	----------------------------------------
	 * 		WIN BUILDER
	 * ----------------------------------------*/
	private JPanel panel;
	private JTextField textFieldEnthalpyFilePath;
	private JTextField textFieldH1X;
	private JTextField textFieldH2X;
	private JTextField textFieldP1Y;
	private JTextField textFieldP2Y;
	private JTextField txtFieldTemperaturePressionFile;

	private JTextField txtFieldSatCurveFile;
	private JTextField textFieldStepH1X;
	private JTextField textFieldStepH2X;
	private JTextField textFieldStepP1Y;
	private JTextField textFieldStepP2Y;

	private JTextField textFieldRefrigeranName;

	// -------------------------------------------------------
	// 				TEST THE APPLICATION STANDALONE 
	// -------------------------------------------------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {		
					Enthalpy enthalpy1= new Enthalpy();
					WinEnthalpy vwinEnthalpy1 = new WinEnthalpy(
							new Pac(), 
							enthalpy1, 
							new MeasureTable(new ArrayList<MeasurePoint>(), new GuiConfig()), 
							new ResultTable(new ArrayList<MeasurePoint>(), new GuiConfig()),
							new ArrayList<MeasurePoint>(),
							new ArrayList<ElDraw>(), 
							new WinPressTemp(enthalpy1),
							new JTextField());
					WinConfEnthalpy frame = new WinConfEnthalpy(vwinEnthalpy1);
					frame.setVisible(true);

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
	public WinConfEnthalpy(WinEnthalpy vwinEnthalpy) {
		winEnthalpy = vwinEnthalpy;
		enthalpy = vwinEnthalpy.getEnthalpy();
		measurePointL = vwinEnthalpy.getMeasurePointL();
		eDrawL = vwinEnthalpy.geteDrawL();
		measureTable = vwinEnthalpy.getMeasureTable();
		resultTable = vwinEnthalpy.getResultTable();
		pac = vwinEnthalpy.getPac();
		panelPacToolTextFieldCOP = vwinEnthalpy.getPanelPacToolTextFieldCOP();
		
		panelEnthalpy = vwinEnthalpy.getPanelEnthalpy();
		enthalpyBkgdImg = enthalpy.getEnthalpyBkgImage();
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
		setIconImage(Toolkit.getDefaultToolkit().getImage(WinConfEnthalpy.class.getResource("/gui/images/PAC-Tool_32.png")));
		setBounds(100, 100, 542, 555);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);

		JButton btnEnthalpyFileChoice = new JButton("Fichier Image");
		btnEnthalpyFileChoice.setBounds(427, 24, 97, 23);
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
					textFieldEnthalpyFilePath.setText(imagepath);
					
					enthalpyBkgdImg.setEnthalpyImageFile(textFieldEnthalpyFilePath.getText());	
					panelEnthalpy.openEnthalpyImageFile();
					
					logger.trace("Reset to Zero the measure points List");
					for (int n = 0; n < measurePointL.size(); n++ ) {
						measurePointL.get(n).clearMeasure();
					}
					
					logger.trace("Remove all Draw elements (eDrawL) (Clear the display)");
					eDrawL.clear();

					logger.trace("Update MeasureTable");
					measureTable.setAllTableValues();

					logger.trace("Update ResultTable");
					resultTable.setAllTableValues();
				
					logger.trace("Repaint and Complete winEnthalpy");
					winEnthalpy.repaint();
					winEnthalpy.updateAllTextField();

					logger.trace("COP ={}", Comp.cop(measurePointL,pac));
					panelPacToolTextFieldCOP.setText(String.valueOf(Comp.cop(measurePointL,pac)));

					
					try {
						if (panelEnthalpy.isVisible() ) {
							panelEnthalpy.clean();
						}
					} catch (NullPointerException e) {

					}
				}
			}
		});
		panel.setLayout(null);
		panel.add(btnEnthalpyFileChoice);

		JButton btnOK = new JButton("Fermer");
		btnOK.setBounds(467, 493, 67, 23);
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println(textFieldEnthalpyFilePath.getText());
				//confEnthalpy.setEnthalpyImageFile(textFieldEnthalpyFilePath.getText());		
				dispose();
			}
		});

		JLabel lblEnthalpyFile = new JLabel("Fichier Image Enthalpie :");
		lblEnthalpyFile.setBounds(10, 28, 125, 14);
		panel.add(lblEnthalpyFile);

		textFieldEnthalpyFilePath = new JTextField();
		textFieldEnthalpyFilePath.setBounds(145, 25, 272, 20);
		textFieldEnthalpyFilePath.setText(enthalpyBkgdImg.getEnthalpyImageFile());
		textFieldEnthalpyFilePath.setColumns(10);
		panel.add(textFieldEnthalpyFilePath);
		panel.add(btnOK);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Enthalpie", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 85, 514, 91);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblHOrigine = new JLabel("New label");
		lblHOrigine.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblHOrigine.setBackground(Color.WHITE);
		lblHOrigine.setIcon(new ImageIcon(WinConfEnthalpy.class.getResource("/gui/images/HOrigLocation.png")));
		lblHOrigine.setBounds(30, 22, 64, 50);
		panel_1.add(lblHOrigine);

		textFieldH1X = new JTextField();
		textFieldH1X.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpy.setxHmin(Integer.valueOf(textFieldH1X.getText()));
				enthalpyBkgdImg.setRefCurveH1x(Integer.valueOf(textFieldH1X.getText()));
				panelEnthalpy.setxHmin(Integer.valueOf(textFieldH1X.getText()));	
			}
		});
		textFieldH1X.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldH1X.setText(String.valueOf(enthalpyBkgdImg.getRefCurveH1x()));
		textFieldH1X.setToolTipText("");
		textFieldH1X.setBounds(156, 25, 46, 20);
		panel_1.add(textFieldH1X);
		textFieldH1X.setColumns(10);

		JLabel lblHFinal = new JLabel("New label");
		lblHFinal.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblHFinal.setIcon(new ImageIcon(WinConfEnthalpy.class.getResource("/gui/images/HFinalLocation.png")));
		lblHFinal.setBounds(274, 21, 64, 53);
		panel_1.add(lblHFinal);

		textFieldH2X = new JTextField();
		textFieldH2X.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enthalpy.setxHmax(Integer.valueOf(textFieldH2X.getText()));
				enthalpyBkgdImg.setRefCurveH2x(Integer.valueOf(textFieldH2X.getText()));	
				panelEnthalpy.setxHmax(Integer.valueOf(textFieldH2X.getText()));	
			}
		});
		textFieldH2X.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldH2X.setText(String.valueOf(enthalpyBkgdImg.getRefCurveH2x()));
		textFieldH2X.setBounds(408, 23, 46, 20);
		panel_1.add(textFieldH2X);
		textFieldH2X.setColumns(10);

		JLabel lblKjkg0 = new JLabel("kJ/kg");
		lblKjkg0.setBounds(211, 28, 46, 14);
		panel_1.add(lblKjkg0);

		JLabel lblKjkg01 = new JLabel("kJ/kg");
		lblKjkg01.setBounds(469, 26, 35, 14);
		panel_1.add(lblKjkg01);

		BasicArrowButton btnArrH1X_Right = new BasicArrowButton(BasicArrowButton.EAST);
		btnArrH1X_Right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgdImg.setiBgH1x(enthalpyBkgdImg.getiBgH1x()- Integer.valueOf(textFieldStepH1X.getText()));
				winEnthalpy.repaint();
			}
		});
		btnArrH1X_Right.setBounds(172, 52, 30, 20);
		panel_1.add(btnArrH1X_Right);

		BasicArrowButton btnArrH1X_left = new BasicArrowButton(BasicArrowButton.WEST);
		btnArrH1X_left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgdImg.setiBgH1x(enthalpyBkgdImg.getiBgH1x()+Integer.valueOf(textFieldStepH1X.getText()));
				winEnthalpy.repaint();
			}
		});
		btnArrH1X_left.setBounds(112, 52, 30, 20);
		panel_1.add(btnArrH1X_left);

		BasicArrowButton btnArrH2X_Right = new BasicArrowButton(BasicArrowButton.EAST);
		btnArrH2X_Right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgdImg.setiBgH2x(enthalpyBkgdImg.getiBgH2x()- Integer.valueOf(textFieldStepH2X.getText()));
				winEnthalpy.repaint();
			}
		});
		btnArrH2X_Right.setBounds(420, 50, 30, 20);
		panel_1.add(btnArrH2X_Right);

		BasicArrowButton btnArrH2X_left = new BasicArrowButton(BasicArrowButton.WEST);
		btnArrH2X_left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgdImg.setiBgH2x(enthalpyBkgdImg.getiBgH2x()+Integer.valueOf(textFieldStepH2X.getText()));
				winEnthalpy.repaint();
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
		panel_2.setBounds(10, 187, 514, 91);
		panel.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblPOrigine = new JLabel("New label");
		lblPOrigine.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPOrigine.setBackground(Color.WHITE);
		lblPOrigine.setIcon(new ImageIcon(WinConfEnthalpy.class.getResource("/gui/images/POrigLocation.png")));
		lblPOrigine.setBounds(30, 22, 64, 50);
		panel_2.add(lblPOrigine);

		textFieldP1Y = new JTextField();
		textFieldP1Y.setToolTipText("Multiple / Log10 ");
		textFieldP1Y.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldP1Y.setText(String.valueOf(enthalpyBkgdImg.getRefCurveP1y()));
		textFieldP1Y.setBounds(155, 22, 46, 20);
		panel_2.add(textFieldP1Y);
		textFieldP1Y.setColumns(10);

		JLabel lblPFinal = new JLabel("New label");
		lblPFinal.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPFinal.setIcon(new ImageIcon(WinConfEnthalpy.class.getResource("/gui/images/PFinalLocation.png")));
		lblPFinal.setBounds(275, 22, 64, 50);
		panel_2.add(lblPFinal);

		textFieldP2Y = new JTextField();
		textFieldP2Y.setToolTipText("Multiple / Log10 ");
		textFieldP2Y.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldP2Y.setText(String.valueOf(enthalpyBkgdImg.getRefCurveP2y()));
		textFieldP2Y.setBounds(410, 19, 46, 20);
		panel_2.add(textFieldP2Y);
		textFieldP2Y.setColumns(10);

		JLabel lblBar0 = new JLabel("bar");
		lblBar0.setBounds(208, 25, 57, 14);
		panel_2.add(lblBar0);

		JLabel lblBar01 = new JLabel("bar");
		lblBar01.setBounds(466, 24, 26, 14);
		panel_2.add(lblBar01);

		BasicArrowButton btnArrP1Y_Right = new BasicArrowButton(BasicArrowButton.NORTH);
		btnArrP1Y_Right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgdImg.setiBgP1y(enthalpyBkgdImg.getiBgP1y()+ Integer.valueOf(textFieldStepP1Y.getText()));
				winEnthalpy.repaint();
			}
		});
		btnArrP1Y_Right.setBounds(171, 51, 30, 20);
		panel_2.add(btnArrP1Y_Right);

		BasicArrowButton btnArrP1Y_left = new BasicArrowButton(BasicArrowButton.SOUTH);
		btnArrP1Y_left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				enthalpyBkgdImg.setiBgP1y(enthalpyBkgdImg.getiBgP1y()- Integer.valueOf(textFieldStepP1Y.getText()));
				winEnthalpy.repaint();
			}
		});
		btnArrP1Y_left.setBounds(111, 51, 30, 20);
		panel_2.add(btnArrP1Y_left);

		BasicArrowButton btnArrP2Y_Right = new BasicArrowButton(BasicArrowButton.NORTH);
		btnArrP2Y_Right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgdImg.setiBgP2y(enthalpyBkgdImg.getiBgP2y()+ Integer.valueOf(textFieldStepP2Y.getText()));
				winEnthalpy.repaint();
			}
		});
		btnArrP2Y_Right.setBounds(420, 50, 30, 20);
		panel_2.add(btnArrP2Y_Right);

		BasicArrowButton btnArrP2Y_left = new BasicArrowButton(BasicArrowButton.SOUTH);
		btnArrP2Y_left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyBkgdImg.setiBgP2y(enthalpyBkgdImg.getiBgP2y()- Integer.valueOf(textFieldStepP2Y.getText()));
				winEnthalpy.repaint();
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

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Relation Temp\u00E9rature / Pression", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(10, 289, 514, 91);
		panel.add(panel_3);

		JLabel lblTempPress = new JLabel("New label");
		lblTempPress.setIcon(new ImageIcon(WinConfEnthalpy.class.getResource("/gui/images/PressionTemperature.png")));
		lblTempPress.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblTempPress.setBackground(Color.WHITE);
		lblTempPress.setBounds(10, 20, 106, 50);
		panel_3.add(lblTempPress);

		txtFieldTemperaturePressionFile = new JTextField();
		txtFieldTemperaturePressionFile.setText("D:\\Users\\kluges1\\workspace\\pac-tool\\ressources\\R22\\P2T_R22.txt");
		txtFieldTemperaturePressionFile.setHorizontalAlignment(SwingConstants.RIGHT);
		txtFieldTemperaturePressionFile.setColumns(10);
		txtFieldTemperaturePressionFile.setBounds(126, 35, 273, 20);
		panel_3.add(txtFieldTemperaturePressionFile);

		JButton buttonLoadTemPressFile = new JButton("Fichier T/P");
		buttonLoadTemPressFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "Temperature-Pression files", "txt");
				chooser.setFileFilter(filter);
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				int returnVal = chooser.showOpenDialog(panel);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					//System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
					String filepath=chooser.getSelectedFile().getAbsolutePath();
					txtFieldTemperaturePressionFile.setText(filepath);
					enthalpy.setFileNameTP(txtFieldTemperaturePressionFile.getText());	

				}
			}
		});
		buttonLoadTemPressFile.setBounds(409, 34, 95, 23);
		panel_3.add(buttonLoadTemPressFile);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Courbe de Staturation", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(10, 391, 514, 91);
		panel.add(panel_4);

		JLabel lblSatCurve = new JLabel("New label");
		lblSatCurve.setIcon(new ImageIcon(WinConfEnthalpy.class.getResource("/gui/images/SaturationCurve.png")));
		lblSatCurve.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblSatCurve.setBackground(Color.WHITE);
		lblSatCurve.setBounds(10, 20, 106, 50);
		panel_4.add(lblSatCurve);

		txtFieldSatCurveFile = new JTextField();
		txtFieldSatCurveFile.setText("D:\\Users\\kluges1\\workspace\\pac-tool\\ressources\\R22\\SaturationCurve_R22.txt");
		txtFieldSatCurveFile.setHorizontalAlignment(SwingConstants.RIGHT);
		txtFieldSatCurveFile.setColumns(10);
		txtFieldSatCurveFile.setBounds(126, 35, 273, 20);
		panel_4.add(txtFieldSatCurveFile);

		JButton btnLoadSatCurveFile = new JButton("Fichier Sat.");
		btnLoadSatCurveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "Saturation Files", "txt");
				chooser.setFileFilter(filter);
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				int returnVal = chooser.showOpenDialog(panel);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					//System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
					String filepath=chooser.getSelectedFile().getAbsolutePath();
					txtFieldSatCurveFile.setText(filepath);
					enthalpy.setFileNameSAT(txtFieldSatCurveFile.getText());	

				}
			}
		} );
		btnLoadSatCurveFile.setBounds(409, 34, 95, 23);
		panel_4.add(btnLoadSatCurveFile);
		
		JLabel lblEnthalpyRefrigerantName = new JLabel("Refrigerant Name :");
		lblEnthalpyRefrigerantName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEnthalpyRefrigerantName.setBounds(20, 60, 104, 14);
		panel.add(lblEnthalpyRefrigerantName);
		
		textFieldRefrigeranName = new JTextField();
		textFieldRefrigeranName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpy.setNameRefrigerant(textFieldRefrigeranName.getText());
				logger.info("Refrigerant Name : {}",enthalpy.getNameRefrigerant());
				winEnthalpy.repaint();
			}
		});
		textFieldRefrigeranName.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldRefrigeranName.setText(enthalpy.getNameRefrigerant());
		textFieldRefrigeranName.setBounds(145, 56, 86, 20);
		panel.add(textFieldRefrigeranName);
		textFieldRefrigeranName.setColumns(10);
	}
	
	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public void applyConfig() {
		
		textFieldRefrigeranName.setText(enthalpy.getNameRefrigerant());
		textFieldEnthalpyFilePath.setText(enthalpyBkgdImg.getEnthalpyImageFile());
		textFieldH1X.setText(String.valueOf(enthalpyBkgdImg.getRefCurveH1x()));
		textFieldH2X.setText(String.valueOf(enthalpyBkgdImg.getRefCurveH2x()));
		textFieldP1Y.setText(String.valueOf(enthalpyBkgdImg.getRefCurveP1y()));
		textFieldP2Y.setText(String.valueOf(enthalpyBkgdImg.getRefCurveP2y()));
		
		

		
	}
}
