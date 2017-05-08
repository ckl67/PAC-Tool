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


public class WinCirculatorSrc extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(WinCirculatorSrc.class.getName());

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private Pac pac;
	private GuiConfig guiConfig;

	// Win Builder
	private JTextField textFieldCirculatorSrcVoltage;
	private JTextField textFieldCirculatorSrcName;
	private JComboBox<String> comboBoxCirculatorSrc;
	private JLabel lblVoltage;
	private JTextField textFieldFeatureCurrent;
	private JTextField textFieldFeaturePower;
	private JTextField textFieldFeatureRotatePerMinutes;
	private JComboBox<String> comboBoxFeature;

	private JLabel lblPower;
	// -------------------------------------------------------
	// 				TEST THE APPLICATION STANDALONE 
	// -------------------------------------------------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {				
					WinCirculatorSrc frame = new WinCirculatorSrc(new Pac(), new GuiConfig() );
					frame.setVisible(true);

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
	 * WinCirculatorSrc
	 * 	@param pac --> PAC
	 * 	@param vguiConfig --> Whole configuration of PAC TOOL GUI
	 */
	public WinCirculatorSrc(Pac vpac, GuiConfig vguiConfig) {
		pac = vpac;
		guiConfig =vguiConfig;
		initialize();
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Apply the WinPac GUI configuration to GUI CirculatorSrc.
	 * @param guiConfig
	 */
	public void applyConfig(GuiConfig vguiConfig) {

		logger.info("applyConfig : NB CirculatorSrc={}", pac.getNbOfCirculatorSrcNb());
		// Remove all CirculatorSrc items in ComboBox (except the first)
//		int tmpcnt = comboBoxCirculatorSrc.getItemCount();
//		for(int i=1;i<tmpcnt;i++) {
//			comboBoxCirculatorSrc.removeItemAt(i);
//		}

		// Fill CirculatorSrc ComboBox
//		for(int i=1;i<pac.getNbOfCirculatorSrcNb();i++) {
//			comboBoxCirculatorSrc.insertItemAt(pac.getCirculatorSrcNb(i).getName(),i);
//		}
		comboBoxCirculatorSrc.setSelectedIndex(0);
		pac.selectCurrentCirculatorSrc(0);
		fillCirculatorSrcTexField(pac.getCurrentCirculatorSrc());
	}


	/**
	 * fillCirculatorSrcTexField
	 * 		Fill CirculatorSrc GUI Text field 
	 * 		The data are read from the circulatorSrc variable, where the information is stored
	 * 		The data are always stored in SI format !
	 * 	@param circulatorSrc
	 */
	private void fillCirculatorSrcTexField(Circulator circulatorSrc) {
		textFieldCirculatorSrcName.setText(circulatorSrc.getName());
		textFieldCirculatorSrcVoltage.setText(String.valueOf(circulatorSrc.getVoltage()));
	}

	/**
	 * fillCirculatorSrcFeature
	 * 		Fill the Circulator (ACTIVE Feature) GUI text Feature
	 * 	@param circulatorSrc
	 */
	private void fillCirculatorSrcFeature(Circulator circulatorSrc) {
		textFieldFeatureCurrent.setText(String.valueOf(circulatorSrc.getActiveFeatureCurrent()));
		textFieldFeaturePower.setText(String.valueOf(circulatorSrc.getActiveFeaturePower()));
		textFieldFeatureRotatePerMinutes.setText(String.valueOf(circulatorSrc.getActiveFeatureRotatePerMinutes()));
	}


	/**
	 * Will save the information from Panel TextField to PAC variable
	 * Data will ALWAYS be stored in International System : SI Format
	 * @param paci
	 */
	private void UpdateTextField2CirculatorSrc( Circulator circulatorSrc) {
		// AT that stage all information are in SI !
		circulatorSrc.setName(textFieldCirculatorSrcName.getText());
		circulatorSrc.setVoltage(Double.valueOf(textFieldCirculatorSrcVoltage.getText()));
	}

	private void UpdateTextField2CirculatorSrcFeature( Circulator circulatorSrc) {
		circulatorSrc.setActiveFeatureCurrent(Double.valueOf(textFieldFeatureCurrent.getText()));
		circulatorSrc.setActiveFeaturePower(Integer.valueOf(textFieldFeaturePower.getText()));
		circulatorSrc.setActiveFeatureRotatePerMinutes(Integer.valueOf(textFieldFeatureRotatePerMinutes.getText()));
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
		setBounds(100, 100, 437, 431);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		// ===============================================================================================================
		//													TABBED PANE
		// ===============================================================================================================

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 431, 401);
		getContentPane().add(tabbedPane);

		// ===============================================================================================================
		//									                 PANEL COMPRESSOR
		// ===============================================================================================================
		JPanel panelCirculator = new JPanel();
		tabbedPane.addTab("Circulator", null, panelCirculator, null);
		panelCirculator.setLayout(null);

		
		// ================================================================
		// 					  	 To Be created at First
		// ================================================================

		// ================================================================
		// 					  	 Panel
		// ================================================================
		JPanel panel1 = new JPanel();
		panel1.setBounds(10, 88, 410, 71);
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

		textFieldCirculatorSrcVoltage = new JTextField();
		textFieldCirculatorSrcVoltage.setBounds(72, 25, 67, 20);
		panel1.add(textFieldCirculatorSrcVoltage);

		textFieldCirculatorSrcVoltage.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldCirculatorSrcVoltage.setColumns(10);

		JLabel lblUnityVoltage = new JLabel("V");
		lblUnityVoltage.setBounds(149, 28, 25, 14);
		panel1.add(lblUnityVoltage);

		// ---------------------------------------------------------------
		// CirculatorSrc Name
		// ---------------------------------------------------------------
		textFieldCirculatorSrcName = new JTextField();
		textFieldCirculatorSrcName.setBounds(10, 11, 167, 52);
		panelCirculator.add(textFieldCirculatorSrcName);
		textFieldCirculatorSrcName.setToolTipText("Name can be modified");
		textFieldCirculatorSrcName.setForeground(new Color(0, 0, 128));
		textFieldCirculatorSrcName.setBorder(null);
		textFieldCirculatorSrcName.setBackground(UIManager.getColor("DesktopPane.background"));
		textFieldCirculatorSrcName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCirculatorSrcName.setFont(new Font("Tahoma", Font.BOLD, 16));
		textFieldCirculatorSrcName.setColumns(10);

		// ---------------------------------------------------------------
		// CirculatorSrc Save
		// ---------------------------------------------------------------
		JButton btnSaveCirculatorSrc = new JButton("Sauv.");
		btnSaveCirculatorSrc.setBounds(265, 48, 68, 23);
		panelCirculator.add(btnSaveCirculatorSrc);
		btnSaveCirculatorSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCirculatorSrc.getSelectedIndex();
				if ( ComboId >= 0 ) {
					logger.info("Save CirculatorSrc {}", ComboId );
					pac.selectCurrentCirculatorSrc(ComboId);
					UpdateTextField2CirculatorSrc(pac.getCurrentCirculatorSrc());
					UpdateTextField2CirculatorSrcFeature(pac.getCurrentCirculatorSrc());
					String tmp = textFieldCirculatorSrcName.getText();
					//Impossible to rename an item, so we will create a new one, and delete the old
					comboBoxCirculatorSrc.insertItemAt(tmp, ComboId);
					comboBoxCirculatorSrc.removeItemAt(ComboId+1);
				}
			}
		});
		btnSaveCirculatorSrc.setFont(new Font("Tahoma", Font.PLAIN, 9));

		// ---------------------------------------------------------------
		// Delete CirculatorSrc 
		// ---------------------------------------------------------------
		JButton btnDeleteCirculatorSrc = new JButton("Suppr.");
		btnDeleteCirculatorSrc.setBounds(340, 48, 68, 23);
		panelCirculator.add(btnDeleteCirculatorSrc);
		btnDeleteCirculatorSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCirculatorSrc.getSelectedIndex();
				if ( ComboId > 0 ) {
					logger.trace("pac.removeCirculatorSrc {}", ComboId);
					pac.removeCirculatorSrc(ComboId);
					comboBoxCirculatorSrc.removeItemAt(ComboId);
					comboBoxCirculatorSrc.setSelectedIndex(ComboId-1);
				} else {
					JOptionPane.showMessageDialog(getContentPane(), "This entry cannot be deleted");
				}
			}
		});
		btnDeleteCirculatorSrc.setFont(new Font("Tahoma", Font.PLAIN, 9));

		// ---------------------------------------------------------------
		// New CirculatorSrc
		// ---------------------------------------------------------------
		JButton btnNewCirculatorSrc = new JButton("Nouv.");
		btnNewCirculatorSrc.setBounds(187, 48, 68, 23);
		panelCirculator.add(btnNewCirculatorSrc);
		btnNewCirculatorSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCirculatorSrc.getSelectedIndex();
				ComboId++;
				pac.addNewCirculatorSrc(ComboId);
				pac.selectCurrentCirculatorSrc(ComboId);

				Circulator circulatorSrc = pac.getCirculatorSrcNb(ComboId);
				circulatorSrc.setName("Empty");

				// For a new item we have to reset the feature list
				if (comboBoxFeature != null) {
					// Normally Do not use comboBoxFeature.removeItemAt(..) 
					// because it will move the combo Box, and launch the function behind
					// But here we must either that it will remove the arrow ???
					//int tmpcnt = comboBoxFeature.getItemCount();
					//for(int i=0;i<tmpcnt;i++) {
					//	comboBoxFeature.removeItemAt(i);
					//}
					
					comboBoxFeature.addItem("Feature: 0");
					comboBoxFeature.setSelectedIndex(0);
				}
				
				comboBoxCirculatorSrc.insertItemAt("Empty", ComboId);
				comboBoxCirculatorSrc.setSelectedIndex(ComboId);
				textFieldCirculatorSrcName.setText("Empty");

			}
		});
		btnNewCirculatorSrc.setFont(new Font("Tahoma", Font.PLAIN, 9));


		// =================================================================================

		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(null, "Features", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel2.setBounds(10, 165, 410, 199);
		panelCirculator.add(panel2);
		panel2.setLayout(null);

		// -----------------------
		// Feature Save
		// -----------------------
		JButton btnFeatureSave = new JButton("Sauv.");
		btnFeatureSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCirculatorSrc.getSelectedIndex();
				int featureId = comboBoxFeature.getSelectedIndex();

				if ((ComboId>=0) && (featureId>=0)) {

					logger.trace("Save featureId= {} For Circulateur Id={}",featureId,ComboId);

					Circulator circulatorSrc = pac.getCirculatorSrcNb(ComboId);
					circulatorSrc.selectActiveFeature(featureId);
					UpdateTextField2CirculatorSrcFeature(circulatorSrc);
				}
			}
		});
		btnFeatureSave.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnFeatureSave.setBounds(257, 64, 68, 23);
		panel2.add(btnFeatureSave);

		// -----------------------
		// Feature Delete
		// -----------------------
		JButton btnFeatureDelete = new JButton("Suppr.");
		btnFeatureDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ComboId = comboBoxCirculatorSrc.getSelectedIndex();
				int featureId = comboBoxFeature.getSelectedIndex();

				if ((ComboId>=0) && (featureId>0)) {
					logger.trace("Delete featureId={} For Circulateur Id={}",featureId,ComboId);

					Circulator circulatorSrc = pac.getCirculatorSrcNb(ComboId);
					circulatorSrc.selectActiveFeature(featureId);
					circulatorSrc.clearActiveFeatures();

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
		JButton btnFeatureNew = new JButton("Nouv.");
		btnFeatureNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ComboId = comboBoxCirculatorSrc.getSelectedIndex();
				int featureId = comboBoxFeature.getItemCount();
				if ((ComboId>=0) && (featureId>=0)) {
					logger.trace("New featureId={} For Circulateur Id={}",featureId,ComboId);
					Circulator circulatorSrc = pac.getCirculatorSrcNb(ComboId);
					circulatorSrc.selectActiveFeature(featureId);
					circulatorSrc.addFeatures(0.0, 0, 0);
					comboBoxFeature.insertItemAt("Feature :"+featureId, featureId);
					comboBoxFeature.setSelectedIndex(featureId);
				}				
			}
		});
		btnFeatureNew.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnFeatureNew.setBounds(179, 64, 68, 23);
		panel2.add(btnFeatureNew);

		JLabel lblRotatePerMinutes = new JLabel("Rotate Per Minutes :");
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

		JLabel lblCurrent = new JLabel("Current :");
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

		// ---------------------------------------------------------------
		// Combobox CirculatorSrc (Must be positioned At the end because will access to TextField)
		// ---------------------------------------------------------------
		comboBoxCirculatorSrc = new JComboBox<String>();
		comboBoxCirculatorSrc.setBounds(252, 11, 131, 20);
		panelCirculator.add(comboBoxCirculatorSrc);
		comboBoxCirculatorSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCirculatorSrc.getSelectedIndex();
				pac.selectCurrentCirculatorSrc(ComboId);	

				Circulator circulatorSrc = pac.getCurrentCirculatorSrc();

				fillCirculatorSrcTexField(circulatorSrc);
				int featureId = circulatorSrc.getActiveFeatureId();
				logger.trace("comboBoxCirculatorSrc: featureId={} For Circulateur Id={}",featureId,ComboId);

				
				fillCirculatorSrcFeature(circulatorSrc);
				if (comboBoxFeature != null) {
					// Do not use comboBoxFeature.removeItemAt(..) because 
					// it will move the combo Box, and launch the function behind 
					comboBoxFeature.removeAllItems();

					for(int i=0;i<pac.getCirculatorSrcNb(ComboId).getNbOfFeatures();i++) {
						comboBoxFeature.addItem("Feature:"+i);
					}
					comboBoxFeature.setSelectedIndex(featureId);
				}

			}
		});
		comboBoxCirculatorSrc.addItem(pac.getCirculatorSrcNb(0).getName());

		// -----------------------
		// Combobox Feature (Must be positioned At the end because will access to TextField)
		// -----------------------
		comboBoxFeature = new JComboBox<String>();
		comboBoxFeature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCirculatorSrc.getSelectedIndex();
				int featureId = comboBoxFeature.getSelectedIndex();

				if ((ComboId>=0) && (featureId>=0)) {
					logger.trace("comboBoxFeature move to featureId={} For Circulateur Id={}",featureId,ComboId);

					Circulator circulatorSrc = pac.getCirculatorSrcNb(ComboId);
					circulatorSrc.selectActiveFeature(featureId);
					fillCirculatorSrcFeature(circulatorSrc);
				}
			}
		});
		comboBoxFeature.setBounds(239, 27, 131, 20);
		// Fill Feature for Combobox 0
		for(int i=0;i<pac.getCirculatorSrcNb(0).getNbOfFeatures();i++) {
			comboBoxFeature.addItem("Feature:"+i);
		}
		fillCirculatorSrcFeature(pac.getCirculatorSrcNb(0));
		panel2.add(comboBoxFeature);

		// -----------------------
		// Button Feature Auto Rename (Must be at the end-end)
		// -----------------------
		JButton btnAutoRenameFeature = new JButton("Rename List");
		btnAutoRenameFeature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ComboId = comboBoxCirculatorSrc.getSelectedIndex();
				int featureId = comboBoxFeature.getSelectedIndex();

				if ((ComboId>=0) && (featureId>=0)) {
					
					// Do not use comboBoxFeature.removeItemAt(..) because 
					// it will move the combo Box, and launch the function behind 
					comboBoxFeature.removeAllItems();

					//int tmpcnt = comboBoxFeature.getItemCount();
					//for(int i=0;i<tmpcnt;i++) {
					//	comboBoxFeature.removeItemAt(0);
					//}

					for(int i=0;i<pac.getCirculatorSrcNb(ComboId).getNbOfFeatures();i++) {
						comboBoxFeature.addItem("Feature:"+i);
					}
					comboBoxFeature.setSelectedIndex(featureId);
				}
			}
		});
		btnAutoRenameFeature.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnAutoRenameFeature.setBounds(139, 26, 90, 23);
		panel2.add(btnAutoRenameFeature);

	}

	void changeLanguage(){
		lblPower.setText(TCirculator.CIRCUL_POWER.getLangue(guiConfig.getLanguage()));
	}
}
