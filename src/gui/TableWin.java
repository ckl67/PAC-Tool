package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import mpoints.EloMeasurePoint;
import mpoints.MeasurePoint;
import mpoints.MeasurePointTableModel;
import pac.Pac;
import translation.TLanguage;

public class TableWin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

					GuiConfig guiConfig = new GuiConfig();
					guiConfig.setLanguage(TLanguage.FRENCH);

					TableWin tableWin = new TableWin(lMeasurePoints,guiConfig);
					tableWin.setVisible(true);
					


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public TableWin(List<MeasurePoint> lMeasurePoints, GuiConfig guiConfig) {
		super();
		setTitle("Notes des élèves");
		setPreferredSize(new Dimension(500, 400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MeasurePointTableModel table = new MeasurePointTableModel(lMeasurePoints,guiConfig);
		//getContentPane().add(new JScrollPane(), CENTER);

		pack();
	}
}
