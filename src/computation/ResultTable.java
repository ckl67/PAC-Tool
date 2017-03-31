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

public class ResultTable extends JTable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(ResultTable.class.getName());

	private MeasureCollection measureCollection;
	private GuiConfig guiConfig;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public ResultTable(MeasureCollection vmeasureCollection, GuiConfig vguiConfig) {
		measureCollection = vmeasureCollection;
		guiConfig = vguiConfig;
		intiTable();

	}
	// -------------------------------------------------------
	// 						METHOD
	// -------------------------------------------------------

	public void setAllTableValues() {
		for (ResultObject p : ResultObject.values()) {

			List<MeasurePoint> measureL = measureCollection.getMeasurePL();
			double result = 0;
			switch (p) {
			case T1_T8: 
				result = measureL.get(MeasureObject.T1.ordinal()).getMT()- measureL.get(MeasureObject.T8.ordinal()).getMT();  
				setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
				break;
			case T2_T1: 
				result = measureL.get(MeasureObject.T2.ordinal()).getMT()- measureL.get(MeasureObject.T1.ordinal()).getMT();  
				setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
				break;
			case T3_T2:
				result = measureL.get(MeasureObject.P3.ordinal()).getMT()- measureL.get(MeasureObject.T2.ordinal()).getMT();  
				setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
				break;
			case T3T4:
				result = measureL.get(MeasureObject.P3.ordinal()).getMT();  
				setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
				break;
			case T4_T5:
				result = measureL.get(MeasureObject.P4.ordinal()).getMT()- measureL.get(MeasureObject.T5.ordinal()).getMT();  
				setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
				break;
			case T5_T6:
				result = measureL.get(MeasureObject.T5.ordinal()).getMT()- measureL.get(MeasureObject.T6.ordinal()).getMT();  
				setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
				break;
			case T7:
				result = measureL.get(MeasureObject.P7.ordinal()).getMT();  
				setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
				break;
			case T8_T7:
				result = measureL.get(MeasureObject.T8.ordinal()).getMT()- measureL.get(MeasureObject.P7.ordinal()).getMT();  
				setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
				break;
			default :
				break;

			} 
			logger.info(" {}= {} {}",p.getDisplayTxt(),result,p.getUnity());

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
			defaultTableModel.addRow( new Object[] {p.getDisplayTxt(),p.getDefinition(guiConfig.getLanguage()),"0.00",p.getUnity()});				
		}
		setModel(defaultTableModel);

	}

	public MeasureCollection getMeasureCollection() {
		return measureCollection;
	}

}
