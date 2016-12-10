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

	private ConfEnthalpy confEnthalpy;
	private JFrame frmPactoolConfigurationDiargramme;
	private JTextField textFieldEnthalpyFilePath;
	private JTextField textFieldHOrigine;
	private JTextField textFieldHFinal;
	private JTextField textFieldPOrigine;
	private JTextField textFieldPFinal;

	// ========================================================================================
	public void WinConfEnthalpyVisible() {
		frmPactoolConfigurationDiargramme.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public WinConfEnthalpy(ConfEnthalpy vconfEnthalpy) {
		confEnthalpy = vconfEnthalpy;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPactoolConfigurationDiargramme = new JFrame();
		frmPactoolConfigurationDiargramme.setTitle("Pac-Tool Configuration Diargramme Enthalpique");
		frmPactoolConfigurationDiargramme.setResizable(false);
		frmPactoolConfigurationDiargramme.setIconImage(Toolkit.getDefaultToolkit().getImage(WinConfEnthalpy.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frmPactoolConfigurationDiargramme.setBounds(100, 100, 550, 331);
		frmPactoolConfigurationDiargramme.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		frmPactoolConfigurationDiargramme.getContentPane().add(panel, BorderLayout.CENTER);

		JButton btnEnthalpyFileChoice = new JButton("Fichier Image");
		btnEnthalpyFileChoice.setBounds(427, 24, 97, 23);
		btnEnthalpyFileChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "Enthalpie files", "png");
				chooser.setFileFilter(filter);
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				int returnVal = chooser.showOpenDialog(frmPactoolConfigurationDiargramme);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					//System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
					String imagepath=chooser.getSelectedFile().getAbsolutePath();
					textFieldEnthalpyFilePath.setText(imagepath);
				}
			}
		});
		panel.setLayout(null);
		panel.add(btnEnthalpyFileChoice);

		JButton btnOK = new JButton("OK");
		btnOK.setBounds(477, 262, 47, 23);
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println(textFieldEnthalpyFilePath.getText());
				confEnthalpy.setEnthalpyImageFile(textFieldEnthalpyFilePath.getText());		
				frmPactoolConfigurationDiargramme.dispose();
			}
		});

		JLabel lblEnthalpyFile = new JLabel("Fichier Enthalpie");
		lblEnthalpyFile.setBounds(10, 28, 78, 14);
		panel.add(lblEnthalpyFile);

		textFieldEnthalpyFilePath = new JTextField();
		textFieldEnthalpyFilePath.setBounds(104, 25, 313, 20);
		textFieldEnthalpyFilePath.setText("D:/Users/kluges1/workspace/pac-tool/src/pacp/images/diagrammes enthalpie/R22.png");
		textFieldEnthalpyFilePath.setColumns(10);
		panel.add(textFieldEnthalpyFilePath);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(402, 262, 65, 23);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmPactoolConfigurationDiargramme.dispose();
			}
		});
		panel.add(btnCancel);
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
		textFieldHOrigine.setText(String.valueOf(confEnthalpy.getiHOrigine()));
		textFieldHOrigine.setToolTipText("");
		textFieldHOrigine.setBounds(104, 23, 86, 20);
		panel_1.add(textFieldHOrigine);
		textFieldHOrigine.setColumns(10);

		JButton btnHOrigine = new JButton("Centrer");
		btnHOrigine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confEnthalpy.setlocateOrigineH(true);
				confEnthalpy.setiHOrigine(Integer.valueOf(textFieldHOrigine.getText()));
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
		textFieldHFinal.setText(String.valueOf(confEnthalpy.getiHFinal()));
		textFieldHFinal.setBounds(368, 23, 86, 20);
		panel_1.add(textFieldHFinal);
		textFieldHFinal.setColumns(10);

		JButton btnHFinal = new JButton("Centrer");
		btnHFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confEnthalpy.setlocateFinalH(true);
				confEnthalpy.setiHFinal(Integer.valueOf(textFieldHFinal.getText()));
			}
		});
		btnHFinal.setBounds(365, 53, 89, 23);
		panel_1.add(btnHFinal);

		JLabel lblKjkg = new JLabel("kJ/kg");
		lblKjkg.setBounds(199, 26, 46, 14);
		panel_1.add(lblKjkg);

		JLabel label = new JLabel("kJ/kg");
		label.setBounds(469, 26, 35, 14);
		panel_1.add(label);

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
		textFieldPOrigine.setText(String.valueOf(confEnthalpy.getiPOrigine()));
		textFieldPOrigine.setBounds(104, 21, 86, 20);
		panel_2.add(textFieldPOrigine);
		textFieldPOrigine.setColumns(10);

		JButton btnPOrigine = new JButton("Centrer");
		btnPOrigine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confEnthalpy.setlocateOrigineP(true);
				confEnthalpy.setiPOrigine(Integer.valueOf(textFieldPOrigine.getText()));									
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
		textFieldPFinal.setText(String.valueOf(confEnthalpy.getiPFinal()));
		textFieldPFinal.setBounds(370, 19, 86, 20);
		panel_2.add(textFieldPFinal);
		textFieldPFinal.setColumns(10);

		JButton btnPFinal = new JButton("Centrer");
		btnPFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confEnthalpy.setlocateFinalP(true);
				confEnthalpy.setiPFinal(Integer.valueOf(textFieldPFinal.getText()));
			}
		});
		btnPFinal.setBounds(367, 49, 89, 23);
		panel_2.add(btnPFinal);

		JLabel lblBar = new JLabel("bar");
		lblBar.setBounds(197, 24, 46, 14);
		panel_2.add(lblBar);

		JLabel label_1 = new JLabel("bar");
		label_1.setBounds(466, 24, 26, 14);
		panel_2.add(label_1);
	}
}
