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
import gui.GuiConfig;
import gui.helpaboutdef.WinAbout;

import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import pac.Circulator;
import pac.Pac;
import translation.TCirculator;
import translation.TLanguage;
import javax.swing.ImageIcon;


public class WinCirculatorDistr extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());


	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private Pac pac;
	private GuiConfig guiConfig;

	// Win Builder
	private JTextField textFieldCirculatorDistrVoltage;
	private JTextField textFieldCirculatorDistrName;
	private JLabel lblVoltage;
	private JTextField textFieldFeatureCurrent;
	private JTextField textFieldFeaturePower;
	private JTextField textFieldFeatureRotatePerMinutes;
	private JComboBox<String> comboBoxFeature;

	private JLabel lblPower;
	private JLabel lblRotatePerMinutes;
	private JLabel lblCurrent;
	private JPanel panel1;
	private JPanel panel2;
	private JButton btnAutoRenameFeature;
	private JButton btnFeatureNew;
	private JButton btnFeatureSave;
	private JButton btnFeatureDelete;
	private JButton btnSaveCirculatorDistr;
	private JPanel panelCirculator;
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
					WinCirculatorDistr WinCirculatorDistrFrame = new WinCirculatorDistr(pac, guiConfig );
					// First fillCompressorTextField then applyConfig 
					WinCirculatorDistrFrame.fillCirculatorDistrTexField();
					WinCirculatorDistrFrame.applyConfig();
					WinCirculatorDistrFrame.setVisible(true);		
					
					guiConfig.setLanguage(TLanguage.FRENCH);
					WinCirculatorDistrFrame.applyConfig();

				} catch (Exception e) {
					logger.error(e);
				}
			}
		});
	}

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	/**
	 * WinCirculatorDistr
	 * 	@param pac --> PAC
	 * 	@param vguiConfig --> Whole configuration of PAC TOOL GUI
	 */
	public WinCirculatorDistr(Pac vpac, GuiConfig vguiConfig) {
		pac = vpac;
		guiConfig =vguiConfig;
		initialize();
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * fillCirculatorDistrTexField
	 * 		Fill CirculatorDistr GUI Text field 
	 * 		The data are read from the circulatorDistr variable, where the information is stored
	 * 		The data are always stored in SI format !
	 * 	@param circulatorDistr
	 */
	private void fillCirculatorDistrTexField() {
		Circulator circulatorDistr;
		circulatorDistr = pac.getCirculatorDistr();
		
		logger.info("(fillCirculatorDistrTexField) Circulator Name {}",circulatorDistr.getName());

		textFieldCirculatorDistrName.setText(circulatorDistr.getName());
		textFieldCirculatorDistrVoltage.setText(String.valueOf(circulatorDistr.getVoltage()));
		
		fillCirculatorDistrFeature(circulatorDistr);
	}

	/**
	 * fillCirculatorDistrFeature
	 * 		Fill the Circulator (ACTIVE Feature) GUI text Feature
	 * 	@param circulatorDistr
	 */
	private void fillCirculatorDistrFeature(Circulator circulatorDistr) {
		textFieldFeatureCurrent.setText(String.valueOf(circulatorDistr.getActiveFeatureCurrent()));
		textFieldFeaturePower.setText(String.valueOf(circulatorDistr.getActiveFeaturePower()));
		textFieldFeatureRotatePerMinutes.setText(String.valueOf(circulatorDistr.getActiveFeatureRotatePerMinutes()));
	}
	
	/**
	 * Apply the WinPac GUI configuration to GUI CirculatorDistr.
	 * @param guiConfig
	 */
	public void applyConfig() {
		changeLanguage();
	}


	/**
	 * Will save the information from Panel TextField to PAC variable
	 * Data will ALWAYS be stored in International System : SI Format
	 * @param paci
	 */
	private void saveTextField2CirculatorDistr( Circulator circulatorDistr) {
		// AT that stage all information are in SI !
		circulatorDistr.setName(textFieldCirculatorDistrName.getText());
		circulatorDistr.setVoltage(Double.valueOf(textFieldCirculatorDistrVoltage.getText()));
	}

	private void saveTextField2CirculatorDistrFeature( Circulator circulatorDistr) {
		circulatorDistr.setActiveFeatureCurrent(Double.valueOf(textFieldFeatureCurrent.getText()));
		circulatorDistr.setActiveFeaturePower(Integer.valueOf(textFieldFeaturePower.getText()));
		circulatorDistr.setActiveFeatureRotatePerMinutes(Integer.valueOf(textFieldFeatureRotatePerMinutes.getText()));
	}

	private void changeLanguage(){
		
		this.setTitle(TCirculator.CIRCUL_TITLE_DISTRIBUTION.getLangue(guiConfig.getLanguage()));

		lblVoltage.setText(TCirculator.CIRCUL_VOLTAGE.getLangue(guiConfig.getLanguage()));
		lblRotatePerMinutes.setText(TCirculator.CIRCUL_ROTATE_PER_MINUTES.getLangue(guiConfig.getLanguage()));
		lblPower.setText(TCirculator.CIRCUL_POWER.getLangue(guiConfig.getLanguage()));
		lblCurrent.setText(TCirculator.CIRCUL_CURRENT.getLangue(guiConfig.getLanguage()));
		
		TitledBorder panel1_Border = (TitledBorder) panel1.getBorder();
		panel1_Border.setTitle(TCirculator.CIRCUL_MANUFACTURER_DATA.getLangue(guiConfig.getLanguage()));

		TitledBorder panel2_Border = (TitledBorder) panel2.getBorder();
		panel2_Border.setTitle(TCirculator.CIRCUL_FEATURES.getLangue(guiConfig.getLanguage()));
		
		btnAutoRenameFeature.setText(TCirculator.CIRCUL_RENAME_LIST.getLangue(guiConfig.getLanguage()));
		btnFeatureNew.setText(TCirculator.CIRCUL_NEW.getLangue(guiConfig.getLanguage()));
		btnFeatureSave.setText(TCirculator.CIRCUL_SAVE.getLangue(guiConfig.getLanguage()));
		btnSaveCirculatorDistr.setText(TCirculator.CIRCUL_SAVE.getLangue(guiConfig.getLanguage()));
		btnFeatureDelete.setText(TCirculator.CIRCUL_DELETE.getLangue(guiConfig.getLanguage()));
		
		tabbedPane.setTitleAt(0, TCirculator.CIRCUL_TITLE.getLangue(guiConfig.getLanguage()));
		

	}


	// -------------------------------------------------------
	// 				 GENERATED BY WIN BUILDER
	// -------------------------------------------------------


	public void initialize() {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			logger.info(e);
		}

		setTitle("Circulator Source");
		setIconImage(Toolkit.getDefaultToolkit().getImage(WinAbout.class.getResource("/gui/images/PAC-Tool_16.png")));
		setBounds(100, 100, 437, 428);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		// ===============================================================================================================
		//													TABBED PANE
		// ===============================================================================================================

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 431, 399);
		getContentPane().add(tabbedPane);

		// ===============================================================================================================
		//									                 PANEL COMPRESSOR
		// ===============================================================================================================
		panelCirculator = new JPanel();
		tabbedPane.addTab("Circulator", null, panelCirculator, null);
		panelCirculator.setLayout(null);

		
		// ================================================================
		// 					  	 To Be created at First
		// ================================================================

		// ================================================================
		// 					  	 Panel
		// ================================================================
		panel1 = new JPanel();
		panel1.setBounds(6, 36, 279, 93);
		panelCirculator.add(panel1);
		panel1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Donn\u00E9es Constructeur", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel1.setLayout(null);

		// ---------------------------------------------------------------
		// Voltage
		// ---------------------------------------------------------------
		lblVoltage = new JLabel("Voltage :");
		lblVoltage.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVoltage.setBounds(10, 28, 52, 14);
		panel1.add(lblVoltage);

		textFieldCirculatorDistrVoltage = new JTextField();
		textFieldCirculatorDistrVoltage.setBounds(72, 25, 67, 20);
		panel1.add(textFieldCirculatorDistrVoltage);

		textFieldCirculatorDistrVoltage.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCirculatorDistrVoltage.setColumns(10);

		JLabel lblUnityVoltage = new JLabel("V");
		lblUnityVoltage.setBounds(149, 28, 25, 14);
		panel1.add(lblUnityVoltage);

		// ---------------------------------------------------------------
		// CirculatorDistr Save
		// ---------------------------------------------------------------
		btnSaveCirculatorDistr = new JButton("Sauv.");
		btnSaveCirculatorDistr.setBounds(345, 343, 68, 23);
		panelCirculator.add(btnSaveCirculatorDistr);
		btnSaveCirculatorDistr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					saveTextField2CirculatorDistr(pac.getCirculatorDistr());
					saveTextField2CirculatorDistrFeature(pac.getCirculatorDistr());
			}
		});
		btnSaveCirculatorDistr.setFont(new Font("Tahoma", Font.PLAIN, 9));

		// =================================================================================

		panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(null, "Features", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel2.setBounds(7, 136, 410, 199);
		panelCirculator.add(panel2);
		panel2.setLayout(null);

		// -----------------------
		// Feature Save
		// -----------------------
		btnFeatureSave = new JButton("Sauv.");
		btnFeatureSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					Circulator circulatorDistr = pac.getCirculatorDistr();
					saveTextField2CirculatorDistrFeature(circulatorDistr);
			}
		});
		btnFeatureSave.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnFeatureSave.setBounds(257, 64, 68, 23);
		panel2.add(btnFeatureSave);

		// -----------------------
		// Feature Delete
		// -----------------------
		btnFeatureDelete = new JButton("Suppr.");
		btnFeatureDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int featureId = comboBoxFeature.getSelectedIndex();

				if (featureId>0) {
					logger.trace("Delete featureId={} ",featureId);

					Circulator circulatorDistr = pac.getCirculatorDistr();
					circulatorDistr.selectActiveFeature(featureId);
					circulatorDistr.clearActiveFeatures();

					comboBoxFeature.removeItemAt(featureId);
					comboBoxFeature.setSelectedIndex(featureId-1);

				} else {
					JOptionPane.showMessageDialog(panel1, "This entry cannot be deleted");
				}
			}
		});
		btnFeatureDelete.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnFeatureDelete.setBounds(332, 64, 68, 23);
		panel2.add(btnFeatureDelete);

		// -----------------------
		// Feature New
		// -----------------------
		btnFeatureNew = new JButton("Nouv.");
		btnFeatureNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int featureId = comboBoxFeature.getItemCount();
				if  (featureId>=0) {
					logger.trace("New featureId={}",featureId);
					Circulator circulatorDistr = pac.getCirculatorDistr();
					circulatorDistr.selectActiveFeature(featureId);
					circulatorDistr.addFeatures(0.0, 0, 0);
					comboBoxFeature.insertItemAt("Feature :"+featureId, featureId);
					comboBoxFeature.setSelectedIndex(featureId);
				}				
			}
		});
		btnFeatureNew.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnFeatureNew.setBounds(179, 64, 68, 23);
		panel2.add(btnFeatureNew);

		lblRotatePerMinutes = new JLabel("Rotate Per Minutes :");
		lblRotatePerMinutes.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRotatePerMinutes.setBounds(22, 113, 113, 14);
		panel2.add(lblRotatePerMinutes);

		textFieldFeatureRotatePerMinutes = new JTextField();
		textFieldFeatureRotatePerMinutes.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldFeatureRotatePerMinutes.setColumns(10);
		textFieldFeatureRotatePerMinutes.setBounds(145, 110, 67, 20);
		panel2.add(textFieldFeatureRotatePerMinutes);

		JLabel lblRmn = new JLabel("r/mn");
		lblRmn.setBounds(222, 113, 45, 14);
		panel2.add(lblRmn);

		lblPower = new JLabel("Power :");
		lblPower.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPower.setBounds(83, 141, 52, 14);
		panel2.add(lblPower);

		textFieldFeaturePower = new JTextField();
		textFieldFeaturePower.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldFeaturePower.setColumns(10);
		textFieldFeaturePower.setBounds(145, 138, 67, 20);
		panel2.add(textFieldFeaturePower);

		JLabel lblKw = new JLabel("Kw");
		lblKw.setBounds(222, 141, 25, 14);
		panel2.add(lblKw);

		lblCurrent = new JLabel("Current :");
		lblCurrent.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrent.setBounds(83, 169, 52, 14);
		panel2.add(lblCurrent);

		textFieldFeatureCurrent = new JTextField();
		textFieldFeatureCurrent.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldFeatureCurrent.setColumns(10);
		textFieldFeatureCurrent.setBounds(145, 166, 67, 20);
		panel2.add(textFieldFeatureCurrent);

		JLabel lblA = new JLabel("A");
		lblA.setBounds(222, 169, 25, 14);
		panel2.add(lblA);

		// -----------------------
		// Combobox Feature (Must be positioned At the end because will access to TextField)
		// -----------------------
		comboBoxFeature = new JComboBox<String>();
		comboBoxFeature.setEditable(true);
		comboBoxFeature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int featureId = comboBoxFeature.getSelectedIndex();

				if (featureId>=0) {
					logger.trace("comboBoxFeature move to featureId={}",featureId);

					Circulator circulatorDistr = pac.getCirculatorDistr();
					circulatorDistr.selectActiveFeature(featureId);
					fillCirculatorDistrFeature(circulatorDistr);
				}
			}
		});
		comboBoxFeature.setBounds(239, 27, 131, 20);
		// Fill Feature for Combobox 0
		for(int i=0;i<pac.getCirculatorDistr().getNbOfFeatures();i++) {
			comboBoxFeature.addItem("Feature:"+i);
		}
		fillCirculatorDistrFeature(pac.getCirculatorDistr());
		panel2.add(comboBoxFeature);

		// -----------------------
		// Button Feature Auto Rename (Must be at the end-end)
		// -----------------------
		btnAutoRenameFeature = new JButton("Rename List");
		btnAutoRenameFeature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int featureId = comboBoxFeature.getSelectedIndex();

				if (featureId>=0) {
					
					// Do not use comboBoxFeature.removeItemAt(..) because 
					// it will move the combo Box, and launch the function behind 
					comboBoxFeature.removeAllItems();

					//int tmpcnt = comboBoxFeature.getItemCount();
					//for(int i=0;i<tmpcnt;i++) {
					//	comboBoxFeature.removeItemAt(0);
					//}

					for(int i=0;i<pac.getCirculatorDistr().getNbOfFeatures();i++) {
						comboBoxFeature.addItem("Feature:"+i);
					}
					comboBoxFeature.setSelectedIndex(featureId);
				}
			}
		});
		btnAutoRenameFeature.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnAutoRenameFeature.setBounds(139, 26, 90, 23);
		panel2.add(btnAutoRenameFeature);
		
				// ---------------------------------------------------------------
				// CirculatorDistr Name
				// ---------------------------------------------------------------
				textFieldCirculatorDistrName = new JTextField();
				textFieldCirculatorDistrName.setText("Circulator");
				textFieldCirculatorDistrName.setBounds(249, 7, 167, 34);
				panelCirculator.add(textFieldCirculatorDistrName);
				textFieldCirculatorDistrName.setToolTipText("Name can be modified");
				textFieldCirculatorDistrName.setForeground(new Color(0, 0, 0));
				textFieldCirculatorDistrName.setBorder(null);
				textFieldCirculatorDistrName.setBackground(UIManager.getColor("DesktopPane.background"));
				textFieldCirculatorDistrName.setHorizontalAlignment(SwingConstants.RIGHT);
				textFieldCirculatorDistrName.setFont(new Font("Tahoma", Font.BOLD, 14));
				textFieldCirculatorDistrName.setColumns(10);
				
				JLabel lblNewLabel = new JLabel("");
				lblNewLabel.setBounds(319, 45, 68, 76);
				panelCirculator.add(lblNewLabel);
				lblNewLabel.setIcon(new ImageIcon(WinCirculatorDistr.class.getResource("/gui/images/circulator_64.png")));

	}
}
