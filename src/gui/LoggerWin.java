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
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import gui.info.InfoWin;

import javax.swing.JRadioButton;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;


public class LoggerWin extends JFrame {

	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	private static final long serialVersionUID = 1L;
	/* 	----------------------------------------
	 * 		INSTANCE VAR
	 * ----------------------------------------*/
	private String logFileName;

	private String eMailFrom = "christian.klugesherz@gmail.com";
	private String eMailTo = "christian.klugesherz@gmail.com";


	// By default the logger will always be on Console, and with Level OFF !
	// And only Root AppenderRef 
	// Be care, .xml file are composed of severals AppenderRef, so, here we will not configure them
	// We use a Command line argument to activate either "Console .XML" or by default Console {root} {OFF}
	// The argument is : 
	// 		-Logger  	--> Here we used .XML
	//		without	 	--> Console OFF
	// By Default the logger will be OFF, to avoid to pollute the output !
	// In case we want to activate the LOGGER based on .xml, we have to use argument : -Logger  

	/* 	----------------------------------------
	 * 		WIN BUILDER
	 * ----------------------------------------*/

	private JPanel contentPane;
	private JTextField textFieldLoggerFilePath;
	private JRadioButton rdbtnLevelTrace;
	private JRadioButton rdbtnLevelDebug;
	private JRadioButton rdbtnLevelInfo;
	private JRadioButton rdbtnLevelError;
	private JRadioButton rdbtnLevelOff;
	private JTextField txtPactoollog;
	private JPanel panel;
	private JRadioButton rdbtnFile;
	private JRadioButton rdbtnConsoleXml;
	private JButton btnDebug;
	private JRadioButton rdbtnLevelWarn;
	private JLabel lblConfigToBeApplied;
	private ButtonGroup groupLevel;
	private JRadioButton rdbtnConsoleOnlyRoot;
	private JLabel lblCurrentConfigApplied;
	private JLabel lblNewLabel_1;

	// -------------------------------------------------------
	// 						TEST
	// -------------------------------------------------------
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					boolean xmlConsoleLoggerActivated = false;
					for (int i= 0; i< args.length; i++) {
						System.out.println("Command line arguments: " + args[i] );
						if (args[i].equals("-Logger")) {
							System.out.println(".xml Console Logger will be activated");
							xmlConsoleLoggerActivated = true;	
						} else {
							System.out.println("Default Console Logger will be deactivated");
							xmlConsoleLoggerActivated = false;								
						}
					}
					LoggerWin frame = new LoggerWin(xmlConsoleLoggerActivated);
					frame.setVisible(true);
					frame.readConfigSetRadioButton();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// -------------------------------------------------------
	// 						CREATE FRAME
	// -------------------------------------------------------
	public LoggerWin(boolean consoleRootXMLActivated) {

		logFileName = null;

		if (consoleRootXMLActivated) {
			// Default
			// Will read the configuration from XML file
			// displayAppenderRefRootLevels();
		}	
		else {
			// We will remove all loggers, and set AppenderRef Root Logger to OFF
			removeAllLoggerExceptRoot();
			createConsoleAppenderRefInRootLogger(Level.OFF);
			//displayAppenderRefRootLevels();
		}

		setTitle("Pac-Tool Logger");
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoggerWin.class.getResource("/gui/images/PAC-Tool_16.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 560, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPathFile = new JLabel("Log File Path :");
		lblPathFile.setBounds(135, 25, 80, 14);
		contentPane.add(lblPathFile);

		textFieldLoggerFilePath = new JTextField();
		textFieldLoggerFilePath.setEditable(false);
		textFieldLoggerFilePath.setText(getUserAppDirectory());
		textFieldLoggerFilePath.setBounds(225, 22, 309, 20);
		contentPane.add(textFieldLoggerFilePath);
		textFieldLoggerFilePath.setColumns(10);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnClose.setBounds(464, 303, 70, 23);
		contentPane.add(btnClose);

		JPanel panelLevel = new JPanel();
		panelLevel.setBorder(new TitledBorder(null, "Level", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLevel.setBounds(24, 126, 476, 61);
		contentPane.add(panelLevel);
		panelLevel.setLayout(new GridLayout(0, 6, 0, 0));

		rdbtnLevelTrace = new JRadioButton("Trace   >");
		rdbtnLevelTrace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnLevelTrace.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelDebug.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelWarn.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelError.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelOff.setFont(new Font("Tahoma", Font.PLAIN, 11));
				setLabelConfigToBeApplied();
			}
		});
		panelLevel.add(rdbtnLevelTrace);

		rdbtnLevelDebug = new JRadioButton("Debug   >");
		rdbtnLevelDebug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnLevelTrace.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelDebug.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelWarn.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelError.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelOff.setFont(new Font("Tahoma", Font.PLAIN, 11));
				setLabelConfigToBeApplied();
			}
		});
		panelLevel.add(rdbtnLevelDebug);

		rdbtnLevelInfo = new JRadioButton("Info   >");
		rdbtnLevelInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnLevelTrace.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelDebug.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelWarn.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelError.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelOff.setFont(new Font("Tahoma", Font.PLAIN, 11));
				setLabelConfigToBeApplied();
			}
		});
		panelLevel.add(rdbtnLevelInfo);

		rdbtnLevelWarn = new JRadioButton("Warn   >");
		rdbtnLevelWarn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnLevelTrace.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelDebug.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelInfo.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelWarn.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelError.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelOff.setFont(new Font("Tahoma", Font.PLAIN, 11));
				setLabelConfigToBeApplied();
			}
		});
		panelLevel.add(rdbtnLevelWarn);

		rdbtnLevelError = new JRadioButton("Error   >");
		rdbtnLevelError.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnLevelTrace.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelDebug.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelInfo.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelWarn.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelError.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelOff.setFont(new Font("Tahoma", Font.PLAIN, 11));
				setLabelConfigToBeApplied();
			}
		});
		panelLevel.add(rdbtnLevelError);

		rdbtnLevelOff = new JRadioButton("Off");
		rdbtnLevelOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnLevelTrace.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelDebug.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelInfo.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelWarn.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelError.setFont(new Font("Tahoma", Font.ITALIC, 11));
				rdbtnLevelOff.setFont(new Font("Tahoma", Font.PLAIN, 11));
				setLabelConfigToBeApplied();
			}
		});
		panelLevel.add(rdbtnLevelOff);

		groupLevel = new ButtonGroup();
		groupLevel.add(rdbtnLevelTrace);
		groupLevel.add(rdbtnLevelDebug);
		groupLevel.add(rdbtnLevelInfo);
		groupLevel.add(rdbtnLevelError);
		groupLevel.add(rdbtnLevelWarn);
		groupLevel.add(rdbtnLevelOff);

		JLabel lblFileName = new JLabel("Log File Name :");
		lblFileName.setBounds(135, 56, 80, 14);
		contentPane.add(lblFileName);

		txtPactoollog = new JTextField();
		txtPactoollog.setEditable(false);
		txtPactoollog.setText("Pac-Tool.log");
		txtPactoollog.setBounds(222, 53, 109, 20);
		contentPane.add(txtPactoollog);
		txtPactoollog.setColumns(10);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Logger", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(22, 198, 351, 64);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 3, 0, 0));

		rdbtnFile = new JRadioButton("File");
		rdbtnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnLevelTrace.setEnabled(true);
				rdbtnLevelDebug.setEnabled(true);
				rdbtnLevelInfo.setEnabled(true);
				rdbtnLevelError.setEnabled(true);
				rdbtnLevelWarn.setEnabled(true);
				rdbtnLevelOff.setEnabled(true);
				setLabelConfigToBeApplied();
			}
		});
		panel.add(rdbtnFile);

		rdbtnConsoleXml = new JRadioButton("Console (.xml)");
		rdbtnConsoleXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnLevelTrace.setEnabled(false);
				rdbtnLevelDebug.setEnabled(false);
				rdbtnLevelInfo.setEnabled(false);
				rdbtnLevelError.setEnabled(false);
				rdbtnLevelWarn.setEnabled(false);
				rdbtnLevelOff.setEnabled(false);
				setLabelConfigToBeApplied();
			}
		});
		panel.add(rdbtnConsoleXml);

		rdbtnConsoleOnlyRoot = new JRadioButton("Console");
		rdbtnConsoleOnlyRoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnLevelTrace.setEnabled(true);
				rdbtnLevelDebug.setEnabled(true);
				rdbtnLevelInfo.setEnabled(true);
				rdbtnLevelError.setEnabled(true);
				rdbtnLevelWarn.setEnabled(true);
				rdbtnLevelOff.setEnabled(true);
				setLabelConfigToBeApplied();				
			}
		});
		panel.add(rdbtnConsoleOnlyRoot);

		ButtonGroup groupLog = new ButtonGroup();
		groupLog.add(rdbtnFile);
		groupLog.add(rdbtnConsoleXml);
		groupLog.add(rdbtnConsoleOnlyRoot);

		JButton btnApply = new JButton("Apply Config.");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rdbtnFile.isSelected()) {
					// Will create the File Appender in <Appenders>
					removeAllLoggerExceptRoot();
					logFileName = createFileAppender();

					Level vlevel = Level.OFF;
					if (rdbtnLevelTrace.isSelected()) 
						vlevel = Level.TRACE;
					else if (rdbtnLevelDebug.isSelected()) 
						vlevel = Level.DEBUG;
					else if (rdbtnLevelInfo.isSelected()) 
						vlevel = Level.INFO;
					else if (rdbtnLevelError.isSelected()) 
						vlevel = Level.ERROR;
					else if (rdbtnLevelWarn.isSelected()) 
						vlevel = Level.WARN;
					else if (rdbtnLevelOff.isSelected()) 
						vlevel = Level.OFF;
					else  
						logger.debug("Error wrong Level");

					createFileAppenderRefInRootLogger(vlevel);

				} else if (rdbtnConsoleOnlyRoot.isSelected()) {
					// Will only use the root appenderRef in console
					removeAllLoggerExceptRoot();

					Level vlevel = Level.OFF;
					if (rdbtnLevelTrace.isSelected()) 
						vlevel = Level.TRACE;
					else if (rdbtnLevelDebug.isSelected()) 
						vlevel = Level.DEBUG;
					else if (rdbtnLevelInfo.isSelected()) 
						vlevel = Level.INFO;
					else if (rdbtnLevelError.isSelected()) 
						vlevel = Level.ERROR;
					else if (rdbtnLevelWarn.isSelected()) 
						vlevel = Level.WARN;
					else if (rdbtnLevelOff.isSelected()) 
						vlevel = Level.OFF;
					else  
						logger.debug("Error wrong Level");
					createConsoleAppenderRefInRootLogger(vlevel);

				} else {
					// Will Read the XML file
					((org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false)).reconfigure();
				}
				readConfigSetRadioButton();
				lblConfigToBeApplied.setText("");
			}
		});
		btnApply.setBounds(401, 198, 118, 23);
		contentPane.add(btnApply);

		btnDebug = new JButton("Test");
		btnDebug.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnDebug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.trace("Test Trace");
				logger.debug("Test Debug");
				logger.info("Test Info");
				logger.warn("Test Warn");
				logger.error("Test Error");
			}
		});
		btnDebug.setBounds(312, 75, 51, 20);
		contentPane.add(btnDebug);

		JLabel lblNewLabel = new JLabel("<html>Keep the file closed during loging !<br>\r\nEach new run of Pac-Tool will delete the Log File !\r\n<html>");
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNewLabel.setBounds(135, 79, 239, 36);
		contentPane.add(lblNewLabel);

		JButton btnMail = new JButton("eMail Issue");
		btnMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InfoWin window3 = new InfoWin("Issue","/gui/info/IssueEmail.html",800);
				window3.setVisible(true);
				//sendEmail();
			}
		});
		btnMail.setBounds(401, 74, 118, 23);
		contentPane.add(btnMail);

		lblConfigToBeApplied = new JLabel("");
		lblConfigToBeApplied.setVerticalAlignment(SwingConstants.TOP);
		lblConfigToBeApplied.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblConfigToBeApplied.setBounds(244, 273, 210, 70);
		contentPane.add(lblConfigToBeApplied);

		lblCurrentConfigApplied = new JLabel("New label");
		lblCurrentConfigApplied.setVerticalAlignment(SwingConstants.TOP);
		lblCurrentConfigApplied.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblCurrentConfigApplied.setBounds(22, 273, 210, 70);
		contentPane.add(lblCurrentConfigApplied);

		// As console is activated by default, we have to disable the Level buttons
		rdbtnLevelTrace.setEnabled(false);
		rdbtnLevelDebug.setEnabled(false);
		rdbtnLevelInfo.setEnabled(false);
		rdbtnLevelError.setEnabled(false);
		rdbtnLevelWarn.setEnabled(false);
		rdbtnLevelOff.setEnabled(false);

		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(LoggerWin.class.getResource("/gui/images/Logger.png")));
		lblNewLabel_1.setBounds(23, 23, 80, 60);
		contentPane.add(lblNewLabel_1);
		
		JButton buttonCleanLogFile = new JButton("Clean Log File");
		buttonCleanLogFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		buttonCleanLogFile.setBounds(401, 232, 118, 23);
		contentPane.add(buttonCleanLogFile);

		//setLabelConfigToBeApplied();

	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------


	public String getSelectedButtonText(ButtonGroup buttonGroup) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				System.out.println(button.getText());
				return button.getText();
			}
		}
		return null;

	}

	private void setLabelConfigToBeApplied() {

		String tmp = "<html>";
		tmp = tmp + "Preparated Configuration <br>";	

		if (rdbtnFile.isSelected()) { 
			tmp = tmp + "Logger: {root} <br> Root AppenderRef: {"; 
			tmp = tmp + "File} ";
			tmp = tmp + "Level:  {";
			if (rdbtnLevelTrace.isSelected()) 
				tmp = tmp + "TRACE}";
			else if (rdbtnLevelDebug.isSelected()) 
				tmp = tmp + "DEBUG}";
			else if (rdbtnLevelInfo.isSelected()) 
				tmp = tmp + "INFO}";
			else if (rdbtnLevelError.isSelected()) 
				tmp = tmp + "ERROR}";
			else if (rdbtnLevelWarn.isSelected()) 
				tmp = tmp + "WARN}";
			else if (rdbtnLevelOff.isSelected()) 
				tmp = tmp + "OFF}";
			else  
				logger.debug("Error wrong Level");
			tmp = tmp + "<br>";
		} else if (rdbtnConsoleOnlyRoot.isSelected()) {
			tmp = tmp + "Logger: {root} <br> Root AppenderRef: {"; 
			tmp = tmp + "Console} ";
			tmp = tmp + "Level:  {";
			if (rdbtnLevelTrace.isSelected()) 
				tmp = tmp + "TRACE}";
			else if (rdbtnLevelDebug.isSelected()) 
				tmp = tmp + "DEBUG}";
			else if (rdbtnLevelInfo.isSelected()) 
				tmp = tmp + "INFO}";
			else if (rdbtnLevelError.isSelected()) 
				tmp = tmp + "ERROR}";
			else if (rdbtnLevelWarn.isSelected()) 
				tmp = tmp + "WARN}";
			else if (rdbtnLevelOff.isSelected()) 
				tmp = tmp + "OFF}";
			else  
				logger.debug("Error wrong Level");
			tmp = tmp + "<br>";
		} 	else {
			tmp = tmp + "Logger: {.xml} <br> Root AppenderRef: {"; 
			tmp = tmp + "Console} ";
			tmp = tmp + "Level: ";
			tmp = tmp + "{.xml}";
		}
		tmp = tmp + "</html>";
		lblConfigToBeApplied.setText(tmp);
	}

	public void readConfigSetRadioButton() {

		readConfigSetRadioButtonLogLevel();
		readConfigSetRadioButtonLogger();
		String tmp = "<html>";
		tmp = tmp + "Current Configuration <br>";
		tmp = tmp + displayAllDeclaredLoggers() + "<br>";
		tmp = tmp + displayAppenderRefRootLevels();
		tmp = tmp + "</html>";
		lblCurrentConfigApplied.setText(tmp);
	}

	public void readConfigSetRadioButtonLogLevel() {

		if (getUniqueAppenderRefRootName() == "File" ) {
			if (getFileAppenderRefRootLevel() == Level.TRACE) {
				rdbtnLevelTrace.setSelected(true);
			}
			else if (getFileAppenderRefRootLevel() == Level.DEBUG) {
				rdbtnLevelDebug.setSelected(true);
			}
			else if (getFileAppenderRefRootLevel() == Level.INFO) {
				rdbtnLevelInfo.setSelected(true);
			}
			else if (getFileAppenderRefRootLevel() == Level.WARN) {
				rdbtnLevelWarn.setSelected(true);
			}
			else if (getFileAppenderRefRootLevel() == Level.ERROR) {
				rdbtnLevelError.setSelected(true);
			}
			else if (getFileAppenderRefRootLevel() == Level.OFF) {
				rdbtnLevelOff.setSelected(true);
			}
			else  
				logger.error("(readConfigSetRadioButtonLogLevel):: Wrong File level value");
		} else if (getUniqueAppenderRefRootName() == "Console" ) {
			if (getConsoleAppenderRefRootLevel() == Level.TRACE) {
				rdbtnLevelTrace.setSelected(true);
				rdbtnLevelTrace.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelDebug.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelWarn.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelError.setFont(new Font("Tahoma", Font.PLAIN, 11));
				rdbtnLevelOff.setFont(new Font("Tahoma", Font.PLAIN, 11));

			}
			else if (getConsoleAppenderRefRootLevel() == Level.DEBUG) {
				rdbtnLevelDebug.setSelected(true);
			}
			else if (getConsoleAppenderRefRootLevel() == Level.INFO) {
				rdbtnLevelInfo.setSelected(true);
			}
			else if (getConsoleAppenderRefRootLevel() == Level.ERROR) {
				rdbtnLevelError.setSelected(true);
			}
			else if (getConsoleAppenderRefRootLevel() == Level.WARN) {
				rdbtnLevelWarn.setSelected(true);
			}
			else if (getConsoleAppenderRefRootLevel() == Level.OFF) {
				rdbtnLevelOff.setSelected(true);
			}
			else { 
				logger.error("(readConfigSetRadioButtonLogLevel):: Wrong Console level value");
			}
		} else {
			logger.error("(readConfigSetRadioButtonLogLevel):: Wrong Logger value");
		}
	}

	public void readConfigSetRadioButtonLogger() {
		if (getUniqueAppenderRefRootName() == "File" )
			rdbtnFile.setSelected(true);
		else if (getUniqueAppenderRefRootName() == "Console" ) 
			// Only XML will have several Loggers !!
			if (displayAllDeclaredLoggers().length() > 17)
				rdbtnConsoleXml.setSelected(true);
			else
				rdbtnConsoleOnlyRoot.setSelected(true);
		else
			logger.error("(readConfigSetRadioButtonLogger):: Wrong Logger value");
	}


	// -------------------------------------------------------------------------------------------
	// 										Miscellaneous
	// -------------------------------------------------------------------------------------------
	/**
	 * getUserAppDirectory
	 * Return the user "application" directory where le Log file will be stored
	 * @return
	 */
	private String getUserAppDirectory() {
		String workingDirectory;
		String OS = (System.getProperty("os.name")).toUpperCase();	
		if (OS.contains("WIN"))	{
			workingDirectory = System.getenv("AppData");
		}
		else {
			workingDirectory = System.getProperty("user.home");
			//if we are on a MAC, we are not done, we look for "Application Support"
			workingDirectory += "/Library/Application Support";
		}
		return workingDirectory;
	}

	// -------------------------------------------------------------------------------------------
	// 								Log4j2 Structure and principle !
	// -------------------------------------------------------------------------------------------
	/*
		The approach is quite difficult because we have to consider following structure
		(see below)
		and also that in "LoggerConfig" : 
			Appender and AppenderRefs must be linked !
			loggerConfig.getAppenders() --> Will return the Appenders in "Config". For me this is a BUG

			---"Config"---
					Appenders
							Appender(0)
								Console
							Appender(1)
								File
						LoggerConfigs
							-- LoggerConfig(0) 
							-- LoggerConfig(1)
							-- LoggerConfig(2)

			----"LoggerConfig"----
					- AppenderRefs
						-- AppenderRef(0)
							-- Name Console
							-- Level : DEBUG
					- Appenders
						-- Appender(0)
							-- Name Console
							-- Level : DEBUG
					- Level -- ALL

	 */
	// -------------------------------------------------------------------------------------------

	// -------------------------------------------------------------------------------------------
	// 										Logger in "Config"
	// -------------------------------------------------------------------------------------------

	/**
	 * Will remove all Logger in <Loggers> except Root Logger
	 */
	@SuppressWarnings("resource")
	public void removeAllLoggerExceptRoot() {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);

		Configuration config = ctx.getConfiguration();

		config.removeLogger("coolant");
		config.removeLogger("gui");
		config.removeLogger("mpoints");
		config.removeLogger("pac");
		config.removeLogger("refrigerant");

		ctx.updateLoggers();  
	}

	/**
	 * Will return all the Loggers 
	 * @return
	 */
	@SuppressWarnings({ "resource", "unused" })
	private Map<String, LoggerConfig> getAllDeclaredLoggers() {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		return( config.getLoggers() );
	}

	/**
	 * Will Display all the Loggers 
	 * @return
	 */
	@SuppressWarnings("resource")
	private String displayAllDeclaredLoggers() {
		String out="";
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		logger.info("All Declared Loggers {}", config.getLoggers().toString());
		out = "Loggers :"+ config.getLoggers().toString();
		return out;
	}


	// -------------------------------------------------------------------------------------------
	// 									Appenders in "Config"
	// -------------------------------------------------------------------------------------------

	/**
	 * Will create the File Appender in <Appenders>
	 * 
	 * 		<File name="File" fileName="${logPath}/pac-tool.log" append="false">
	 *				<PatternLayout
	 *				pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n" />
	 *		</File>
	 */
	@SuppressWarnings("resource")
	private String createFileAppender() {
		String logPath;
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);

		Configuration config = ctx.getConfiguration();

		logPath = Paths.get(getUserAppDirectory()+"/Pac-Tool").toString();

		// --- create the PatternLayout
		//<PatternLayout
		//pattern="%d{yyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n" />
		Layout<? extends Serializable> layout = PatternLayout.newBuilder()
				.withConfiguration(config)
				.withPattern("%d{yyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n")
				.build();

		// --- create the Appender
		//<File name="LogFile" fileName="./Pac-Tool.log"
		//		immediateFlush="true" append="false">
		//		<PatternLayout
		//			pattern="%d{yyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n" />
		//	</File>
		Appender appender  = FileAppender.newBuilder()
				.setConfiguration(config)
				.withName("File")
				.withFileName(logPath + "/Pac-Tool.log")
				.withAppend(false)
				.withImmediateFlush(true)
				.withLayout(layout)
				.build();

		appender.start();
		config.addAppender(appender);

		return logPath;

	}


	/**
	 * Will return all the appenders 
	 * @return
	 */
	@SuppressWarnings({ "resource", "unused" })
	private Map<String, Appender> getAllDeclaredAppenders() {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		return( config.getAppenders() );
	}

	// -------------------------------------------------------------------------------------------
	// 								Appender and AppenderRef in "LoggerConfig"
	//								(Appender and AppenderRefs must be linked)
	// -------------------------------------------------------------------------------------------


	/**   Add File appender in Root Logger
	 * The Root Level Will be "ALL"
	 * 
	 * before
	 *			<Root level=xxx>
	 *				<AppenderRef ref="Console" level=.... />
	 *			</Root>
	 *	
	 * After
	 *			<Root level="ALL">
	 *				<AppenderRef ref="File" level=fileLevel />
	 *			</Root>
	 * 
	 * https://stackify.com/log4j2-java/
	 */
	@SuppressWarnings("resource")
	private void createFileAppenderRefInRootLogger(Level fileLevel) {

		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config= ctx.getConfiguration();

		// The error done here was to get the loggerConfig !!
		//	--> We have to create a new one
		// LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

		// Second Appender and AppenderRefs must be linked !!
		AppenderRef ref2 = AppenderRef.createAppenderRef("File", fileLevel, null);
		AppenderRef[] refs = new AppenderRef[] { ref2  };

		LoggerConfig loggerConfig  = LoggerConfig.createLogger(false, Level.ALL, LogManager.ROOT_LOGGER_NAME, "true", refs, null, config, null);

		loggerConfig.addAppender(config.getAppender("File"), fileLevel, null);

		// Important to remove the root logger !
		config.removeLogger(LogManager.ROOT_LOGGER_NAME);
		config.addLogger(LogManager.ROOT_LOGGER_NAME, loggerConfig);

		ctx.updateLoggers();  
	}


	@SuppressWarnings("resource")
	public void createConsoleAppenderRefInRootLogger(Level clevel) {

		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config= ctx.getConfiguration();

		// The error done here was to get the loggerConfig !!
		//	--> We have to create a new one
		// LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

		// Second Appender and AppenderRefs must be linked !!
		AppenderRef ref1 = AppenderRef.createAppenderRef("Console", clevel, null);
		AppenderRef[] refs = new AppenderRef[] { ref1  };

		LoggerConfig loggerConfig  = LoggerConfig.createLogger(false, Level.ALL, LogManager.ROOT_LOGGER_NAME, "true", refs, null, config, null);

		loggerConfig.addAppender(config.getAppender("Console"), clevel, null);

		// Important to remove the root logger !
		config.removeLogger(LogManager.ROOT_LOGGER_NAME);
		config.addLogger(LogManager.ROOT_LOGGER_NAME, loggerConfig);

		ctx.updateLoggers();  
	}

	// -------------------------------------------------------------------------------------------
	// 								AppenderRef Levels in "LoggerConfig"
	// -------------------------------------------------------------------------------------------

	/**
	 * displayAppenderRefRootLevels()
	 * Will display 	Console -> DEBUG
	 * 					File	-> INFO
	 * 	<Root level="ALL">
	 *		<AppenderRef ref="Console" level="DEBUG" />
	 * 		<AppenderRef ref="File" level="INFO" />
	 *	</Root>
	 *
	 */
	@SuppressWarnings("resource")
	private String displayAppenderRefRootLevels () {
		String out;
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

		out = "Root AppenderRef: ";
		for (AppenderRef ref : loggerConfig.getAppenderRefs()) {
			logger.info("AppenderRef: {}, level: {}", ref.getRef(), ref.getLevel().toString());
			System.out.printf("(P) AppenderRef: %s, level: %s \n",  ref.getRef(), ref.getLevel().toString());
			out = out + "{" + ref.getRef() + "} level: {" + ref.getLevel().toString() + "}";
		}

		logger.info("	--> LoggerConfig Level = {}",loggerConfig.getLevel());
		System.out.printf("(P) --> LoggerConfig Level =  %s\n ", loggerConfig.getLevel());


		// -----------------------------------------------------------------------
		// Remark !
		// With the following command, we will get the appenders of "Config" !
		// loggerConfig.getAppenders();
		// -----------------------------------------------------------------------

		return(out);

	}


	/**
	 * getConsoleAppenderRefRootLevel()
	 * Will return  DEBUG
	 * 	<Root level="ALL">
	 *		<AppenderRef ref="Console" level="DEBUG" />
	 * 		<AppenderRef ref="File" level="INFO" />
	 *	</Root>
	 *
	 */
	@SuppressWarnings({ "resource"})
	private Level getConsoleAppenderRefRootLevel () {
		Level vlevel = null;

		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

		for (AppenderRef ref : loggerConfig.getAppenderRefs()) {
			if (ref.getRef().toString().equals("Console"))
				vlevel = ref.getLevel();
		}
		return vlevel;
	}

	/**
	 * getFileAppenderRefRootLevel()
	 * Will return  INFO
	 * 	<Root level="ALL">
	 *		<AppenderRef ref="Console" level="DEBUG" />
	 * 		<AppenderRef ref="File" level="INFO" />
	 *	</Root>
	 *
	 */
	@SuppressWarnings({ "resource" })
	private Level getFileAppenderRefRootLevel () {
		Level vlevel = null;

		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

		for (AppenderRef ref : loggerConfig.getAppenderRefs()) {
			if (ref.getRef().toString().equals("File"))
				vlevel = ref.getLevel();
		}
		return vlevel;
	}

	/**
	 * getUniqueAppenderRefRootName()
	 * Will return the only one activate AppenderRef: in following case: Console
	 * 	<Root level="ALL">
	 *		<AppenderRef ref="Console" level="DEBUG" />
	 *	</Root>
	 *
	 */
	@SuppressWarnings({ "resource"})
	private String getUniqueAppenderRefRootName () {
		String name = null;

		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

		for (AppenderRef ref : loggerConfig.getAppenderRefs()) {
			if (ref.getRef().toString().equals("File") )
				name = "File";
			else if (ref.getRef().toString().equals("Console") )
				name = "Console";
			else
				name = "Wrong";

		}
		return name;
	}

	// -------------------------------------------------------------------------------------------
	//									Logger Level in "LoggerConfig"
	// -------------------------------------------------------------------------------------------

	/**
	 * displayLoggerRootLevel()
	 * Will display 	Root Level -> ALL
	 * 					
	 * 	<Root level="ALL">
	 *		<AppenderRef ref="Console" level="DEBUG" />
	 * 		<AppenderRef ref="File" level="INFO" />
	 *	</Root>
	 *
	 */
	@SuppressWarnings({ "resource", "unused" })
	private String displayLoggerRootLevel () {
		String out="";

		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

		logger.info("Logger Root = level: {}", loggerConfig.getLevel());
		System.out.printf("(P) Logger Root = level: %s\n", loggerConfig.getLevel());
		out = "Logger Root Level:" + loggerConfig.getLevel();
		return(out);
	}


	/**
	 * getLoggerLevel(LogManager.ROOT_LOGGER_NAME)
	 * Will get the Logger Level
	 * Example:
	 *  Will return ALL
	 * 					
	 * 	<Root level="ALL">
	 *		<AppenderRef ref="Console" level="DEBUG" />
	 * 		<AppenderRef ref="File" level="INFO" />
	 *	</Root>

	 * @param loggerName
	 * @return Level
	 */
	@SuppressWarnings({ "resource", "unused" })
	private Level getLoggerLevel (String loggerName) {

		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config= ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(loggerName); 
		return loggerConfig.getLevel();
	}


	/**
	 * setLoggerLevel(LogManager.ROOT_LOGGER_NAME, Level)
	 * Will set the Logger Level
	 * Example:
	 *  Will set ALL --> INFO
	 * 					
	 * 	<Root level="ALL">
	 *		<AppenderRef ref="Console" level="DEBUG" />
	 * 		<AppenderRef ref="File" level="INFO" />
	 *	</Root>
	 * 
	 * 
	 * @param loggerName
	 * @param vlevel
	 */
	@SuppressWarnings({ "resource", "unused" })
	private void setLoggerLevel(String loggerName, Level vlevel) {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config= ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(loggerName); 
		loggerConfig.setLevel(vlevel);
		ctx.updateLoggers();  
	}


	// https://kodejava.org/how-do-i-send-an-email-with-attachment/
	public void sendEmail() {
		// Defines the E-Mail information.
		String from = eMailFrom;
		String to = eMailTo;
		String subject = "Pac-Tool New Issue Discovered";
		String bodyText = "Bonjour <br> Je viens de trouver un nouveau problème avec Pac-Tool,<br>Description ";

		// The attachment file name.
		String attachmentName = logFileName;

		// Creates a Session with the following properties.
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587 ");
		Session session = Session.getDefaultInstance(props);

		try {
			InternetAddress fromAddress = new InternetAddress(from);
			InternetAddress toAddress = new InternetAddress(to);

			// Create an Internet mail msg.
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(fromAddress);
			msg.setRecipient(Message.RecipientType.TO, toAddress);
			msg.setSubject(subject);
			msg.setSentDate(new Date());

			// Set the email msg text.
			MimeBodyPart messagePart = new MimeBodyPart();
			messagePart.setText(bodyText);

			// Set the email attachment file (if exist)
			Path file = Paths.get(logFileName);

			// Create Multipart E-Mail.
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messagePart);

			if(Files.exists(file)){
				FileDataSource fileDataSource = new FileDataSource(attachmentName);

				MimeBodyPart attachmentPart = new MimeBodyPart();
				attachmentPart.setDataHandler(new DataHandler(fileDataSource));
				attachmentPart.setFileName(fileDataSource.getName());
				multipart.addBodyPart(attachmentPart);
			}

			msg.setContent(multipart);

			// Send the msg. Don't forget to set the username and password
			// to authenticate to the mail server.
			Transport.send(msg, "christian.klugesherz@gmail.com", "");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
