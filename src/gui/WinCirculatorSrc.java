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


public class WinCirculatorSrc extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(WinCirculatorSrc.class.getName());

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private Pac pac;
	private WinPacToolConfig winPacToolConfig;

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
					WinCirculatorSrc frame = new WinCirculatorSrc(new Pac(), new WinPacToolConfig() );
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
	 * WinCirculatorSrc
	 * 	@param pac --> PAC
	 * 	@param vwinPacToolConfig --> Whole configuration of PAC TOOL GUI
	 */
	public WinCirculatorSrc(Pac vpac, WinPacToolConfig vwinPacToolConfig) {
		pac = vpac;
		winPacToolConfig =vwinPacToolConfig;

		initialize();
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Apply the WinPac GUI configuration to GUI CirculatorSrc.
	 * @param winPacToolConfig
	 */
	public void applyConfig(WinPacToolConfig vwinPacToolConfig) {

		logger.info("applyConfig : NB CirculatorSrc={}", pac.getNbOfCirculatorSrcNb());
		// Remove all CirculatorSrc items in ComboBox (except the first)
		for(int i=1;i<comboBoxCirculatorSrc.getItemCount();i++) {
			comboBoxCirculatorSrc.removeItemAt(1);
		}

		// Fill CirculatorSrc ComboBox
		for(int i=1;i<pac.getNbOfCirculatorSrcNb();i++) {
			comboBoxCirculatorSrc.insertItemAt(pac.getCirculatorSrcNb(i).getName(),i);
		}
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
	 * fillCirculatorFeature
	 * 		Fill the Circulator (ACTIVE Feature) GUI text Feature
	 * 	@param circulatorSrc
	 */
	private void fillCirculatorFeature(Circulator circulatorSrc) {
		System.out.println("why"+circulatorSrc.getActiveFeatureCurrent());
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
		setBounds(100, 100, 436, 371);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		// ================================================================
		// 					  	 To Be created at First
		// ================================================================
		comboBoxCirculatorSrc = new JComboBox<String>();
		comboBoxFeature = new JComboBox<String>();
		textFieldFeatureCurrent = new JTextField();
		textFieldFeaturePower = new JTextField();
		textFieldFeatureRotatePerMinutes = new JTextField();

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
		getContentPane().add(textFieldCirculatorSrcName);
		textFieldCirculatorSrcName.setToolTipText("Name can be modified");
		textFieldCirculatorSrcName.setForeground(new Color(0, 0, 128));
		textFieldCirculatorSrcName.setBorder(null);
		textFieldCirculatorSrcName.setBackground(UIManager.getColor("DesktopPane.background"));
		textFieldCirculatorSrcName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCirculatorSrcName.setFont(new Font("Tahoma", Font.BOLD, 16));
		textFieldCirculatorSrcName.setColumns(10);

		// ---------------------------------------------------------------
		// Combo box CirculatorSrc
		// ---------------------------------------------------------------
		comboBoxCirculatorSrc.setBounds(252, 11, 131, 20);
		getContentPane().add(comboBoxCirculatorSrc);
		comboBoxCirculatorSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Choice comboBoxCirculatorSrc");
				int ComboId = comboBoxCirculatorSrc.getSelectedIndex();
				Circulator circulatorSrc = pac.getCirculatorSrcNb(ComboId);
				fillCirculatorSrcTexField(circulatorSrc);
				pac.selectCurrentCirculatorSrc(ComboId);	

				// Remove all items in the combobox
				System.out.println("comboBoxFeature.getItemCount() = "+comboBoxFeature.getItemCount());
				int itemCountFeature = comboBoxFeature.getItemCount();
				for(int i=0;i<itemCountFeature;i++){
					comboBoxFeature.removeItemAt(0);
				}

				System.out.println("comboBoxFeature.getItemCount() = "+comboBoxFeature.getItemCount());
				// Add Item
				for(int i=0;i<circulatorSrc.getNbOfFeatures();i++) {
					comboBoxFeature.addItem("Feature:"+i);
				}
				System.out.println("comboBoxFeature.getItemCount() = "+comboBoxFeature.getItemCount());

				System.out.println("---------> DEBUG 2  comboBoxFeature.getItemCount() = "+comboBoxFeature.getItemCount());
				System.out.println("---------> DEBUG 3  circulatorSrc.getNbOfFeatures() = "+circulatorSrc.getNbOfFeatures());

				// Select the feature for the current circulatorSrc
				comboBoxFeature.setSelectedIndex(0);
				fillCirculatorFeature(circulatorSrc);


			}
		});
		comboBoxCirculatorSrc.addItem(pac.getCirculatorSrcNb(0).getName());


		// ---------------------------------------------------------------
		// CirculatorSrc Save
		// ---------------------------------------------------------------
		JButton btnSaveCirculatorSrc = new JButton("Sauv.");
		btnSaveCirculatorSrc.setBounds(265, 48, 68, 23);
		getContentPane().add(btnSaveCirculatorSrc);
		btnSaveCirculatorSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBoxCirculatorSrc.getSelectedIndex();
				if ( ComboId >= 0 ) {
					logger.info("Save CirculatorSrc {}", ComboId );
					pac.selectCurrentCirculatorSrc(ComboId);
					UpdateTextField2CirculatorSrc(pac.getCurrentCirculatorSrc());
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
		getContentPane().add(btnDeleteCirculatorSrc);
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
		getContentPane().add(btnNewCirculatorSrc);
		btnNewCirculatorSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Nouv");
				int ComboId = comboBoxCirculatorSrc.getSelectedIndex();
				ComboId++;
				pac.addNewCirculatorSrc(ComboId);
				comboBoxCirculatorSrc.insertItemAt("Empty", ComboId);
				comboBoxCirculatorSrc.setSelectedIndex(ComboId);
				textFieldCirculatorSrcName.setText("Empty");

				Circulator circulatorSrc = pac.getCirculatorSrcNb(ComboId);
				circulatorSrc.setName("Empty");

				// Select 1 Feature for the new Circulator
				comboBoxFeature.setSelectedIndex(0);
				circulatorSrc.selectActiveFeature(0);
				fillCirculatorFeature(circulatorSrc);
				System.out.println(circulatorSrc.getName());
				System.out.println(circulatorSrc.getActiveFeaturePower());
			}
		});
		btnNewCirculatorSrc.setFont(new Font("Tahoma", Font.PLAIN, 9));

		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(null, "Features", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel2.setBounds(10, 170, 410, 165);
		getContentPane().add(panel2);
		panel2.setLayout(null);

		JButton btnFeatureSave = new JButton("Sauv.");
		btnFeatureSave.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnFeatureSave.setBounds(245, 26, 68, 23);
		panel2.add(btnFeatureSave);

		JButton btnFeatureDelete = new JButton("Suppr.");
		btnFeatureDelete.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnFeatureDelete.setBounds(320, 26, 68, 23);
		panel2.add(btnFeatureDelete);

		JButton btnFeatureNew = new JButton("Nouv.");
		btnFeatureNew.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnFeatureNew.setBounds(167, 26, 68, 23);
		panel2.add(btnFeatureNew);

		JLabel lblRotatePerMinutes = new JLabel("Rotate Per Minutes :");
		lblRotatePerMinutes.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRotatePerMinutes.setBounds(68, 70, 113, 14);
		panel2.add(lblRotatePerMinutes);

		textFieldFeatureRotatePerMinutes.setText("a");
		textFieldFeatureRotatePerMinutes.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldFeatureRotatePerMinutes.setColumns(10);
		textFieldFeatureRotatePerMinutes.setBounds(191, 67, 67, 20);
		panel2.add(textFieldFeatureRotatePerMinutes);

		JLabel lblRmn = new JLabel("r/mn");
		lblRmn.setBounds(268, 70, 45, 14);
		panel2.add(lblRmn);

		lblPower = new JLabel("Power :");
		lblPower.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPower.setBounds(129, 98, 52, 14);
		panel2.add(lblPower);

		textFieldFeaturePower.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldFeaturePower.setColumns(10);
		textFieldFeaturePower.setBounds(191, 95, 67, 20);
		panel2.add(textFieldFeaturePower);

		JLabel lblKw = new JLabel("Kw");
		lblKw.setBounds(268, 98, 25, 14);
		panel2.add(lblKw);

		JLabel lblCurrent = new JLabel("Current :");
		lblCurrent.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCurrent.setBounds(129, 126, 52, 14);
		panel2.add(lblCurrent);

		textFieldFeatureCurrent.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldFeatureCurrent.setColumns(10);
		textFieldFeatureCurrent.setBounds(191, 123, 67, 20);
		panel2.add(textFieldFeatureCurrent);

		JLabel lblA = new JLabel("A");
		lblA.setBounds(268, 126, 25, 14);
		panel2.add(lblA);

		comboBoxFeature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("comboBoxFeature move");

				int ComboId = comboBoxCirculatorSrc.getSelectedIndex();
				int featureId = comboBoxFeature.getSelectedIndex();

				System.out.println("comboBoxFeature move :: featureId" + featureId);

				Circulator circulatorSrc = pac.getCirculatorSrcNb(ComboId);
				circulatorSrc.selectActiveFeature(featureId);
				fillCirculatorFeature(circulatorSrc);
			}
		});
		comboBoxFeature.setBounds(10, 27, 131, 20);
		fillCirculatorFeature(pac.getCirculatorSrcNb(0));
		panel2.add(comboBoxFeature);
	}
	
	void changeLanguage(){
		lblPower.setText(Translation.Power.getLangue(winPacToolConfig.getLanguage()));
	}
}
