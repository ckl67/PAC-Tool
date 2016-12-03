package pacp;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class WinEnthalpie {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinEnthalpie window = new WinEnthalpie();
					window.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WinEnthalpie() {
		initialize();
	}

	
	public void WinEnthalpieVisible() {
		frame.setVisible(true);
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelEnthalpieBottom = new JPanel();
		frame.getContentPane().add(panelEnthalpieBottom, BorderLayout.SOUTH);

		JLabel lblCoordinate = new JLabel("Mouse Coordinate");

		MouseMotionAdapter ma = new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent m) {
				String s = "x: %d   y: %d";
				lblCoordinate.setText(String.format(s, m.getX(), m.getY()));
			}
		};

		panelEnthalpieBottom.add(lblCoordinate);

		JPanel panelEnthalpieRight = new JPanel();
		frame.getContentPane().add(panelEnthalpieRight, BorderLayout.EAST);

		JButton btnNewButton = new JButton("New button");
		panelEnthalpieRight.add(btnNewButton);

		PanelEnthalpie panel_draw = new PanelEnthalpie(640,480, "/pacp/images/diagrammes enthalpie/R22.png",ma);	
		frame.getContentPane().add(panel_draw, BorderLayout.CENTER);

	}

}
