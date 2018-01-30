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
package gui.pac;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JButton;
import computation.Misc;
import gui.GuiConfig;
import pac.Compressor;
import pac.Pac;
import translation.TCompressor;
import translation.TLanguage;

public class WinCompressor extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private Pac pac;
	private GuiConfig guiConfig;

	// Win Builder
	private JTextField textFieldCompEvap;
	private JTextField textFieldCompRG;
	private JTextField textFieldCompCond;
	private JTextField textFieldCompLiq;
	private JTextField textFieldCompCapacity;
	private JTextField textFieldCompPower;
	private JTextField textFieldCompCurrent;
	private JTextField textFieldCompSurchauffe;
	private JTextField textFieldCompSousRefroid;
	private JTextField textFieldCompEER;
	private JTextField textFieldCompMassFlow;
	private JTextField textFieldCompDeltaH0;
	private JTextField textFieldCompVoltage;
	private JTextField textFieldCompCosPhi;
	private JCheckBox checkoxFaren;
	private JCheckBox checkoxBTU;
	private JCheckBox checkoxPound;
	private JLabel lblCapacity;
	private JLabel lblPower;
	private JLabel lblCurrent1;
	private JLabel lblEer;
	private JLabel lblVoltage1;
	private JLabel lblMassflow;
	private JLabel lblEvap;
	private JLabel lblRG;
	private JLabel lblCond;
	private JLabel lblLiq;
	private JLabel lblSurchauffe;
	private JLabel lblSousRefroid;
	private JTextField textFieldCompCurrentMeasure;
	private JTextField textFieldCompVoltageMeasure;
	private JLabel lblPowerOnMotorShaft;
	private JTextField textCompPowerShaftPercent;
	private JLabel labelPercentage;
	private JPanel panelMeasure;
	private JPanel panel_m1;
	private TitledBorder panel_m1Border;
	private JButton button_1;
	private JLabel lblFieldCompName2;
	private JLabel lblFieldCompName1;
	private JLabel lblCurrent2;
	private JLabel lblVoltage2;
	private JPanel panel_pc1;
	private JPanel panel_pc2;
	private JTabbedPane tabbedPane;

	// -------------------------------------------------------
	// 				TEST THE APPLICATION STANDALONE 
	// -------------------------------------------------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pac pac = new Pac();
					GuiConfig guiConfig = new GuiConfig();
					WinCompressor winCompFrame = new WinCompressor(pac, guiConfig );
					// First fillCompressorTextField then applyConfig 
					winCompFrame.fillCompressorTextField();
					winCompFrame.applyConfig();
					winCompFrame.setVisible(true);
					
					guiConfig.setLanguage(TLanguage.FRENCH);
					winCompFrame.applyConfig();
					
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
	 * 
	 * @param pac --> PAC
	 * @param vguiConfig --> Whole configuration of PAC TOOL GUI
	 */
	public WinCompressor(Pac vpac, GuiConfig vguiConfig) {
		pac = vpac;
		guiConfig =vguiConfig;
		initialize();
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Fill Compressor Text field 
	 * The data are read from the compressor variable, where the information is stored
	 * The data are always stored in SI format !
	 * BY DEFAULT CHECKBOX MUST NOT BEEN SET, OTHERWHISE .doClick() will be called 
	 * without data in the textfield !!
	 * 
	 */
	private void fillCompressorTextField() {

		Compressor compressor = pac.getCompressor();
		
		logger.info("(fillCompressorTextField) Compressor Name {}",compressor.getName());

		lblFieldCompName1.setText(compressor.getName());
		lblFieldCompName2.setText(compressor.getName());

		textFieldCompEvap.setText(String.valueOf(compressor.getEvap()));
		textFieldCompRG.setText(String.valueOf(compressor.getRG()));
		textFieldCompCond.setText(String.valueOf(compressor.getCond()));
		textFieldCompLiq.setText(String.valueOf(compressor.getLiq()));
		textFieldCompCapacity.setText(String.valueOf(compressor.getCapacity()));
		textFieldCompPower.setText(String.valueOf(compressor.getPower()));
		textFieldCompCurrent.setText(String.valueOf(compressor.getCurrent()));
		textFieldCompSurchauffe.setText(String.valueOf((double)Math.round(compressor.getOverheated())));	
		textFieldCompSousRefroid.setText(String.valueOf((double)Math.round(compressor.getUnderCooling())));
		textFieldCompEER.setText(String.valueOf(Math.round(compressor.getCapacity()/compressor.getPower()*10.0)/10.0));
		textFieldCompMassFlow.setText(String.valueOf(compressor.getMassFlow()));
		textFieldCompDeltaH0.setText(String.valueOf(Math.round(compressor.getCapacity()/compressor.getMassFlow()/1000.0)));
		textFieldCompVoltage.setText(String.valueOf(compressor.getVoltage()));
		double tmp = Math.round(Misc.cosphi(compressor.getPower(), compressor.getVoltage(), compressor.getCurrent())*10000.0)/10000.0;
		textFieldCompCosPhi.setText(String.valueOf(tmp));

		textFieldCompCurrentMeasure.setText(String.valueOf(compressor.getCurrentMeasure()));
		textFieldCompVoltageMeasure.setText(String.valueOf(compressor.getVoltageMeasure()));
		textCompPowerShaftPercent.setText(String.valueOf(compressor.getPowerShaftPercent()));

	}

	/**
	 * Apply the WinPac GUI configuration to GUI Compressor.
	 * @param guiConfig
	 */
	public void applyConfig() {

		logger.trace("(applyConfig):: checkoxFaren={}  checkoxFaren={}  checkoxFaren={}",
				guiConfig.getUnitCompFaren(),
				guiConfig.getUnitCompBTU(),
				guiConfig.getUnitCompPound());
		
		// If button Fahrenheit is pressed, we will simulate a non-selection  
		// By default MUST NOT BE selected
		if (guiConfig.getUnitCompFaren()) {
			checkoxFaren.doClick();
		}

		// If button BTU is pressed, we will simulate a non-selection  
		// By default MUST NOT BE selected
		if (guiConfig.getUnitCompBTU()) {
			checkoxBTU.doClick();
		}

		// If button Pound is pressed, we will simulate a non-selection  
		// By default MUST NOT BE selected
		if (guiConfig.getUnitCompPound()) {
			checkoxPound.doClick();
		}
		
		changeLanguage();
		
	}	
	
	/**
	 * Will save the information from Panel TextField to PAC variable
	 * Data will ALWAYS be stored in International System : SI Format
	 * @param paci
	 */
	private void saveTextField2Compressor( ) {

		Compressor compressor = pac.getCompressor();

		logger.info("(saveTextField2Compressor) Compressor Name {}",compressor.getName());
		boolean weclickf = false;
		boolean weclickb = false;
		boolean weclickp = false;

		// If button Fahrenheit is pressed, we will simulate a non-selection  
		if (checkoxFaren.isSelected()) {
			checkoxFaren.doClick();
			weclickf = true;
		}

		// If button BTU is pressed, we will simulate a non-selection  
		if (checkoxBTU.isSelected()) {
			checkoxBTU.doClick();
			weclickb = true;
		}

		// If button Pound is pressed, we will simulate a non-selection  
		if (checkoxPound.isSelected()) {
			checkoxPound.doClick();
			weclickp = true;
		}

		// AT that stage all information are in SI !
		compressor.setEvap(Double.valueOf(textFieldCompEvap.getText()));
		compressor.setRG(Double.valueOf(textFieldCompRG.getText()));
		compressor.setCond(Double.valueOf(textFieldCompCond.getText()));
		compressor.setLiq(Double.valueOf(textFieldCompLiq.getText()));
		compressor.setCapacity(Double.valueOf(textFieldCompCapacity.getText()));
		compressor.setPower(Double.valueOf(textFieldCompPower.getText()));
		compressor.setCurrent(Double.valueOf(textFieldCompCurrent.getText()));
		compressor.setMassFlow(Double.valueOf(textFieldCompMassFlow.getText()));
		compressor.setVoltage(Double.valueOf(textFieldCompVoltage.getText()));

		compressor.setCurrentMeasure(Double.valueOf(textFieldCompCurrentMeasure.getText()));
		compressor.setVoltageMeasure(Double.valueOf(textFieldCompVoltageMeasure.getText()));
		compressor.setPowerShaftPercent(Integer.valueOf(textCompPowerShaftPercent.getText()));

		// If needed we go back to the Check box selection
		if (weclickf) {
			checkoxFaren.doClick();
		}
		if (weclickb) {
			checkoxBTU.doClick();
		}
		if (weclickp) {
			checkoxPound.doClick();
		}
	}

	/*
	 * Change the Langage 
	 */
	private void changeLanguage(){
		lblEer.setText(TCompressor.COMP_EER.getLangue(guiConfig.getLanguage()));
		lblEvap.setText(TCompressor.COMP_EVAP.getLangue(guiConfig.getLanguage()));
		lblRG.setText(TCompressor.COMP_RG.getLangue(guiConfig.getLanguage()));
		lblSurchauffe.setText(TCompressor.COMP_OVERHEATED.getLangue(guiConfig.getLanguage()));
		lblCond.setText(TCompressor.COMP_COND.getLangue(guiConfig.getLanguage()));
		lblLiq.setText(TCompressor.COMP_LIQ.getLangue(guiConfig.getLanguage()));
		lblSousRefroid.setText(TCompressor.COMP_UNDERCOOLING.getLangue(guiConfig.getLanguage()));
		lblCapacity.setText(TCompressor.COMP_CAPACITY.getLangue(guiConfig.getLanguage()));
		lblPower.setText(TCompressor.COMP_POWER.getLangue(guiConfig.getLanguage()));
		lblCurrent1.setText(TCompressor.COMP_CURRENT.getLangue(guiConfig.getLanguage()));
		lblCurrent2.setText(TCompressor.COMP_CURRENT.getLangue(guiConfig.getLanguage()));
		lblVoltage1.setText(TCompressor.COMP_VOLTAGE.getLangue(guiConfig.getLanguage()));
		lblVoltage2.setText(TCompressor.COMP_VOLTAGE.getLangue(guiConfig.getLanguage()));
		lblMassflow.setText(TCompressor.COMP_MASSFLOW.getLangue(guiConfig.getLanguage()));
		lblPowerOnMotorShaft.setText(TCompressor.COMP_POWER_MOTOR_SHAFT.getLangue(guiConfig.getLanguage()));
		
		TitledBorder panel_pc1Border = (TitledBorder) panel_pc1.getBorder();
		panel_pc1Border.setTitle(TCompressor.COMP_DATA_PERFORMANCE_TITLE1.getLangue(guiConfig.getLanguage()));

		TitledBorder panel_pc2Border = (TitledBorder) panel_pc2.getBorder();
		panel_pc2Border.setTitle(TCompressor.COMP_DATA_PERFORMANCE_TITLE2.getLangue(guiConfig.getLanguage()));

		TitledBorder panel_m1Border = (TitledBorder) panel_m1.getBorder();
		panel_m1Border.setTitle(TCompressor.COMP_DATA_MEASURE_TITLE1.getLangue(guiConfig.getLanguage()));
		
		tabbedPane.setTitleAt(0, TCompressor.COMP_TITLE1.getLangue(guiConfig.getLanguage()));
		tabbedPane.setTitleAt(1, TCompressor.COMP_TITLE2.getLangue(guiConfig.getLanguage()));

		
	}

	
	// -------------------------------------------------------
	// 				 GENERATED BY WIN BUILDER
	// -------------------------------------------------------

	/**
	 * Will initialize the GUI compressor window values with the default compressor value
	 * 
	 */
	private void initialize() {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			logger.info(e);
		}

		this.setTitle(TCompressor.COMP_WIN_TITLE.getLangue(guiConfig.getLanguage()));

		this.setTitle("Compressor");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(WinCompressor.class.getResource("/gui/images/PAC-Tool_16.png")));
		this.setBounds(100, 100, 445, 549);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);

		// ===============================================================================================================
		//													TABBED PANE
		// ===============================================================================================================

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 439, 522);
		getContentPane().add(tabbedPane);

		// ===============================================================================================================
		//									                 PANEL COMPRESSOR
		// ===============================================================================================================
		JPanel panelCompressor = new JPanel();
		tabbedPane.addTab("Compressor", null, panelCompressor, null);
		panelCompressor.setLayout(null);


		// ================================================================
		// 					  	Performance Panel
		// ================================================================
		panel_pc1 = new JPanel();
		panel_pc1.setBounds(9, 45, 412, 161);
		panelCompressor.add(panel_pc1);
		panel_pc1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Data Performance (Temperature)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_pc1.setLayout(null);


		// ---------------------------------------------------------------
		// EVAP
		// ---------------------------------------------------------------
		lblEvap = new JLabel("Evap :");
		lblEvap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEvap.setBounds(5, 28, 93, 14);
		panel_pc1.add(lblEvap);

		textFieldCompEvap = new JTextField();
		textFieldCompEvap.setToolTipText("Temp\u00E9rature d'\u00E9vaporation (T0)");
		textFieldCompEvap.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tRG = Double.valueOf( textFieldCompRG.getText());
				double tEvap = Double.valueOf( textFieldCompEvap.getText());
				double tmp = Math.round(tRG - tEvap);
				textFieldCompSurchauffe.setText(String.valueOf(tmp));
			}
		});
		textFieldCompEvap.setBounds(99, 25, 67, 20);
		panel_pc1.add(textFieldCompEvap);

		textFieldCompEvap.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompEvap.setColumns(10);

		JLabel lblTemp_unity1 = new JLabel("\u00B0C");
		lblTemp_unity1.setBounds(176, 28, 25, 14);
		panel_pc1.add(lblTemp_unity1);

		// ---------------------------------------------------------------
		// RG
		// ---------------------------------------------------------------
		lblRG = new JLabel("RG :");
		lblRG.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRG.setBounds(5, 67, 93, 14);
		panel_pc1.add(lblRG);

		textFieldCompRG = new JTextField();
		textFieldCompRG.setToolTipText("Temp\u00E9rature d'aspiration du compresseur Point : (1)");
		textFieldCompRG.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tRG = Double.valueOf( textFieldCompRG.getText());
				double tEvap = Double.valueOf( textFieldCompEvap.getText());
				double tmp = Math.round(tRG - tEvap);
				textFieldCompSurchauffe.setText(String.valueOf(tmp));			
			}
		});
		textFieldCompRG.setBounds(99, 64, 67, 20);
		panel_pc1.add(textFieldCompRG);
		textFieldCompRG.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompRG.setColumns(10);

		JLabel lblTemp_unity2 = new JLabel("\u00B0C");
		lblTemp_unity2.setBounds(176, 67, 25, 14);
		panel_pc1.add(lblTemp_unity2);

		// ---------------------------------------------------------------
		// SURCHAUFFE
		// ---------------------------------------------------------------
		lblSurchauffe = new JLabel("Overheated :");
		lblSurchauffe.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSurchauffe.setBounds(38, 101, 81, 14);
		panel_pc1.add(lblSurchauffe);

		textFieldCompSurchauffe = new JTextField();
		textFieldCompSurchauffe.setText("0.0");
		textFieldCompSurchauffe.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompSurchauffe.setBackground(Color.PINK);
		textFieldCompSurchauffe.setEditable(false);
		textFieldCompSurchauffe.setBounds(120, 98, 46, 20);
		panel_pc1.add(textFieldCompSurchauffe);
		textFieldCompSurchauffe.setColumns(10);

		JLabel lblTemp_unity5 = new JLabel("\u00B0C");
		lblTemp_unity5.setBounds(176, 101, 25, 14);
		panel_pc1.add(lblTemp_unity5);

		// ---------------------------------------------------------------
		// COND
		// ---------------------------------------------------------------
		lblCond = new JLabel("Cond :");
		lblCond.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCond.setBounds(203, 28, 99, 14);
		panel_pc1.add(lblCond);

		textFieldCompCond = new JTextField();
		textFieldCompCond.setToolTipText("Temp\u00E9rature de condensation (TK) ");
		textFieldCompCond.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tcond = Double.valueOf( textFieldCompCond.getText());
				double tliq = Double.valueOf( textFieldCompLiq.getText());
				double tmp = Math.round(tcond - tliq);
				textFieldCompSousRefroid.setText(String.valueOf(tmp));
			}
		});
		textFieldCompCond.setBounds(302, 25, 75, 20);
		textFieldCompCond.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompCond.setColumns(10);
		panel_pc1.add(textFieldCompCond);

		JLabel lblTemp_unity3 = new JLabel("\u00B0C");
		lblTemp_unity3.setBounds(380, 28, 23, 14);
		panel_pc1.add(lblTemp_unity3);

		// ---------------------------------------------------------------
		// LIQ
		// ---------------------------------------------------------------
		lblLiq = new JLabel("Liq :");
		lblLiq.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLiq.setBounds(203, 67, 99, 14);
		panel_pc1.add(lblLiq);

		textFieldCompLiq = new JTextField();
		textFieldCompLiq.setToolTipText("Temp\u00E9rature Entr\u00E9e D\u00E9tendeur : Point (3) ");
		textFieldCompLiq.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tcond = Double.valueOf( textFieldCompCond.getText());
				double tliq = Double.valueOf( textFieldCompLiq.getText());
				double tmp = Math.round(tcond - tliq);
				textFieldCompSousRefroid.setText(String.valueOf(tmp));
			}
		});

		textFieldCompLiq.setBounds(302, 64, 75, 20);
		textFieldCompLiq.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompLiq.setColumns(10);
		panel_pc1.add(textFieldCompLiq);

		JLabel lblTemp_unity4 = new JLabel("\u00B0C");
		lblTemp_unity4.setBounds(380, 64, 23, 14);
		panel_pc1.add(lblTemp_unity4);

		// ---------------------------------------------------------------
		// SOUS REFROIDISSEMENT
		// ---------------------------------------------------------------

		lblSousRefroid = new JLabel("Under cooling :");
		lblSousRefroid.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSousRefroid.setBounds(226, 101, 103, 14);
		panel_pc1.add(lblSousRefroid);

		textFieldCompSousRefroid = new JTextField();
		textFieldCompSousRefroid.setText("0.0");
		textFieldCompSousRefroid.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompSousRefroid.setEditable(false);
		textFieldCompSousRefroid.setColumns(10);
		textFieldCompSousRefroid.setBackground(Color.PINK);
		textFieldCompSousRefroid.setBounds(331, 98, 46, 20);
		panel_pc1.add(textFieldCompSousRefroid);

		JLabel lblTemp_unity6 = new JLabel("\u00B0C");
		lblTemp_unity6.setBounds(380, 101, 23, 14);
		panel_pc1.add(lblTemp_unity6);

		// ---------------------------------------------------------------
		// Check Box Farenheit / Celcius
		// ---------------------------------------------------------------
		checkoxFaren = new JCheckBox("Farenheit");
		checkoxFaren.setBounds(302, 131, 95, 23);
		panel_pc1.add(checkoxFaren);
		checkoxFaren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (checkoxFaren.isSelected()) {
					guiConfig.setUnitCompFaren(true);
					lblTemp_unity1.setText("°F");
					lblTemp_unity2.setText("°F");
					lblTemp_unity3.setText("°F");
					lblTemp_unity4.setText("°F");
					lblTemp_unity5.setText("°F");
					lblTemp_unity6.setText("°F");

					double tcond = Misc.degre2farenheit(Double.valueOf( textFieldCompCond.getText()));
					double tliq = Misc.degre2farenheit(Double.valueOf( textFieldCompLiq.getText()));
					double tRG = Misc.degre2farenheit(Double.valueOf( textFieldCompRG.getText()));
					double tEvap = Misc.degre2farenheit(Double.valueOf( textFieldCompEvap.getText()));

					textFieldCompEvap.setText(String.valueOf(Math.round(tEvap*100.0)/100.0));
					textFieldCompRG.setText(String.valueOf(Math.round(tRG*100.0)/100.0));
					textFieldCompCond.setText(String.valueOf(Math.round(tcond*100.0)/100.0));
					textFieldCompLiq.setText(String.valueOf(Math.round(tliq*100.0)/100.0));

					double tmp = Math.round(tRG - tEvap);
					textFieldCompSurchauffe.setText(String.valueOf(tmp));			

					tmp = Math.round(tcond - tliq);
					textFieldCompSousRefroid.setText(String.valueOf(tmp));
				} else {
					guiConfig.setUnitCompFaren(false);
					lblTemp_unity1.setText("°C");
					lblTemp_unity2.setText("°C");
					lblTemp_unity3.setText("°C");
					lblTemp_unity4.setText("°C");
					lblTemp_unity5.setText("°C");
					lblTemp_unity6.setText("°C");

					double tcond = Misc.farenheit2degre(Double.valueOf( textFieldCompCond.getText()));
					double tliq = Misc.farenheit2degre(Double.valueOf( textFieldCompLiq.getText()));
					double tRG = Misc.farenheit2degre(Double.valueOf( textFieldCompRG.getText()));
					double tEvap = Misc.farenheit2degre(Double.valueOf( textFieldCompEvap.getText()));

					textFieldCompEvap.setText(String.valueOf(Math.round(tEvap*100.0)/100.0));
					textFieldCompRG.setText(String.valueOf(Math.round(tRG*100.0)/100.0));
					textFieldCompCond.setText(String.valueOf(Math.round(tcond*100.0)/100.0));
					textFieldCompLiq.setText(String.valueOf(Math.round(tliq*100.0)/100.0));

					double tmp = Math.round(tRG - tEvap);
					textFieldCompSurchauffe.setText(String.valueOf(tmp));			

					tmp = Math.round(tcond - tliq);
					textFieldCompSousRefroid.setText(String.valueOf(tmp));
				}
			}
		});

		// ================================================================
		// 					   	Performance 2 Panel
		// ================================================================
		panel_pc2 = new JPanel();
		panel_pc2.setBounds(9, 217, 412, 234);
		panelCompressor.add(panel_pc2);
		panel_pc2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Data Performance (Others)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_pc2.setLayout(null);



		// ---------------------------------------------------------------
		// Capacity
		// ---------------------------------------------------------------

		lblCapacity = new JLabel("Capacity :");
		lblCapacity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCapacity.setToolTipText("");
		lblCapacity.setBounds(5, 28, 73, 14);
		panel_pc2.add(lblCapacity);

		textFieldCompCapacity = new JTextField();
		textFieldCompCapacity.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {

				double vCapacity = Double.valueOf( textFieldCompCapacity.getText());
				double vPower =  Double.valueOf( textFieldCompPower.getText());
				double vMassFlow = Double.valueOf(textFieldCompMassFlow.getText());
				double tmp = Math.round(vCapacity/vPower*10.0)/10.0;
				textFieldCompEER.setText(String.valueOf(tmp));

				if (checkoxBTU.isSelected() | checkoxPound.isSelected())  {
					textFieldCompDeltaH0.setText("-----");
				} else {
					tmp = Math.round(vCapacity/vMassFlow/1000.0);
					textFieldCompDeltaH0.setText(String.valueOf(tmp));
				}
			}
		});
		textFieldCompCapacity.setToolTipText("Puissance frigorifique: (H1-H3) x D\u00E9bit Massique");
		textFieldCompCapacity.setBounds(82, 25, 62, 20);
		textFieldCompCapacity.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompCapacity.setColumns(10);
		panel_pc2.add(textFieldCompCapacity);

		JLabel lblCapacity_unity = new JLabel("Watt");
		lblCapacity_unity.setToolTipText("(BUT/hr) British Thermal Unit / hour = Unit\u00E9 de mesure d'\u00E9nergie thermique / Heure. L'unit\u00E9 de puissance du SI est le watt (symbole : W), qui correspond \u00E0  un joule fourni par seconde.");
		lblCapacity_unity.setBounds(154, 28, 46, 14);
		panel_pc2.add(lblCapacity_unity);

		// ---------------------------------------------------------------
		// Power
		// ---------------------------------------------------------------
		lblPower = new JLabel("Power :");
		lblPower.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPower.setBounds(5, 59, 73, 14);
		panel_pc2.add(lblPower);

		textFieldCompPower = new JTextField();
		textFieldCompPower.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {

				double vCapacity = Double.valueOf( textFieldCompCapacity.getText());
				double vPower =  Double.valueOf( textFieldCompPower.getText());
				double vVoltage = Double.valueOf(textFieldCompVoltage.getText());
				double vCurrent = Double.valueOf(textFieldCompCurrent.getText());
				double tmp = Math.round(vCapacity/vPower*10.0)/10.0;
				textFieldCompEER.setText(String.valueOf(tmp));	

				tmp = Math.round(Misc.cosphi(vPower, vVoltage,vCurrent)*10000.0)/10000.0;
				textFieldCompCosPhi.setText(String.valueOf(tmp));
			}
		});
		textFieldCompPower.setToolTipText("Puissance Absorb\u00E9e");
		textFieldCompPower.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompPower.setBounds(82, 56, 62, 20);
		textFieldCompPower.setColumns(10);
		panel_pc2.add(textFieldCompPower);

		JLabel lblPower_Unity = new JLabel("Watt");
		lblPower_Unity.setBounds(154, 62, 46, 14);
		panel_pc2.add(lblPower_Unity);

		// ---------------------------------------------------------------
		// Courant
		// ---------------------------------------------------------------
		lblCurrent1 = new JLabel("Current :");
		lblCurrent1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrent1.setBounds(5, 90, 73, 14);
		panel_pc2.add(lblCurrent1);

		textFieldCompCurrent = new JTextField();
		textFieldCompCurrent.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				double vPower =  Double.valueOf( textFieldCompPower.getText());
				double vVoltage = Double.valueOf(textFieldCompVoltage.getText());
				double vCurrent = Double.valueOf(textFieldCompCurrent.getText());

				double tmp = Math.round(Misc.cosphi(vPower, vVoltage,vCurrent)*10000.0)/10000.0;
				textFieldCompCosPhi.setText(String.valueOf(tmp));
			}
		});
		textFieldCompCurrent.setToolTipText("Courant absorb\u00E9");
		textFieldCompCurrent.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompCurrent.setColumns(10);
		textFieldCompCurrent.setBounds(82, 87, 62, 20);
		panel_pc2.add(textFieldCompCurrent);

		JLabel lblCurrent_unity1 = new JLabel("A");
		lblCurrent_unity1.setBounds(154, 90, 46, 14);
		panel_pc2.add(lblCurrent_unity1);

		// ---------------------------------------------------------------
		// EER
		// ---------------------------------------------------------------
		lblEer = new JLabel("EER :");
		lblEer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEer.setBounds(5, 128, 73, 14);
		panel_pc2.add(lblEer);

		textFieldCompEER = new JTextField();
		textFieldCompEER.setEditable(false);
		textFieldCompEER.setBackground(Color.PINK);
		textFieldCompEER.setToolTipText("EER (Energy Efficiency Ratio) : Coefficient d\u2019efficacit\u00E9 frigorifique");
		textFieldCompEER.setText("0.0");
		textFieldCompEER.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompEER.setColumns(10);
		textFieldCompEER.setBounds(82, 125, 62, 20);
		panel_pc2.add(textFieldCompEER);

		JLabel lblEER_unity = new JLabel("None");
		lblEER_unity.setBounds(154, 125, 73, 22);
		panel_pc2.add(lblEER_unity);

		// ---------------------------------------------------------------
		// Mass Flow
		// ---------------------------------------------------------------

		lblMassflow = new JLabel("Mass Flow :");
		lblMassflow.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMassflow.setBounds(205, 28, 86, 14);
		panel_pc2.add(lblMassflow);

		textFieldCompMassFlow = new JTextField();
		textFieldCompMassFlow.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				double vCapacity = Double.valueOf( textFieldCompCapacity.getText());
				double vMassFlow = Double.valueOf(textFieldCompMassFlow.getText());

				if (checkoxBTU.isSelected() | checkoxPound.isSelected())  {
					textFieldCompDeltaH0.setText("-----");
				} else {
					double tmp = Math.round(vCapacity/vMassFlow/1000.0);
					textFieldCompDeltaH0.setText(String.valueOf(tmp));
				}
			}
		});
		textFieldCompMassFlow.setToolTipText("D\u00E9bit Massique");
		textFieldCompMassFlow.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompMassFlow.setColumns(10);
		textFieldCompMassFlow.setBounds(295, 25, 51, 20);
		panel_pc2.add(textFieldCompMassFlow);

		JLabel lblMassFlow_unity = new JLabel("Kg/s");
		lblMassFlow_unity.setBounds(356, 28, 36, 14);
		panel_pc2.add(lblMassFlow_unity);

		// ---------------------------------------------------------------
		// Check Box BTU/HR or Watt
		// ---------------------------------------------------------------
		checkoxBTU = new JCheckBox("BTU/hr");
		checkoxBTU.setToolTipText("British Thermal Unit / hour");
		checkoxBTU.setBounds(324, 171, 68, 23);
		checkoxBTU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (checkoxBTU.isSelected()) {
					guiConfig.setUnitCompBTU(true);
					lblCapacity_unity.setText("Btu/hr");
					lblEER_unity.setText("BTU/(hr.W)");

					double vCapacity = Misc.watt2btuhr(Double.valueOf( textFieldCompCapacity.getText()));
					double vPower =  Double.valueOf( textFieldCompPower.getText());

					textFieldCompCapacity.setText(String.valueOf(Math.round(vCapacity*100.0)/100.0));
					textFieldCompEER.setText(String.valueOf(Math.round(vCapacity/vPower*10.0)/10.0));
					textFieldCompDeltaH0.setText("-----");

				} else {
					guiConfig.setUnitCompBTU(false);
					lblCapacity_unity.setText("Watt");
					lblEER_unity.setText("");

					double vCapacity = Misc.btuhr2watt(Double.valueOf( textFieldCompCapacity.getText()));
					double vPower =  Double.valueOf( textFieldCompPower.getText());
					double vMassFlow = Double.valueOf(textFieldCompMassFlow.getText());

					textFieldCompCapacity.setText(String.valueOf(Math.round(vCapacity*100.0)/100.0));
					textFieldCompEER.setText(String.valueOf(Math.round(vCapacity/vPower*10.0)/10.0));

					if (checkoxBTU.isSelected() | checkoxPound.isSelected())  {
						textFieldCompDeltaH0.setText("-----");
					} else {
						double tmp = Math.round(vCapacity/vMassFlow/1000.0);
						textFieldCompDeltaH0.setText(String.valueOf(tmp));
					}
				}
			}
		});
		panel_pc2.add(checkoxBTU);

		// ---------------------------------------------------------------
		// Pound
		// ---------------------------------------------------------------
		checkoxPound = new JCheckBox("lbs/h");
		checkoxPound.setToolTipText("Pounds/hour");
		checkoxPound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (checkoxPound.isSelected()) {
					guiConfig.setUnitCompPound(true);
					lblMassFlow_unity.setText("lbs/hr");

					double vMassFlow = Misc.kg2pound(Double.valueOf(textFieldCompMassFlow.getText()));
					textFieldCompMassFlow.setText(String.valueOf(Math.round(vMassFlow*10.0)/10.0));

					textFieldCompDeltaH0.setText("-----");

				} else {
					guiConfig.setUnitCompPound(false);
					lblMassFlow_unity.setText("Kg/s");

					double vCapacity = Double.valueOf( textFieldCompCapacity.getText());
					double vMassFlow = Misc.pound2kg(Double.valueOf(textFieldCompMassFlow.getText()));
					textFieldCompMassFlow.setText(String.valueOf(Math.round(vMassFlow*10000.0)/10000.0));		

					if (checkoxBTU.isSelected() | checkoxPound.isSelected())  {
						textFieldCompDeltaH0.setText("-----");
					} else {
						double tmp = Math.round(vCapacity/vMassFlow/1000.0);
						textFieldCompDeltaH0.setText(String.valueOf(tmp));
					}

				}
			}
		});
		checkoxPound.setBounds(324, 201, 68, 23);
		panel_pc2.add(checkoxPound);

		// ---------------------------------------------------------------
		// H1-H3
		// ---------------------------------------------------------------

		JLabel lblDeltaH0 = new JLabel("H1-H3 :");
		lblDeltaH0.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDeltaH0.setBounds(220, 59, 62, 14);
		panel_pc2.add(lblDeltaH0);

		textFieldCompDeltaH0 = new JTextField();
		textFieldCompDeltaH0.setBackground(Color.PINK);
		textFieldCompDeltaH0.setEditable(false);
		textFieldCompDeltaH0.setToolTipText("Delta Enthalpie ");
		textFieldCompDeltaH0.setText("0.0");
		textFieldCompDeltaH0.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompDeltaH0.setColumns(10);
		textFieldCompDeltaH0.setBounds(295, 56, 51, 20);
		panel_pc2.add(textFieldCompDeltaH0);

		JLabel lblDeltaH0_unity = new JLabel("KJ/Kg");
		lblDeltaH0_unity.setBounds(356, 59, 36, 14);
		panel_pc2.add(lblDeltaH0_unity);

		// ---------------------------------------------------------------
		// Voltage
		// ---------------------------------------------------------------
		lblVoltage1 = new JLabel("Voltage :");
		lblVoltage1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVoltage1.setBounds(5, 177, 73, 14);
		panel_pc2.add(lblVoltage1);

		textFieldCompVoltage = new JTextField();
		textFieldCompVoltage.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {			
				double vPower =  Double.valueOf( textFieldCompPower.getText());
				double vVoltage = Double.valueOf(textFieldCompVoltage.getText());
				double vCurrent = Double.valueOf(textFieldCompCurrent.getText());

				double tmp = Math.round(Misc.cosphi(vPower, vVoltage,vCurrent)*10000.0)/10000.0;
				textFieldCompCosPhi.setText(String.valueOf(tmp));
			}
		});
		textFieldCompVoltage.setToolTipText("Tension");
		textFieldCompVoltage.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompVoltage.setColumns(10);
		textFieldCompVoltage.setBounds(82, 174, 62, 20);
		panel_pc2.add(textFieldCompVoltage);

		JLabel lblVoltage_unity1 = new JLabel("V");
		lblVoltage_unity1.setBounds(154, 177, 46, 14);
		panel_pc2.add(lblVoltage_unity1);

		// ---------------------------------------------------------------
		// Cos Phi
		// ---------------------------------------------------------------
		JLabel lblCosphi = new JLabel("Cos (\u03C6)");
		lblCosphi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCosphi.setBounds(5, 208, 73, 14);
		panel_pc2.add(lblCosphi);

		textFieldCompCosPhi = new JTextField();
		textFieldCompCosPhi.setBounds(82, 204, 62, 20);
		panel_pc2.add(textFieldCompCosPhi);
		textFieldCompCosPhi.setToolTipText("Cosinus(Phi)");
		textFieldCompCosPhi.setText("0.0");
		textFieldCompCosPhi.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompCosPhi.setEditable(false);
		textFieldCompCosPhi.setColumns(10);
		textFieldCompCosPhi.setBackground(Color.PINK);

		button_1 = new JButton("Sauv.");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Save Compressor {}");
				saveTextField2Compressor();

			}
		});
		button_1.setBounds(353, 462, 68, 23);
		panelCompressor.add(button_1);
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		lblFieldCompName1 = new JLabel("ZR40K3-PFG");
		lblFieldCompName1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFieldCompName1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFieldCompName1.setBounds(9, 11, 412, 23);
		panelCompressor.add(lblFieldCompName1);

		// ===============================================================================================================
		//									                 PANEL MEASURE
		// ===============================================================================================================

		panelMeasure = new JPanel();
		tabbedPane.addTab("Measure", null, panelMeasure, null);
		panelMeasure.setLayout(null);

		panel_m1 = new JPanel();

		panel_m1Border = new TitledBorder(null, "Measure", TitledBorder.LEADING, TitledBorder.TOP, null, null);
		panel_m1.setBorder(panel_m1Border);
		panel_m1.setBounds(12, 45, 412, 107);
		panelMeasure.add(panel_m1);
		panel_m1.setLayout(null);

		lblCurrent2 = new JLabel("Current :");
		lblCurrent2.setBounds(72, 17, 121, 14);
		panel_m1.add(lblCurrent2);
		lblCurrent2.setHorizontalAlignment(SwingConstants.RIGHT);

		textFieldCompCurrentMeasure = new JTextField();
		textFieldCompCurrentMeasure.setBounds(218, 15, 62, 20);
		panel_m1.add(textFieldCompCurrentMeasure);
		textFieldCompCurrentMeasure.setToolTipText("Courant absorb\u00E9");
		textFieldCompCurrentMeasure.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompCurrentMeasure.setColumns(10);

		JLabel lblCurrent_unity2 = new JLabel("A");
		lblCurrent_unity2.setBounds(292, 18, 26, 14);
		panel_m1.add(lblCurrent_unity2);

		lblVoltage2 = new JLabel("Voltage :");
		lblVoltage2.setBounds(72, 45, 121, 14);
		panel_m1.add(lblVoltage2);
		lblVoltage2.setHorizontalAlignment(SwingConstants.RIGHT);

		textFieldCompVoltageMeasure = new JTextField();
		textFieldCompVoltageMeasure.setBounds(218, 43, 62, 20);
		panel_m1.add(textFieldCompVoltageMeasure);
		textFieldCompVoltageMeasure.setToolTipText("Tension");
		textFieldCompVoltageMeasure.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompVoltageMeasure.setColumns(10);

		JLabel lblVoltage_unity2 = new JLabel("V");
		lblVoltage_unity2.setBounds(292, 46, 46, 14);
		panel_m1.add(lblVoltage_unity2);

		lblPowerOnMotorShaft = new JLabel("Power on motor shaft:");
		lblPowerOnMotorShaft.setBounds(20, 73, 173, 14);
		panel_m1.add(lblPowerOnMotorShaft);
		lblPowerOnMotorShaft.setHorizontalAlignment(SwingConstants.RIGHT);

		textCompPowerShaftPercent = new JTextField();
		textCompPowerShaftPercent.setBounds(218, 71, 62, 20);
		panel_m1.add(textCompPowerShaftPercent);
		textCompPowerShaftPercent.setToolTipText("Puissance disponible sur arbre moteur");
		textCompPowerShaftPercent.setHorizontalAlignment(SwingConstants.RIGHT);
		textCompPowerShaftPercent.setColumns(10);

		labelPercentage = new JLabel("%");
		labelPercentage.setBounds(292, 74, 37, 14);
		panel_m1.add(labelPercentage);

		JButton button = new JButton("Sauv.");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Save Compressor {}");
				saveTextField2Compressor();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 9));
		button.setBounds(356, 163, 68, 23);
		panelMeasure.add(button);
		
		lblFieldCompName2 = new JLabel("ZR40K3-PFG");
		lblFieldCompName2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFieldCompName2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFieldCompName2.setBounds(12, 11, 412, 23);
		panelMeasure.add(lblFieldCompName2);

	}
}
