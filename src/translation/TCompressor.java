package translation;

public enum TCompressor {

	// Compressor
	COMP_WIN_TITLE("Compressor: ", "Compresseur: "),
	COMP_CAPACITY("Capacity: ", "Capacité: "),
	COMP_POWER("Power: ", "Puissance "),
	COMP_CURRENT("Current: ", "Courant: "),
	COMP_EER("EER: ", "COP Froid: "),
	COMP_VOLTAGE("Voltage: ", "Tension: "),
	COMP_MASSFLOW("Mass flow: ", "Débit Massique: "),
	COMP_EVAP("Evap: ", "T0. Evap.: "),
	COMP_RG("RG: ", "T. Asp. Comp.: "),
	COMP_COND("Cond: ", "TK. Cond.: "),
	COMP_LIQ("Liq: ", "T. Détent.: "),
	COMP_UNDERCOOLING("Under cooling: ", "S/Refroidissement: "),
	COMP_OVERHEATED("Overheated: ", "Surchauffe: "),
	COMP_POWER_MOTOR_SHAFT("Power on motor shaft in %: ", "Puissance sur arbre moteur en %: "),
	COMP_DATA_PERFORMANCE_TITLE1("Data Performance (Temperature)", "Données Performance Constructeur (Température)"),
	COMP_DATA_PERFORMANCE_TITLE2("Data Performance (Other)", "Données Performance Constructeur (Autre)"),
	COMP_DATA_MEASURE_TITLE1("Measures", "Mesures"),
	COMP_TITLE1("Compressor", "Compresseur"),
	COMP_TITLE2("Measures", "Mesures"),

	;
	
	// --------------------------------------------------------------------
	// DEFINITION OF VARIABLES USED IN ENUMERATION
	// --------------------------------------------------------------------
	private String englich;
	private String french;

	TCompressor( String venglich, String vfrench ) {
		this.englich = venglich;
		this.french = vfrench;
	}
	
	public String getLangue( TLanguage vlangue) {
		if (vlangue == TLanguage.ENGLICH)
			return englich;
		else if (vlangue == TLanguage.FRENCH)
			return french;
		else
			return englich;
	}
	
}
