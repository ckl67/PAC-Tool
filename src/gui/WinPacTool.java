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

package gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.Toolkit;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import computation.Misc;
import enthalpy.Enthalpy;
import pac.Pac;
import java.awt.SystemColor;


public class WinPacTool extends JFrame {

	private static final Logger logger = LogManager.getLogger(WinPacTool.class.getName());
	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private PacToolVar pacToolVar;
	private Pac pac;
	private Enthalpy enthalpy;
	private WinPacToolConfig winPacToolConfig;
	private WinCompressor winCompressor;
	private WinEnthalpy winEnthalpy;
	private JPanel contentPane;
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	/**
	 * Create the frame. Class WinPacTool inheriting of JFrame
	 */
	public WinPacTool(PacToolVar vpacToolVar) {
		setResizable(false);
		pacToolVar = vpacToolVar;
		pac = pacToolVar.getPac();
		enthalpy = pacToolVar.getEnthalpy();
		winPacToolConfig = pacToolVar.getWinPacToolConfig();

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		setTitle("PAC-Tool (" + Misc.PACTool_Version + ")");
		setIconImage(Toolkit.getDefaultToolkit().getImage(WinPacTool.class.getResource("/gui/images/PAC-Tool_32.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 1, 450, 48);

		// Create Window
		initialize();
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------


	// -------------------------------------------------------
	// 				 GENERATED BY WIN BUILDER
	// -------------------------------------------------------
	private void  initialize() {

		// ===============================================================================================================
		// 													MENU
		// ===============================================================================================================

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.setIcon(new ImageIcon(WinPacTool.class.getResource("/gui/images/load-icon-16.png")));
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "Configuratin file (.cfg)", "cfg");
				chooser.setFileFilter(filter);
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				int returnVal = chooser.showOpenDialog(contentPane);
				if(returnVal == JFileChooser.APPROVE_OPTION) {

					// Read the configuration from File
					PacToolConfig.readConfigFile(pac, enthalpy, winPacToolConfig, chooser.getSelectedFile().getAbsolutePath());
					
					winCompressor.applyConfig(winPacToolConfig);

				}
			}
		});
		mnFile.add(mntmLoad);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "Configuratin file (.cfg)", "cfg");
				chooser.setApproveButtonText("Sauvegarder");
				chooser.setFileFilter(filter);
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				if(chooser.showOpenDialog(contentPane) == JFileChooser.APPROVE_OPTION) {
					PacToolConfig.saveConfigFile(pac, enthalpy, winPacToolConfig, chooser.getSelectedFile().getAbsolutePath());
				} 
				
			}
		});
		mntmSave.setIcon(new ImageIcon(WinPacTool.class.getResource("/gui/images/save-icone-16.png")));
		mnFile.add(mntmSave);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.setIcon(new ImageIcon(WinPacTool.class.getResource("/gui/images/sortir-session16.png")));
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmQuit);

		JMenu mnPac = new JMenu("Pac");
		menuBar.add(mnPac);

		JMenuItem mntmCompressor = new JMenuItem("Compressor");
		mntmCompressor.setIcon(new ImageIcon(WinPacTool.class.getResource("/gui/images/compresseur-16.png")));
		mntmCompressor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("Open winCompressor");
				winCompressor = new WinCompressor(pacToolVar);
				winCompressor.setVisible(true);
			}
		});
		mnPac.add(mntmCompressor);
		
		JMenu mnEnthalpy = new JMenu("Enthalpy");
		menuBar.add(mnEnthalpy);
		
		JMenuItem mntmDiagram = new JMenuItem("Diagram");
		mntmDiagram.setIcon(new ImageIcon(WinPacTool.class.getResource("/gui/images/enthalpie-16.jpg")));
		mntmDiagram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("Open winEnthalpy");
				winEnthalpy = new WinEnthalpy(pacToolVar);
				winEnthalpy.setVisible(true);
			}
		});
		mnEnthalpy.add(mntmDiagram);
		
		JMenuItem mntmPressTemp = new JMenuItem("Pressure Temperature");
		mntmPressTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Open WinPressTemp");
				WinPressTemp window = new WinPressTemp(enthalpy);
				window.setVisible(true);
			}
		});
		mntmPressTemp.setIcon(new ImageIcon(WinPacTool.class.getResource("/gui/images/PressionTemperature-16.png")));
		mnEnthalpy.add(mntmPressTemp);
		
		JMenu mnMeasures = new JMenu("Measures");
		menuBar.add(mnMeasures);
		
		JMenuItem mntmTable = new JMenuItem("Table");
		mntmTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("Open WinPressTemp");
				WinMeasureTable winMeasureTable = new WinMeasureTable();
				winMeasureTable.setVisible(true);
			}
		});
		mnMeasures.add(mntmTable);

		JMenu mnDisplay = new JMenu("Preferences");
		menuBar.add(mnDisplay);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("Open WinAbout");
				WinAbout winAbout = new WinAbout();
				winAbout.setVisible(true);
			}
		});
		mnHelp.add(mntmAbout);

		// ===============================================================================================================
		// 													PANEL
		// ===============================================================================================================

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(1, 1, 1, 1));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		contentPane.add(panel, BorderLayout.CENTER);
	}
}
