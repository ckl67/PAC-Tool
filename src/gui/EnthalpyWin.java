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
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
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
import mpoints.EloMeasurePointSelection;
import mpoints.EloMeasureResult;
import mpoints.EloMeasurePoint;
import mpoints.MeasurePoint;
import mpoints.MeasureResult;
import pac.Pac;
import refrigerant.Refrigerant;
import refrigerant.TSat;
import translation.TLanguage;

import javax.swing.event.ChangeEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.UIManager;

public class EnthalpyWin extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	/* 	----------------------------------------
	 * 		INSTANCE VAR
	 * ----------------------------------------*/

	/* 	----------------------------------------
	 * 		INSTANCE VAR
	 * ----------------------------------------*/
	private Pac pac;
	private List<MeasurePoint> lMeasurePoints;
	private List<MeasureResult> lMeasureResults;
	private EnthalpyBkgImg enthalpyBkgImg;
	private List<EnthalpyElDraw> lEnthalpyElDraw;	


	/* 	----------------------------------------
	 * 		WIN BUILDER
	 * ----------------------------------------*/
	private JTextField panelPacToolTextFieldCOP;

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
					// Create the PAC
					Pac pac = new Pac();

					// Set Gaz used on PAC
					//pac.getRefrigerant().loadNewRefrigerant("./ressources/R407/R407C/Saturation Table R407C Dupont-Suva.txt");
					pac.getRefrigerant().loadNewRefrigerant("./ressources/R22/Saturation Table R22.txt");
					System.out.println(pac.getRefrigerant().getRfgName());

					// Set configuration
					GuiConfig guiConfig = new GuiConfig();
					guiConfig.setLanguage(TLanguage.FRENCH);

					
					// Create the List of measure points
					List<MeasurePoint> lMeasurePoints;
					lMeasurePoints = new ArrayList<MeasurePoint>(); 
					for (EloMeasurePoint p : EloMeasurePoint.values()) {
						lMeasurePoints.add(new MeasurePoint(p));
					}
			
					//Compute Points
					MeasurePoint mp1 = lMeasurePoints.get(EloMeasurePoint.P1.id());
					MeasurePoint mp2 = lMeasurePoints.get(EloMeasurePoint.P2.id());
					MeasurePoint mp3 = lMeasurePoints.get(EloMeasurePoint.P3.id());
					MeasurePoint mp4 = lMeasurePoints.get(EloMeasurePoint.P4.id());
					MeasurePoint mp5 = lMeasurePoints.get(EloMeasurePoint.P5.id());
					MeasurePoint mp6 = lMeasurePoints.get(EloMeasurePoint.P6.id());
					MeasurePoint mp7 = lMeasurePoints.get(EloMeasurePoint.P7.id());
					MeasurePoint mp8 = lMeasurePoints.get(EloMeasurePoint.P8.id());

					mp3.setValue(15, pac, lMeasurePoints);
					mp7.setValue(4, pac, lMeasurePoints);
					mp4.setValue(15, pac, lMeasurePoints);
					mp1.setValue(0, pac, lMeasurePoints);
					mp2.setValue(69, pac, lMeasurePoints);
					mp5.setValue(30, pac, lMeasurePoints);
					mp6.setValue(-10, pac, lMeasurePoints);
					mp8.setValue(0, pac, lMeasurePoints);


					// Create the List of Results
					List<MeasureResult> lMeasureResults;
					lMeasureResults = new ArrayList<MeasureResult>(); 
					for (EloMeasureResult p : EloMeasureResult.values()) {
						lMeasureResults.add(new MeasureResult(p,lMeasurePoints,pac));
					}

					// Create the list of Element Enthalpy Draw
					List<EnthalpyElDraw> lEnthalpyElDraw;
					lEnthalpyElDraw = new ArrayList<EnthalpyElDraw>(); 
					
					//EnthalpyElDraw enthalpyElDraw_P1 = new EnthalpyElDraw(EloEnthalpyElDraw.P1,lMeasurePoints);

					double isoThermT = 15.0;
					for (EloEnthalpyElDraw p : EloEnthalpyElDraw.values()) {
						lEnthalpyElDraw.add(new EnthalpyElDraw(p,lMeasurePoints,pac,isoThermT));
					}

					// Create Background Image
					// EnthalpyBkgImg enthalpyBkgImg = new EnthalpyBkgImg("./ressources/R407/R407C/R407C couleur A4.png");
					EnthalpyBkgImg enthalpyBkgImg = new EnthalpyBkgImg("./ressources/R22/R22 couleur A4.png");
					//enthalpyBkgImg.setRefCurveH1x(100);
					//enthalpyBkgImg.setiBgH1x(136);
					//enthalpyBkgImg.setRefCurveH2x(600);
					//enthalpyBkgImg.setiBgH2x(2724);
					//enthalpyBkgImg.setiBgP1y(1648);;
					//enthalpyBkgImg.setiBgP2y(216);;
					
					// Now we go to create Window image
					EnthalpyWin frame1 = new EnthalpyWin(
							pac, 
							lMeasurePoints, 
							lMeasureResults,
							enthalpyBkgImg,
							lEnthalpyElDraw, 
							new JTextField() );
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
			Pac vpac, 
			List<MeasurePoint> vlMeasurePoints, 
			List<MeasureResult> vlMeasureResults, 
			EnthalpyBkgImg venthalpyBkgImg,
			List<EnthalpyElDraw> vlEnthalpyElDraw,
			JTextField vpanelPacToolTextFieldCOP ) {
		pac = vpac;
		lMeasurePoints = vlMeasurePoints;
		lMeasureResults = vlMeasureResults;
		enthalpyBkgImg = venthalpyBkgImg;

		lEnthalpyElDraw = vlEnthalpyElDraw;
		panelPacToolTextFieldCOP = vpanelPacToolTextFieldCOP;

		pointJPopupMenu = new Point();

		ElDrawIdToMoveOnP = -1;
		initialize();

	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public void applyConfig() {
		enthalpyPanel.setBufBkgdImg(enthalpyPanel.openRefrigerantImageFile());

		Refrigerant refrigerant = pac.getRefrigerant();

		enthalpyPanel.setxHmin(refrigerant.getxHmin());
		enthalpyPanel.setxHmax(refrigerant.getxHmax());
		enthalpyPanel.setyPmin(refrigerant.getyPmin());
		enthalpyPanel.setyPmax(refrigerant.getyPmax());
	}

	public JTextField getPanelPacToolTextFieldCOP() {
		return panelPacToolTextFieldCOP;
	}

	public void updateAllTextField() {
		textPHP.setText(String.format("%.2f",lMeasurePoints.get(0).getMP_P0PK(lMeasurePoints)));
		textPBP.setText(String.format("%.2f",lMeasurePoints.get(0).getMP_P0PK(lMeasurePoints)));
	}


	// -------------------------------------------------------
	// 				 GENERATED BY WIN BUILDER
	//   				--> EXCEPT :: MOUSE MOTION LISTENER
	// -------------------------------------------------------

	/**
	 * Generated by Win builder to create the Popup menu in the panel (Delete Point)
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

		logger.info("Load WinRefrigerant");
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

		// **************************
		// MOUSE MOTION LISTENER  !!
		// **************************
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

				double pResult = enthalpyPanel.getPoYm(evt.getY());
				lblPressureCoord.setText(String.format("P=%.2f bar",pResult));

				// CORRECTION LIQUID OR GAS !!
				double tRresult = refrigerant.getTSatFromP(pResult).getTLiquid();
				lblTempCoord.setText(String.format("T=%.2f °C",tRresult));	

				try {
					if (WinPressTemp.panelTempPressDrawArea.isVisible()) {
						WinPressTemp.panelTempPressDrawArea.spotTempPressFollower(tRresult);
					}
				} catch (NullPointerException e) {
					// Not present ==> Do nothing !
				}

				if (rdbtnSaturation.isSelected()) {
					double pSat = refrigerant.getP_SatCurve_FromH(hResult,pResult);
					double tSat = refrigerant.getT_SatCurve_FromH(hResult,pResult);
					enthalpyPanel.setCurveFollowerX(hResult);
					enthalpyPanel.setCurveFollowerY(pSat);
					if (pSat > 0 )
						lblFollower.setText(String.format("PSat=%.2f / Tsat=%.2f",pSat,tSat));
					else
						lblFollower.setText(String.format("----------"));			
				} else {
					lblFollower.setText(String.format("----------"));
				}

				if (ElDrawIdToMoveOnP >=0 ){
					enthalpyPanel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));

//					lEnthalpyElDraw.get(ElDrawIdToMoveOnP).setX1(hResult);
					String name = lEnthalpyElDraw.get(ElDrawIdToMoveOnP).getTextDisplay(); // T1,T2,..
					for (EloEnthalpyElDraw p : EloEnthalpyElDraw.values()) {
						if (p.name() == name) {
							int n = p.ordinal();  
							//System.out.println(n);
							//System.out.println(measureCollection.getlMeasurePoints().get(n).getMH());
							//		lMeasurePoints.get(n).setMH(hResult);
							//		measureTable.updateTableValues();
							//		resultTable.updateTableValues();
							//System.out.println(measureCollection.getlMeasurePoints().get(n).getMHreal());
						}

					}
				}
				repaint();
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

		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.setIcon(new ImageIcon(EnthalpyWin.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Cut-Black.png")));
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id=0; 
//				id = enthalpyPanel.getIdNearest(lEnthalpyElDraw, EloEnthalpyElDraw.POINT_TXT_ABV,enthalpyPanel.getHoXm((int)pointJPopupMenu.getX()), enthalpyPanel.getPoYm((int)pointJPopupMenu.getY()));
				if (id < 0)
//					id = enthalpyPanel.getIdNearest(lEnthalpyElDraw, EloElDraw.POINT_TXT_BLV, enthalpyPanel.getHoXm((int)pointJPopupMenu.getX()), enthalpyPanel.getPoYm((int)pointJPopupMenu.getY()));

				logger.info(" Element to delete {} ", id);
				if (id >= 0) {
					lEnthalpyElDraw.remove(id);
				}
			}
		});
		popupMenu.add(mntmDelete);

		JMenuItem mntmMove = new JMenuItem("Move");
		mntmMove.setIcon(new ImageIcon(EnthalpyWin.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Paste-Black.png")));
		mntmMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id=0; 
//				int id = enthalpyPanel.getIdNearest(lEnthalpyElDraw, EloElDraw.POINT_TXT_ABV, enthalpyPanel.getHoXm((int)pointJPopupMenu.getX()), enthalpyPanel.getPoYm((int)pointJPopupMenu.getY()));
				if (id < 0)
//					id = enthalpyPanel.getIdNearest(lEnthalpyElDraw, EloElDraw.POINT_TXT_BLV, enthalpyPanel.getHoXm((int)pointJPopupMenu.getX()), enthalpyPanel.getPoYm((int)pointJPopupMenu.getY()));
				logger.info(" Element to move {} ", id);
				if (id >= 0) {
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
		textPHP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double PK = Double.parseDouble(textPHP.getText());


			}
		});
		textPHP.setHorizontalAlignment(SwingConstants.RIGHT);
		textPHP.setText(String.format("%.2f",lMeasurePoints.get(EloMeasurePoint._PK_VAPOR_ID).getValue()));
		panelHight_Hight.add(textPHP);
		textPHP.setColumns(10);

		JLabel lblPBP = new JLabel("BP  (P0)");
		lblPBP.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelHight_Hight.add(lblPBP);

		textPBP = new JTextField();
		textPBP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double P0 = Double.parseDouble(textPBP.getText());


			}
		});
		textPBP.setHorizontalAlignment(SwingConstants.RIGHT);
		textPBP.setText(String.format("%.2f",lMeasurePoints.get(EloMeasurePoint._PR0id).getValue()));
		panelHight_Hight.add(textPBP);
		textPBP.setColumns(10);
		
				JButton btnPressureTemp = new JButton("P/Temp");
				panelHight.add(btnPressureTemp, BorderLayout.SOUTH);
				btnPressureTemp.setMaximumSize(new Dimension(85, 23));
				btnPressureTemp.setAlignmentX(Component.CENTER_ALIGNMENT);
				btnPressureTemp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//			winPressTemp.setVisible(true);
					}
				});

		// ----------------------------------------
		JPanel panelMiddle2 = new JPanel();
		panelRefrigerantRight.add(panelMiddle2);
		panelMiddle2.setLayout(new BorderLayout(0, 0));

		JPanel panelMiddle2_Center = new JPanel();
		panelMiddle2.add(panelMiddle2_Center, BorderLayout.CENTER);
		panelMiddle2_Center.setLayout(new BoxLayout(panelMiddle2_Center, BoxLayout.Y_AXIS));

		rdbtnSaturation = new JRadioButton("Saturation");
		rdbtnSaturation.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelMiddle2_Center.add(rdbtnSaturation);

		rdbtnIsoTherm = new JRadioButton("IsoTherm");
		rdbtnIsoTherm.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelMiddle2_Center.add(rdbtnIsoTherm);
		
		textFieldIsoThermTemp = new JTextField();
		textFieldIsoThermTemp.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldIsoThermTemp.setText("0,00");
		panelMiddle2_Center.add(textFieldIsoThermTemp);
		textFieldIsoThermTemp.setColumns(10);

		rdbtnIsobar = new JRadioButton("IsoBar");
		rdbtnIsobar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelMiddle2_Center.add(rdbtnIsobar);
		
		textFieldIsoBarPressure = new JTextField();
		textFieldIsoBarPressure.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldIsoBarPressure.setText("0,00");
		panelMiddle2_Center.add(textFieldIsoBarPressure);
		textFieldIsoBarPressure.setColumns(10);

		rdbtnNothing = new JRadioButton("Nothing");
		rdbtnNothing.setSelected(true);
		rdbtnNothing.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelMiddle2_Center.add(rdbtnNothing);

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
		panelBottom_Bottom.setLayout(new BoxLayout(panelBottom_Bottom, BoxLayout.Y_AXIS));

		JButton btnResetZoom = new JButton("Centrer");
		btnResetZoom.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnResetZoom.setMaximumSize(new Dimension(85, 23));
		btnResetZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyPanel.centerImg();
				enthalpyPanel.repaint();			
			}
		});
		panelBottom_Bottom.add(btnResetZoom);

		JButton btnClear = new JButton("Effacer");
		btnClear.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnClear.setMaximumSize(new Dimension(85, 23));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpyPanel.clean();
			}
		});
		panelBottom_Bottom.add(btnClear);
	}


	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------


	public EnthalpyPanel getPanelRefrigerant() {
		return enthalpyPanel;
	}
	public List<EnthalpyElDraw> getlEnthalpyElDraw() {
		return lEnthalpyElDraw;
	}

	public List<MeasurePoint> getlMeasurePoints() {
		return lMeasurePoints;
	}


	public Pac getPac() {
		return pac;
	}

}
