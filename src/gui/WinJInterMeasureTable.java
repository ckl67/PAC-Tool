package gui;
 
import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import javax.swing.table.DefaultTableModel;

import computation.MeasureObject;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
 
 
public class WinJInterMeasureTable extends JInternalFrame {
 
    private static final long serialVersionUID = 1L;
    private JTable table;
 
    
    public WinJInterMeasureTable() {
    	setTitle("Measure Table");
    	setFrameIcon(new ImageIcon(WinJInterMeasureTable.class.getResource("/gui/images/PAC-Tool_32.png")));
    	setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setResizable(true);
        setBounds(100, 100, 593, 169);
         
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.createVerticalScrollBar();
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().add(scrollPane);
         
        table = new JTable();
        scrollPane.setColumnHeaderView(table);
        scrollPane.setViewportView(table);
         
        FillData();
    }
    
    private void FillData() {
    	 
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Point");
        defaultTableModel.addColumn("Définition");
        defaultTableModel.addColumn("Valeur");
 
        for (MeasureObject p : MeasureObject.values()) {
			defaultTableModel.addRow( new Object[] {p,p.getDefinition(),p.getXm()});
		}
 
        table.setModel(defaultTableModel);
 
    }
}