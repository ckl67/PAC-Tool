package computation;

public enum ResultObject {
	T1_T8("T1-T8","Surchauffe Interne (Données Compresseur)","°C"),
	T2_T1("T2-T1","Compression","°C"),
	T3_T2("T3-T2","Désurchauffe","°C"),
	T3T4("T3=T4","Point de saturation au niveau de la condensation","°C"),
	T4_T5("T4-T5","Sous Refroidissement","°C"),
	T5_T6("T5-T6","Détente","°C"),
	T7("T7","Point de Saturation Evaporation ","°C"),
	T8_T7("T8-T7","Surchauffe","°C"),
	
	;
	
	// --------------------------------------------------------------------
	// DEFINITION OF VARIABLES USED IN ENUMERATION
	// --------------------------------------------------------------------

	public String displayTxt;
	public String definition;
	public String unity;

	// -------------------------------------------------------
	// 					CONSTRUCTOR
	// -------------------------------------------------------
	ResultObject(String vdisplayTxt, String vdefinition, String vunity) {
		displayTxt = vdisplayTxt;
		definition = vdefinition;
		unity = vunity;
	}
	
	public String getDisplayTxt() {
		return displayTxt;
	}

	public String getDefinition() {
		return definition;
	}

	public String getUnity() {
		return unity;
	}

}
