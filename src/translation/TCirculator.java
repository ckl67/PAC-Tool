package translation;

public enum TCirculator {
	// Circulator
	CIRCUL_POWER("Power: ", "Puissance "),
	;
	
	// --------------------------------------------------------------------
	// DEFINITION OF VARIABLES USED IN ENUMERATION
	// --------------------------------------------------------------------
	private String englich;
	private String french;

	TCirculator( String venglich, String vfrench ) {
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
