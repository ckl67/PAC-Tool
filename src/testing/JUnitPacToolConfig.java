package testing;

//import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import gui.GuiConfig;

public class JUnitPacToolConfig {

	@Test
	public void test() {

		System.out.println("TEST PAC-Tool CONFIGURATION");

		Scanner sken = null;
		try {
			// D:\Users\kluges1\workspace\pac-tool\config
			sken = new Scanner (new File ("D:/Users/kluges1/workspace/pac-tool/config/PAC-Tool.cfg"));
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

		// GuiConfig: Set the Class Instance with JSON data
		GuiConfig guiConfig = new GuiConfig();
		JSONObject jsonObjPrimeConfig = (JSONObject) jsonObj.get("GuiConfig") ;
		System.out.println(jsonObjPrimeConfig);
		guiConfig.setJsonObject(jsonObjPrimeConfig);

		System.out.println("   BTU=" +	guiConfig.getUnitCompBTU());
		System.out.println("   Pound=" +	guiConfig.getUnitCompPound());
		System.out.println("   Faren=" +	guiConfig.getUnitCompFaren());

	}

}
