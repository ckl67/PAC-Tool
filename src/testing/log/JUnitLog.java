package testing.log;

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
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.Test;
import refrigerant.Refrigerant;

public class JUnitLog {

	private static final Logger log = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	@Test
	public void test() {

		// -------------------------------------------------------
		// 							TEST
		// -------------------------------------------------------

		System.out.println(" Logger based on XML file" );
		log.error("Before altering the appender!");
		displayLoggerRootLevel();
		System.out.println(" Set Root Logger to INFO ");
		setLoggerLevel(LogManager.ROOT_LOGGER_NAME, Level.INFO);
		System.out.println(" Root Logger Level = " + getLoggerLevel(LogManager.ROOT_LOGGER_NAME));

		new Refrigerant();

		// Will remove all Loggers, except the root logger, and set Console Level to OFF
		System.out.println(" All Declared Loggers " + getAllDeclaredLoggers().toString());
		removeAllLoggerExceptRoot();
		System.out.println(" All Declared Loggers " + getAllDeclaredLoggers().toString());
		setLoggerLevel(LogManager.ROOT_LOGGER_NAME, Level.OFF);
		System.out.println(" Root Logger Level = " + getLoggerLevel(LogManager.ROOT_LOGGER_NAME));
		System.out.println("");

		
		// Will create the File Appender in <Appenders>
		System.out.println(" All Declared Appenders " + getAllDeclaredAppenders().toString());
		System.out.println(" Will create the File Appender");
		createFileAppender();
		System.out.println(" All Declared Appenders " + getAllDeclaredAppenders().toString());

		// Add File AppenderRef in Root Logger + set Level of File Logger
		System.out.println("Before to Add File AppenderRef");
		displayAppenderRefRootLevels();
		createFileAppenderRefInRootLogger(Level.INFO);
		System.out.println("After to Add File AppenderRef");
		displayAppenderRefRootLevels();

		System.out.println("(P)----------- After altering the appender! We should only have FIle logger");

		new Refrigerant(
				"D:/Users/kluges1/workspace/pac-tool/ressources/R22/R22 Saturation Table.txt",
				"D:/Users/kluges1/workspace/pac-tool/ressources/R22/R22 IsoTherm Table.txt"	);

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
		//		immediateFlush="false" append="false">
		//		<PatternLayout
		//			pattern="%d{yyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n" />
		//	</File>
		Appender appender  = FileAppender.newBuilder()
				.setConfiguration(config)
				.withName("File")
				.withFileName(logPath + "/Pac-Tool.log")
				.withAppend(false)
				.withImmediateFlush(false)
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
	 *				<AppenderRef ref="Console" level="OFF" />
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
	public void displayAppenderRefRootLevels () {

		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

		for (AppenderRef ref : loggerConfig.getAppenderRefs()) {
			System.out.printf("AppenderRef: %s, level: %s\n", ref.getRef(), ref.getLevel().toString());
		}

		System.out.println("	--> As information (Appenders of LoggerConfig cannot be displayed) ");
		System.out.println("	--> LoggerConfig Level = " + loggerConfig.getLevel());
		
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
	@SuppressWarnings({ "resource" })
	public Level getConsoleAppenderRefRootLevel () {
		Level vlevel = null;
		
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

		for (AppenderRef ref : loggerConfig.getAppenderRefs()) {
			if (ref.getRef().toString().equals("Console") )
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
			if (ref.getRef().toString().equals("Console") )
				vlevel = ref.getLevel();
		}
		return vlevel;
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


		System.out.printf("Logger Root = level: %s\n", loggerConfig.getLevel());

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
