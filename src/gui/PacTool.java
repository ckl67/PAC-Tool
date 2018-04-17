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

import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PacTool {
	
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	// -------------------------------------------------------
	// 							MAIN
	// -------------------------------------------------------
	public static void main(String[] args){
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			logger.error(e);
		}

		boolean xmlConsoleLoggerActivated = false;
		for (int i= 0; i< args.length; i++) {
			logger.info("Command line arguments: " + args[i] );
			if (args[i].equals("-Logger")) {
				logger.info(".xml Console Logger will be activated");
				xmlConsoleLoggerActivated = true;	
			} else {
				logger.info("Console Logger will be deactivated");
				xmlConsoleLoggerActivated = false;								
			}
		}
		
		// Force point (".") as decimal separator --> set your Locale
		Locale.setDefault(new Locale("en", "US"));
		
		PacToolVar pacToolVar = new PacToolVar(xmlConsoleLoggerActivated);
		
		PacToolWin pacToolWin = new PacToolWin(pacToolVar); 
		pacToolWin.setVisible(true);
	}
}
