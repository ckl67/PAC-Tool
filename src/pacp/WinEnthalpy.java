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
package pacp;

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
import java.awt.Toolkit;
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
import javax.swing.event.ChangeEvent;
import javax.swing.ImageIcon;

public class WinEnthalpy {
	/* ----------------------------------------
	 *  	STATIC GLOBAL VAR PUBLIC
	 * ---------------------------------------- */
	public static PanelEnthalpy panelEnthalpyDrawArea;

	/* 	----------------------------------------
	 * 		INSTANCE VAR
	 * ----------------------------------------*/
	private Enthalpy enthalpy;				// Enthalpy declaration
	private List<ElDraw> eDrawL;			// Draw elements List: lines/points/...
	private List<MeasurePoints> measurePL;
	
	private static Point pointJPopupMenu; 	// JPopupMenu's Position --> Must be static

	/* 	----------------------------------------
	 * 		WIN BUILDER
	 * ----------------------------------------*/
	private JFrame frame;
	private JLabel lblMouseCoordinate;
	private JLabel lblEnthalpyCoord;
	private JLabel lblPressureCoord;
	private JLabel lblTempCoord;
	private JLabel lblFollower;
	private JRadioButton rdbtnSaturation;
	private JRadioButton rdbtnNothin;
	private JTextField textPHP;
	private JTextField textPBP;

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
				List<MeasurePoints> measurePL1 = new ArrayList<MeasurePoints>();
				measurePL1.add(new MeasurePoints("T1",515,90,"Temp�rature des gaz BP\n apr�s surchauffe interne\n et avant compression","�C",WinMeasurePoints._GROUP_BP));
				measurePL1.add(new MeasurePoints("T2",546,90,"Temp�rature des gaz HP\n en fin de compression\n (Cloche du compresseur)","�C",WinMeasurePoints._GROUP_HP));
				measurePL1.add(new MeasurePoints("P3",582,135,"Temp�rature du d�but de condensation\n (Mesure HP Manifod)","Bar",WinMeasurePoints._GROUP_HP));
				measurePL1.add(new MeasurePoints("P4",583,203,"Temp�rature de fin de condensation\n (Mesure HP Manifod)","Bar",WinMeasurePoints._GROUP_HP));
				measurePL1.add(new MeasurePoints("T5",512,247,"Temp�rature des gaz HP\n apr�s sous refroidissement","�C",WinMeasurePoints._GROUP_HP));
				measurePL1.add(new MeasurePoints("T6",433,248,"Temp�rature sortie D�tendeur / Capillaire","�C",WinMeasurePoints._GROUP_BP));
				measurePL1.add(new MeasurePoints("P7",371,135,"Temp�rature �vaporation\n (Mesure BP Manifold)","Bar",WinMeasurePoints._GROUP_BP ));
				measurePL1.add(new MeasurePoints("T8",479,89, "Temp�rature des gaz HP\napr�s surchauffe externe","�C",WinMeasurePoints._GROUP_BP));
				measurePL1.add(new MeasurePoints("TMi",663,57,"Temp�rature Retour Eau Chauffage","�C",WinMeasurePoints._GROUP_HEAT));
				measurePL1.add(new MeasurePoints("TMo",663,282,"Temp�rature D�part Eau Chauffage","�C",WinMeasurePoints._GROUP_HEAT));
				measurePL1.add(new MeasurePoints("TCi",321,281,"Temp�rature Retour Eau Captage","�C",WinMeasurePoints._GROUP_SOURCE));
				measurePL1.add(new MeasurePoints("TCo",321,57,"Temp�rature D�part Eau Captage","�C",WinMeasurePoints._GROUP_SOURCE));
				try {
					WinEnthalpy window = new WinEnthalpy(new Enthalpy(),eDrawL1,measurePL1);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WinEnthalpy(Enthalpy vconfEnthalpy, List<ElDraw> veDrawL, List<MeasurePoints> vmeasurePL) {
		enthalpy = vconfEnthalpy;
		eDrawL = veDrawL;
		measurePL = vmeasurePL;
		
		pointJPopupMenu = new Point();
		
		enthalpy.loadPTFile();
		enthalpy.loadSatFile();

		initialize();
		
		@SuppressWarnings("unused")
		PanelEnthRepaintAction repaintAction = new PanelEnthRepaintAction();
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	/**
	 * Get the frame visible
	 */
	public void WinEnthalpyVisible() {
		frame.setVisible(true);
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
		frame = new JFrame();
		frame.setTitle("Diagramme Enthalpique");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WinEnthalpy.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		// ----------------------------------------
		// Panel Bottom with info 
		// ----------------------------------------

		JPanel panelEnthalpyBottom = new JPanel();
		frame.getContentPane().add(panelEnthalpyBottom, BorderLayout.SOUTH);

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
		frame.getContentPane().add(panelEnthalpyRight, BorderLayout.EAST);
		panelEnthalpyRight.setLayout(new GridLayout(0, 1, 0, 0));

		// ----------------------------------------
		// Panel Draw Enthalpy : Base
		// ----------------------------------------

		panelEnthalpyDrawArea = new PanelEnthalpy(enthalpy,eDrawL);	
		panelEnthalpyDrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		panelEnthalpyDrawArea.setBackground(Color.WHITE);
		frame.getContentPane().add(panelEnthalpyDrawArea, BorderLayout.CENTER);

		// **************************
		// MOUSE MOTION LISTENER  !!
		// **************************
		panelEnthalpyDrawArea.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent evt) {

				lblMouseCoordinate.setText(String.format("(x: %d y: %d)", evt.getX(), evt.getY()));

				double hResult = panelEnthalpyDrawArea.getHoXm(evt.getX());
				lblEnthalpyCoord.setText(String.format("H=%.2f kJ/kg",hResult));

				double pResult = panelEnthalpyDrawArea.getPoYm(evt.getY());
				lblPressureCoord.setText(String.format("P=%.2f bar",pResult));

				double tRresult = enthalpy.convP2T(pResult);
				lblTempCoord.setText(String.format("T=%.2f �C",tRresult));	

				try {
					if (WinPressTemp.panelTempPressDrawArea.isVisible()) {
						WinPressTemp.panelTempPressDrawArea.spotTempPressFollower(tRresult,pResult);
					}
				} catch (NullPointerException e) {
					// Not present ==> Do nothing !
				}

				if (rdbtnSaturation.isSelected()) {
					double pSat = enthalpy.convSatH2P(hResult,pResult);
					panelEnthalpyDrawArea.setCurveFollowerX(hResult);
					panelEnthalpyDrawArea.setCurveFollowerY(pSat);
					if (pSat > 0 )
						lblFollower.setText(String.format("PSat=%.2f bar",pSat));
					else
						lblFollower.setText(String.format("----------"));			
				} else {
					lblFollower.setText(String.format("----------"));
				}
				//panelEnthalpyDrawArea.repaint();
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
				//panelEnthalpyDrawArea.repaint();
			}
		});
		panelEnthalpyDrawArea.add(slider, BorderLayout.NORTH);

		// ----------------------------------------
		// Panel Draw Enthalpy : POPUP Menu
		// ----------------------------------------
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(panelEnthalpyDrawArea, popupMenu);

		JMenuItem mntmDelete = new JMenuItem("Effacer");
		mntmDelete.setIcon(new ImageIcon(WinEnthalpy.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Cut-Black.png")));
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id = panelEnthalpyDrawArea.getIdNearest(eDrawL, panelEnthalpyDrawArea.getHoXm((int)pointJPopupMenu.getX()), panelEnthalpyDrawArea.getPoYm((int)pointJPopupMenu.getY()), 1);
				if (id >= 0) {
					eDrawL.remove(id);
					//panelEnthalpyDrawArea.repaint();					
				}
			}
		});
		popupMenu.add(mntmDelete);


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

				// Remove the PK element
				for(int i=0;i<eDrawL.size();i++) {
					if (eDrawL.get(i).getType() == ElDraw._LineHorzInfHP)
						eDrawL.remove(i);
				}
				measurePL.get(WinMeasurePoints._HP1_ID).setValue(PK);
				measurePL.get(WinMeasurePoints._HP2_ID).setValue(PK);
				textPHP.setText(String.format("%.2f",measurePL.get(WinMeasurePoints._HP1_ID).getValue()));
				ElDraw edraw = new ElDraw(ElDraw._LineHorzInfHP,PK);
				eDrawL.add(edraw);
				//panelEnthalpyDrawArea.repaint();

			}
		});
		textPHP.setHorizontalAlignment(SwingConstants.RIGHT);
		textPHP.setText(String.format("%.2f",measurePL.get(WinMeasurePoints._HP1_ID).getValue()));
		panelHight_Hight.add(textPHP);
		textPHP.setColumns(10);

		JLabel lblPBP = new JLabel("BP  (P0)");
		lblPBP.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelHight_Hight.add(lblPBP);

		textPBP = new JTextField();
		textPBP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double P0 = Double.parseDouble(textPBP.getText());
				// Remove the P0 element
				for(int i=0;i<eDrawL.size();i++) {
					if (eDrawL.get(i).getType() == ElDraw._LineHorzInfBP)
						eDrawL.remove(i);
				}
				
				measurePL.get(WinMeasurePoints._BP_ID).setValue(P0);
				textPBP.setText(String.format("%.2f",measurePL.get(WinMeasurePoints._BP_ID).getValue()));

				ElDraw edraw = new ElDraw(ElDraw._LineHorzInfBP,P0);
				eDrawL.add(edraw);
				//panelEnthalpyDrawArea.repaint();
			}
		});
		textPBP.setHorizontalAlignment(SwingConstants.RIGHT);
		textPBP.setText(String.format("%.2f",measurePL.get(WinMeasurePoints._BP_ID).getValue()));
		panelHight_Hight.add(textPBP);
		textPBP.setColumns(10);

		// ----------------------------------------
		JPanel panelMiddle1 = new JPanel();
		panelEnthalpyRight.add(panelMiddle1);
		panelMiddle1.setLayout(new BorderLayout(0, 0));

		JPanel panelMiddle1_Center = new JPanel();
		panelMiddle1.add(panelMiddle1_Center, BorderLayout.CENTER);
		panelMiddle1_Center.setLayout(new BoxLayout(panelMiddle1_Center, BoxLayout.Y_AXIS));

		JButton btnPressureTemp = new JButton("P/Temp");
		btnPressureTemp.setMaximumSize(new Dimension(85, 23));
		btnPressureTemp.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPressureTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinPressTemp window = new WinPressTemp(enthalpy);
				window.WinPressTempVisible();
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
				//panelEnthalpyDrawArea.repaint();			
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
	
	public void updateAllTextField() {
		System.out.println("Update textfiels");
		textPHP.setText(String.format("%.2f",measurePL.get(WinMeasurePoints._HP1_ID).getValue()));
		textPBP.setText(String.format("%.2f",measurePL.get(WinMeasurePoints._BP_ID).getValue()));
		
	}


}
