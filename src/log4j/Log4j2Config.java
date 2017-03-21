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
package log4j;

import java.io.Serializable;
import java.nio.file.Paths;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;


public class Log4j2Config {

	private static final Logger logger = LogManager.getLogger(Log4j2Config.class.getName());
	private String logPath;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Log4j2Config() {
		logPath = Paths.get(getUserAppDirectory()+"/Pac-Tool").toString();
		//System.setProperty("logpath.name",logPath);

		@SuppressWarnings("resource")
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config= ctx.getConfiguration();

		// --- create the PatternLayout
		//<PatternLayout
		//pattern="%d{yyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n" />
		Layout<? extends Serializable> layout = PatternLayout.newBuilder()
				.withPattern("%d{yyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n")
				.build();

		// --- create the Appender
		//<File name="LogFile" fileName="./Pac-Tool.log"
		//		immediateFlush="false" append="false">
		//		<PatternLayout
		//			pattern="%d{yyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n" />
		//	</File>
		Appender appender  = FileAppender.newBuilder()
				.withName("LogFile")
				.withFileName(logPath + "/Pac-Tool.log")
				.withAppend(false)
				.withImmediateFlush(false)
				.withLayout(layout)
				.build();

		appender.start();
		config.addAppender(appender);
	}

	// -------------------------------------------------------
	// 							TEST
	// -------------------------------------------------------

	public static void main(String[] args) {

		// Initialize the Logger
		Log4j2Config log4j2Config = new Log4j2Config();
		System.out.println("--------- Path of the Log File : " + log4j2Config.getLogPath());
		System.out.println("--------- All Declared Appenders " + log4j2Config.getAllDeclaredAppenders().toString());
		System.out.println("Read the Appenders Activate in the Logger");
		System.out.println("-------- Is Logger Console active --> " + log4j2Config.isLoggerConsole());
		System.out.println("-------- Is Logger File active --> " + log4j2Config.isLoggerLogFile());

		// Create the Logger
		// The logger must be declared afterwards the initialization
		//		Logger logger = LogManager.getLogger(Log4j2Config.class.getName());

		// Apply the level specified in log4j2.xml
		System.out.println("--------- Log Level (default in .xml) = " + log4j2Config.getLevel());
		logger.error("This is Logger for 1 Error");
		logger.info("This is Logger for 1 Info");
		logger.debug("This is Logger for 1 Debug");
		logger.trace("This is Logger for 1 Trace");


		// Read the Appenders Activate in the Logger
		System.out.println("Read the Appenders Activate in the Logger");
		System.out.println("-------- Is Logger Console active --> " + log4j2Config.isLoggerConsole());
		System.out.println("-------- Is Logger File active --> " + log4j2Config.isLoggerLogFile());


		System.out.println("Remove Console Logger");
		log4j2Config.activeLoggerConsole(false);
		System.out.println("-------- Is Logger Console active --> " + log4j2Config.isLoggerConsole());
		System.out.println("-------- Is Logger File active --> " + log4j2Config.isLoggerLogFile());

		logger.error("This is Logger 2 Error");
		logger.info("This is Logger 2 Info");
		logger.debug("This is Logger 2 Debug");
		logger.trace("This is Logger 2 Trace");

		System.out.println("Add Console Logger");
		log4j2Config.activeLoggerConsole(true);
		System.out.println("-------- Is Logger Console active --> " + log4j2Config.isLoggerConsole());
		System.out.println("-------- Is Logger File active --> " + log4j2Config.isLoggerLogFile());

		System.out.println("Add File Logger");
		log4j2Config.activeLoggerLogFile(true);
		System.out.println("-------- Is Logger Console active --> " + log4j2Config.isLoggerConsole());
		System.out.println("-------- Is Logger File active --> " + log4j2Config.isLoggerLogFile());

		logger.error("This is Logger 3 Error");
		logger.info("This is Logger 3 Info");
		logger.debug("This is Logger 3 Debug");
		logger.trace("This is Logger 3 Trace");

		System.out.println("Before to Set Log Level = " + log4j2Config.getLevel());
		log4j2Config.setLevel(Level.TRACE);
		System.out.println("Log Level = " + log4j2Config.getLevel());

		logger.error("This is Logger 4 Error");
		logger.info("This is Logger 4 Info");
		logger.debug("This is Logger 4 Debug");
		logger.trace("This is Logger 4 Trace");

	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	// ------------------
	// 	DESTINATION FILE
	// ------------------
	/**
	 * getUserAppDirectory
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

	/**
	 * getLogPath
	 * @return
	 */
	public String getLogPath() {
		return logPath;
	}

	// ------------------
	// 	CONSOLE
	// ------------------
	/**
	 * isLoggerConsole()
	 * 		Check if Console Logger is used
	 * @return boolean
	 */
	@SuppressWarnings("resource")
	public boolean isLoggerConsole() {
		// https://logging.apache.org/log4j/2.0/log4j-core/apidocs/org/apache/logging/log4j/core/config/LoggerConfig.html
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config= ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME); 
		//System.out.println("Appenders declared in .xml :" + loggerConfig.getAppenderRefs());
		//System.out.println("Appenders used in Logger :" + loggerConfig.getAppenders());
		Map<String,Appender> ksAppender;
		ksAppender = loggerConfig.getAppenders();
		if (ksAppender.get("Console") != null)
			return true;
		else
			return false;
	}

	/**
	 * activeLoggerConsole()
	 * 		Remove or Add Console Logger
	 */
	public void activeLoggerConsole(boolean state) {
		// https://logging.apache.org/log4j/2.0/log4j-core/apidocs/org/apache/logging/log4j/core/config/LoggerConfig.html
		@SuppressWarnings("resource")
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config= ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME); 
		if (state == false) {
			System.out.println("Remove Console Logger");
			loggerConfig.removeAppender("Console");
		} else {
			System.out.println("Add Console Logger");
			Appender appender = config.getAppender("Console");
			loggerConfig.addAppender(appender, null, null );
		}
		ctx.updateLoggers();  
		//System.out.println("Appenders used in Logger :" + loggerConfig.getAppenders());
	}


	// ------------------
	// 	LOGFILE
	// ------------------
	/**
	 * isLoggerLogFile()
	 * 		Check if LogFile Logger is used
	 * @return boolean
	 */
	@SuppressWarnings("resource")
	public boolean isLoggerLogFile() {
		// https://logging.apache.org/log4j/2.0/log4j-core/apidocs/org/apache/logging/log4j/core/config/LoggerConfig.html
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config= ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME); 
		//System.out.println("Appenders declared in .xml :" + loggerConfig.getAppenderRefs());
		//System.out.println("Appenders used in Logger :" + loggerConfig.getAppenders());
		Map<String,Appender> ksAppender;
		ksAppender = loggerConfig.getAppenders();
		if (ksAppender.get("LogFile") != null)
			return true;
		else
			return false;
	}

	/**
	 * activeLoggerLogFile()
	 * 		Remove or Add LogFile Logger
	 */
	public void activeLoggerLogFile(boolean state) {
		// https://logging.apache.org/log4j/2.0/log4j-core/apidocs/org/apache/logging/log4j/core/config/LoggerConfig.html
		@SuppressWarnings("resource")
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config= ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
		if (state == false) {
			System.out.println("Remove LogFile Logger");
			loggerConfig.removeAppender("LogFile");
		} else {
			System.out.println("Add LogFile Logger");
			Appender appender = config.getAppender("LogFile");
			loggerConfig.addAppender(appender, null, null );
		}
		ctx.updateLoggers();  
		//System.out.println("Appenders used in Logger :" + loggerConfig.getAppenders());
	}

	@SuppressWarnings("resource")
	public Map<String, Appender> getAllDeclaredAppenders() {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config= ctx.getConfiguration();
		return( config.getAppenders() );
	}

	// ------------------
	// 	LOGFILE
	// ------------------

	/**
	 * getLevel
	 * @return
	 */
	@SuppressWarnings("resource")
	public Level getLevel () {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config= ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME); 
		return loggerConfig.getLevel();
	}


	/**
	 * Set Level
	 * @param vlevel
	 */
	public void setLevel( Level vlevel) {
		@SuppressWarnings("resource")
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config= ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME); 
		loggerConfig.setLevel(vlevel);
		System.out.println("Log Level = " + loggerConfig.getLevel());
		ctx.updateLoggers();  
	}
}
