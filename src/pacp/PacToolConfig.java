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
package pacp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	public static void saveConfigFile(List<Pac> pacl, Enthalpy enthalpy, PrimeConfig primeConfig, String fileName) {

		// Overall JSON object
		JSONObject jsonObjPacTool = new JSONObject();

		// Create PAC Array JSON Object
		JSONArray jsonObjPacL = new JSONArray();			
		for(int i=0;i<pacl.size();i++) {
			JSONObject jsonObj = new JSONObject();
			jsonObj = pacl.get(i).getJsonObject();
			jsonObjPacL.add(jsonObj);
		}
		jsonObjPacTool.put("PacList", jsonObjPacL);  

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
	public static void readConfigFile(List<Pac> pacl, Enthalpy enthalpy, PrimeConfig primeConfig, String fileName) {
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
			System.err.println("Unable to Parse JSON read the file: fileName");
		}  

		// PrimeConfig
		JSONObject jsonObjPrimeConfig = (JSONObject) jsonObj.get("PrimeConfig") ;
		primeConfig.setJsonObject(jsonObjPrimeConfig);
		//System.out.println(jsonObjPrimeConfig);
				

		// Enthalpy (containing also EnthalpyBkgdImg)
		JSONObject jsonObjEnthalpy = (JSONObject) jsonObj.get("Enthalpy");
		enthalpy.setJsonObject(jsonObjEnthalpy);
		//System.out.println(jsonObjEnthalpy);

		// Create PAC List
		for(int i=1;i<pacl.size();i++) {
			pacl.remove(i);
		}
		JSONArray jsonObjectPacL = (JSONArray) jsonObj.get("PacList");
		for(int i=0; i< jsonObjectPacL.size();i++) {
			JSONObject jsonObjectPac = (JSONObject) jsonObjectPacL.get(i);
			//System.out.println(jsonObjectPac);
			if(i==0) {
				pacl.get(i).setJsonObject(jsonObjectPac);				
			} else {
				pacl.add(i, new Pac());
				pacl.get(i).setJsonObject(jsonObjectPac);
			}
		}	
	}


}
