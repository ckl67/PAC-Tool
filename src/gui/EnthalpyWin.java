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

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import log4j.Log4j2Config;
import mpoints.EloMeasureResult;
import mpoints.EloMeasurePoint;
import mpoints.MeasurePoint;
import mpoints.MeasureResult;
import pac.Pac;
import refrigerant.Refrigerant;
import translation.TLanguage;
import javax.swing.event.ChangeEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.UIManager;
import java.awt.SystemColor;


public class EnthalpyWin extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	/* 	----------------------------------------
	 * 		INSTANCE VAR
	 * ----------------------------------------*/
	private GuiConfig guiConfig;
	private Pac pac;
	private List<MeasurePoint> lMeasurePoints;
	private List<MeasureResult> lMeasureResults;
	private List<EnthalpyElDraw> lEnthalpyElDraw;	
	private EnthalpyBkgImg enthalpyBkgImg;
	private MeasurePointTableWin measurePointTableWin;
	private MeasureResultTableWin measureResultTableWin;

	private PressTempWin pressTempWin;

	/* 	----------------------------------------
	 * 		WIN BUILDER
	 * ----------------------------------------*/
	private EnthalpyPanel enthalpyPanel;

	private JLabel lblMouseCoordinate;
	private JLabel lblRefrigerantCoord;
	private JLabel lblPressureCoord;
	private JLabel lblTempCoord;
	private JLabel lblFollower;
	private JRadioButton rdbtnSaturation;
	private JRadioButton rdbtnNothing;
	private JTextField textPHP;
	private JTextField textPBP;
	private static Point pointJPopupMenu; 	// JPopupMenu's Position --> Must be static

	private int ElDrawIdToMoveOnP;
	private JRadioButton rdbtnIsoTherm;
	private JRadioButton rdbtnIsobar;
	private JTextField textFieldIsoThermTemp;
	private JTextField textFieldIsoBarPressure;

	// -------------------------------------------------------
	// 				TEST THE APPLICATION STANDALONE 
	// -------------------------------------------------------
	public static void main(String[] args) {
		new Log4j2Config();
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					// Force point (".") as decimal separator --> set your Locale
					Locale.setDefault(new Locale("en", "US"));

					// Create the PAC
					Pac pac = new Pac();

					// Set Gaz used on PAC
					//pac.getRefrigerant().loadNewRefrigerant("./ressources/R407/R407C/Saturation Table R407C Dupont-Suva.txt");
					pac.getRefrigerant().loadNewRefrigerant("./ressources/R22/Saturation Table R22.txt");
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

					//Enter the Compute Points
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

					// Fill the list of Measure Results
					for (EloMeasureResult p : EloMeasureResult.values()) {
						lMeasureResults.get(p.id()).setValue(lMeasurePoints,pac);
					}

					// Now we can display
					MeasureResultTableWin measureResultTableWin = new MeasureResultTableWin(lMeasureResults, guiConfig); 
					measureResultTableWin.setVisible(true);

					
					// Create an Empty list of Element Enthalpy Draw
					// add set the correct value based on the information computed in lMeasurePoints
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

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public EnthalpyWin(
			GuiConfig vguiConfig,
			Pac vpac, 
			List<MeasurePoint> vlMeasurePoints, 
			List<MeasureResult> vlMeasureResults, 
			EnthalpyBkgImg venthalpyBkgImg,
			List<EnthalpyElDraw> vlEnthalpyElDraw, 
			MeasurePointTableWin vmeasurePointTableWin,
			MeasureResultTableWin vmeasureResultTableWin,
			PressTempWin vwinPressTemp
			) {
		guiConfig = vguiConfig;
		pac = vpac;
		lMeasurePoints = vlMeasurePoints;
		lMeasureResults = vlMeasureResults;
		lEnthalpyElDraw = vlEnthalpyElDraw;
		measurePointTableWin = vmeasurePointTableWin;
		measureResultTableWin = vmeasureResultTableWin;
		pressTempWin = vwinPressTemp;

		enthalpyBkgImg = venthalpyBkgImg;

		pointJPopupMenu = new Point();

		ElDrawIdToMoveOnP = -1;
		initialize();

	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public void updateAllTextField() {
		textPHP.setText(String.format("%.2f",lMeasurePoints.get(0).getMP_P0PK(lMeasurePoints)));
		textPBP.setText(String.format("%.2f",lMeasurePoints.get(0).getMP_P0PK(lMeasurePoints)));
	}

	// -------------------------------------------------------
	// 				 GENERATED BY WIN BUILDER
	//   				--> EXCEPT :: MOUSE MOTION LISTENER
	// -------------------------------------------------------

	/**
	 * Generated by Win builder to create the Popup menu in the panel 
	 */
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				pointJPopupMenu = e.getPoint();
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			logger.info(e);
		}

		setTitle("Diagramme Enthalpique");
		setBounds(100, 100, 800, 500);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(EnthalpyWin.class.getResource("/gui/images/PAC-Tool_16.png")));
		getContentPane().setLayout(new BorderLayout(0, 0));

		// ----------------------------------------
		// Panel Bottom with info 
		// ----------------------------------------

		JPanel panelRefrigerantBottom = new JPanel();
		getContentPane().add(panelRefrigerantBottom, BorderLayout.SOUTH);

		lblMouseCoordinate = new JLabel("Mouse Coordinate");
		lblMouseCoordinate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMouseCoordinate.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelRefrigerantBottom.setLayout(new GridLayout(0, 5, 0, 0));

		lblRefrigerantCoord = new JLabel("Refrigerant Coordinate");
		lblRefrigerantCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblRefrigerantCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelRefrigerantBottom.add(lblRefrigerantCoord);

		lblPressureCoord = new JLabel("Pressure Coordinate");
		lblPressureCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblPressureCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelRefrigerantBottom.add(lblPressureCoord);

		lblTempCoord = new JLabel("Temperature Coordinate");
		lblTempCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblTempCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelRefrigerantBottom.add(lblTempCoord);

		lblFollower = new JLabel("Follower");
		lblFollower.setHorizontalAlignment(SwingConstants.CENTER);
		lblFollower.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelRefrigerantBottom.add(lblFollower);

		panelRefrigerantBottom.add(lblMouseCoordinate);
		JPanel panelRefrigerantRight = new JPanel();
		panelRefrigerantRight.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(panelRefrigerantRight, BorderLayout.EAST);
		panelRefrigerantRight.setLayout(new GridLayout(0, 1, 0, 0));

		// ----------------------------------------
		// Panel Draw Refrigerant : Base
		// ----------------------------------------

		enthalpyPanel = new EnthalpyPanel(pac.getRefrigerant(), enthalpyBkgImg, lEnthalpyElDraw);	
		enthalpyPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		enthalpyPanel.setBackground(Color.WHITE);
		getContentPane().add(enthalpyPanel, BorderLayout.CENTER);

		// ********************************************************************************************
		// 									MOUSE MOTION LISTENER  !!
		// ********************************************************************************************
		enthalpyPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				ElDrawIdToMoveOnP = -1;
				enthalpyPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		enthalpyPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent evt) {

				Refrigerant refrigerant = pac.getRefrigerant();

				lblMouseCoordinate.setText(String.format("(x: %d y: %d)", evt.getX(), evt.getY()));

				double hResult = enthalpyPanel.getHoXm(evt.getX());
				lblRefrigerantCoord.setText(String.format("H=%.2f kJ/kg",hResult));

				double pResult = enthalpyPanel.getP_LogP(enthalpyPanel.getLogPoYm(evt.getY()));
				lblPressureCoord.setText(String.format("P=%.2f bar",pResult));

				double tRresultL = refrigerant.getTSatFromP(pResult).getTLiquid();
				double tRresultG = refrigerant.getTSatFromP(pResult).getTGas();
				lblTempCoord.setText(String.format("T=%.2f / %.2f °C",tRresultL,tRresultG));	

				if (rdbtnSaturation.isSelected()) {
					// Hide Isotherm
					lEnthalpyElDraw.get(EloEnthalpyElDraw.ISOTHERM.ordinal()).setVisible(false);

					double pSat = refrigerant.getP_SatCurve_FromH(hResult,pResult);
					double tSat = refrigerant.getT_SatCurve_FromH(hResult,pResult);
					enthalpyPanel.setHCurveFollower(hResult);
					enthalpyPanel.setPCurveFollower(pSat);
					lblFollower.setText(String.format("PSat=%.2f / Tsat=%.2f",pSat,tSat));
					repaint();

					pressTempWin.getPanelTempPressDrawArea().spotTempPressFollower(tSat);

				} else if (rdbtnIsoTherm.isSelected()) {
					double tIsotherm = Double.parseDouble(textFieldIsoThermTemp.getText()); 

					lEnthalpyElDraw.get(EloEnthalpyElDraw.ISOTHERM.ordinal()).set(lMeasurePoints, pac, tIsotherm);
					lEnthalpyElDraw.get(EloEnthalpyElDraw.ISOTHERM.ordinal()).setVisible(true);

					double pIsotherm = refrigerant.getPIsotherm(hResult, tIsotherm, pResult);
					double hIsotherm = refrigerant.getHIsotherm(hResult, tIsotherm);
					enthalpyPanel.setHCurveFollower(hIsotherm);
					enthalpyPanel.setPCurveFollower(pIsotherm);
					lblFollower.setText(String.format("IsoTherm P=%.2f / T=%.2f",pIsotherm,tIsotherm));
					repaint();


					pressTempWin.getPanelTempPressDrawArea().spotTempPressFollower(tIsotherm);


				} else if (rdbtnIsobar.isSelected()) {
					// Hide Isotherm
					lEnthalpyElDraw.get(EloEnthalpyElDraw.ISOTHERM.ordinal()).setVisible(false);

					double pIsobar = Double.parseDouble(textFieldIsoBarPressure.getText()); 
					double tIsobar = refrigerant.getIsobaricT(pIsobar,hResult);
					enthalpyPanel.setHCurveFollower(hResult);
					enthalpyPanel.setPCurveFollower(pIsobar);
					if (hResult > pac.getRefrigerant().getHSatFromP(pIsobar).getHGas())
						lblFollower.setText(String.format("IsoBar P=%.2f / T=~%.2f",pIsobar,tIsobar));
					else
						lblFollower.setText(String.format("IsoBar P=%.2f / T=%.2f",pIsobar,tIsobar));

					repaint();

					pressTempWin.getPanelTempPressDrawArea().spotTempPressFollower(tIsobar);

				}
				else {
					lblFollower.setText(String.format("----------"));
				}

				if (ElDrawIdToMoveOnP >=0 ){
					enthalpyPanel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));

					int mpid = lEnthalpyElDraw.get(ElDrawIdToMoveOnP).getEloMeasurePoint();
					logger.trace("ElDrawId to move={}  --> Mesure Point id={} ",ElDrawIdToMoveOnP,mpid);

					// Update H for Selected Measure point
					logger.info("Before MP({})={}",mpid,lMeasurePoints.get(mpid).getMP_H());
					lMeasurePoints.get(mpid).setMP_H(hResult);
					logger.info("After MP({})={}",mpid,lMeasurePoints.get(mpid).getMP_H());

					// Fill the list of Measure Results
					for (EloMeasureResult p : EloMeasureResult.values()) {
						lMeasureResults.get(p.id()).setValue(lMeasurePoints,pac);
					}

					// Compute the Draw element based on lMeasurePoints
					for (EloEnthalpyElDraw p : EloEnthalpyElDraw.values()) {
						lEnthalpyElDraw.get(p.ordinal()).set(lMeasurePoints);
					}
					repaint();
					measurePointTableWin.updateTableValues(lMeasurePoints,guiConfig);
					measureResultTableWin.updateTableValues(lMeasureResults, guiConfig);				

				}
			}
		});

		// ----------------------------------------
		// Panel Draw Refrigerant : Slider (Smooth the background image) 
		// ----------------------------------------

		JSlider slider = new JSlider();
		slider.setBackground(Color.WHITE);
		slider.setFocusable(false);
		slider.setValue((int)(enthalpyPanel.getAlphaBlurBkgdImg()*100));

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int v = slider.getValue();
				enthalpyPanel.setImageAlphaBlure((float) v / slider.getMaximum());
				enthalpyPanel.repaint();
			}
		});
		enthalpyPanel.add(slider, BorderLayout.NORTH);

		// ----------------------------------------
		// Panel Draw Refrigerant : POPUP Menu
		// ----------------------------------------
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(enthalpyPanel, popupMenu);

		JMenuItem mntmMove = new JMenuItem("Move");
		mntmMove.setIcon(new ImageIcon(EnthalpyWin.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Paste-Black.png")));
		mntmMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id = enthalpyPanel.getIdNearestPoint(lEnthalpyElDraw, (int)pointJPopupMenu.getX(), (int)pointJPopupMenu.getY());
				if (id >= 0) {
					logger.info(" Element to move {} ", id);
					ElDrawIdToMoveOnP = id;
				}
			}
		});
		popupMenu.add(mntmMove);

		// ----------------------------------------
		// Command on the Right side (different level of JPanels 
		// ----------------------------------------
		JPanel panelHight = new JPanel();
		panelRefrigerantRight.add(panelHight);
		panelHight.setLayout(new BorderLayout(0, 0));

		JPanel panelHight_Hight = new JPanel();
		panelHight.add(panelHight_Hight, BorderLayout.NORTH);
		panelHight_Hight.setLayout(new BoxLayout(panelHight_Hight, BoxLayout.Y_AXIS));

		JLabel lblPHP = new JLabel("HP (PK)");
		lblPHP.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPHP.setHorizontalAlignment(SwingConstants.LEFT);
		panelHight_Hight.add(lblPHP);

		textPHP = new JTextField();
		textPHP.setBackground(SystemColor.controlHighlight);
		textPHP.setEditable(false);
		textPHP.setHorizontalAlignment(SwingConstants.RIGHT);
		textPHP.setText(String.format("%.2f",lMeasurePoints.get(EloMeasurePoint._PK_VAPOR_ID).getValue()));
		panelHight_Hight.add(textPHP);
		textPHP.setColumns(10);

		JLabel lblPBP = new JLabel("BP  (P0)");
		lblPBP.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelHight_Hight.add(lblPBP);

		textPBP = new JTextField();
		textPBP.setBackground(SystemColor.controlHighlight);
		textPBP.setEditable(false);
		textPBP.setHorizontalAlignment(SwingConstants.RIGHT);
		textPBP.setText(String.format("%.2f",lMeasurePoints.get(EloMeasurePoint._PR0id).getValue()));
		panelHight_Hight.add(textPBP);
		textPBP.setColumns(10);

		// ----------------------------------------
		JPanel panelMiddle = new JPanel();
		panelRefrigerantRight.add(panelMiddle);
		panelMiddle.setLayout(new BorderLayout(0, 0));

		JPanel panelMiddle_South = new JPanel();
		panelMiddle.add(panelMiddle_South, BorderLayout.SOUTH);
		panelMiddle_South.setLayout(new BoxLayout(panelMiddle_South, BoxLayout.Y_AXIS));

		rdbtnSaturation = new JRadioButton("Saturation");
		rdbtnSaturation.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelMiddle_South.add(rdbtnSaturation);

		rdbtnIsoTherm = new JRadioButton("IsoTherm");
		rdbtnIsoTherm.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelMiddle_South.add(rdbtnIsoTherm);

		textFieldIsoThermTemp = new JTextField();
		textFieldIsoThermTemp.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldIsoThermTemp.setText("0.00");
		panelMiddle_South.add(textFieldIsoThermTemp);
		textFieldIsoThermTemp.setColumns(10);

		rdbtnIsobar = new JRadioButton("IsoBar");
		rdbtnIsobar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelMiddle_South.add(rdbtnIsobar);

		textFieldIsoBarPressure = new JTextField();
		textFieldIsoBarPressure.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				if ( Double.parseDouble(textFieldIsoBarPressure.getText()) <enthalpyPanel.getPgMin())
				{
					String tmp; 
					tmp = String.format("%.2f",enthalpyPanel.getPgMin());
					textFieldIsoBarPressure.setText(tmp);
				}		
			}
		});
		textFieldIsoBarPressure.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldIsoBarPressure.setText(String.format("%.2f",enthalpyPanel.getPgMin()));
		panelMiddle_South.add(textFieldIsoBarPressure);
		textFieldIsoBarPressure.setColumns(10);

		rdbtnNothing = new JRadioButton("Nothing");
		rdbtnNothing.setSelected(true);
		rdbtnNothing.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelMiddle_South.add(rdbtnNothing);

		ButtonGroup btGroupPosFollower = new ButtonGroup();
		btGroupPosFollower.add(rdbtnSaturation);
		btGroupPosFollower.add(rdbtnIsoTherm);
		btGroupPosFollower.add(rdbtnIsobar);
		btGroupPosFollower.add(rdbtnNothing);

		// ----------------------------------------
		JPanel panelBottom = new JPanel();
		panelRefrigerantRight.add(panelBottom);
		panelBottom.setLayout(new BorderLayout(0, 0));

		JPanel panelBottom_Bottom = new JPanel();
		panelBottom.add(panelBottom_Bottom, BorderLayout.SOUTH);

		JButton btnResetZoom = new JButton("Centrer");
		btnResetZoom.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnResetZoom.setMaximumSize(new Dimension(85, 23));
		btnResetZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyPanel.centerImg();		
			}
		});
		panelBottom_Bottom.setLayout(new BorderLayout(0, 0));
		panelBottom_Bottom.add(btnResetZoom);

		JButton btnClear = new JButton("Recalcule");
		panelBottom_Bottom.add(btnClear, BorderLayout.NORTH);
		btnClear.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnClear.setMaximumSize(new Dimension(85, 23));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				computeAll();
				repaint();
			}
		});
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public void applyConfig() {
		
	}

	private void computeAll() {
		// Fill the list of Measure Results
		for (EloMeasureResult p : EloMeasureResult.values()) {
			lMeasureResults.get(p.id()).setValue(lMeasurePoints,pac);
		}

		// Compute the Draw element based on lMeasurePoints
		for (EloEnthalpyElDraw p : EloEnthalpyElDraw.values()) {
			lEnthalpyElDraw.get(p.ordinal()).set(lMeasurePoints);
		}

	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public EnthalpyPanel getEnthalpyPanel() {
		return enthalpyPanel;
	}

	public EnthalpyBkgImg getEnthalpyBkgImg() {
		return enthalpyBkgImg;
	}

	public Pac getPac() {
		return pac;
	}

}
