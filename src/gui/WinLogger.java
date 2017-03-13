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

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import log4j.Log4jDynConfig;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WinLogger extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldLoggerFileName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// If you logs before the System.setProperty, this will cause the variable UNDEFINED error.
					// 		private static final Logger logger = LogManager.getLogger(test.class.getName());
					// is forbidden here !!

					Log4jDynConfig log4jDynConfig1 = new Log4jDynConfig();
					WinLogger frame = new WinLogger(log4jDynConfig1);
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
	public WinLogger(Log4jDynConfig log4jDynConfig) {
		// If you logs before the System.setProperty, this will cause the variable UNDEFINED error.
		// 		private static final Logger logger = LogManager.getLogger(test.class.getName());
		// is forbidden here !!
		
		setTitle("Pac-Tool Logger");
		setIconImage(Toolkit.getDefaultToolkit().getImage(WinLogger.class.getResource("/gui/images/PAC-Tool_16.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 485, 183);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPathFile = new JLabel("Path File :");
		lblPathFile.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPathFile.setBounds(10, 26, 77, 14);
		contentPane.add(lblPathFile);
		
		textFieldLoggerFileName = new JTextField();
		textFieldLoggerFileName.setText(log4jDynConfig.getAppenderFile());
		textFieldLoggerFileName.setBounds(97, 23, 347, 20);
		contentPane.add(textFieldLoggerFileName);
		textFieldLoggerFileName.setColumns(10);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnClose.setBounds(370, 111, 89, 23);
		contentPane.add(btnClose);
	}
}
