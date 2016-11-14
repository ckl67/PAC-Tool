package pacp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkListener;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.event.HyperlinkEvent;
import java.awt.Toolkit;

public class AboutWin {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void NewAboutWin() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AboutWin window = new AboutWin();
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
	public AboutWin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(AboutWin.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 370, 152);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextPane txtpnabout = new JTextPane();
		txtpnabout.setEditable(false);
		txtpnabout.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent arg0) {
				
			}
			
		});
		txtpnabout.setContentType("text/html");
		txtpnabout.setText("<span\r\n style=\"font-family: Helvetica,Arial,sans-serif; color: rgb(102, 102, 204);\"><span\r\n style=\"font-weight: bold;\">\r\nPAC-Tool </span><br>\r\n&nbsp;&nbsp;&nbsp; Outil pour Pompe \u00E0 Chaleur <br>\r\n<br>\r\n<span style=\"text-decoration: underline;\">Auteur:</span>\r\nChristian\r\nKlugesherz<br>\r\n<span style=\"text-decoration: underline;\">email :</span>\r\n<a href=\"mailto:christian.klugesherz@gmail.com\">christian.klugesherz@gmail.com</a></span>\r\n");

		txtpnabout.setBounds(84, 11, 275, 111);
		frame.getContentPane().add(txtpnabout);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AboutWin.class.getResource("/pacp/images/PAC-Tool_64.png")));
		lblNewLabel.setBounds(10, 11, 64, 64);
		frame.getContentPane().add(lblNewLabel);
	}
}
