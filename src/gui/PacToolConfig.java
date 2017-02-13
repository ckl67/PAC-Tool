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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import computation.Misc;
import enthalpy.Enthalpy;
import pac.Pac;

public class PacToolConfig {

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	private PacToolConfig() { }  // Prevents instantiation

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * Save PAC-Tool overall configuration file
	 * @param pacl
	 * @param enthalpy
	 * @param primeConfig
	 * @param fileName
	 */
	@SuppressWarnings("unchecked")
	public static void saveConfigFile(Pac pac, Enthalpy enthalpy, PrimeConfig primeConfig, String fileName) {

		// Overall JSON object
		JSONObject jsonObjPacTool = new JSONObject();

		// Create PAC Array JSON Object
		JSONObject jsonObjPac = new JSONObject();
		jsonObjPac = pac.getJsonObject();
		jsonObjPacTool.put("PAC",jsonObjPac);

		// Enthalpy (containing also EnthalpyBkgdImg)
		JSONObject jsonObjEnthalpy = new JSONObject();
		jsonObjEnthalpy = enthalpy.getJsonObject();
		jsonObjPacTool.put("Enthalpy",jsonObjEnthalpy);

		// PrimeConfig
		JSONObject jsonObjPrimeConfig = new JSONObject();
		jsonObjPrimeConfig = primeConfig.getJsonObject();
		jsonObjPacTool.put("PrimeConfig",jsonObjPrimeConfig);

		// Writing to a file  
		try {  
			File file=new File(fileName);  
			file.createNewFile();  
			FileWriter fileWriter = new FileWriter(file);  
			//fileWriter.write(jsonObjPacTool.toJSONString());  

			// Format easy readable JSON 			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();

			String tmpi= jsonObjPacTool.toJSONString();
			String tmpo = "# ---------------------------------------------------\n" + "# PAC-Tool: " + Misc.PACTool_Version + "\n# Configuration File generated: " + dateFormat.format(date) + "\n# ---------------------------------------------------\n";
			String tab = "";
			int ntab= 0;
			for(int i=0; i< tmpi.length(); i++) {
				tmpo = tmpo + tmpi.charAt(i);
				if (tmpi.charAt(i) == '{') {
					ntab = ntab + 3; 
					tab = Misc.genRepeatChar(' ', ntab);
					tmpo = tmpo + "\n" + tab;
				}
				if (tmpi.charAt(i) == '}') {
					ntab = ntab - 3; 
					tab = Misc.genRepeatChar(' ', ntab);
				}
				if (tmpi.charAt(i) == '[') {
					ntab = ntab + 3; 
					tab = Misc.genRepeatChar(' ', ntab);
					tmpo = tmpo + "\n" + tab;
				}
				if (tmpi.charAt(i) == ']') {
					ntab = ntab - 3; 
					tab = Misc.genRepeatChar(' ', ntab);
				}
				if (tmpi.charAt(i) == ',') 
					tmpo = tmpo + "\n" + tab;
			}

			fileWriter.write(tmpo);  
			fileWriter.flush();  
			fileWriter.close();  

		} catch (IOException e) { 
			System.out.println("saveConfigFile");
			e.printStackTrace();  
		}  
	}

	/**
	 * Read PAC-Tool overall configuration file
	 * @param pacl
	 * @param enthalpy
	 * @param primeConfig
	 * @param fileName
	 */
	public static void readConfigFile(Pac pac, Enthalpy enthalpy, PrimeConfig primeConfig, String fileName) {
		File file = new File (fileName);
		Scanner sken = null;

		try {
			sken = new Scanner (file);
		} catch (FileNotFoundException e) {
			System.err.println("Unable to read the file: fileName");
		}

		String strLineJSON = "";
		while (sken.hasNext () ){
			String strLine  = sken.nextLine ();
			if (!strLine .startsWith("#") ) {
				strLineJSON = strLineJSON + strLine;
			}
		}

		// Parse to JSON Object
		JSONParser parser = new JSONParser();  
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) parser.parse(strLineJSON);
		} catch (ParseException e) {
			System.out.println("readConfigFile");
			e.printStackTrace();  
		}  

		// PrimeConfig
		JSONObject jsonObjPrimeConfig = (JSONObject) jsonObj.get("PrimeConfig") ;
		primeConfig.setJsonObject(jsonObjPrimeConfig);
		//System.out.println(jsonObjPrimeConfig);
				

		// Enthalpy (containing also EnthalpyBkgdImg)
		JSONObject jsonObjEnthalpy = (JSONObject) jsonObj.get("Enthalpy");
		enthalpy.setJsonObject(jsonObjEnthalpy);
		//System.out.println(jsonObjEnthalpy);

		// PAC
		JSONObject jsonObjPac = (JSONObject) jsonObj.get("PAC");
		pac.setJsonObject(jsonObjPac);
		//System.out.println(jsonObjPac);

		// Close scanner to avoid memory leak
		sken.close();

	}

}
