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
package pacp;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;

public class WinConfEnthalpy {

	private Enthalpy enthalpy;

	private JFrame frmPactoolConfigurationDiagramme;
	private JTextField textFieldEnthalpyFilePath;
	private JTextField textFieldHOrigine;
	private JTextField textFieldHFinal;
	private JTextField textFieldPOrigine;
	private JTextField textFieldPFinal;
	private JTextField txtFieldTemperaturePressionFile;

	// ========================================================================================
	public void WinConfEnthalpyVisible() {
		frmPactoolConfigurationDiagramme.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public WinConfEnthalpy(Enthalpy vconfEnthalpy) {
		enthalpy = vconfEnthalpy;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPactoolConfigurationDiagramme = new JFrame();
		frmPactoolConfigurationDiagramme.setTitle("Pac-Tool Configuration Diargramme Enthalpique");
		frmPactoolConfigurationDiagramme.setResizable(false);
		frmPactoolConfigurationDiagramme.setIconImage(Toolkit.getDefaultToolkit().getImage(WinConfEnthalpy.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frmPactoolConfigurationDiagramme.setBounds(100, 100, 550, 465);
		frmPactoolConfigurationDiagramme.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		frmPactoolConfigurationDiagramme.getContentPane().add(panel, BorderLayout.CENTER);

		JButton btnEnthalpyFileChoice = new JButton("Fichier Image");
		btnEnthalpyFileChoice.setBounds(427, 24, 97, 23);
		btnEnthalpyFileChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "Enthalpie files", "png");
				chooser.setFileFilter(filter);
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				int returnVal = chooser.showOpenDialog(frmPactoolConfigurationDiagramme);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					//System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
					String imagepath=chooser.getSelectedFile().getAbsolutePath();
					textFieldEnthalpyFilePath.setText(imagepath);
					enthalpy.setEnthalpyImageFile(textFieldEnthalpyFilePath.getText());	
					try {
						if (WinEnthalpy.panelEnthalpyDrawArea.isVisible() ) {
							WinEnthalpy.panelEnthalpyDrawArea.openEnthalpyImageFile();
							WinEnthalpy.panelEnthalpyDrawArea.clean();
						}
					} catch (NullPointerException e) {

					}
				}
			}
		});
		panel.setLayout(null);
		panel.add(btnEnthalpyFileChoice);

		JButton btnOK = new JButton("Fermer");
		btnOK.setBounds(467, 403, 67, 23);
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println(textFieldEnthalpyFilePath.getText());
				//confEnthalpy.setEnthalpyImageFile(textFieldEnthalpyFilePath.getText());		
				frmPactoolConfigurationDiagramme.dispose();
			}
		});

		JLabel lblEnthalpyFile = new JLabel("Fichier Enthalpie");
		lblEnthalpyFile.setBounds(10, 28, 78, 14);
		panel.add(lblEnthalpyFile);

		textFieldEnthalpyFilePath = new JTextField();
		textFieldEnthalpyFilePath.setBounds(104, 25, 313, 20);
		textFieldEnthalpyFilePath.setText("D:/Users/kluges1/workspace/pac-tool/ressources/R22.png");
		textFieldEnthalpyFilePath.setColumns(10);
		panel.add(textFieldEnthalpyFilePath);
		panel.add(btnOK);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Enthalpie", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 58, 514, 91);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblHOrigine = new JLabel("New label");
		lblHOrigine.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblHOrigine.setBackground(Color.WHITE);
		lblHOrigine.setIcon(new ImageIcon(WinConfEnthalpy.class.getResource("/pacp/images/HOrigLocation.png")));
		lblHOrigine.setBounds(30, 22, 64, 50);
		panel_1.add(lblHOrigine);

		textFieldHOrigine = new JTextField();
		textFieldHOrigine.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldHOrigine.setText(String.valueOf(enthalpy.getiHOrigine()));
		textFieldHOrigine.setToolTipText("");
		textFieldHOrigine.setBounds(104, 23, 86, 20);
		panel_1.add(textFieldHOrigine);
		textFieldHOrigine.setColumns(10);

		JButton btnHOrigine = new JButton("Centrer");
		btnHOrigine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpy.setlocateOrigineH(true);
				enthalpy.setiHOrigine(Integer.valueOf(textFieldHOrigine.getText()));
			}
		});
		btnHOrigine.setBounds(104, 53, 89, 23);
		panel_1.add(btnHOrigine);

		JLabel lblHFinal = new JLabel("New label");
		lblHFinal.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblHFinal.setIcon(new ImageIcon(WinConfEnthalpy.class.getResource("/pacp/images/HFinalLocation.png")));
		lblHFinal.setBounds(276, 22, 64, 53);
		panel_1.add(lblHFinal);

		textFieldHFinal = new JTextField();
		textFieldHFinal.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldHFinal.setText(String.valueOf(enthalpy.getiHFinal()));
		textFieldHFinal.setBounds(368, 23, 86, 20);
		panel_1.add(textFieldHFinal);
		textFieldHFinal.setColumns(10);

		JButton btnHFinal = new JButton("Centrer");
		btnHFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enthalpy.setlocateFinalH(true);
				enthalpy.setiHFinal(Integer.valueOf(textFieldHFinal.getText()));
			}
		});
		btnHFinal.setBounds(365, 53, 89, 23);
		panel_1.add(btnHFinal);

		JLabel lblKjkg0 = new JLabel("kJ/kg");
		lblKjkg0.setBounds(199, 26, 46, 14);
		panel_1.add(lblKjkg0);

		JLabel lblKjkg01 = new JLabel("kJ/kg");
		lblKjkg01.setBounds(469, 26, 35, 14);
		panel_1.add(lblKjkg01);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Pression", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 160, 514, 91);
		panel.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblPOrigine = new JLabel("New label");
		lblPOrigine.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPOrigine.setBackground(Color.WHITE);
		lblPOrigine.setIcon(new ImageIcon(WinConfEnthalpy.class.getResource("/pacp/images/POrigLocation.png")));
		lblPOrigine.setBounds(30, 22, 64, 50);
		panel_2.add(lblPOrigine);

		textFieldPOrigine = new JTextField();
		textFieldPOrigine.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldPOrigine.setText(String.valueOf(enthalpy.getiPOrigine()));
		textFieldPOrigine.setBounds(104, 21, 86, 20);
		panel_2.add(textFieldPOrigine);
		textFieldPOrigine.setColumns(10);

		JButton btnPOrigine = new JButton("Centrer");
		btnPOrigine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enthalpy.setlocateOrigineP(true);
				enthalpy.setiPOrigine(Integer.valueOf(textFieldPOrigine.getText()));									
			}
		});
		btnPOrigine.setBounds(104, 52, 89, 23);
		panel_2.add(btnPOrigine);

		JLabel lblPFinal = new JLabel("New label");
		lblPFinal.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblPFinal.setIcon(new ImageIcon(WinConfEnthalpy.class.getResource("/pacp/images/PFinalLocation.png")));
		lblPFinal.setBounds(280, 19, 64, 50);
		panel_2.add(lblPFinal);

		textFieldPFinal = new JTextField();
		textFieldPFinal.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldPFinal.setText(String.valueOf(enthalpy.getiPFinal()));
		textFieldPFinal.setBounds(370, 19, 86, 20);
		panel_2.add(textFieldPFinal);
		textFieldPFinal.setColumns(10);

		JButton btnPFinal = new JButton("Centrer");
		btnPFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enthalpy.setlocateFinalP(true);
				enthalpy.setiPFinal(Integer.valueOf(textFieldPFinal.getText()));
			}
		});
		btnPFinal.setBounds(367, 49, 89, 23);
		panel_2.add(btnPFinal);

		JLabel lblBar0 = new JLabel("bar");
		lblBar0.setBounds(197, 24, 46, 14);
		panel_2.add(lblBar0);

		JLabel lblBar01 = new JLabel("bar");
		lblBar01.setBounds(466, 24, 26, 14);
		panel_2.add(lblBar01);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Relation Temp\u00E9rature / Pression", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(10, 262, 514, 91);
		panel.add(panel_3);

		JLabel lblTempPress = new JLabel("New label");
		lblTempPress.setIcon(new ImageIcon(WinConfEnthalpy.class.getResource("/pacp/images/PressionTemperature.png")));
		lblTempPress.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblTempPress.setBackground(Color.WHITE);
		lblTempPress.setBounds(10, 20, 106, 50);
		panel_3.add(lblTempPress);

		txtFieldTemperaturePressionFile = new JTextField();
		txtFieldTemperaturePressionFile.setText("D:\\Users\\kluges1\\workspace\\pac-tool\\ressources\\P2T_R22.txt");
		txtFieldTemperaturePressionFile.setHorizontalAlignment(SwingConstants.RIGHT);
		txtFieldTemperaturePressionFile.setColumns(10);
		txtFieldTemperaturePressionFile.setBounds(126, 35, 273, 20);
		panel_3.add(txtFieldTemperaturePressionFile);

		JButton buttonLoadTemPressFile = new JButton("Fichier T/P");
		buttonLoadTemPressFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "Temperature-Pression files", "txt");
				chooser.setFileFilter(filter);
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				int returnVal = chooser.showOpenDialog(frmPactoolConfigurationDiagramme);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					//System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
					String imagepath=chooser.getSelectedFile().getAbsolutePath();
					txtFieldTemperaturePressionFile.setText(imagepath);
					enthalpy.setTemperaturePressureFile(txtFieldTemperaturePressionFile.getText());	

				}
			}
		});
		buttonLoadTemPressFile.setBounds(409, 34, 83, 23);
		panel_3.add(buttonLoadTemPressFile);
	}
}
