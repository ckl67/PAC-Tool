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
import mpoints.MeasurePoint;
import pac.Pac;
import translation.TLanguage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

/**
 * TableDemoWin uses a custom Table.
 */
public class TableDemoWin extends JFrame {

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
				TableDemoWin tableDemoWin = new TableDemoWin(lMeasurePoints, guiConfig);
				tableDemoWin.setVisible(true);
				
				// FROM NOW lMeasurePoints data are linked to the table value !!
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

				
			}
		});
	}

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public TableDemoWin( List<MeasurePoint> lMeasurePoints, GuiConfig guiConfig) {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			logger.info(e);
		}

		// Create Frame
		setTitle("Measure Table");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutWin.class.getResource("/gui/images/PAC-Tool_16.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		setBounds(100, 100, 700, 271);


		// Create Table
		table = new JTable(new MyTableModel(lMeasurePoints, guiConfig));
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setAutoCreateRowSorter(true);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		table.getColumnModel().getColumn(2).setCellRenderer( rightRenderer );
		table.getColumnModel().getColumn(3).setCellRenderer( rightRenderer );
		table.getColumnModel().getColumn(4).setCellRenderer( rightRenderer );

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

		setJTableColumnsWidth( 700, 5, 65, 10, 10, 10);

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

	// ****************************************************************************************
	// 										NEW CLASS : 	
	// ****************************************************************************************

	class MyTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		private String[] columnNames = { "Point", "Definition", "T (°C)", "P (bar)", "H (Kj/kg)" };

		private List<MeasurePoint> lMeasurePoints;
		private GuiConfig guiConfig;

		// -------------------------------------------------------
		// 						CONSTRUCTOR
		// -------------------------------------------------------
		public MyTableModel( List<MeasurePoint> vlMeasurePoints, GuiConfig vguiConfig) {
			lMeasurePoints = vlMeasurePoints;
			guiConfig = vguiConfig;
		}

		// -------------------------------------------------------
		// 						METHOD
		// -------------------------------------------------------

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return lMeasurePoints.size();
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		// Used to fill the table
		public Object getValueAt(int row, int col) {
			MeasurePoint m = lMeasurePoints.get(row); 
			switch (col) {

			// { "Point", "Definition", "T (°C)", "P (bar)", "H (Kj/kg)" };
			case 0:
				return m.getMPObject().name();
			case 1:
				return m.getMPObject().getDefinition(guiConfig.getLanguage());
			case 2:
				return Math.round(m.getMP_T()*100)/100.0;
			case 3:
				return Math.round(m.getMP_P()*100)/100.0;
			case 4:
				return Math.round(m.getMP_H()*100)/100.0;
			default:
				throw new IllegalArgumentException();
			}
		}

		/**
		 * Don't need to implement this method unless your table's editable.
		 */
		public boolean isCellEditable(int row, int col) {
			//Note that the data/cell address is constant,
			//no matter where the cell appears onscreen.
			if (col < 2) {
				return false;
			} else {
				return true;
			}
		}

		/**
		 * Don't need to implement this method unless your table's data can
		 * change.
		 */
		public void setValueAt(Object value, int row, int col) {
			logger.debug("Setting value at {},{} to {} (an instance of {} ",
					row,col, value, value.getClass());

			MeasurePoint mp = lMeasurePoints.get(row); 
			switch (col) {

			// { "Point", "Definition", "T (°C)", "P (bar)", "H (Kj/kg)" };
			case 0:
				break;
			case 1:
				break;
			case 2:
				double t = Double.valueOf(value.toString());
				mp.setMP_T(t);
				break;
			case 3:
				double p = Double.valueOf(value.toString());
				mp.setMP_P(p);
				break;
			case 4:
				double h = Double.valueOf(value.toString());		
				mp.setMP_H(h);
				break;
			default:
				throw new IllegalArgumentException();
			}

			fireTableCellUpdated(row, col);


			logger.debug("New value of data:");
			logger.debug(mp.getMPObject().name());
			logger.debug(mp.getMPObject().getDefinition(TLanguage.FRENCH));
			logger.debug("    Group " + mp.getMPObject().getGroup());
			logger.debug("    Val = " + mp.getValue());
			logger.debug("    Selected = " + mp.getMPObjectSelection());
			logger.debug("    P = " + mp.getMP_P());
			logger.debug("    T = " + mp.getMP_T());
			logger.debug("    H = " + mp.getMP_H());
			logger.debug("    PO/PK = " + mp.getMP_P0PK(lMeasurePoints));
			logger.debug("\n"); 
		}
	}

}