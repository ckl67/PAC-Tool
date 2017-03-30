package translation;

public enum Translation {
	Capacity("Capacity: ", "Capacité: "),
	Power("Power: ", "Puissance "),
	Current("Current: ", "Courant: "),
	EER("EER: ", "COP Froid: "),
	Voltage("Voltage: ", "Tension: "),
	Mass_flow("Mass flow: ", "Débit Massique: "),
	Evap("Evap: ", "T0. Evap.: "),
	RG("RG: ", "T. Asp. Comp.: "),
	Cond("Cond: ", "TK. Cond.: "),
	Liq("Liq: ", "T. Détent.: "),
	Under_cooling("Under cooling: ", "S/Refroidissement: "),
	Overheated("Overheated: ", "Surchauffe: "),
	;	

	// --------------------------------------------------------------------
	// CONSTANTS
	// --------------------------------------------------------------------
	public static int _ENGLICH = 0;
	public static int _FRENCH = 1;
	
	// --------------------------------------------------------------------
	// DEFINITION OF VARIABLES USED IN ENUMERATION
	// --------------------------------------------------------------------
	private String englich;
	private String french;
	
	Translation( String venglich, String vfrench ) {
		this.englich = venglich;
		this.french = vfrench;
	}


	public String getLangue( int vlangue) {
		if (vlangue == _ENGLICH)
			return englich;
		else if (vlangue == _FRENCH)
			return french;
		else
			return englich;
	}
	
}
