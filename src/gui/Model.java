package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class Model extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Model frame = new Model();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Model() {
		setBounds(100, 100, 450, 361);

	}

}
