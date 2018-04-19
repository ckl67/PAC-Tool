package mpoints;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import gui.GuiConfig;
import translation.TMeasurePoint;

public class MeasurePointTableModel extends AbstractTableModel {

	private GuiConfig guiConfig;
	private List<MeasurePoint> tabLMeasurePoints;

	private final String[] tableHeader = { 
			"a",
			"b",
			"T (°C)",
			"P (bar)",
	"H (Kj/kg)" };

	public MeasurePointTableModel(List<MeasurePoint> lMeasurePoints, GuiConfig guiConfig) {
		this.tabLMeasurePoints.addAll(lMeasurePoints);
		this.guiConfig = guiConfig;
	}

	@Override
	public int getRowCount() {
		return tableHeader.length;
	}

	public String getColumnName(int columnIndex) {
		return tableHeader[columnIndex];
	}

	@Override
	public int getColumnCount() {
		return tabLMeasurePoints.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {

	}

}
