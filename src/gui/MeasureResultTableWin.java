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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import gui.info.AboutWin;
import mpoints.EloMeasurePoint;
import mpoints.EloMeasureResult;
import mpoints.MeasurePoint;
import mpoints.MeasureResult;
import pac.Pac;
import translation.TLanguage;
import translation.TMeasureResult;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

/**
 * TableDemoWin uses a custom Table.
 */
public class MeasureResultTableWin extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	private JTable table;

	// -------------------------------------------------------
	// 				TEST THE APPLICATION STANDALONE 
	// -------------------------------------------------------
	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				GuiConfig guiConfig = new GuiConfig();
				guiConfig.setLanguage(TLanguage.FRENCH);

				// Create the List of measure points
				List<MeasurePoint> lMeasurePoints;
				lMeasurePoints = new ArrayList<MeasurePoint>(); 
				for (EloMeasurePoint p : EloMeasurePoint.values()) {
					lMeasurePoints.add(new MeasurePoint(p));
				}

				// Create Frame
				//MeasurePointTableWin tableDemoWin = new MeasurePointTableWin(lMeasurePoints, guiConfig);
				//tableDemoWin.setVisible(true);

				//Compute Points
				//MeasurePoint mp1 = lMeasurePoints.get(EloMeasurePoint.P1.id());
				MeasurePoint mp2 = lMeasurePoints.get(EloMeasurePoint.P2.id());
				MeasurePoint mp3 = lMeasurePoints.get(EloMeasurePoint.P3.id());
				MeasurePoint mp4 = lMeasurePoints.get(EloMeasurePoint.P4.id());
				MeasurePoint mp5 = lMeasurePoints.get(EloMeasurePoint.P5.id());
				MeasurePoint mp6 = lMeasurePoints.get(EloMeasurePoint.P6.id());
				MeasurePoint mp7 = lMeasurePoints.get(EloMeasurePoint.P7.id());
				MeasurePoint mp8 = lMeasurePoints.get(EloMeasurePoint.P8.id());

				Pac pac = new Pac();
				mp3.setValue(15, pac, lMeasurePoints);
				mp7.setValue(4, pac, lMeasurePoints);
				mp4.setValue(15, pac, lMeasurePoints);
				//mp1.setValue(0, pac, lMeasurePoints);
				mp2.setValue(69, pac, lMeasurePoints);
				mp5.setValue(30, pac, lMeasurePoints);
				mp6.setValue(-10, pac, lMeasurePoints);
				mp8.setValue(-10, pac, lMeasurePoints);


				// Create the List of Measure Results
				List<MeasureResult> lMeasureResults;
				lMeasureResults = new ArrayList<MeasureResult>(); 

				for (EloMeasureResult p : EloMeasureResult.values()) {
					lMeasureResults.add(new MeasureResult(p));
				}

				// Fill the list of Measure Results
				for (EloMeasureResult p : EloMeasureResult.values()) {
					lMeasureResults.get(p.id()).setValue(lMeasurePoints,pac);
				}

				// Now we can display
				MeasureResultTableWin measureResultTableWin = new MeasureResultTableWin(lMeasureResults, guiConfig); 
				measureResultTableWin.setVisible(true);

				// Now something can be changed
				measureResultTableWin.updateTableValues();				

			}
		});
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

		// Create Table
		table = new JTable(new MyTableModel(lMeasureResults, guiConfig));
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setAutoCreateRowSorter(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		table.getColumnModel().getColumn(2).setCellRenderer( rightRenderer );


		//Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		//Add the scroll pane to this panel.
		getContentPane().add(scrollPane);

		// Create Menu bar
		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmPrint = new JMenuItem("Print");
		mntmPrint.setIcon(new ImageIcon(MeasurePointTableWin.class.getResource("/gui/images/imprimante-16.png")));
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
					logger.error(exc);
				}			

			}
		});
		mnFile.add(mntmPrint);
		getContentPane().add(scrollPane);

		// { "Calcul", "Definition", "Value" };
		setJTableColumnsWidth( 700, 15, 65, 10);

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

	public void updateTableValues() {
		table.repaint();
	}


	// ****************************************************************************************
	// 										NEW CLASS : 	
	// ****************************************************************************************

	class MyTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		// { "Calcul", "Definition", "Value" };
		private String[] columnNames = new String[3];
		private List<MeasureResult> lMeasureResults;
		private GuiConfig guiConfig;

		// -------------------------------------------------------
		// 						CONSTRUCTOR
		// -------------------------------------------------------
		public MyTableModel( List<MeasureResult> vlMeasureResults, GuiConfig vguiConfig) {

			lMeasureResults = vlMeasureResults;
			guiConfig = vguiConfig;
			
			columnNames[0] = (String)TMeasureResult.DEF_TAB_RESULT.getLangue(guiConfig.getLanguage());
			columnNames[1] = (String)TMeasureResult.DEF_TAB_DEFINITION.getLangue(guiConfig.getLanguage());
			columnNames[2] = (String)TMeasureResult.DEF_TAB_VALUE.getLangue(guiConfig.getLanguage());

		}

		// -------------------------------------------------------
		// 						METHOD
		// -------------------------------------------------------

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return lMeasureResults.size();
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		/**
		 * Used to fill the table
		 */
		public Object getValueAt(int row, int col) {
			MeasureResult m = lMeasureResults.get(row); 
			switch (col) {

			// { "Calcul", "Definition", "Value" };
			case 0:
				return m.getMRObject().getDisplayTxt();
			case 1:
				return m.getMRObject().getDefinition(guiConfig.getLanguage());
			case 2:
				return Math.round(m.getValue()*100)/100.0;
			default:
				throw new IllegalArgumentException();
			}
		}
	}
}