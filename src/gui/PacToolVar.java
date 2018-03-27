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
import gui.helpaboutdef.AboutWin;
import gui.helpaboutdef.DefinitionWin;
import gui.pac.CirculatorDistrWin;
import gui.pac.CirculatorSrcWin;
import gui.pac.CompressorWin;
import log4j.Log4j2Config;
import mpoints.EloMeasurePoint;
import mpoints.EloMeasureResult;
import mpoints.MeasurePoint;
import mpoints.MeasureResult;
import pac.Pac;
import translation.TLanguage;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class PacToolVar {

	// -------------------------------------------------------
	// 						CONSTANTES (DEFINE)
	// -------------------------------------------------------
	public static final String PACTool_Version = "Version Alpha 0.4.0";
	
	// Create all instances which will be used in WinPacTool + PanelPacTool
	// AND which must be used outside of WinPacTool + PanelPacTool
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
	private DefinitionWin definitionWin;
	private DefinitionWin abreviationWin;
	private LoggerWin loggerWin;
	
	private JTextField panelPacToolTextFieldCOP;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	PacToolVar(Log4j2Config log4j2Config) {

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
		int iterations = 18;
		int percent = 0;
		
		pac = new Pac();								// Contains Pac Features
		pac.getRefrigerant().loadNewRefrigerant("./ressources/R22/Saturation Table R22.txt");
		lblLoading.setText("Loading...... Pac Features");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		guiConfig = new GuiConfig();		// GUI Configuration
		guiConfig.setLanguage(TLanguage.FRENCH);
		lblLoading.setText("Loading...... GUI Configuration");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		lMeasurePoints = new ArrayList<MeasurePoint>(); 
		for (EloMeasurePoint p : EloMeasurePoint.values())
			lMeasurePoints.add(new MeasurePoint(p));
		lblLoading.setText("Loading...... Measure Point Elements List");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		measurePointTableWin = new MeasurePointTableWin(lMeasurePoints, guiConfig);
		lblLoading.setText("Loading...... Win. Measure Table");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		lMeasureResults = new ArrayList<MeasureResult>(); 
		for (EloMeasureResult p : EloMeasureResult.values()) {
			lMeasureResults.add(new MeasureResult(p));
		}
		lblLoading.setText("Loading...... Measure Result Elements List");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		lEnthalpyElDraw = new ArrayList<EnthalpyElDraw>(); 
		for (EloEnthalpyElDraw p : EloEnthalpyElDraw.values()) {
			lEnthalpyElDraw.add(new EnthalpyElDraw(p));
		}
		lblLoading.setText("Loading...... Draw Elements List");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		// Create Background Image
		// EnthalpyBkgImg enthalpyBkgImg = new EnthalpyBkgImg("./ressources/R407/R407C/R407C couleur A4.png");
		enthalpyBkgImg = new EnthalpyBkgImg("./ressources/R22/R22 couleur A4.png");
		lblLoading.setText("Loading...... Background Image");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);
		
		pressTempWin = new PressTempWin(pac.getRefrigerant());
		lblLoading.setText("Loading...... Win. Temperature/Pressure");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		compressorWin = new CompressorWin(pac, guiConfig);
		lblLoading.setText("Loading...... Win. Compressor");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		circulatorSrcWin = new CirculatorSrcWin(pac, guiConfig);
		circulatorDistrWin = new CirculatorDistrWin(pac,guiConfig);
		lblLoading.setText("Loading...... Win. Circulator");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);
		
		panelPacToolTextFieldCOP = new JTextField();
		
		enthalpyWin = new EnthalpyWin(pac, enthalpy, measureResultTableWin, resultTable, lMeasurePoints, eDrawL,pressTempWin, panelPacToolTextFieldCOP);
		lblLoading.setText("Loading...... Win. Enthalpy");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		enthalpyWinConf = new EnthalpyWinConf(enthalpyWin);
		lblLoading.setText("Loading...... Win. Configuration Enthalpy");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		aboutWin = new AboutWin();
		lblLoading.setText("Loading...... Win. About");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		definitionWin = new DefinitionWin("Définition","/gui/helpaboutdef/Definitions.html");
		lblLoading.setText("Loading...... Win. Definition");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);
		
		abreviationWin = new DefinitionWin("Abréviation","/gui/helpaboutdef/Abreviation.html");
		lblLoading.setText("Loading...... Win. Definition");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);
  
		loggerWin = new LoggerWin(log4j2Config);
		lblLoading.setText("Loading...... Win. Logger");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);
		
		frame.setVisible(false);

	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------


}
