package translation;

public enum TCirculator {
	// Circulator
	CIRCUL_VOLTAGE("Voltage: ", "Volt: "),
	CIRCUL_ROTATE_PER_MINUTES("Rotate Per Minutes: ", "Rotation par minutes "),
	CIRCUL_POWER("Power: ", "Puissance: "),
	CIRCUL_CURRENT("Current: ", "Courant "),
	CIRCUL_MANUFACTURER_DATA("Manufacturer Data", "Données Constructeur"),
	CIRCUL_FEATURES("Features", "Caractéristiques"),
	CIRCUL_RENAME_LIST("Rename List", "Renom. Liste"),
	CIRCUL_NEW("New", "Nouv."),
	CIRCUL_SAVE("Save", "Sauv."),
	CIRCUL_DELETE("Delete", "Suppr"),
	CIRCUL_TITLE("Circulator", "Circulateur"),
	CIRCUL_TITLE_SOURCE("Circulator Source", "Circulateur Source"),
	CIRCUL_TITLE_DISTRIBUTION("Circulator Distribution", "Circulateur Distribution"),
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
