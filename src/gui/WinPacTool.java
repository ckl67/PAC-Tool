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
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.Toolkit;
import javax.swing.JSeparator;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import computation.Misc;
import enthalpy.Enthalpy;
import pac.Pac;


public class WinPacTool extends JFrame {

	private static final Logger logger = LogManager.getLogger(WinPacTool.class.getName());
	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private Pac pac;
	private Enthalpy enthalpy;
	private WinPacToolConfig winPacToolConfig;
	
	// -------------------------------------------------------
	private JDesktopPane desktopPaneMain;
	private WinCompressor winCompressor;
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	/**
	 * Create the frame. Class WinPacTool inheriting of JFrame
	 */
	public WinPacTool(Pac vpac, Enthalpy venthalpy, WinPacToolConfig vwinPacToolConfig) {

		pac = vpac;
		enthalpy = venthalpy;
		winPacToolConfig = vwinPacToolConfig;

		// Theme Window
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			logger.error(e);
		}

		setTitle("PAC-Tool (" + Misc.PACTool_Version + ")");
		setIconImage(Toolkit.getDefaultToolkit().getImage(WinPacTool.class.getResource("/gui/images/PAC-Tool_32.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(false);

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
				int returnVal = chooser.showOpenDialog(desktopPaneMain);
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
				if(chooser.showOpenDialog(desktopPaneMain) == JFileChooser.APPROVE_OPTION) {
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
				WinCompressor winCompressor = new WinCompressor(pac,winPacToolConfig);
				winCompressor.setVisible(true);
				desktopPaneMain.add(winCompressor);
			}
		});
		mnPac.add(mntmCompressor);
		
		JMenu mnEnthalpy = new JMenu("Enthalpy");
		menuBar.add(mnEnthalpy);
		
		JMenuItem mntmDiagram = new JMenuItem("Diagram");
		mntmDiagram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnEnthalpy.add(mntmDiagram);
		
		JMenu mnMeasures = new JMenu("Measures");
		menuBar.add(mnMeasures);

		JMenu mnDisplay = new JMenu("Preferences");
		menuBar.add(mnDisplay);

		JMenu mnNewDisplay = new JMenu("Screen Size");
		mnDisplay.add(mnNewDisplay);

		JMenuItem mntmDisplaySizeSwap = new JMenuItem("Swap");
		mntmDisplaySizeSwap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (isUndecorated()) {
					//this will dispose your JFrame
					dispose();
					//here to set it with no Title bar
					setUndecorated(false);
					pack();
					setLocationRelativeTo(null);
					//as you dispose your JFrame, you have to remake it Visible..
					setVisible(true);
				} else {
					dispose();
					setUndecorated(true);
					setExtendedState(MAXIMIZED_BOTH);
					setVisible(true);
				}

			}
		});
		mnNewDisplay.add(mntmDisplaySizeSwap);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinAbout winAbout = new WinAbout();
				winAbout.setVisible(true);
				desktopPaneMain.add(winAbout);
			}
		});
		mnHelp.add(mntmAbout);

		// ===============================================================================================================
		// 													PANEL
		// ===============================================================================================================

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(1, 1, 1, 1));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		desktopPaneMain = new JDesktopPane() {
			private static final long serialVersionUID = 1L;
			private Image image;
			    {
			        try {
			        	
			            URL url = this.getClass().getResource("/gui/images/background-image.jpg");
			            image = ImageIO.read(url);
			        } catch (IOException e) {
			            logger.error(e);
			        }
			    }

			    @Override
			    protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			    }
		};
		desktopPaneMain.setBackground(SystemColor.inactiveCaption);
		contentPane.add(desktopPaneMain, BorderLayout.CENTER);
	}
}