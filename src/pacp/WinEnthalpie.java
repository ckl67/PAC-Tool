package pacp;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;

import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

public class WinEnthalpie {

	private JFrame frame;
	PanelEnthalpie panelEnthalpyDrawArea;
	JLabel lblMouseCoordinate;
	JLabel lblEnthalpyCoord;
	
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

		JPanel panelEnthalpyBottom = new JPanel();
		frame.getContentPane().add(panelEnthalpyBottom, BorderLayout.SOUTH);

		lblMouseCoordinate = new JLabel("Mouse Coordinate");
		lblMouseCoordinate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMouseCoordinate.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent m) {
				String s = "x: %d   y: %d";
				lblMouseCoordinate.setText(String.format(s, m.getX(), m.getY()));
				
				double result = panelEnthalpyDrawArea.getH(m.getX());
				lblEnthalpyCoord.setText(String.format("H=%.2f",result));
		    
			}
		};
		panelEnthalpyBottom.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblEnthalpyCoord = new JLabel("Enthalpy Coordinate");
		lblEnthalpyCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.add(lblEnthalpyCoord);

		panelEnthalpyBottom.add(lblMouseCoordinate);

		JPanel panelEnthalpyRight = new JPanel();
		panelEnthalpyRight.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		frame.getContentPane().add(panelEnthalpyRight, BorderLayout.EAST);
		panelEnthalpyRight.setLayout(new GridLayout(0, 1, 0, 0));
		
		panelEnthalpyDrawArea = new PanelEnthalpie("/pacp/images/diagrammes enthalpie/R22.png",ma);	
		panelEnthalpyDrawArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		panelEnthalpyDrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		frame.getContentPane().add(panelEnthalpyDrawArea, BorderLayout.CENTER);
		
		JPanel panelHight = new JPanel();
		panelEnthalpyRight.add(panelHight);
		panelHight.setLayout(new BoxLayout(panelHight, BoxLayout.Y_AXIS));
		
		JButton btnSetOrgEnthalpy = new JButton("Origine H");
		btnSetOrgEnthalpy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelEnthalpyDrawArea.SetOrigineH();
			}
		});
		panelHight.add(btnSetOrgEnthalpy);
		
		JButton btnSetFinalEnthalpy = new JButton(" Final H ");
		btnSetFinalEnthalpy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelEnthalpyDrawArea.SetFinalH();
			}
		});
		panelHight.add(btnSetFinalEnthalpy);
		
		JPanel panelBottom = new JPanel();
		panelEnthalpyRight.add(panelBottom);
		panelBottom.setLayout(new BorderLayout(0, 0));
		
		JButton btnClear = new JButton("Effacer");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelEnthalpyDrawArea.Clean();
			}
		});
		panelBottom.add(btnClear, BorderLayout.SOUTH);




	}

}
