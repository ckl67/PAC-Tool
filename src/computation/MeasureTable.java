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
package computation;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gui.GuiConfig;
import gui.WinMeasureTable;

public class MeasureTable extends JTable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(WinMeasureTable.class.getName());

	private List<MeasurePoint> measurePointL;
	private GuiConfig guiConfig;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public MeasureTable(List<MeasurePoint> vmeasurePointL , GuiConfig vguiConfig) {
		measurePointL = vmeasurePointL;
		guiConfig = vguiConfig;
		intiTable();

	}
	// -------------------------------------------------------
	// 						METHOD
	// -------------------------------------------------------

	public void setAllTableValues() {

		for (MeasureObject p : MeasureObject.values()) {
			int n = p.ordinal(); 		// p = T1,T2,... n = 0 , 1, 
			MeasurePoint m = measurePointL.get(n);  

			setValueAt( Math.round(m.getMT()*100.0)/100.0, n, 2);
			setValueAt( Math.round(m.getMP()*100.0)/100.0, n, 3);
			setValueAt( Math.round(m.getMH()*100.0)/100.0, n, 4);

			logger.info(
					"Point = {} Choice Status = {} value= {} T={} --> P={} ==> P0 or PK ={} H ={} ",
					p, m.getMeasureChoiceStatus(),m.getValue(),m.getMT(),m.getMP(),m.getMP0PK(),m.getMH()						
					);

		}
	}


	private void intiTable() {

		DefaultTableModel defaultTableModel = new DefaultTableModel();
		defaultTableModel.addColumn("Point");
		defaultTableModel.addColumn("Définition");
		defaultTableModel.addColumn("T (°C)");
		defaultTableModel.addColumn("P (bar)");
		defaultTableModel.addColumn("H (Kj/kg)");

		// Initialize
		for (MeasureObject p : MeasureObject.values()) {
			defaultTableModel.addRow( new Object[] {p,p.getDefinition(guiConfig.getLanguage()),"0.00","0.00","0.00"});
		}
		setModel(defaultTableModel);

	}
	
}
