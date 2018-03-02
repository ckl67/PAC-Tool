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
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import gui.helpaboutdef.AboutWin;
import mpoints.EloMeasurePoint;
import mpoints.EloMeasureResult;
import mpoints.MeasurePoint;
import mpoints.MeasureResult;
import pac.Pac;
import translation.TLanguage;
import translation.TMeasureResult;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class MeasureResultTableWin extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	private JTable table;

	// -------------------------------------------------------
	// 				TEST THE APPLICATION STANDALONE 
	// -------------------------------------------------------
	/**
	 * Launch the application for local test
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					// Create the List of measure points
					List<MeasurePoint> lMeasurePoints;
					lMeasurePoints = new ArrayList<MeasurePoint>(); 
					for (EloMeasurePoint p : EloMeasurePoint.values()) {
						lMeasurePoints.add(new MeasurePoint(p));
					}

					// Create PAC
					Pac pac = new Pac();

					// Set configuration
					GuiConfig guiConfig = new GuiConfig();
					guiConfig.setLanguage(TLanguage.FRENCH);

					//Compute Points
					MeasurePoint mp1 = lMeasurePoints.get(EloMeasurePoint.P1.id());
					MeasurePoint mp2 = lMeasurePoints.get(EloMeasurePoint.P2.id());
					MeasurePoint mp3 = lMeasurePoints.get(EloMeasurePoint.P3.id());
					MeasurePoint mp4 = lMeasurePoints.get(EloMeasurePoint.P4.id());
					MeasurePoint mp5 = lMeasurePoints.get(EloMeasurePoint.P5.id());
					MeasurePoint mp6 = lMeasurePoints.get(EloMeasurePoint.P6.id());
					MeasurePoint mp7 = lMeasurePoints.get(EloMeasurePoint.P7.id());
					MeasurePoint mp8 = lMeasurePoints.get(EloMeasurePoint.P8.id());

					mp3.setValue(15, pac, lMeasurePoints);
					mp7.setValue(4, pac, lMeasurePoints);
					mp4.setValue(15, pac, lMeasurePoints);
					mp1.setValue(0, pac, lMeasurePoints);
					mp2.setValue(69, pac, lMeasurePoints);
					mp5.setValue(30, pac, lMeasurePoints);
					mp6.setValue(-10, pac, lMeasurePoints);
					mp8.setValue(-10, pac, lMeasurePoints);

					
					// Create the List of measure points
					List<MeasureResult> lMeasureResults;
					lMeasureResults = new ArrayList<MeasureResult>(); 
					for (EloMeasureResult p : EloMeasureResult.values()) {
						lMeasureResults.add(new MeasureResult(p,lMeasurePoints,pac));
					}

					// Now we can display
					MeasureResultTableWin measureResultTableWin = new MeasureResultTableWin(lMeasureResults, guiConfig); 
					measureResultTableWin.setVisible(true);
					
					// Now something can be changed
					measureResultTableWin.updateTableValues(lMeasureResults, guiConfig);				

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Update the window with the new measure points
	 * @param lMeasureResults
	 * @param guiConfig
	 */
	public void updateTableValues(List<MeasureResult> lMeasureResults, GuiConfig guiConfig) {

		for (int n = 0; n < lMeasureResults.size(); n++) {

			MeasureResult m = lMeasureResults.get(n);  
			table.setValueAt( Math.round(m.getValue()*100)/100.0, n, 2);
			logger.info(
					"Result = {} Def = {} value= {} ",
					m.getMRObject().getDisplayTxt(),
					m.getMRObject().getDefinition(guiConfig.getLanguage()),
					m.getValue()	
					);
		}
	}

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public MeasureResultTableWin( List<MeasureResult> lMeasureResults, GuiConfig guiConfig ) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			logger.info(e);
		}

		setTitle("Measure Table");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutWin.class.getResource("/gui/images/PAC-Tool_16.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		setBounds(100, 100, 700, 271);

		// Construct table
		DefaultTableModel defaultTableModel = new DefaultTableModel();
		defaultTableModel.addColumn(TMeasureResult.DEF_TAB_RESULT.getLangue(guiConfig.getLanguage()));
		defaultTableModel.addColumn(TMeasureResult.DEF_TAB_DEFINITION.getLangue(guiConfig.getLanguage()));
		defaultTableModel.addColumn(TMeasureResult.DEF_TAB_VALUE.getLangue(guiConfig.getLanguage()));

		for (int i = 0; i < lMeasureResults.size(); i++) {

			MeasureResult m = lMeasureResults.get(i);  
			defaultTableModel.addRow( 
					new Object[] {
							" " + m.getMRObject().getDisplayTxt(),
							" " + m.getMRObject().getDefinition(guiConfig.getLanguage()),
							m.getValue()	
					});
			
			logger.info(
					"Result = {} Def = {} value= {} ",
					m.getMRObject().getDisplayTxt(),
					m.getMRObject().getDefinition(guiConfig.getLanguage()),
					m.getValue()	
					);	
		}
		table = new JTable(defaultTableModel);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.createVerticalScrollBar();
		getContentPane().setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmPrint = new JMenuItem("Print");
		mntmPrint.setIcon(new ImageIcon(MeasureResultTableWin.class.getResource("/gui/images/imprimante-16.png")));
		mntmPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					boolean complete = table.print();
					if (complete) {
						/* show a success message  */
						System.out.print("OK");
					} else {
						/*show a message indicating that printing was cancelled */
						System.out.print("NOK");
					}
				} catch (PrinterException exc) {
					System.out.println(exc);
				}			

			}
		});
		mnFile.add(mntmPrint);
		getContentPane().add(scrollPane);

		scrollPane.setColumnHeaderView(table);
		scrollPane.setViewportView(table);

		setJTableColumnsWidth( 700, 15, 65,10);

	}

	// -------------------------------------------------------
	// 						METHOD
	// -------------------------------------------------------

	private void setJTableColumnsWidth(int tablePreferredWidth, double... percentages) {
		double total = 0;
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			total += percentages[i];
		}

		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth((int)
					(tablePreferredWidth * (percentages[i] / total)));
		}
	}

}