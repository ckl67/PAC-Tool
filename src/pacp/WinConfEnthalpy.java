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
	private JFrame frame;
	private JTextField textFieldEnthalpyFilePath;
	private JTextField textFieldHOrigine;
	private JTextField textFieldHFinal;

	// ========================================================================================
	public void WinConfEnthalpyVisible() {
		frame.setVisible(true);
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
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WinConfEnthalpy.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frame.setBounds(100, 100, 550, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
						
								JButton btnEnthalpyFileChoice = new JButton("Fichier Image");
								btnEnthalpyFileChoice.setBounds(427, 24, 97, 23);
								btnEnthalpyFileChoice.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {

										JFileChooser chooser = new JFileChooser();
										FileNameExtensionFilter filter = new FileNameExtensionFilter( "Enthalpie files", "png");
										chooser.setFileFilter(filter);
										File workingDirectory = new File(System.getProperty("user.dir"));
										chooser.setCurrentDirectory(workingDirectory);
										int returnVal = chooser.showOpenDialog(frame);
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
						btnOK.setBounds(477, 228, 47, 23);
						btnOK.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								System.out.println(textFieldEnthalpyFilePath.getText());
								confEnthalpy.setEnthalpyImageFile(textFieldEnthalpyFilePath.getText());		
								frame.dispose();
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
						btnCancel.setBounds(402, 228, 65, 23);
						btnCancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								frame.dispose();
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
								lblHOrigine.setBounds(30, 23, 64, 53);
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
								lblHFinal.setBounds(309, 23, 64, 53);
								panel_1.add(lblHFinal);
								
								textFieldHFinal = new JTextField();
								textFieldHFinal.setHorizontalAlignment(SwingConstants.RIGHT);
								textFieldHFinal.setText(String.valueOf(confEnthalpy.getiHFinal()));
								textFieldHFinal.setBounds(402, 23, 86, 20);
								panel_1.add(textFieldHFinal);
								textFieldHFinal.setColumns(10);
								
								JButton btnHFinal = new JButton("Centrer");
								btnHFinal.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										confEnthalpy.setlocateFinalH(true);
										confEnthalpy.setiHFinal(Integer.valueOf(textFieldHFinal.getText()));
									}
								});
								btnHFinal.setBounds(399, 53, 89, 23);
								panel_1.add(btnHFinal);
	}
}
