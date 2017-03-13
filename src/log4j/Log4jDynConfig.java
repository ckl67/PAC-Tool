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

import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;

public class Log4jDynConfig {

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	// Configure the destination file of log4j2 --> Relation to log4j2.xml
	// A common error is caused by the static logger
	// If you logs before the System.setProperty, this will cause the variable UNDEFINED error.
	// 		private static final Logger logger = LogManager.getLogger(test.class.getName());
	// is forbidden here !!
	public Log4jDynConfig() {

		String logPath = Paths.get(getUserAppDirectory()+"/Pac-Tool").toString();

		//  The Java platform itself uses a Properties object to maintain its own configuration.
		System.setProperty("logpath.name",logPath);
		//System.out.println("Java System Property (logpath.name) = " + System.getProperty("logpath.name"));
	}

	// -------------------------------------------------------
	// 							MAIN
	// -------------------------------------------------------

	public static void main(String[] args) {

		Log4jDynConfig log4jDynConfig = new Log4jDynConfig();
		System.out.println("File appender will be stored in " + log4jDynConfig.getAppenderFile());

		Logger logger = LogManager.getLogger(Log4jDynConfig.class.getName());
		logger.info("This is Logger 1 Info");

	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	@SuppressWarnings("resource")
	public String getAppenderFile () {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		FileAppender fileAppender = config.getAppender("LogFile");
		return Paths.get(fileAppender.getFileName()).toString();
	}
	
	/**
	 * getUserAppDirectory
	 * @return
	 */
	private String getUserAppDirectory() {
		String workingDirectory;
		String OS = (System.getProperty("os.name")).toUpperCase();	
		if (OS.contains("WIN"))
		{
			//it is simply the location of the "AppData" folder
			workingDirectory = System.getenv("AppData");
		}
		else
		{
			//Otherwise, we assume Linux or Mac
			workingDirectory = System.getProperty("user.home");
			//if we are on a Mac, we are not done, we look for "Application Support"
			workingDirectory += "/Library/Application Support";
		}
		return workingDirectory;
	}


}
