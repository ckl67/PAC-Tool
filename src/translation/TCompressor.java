package translation;

public enum TCompressor {

	// Compressor
	COMP_CAPACITY("Capacity: ", "Capacit�: "),
	COMP_POWER("Power: ", "Puissance "),
	COMP_CURRENT("Current: ", "Courant: "),
	COMP_EER("EER: ", "COP Froid: "),
	COMP_VOLTAGE("Voltage: ", "Tension: "),
	COMP_MASSFLOW("Mass flow: ", "D�bit Massique: "),
	COMP_EVAP("Evap: ", "T0. Evap.: "),
	COMP_RG("RG: ", "T. Asp. Comp.: "),
	COMP_COND("Cond: ", "TK. Cond.: "),
	COMP_LIQ("Liq: ", "T. D�tent.: "),
	COMP_UNDERCOOLING("Under cooling: ", "S/Refroidissement: "),
	COMP_OVERHEATED("Overheated: ", "Surchauffe: "),
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
