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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import computation.Misc;
import pac.Compressor;
import pac.Pac;

public class WinCompressor extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(WinCompressor.class.getName());

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private PacToolVar pacToolVar;
	private Pac pac;
	private WinPacToolConfig winPacToolConfig;
	
	// Win Builder
	private JTextField textFieldCompressorEvap;
	private JTextField textFieldCompressorRG;
	private JTextField textFieldCompressorCond;
	private JTextField textFieldCompressorLiq;
	private JTextField textFieldCompressorCapacity;
	private JTextField textFieldCompressorPower;
	private JTextField textFieldCompressorCurrent;
	private JTextField textFieldCompressorSurchauffe;
	private JTextField textFieldCompressorSousRefroid;
	private JTextField textFieldCompressorEER;
	private JTextField textFieldCompressorMassFlow;
	private JTextField textFieldCompressorDeltaH0;
	private JTextField textFieldCompressorVoltage;
	private JTextField textFieldCompressorCosPhi;
	private JTextField textFieldCompressorName;
	private JCheckBox checkoxFaren;
	private JCheckBox checkoxBTU;
	private JCheckBox checkoxPound;
	private JComboBox<String> comboBoxCompressor;
	private JLabel lblCapacity;
	private JLabel lblPower;
	private JLabel lblCurrent;
	private JLabel lblEer;
	private JLabel lblVoltage;
	private JLabel lblMassflow;
	private JLabel lblEvap;
	private JLabel lblRG;
	private JLabel lblCond;
	private JLabel lblLiq;
	private JLabel lblSurchauffe;
	private JLabel lblSousRefroid;

	// -------------------------------------------------------
	// 				TEST THE APPLICATION STANDALONE 
	// -------------------------------------------------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {				
					WinCompressor frame = new WinCompressor(new PacToolVar());
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
	 * 
	 * @param pac --> PAC
	 * @param vwinPacToolConfig --> Whole configuration of PAC TOOL GUI
	 */
	public WinCompressor(PacToolVar vpacToolVar) {
		pacToolVar = vpacToolVar;
		pac = pacToolVar.getPac();
		winPacToolConfig =pacToolVar.getWinPacToolConfig();

		initialize();
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	
	/**
	 * Apply the WinPac GUI configuration to GUI Compressor.
	 * @param winPacToolConfig
	 */
	public void applyConfig(WinPacToolConfig vwinPacToolConfig) {
		
		logger.info("applyConfig : NB Compressor={}", pac.getNbOfCompressorNb());
		// Remove all Compressor items in ComboBox (except the first)
		for(int i=1;i<pac.getNbOfCompressorNb();i++) {
			comboBoxCompressor.removeItemAt(i);
		}

		// Set the Compressor Check Box (Fahrenheit/Pound/BTU) before to affect the data to text field, 
		// no actions will be performed by this settings 
		checkoxFaren.setSelected(vwinPacToolConfig.getUnitCompFaren());
		checkoxBTU.setSelected(vwinPacToolConfig.getUnitCompBTU()); 		
		checkoxPound.setSelected(vwinPacToolConfig.getUnitCompPound()); 		

		// Fill Compressor ComboBox
		for(int i=1;i<pac.getNbOfCompressorNb();i++) {
			if(i>0) {
				comboBoxCompressor.insertItemAt(pac.getCompressorNb(i).getName(),i);
			}
		}
		comboBoxCompressor.setSelectedIndex(0);
		pac.selectCurrentCompressor(0);
		fillCompressorTexField(pac.getCurrentCompressor());
		
	}
	
	
	/**
	 * Fill Compressor Text field 
	 * The data are read from the compressor variable, where the information is stored
	 * The data are always stored in SI format !
	 * BY DEFAULT CHECKBOX MUST NOT BEEN SET, OTHERWHISE .doClick() will be called 
	 * without data in the textfield !!
	 * 
	 */
	private void fillCompressorTexField(Compressor compressor) {

		logger.info("(fillCompressorTexField) Compressor Name {}",compressor.getName());
		boolean weclickf = false;
		boolean weclickb = false;
		boolean weclickp = false;

		// If button Fahrenheit is pressed, we will simulate a non-selection  
		// By default MUST NOT BE selected
		if (checkoxFaren.isSelected()) {
			checkoxFaren.doClick();
			weclickf = true;
		}

		// If button BTU is pressed, we will simulate a non-selection  
		// By default MUST NOT BE selected
		if (checkoxBTU.isSelected()) {
			checkoxBTU.doClick();
			weclickb = true;
		}

		// If button Pound is pressed, we will simulate a non-selection  
		// By default MUST NOT BE selected
		if (checkoxPound.isSelected()) {
			checkoxPound.doClick();
			weclickp = true;
		}

		textFieldCompressorName.setText(compressor.getName());

		textFieldCompressorEvap.setText(String.valueOf(compressor.getEvap()));
		textFieldCompressorRG.setText(String.valueOf(compressor.getRG()));
		textFieldCompressorCond.setText(String.valueOf(compressor.getCond()));
		textFieldCompressorLiq.setText(String.valueOf(compressor.getLiq()));
		textFieldCompressorCapacity.setText(String.valueOf(compressor.getCapacity()));
		textFieldCompressorPower.setText(String.valueOf(compressor.getPower()));
		textFieldCompressorCurrent.setText(String.valueOf(compressor.getCurrent()));
		textFieldCompressorSurchauffe.setText(String.valueOf((double)Math.round(compressor.getOverheated())));	
		textFieldCompressorSousRefroid.setText(String.valueOf((double)Math.round(compressor.getUnderCooling())));
		textFieldCompressorEER.setText(String.valueOf(Math.round(compressor.getCapacity()/compressor.getPower()*10.0)/10.0));
		textFieldCompressorMassFlow.setText(String.valueOf(compressor.getMassFlow()));
		textFieldCompressorDeltaH0.setText(String.valueOf(Math.round(compressor.getCapacity()/compressor.getMassFlow()/1000.0)));
		textFieldCompressorVoltage.setText(String.valueOf(compressor.getVoltage()));
		double tmp = Math.round(Misc.cosphi(compressor.getPower(), compressor.getVoltage(), compressor.getCurrent())*10000.0)/10000.0;
		textFieldCompressorCosPhi.setText(String.valueOf(tmp));

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

	/**
	 * Will save the information from Panel TextField to PAC variable
	 * Data will ALWAYS be stored in International System : SI Format
	 * @param paci
	 */
	private void UpdateTextField2Compressor( Compressor compressor) {

		logger.info("(UpdateTextField2Compressor) Compressor Name {}",compressor.getName());
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
		compressor.setName(textFieldCompressorName.getText());

		compressor.setEvap(Double.valueOf(textFieldCompressorEvap.getText()));
		compressor.setRG(Double.valueOf(textFieldCompressorRG.getText()));
		compressor.setCond(Double.valueOf(textFieldCompressorCond.getText()));
		compressor.setLiq(Double.valueOf(textFieldCompressorLiq.getText()));
		compressor.setCapacity(Double.valueOf(textFieldCompressorCapacity.getText()));
		compressor.setPower(Double.valueOf(textFieldCompressorPower.getText()));
		compressor.setCurrent(Double.valueOf(textFieldCompressorCurrent.getText()));
		compressor.setMassFlow(Double.valueOf(textFieldCompressorMassFlow.getText()));
		compressor.setVoltage(Double.valueOf(textFieldCompressorVoltage.getText()));

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
	

	// -------------------------------------------------------
	// 				 GENERATED BY WIN BUILDER
	// -------------------------------------------------------

	
	public void initialize() {
		setTitle("Compressor");
		setIconImage(Toolkit.getDefaultToolkit().getImage(WinAbout.class.getResource("/gui/images/PAC-Tool_16.png")));
		setBounds(100, 100, 450, 526);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		// ===============================================================================================================
		//									                 PANEL PAC
		// ===============================================================================================================
		JPanel panelSroll = new JPanel();
		panelSroll.setBounds(0, 0, 432, 486);
		getContentPane().add(panelSroll);
		panelSroll.setForeground(Color.BLUE);
		panelSroll.setLayout(null);

		// ================================================================
		// 					  	Performance Panel
		// ================================================================
		JPanel panel_pc1 = new JPanel();
		panel_pc1.setBorder(new TitledBorder(null, "Donn\u00E9es Performance Constructeur (Temp\u00E9rature)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_pc1.setBounds(5, 73, 420, 161);
		panel_pc1.setLayout(null);
		panelSroll.add(panel_pc1);

		// ---------------------------------------------------------------
		// EVAP
		// ---------------------------------------------------------------
		lblEvap = new JLabel("Evap :");
		lblEvap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEvap.setBounds(5, 28, 93, 14);
		panel_pc1.add(lblEvap);

		textFieldCompressorEvap = new JTextField();
		textFieldCompressorEvap.setToolTipText("Temp\u00E9rature d'\u00E9vaporation (T0)");
		textFieldCompressorEvap.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tRG = Double.valueOf( textFieldCompressorRG.getText());
				double tEvap = Double.valueOf( textFieldCompressorEvap.getText());
				double tmp = Math.round(tRG - tEvap);
				textFieldCompressorSurchauffe.setText(String.valueOf(tmp));
			}
		});
		textFieldCompressorEvap.setBounds(99, 25, 67, 20);
		panel_pc1.add(textFieldCompressorEvap);

		textFieldCompressorEvap.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorEvap.setColumns(10);

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

		textFieldCompressorRG = new JTextField();
		textFieldCompressorRG.setToolTipText("Temp\u00E9rature d'aspiration du compresseur Point : (1)");
		textFieldCompressorRG.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tRG = Double.valueOf( textFieldCompressorRG.getText());
				double tEvap = Double.valueOf( textFieldCompressorEvap.getText());
				double tmp = Math.round(tRG - tEvap);
				textFieldCompressorSurchauffe.setText(String.valueOf(tmp));			
			}
		});
		textFieldCompressorRG.setBounds(99, 64, 67, 20);
		panel_pc1.add(textFieldCompressorRG);
		textFieldCompressorRG.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorRG.setColumns(10);

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

		textFieldCompressorSurchauffe = new JTextField();
		textFieldCompressorSurchauffe.setText("0.0");
		textFieldCompressorSurchauffe.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorSurchauffe.setBackground(Color.PINK);
		textFieldCompressorSurchauffe.setEditable(false);
		textFieldCompressorSurchauffe.setBounds(120, 98, 46, 20);
		panel_pc1.add(textFieldCompressorSurchauffe);
		textFieldCompressorSurchauffe.setColumns(10);

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

		textFieldCompressorCond = new JTextField();
		textFieldCompressorCond.setToolTipText("Temp\u00E9rature de condensation (TK) ");
		textFieldCompressorCond.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tcond = Double.valueOf( textFieldCompressorCond.getText());
				double tliq = Double.valueOf( textFieldCompressorLiq.getText());
				double tmp = Math.round(tcond - tliq);
				textFieldCompressorSousRefroid.setText(String.valueOf(tmp));
			}
		});
		textFieldCompressorCond.setBounds(302, 25, 75, 20);
		textFieldCompressorCond.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorCond.setColumns(10);
		panel_pc1.add(textFieldCompressorCond);

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

		textFieldCompressorLiq = new JTextField();
		textFieldCompressorLiq.setToolTipText("Temp\u00E9rature Entr\u00E9e D\u00E9tendeur : Point (3) ");
		textFieldCompressorLiq.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				double tcond = Double.valueOf( textFieldCompressorCond.getText());
				double tliq = Double.valueOf( textFieldCompressorLiq.getText());
				double tmp = Math.round(tcond - tliq);
				textFieldCompressorSousRefroid.setText(String.valueOf(tmp));
			}
		});

		textFieldCompressorLiq.setBounds(302, 64, 75, 20);
		textFieldCompressorLiq.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorLiq.setColumns(10);
		panel_pc1.add(textFieldCompressorLiq);

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

		textFieldCompressorSousRefroid = new JTextField();
		textFieldCompressorSousRefroid.setText("0.0");
		textFieldCompressorSousRefroid.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorSousRefroid.setEditable(false);
		textFieldCompressorSousRefroid.setColumns(10);
		textFieldCompressorSousRefroid.setBackground(Color.PINK);
		textFieldCompressorSousRefroid.setBounds(331, 98, 46, 20);
		panel_pc1.add(textFieldCompressorSousRefroid);

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
					winPacToolConfig.setUnitCompFaren(true);
					lblTemp_unity1.setText("°F");
					lblTemp_unity2.setText("°F");
					lblTemp_unity3.setText("°F");
					lblTemp_unity4.setText("°F");
					lblTemp_unity5.setText("°F");
					lblTemp_unity6.setText("°F");

					double tcond = Misc.degre2farenheit(Double.valueOf( textFieldCompressorCond.getText()));
					double tliq = Misc.degre2farenheit(Double.valueOf( textFieldCompressorLiq.getText()));
					double tRG = Misc.degre2farenheit(Double.valueOf( textFieldCompressorRG.getText()));
					double tEvap = Misc.degre2farenheit(Double.valueOf( textFieldCompressorEvap.getText()));

					textFieldCompressorEvap.setText(String.valueOf(Math.round(tEvap*100.0)/100.0));
					textFieldCompressorRG.setText(String.valueOf(Math.round(tRG*100.0)/100.0));
					textFieldCompressorCond.setText(String.valueOf(Math.round(tcond*100.0)/100.0));
					textFieldCompressorLiq.setText(String.valueOf(Math.round(tliq*100.0)/100.0));

					double tmp = Math.round(tRG - tEvap);
					textFieldCompressorSurchauffe.setText(String.valueOf(tmp));			

					tmp = Math.round(tcond - tliq);
					textFieldCompressorSousRefroid.setText(String.valueOf(tmp));
				} else {
					winPacToolConfig.setUnitCompFaren(false);
					lblTemp_unity1.setText("°C");
					lblTemp_unity2.setText("°C");
					lblTemp_unity3.setText("°C");
					lblTemp_unity4.setText("°C");
					lblTemp_unity5.setText("°C");
					lblTemp_unity6.setText("°C");

					double tcond = Misc.farenheit2degre(Double.valueOf( textFieldCompressorCond.getText()));
					double tliq = Misc.farenheit2degre(Double.valueOf( textFieldCompressorLiq.getText()));
					double tRG = Misc.farenheit2degre(Double.valueOf( textFieldCompressorRG.getText()));
					double tEvap = Misc.farenheit2degre(Double.valueOf( textFieldCompressorEvap.getText()));

					textFieldCompressorEvap.setText(String.valueOf(Math.round(tEvap*100.0)/100.0));
					textFieldCompressorRG.setText(String.valueOf(Math.round(tRG*100.0)/100.0));
					textFieldCompressorCond.setText(String.valueOf(Math.round(tcond*100.0)/100.0));
					textFieldCompressorLiq.setText(String.valueOf(Math.round(tliq*100.0)/100.0));

					double tmp = Math.round(tRG - tEvap);
					textFieldCompressorSurchauffe.setText(String.valueOf(tmp));			

					tmp = Math.round(tcond - tliq);
					textFieldCompressorSousRefroid.setText(String.valueOf(tmp));
				}
			}
		});

		// ================================================================
		// 					   	Performance 2 Panel
		// ================================================================
		JPanel panel_pc2 = new JPanel();
		panel_pc2.setBounds(5, 245, 420, 234);
		panel_pc2.setBorder(new TitledBorder(null, "Donn\u00E9es Performance Constructeur (Autres)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_pc2.setLayout(null);
		panelSroll.add(panel_pc2);



		// ---------------------------------------------------------------
		// Capacity
		// ---------------------------------------------------------------

		lblCapacity = new JLabel("Capacity :");
		lblCapacity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCapacity.setToolTipText("");
		lblCapacity.setBounds(5, 28, 73, 14);
		panel_pc2.add(lblCapacity);

		textFieldCompressorCapacity = new JTextField();
		textFieldCompressorCapacity.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {

				double vCapacity = Double.valueOf( textFieldCompressorCapacity.getText());
				double vPower =  Double.valueOf( textFieldCompressorPower.getText());
				double vMassFlow = Double.valueOf(textFieldCompressorMassFlow.getText());
				double tmp = Math.round(vCapacity/vPower*10.0)/10.0;
				textFieldCompressorEER.setText(String.valueOf(tmp));

				if (checkoxBTU.isSelected() | checkoxPound.isSelected())  {
					textFieldCompressorDeltaH0.setText("-----");
				} else {
					tmp = Math.round(vCapacity/vMassFlow/1000.0);
					textFieldCompressorDeltaH0.setText(String.valueOf(tmp));
				}
			}
		});
		textFieldCompressorCapacity.setToolTipText("Puissance frigorifique: (H1-H3) x D\u00E9bit Massique");
		textFieldCompressorCapacity.setBounds(82, 25, 62, 20);
		textFieldCompressorCapacity.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorCapacity.setColumns(10);
		panel_pc2.add(textFieldCompressorCapacity);

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

		textFieldCompressorPower = new JTextField();
		textFieldCompressorPower.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {

				double vCapacity = Double.valueOf( textFieldCompressorCapacity.getText());
				double vPower =  Double.valueOf( textFieldCompressorPower.getText());
				double vVoltage = Double.valueOf(textFieldCompressorVoltage.getText());
				double vCurrent = Double.valueOf(textFieldCompressorCurrent.getText());
				double tmp = Math.round(vCapacity/vPower*10.0)/10.0;
				textFieldCompressorEER.setText(String.valueOf(tmp));	

				tmp = Math.round(Misc.cosphi(vPower, vVoltage,vCurrent)*10000.0)/10000.0;
				textFieldCompressorCosPhi.setText(String.valueOf(tmp));
			}
		});
		textFieldCompressorPower.setToolTipText("Puissance Absorb\u00E9e");
		textFieldCompressorPower.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorPower.setBounds(82, 56, 62, 20);
		textFieldCompressorPower.setColumns(10);
		panel_pc2.add(textFieldCompressorPower);

		JLabel lblPower_Unity = new JLabel("Watt");
		lblPower_Unity.setBounds(154, 62, 46, 14);
		panel_pc2.add(lblPower_Unity);

		// ---------------------------------------------------------------
		// Courant
		// ---------------------------------------------------------------
		lblCurrent = new JLabel("Current :");
		lblCurrent.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrent.setBounds(5, 90, 73, 14);
		panel_pc2.add(lblCurrent);

		textFieldCompressorCurrent = new JTextField();
		textFieldCompressorCurrent.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				double vPower =  Double.valueOf( textFieldCompressorPower.getText());
				double vVoltage = Double.valueOf(textFieldCompressorVoltage.getText());
				double vCurrent = Double.valueOf(textFieldCompressorCurrent.getText());

				double tmp = Math.round(Misc.cosphi(vPower, vVoltage,vCurrent)*10000.0)/10000.0;
				textFieldCompressorCosPhi.setText(String.valueOf(tmp));
			}
		});
		textFieldCompressorCurrent.setToolTipText("Courant absorb\u00E9");
		textFieldCompressorCurrent.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorCurrent.setColumns(10);
		textFieldCompressorCurrent.setBounds(82, 87, 62, 20);
		panel_pc2.add(textFieldCompressorCurrent);

		JLabel lblCurrent_unity = new JLabel("A");
		lblCurrent_unity.setBounds(154, 90, 46, 14);
		panel_pc2.add(lblCurrent_unity);

		// ---------------------------------------------------------------
		// EER
		// ---------------------------------------------------------------
		lblEer = new JLabel("EER :");
		lblEer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEer.setBounds(5, 128, 73, 14);
		panel_pc2.add(lblEer);

		textFieldCompressorEER = new JTextField();
		textFieldCompressorEER.setEditable(false);
		textFieldCompressorEER.setBackground(Color.PINK);
		textFieldCompressorEER.setToolTipText("EER (Energy Efficiency Ratio) : Coefficient d\u2019efficacit\u00E9 frigorifique");
		textFieldCompressorEER.setText("0.0");
		textFieldCompressorEER.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorEER.setColumns(10);
		textFieldCompressorEER.setBounds(82, 125, 62, 20);
		panel_pc2.add(textFieldCompressorEER);

		JLabel lblEER_unity = new JLabel("");
		lblEER_unity.setBounds(154, 125, 73, 22);
		panel_pc2.add(lblEER_unity);

		// ---------------------------------------------------------------
		// Mass Flow
		// ---------------------------------------------------------------

		lblMassflow = new JLabel("Mass Flow :");
		lblMassflow.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMassflow.setBounds(205, 28, 86, 14);
		panel_pc2.add(lblMassflow);

		textFieldCompressorMassFlow = new JTextField();
		textFieldCompressorMassFlow.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				double vCapacity = Double.valueOf( textFieldCompressorCapacity.getText());
				double vMassFlow = Double.valueOf(textFieldCompressorMassFlow.getText());

				if (checkoxBTU.isSelected() | checkoxPound.isSelected())  {
					textFieldCompressorDeltaH0.setText("-----");
				} else {
					double tmp = Math.round(vCapacity/vMassFlow/1000.0);
					textFieldCompressorDeltaH0.setText(String.valueOf(tmp));
				}
			}
		});
		textFieldCompressorMassFlow.setToolTipText("D\u00E9bit Massique");
		textFieldCompressorMassFlow.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorMassFlow.setColumns(10);
		textFieldCompressorMassFlow.setBounds(295, 25, 51, 20);
		panel_pc2.add(textFieldCompressorMassFlow);

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
					winPacToolConfig.setUnitCompBTU(true);
					lblCapacity_unity.setText("Btu/hr");
					lblEER_unity.setText("BTU/(hr.W)");

					double vCapacity = Misc.watt2btuhr(Double.valueOf( textFieldCompressorCapacity.getText()));
					double vPower =  Double.valueOf( textFieldCompressorPower.getText());

					textFieldCompressorCapacity.setText(String.valueOf(Math.round(vCapacity*100.0)/100.0));
					textFieldCompressorEER.setText(String.valueOf(Math.round(vCapacity/vPower*10.0)/10.0));
					textFieldCompressorDeltaH0.setText("-----");

				} else {
					winPacToolConfig.setUnitCompBTU(false);
					lblCapacity_unity.setText("Watt");
					lblEER_unity.setText("");

					double vCapacity = Misc.btuhr2watt(Double.valueOf( textFieldCompressorCapacity.getText()));
					double vPower =  Double.valueOf( textFieldCompressorPower.getText());
					double vMassFlow = Double.valueOf(textFieldCompressorMassFlow.getText());

					textFieldCompressorCapacity.setText(String.valueOf(Math.round(vCapacity*100.0)/100.0));
					textFieldCompressorEER.setText(String.valueOf(Math.round(vCapacity/vPower*10.0)/10.0));

					if (checkoxBTU.isSelected() | checkoxPound.isSelected())  {
						textFieldCompressorDeltaH0.setText("-----");
					} else {
						double tmp = Math.round(vCapacity/vMassFlow/1000.0);
						textFieldCompressorDeltaH0.setText(String.valueOf(tmp));
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
					winPacToolConfig.setUnitCompPound(true);
					lblMassFlow_unity.setText("lbs/hr");

					double vMassFlow = Misc.kg2pound(Double.valueOf(textFieldCompressorMassFlow.getText()));
					textFieldCompressorMassFlow.setText(String.valueOf(Math.round(vMassFlow*10.0)/10.0));

					textFieldCompressorDeltaH0.setText("-----");

				} else {
					winPacToolConfig.setUnitCompPound(false);
					lblMassFlow_unity.setText("Kg/s");

					double vCapacity = Double.valueOf( textFieldCompressorCapacity.getText());
					double vMassFlow = Misc.pound2kg(Double.valueOf(textFieldCompressorMassFlow.getText()));
					textFieldCompressorMassFlow.setText(String.valueOf(Math.round(vMassFlow*10000.0)/10000.0));		

					if (checkoxBTU.isSelected() | checkoxPound.isSelected())  {
						textFieldCompressorDeltaH0.setText("-----");
					} else {
						double tmp = Math.round(vCapacity/vMassFlow/1000.0);
						textFieldCompressorDeltaH0.setText(String.valueOf(tmp));
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

		textFieldCompressorDeltaH0 = new JTextField();
		textFieldCompressorDeltaH0.setBackground(Color.PINK);
		textFieldCompressorDeltaH0.setEditable(false);
		textFieldCompressorDeltaH0.setToolTipText("Delta Enthalpie ");
		textFieldCompressorDeltaH0.setText("0.0");
		textFieldCompressorDeltaH0.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorDeltaH0.setColumns(10);
		textFieldCompressorDeltaH0.setBounds(295, 56, 51, 20);
		panel_pc2.add(textFieldCompressorDeltaH0);

		JLabel lblDeltaH0_unity = new JLabel("KJ/Kg");
		lblDeltaH0_unity.setBounds(356, 59, 36, 14);
		panel_pc2.add(lblDeltaH0_unity);

		// ---------------------------------------------------------------
		// Voltage
		// ---------------------------------------------------------------
		lblVoltage = new JLabel("Voltage :");
		lblVoltage.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVoltage.setBounds(5, 177, 73, 14);
		panel_pc2.add(lblVoltage);

		textFieldCompressorVoltage = new JTextField();
		textFieldCompressorVoltage.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {			
				double vPower =  Double.valueOf( textFieldCompressorPower.getText());
				double vVoltage = Double.valueOf(textFieldCompressorVoltage.getText());
				double vCurrent = Double.valueOf(textFieldCompressorCurrent.getText());

				double tmp = Math.round(Misc.cosphi(vPower, vVoltage,vCurrent)*10000.0)/10000.0;
				textFieldCompressorCosPhi.setText(String.valueOf(tmp));
			}
		});
		textFieldCompressorVoltage.setToolTipText("Tension");
		textFieldCompressorVoltage.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorVoltage.setColumns(10);
		textFieldCompressorVoltage.setBounds(82, 174, 62, 20);
		panel_pc2.add(textFieldCompressorVoltage);

		JLabel lblVoltage_unity = new JLabel("V");
		lblVoltage_unity.setBounds(154, 177, 46, 14);
		panel_pc2.add(lblVoltage_unity);

		// ---------------------------------------------------------------
		// Cos Phi
		// ---------------------------------------------------------------
		JLabel lblCosphi = new JLabel("Cos (\u03C6)");
		lblCosphi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCosphi.setBounds(5, 208, 73, 14);
		panel_pc2.add(lblCosphi);

		textFieldCompressorCosPhi = new JTextField();
		textFieldCompressorCosPhi.setToolTipText("Cosinus(Phi)");
		textFieldCompressorCosPhi.setText("0.0");
		textFieldCompressorCosPhi.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCompressorCosPhi.setEditable(false);
		textFieldCompressorCosPhi.setColumns(10);
		textFieldCompressorCosPhi.setBackground(Color.PINK);
		textFieldCompressorCosPhi.setBounds(82, 205, 62, 20);
		panel_pc2.add(textFieldCompressorCosPhi);

		// ---------------------------------------------------------------
		// Compressor Name
		// ---------------------------------------------------------------
		textFieldCompressorName = new JTextField();
		textFieldCompressorName.setToolTipText("Name can be modified");
		textFieldCompressorName.setForeground(new Color(0, 0, 128));
		textFieldCompressorName.setBorder(null);
		textFieldCompressorName.setBackground(UIManager.getColor("DesktopPane.background"));
		textFieldCompressorName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCompressorName.setFont(new Font("Tahoma", Font.BOLD, 16));
		textFieldCompressorName.setBounds(10, 10, 167, 52);
		textFieldCompressorName.setColumns(10);
		panelSroll.add(textFieldCompressorName);

		// ---------------------------------------------------------------
		// Combo box Compressor
		// ---------------------------------------------------------------
		comboBoxCompressor = new JComboBox<String>();
		comboBoxCompressor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCompressor.getSelectedIndex();
				fillCompressorTexField(pac.getCompressorNb(ComboId));
				pac.selectCurrentCompressor(ComboId);
			}
		});
		comboBoxCompressor.setBounds(243, 10, 131, 20);
		comboBoxCompressor.addItem(pac.getCompressorNb(0).getName());
		panelSroll.add(comboBoxCompressor);


		// ---------------------------------------------------------------
		// Compressor Save
		// ---------------------------------------------------------------
		JButton btnSaveCompressor = new JButton("Sauv.");
		btnSaveCompressor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCompressor.getSelectedIndex();
				if ( ComboId >= 0 ) {
					logger.info("Save Compressor {}", ComboId );
					pac.selectCurrentCompressor(ComboId);
					UpdateTextField2Compressor(pac.getCurrentCompressor());
					String tmp = textFieldCompressorName.getText();
					//Impossible to rename an item, so we will create a new one, and delete the old
					comboBoxCompressor.insertItemAt(tmp, ComboId);
					comboBoxCompressor.removeItemAt(ComboId+1);
				}
			}
		});
		btnSaveCompressor.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnSaveCompressor.setBounds(277, 39, 68, 23);
		panelSroll.add(btnSaveCompressor);

		// ---------------------------------------------------------------
		// Delete Compressor 
		// ---------------------------------------------------------------
		JButton btnDeleteCompressor = new JButton("Suppr.");
		btnDeleteCompressor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCompressor.getSelectedIndex();
				if ( ComboId > 0 ) {
					pac.removeCompressor(ComboId);
					comboBoxCompressor.removeItemAt(ComboId);
					comboBoxCompressor.setSelectedIndex(ComboId-1);
				} else {
					JOptionPane.showMessageDialog(panelSroll, "This entry cannot be deleted");
				}
			}
		});
		btnDeleteCompressor.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnDeleteCompressor.setBounds(355, 39, 68, 23);
		panelSroll.add(btnDeleteCompressor);

		// ---------------------------------------------------------------
		// New Compressor
		// ---------------------------------------------------------------
		JButton btnNewCompressor = new JButton("Nouv.");
		btnNewCompressor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCompressor.getSelectedIndex();
				ComboId++;
				pac.addNewCompressor(ComboId);
				comboBoxCompressor.insertItemAt("Empty", ComboId);
				comboBoxCompressor.setSelectedIndex(ComboId);
				textFieldCompressorName.setText("Empty");
				pac.getCompressorNb(ComboId).setName("Empty");
			}
		});
		btnNewCompressor.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnNewCompressor.setBounds(199, 39, 68, 23);
		panelSroll.add(btnNewCompressor);

	}

}
