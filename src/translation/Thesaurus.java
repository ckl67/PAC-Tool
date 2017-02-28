package translation;

import java.util.HashMap;

public class Thesaurus {

	private HashMap<String, String> hmapTranslate = new HashMap<String, String>();
	private HashMap<String, String> hmapTranslate_fr = new HashMap<String, String>();

	public Thesaurus() {

		//By default in English
		hmapTranslate.put("Capacity", 		"Capacity: ");
		hmapTranslate.put("Power", 			"Power: ");
		hmapTranslate.put("Current", 		"Current: ");
		hmapTranslate.put("EER", 			"EER: ");
		hmapTranslate.put("Voltage", 		"Voltage: ");
		hmapTranslate.put("Mass flow", 		"Mass flow: ");
		hmapTranslate.put("Evap", 			"Evap: ");
		hmapTranslate.put("RG", 			"RG: ");
		hmapTranslate.put("Cond", 			"Cond: ");
		hmapTranslate.put("Liq", 			"Liq: ");
		hmapTranslate.put("Under cooling", 	"Under cooling: ");
		hmapTranslate.put("Overheated", 	"Overheated: ");

		//By French
		hmapTranslate_fr.put("Capacity", 	"Capacité: ");
		hmapTranslate_fr.put("Capacity",	"P. Frigo.: ");
		hmapTranslate_fr.put("Power", 		"P. Absorb.: ");
		hmapTranslate_fr.put("Current", 	"Courant: ");
		hmapTranslate_fr.put("EER", 		"COP Froid: ");
		hmapTranslate_fr.put("Voltage", 	"Tension: ");
		hmapTranslate_fr.put("Mass flow", 	"Débit Massique: ");
		hmapTranslate_fr.put("Evap", 		"T0. Evap.: ");
		hmapTranslate_fr.put("RG", 			"T. Asp. Comp.: ");
		hmapTranslate_fr.put("Cond", 		"TK. Cond.: ");
		hmapTranslate_fr.put("Liq", 		"T. Détent.: ");
		hmapTranslate.put("Under cooling", 	"S/Refroidissement: ");
		hmapTranslate.put("Overheated", 	"Surchauffe: ");
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public String TranslateText(String key, String dic) {
		String result = "Wrong Key in TranslateText method";

		// By default English
		String out = hmapTranslate.get(key);

		// French : revert to default if not found
		if (dic.equals("fr")) {
			String outfr = hmapTranslate_fr.get(key);
			if (outfr != null)
				out = outfr; 
		}

		if (out != null)
			result = out;
		
		return result;
	}
}
