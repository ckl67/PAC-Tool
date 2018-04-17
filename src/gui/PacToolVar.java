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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import gui.info.AboutWin;
import gui.info.InfoWin;
import gui.pac.CirculatorDistrWin;
import gui.pac.CirculatorSrcWin;
import gui.pac.CompressorWin;
import mpoints.EloMeasurePoint;
import mpoints.EloMeasureResult;
import mpoints.MeasurePoint;
import mpoints.MeasureResult;
import pac.Pac;
import translation.TLanguage;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.SystemColor;

public class PacToolVar {

	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	// -------------------------------------------------------
	// 						CONSTANTES (DEFINE)
	// -------------------------------------------------------
	public static final String PACTool_Version = "Version Alpha 0.5.0";
	
	// Create all instances which will be used in PacToolWin + PacToolPanel
	// AND which must be used outside of PacToolWin + PacToolPanel
	// 		By creating all the instances here, it assure that only 1 instance will be created
	//  	All data are loaded once --> no problem of missing variables 
			
	private Pac pac;
	private GuiConfig guiConfig;

	private List<MeasurePoint> lMeasurePoints;
	private MeasurePointTableWin measurePointTableWin;


	private List<MeasureResult> lMeasureResults;
	private MeasureResultTableWin measureResultTableWin;
	
	private List<EnthalpyElDraw> lEnthalpyElDraw;
	
	private EnthalpyBkgImg enthalpyBkgImg;
	
	private CompressorWin compressorWin;
	private CirculatorDistrWin circulatorDistrWin;
	private CirculatorSrcWin circulatorSrcWin;

	private EnthalpyWin enthalpyWin;
	private EnthalpyWinConf enthalpyWinConf;

	private PressTempWin pressTempWin;

	private AboutWin aboutWin;
	private InfoWin infoWin;
	private InfoWin abreviationWin;
	private LoggerWin loggerWin;
	
	private JTextField panelPacToolTextFieldCOP;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	PacToolVar(boolean defConsoleLoggerActivated) {


		// ------ Frame ------------
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();	
		JFrame frame = new JFrame();
	    
		frame.setAlwaysOnTop(true);
		frame.setUndecorated(true);
		frame.setBounds(100, 100, 362, 120);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblLoading = new JLabel("Loading......");
		lblLoading.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLoading.setForeground(Color.WHITE);
		lblLoading.setBounds(26, 11, 292, 22);
		panel.add(lblLoading);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setForeground(new Color(152, 251, 152));
		progressBar.setBounds(124, 44, 194, 22);
		panel.add(progressBar);

		JLabel lblImage = new JLabel("New label");
		lblImage.setIcon(new ImageIcon(PacToolVar.class.getResource("/gui/images/PAC-Tool_64.png")));
		lblImage.setBounds(26, 44, 63, 63);
		panel.add(lblImage);

		JLabel lblPacVersion = new JLabel("PAC-Tool (" + PACTool_Version + ")");
		lblPacVersion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPacVersion.setForeground(Color.WHITE);
		lblPacVersion.setBounds(146, 93, 194, 14);
		panel.add(lblPacVersion);

	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
		frame.setVisible(true);
		
		// -------------------------
		int i = 1;
		int iterations = 17;
		int percent = 0;
		
		logger.info("Initialize All Variables");

		// 1
		pac = new Pac();								// Contains Pac Features
		pac.getRefrigerant().loadNewRefrigerant("./ressources/R22/Saturation Table R22.txt");
		lblLoading.setText("Loading...... Pac Features");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		// 2
		guiConfig = new GuiConfig();		// GUI Configuration
		guiConfig.setLanguage(TLanguage.FRENCH);
		lblLoading.setText("Loading...... GUI Configuration");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		// 3
		lMeasurePoints = new ArrayList<MeasurePoint>(); 
		for (EloMeasurePoint p : EloMeasurePoint.values())
			lMeasurePoints.add(new MeasurePoint(p));
		lblLoading.setText("Loading...... Measure Point Elements List");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		// 4
		measurePointTableWin = new MeasurePointTableWin(lMeasurePoints, guiConfig);
		lblLoading.setText("Loading...... Win. Measure Table");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		// 5
		lMeasureResults = new ArrayList<MeasureResult>(); 
		for (EloMeasureResult p : EloMeasureResult.values()) 
			lMeasureResults.add(new MeasureResult(p));
		lblLoading.setText("Loading...... Measure Result Elements List");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		// 6
		measureResultTableWin = new MeasureResultTableWin(lMeasureResults, guiConfig); 
		lblLoading.setText("Loading...... Win. Result Table");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);
		
		// 7
		lEnthalpyElDraw = new ArrayList<EnthalpyElDraw>(); 
		for (EloEnthalpyElDraw p : EloEnthalpyElDraw.values()) 
			lEnthalpyElDraw.add(new EnthalpyElDraw(p));
		lblLoading.setText("Loading...... Draw Elements List");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		// 8
		// Create Background Image
		// EnthalpyBkgImg enthalpyBkgImg = new EnthalpyBkgImg("./ressources/R407/R407C/R407C couleur A4.png");
		enthalpyBkgImg = new EnthalpyBkgImg("./ressources/R22/R22 couleur A4.png");
		lblLoading.setText("Loading...... Background Image");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);
		
		// 9
		pressTempWin = new PressTempWin(pac.getRefrigerant());
		lblLoading.setText("Loading...... Win. Temperature/Pressure");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		// 10
		compressorWin = new CompressorWin(pac, guiConfig);
		lblLoading.setText("Loading...... Win. Compressor");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		// 11
		circulatorSrcWin = new CirculatorSrcWin(pac, guiConfig);
		circulatorDistrWin = new CirculatorDistrWin(pac,guiConfig);
		lblLoading.setText("Loading...... Win. Circulator");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);
		
		panelPacToolTextFieldCOP = new JTextField();
		
		// 12
		enthalpyWin = new EnthalpyWin(
				guiConfig,
				pac, 
				lMeasurePoints, 
				lMeasureResults,
				enthalpyBkgImg,
				lEnthalpyElDraw,
				measurePointTableWin,
				measureResultTableWin,
				pressTempWin);	
		lblLoading.setText("Loading...... Win. Enthalpy");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		// 13
		enthalpyWinConf = new EnthalpyWinConf(enthalpyWin);
		lblLoading.setText("Loading...... Win. Configuration Enthalpy");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		// 14
		aboutWin = new AboutWin();
		lblLoading.setText("Loading...... Win. About");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		// 15
		infoWin = new InfoWin("Définition","/gui/info/Definitions.html");
		lblLoading.setText("Loading...... Win. Definition");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);
		
		// 16
		abreviationWin = new InfoWin("Abréviation","/gui/info/Abreviation.html");
		lblLoading.setText("Loading...... Win. Definition");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);
  
		// 17
		loggerWin = new LoggerWin(defConsoleLoggerActivated);
		loggerWin.readConfigSetRadioButton();
		lblLoading.setText("Loading...... Win. Logger");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);
		
		frame.setVisible(false);

	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public Pac getPac() {
		return pac;
	}

	public GuiConfig getGuiConfig() {
		return guiConfig;
	}

	public List<MeasurePoint> getlMeasurePoints() {
		return lMeasurePoints;
	}

	public MeasurePointTableWin getMeasurePointTableWin() {
		return measurePointTableWin;
	}

	public List<MeasureResult> getlMeasureResults() {
		return lMeasureResults;
	}

	public MeasureResultTableWin getMeasureResultTableWin() {
		return measureResultTableWin;
	}

	public List<EnthalpyElDraw> getlEnthalpyElDraw() {
		return lEnthalpyElDraw;
	}

	public EnthalpyBkgImg getEnthalpyBkgImg() {
		return enthalpyBkgImg;
	}

	public CompressorWin getCompressorWin() {
		return compressorWin;
	}

	public CirculatorDistrWin getCirculatorDistrWin() {
		return circulatorDistrWin;
	}

	public CirculatorSrcWin getCirculatorSrcWin() {
		return circulatorSrcWin;
	}

	public EnthalpyWin getEnthalpyWin() {
		return enthalpyWin;
	}

	public EnthalpyWinConf getEnthalpyWinConf() {
		return enthalpyWinConf;
	}

	public PressTempWin getPressTempWin() {
		return pressTempWin;
	}

	public AboutWin getAboutWin() {
		return aboutWin;
	}

	public InfoWin getDefinitionWin() {
		return infoWin;
	}

	public InfoWin getAbreviationWin() {
		return abreviationWin;
	}

	public LoggerWin getLoggerWin() {
		return loggerWin;
	}

	public JTextField getPanelPacToolTextFieldCOP() {
		return panelPacToolTextFieldCOP;
	}



}
