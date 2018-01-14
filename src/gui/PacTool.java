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

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import log4j.Log4j2Config;

public class PacTool {
	
	//private static final Logger logger = LogManager.getLogger(PacTool.class.getName());
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	// -------------------------------------------------------
	// 							MAIN
	// -------------------------------------------------------
	public static void main(String[] args){
		
		// Force point (".") as decimal separator --> set your Locale
		Locale.setDefault(new Locale("en", "US"));
		
		Log4j2Config log4j2Config = new Log4j2Config();
		logger.info("Read the Appenders Declared");
		logger.info("  All declared Appenders = {} ",log4j2Config.getAllDeclaredAppenders());
		
		logger.info("Read the Appenders Activated in the Logger");
		logger.info("  Is Logger Console active --> {}", log4j2Config.isLoggerConsole());
		logger.info("  Is Logger File active --> {}", log4j2Config.isLoggerLogFile());

		
		PacToolVar pacToolVar = new PacToolVar(log4j2Config);
		
		WinPacTool winPacTool = new WinPacTool(pacToolVar); 
		winPacTool.setVisible(true);
	}
}
