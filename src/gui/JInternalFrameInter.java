package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import computation.MeasureObject;

import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ScrollPaneConstants;

public class JInternalFrameInter extends JInternalFrame {
	private JTable table;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JInternalFrameInter frame = new JInternalFrameInter();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
	
	/**
	 * Create the frame.
	 */
	public JInternalFrameInter() {
		setMaximizable(true);
		setClosable(true);
		setResizable(true);
		setBounds(100, 100, 593, 486);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 24, 352, 70);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int currentRow = table.getSelectedRow();
				textField.setText(table.getValueAt(currentRow,0).toString());
			}
		});
		scrollPane.setColumnHeaderView(table);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Test", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(37, 112, 352, 103);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblVal = new JLabel("Val:");
		lblVal.setBounds(23, 25, 55, 16);
		panel.add(lblVal);
		
		textField = new JTextField();
		textField.setBounds(66, 19, 167, 28);
		panel.add(textField);
		textField.setColumns(10);
		FillData();

	}
}
