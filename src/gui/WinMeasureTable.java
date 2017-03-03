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
import java.awt.Toolkit;
import javax.swing.table.DefaultTableModel;

import computation.MeasureCollection;
import computation.MeasureObject;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JFrame;
 
 
public class WinMeasureTable extends JFrame {
 
    private static final long serialVersionUID = 1L;
    private JTable table;
 
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
    
    public WinMeasureTable(MeasureCollection measureCollection) {
    	
    	setTitle("Measure Table");
		setIconImage(Toolkit.getDefaultToolkit().getImage(WinAbout.class.getResource("/gui/images/PAC-Tool_16.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
    
	// -------------------------------------------------------
	// 						METHOD
	// -------------------------------------------------------
    
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