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
import log4j.Log4j2Config;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import org.apache.logging.log4j.Level;
import javax.swing.JRadioButton;
import java.awt.GridLayout;
import javax.swing.JCheckBox;

public class WinLogger extends JFrame {

	private static final long serialVersionUID = 1L;
	private Log4j2Config log4j2Config;
	private JPanel contentPane;
	private JTextField textFieldLoggerFilePath;
	private JRadioButton rdbtnLevelAll;
	private JRadioButton rdbtnLevelTrace;
	private JRadioButton rdbtnLevelDebug;
	private JRadioButton rdbtnLevelInfo;
	private JRadioButton rdbtnLevelWarn;
	private JRadioButton rdbtnLevelError;
	private JRadioButton rdbtnLevelFatal;
	private JRadioButton rdbtnLevelOff;
	private JTextField txtPactoollog;
	private JPanel panel;
	private JCheckBox chckbxConsole;
	private JCheckBox chckbxFile;

	// -------------------------------------------------------
	// 						TEST
	// -------------------------------------------------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Log4j2Config log4jDynConfig1 = new Log4j2Config();
					
					WinLogger frame = new WinLogger(log4jDynConfig1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// -------------------------------------------------------
	// 						CREATE FRAME
	// -------------------------------------------------------
	public WinLogger(Log4j2Config vlog4j2Config) {
		log4j2Config = vlog4j2Config;
		
		setTitle("Pac-Tool Logger");
		setIconImage(Toolkit.getDefaultToolkit().getImage(WinLogger.class.getResource("/gui/images/PAC-Tool_16.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 511, 285);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPathFile = new JLabel("Log File Path :");
		lblPathFile.setBounds(22, 26, 89, 14);
		contentPane.add(lblPathFile);

		textFieldLoggerFilePath = new JTextField();
		textFieldLoggerFilePath.setEditable(false);
		textFieldLoggerFilePath.setText(log4j2Config.getLogPath());
		textFieldLoggerFilePath.setBounds(119, 23, 304, 20);
		contentPane.add(textFieldLoggerFilePath);
		textFieldLoggerFilePath.setColumns(10);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnClose.setBounds(400, 219, 89, 23);
		contentPane.add(btnClose);

		JPanel panelLevel = new JPanel();
		panelLevel.setBorder(new TitledBorder(null, "Level", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLevel.setBounds(22, 100, 329, 97);
		contentPane.add(panelLevel);
		panelLevel.setLayout(new GridLayout(0, 4, 0, 0));

		rdbtnLevelAll = new JRadioButton("All");
		rdbtnLevelAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log4j2Config.setLevel(Level.ALL);
			}
		});
		panelLevel.add(rdbtnLevelAll);

		rdbtnLevelTrace = new JRadioButton("Trace");
		rdbtnLevelTrace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log4j2Config.setLevel(Level.TRACE);
			}
		});
		panelLevel.add(rdbtnLevelTrace);

		rdbtnLevelDebug = new JRadioButton("Debug");
		rdbtnLevelDebug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log4j2Config.setLevel(Level.DEBUG);
			}
		});
		panelLevel.add(rdbtnLevelDebug);

		rdbtnLevelInfo = new JRadioButton("Info");
		rdbtnLevelInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log4j2Config.setLevel(Level.INFO);
			}
		});
		panelLevel.add(rdbtnLevelInfo);

		rdbtnLevelWarn = new JRadioButton("Warn");
		rdbtnLevelWarn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log4j2Config.setLevel(Level.WARN);
			}
		});
		panelLevel.add(rdbtnLevelWarn);

		rdbtnLevelError = new JRadioButton("Error");
		rdbtnLevelError.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log4j2Config.setLevel(Level.ERROR);
			}
		});
		panelLevel.add(rdbtnLevelError);

		rdbtnLevelFatal = new JRadioButton("Fatal");
		rdbtnLevelFatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log4j2Config.setLevel(Level.FATAL);
			}
		});
		panelLevel.add(rdbtnLevelFatal);

		rdbtnLevelOff = new JRadioButton("Off");
		rdbtnLevelOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log4j2Config.setLevel(Level.OFF);
			}
		});
		panelLevel.add(rdbtnLevelOff);

		ButtonGroup groupLevel = new ButtonGroup();
		groupLevel.add(rdbtnLevelAll);
		groupLevel.add(rdbtnLevelTrace);
		groupLevel.add(rdbtnLevelDebug);
		groupLevel.add(rdbtnLevelInfo);
		groupLevel.add(rdbtnLevelWarn);
		groupLevel.add(rdbtnLevelError);
		groupLevel.add(rdbtnLevelFatal);
		groupLevel.add(rdbtnLevelOff);
		
		JLabel lblFileName = new JLabel("Log File Name :");
		lblFileName.setBounds(22, 62, 77, 14);
		contentPane.add(lblFileName);
		
		txtPactoollog = new JTextField();
		txtPactoollog.setEditable(false);
		txtPactoollog.setText("Pac-Tool.log");
		txtPactoollog.setBounds(119, 59, 86, 20);
		contentPane.add(txtPactoollog);
		txtPactoollog.setColumns(10);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Logger", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(373, 100, 104, 97);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		chckbxConsole = new JCheckBox("Console");
		chckbxConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log4j2Config.activeLoggerConsole(chckbxConsole.isSelected());
			}
		});
		panel.add(chckbxConsole);
		
		chckbxFile = new JCheckBox("File");
		chckbxFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log4j2Config.activeLoggerLogFile(chckbxFile.isSelected());
			}
		});
		panel.add(chckbxFile);

		setRadioButtonLogLevel();
		setCheckBoxLogger();
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public void setRadioButtonLogLevel() {
	
		if (log4j2Config.getLevel() == Level.ALL)
			rdbtnLevelAll.setSelected(true);
		else if (log4j2Config.getLevel() == Level.TRACE) 
			rdbtnLevelTrace.setSelected(true);
		else if (log4j2Config.getLevel() == Level.DEBUG) 
			rdbtnLevelDebug.setSelected(true);
		else if (log4j2Config.getLevel() == Level.INFO) 
			rdbtnLevelInfo.setSelected(true);
		else if (log4j2Config.getLevel() == Level.WARN) 
			rdbtnLevelWarn.setSelected(true);
		else if (log4j2Config.getLevel() == Level.ERROR) 
			rdbtnLevelError.setSelected(true);
		else if (log4j2Config.getLevel() == Level.FATAL) 
			rdbtnLevelFatal.setSelected(true);
		else  
			rdbtnLevelOff.setSelected(true);
	}
	
	public void setCheckBoxLogger() {
		chckbxConsole.setSelected(log4j2Config.isLoggerConsole());
		chckbxFile.setSelected(log4j2Config.isLoggerLogFile());

	}
}
