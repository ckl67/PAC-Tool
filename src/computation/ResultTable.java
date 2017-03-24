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
import gui.WinResultTable;

public class ResultTable extends JTable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(ResultTable.class.getName());

	private MeasureCollection measureCollection;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public ResultTable(MeasureCollection vmeasureCollection) {
		measureCollection = vmeasureCollection;
		intiTable();

	}
	// -------------------------------------------------------
	// 						METHOD
	// -------------------------------------------------------

	public void setAllTableValues() {
		for (ResultObject p : ResultObject.values()) {
			
			List<MeasurePoint> measureL = measureCollection.getMeasurePL();
			switch (p) {
			case T1_T8: 
				int nT1 = MeasureObject.T1.ordinal();
				int nT8 = MeasureObject.T8.ordinal();
				double val = measureL.get(nT1).getMT()- measureL.get(nT8).getMT();  
				
				setValueAt( (val*100)/100, p.ordinal(), 2);
				
			
				break;
			default :
				break;
			} 



		}

	}


	private void intiTable() {

		DefaultTableModel defaultTableModel = new DefaultTableModel();
		defaultTableModel.addColumn("Compute");
		defaultTableModel.addColumn("Définition");
		defaultTableModel.addColumn("Valeur");
		defaultTableModel.addColumn("T (°C)");


		// Initialize
		for (ResultObject p : ResultObject.values()) {
			
			List<MeasurePoint> measureL = measureCollection.getMeasurePL();
			switch (p) {
			case T1_T8: 
				int nT1 = MeasureObject.T1.ordinal();
				int nT8 = MeasureObject.T8.ordinal();
				double val = measureL.get(nT1).getMT()- measureL.get(nT8).getMT();  
				defaultTableModel.addRow( new Object[] {p.getDisplayTxt(),p.getDefinition(),val,p.getUnity()});				
				break;
			default :
				break;
			} 



		}
		setModel(defaultTableModel);

	}

	public MeasureCollection getMeasureCollection() {
		return measureCollection;
	}
	
}
