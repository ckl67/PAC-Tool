package pacp.test;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.SwingConstants;


public class MoveArrayList {
	private JFrame frame;
	private JTextField textFieldName;
	
	private List<Person> personList = new ArrayList<Person>();


	//--------------------------------------------------------------------------------------------------------------------------------------------
	// Launch the application.
	//--------------------------------------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MoveArrayList window = new MoveArrayList();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//--------------------------------------------------------------------------------------------------------------------------------------------
	// Create the application.
	//--------------------------------------------------------------------------------------------------------------------------------------------
	public MoveArrayList() {
		personList.add(new Person());
		personList.add(new Person());
		personList.add(new Person());
		
		personList.get(0).setName("Name 0");
		personList.get(1).setName("Name 1");
		personList.get(2).setName("Name 2");
		initialize();
	}

	//--------------------------------------------------------------------------------------------------------------------------------------------
	// Initialize the contents of the frame.
	//--------------------------------------------------------------------------------------------------------------------------------------------
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 379, 142);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// ---------------------------------------------------------------
		// Text Field
		// ---------------------------------------------------------------
		textFieldName = new JTextField();
		textFieldName.setToolTipText("Name can be modified");
		textFieldName.setFont(new Font("Tahoma", Font.BOLD, 16));
		textFieldName.setBorder(null);
		textFieldName.setBackground(UIManager.getColor("Button.background"));
		textFieldName.setBounds(82, 17, 120, 36);
		textFieldName.setColumns(10);
		textFieldName.setText(personList.get(0).getName());	
		frame.getContentPane().add(textFieldName);

		// ---------------------------------------------------------------
		// Label
		// ---------------------------------------------------------------
		JLabel lblName1 = new JLabel("Name:");
		lblName1.setBounds(10, 19, 50, 14);
		frame.getContentPane().add(lblName1);
		
		JLabel lblName2 = new JLabel("(editable )");
		lblName2.setHorizontalAlignment(SwingConstants.CENTER);
		lblName2.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblName2.setBounds(5, 35, 51, 14);
		frame.getContentPane().add(lblName2);

		// ---------------------------------------------------------------
		// Combobox
		// ---------------------------------------------------------------
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ComboId = comboBox.getSelectedIndex();
				//System.out.println("Combo Id = " + ComboId);
				//System.out.println("   --> " + personList.get(ComboId).getName());			
				textFieldName.setText(personList.get(ComboId).getName());
			}
		});
		comboBox.addItem(personList.get(0).getName());
		comboBox.addItem(personList.get(1).getName());
		comboBox.addItem(personList.get(2).getName());
		comboBox.setBounds(212, 27, 138, 20);
		frame.getContentPane().add(comboBox);
		
		// ---------------------------------------------------------------
		// Save
		// ---------------------------------------------------------------
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ComboId = comboBox.getSelectedIndex();
				String tmp = textFieldName.getText();
				
				comboBox.removeItemAt(ComboId);
				comboBox.insertItemAt(tmp, ComboId);
				comboBox.setSelectedIndex(ComboId);
				personList.get(ComboId).setName(tmp);
				textFieldName.setText(tmp);
			}
		});
		btnSave.setBounds(131, 67, 89, 23);
		frame.getContentPane().add(btnSave);
		
		// ---------------------------------------------------------------
		// Delete
		// ---------------------------------------------------------------
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ComboId = comboBox.getSelectedIndex();
				if ( ComboId > 0 ) {
					System.out.println("(Delete) ComboId="+ComboId);
					personList.remove(ComboId);
					comboBox.removeItemAt(ComboId);
					comboBox.setSelectedIndex(ComboId-1);
				} else {
					JOptionPane.showMessageDialog(frame, "This entry cannot be deleted");
				}
			}
		});
		btnDelete.setBounds(230, 67, 89, 23);
		frame.getContentPane().add(btnDelete);

		// ---------------------------------------------------------------
		// New
		// ---------------------------------------------------------------
		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ComboId = comboBox.getSelectedIndex();
				//System.out.println("ComboId="+ComboId);
				ComboId++;
				personList.add(ComboId, new Person());
				comboBox.insertItemAt("Empty", ComboId);
				comboBox.setSelectedIndex(ComboId);
			}
		});
		btnNew.setBounds(32, 67, 89, 23);
		frame.getContentPane().add(btnNew);
		

	}
}
