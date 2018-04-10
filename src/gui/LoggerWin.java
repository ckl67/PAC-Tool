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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.Map;
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
import javax.swing.JRadioButton;
import java.awt.GridLayout;
import java.awt.Font;


public class LoggerWin extends JFrame {

	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	private static final long serialVersionUID = 1L;
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
	private JRadioButton rdbtnConsole;
	private JButton btnDebug;

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
					LoggerWin frame = new LoggerWin();
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
	public LoggerWin() {
	
		setTitle("Pac-Tool Logger");
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoggerWin.class.getResource("/gui/images/PAC-Tool_16.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 507, 311);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPathFile = new JLabel("Log File Path :");
		lblPathFile.setBounds(22, 26, 102, 14);
		contentPane.add(lblPathFile);

		textFieldLoggerFilePath = new JTextField();
		textFieldLoggerFilePath.setEditable(false);
		textFieldLoggerFilePath.setText(getUserAppDirectory());
		textFieldLoggerFilePath.setBounds(134, 23, 304, 20);
		contentPane.add(textFieldLoggerFilePath);
		textFieldLoggerFilePath.setColumns(10);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnClose.setBounds(375, 228, 89, 23);
		contentPane.add(btnClose);

		JPanel panelLevel = new JPanel();
		panelLevel.setBorder(new TitledBorder(null, "Level", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLevel.setBounds(24, 126, 440, 61);
		contentPane.add(panelLevel);
		panelLevel.setLayout(new GridLayout(0, 5, 0, 0));

		rdbtnLevelTrace = new JRadioButton("Trace   >");
		panelLevel.add(rdbtnLevelTrace);

		rdbtnLevelDebug = new JRadioButton("Debug   >");
		panelLevel.add(rdbtnLevelDebug);

		rdbtnLevelInfo = new JRadioButton("Info   >");
		panelLevel.add(rdbtnLevelInfo);

		rdbtnLevelError = new JRadioButton("Error   >");
		panelLevel.add(rdbtnLevelError);

		rdbtnLevelOff = new JRadioButton("Off");
		panelLevel.add(rdbtnLevelOff);

		ButtonGroup groupLevel = new ButtonGroup();
		groupLevel.add(rdbtnLevelTrace);
		groupLevel.add(rdbtnLevelDebug);
		groupLevel.add(rdbtnLevelInfo);
		groupLevel.add(rdbtnLevelError);
		groupLevel.add(rdbtnLevelOff);

		JLabel lblFileName = new JLabel("Log File Name :");
		lblFileName.setBounds(22, 62, 102, 14);
		contentPane.add(lblFileName);

		txtPactoollog = new JTextField();
		txtPactoollog.setEditable(false);
		txtPactoollog.setText("Pac-Tool.log");
		txtPactoollog.setBounds(134, 54, 86, 20);
		contentPane.add(txtPactoollog);
		txtPactoollog.setColumns(10);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Logger", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(22, 198, 219, 64);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		rdbtnFile = new JRadioButton("File");
		panel.add(rdbtnFile);

		rdbtnConsole = new JRadioButton("Console (.xml)");
		panel.add(rdbtnConsole);

		ButtonGroup groupLog = new ButtonGroup();
		groupLog.add(rdbtnFile);
		groupLog.add(rdbtnConsole);

		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (rdbtnFile.isSelected()) {
					// Will create the File Appender in <Appenders>
					removeAllLoggerExceptRoot();
					createFileAppender();

					Level vlevel = Level.OFF;
					if (rdbtnLevelTrace.isSelected()) 
						vlevel = Level.TRACE;
					else if (rdbtnLevelDebug.isSelected()) 
						vlevel = Level.DEBUG;
					else if (rdbtnLevelInfo.isSelected()) 
						vlevel = Level.INFO;
					else if (rdbtnLevelError.isSelected()) 
						vlevel = Level.ERROR;
					else if (rdbtnLevelOff.isSelected()) 
						vlevel = Level.OFF;
					else  
						logger.debug("Error wrong Level");

					createFileAppenderRefInRootLogger(vlevel);

				} else {
					// Will Read the XML file
					((org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false)).reconfigure();
					setRadioButtonLogLevel();
					displayAllDeclaredLoggers();
					displayAppenderRefRootLevels();
				}
			}
		});
		btnApply.setBounds(265, 228, 89, 23);
		contentPane.add(btnApply);

		btnDebug = new JButton("Test");
		btnDebug.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnDebug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.fatal("---------------------------");
				logger.trace("Test Trace");
				logger.debug("Test Debug");
				logger.info("Test Info");
				logger.error("Test Error");
			}
		});
		btnDebug.setBounds(375, 86, 51, 20);
		contentPane.add(btnDebug);
		
		JLabel lblNewLabel = new JLabel("<html>Keep the file closed during loging !<br>\r\nEach new run of Pac-Tool will clean the Log File !\r\n<html>");
		lblNewLabel.setBounds(134, 85, 239, 36);
		contentPane.add(lblNewLabel);

		setRadioButtonLogger();
		setRadioButtonLogLevel();
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public void setRadioButtonLogLevel() {

		if (getUniqueAppenderRefRootName() == "File" ) {
			if (getFileAppenderRefRootLevel() == Level.TRACE) 
				rdbtnLevelTrace.setSelected(true);
			else if (getFileAppenderRefRootLevel() == Level.DEBUG) 
				rdbtnLevelDebug.setSelected(true);
			else if (getFileAppenderRefRootLevel() == Level.INFO) 
				rdbtnLevelInfo.setSelected(true);
			else if (getFileAppenderRefRootLevel() == Level.ERROR) 
				rdbtnLevelError.setSelected(true);
			else if (getFileAppenderRefRootLevel() == Level.OFF) 
				rdbtnLevelOff.setSelected(true);
			else  
				logger.error("(setRadioButtonLogLevel):: Wrong File level value");
		} else if (getUniqueAppenderRefRootName() == "Console" ) {

			if (getConsoleAppenderRefRootLevel() == Level.TRACE) 
				rdbtnLevelTrace.setSelected(true);
			else if (getConsoleAppenderRefRootLevel() == Level.DEBUG) 
				rdbtnLevelDebug.setSelected(true);
			else if (getConsoleAppenderRefRootLevel() == Level.INFO) 
				rdbtnLevelInfo.setSelected(true);
			else if (getConsoleAppenderRefRootLevel() == Level.ERROR) 
				rdbtnLevelError.setSelected(true);
			else if (getConsoleAppenderRefRootLevel() == Level.OFF) 
				rdbtnLevelOff.setSelected(true);
			else  
				logger.error("(setRadioButtonLogLevel):: Wrong Console level value");
		} else {
			logger.error("(setRadioButtonLogLevel):: Wrong Logger value");
		}
	}

	public void setRadioButtonLogger() {
		if (getUniqueAppenderRefRootName() == "File" )
			rdbtnFile.setSelected(true);
		else if (getUniqueAppenderRefRootName() == "Console" ) 
			rdbtnConsole.setSelected(true);
		else
			logger.error("(setRadioButtonLogger):: Wrong Logger value");
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
	@SuppressWarnings("resource")
	public Map<String, LoggerConfig> getAllDeclaredLoggers() {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		return( config.getLoggers() );
	}

	/**
	 * Will Display all the Loggers 
	 * @return
	 */
	@SuppressWarnings("resource")
	public void displayAllDeclaredLoggers() {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		logger.info("All Declared Loggers {}", config.getLoggers().toString());
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
	public void createFileAppender() {
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
	}


	/**
	 * Will return all the appenders 
	 * @return
	 */
	@SuppressWarnings("resource")
	public Map<String, Appender> getAllDeclaredAppenders() {
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
	public void createFileAppenderRefInRootLogger(Level fileLevel) {

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
	public void displayAppenderRefRootLevels () {

		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

		for (AppenderRef ref : loggerConfig.getAppenderRefs()) {
			logger.info("AppenderRef: {}, level: {}", ref.getRef(), ref.getLevel().toString());
		}

		logger.info("	--> LoggerConfig Level = {}",loggerConfig.getLevel());

		// -----------------------------------------------------------------------
		// Remark !
		// With the following command, we will get the appenders of "Config" !
		// loggerConfig.getAppenders();
		// -----------------------------------------------------------------------

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
	public Level getConsoleAppenderRefRootLevel () {
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
	public Level getFileAppenderRefRootLevel () {
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
	public String getUniqueAppenderRefRootName () {
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
	@SuppressWarnings("resource")
	public void displayLoggerRootLevel () {

		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

		logger.info("Logger Root = level: {}", loggerConfig.getLevel());

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
	@SuppressWarnings("resource")
	public Level getLoggerLevel (String loggerName) {

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
	@SuppressWarnings("resource")
	public void setLoggerLevel(String loggerName, Level vlevel) {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config= ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(loggerName); 
		loggerConfig.setLevel(vlevel);
		ctx.updateLoggers();  
	}
}
