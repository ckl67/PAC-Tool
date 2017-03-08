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


import computation.COP;
import computation.MeasureCollection;
import computation.MeasureTable;
import computation.Misc;
import enthalpy.Enthalpy;
import pac.Pac;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class PacToolVar {

	// By creating all the instances here, assure that 
	//   only 1 instance will be created
	//   all data are loaded once
	//   no problem of missing variables 

	private Pac pac;
	private Enthalpy enthalpy;
	private COP cop;

	private MeasureCollection measureCollection;
	private List<ElDraw> eDrawL;

	private WinPacToolConfig winPacToolConfig;
	private WinCompressor winCompressor;

	private WinEnthalpy winEnthalpy;
	private WinConfEnthalpy winConfEnthalpy;

	private WinPressTemp winPressTemp;

	private MeasureTable measureTable;
	private WinMeasureTable winMeasureTable;

	private WinAbout winAbout;
	private WinDefinition winDefinition;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	PacToolVar() {

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

		JLabel lblPacVersion = new JLabel("PAC-Tool (" + Misc.PACTool_Version + ")");
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
		int iterations = 13;
		int percent = 0;
		
		pac = new Pac();								// Contains Pac Features
		lblLoading.setText("Loading...... Pac Features");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		enthalpy = new Enthalpy();						// Enthalpy Features
		lblLoading.setText("Loading...... Enthalpy Features");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winPacToolConfig = new WinPacToolConfig();		// GUI Configuration
		lblLoading.setText("Loading...... GUI Configuration");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		cop = new COP();								// COP Compute
		lblLoading.setText("Loading...... COP");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		eDrawL = new ArrayList<ElDraw>();				// Draw Elements
		lblLoading.setText("Loading...... Draw Elements");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		measureCollection = new MeasureCollection();	// Measure Collection
		measureTable = new MeasureTable(measureCollection);
		lblLoading.setText("Loading...... Measure Collections");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winPressTemp = new WinPressTemp(enthalpy);
		lblLoading.setText("Loading...... Win. Temperature/Pressure");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winCompressor = new WinCompressor(pac, winPacToolConfig);
		lblLoading.setText("Loading...... Win. Compressor");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winEnthalpy = new WinEnthalpy(pac, enthalpy, measureTable, eDrawL,winPressTemp);
		lblLoading.setText("Loading...... Win. Enthalpy");
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winConfEnthalpy = new WinConfEnthalpy(winEnthalpy);
		lblLoading.setText("Loading...... Win. Configuration Enthalpy");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winMeasureTable = new WinMeasureTable(measureTable);
		lblLoading.setText("Loading...... Win. Measure Table");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winAbout = new WinAbout();
		lblLoading.setText("Loading...... Win. About");		
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winDefinition = new WinDefinition();
		lblLoading.setText("Loading...... Win. Definition");		
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

	public Enthalpy getEnthalpy() {
		return enthalpy;
	}

	public WinPacToolConfig getWinPacToolConfig() {
		return winPacToolConfig;
	}

	public List<ElDraw> geteDrawL() {
		return eDrawL;
	}

	public MeasureCollection getMeasureCollection() {
		return measureCollection;
	}

	public COP getCop() {
		return cop;
	}

	public WinCompressor getWinCompressor() {
		return winCompressor;
	}

	public WinEnthalpy getWinEnthalpy() {
		return winEnthalpy;
	}

	public WinConfEnthalpy getWinConfEnthalpy() {
		return winConfEnthalpy;
	}

	public WinAbout getWinAbout() {
		return winAbout;
	}

	public WinDefinition getWinDefinition() {
		return winDefinition;
	}

	public WinMeasureTable getWinMeasureTable() {
		return winMeasureTable;
	}
	public WinPressTemp getWinPressTemp() {
		return winPressTemp;
	}
	
	public MeasureTable getMeasureTable() {
		return measureTable;
	}

}
