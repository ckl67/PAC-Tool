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

import enthalpy.Enthalpy;
import gui.helpaboutdef.AboutWin;
import gui.helpaboutdef.DefinitionWin;
import gui.pac.CirculatorDistrWin;
import gui.pac.CirculatorSrcWin;
import gui.pac.CompressorWin;
import log4j.Log4j2Config;
import misc.MeasureTable;
import misc.ResultTable;
import mpoints.EloMeasurePoint;
import mpoints.MeasurePoint;
import pac.Pac;
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

	private List<MeasurePoint> measurePointL;
	private List<ElDraw> eDrawL;

	private GuiConfig guiConfig;
	private CompressorWin compressorWin;
	
	private CirculatorDistrWin circulatorDistrWin;
	private CirculatorSrcWin circulatorSrcWin;

	private EnthalpyWin enthalpyWin;
	private WinConfEnthalpy winConfEnthalpy;

	private WinPressTemp winPressTemp;

	private MeasureTable measureTable;
	private MeasurePointTableWin measurePointTableWin;

	private ResultTable resultTable;
	private MeasureResultTableWin measureResultTableWin;

	private AboutWin aboutWin;
	private DefinitionWin definitionWin;
	private WinLogger winLogger;
	
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
		lblLoading.setText("Loading...... Pac Features");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		enthalpy = new Enthalpy();						// Enthalpy Features
		lblLoading.setText("Loading...... Enthalpy Features");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		guiConfig = new GuiConfig();		// GUI Configuration
		lblLoading.setText("Loading...... GUI Configuration");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		eDrawL = new ArrayList<ElDraw>();				// Draw Elements
		lblLoading.setText("Loading...... Draw Elements List");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		
		measurePointL = new ArrayList<MeasurePoint>(); 
		for (EloMeasurePoint p : EloMeasurePoint.values())
			measurePointL.add(new MeasurePoint(p));
		lblLoading.setText("Loading...... Measure Point Elements List");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		
		measureTable = new MeasureTable(measurePointL,guiConfig);
		lblLoading.setText("Loading...... Measure Collections");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		resultTable = new ResultTable(measurePointL,guiConfig);
		lblLoading.setText("Loading...... Result Table");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		
		winPressTemp = new WinPressTemp(enthalpy);
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
		
		enthalpyWin = new EnthalpyWin(pac, enthalpy, measureTable, resultTable, measurePointL, eDrawL,winPressTemp, panelPacToolTextFieldCOP);
		lblLoading.setText("Loading...... Win. Enthalpy");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winConfEnthalpy = new WinConfEnthalpy(enthalpyWin);
		lblLoading.setText("Loading...... Win. Configuration Enthalpy");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		measurePointTableWin = new MeasurePointTableWin(measureTable);
		lblLoading.setText("Loading...... Win. Measure Table");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		measureResultTableWin = new MeasureResultTableWin(resultTable);
		lblLoading.setText("Loading...... Win. Result Table");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		aboutWin = new AboutWin();
		lblLoading.setText("Loading...... Win. About");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		definitionWin = new DefinitionWin();
		lblLoading.setText("Loading...... Win. Definition");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);
		
		winLogger = new WinLogger(log4j2Config);
		lblLoading.setText("Loading...... Win. Logger");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);
		
		frame.setVisible(false);

	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public ResultTable getResultTable() {
		return resultTable;
	}

	public Pac getPac() {
		return pac;
	}

	public Enthalpy getEnthalpy() {
		return enthalpy;
	}

	public GuiConfig getGuiConfig() {
		return guiConfig;
	}

	public List<ElDraw> geteDrawL() {
		return eDrawL;
	}

	public List<MeasurePoint> getMeasurePointL() {
		return measurePointL;
	}
	
	public CompressorWin getWinCompressor() {
		return compressorWin;
	}

	public CirculatorDistrWin getWinCirculatorDistr() {
		return circulatorDistrWin;
	}

	public CirculatorSrcWin getWinCirculatorSrc() {
		return circulatorSrcWin;
	}
	
	public EnthalpyWin getWinEnthalpy() {
		return enthalpyWin;
	}

	public WinConfEnthalpy getWinConfEnthalpy() {
		return winConfEnthalpy;
	}

	public AboutWin getWinAbout() {
		return aboutWin;
	}

	public WinLogger getWinLogger() {
		return winLogger;
	}

	public DefinitionWin getWinDefinition() {
		return definitionWin;
	}

	public MeasurePointTableWin getWinMeasureTable() {
		return measurePointTableWin;
	}
	
	public MeasureResultTableWin getWinResultTable() {
		return measureResultTableWin;
	}

	public WinPressTemp getWinPressTemp() {
		return winPressTemp;
	}
	
	public MeasureTable getMeasureTable() {
		return measureTable;
	}

	public JTextField getPanelPacToolTextFieldCOP() {
		return panelPacToolTextFieldCOP;
	}
}
