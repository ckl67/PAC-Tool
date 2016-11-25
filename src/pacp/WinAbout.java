/*
 * - PAC-Tool - 
 * Tool for understanding basics and computation of PAC (Pompe � Chaleur)
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
package pacp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkListener;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Desktop;
import javax.swing.event.HyperlinkEvent;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URISyntaxException;

public class WinAbout {

	private JFrame frame;

	// ========================================================================================
	/**
	 * Launch the application.
	 */
	public void NewAboutWin() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinAbout window = new WinAbout();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// ========================================================================================
	/**
	 * Create the application.
	 */
	public WinAbout() {
		initialize();
	}

	// ========================================================================================
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WinAbout.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 370, 152);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		PacMain.centreWindow(frame);
		
		JTextPane txtpnabout = new JTextPane();
		txtpnabout.setEditable(false);
		txtpnabout.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
					try {
						Desktop.getDesktop().mail(e.getURL().toURI());
					} catch (IOException e1) {
						throw new RuntimeException(e1);
					} catch (URISyntaxException e1) {
						throw new RuntimeException(e1);
					}
				}
			}
		});
		txtpnabout.setContentType("text/html");
		txtpnabout.setText("<span\r\n style=\"font-family: Helvetica,Arial,sans-serif; color: rgb(102, 102, 204);\"><span\r\n style=\"font-weight: bold;\">\r\nPAC-Tool </span><br>\r\n&nbsp;&nbsp;&nbsp; Outil pour Pompe \u00E0 Chaleur <br>\r\n<br>\r\n<b>Auteur:</b>\r\nChristian\r\nKlugesherz<br>\r\n<b>email:</b>\r\n<a href=\"mailto:christian.klugesherz@gmail.com?Subject=PAC-Tool\"\r\n target=\"_top\">christian.klugesherz@gmail.com</a></span>\r\n");
		txtpnabout.setBounds(84, 11, 275, 111);

		frame.getContentPane().add(txtpnabout);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(WinAbout.class.getResource("/pacp/images/PAC-Tool_64.png")));
		lblNewLabel.setBounds(10, 11, 64, 64);
		frame.getContentPane().add(lblNewLabel);
	}

}
