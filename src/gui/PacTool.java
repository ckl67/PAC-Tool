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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import log4j.Log4j2Config;

public class PacTool {
	
	private static final Logger logger = LogManager.getLogger(PacTool.class.getName());

	// -------------------------------------------------------
	// 							MAIN
	// -------------------------------------------------------
	public static void main(String[] args){
		
		Log4j2Config log4j2Config = new Log4j2Config();
		logger.info(log4j2Config.getAllDeclaredAppenders());
		PacToolVar pacToolVar = new PacToolVar(log4j2Config);
		
		WinPacTool winPacTool = new WinPacTool(pacToolVar); 
		winPacTool.setVisible(true);
	}
}
