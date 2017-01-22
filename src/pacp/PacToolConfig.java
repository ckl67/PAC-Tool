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
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PacToolConfig {

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	private PacToolConfig() { }  // Prevents instantiation

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	// Save PAC-Tool overall configuration file
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
			System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
			
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
					//tmpo = tmpo + "\n" + tab;
				}
				if (tmpi.charAt(i) == '[') {
					ntab = ntab + 3; 
					tab = Misc.genRepeatChar(' ', ntab);
					tmpo = tmpo + "\n" + tab;
				}
				if (tmpi.charAt(i) == ']') {
					ntab = ntab - 3; 
					tab = Misc.genRepeatChar(' ', ntab);
					//tmpo = tmpo + "\n" + tab;
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
}
