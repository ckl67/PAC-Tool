package translation;

public enum TOther {

	DEF_TMP("COP Cold","COP froid"),
	;

	// --------------------------------------------------------------------
	// DEFINITION OF VARIABLES USED IN ENUMERATION
	// --------------------------------------------------------------------
	private String englich;
	private String french;

	TOther( String venglich, String vfrench ) {
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
