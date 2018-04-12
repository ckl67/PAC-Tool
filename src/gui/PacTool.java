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

public class PacTool {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	// -------------------------------------------------------
	// 							MAIN
	// -------------------------------------------------------
	public static void main(String[] args){
		
		boolean defConsoleLoggerActivated = false;
		for (int i= 0; i< args.length; i++) {
			System.out.println("Command line arguments: " + args[i] );
			if (args[i].equals("-Logger")) {
				System.out.println("Console Logger will be activated based on .xml");
				defConsoleLoggerActivated = true;	
			} else {
				System.out.println("Default Console Logger will be deactivated");
				defConsoleLoggerActivated = false;								
			}
		}
		
		// Force point (".") as decimal separator --> set your Locale
		Locale.setDefault(new Locale("en", "US"));
		
		PacToolVar pacToolVar = new PacToolVar(defConsoleLoggerActivated);
		
		PacToolWin pacToolWin = new PacToolWin(pacToolVar); 
		pacToolWin.setVisible(true);
	}
}
