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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import pac.Circulator;
import pac.Pac;
import translation.Translation;


public class WinCirculatorDistr extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(WinCirculatorDistr.class.getName());

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private Pac pac;
	private GuiConfig guiConfig;

	// Win Builder
	private JTextField textFieldCirculatorDistrVoltage;
	private JTextField textFieldCirculatorDistrName;
	private JComboBox<String> comboBoxCirculatorDistr;
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
					WinCirculatorDistr frame = new WinCirculatorDistr(new Pac(), new GuiConfig() );
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
	 * Apply the WinPac GUI configuration to GUI CirculatorDistr.
	 * @param guiConfig
	 */
	public void applyConfig(GuiConfig vguiConfig) {

		logger.info("applyConfig : NB CirculatorDistr={}", pac.getNbOfCirculatorDistrNb());
		// Remove all CirculatorDistr items in ComboBox (except the first)
		int tmpcnt = comboBoxCirculatorDistr.getItemCount();
		for(int i=1;i<tmpcnt;i++) {
			comboBoxCirculatorDistr.removeItemAt(1);
		}

		// Fill CirculatorDistr ComboBox
		for(int i=1;i<pac.getNbOfCirculatorDistrNb();i++) {
			comboBoxCirculatorDistr.insertItemAt(pac.getCirculatorDistrNb(i).getName(),i);
		}
		comboBoxCirculatorDistr.setSelectedIndex(0);
		pac.selectCurrentCirculatorDistr(0);
		fillCirculatorDistrTexField(pac.getCurrentCirculatorDistr());
	}


	/**
	 * fillCirculatorDistrTexField
	 * 		Fill CirculatorDistr GUI Text field 
	 * 		The data are read from the circulatorDistr variable, where the information is stored
	 * 		The data are always stored in SI format !
	 * 	@param circulatorDistr
	 */
	private void fillCirculatorDistrTexField(Circulator circulatorDistr) {
		textFieldCirculatorDistrName.setText(circulatorDistr.getName());
		textFieldCirculatorDistrVoltage.setText(String.valueOf(circulatorDistr.getVoltage()));
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
	 * Will save the information from Panel TextField to PAC variable
	 * Data will ALWAYS be stored in International System : SI Format
	 * @param paci
	 */
	private void UpdateTextField2CirculatorDistr( Circulator circulatorDistr) {
		// AT that stage all information are in SI !
		circulatorDistr.setName(textFieldCirculatorDistrName.getText());
		circulatorDistr.setVoltage(Double.valueOf(textFieldCirculatorDistrVoltage.getText()));
	}

	private void UpdateTextField2CirculatorDistrFeature( Circulator circulatorDistr) {
		circulatorDistr.setActiveFeatureCurrent(Double.valueOf(textFieldFeatureCurrent.getText()));
		circulatorDistr.setActiveFeaturePower(Integer.valueOf(textFieldFeaturePower.getText()));
		circulatorDistr.setActiveFeatureRotatePerMinutes(Integer.valueOf(textFieldFeatureRotatePerMinutes.getText()));
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

		setTitle("Circulator Distribution");
		setIconImage(Toolkit.getDefaultToolkit().getImage(WinAbout.class.getResource("/gui/images/PAC-Tool_16.png")));
		setBounds(100, 100, 436, 403);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		// ================================================================
		// 					  	 To Be created at First
		// ================================================================

		// ================================================================
		// 					  	 Panel
		// ================================================================
		JPanel panel1 = new JPanel();
		panel1.setBounds(10, 88, 410, 71);
		getContentPane().add(panel1);
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
		// CirculatorDistr Name
		// ---------------------------------------------------------------
		textFieldCirculatorDistrName = new JTextField();
		textFieldCirculatorDistrName.setBounds(10, 11, 167, 52);
		getContentPane().add(textFieldCirculatorDistrName);
		textFieldCirculatorDistrName.setToolTipText("Name can be modified");
		textFieldCirculatorDistrName.setForeground(new Color(0, 0, 128));
		textFieldCirculatorDistrName.setBorder(null);
		textFieldCirculatorDistrName.setBackground(UIManager.getColor("DesktopPane.background"));
		textFieldCirculatorDistrName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCirculatorDistrName.setFont(new Font("Tahoma", Font.BOLD, 16));
		textFieldCirculatorDistrName.setColumns(10);

		// ---------------------------------------------------------------
		// CirculatorDistr Save
		// ---------------------------------------------------------------
		JButton btnSaveCirculatorDistr = new JButton("Sauv.");
		btnSaveCirculatorDistr.setBounds(265, 48, 68, 23);
		getContentPane().add(btnSaveCirculatorDistr);
		btnSaveCirculatorDistr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCirculatorDistr.getSelectedIndex();
				if ( ComboId >= 0 ) {
					logger.info("Save CirculatorDistr {}", ComboId );
					pac.selectCurrentCirculatorDistr(ComboId);
					UpdateTextField2CirculatorDistr(pac.getCurrentCirculatorDistr());
					UpdateTextField2CirculatorDistrFeature(pac.getCurrentCirculatorDistr());
					String tmp = textFieldCirculatorDistrName.getText();
					//Impossible to rename an item, so we will create a new one, and delete the old
					comboBoxCirculatorDistr.insertItemAt(tmp, ComboId);
					comboBoxCirculatorDistr.removeItemAt(ComboId+1);
				}
			}
		});
		btnSaveCirculatorDistr.setFont(new Font("Tahoma", Font.PLAIN, 9));

		// ---------------------------------------------------------------
		// Delete CirculatorDistr 
		// ---------------------------------------------------------------
		JButton btnDeleteCirculatorDistr = new JButton("Suppr.");
		btnDeleteCirculatorDistr.setBounds(340, 48, 68, 23);
		getContentPane().add(btnDeleteCirculatorDistr);
		btnDeleteCirculatorDistr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCirculatorDistr.getSelectedIndex();
				if ( ComboId > 0 ) {
					logger.trace("pac.removeCirculatorDistr {}", ComboId);
					pac.removeCirculatorDistr(ComboId);
					comboBoxCirculatorDistr.removeItemAt(ComboId);
					comboBoxCirculatorDistr.setSelectedIndex(ComboId-1);
				} else {
					JOptionPane.showMessageDialog(getContentPane(), "This entry cannot be deleted");
				}
			}
		});
		btnDeleteCirculatorDistr.setFont(new Font("Tahoma", Font.PLAIN, 9));

		// ---------------------------------------------------------------
		// New CirculatorDistr
		// ---------------------------------------------------------------
		JButton btnNewCirculatorDistr = new JButton("Nouv.");
		btnNewCirculatorDistr.setBounds(187, 48, 68, 23);
		getContentPane().add(btnNewCirculatorDistr);
		btnNewCirculatorDistr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCirculatorDistr.getSelectedIndex();
				ComboId++;
				pac.addNewCirculatorDistr(ComboId);
				pac.selectCurrentCirculatorDistr(ComboId);

				Circulator circulatorDistr = pac.getCirculatorDistrNb(ComboId);
				circulatorDistr.setName("Empty");

				// For a new item we have to reset the feature list
				if (comboBoxFeature != null) {
					int tmpcnt = comboBoxFeature.getItemCount();
					for(int i=0;i<tmpcnt;i++) {
						comboBoxFeature.removeItemAt(0);
					}
					comboBoxFeature.addItem("Feature: 0");
					comboBoxFeature.setSelectedIndex(0);
				}
				
				comboBoxCirculatorDistr.insertItemAt("Empty", ComboId);
				comboBoxCirculatorDistr.setSelectedIndex(ComboId);
				textFieldCirculatorDistrName.setText("Empty");

			}
		});
		btnNewCirculatorDistr.setFont(new Font("Tahoma", Font.PLAIN, 9));


		// =================================================================================

		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(null, "Features", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel2.setBounds(10, 165, 410, 199);
		getContentPane().add(panel2);
		panel2.setLayout(null);

		// -----------------------
		// Feature Save
		// -----------------------
		JButton btnFeatureSave = new JButton("Sauv.");
		btnFeatureSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCirculatorDistr.getSelectedIndex();
				int featureId = comboBoxFeature.getSelectedIndex();

				if ((ComboId>=0) && (featureId>=0)) {

					logger.trace("Save featureId= {} For Circulateur Id={}",featureId,ComboId);

					Circulator circulatorDistr = pac.getCirculatorDistrNb(ComboId);
					circulatorDistr.selectActiveFeature(featureId);
					UpdateTextField2CirculatorDistrFeature(circulatorDistr);
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
				int ComboId = comboBoxCirculatorDistr.getSelectedIndex();
				int featureId = comboBoxFeature.getSelectedIndex();

				if ((ComboId>=0) && (featureId>0)) {
					logger.trace("Delete featureId={} For Circulateur Id={}",featureId,ComboId);

					Circulator circulatorDistr = pac.getCirculatorDistrNb(ComboId);
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
		JButton btnFeatureNew = new JButton("Nouv.");
		btnFeatureNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ComboId = comboBoxCirculatorDistr.getSelectedIndex();
				int featureId = comboBoxFeature.getItemCount();
				if ((ComboId>=0) && (featureId>=0)) {
					logger.trace("New featureId={} For Circulateur Id={}",featureId,ComboId);
					Circulator circulatorDistr = pac.getCirculatorDistrNb(ComboId);
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
		// Combobox CirculatorDistr (Must be positioned At the end because will access to TextField)
		// ---------------------------------------------------------------
		comboBoxCirculatorDistr = new JComboBox<String>();
		comboBoxCirculatorDistr.setBounds(252, 11, 131, 20);
		getContentPane().add(comboBoxCirculatorDistr);
		comboBoxCirculatorDistr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCirculatorDistr.getSelectedIndex();
				pac.selectCurrentCirculatorDistr(ComboId);	

				Circulator circulatorDistr = pac.getCurrentCirculatorDistr();

				fillCirculatorDistrTexField(circulatorDistr);
				int featureId = circulatorDistr.getActiveFeatureId();
				logger.trace("comboBoxCirculatorDistr: featureId={} For Circulateur Id={}",featureId,ComboId);

				
				fillCirculatorDistrFeature(circulatorDistr);
				if (comboBoxFeature != null) {
					int tmpcnt = comboBoxFeature.getItemCount();
					for(int i=0;i<tmpcnt;i++) {
						comboBoxFeature.removeItemAt(0);
					}

					for(int i=0;i<pac.getCirculatorDistrNb(ComboId).getNbOfFeatures();i++) {
						comboBoxFeature.addItem("Feature:"+i);
					}
					comboBoxFeature.setSelectedIndex(featureId);
				}

			}
		});
		comboBoxCirculatorDistr.addItem(pac.getCirculatorDistrNb(0).getName());

		// -----------------------
		// Combobox Feature (Must be positioned At the end because will access to TextField)
		// -----------------------
		comboBoxFeature = new JComboBox<String>();
		comboBoxFeature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCirculatorDistr.getSelectedIndex();
				int featureId = comboBoxFeature.getSelectedIndex();

				if ((ComboId>=0) && (featureId>=0)) {
					logger.trace("comboBoxFeature move to featureId={} For Circulateur Id={}",featureId,ComboId);

					Circulator circulatorDistr = pac.getCirculatorDistrNb(ComboId);
					circulatorDistr.selectActiveFeature(featureId);
					fillCirculatorDistrFeature(circulatorDistr);
				}
			}
		});
		comboBoxFeature.setBounds(239, 27, 131, 20);
		// Fill Feature for Combobox 0
		for(int i=0;i<pac.getCirculatorDistrNb(0).getNbOfFeatures();i++) {
			comboBoxFeature.addItem("Feature:"+i);
		}
		fillCirculatorDistrFeature(pac.getCirculatorDistrNb(0));
		panel2.add(comboBoxFeature);

		// -----------------------
		// Button Feature Auto Rename (Must be at the end-end)
		// -----------------------
		JButton btnAutoRenameFeature = new JButton("Rename List");
		btnAutoRenameFeature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ComboId = comboBoxCirculatorDistr.getSelectedIndex();
				int featureId = comboBoxFeature.getSelectedIndex();

				if ((ComboId>=0) && (featureId>=0)) {
					int tmpcnt = comboBoxFeature.getItemCount();
					for(int i=0;i<tmpcnt;i++) {
						comboBoxFeature.removeItemAt(0);
					}

					for(int i=0;i<pac.getCirculatorDistrNb(ComboId).getNbOfFeatures();i++) {
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
		lblPower.setText(Translation.Power.getLangue(guiConfig.getLanguage()));
	}
}
