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

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.Toolkit;
import javax.swing.JSeparator;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gui.info.AboutWin;
import gui.info.InfoWin;
import gui.pac.CirculatorDistrWin;
import gui.pac.CirculatorSrcWin;
import gui.pac.CompressorWin;
import pac.Pac;
import translation.TLanguage;

public class PacToolWin extends JFrame {

	//private static final Logger logger = LogManager.getLogger(PacToolWin.class.getName());
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private PacToolVar pacToolVar;
	
	private Pac pac;
	private GuiConfig guiConfig;
	private CompressorWin compressorWin;
	private CirculatorDistrWin circulatorDistrWin;
	private CirculatorSrcWin circulatorSrcWin;
	
	private EnthalpyWin enthalpyWin;
	private EnthalpyWinConf enthalpyWinConf;
	private PressTempWin pressTempWin;

	private MeasurePointTableWin measurePointTableWin;
	private MeasureResultTableWin measureResultTableWin;

	private AboutWin aboutWin;
	private InfoWin infoWin;
	private LoggerWin loggerWin;

	private PacToolPanel contentPanel;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	/**
	 * Create the frame. Class PacToolWin inheriting of JFrame
	 */
	public PacToolWin(PacToolVar vpacToolVar) {
		setResizable(false);
		pacToolVar = vpacToolVar;
		
		pac = vpacToolVar.getPac();
		
		guiConfig = vpacToolVar.getGuiConfig();
		compressorWin = vpacToolVar.getCompressorWin();
		circulatorDistrWin =  vpacToolVar.getCirculatorDistrWin();
		circulatorSrcWin = vpacToolVar.getCirculatorSrcWin();
		enthalpyWin = vpacToolVar.getEnthalpyWin();
		enthalpyWinConf  = vpacToolVar.getEnthalpyWinConf();
		measurePointTableWin = vpacToolVar.getMeasurePointTableWin();
		measureResultTableWin = vpacToolVar.getMeasureResultTableWin();
		aboutWin = vpacToolVar.getAboutWin();
		infoWin = vpacToolVar.getDefinitionWin();
		pressTempWin = vpacToolVar.getPressTempWin();
		loggerWin = vpacToolVar.getLoggerWin();

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

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			logger.trace(e);
		}
		setTitle("PAC-Tool (" + PacToolVar.PACTool_Version + ")");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PacToolWin.class.getResource("/gui/images/PAC-Tool_32.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ===============================================================================================================
		// 													PANEL
		// ===============================================================================================================

		contentPanel = new PacToolPanel(pacToolVar);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		//setBounds(100, 10, contentPanel.getBgImgWidth()+7, contentPanel.getBgImgHeight()+49);
		setBounds(100, 10, contentPanel.getBgImgWidth()+7, contentPanel.getBgImgHeight()+49);


		// ===============================================================================================================
		// 													MENU
		// ===============================================================================================================

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/load-icon-16.png")));
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter( "Configuratin file (.cfg)", "cfg");
				chooser.setFileFilter(filter);
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				int returnVal = chooser.showOpenDialog(contentPanel);
				if(returnVal == JFileChooser.APPROVE_OPTION) {

					// Read the configuration from File
					PacToolConfig.readConfigFile(pac, guiConfig, chooser.getSelectedFile().getAbsolutePath());

					compressorWin.applyConfig();
					enthalpyWin.applyConfig();
					enthalpyWinConf.applyConfig();

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
				if(chooser.showOpenDialog(contentPanel) == JFileChooser.APPROVE_OPTION) {
					PacToolConfig.saveConfigFile(pac, guiConfig, chooser.getSelectedFile().getAbsolutePath());
				} 

			}
		});
		mntmSave.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/save-icone-16.png")));
		mnFile.add(mntmSave);

		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);

		JMenuItem mntmPrint = new JMenuItem("Print");
		mntmPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				PrinterJob job = PrinterJob.getPrinterJob();
				job.setJobName("Print Java Component");

				job.setPrintable (new Printable() {    
					@Override
					public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
						if (pageIndex > 0) {
							return(NO_SUCH_PAGE);
						} else {
							Graphics2D g2d = (Graphics2D)g;
							g2d.translate(pageFormat.getImageableX(),pageFormat.getImageableY());

							contentPanel.paint(g2d);
							return(PAGE_EXISTS); 
						}
					}

				});

				if (job.printDialog()) {
					try {
						job.print();
					} catch (PrinterException e) {
						System.err.println(e.getMessage()); 
					}
				}

			}
		});
		mntmPrint.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/imprimante-16.png")));
		mnFile.add(mntmPrint);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/sortir-session16.png")));
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmQuit);

		JMenu mnPac = new JMenu("Pac");
		menuBar.add(mnPac);

		JMenuItem mntmCompressor = new JMenuItem("Compressor");
		mntmCompressor.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/compresseur-16.png")));
		mntmCompressor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("JMenuItem: Open compressorWin");
				compressorWin.setVisible(true);
			}
		});
		mnPac.add(mntmCompressor);
		
		JMenuItem mntmCirculatorScr = new JMenuItem("Circulator Source");
		mntmCirculatorScr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("JMenuItem: Open Circulator Source");
				circulatorSrcWin.setVisible(true);
			}
		});
		mntmCirculatorScr.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/circulator_16.png")));
		mnPac.add(mntmCirculatorScr);
		
		JMenuItem mntmCirculatorDistr = new JMenuItem("Circulator Distibution");
		mntmCirculatorDistr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("JMenuItem: Open Circulator Distribution");
				circulatorDistrWin.setVisible(true);
			}
		});
		mntmCirculatorDistr.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/circulator_16.png")));
		mnPac.add(mntmCirculatorDistr);

		JMenu mnEnthalpy = new JMenu("Enthalpy");
		menuBar.add(mnEnthalpy);

		JMenuItem mntmDiagram = new JMenuItem("Diagram Enthalpy");
		mntmDiagram.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/Enthalpy-16.png")));
		mntmDiagram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("JMenuItem: Open enthalpyWin");
				enthalpyWin.setVisible(true);
			}
		});
		mnEnthalpy.add(mntmDiagram);

		JMenuItem mntmPressTemp = new JMenuItem("Pressure Temperature");
		mntmPressTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("JMenuItem: Open PressTempWin");
				pressTempWin.setVisible(true);
			}
		});
		mntmPressTemp.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/PressureTemperature-16.png")));
		mnEnthalpy.add(mntmPressTemp);

		JMenu mnAllData = new JMenu("Data");
		menuBar.add(mnAllData);

		JMenuItem mntmTableMeasures = new JMenuItem("Measures");
		mntmTableMeasures.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/table-16.png")));
		mntmTableMeasures.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("JMenuItem: Open WinMeasureTable");
				measurePointTableWin.setVisible(true);
			}
		});
		mnAllData.add(mntmTableMeasures);
		
		JMenuItem mntmResults = new JMenuItem("Results");
		mntmResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("JMenuItem: Open WinResultTable");
				measureResultTableWin.setVisible(true);			
			}
		});
		mntmResults.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/table-16.png")));
		mnAllData.add(mntmResults);

		JMenu mpreference = new JMenu("Preferences");
		menuBar.add(mpreference);

		JMenuItem mImgEnthalpyCfg = new JMenuItem("Enthalpy Configuration");
		mImgEnthalpyCfg.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/configuration-16.png")));
		mImgEnthalpyCfg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("JMenuItem: Open enthalpyWinConf");
				System.out.println("getNameRefrigerant() : " + pac.getRefrigerant());
				enthalpyWinConf.setVisible(true);
			}
		});
		mpreference.add(mImgEnthalpyCfg);

		ButtonGroup buttonGroup = new ButtonGroup();

		JMenu mlangue = new JMenu("Language");
		mlangue.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/langue-16.png")));
		mpreference.add(mlangue);

		JRadioButtonMenuItem mRationItemFrench = new JRadioButtonMenuItem("Francais");
		mRationItemFrench.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guiConfig.setLanguage(TLanguage.FRENCH);
				compressorWin.applyConfig();
				circulatorSrcWin.applyConfig();
				circulatorDistrWin.applyConfig();
			}
		});
		buttonGroup.add(mRationItemFrench);

		mlangue.add(mRationItemFrench);

		JRadioButtonMenuItem mRationItemEnglisch = new JRadioButtonMenuItem("English");
		mRationItemEnglisch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiConfig.setLanguage(TLanguage.ENGLICH);
				compressorWin.applyConfig();
				circulatorSrcWin.applyConfig();
				circulatorDistrWin.applyConfig();
			}
		});
		mRationItemEnglisch.setSelected(true);
		buttonGroup.add(mRationItemEnglisch);

		mlangue.add(mRationItemEnglisch);;


		JMenu mnGeneral = new JMenu("Geothermy");
		menuBar.add(mnGeneral);

		JMenuItem mntmDefinitio = new JMenuItem("Definitions");
		mntmDefinitio.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/aide-index-16.png")));
		mntmDefinitio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("JMenuItem: Open InfoWin");
				infoWin.setVisible(true);

			}
		});
		mnGeneral.add(mntmDefinitio);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/About16.png")));
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("JMenuItem: Open AboutWin");
				aboutWin.setVisible(true);
			}
		});
		mnHelp.add(mntmAbout);
		
		JSeparator separator_2 = new JSeparator();
		mnHelp.add(separator_2);
		
		JMenuItem mntmLog = new JMenuItem("Log");
		mntmLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logger.trace("JMenuItem: Open loggerWin");
				loggerWin.setVisible(true);
			}
		});
		mntmLog.setIcon(new ImageIcon(PacToolWin.class.getResource("/gui/images/logger-16.png")));
		mnHelp.add(mntmLog);


	}
}