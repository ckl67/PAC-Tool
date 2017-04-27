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
import computation.MeasurePoint;
import computation.MeasureTable;
import computation.ResultTable;
import computation.Comp;
import computation.MeasureChoiceStatus;
import computation.MeasureObject;
import enthalpy.Enthalpy;
import log4j.Log4j2Config;
import pac.Pac;
import javax.swing.event.ChangeEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.UIManager;


public class WinEnthalpy extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(WinEnthalpy.class.getName());

	/* 	----------------------------------------
	 * 		INSTANCE VAR
	 * ----------------------------------------*/
	private Enthalpy enthalpy;		
	private List<MeasurePoint> measurePointL;
	private List<ElDraw> eDrawL;	
	private MeasureTable measureTable;
	private ResultTable resultTable;
	private Pac pac;
	private WinPressTemp winPressTemp;

	private JTextField panelPacToolTextFieldCOP;

	/* 	----------------------------------------
	 * 		WIN BUILDER
	 * ----------------------------------------*/
	private PanelEnthalpy panelEnthalpyDrawArea;
	private JLabel lblMouseCoordinate;
	private JLabel lblEnthalpyCoord;
	private JLabel lblPressureCoord;
	private JLabel lblTempCoord;
	private JLabel lblFollower;
	private JRadioButton rdbtnSaturation;
	private JRadioButton rdbtnNothin;
	private JTextField textPHP;
	private JTextField textPBP;
	private static Point pointJPopupMenu; 	// JPopupMenu's Position --> Must be static

	private int ElDrawIdToMoveOnP;

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
					Enthalpy enthalpy1= new Enthalpy();
					WinEnthalpy frame1 = new WinEnthalpy(
							new Pac(), 
							enthalpy1, 
							new MeasureTable(new ArrayList<MeasurePoint>(), new GuiConfig()), 
							new ResultTable(new ArrayList<MeasurePoint>(), new GuiConfig()),
							new ArrayList<MeasurePoint>(),
							new ArrayList<ElDraw>(), 
							new WinPressTemp(enthalpy1),
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
	public WinEnthalpy(Pac vpac, Enthalpy venthalpy, MeasureTable vmeasureTable, ResultTable vresultTable, List<MeasurePoint> vmeasurePointL, List<ElDraw> veDrawL, WinPressTemp vwinPressTemp, JTextField vpanelPacToolTextFieldCOP ) {
		pac = vpac;
		enthalpy = venthalpy;
		measureTable = vmeasureTable;
		resultTable = vresultTable;
		measurePointL = vmeasurePointL;
		eDrawL = veDrawL;
		winPressTemp = vwinPressTemp;
		panelPacToolTextFieldCOP = vpanelPacToolTextFieldCOP;

		pointJPopupMenu = new Point();

		ElDrawIdToMoveOnP = -1;
		initialize();

	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public void updateAllTextField() {
		textPHP.setText(String.format("%.2f",measurePointL.get(MeasureObject._PK_ID).getValue()));
		textPBP.setText(String.format("%.2f",measurePointL.get(MeasureObject._P0_ID).getValue()));

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

		logger.info("Load WinEnthalpy");
		setTitle("Diagramme Enthalpique");
		setBounds(100, 100, 800, 500);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(WinEnthalpy.class.getResource("/gui/images/PAC-Tool_16.png")));
		getContentPane().setLayout(new BorderLayout(0, 0));

		// ----------------------------------------
		// Panel Bottom with info 
		// ----------------------------------------

		JPanel panelEnthalpyBottom = new JPanel();
		getContentPane().add(panelEnthalpyBottom, BorderLayout.SOUTH);

		lblMouseCoordinate = new JLabel("Mouse Coordinate");
		lblMouseCoordinate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMouseCoordinate.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.setLayout(new GridLayout(0, 5, 0, 0));

		lblEnthalpyCoord = new JLabel("Enthalpy Coordinate");
		lblEnthalpyCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnthalpyCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.add(lblEnthalpyCoord);

		lblPressureCoord = new JLabel("Pressure Coordinate");
		lblPressureCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblPressureCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.add(lblPressureCoord);

		lblTempCoord = new JLabel("Temperature Coordinate");
		lblTempCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblTempCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.add(lblTempCoord);

		lblFollower = new JLabel("Follower");
		lblFollower.setHorizontalAlignment(SwingConstants.CENTER);
		lblFollower.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.add(lblFollower);

		panelEnthalpyBottom.add(lblMouseCoordinate);
		JPanel panelEnthalpyRight = new JPanel();
		panelEnthalpyRight.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(panelEnthalpyRight, BorderLayout.EAST);
		panelEnthalpyRight.setLayout(new GridLayout(0, 1, 0, 0));

		// ----------------------------------------
		// Panel Draw Enthalpy : Base
		// ----------------------------------------

		panelEnthalpyDrawArea = new PanelEnthalpy(enthalpy,eDrawL);	
		panelEnthalpyDrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		panelEnthalpyDrawArea.setBackground(Color.WHITE);
		getContentPane().add(panelEnthalpyDrawArea, BorderLayout.CENTER);

		// **************************
		// MOUSE MOTION LISTENER  !!
		// **************************
		panelEnthalpyDrawArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				ElDrawIdToMoveOnP = -1;
				panelEnthalpyDrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		panelEnthalpyDrawArea.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent evt) {

				lblMouseCoordinate.setText(String.format("(x: %d y: %d)", evt.getX(), evt.getY()));

				double hResult = panelEnthalpyDrawArea.getHoXm(evt.getX());
				lblEnthalpyCoord.setText(String.format("H=%.2f kJ/kg",hResult));

				double pResult = panelEnthalpyDrawArea.getPoYm(evt.getY());
				lblPressureCoord.setText(String.format("P=%.2f bar",pResult));

				double tRresult = enthalpy.convP2T(pResult);
				lblTempCoord.setText(String.format("T=%.2f °C",tRresult));	

				try {
					if (WinPressTemp.panelTempPressDrawArea.isVisible()) {
						WinPressTemp.panelTempPressDrawArea.spotTempPressFollower(tRresult,pResult);
					}
				} catch (NullPointerException e) {
					// Not present ==> Do nothing !
				}

				if (rdbtnSaturation.isSelected()) {
					double pSat = enthalpy.convSatH2P(hResult,pResult);
					double tSat = enthalpy.convP2T(pSat);
					panelEnthalpyDrawArea.setCurveFollowerX(hResult);
					panelEnthalpyDrawArea.setCurveFollowerY(pSat);
					if (pSat > 0 )
						lblFollower.setText(String.format("PSat=%.2f / Tsat=%.2f",pSat,tSat));
					else
						lblFollower.setText(String.format("----------"));			
				} else {
					lblFollower.setText(String.format("----------"));
				}

				if (ElDrawIdToMoveOnP >=0 ){
					panelEnthalpyDrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));

					eDrawL.get(ElDrawIdToMoveOnP).setX1(hResult);
					String name = eDrawL.get(ElDrawIdToMoveOnP).getEnsembleName(); // T1,T2,..
					for (MeasureObject p : MeasureObject.values()) {
						if (p.name() == name) {
							int n = p.ordinal();  
							//System.out.println(n);
							//System.out.println(measureCollection.getmeasurePointL().get(n).getMH());
							measurePointL.get(n).setMH(hResult);
							measureTable.setAllTableValues();
							resultTable.setAllTableValues();
							//System.out.println(measureCollection.getmeasurePointL().get(n).getMHreal());
						}

					}
				}
				repaint();
			}
		});

		// ----------------------------------------
		// Panel Draw Enthalpy : Slider (Smooth the background image) 
		// ----------------------------------------

		JSlider slider = new JSlider();
		slider.setBackground(Color.WHITE);
		slider.setFocusable(false);
		slider.setValue((int)(panelEnthalpyDrawArea.getAlphaBlurBkgdImg()*100));

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int v = slider.getValue();
				panelEnthalpyDrawArea.setImageAlphaBlure((float) v / slider.getMaximum());
				panelEnthalpyDrawArea.repaint();
			}
		});
		panelEnthalpyDrawArea.add(slider, BorderLayout.NORTH);

		// ----------------------------------------
		// Panel Draw Enthalpy : POPUP Menu
		// ----------------------------------------
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(panelEnthalpyDrawArea, popupMenu);

		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.setIcon(new ImageIcon(WinEnthalpy.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Cut-Black.png")));
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id; 
				id = panelEnthalpyDrawArea.getIdNearest(eDrawL, ElDrawObject.PointPK_HP,panelEnthalpyDrawArea.getHoXm((int)pointJPopupMenu.getX()), panelEnthalpyDrawArea.getPoYm((int)pointJPopupMenu.getY()));
				if (id < 0)
					id = panelEnthalpyDrawArea.getIdNearest(eDrawL, ElDrawObject.PointP0_HP, panelEnthalpyDrawArea.getHoXm((int)pointJPopupMenu.getX()), panelEnthalpyDrawArea.getPoYm((int)pointJPopupMenu.getY()));

				logger.info(" Element to delete {} ", id);
				if (id >= 0) {
					eDrawL.remove(id);
				}
			}
		});
		popupMenu.add(mntmDelete);

		JMenuItem mntmMove = new JMenuItem("Move");
		mntmMove.setIcon(new ImageIcon(WinEnthalpy.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Paste-Black.png")));
		mntmMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id = panelEnthalpyDrawArea.getIdNearest(eDrawL, ElDrawObject.PointPK_HP, panelEnthalpyDrawArea.getHoXm((int)pointJPopupMenu.getX()), panelEnthalpyDrawArea.getPoYm((int)pointJPopupMenu.getY()));
				if (id < 0)
					id = panelEnthalpyDrawArea.getIdNearest(eDrawL, ElDrawObject.PointP0_HP, panelEnthalpyDrawArea.getHoXm((int)pointJPopupMenu.getX()), panelEnthalpyDrawArea.getPoYm((int)pointJPopupMenu.getY()));
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
		panelEnthalpyRight.add(panelHight);
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

				measurePointL.get(MeasureObject._PK_VAPOR_ID).setValue(PK);
				measurePointL.get(MeasureObject._PK_LIQUID_ID).setValue(PK);

				measurePointL.get(MeasureObject._PK_LIQUID_ID).setMeasureChoiceStatus(MeasureChoiceStatus.Chosen);
				measurePointL.get(MeasureObject._PK_VAPOR_ID).setMeasureChoiceStatus(MeasureChoiceStatus.Chosen);

				textPHP.setText(String.format("%.2f",measurePointL.get(MeasureObject._PK_VAPOR_ID).getValue()));

				logger.trace("Update the Measure Collection data ");
				Comp.updateAllMeasurePoints(measurePointL,enthalpy,pac);

				logger.trace("Update MeasureTable");
				measureTable.setAllTableValues();

				logger.trace("Update ResultTable");
				resultTable.setAllTableValues();

				logger.trace("Reinitialse the complete Draw elements with the Measure Collection");
				eDrawL.clear();
				eDrawL = ElDraw.createElDrawFrom(measurePointL,eDrawL);

				logger.trace("Repaint winEnthalpy");
				repaint();

				logger.trace("COP ={}", Comp.cop(measurePointL,pac));
				panelPacToolTextFieldCOP.setText(String.valueOf(Comp.cop(measurePointL,pac)));

			}
		});
		textPHP.setHorizontalAlignment(SwingConstants.RIGHT);
		textPHP.setText(String.format("%.2f",measurePointL.get(MeasureObject._PK_VAPOR_ID).getValue()));
		panelHight_Hight.add(textPHP);
		textPHP.setColumns(10);

		JLabel lblPBP = new JLabel("BP  (P0)");
		lblPBP.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelHight_Hight.add(lblPBP);

		textPBP = new JTextField();
		textPBP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double P0 = Double.parseDouble(textPBP.getText());

				measurePointL.get(MeasureObject._P0_ID).setValue(P0);
				measurePointL.get(MeasureObject._P0_ID).setMeasureChoiceStatus(MeasureChoiceStatus.Chosen);
				
				textPBP.setText(String.format("%.2f",measurePointL.get(MeasureObject._P0_ID).getValue()));

				logger.trace("Update the Measure Collection data ");
				Comp.updateAllMeasurePoints(measurePointL,enthalpy,pac);

				logger.trace("Update MeasureTable");
				measureTable.setAllTableValues();

				logger.trace("Update ResultTable");
				resultTable.setAllTableValues();

				logger.trace("Reinitialse the complete Draw elements with the Measure Collection");
				eDrawL.clear();
				eDrawL = ElDraw.createElDrawFrom(measurePointL,eDrawL);

				logger.trace("Repaint winEnthalpy");
				repaint();

				logger.trace("COP ={}", Comp.cop(measurePointL,pac));
				panelPacToolTextFieldCOP.setText(String.valueOf(Comp.cop(measurePointL,pac)));

			}
		});
		textPBP.setHorizontalAlignment(SwingConstants.RIGHT);
		textPBP.setText(String.format("%.2f",measurePointL.get(MeasureObject._P0_ID).getValue()));
		panelHight_Hight.add(textPBP);
		textPBP.setColumns(10);

		// ----------------------------------------
		JPanel panelMiddle1 = new JPanel();
		panelEnthalpyRight.add(panelMiddle1);
		panelMiddle1.setLayout(new BorderLayout(0, 0));

		JPanel panelMiddle1_Center = new JPanel();
		panelMiddle1.add(panelMiddle1_Center, BorderLayout.SOUTH);
		panelMiddle1_Center.setLayout(new BoxLayout(panelMiddle1_Center, BoxLayout.Y_AXIS));

		JButton btnPressureTemp = new JButton("P/Temp");
		btnPressureTemp.setMaximumSize(new Dimension(85, 23));
		btnPressureTemp.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPressureTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				winPressTemp.setVisible(true);
			}
		});
		panelMiddle1_Center.add(btnPressureTemp);

		// ----------------------------------------
		JPanel panelMiddle2 = new JPanel();
		panelEnthalpyRight.add(panelMiddle2);
		panelMiddle2.setLayout(new BorderLayout(0, 0));

		JPanel panelMiddle2_Center = new JPanel();
		panelMiddle2.add(panelMiddle2_Center, BorderLayout.CENTER);
		panelMiddle2_Center.setLayout(new BoxLayout(panelMiddle2_Center, BoxLayout.Y_AXIS));

		rdbtnSaturation = new JRadioButton("Saturation");
		rdbtnSaturation.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelMiddle2_Center.add(rdbtnSaturation);

		rdbtnNothin = new JRadioButton("Nothing");
		rdbtnNothin.setSelected(true);
		rdbtnNothin.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelMiddle2_Center.add(rdbtnNothin);

		ButtonGroup btGroupPosFollower = new ButtonGroup();
		btGroupPosFollower.add(rdbtnSaturation);
		btGroupPosFollower.add(rdbtnNothin);

		// ----------------------------------------
		JPanel panelBottom = new JPanel();
		panelEnthalpyRight.add(panelBottom);
		panelBottom.setLayout(new BorderLayout(0, 0));

		JPanel panelBottom_Bottom = new JPanel();
		panelBottom.add(panelBottom_Bottom, BorderLayout.SOUTH);
		panelBottom_Bottom.setLayout(new BoxLayout(panelBottom_Bottom, BoxLayout.Y_AXIS));

		JButton btnResetZoom = new JButton("Centrer");
		btnResetZoom.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnResetZoom.setMaximumSize(new Dimension(85, 23));
		btnResetZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelEnthalpyDrawArea.centerImg();
				panelEnthalpyDrawArea.repaint();			
			}
		});
		panelBottom_Bottom.add(btnResetZoom);

		JButton btnClear = new JButton("Effacer");
		btnClear.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnClear.setMaximumSize(new Dimension(85, 23));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelEnthalpyDrawArea.clean();
			}
		});
		panelBottom_Bottom.add(btnClear);
	}


	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public Enthalpy getEnthalpy() {
		return enthalpy;
	}

	public PanelEnthalpy getPanelEnthalpyDrawArea() {
		return panelEnthalpyDrawArea;
	}

}
