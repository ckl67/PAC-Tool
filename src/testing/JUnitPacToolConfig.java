package testing;

//import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import enthalpy.Enthalpy;
import gui.WinPacToolConfig;

public class JUnitPacToolConfig {

	@Test
	public void test() {

		System.out.println("TEST PAC-Tool CONFIGURATION");

		Scanner sken = null;
		try {
			sken = new Scanner (new File ("D:/Users/kluges1/workspace/pac-tool/config/PAC-Tool-Test.cfg"));
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

		// Close scanner to avoid memory leak
		sken.close();

		// Parse to JSON Object
		JSONParser parser = new JSONParser();  
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) parser.parse(strLineJSON);
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// WinPacToolConfig: Set the Class Instance with JSON data
		WinPacToolConfig winPacToolConfig = new WinPacToolConfig();
		JSONObject jsonObjPrimeConfig = (JSONObject) jsonObj.get("WinPacToolConfig") ;
		System.out.println(jsonObjPrimeConfig);
		winPacToolConfig.setJsonObject(jsonObjPrimeConfig);

		System.out.println("   BTU=" +	winPacToolConfig.getUnitCompBTU());
		System.out.println("   Pound=" +	winPacToolConfig.getUnitCompPound());
		System.out.println("   Faren=" +	winPacToolConfig.getUnitCompFaren());

		// Enthalpy (containing also EnthalpyBkgdImg)
		Enthalpy enthalpy = new Enthalpy();
		JSONObject jsonObjEnthalpy = (JSONObject) jsonObj.get("Enthalpy");
		System.out.println(jsonObjEnthalpy);
		enthalpy.setJsonObject(jsonObjEnthalpy);

	}

}
