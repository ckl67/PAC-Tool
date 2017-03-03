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
import enthalpy.Enthalpy;
import pac.Pac;
import javax.swing.ImageIcon;

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
		panel.setBackground(new Color(0, 0, 205));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblLoading = new JLabel("Loading......");
		lblLoading.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLoading.setForeground(Color.WHITE);
		lblLoading.setBounds(26, 11, 81, 22);
		panel.add(lblLoading);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(146, 40, 172, 22);
		panel.add(progressBar);

		JLabel lblImage = new JLabel("New label");
		lblImage.setIcon(new ImageIcon(PacToolVar.class.getResource("/gui/images/PAC-Tool_64.png")));
		lblImage.setBounds(26, 44, 63, 63);
		panel.add(lblImage);

	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
		frame.setVisible(true);
		
		// -------------------------
		int i = 1;
		int iterations = 13;
		int percent = 0;
		
		pac = new Pac();								// Contains Pac Features
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		enthalpy = new Enthalpy();						// Enthalpy Features
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winPacToolConfig = new WinPacToolConfig();		// GUI Configuration
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		cop = new COP();								// COP Compute
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		eDrawL = new ArrayList<ElDraw>();				// Draw Elements
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		measureCollection = new MeasureCollection();	// Measure Collection
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winPressTemp = new WinPressTemp(enthalpy);
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winCompressor = new WinCompressor(pac, winPacToolConfig);
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winEnthalpy = new WinEnthalpy(pac, enthalpy, measureCollection, eDrawL,winPressTemp);
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winConfEnthalpy = new WinConfEnthalpy(winEnthalpy);
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winMeasureTable = new WinMeasureTable(measureCollection);
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winAbout = new WinAbout();
		percent = 100*i++/iterations;
		progressBar.setValue(percent);

		winDefinition = new WinDefinition();
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

}
