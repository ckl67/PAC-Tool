package translation;

public enum TResultObject {

	// Result Objects
	DEF_ROBJ_T1_T8("Internal Overheating (Compressor Data)","Surchauffe Interne (Donn�es Compresseur)"),
	DEF_ROBJ_T2_T1("Compression","Compression"),
	DEF_ROBJ_T3_T2("DeOverheating","D�surchauffe"),
	DEF_ROBJ_T3T4("Saturation point at the level of the condensation","Point de saturation au niveau de la condensation"),
	DEF_ROBJ_T4_T5("Under Cooling","Sous Refroidissement"),
	DEF_ROBJ_T5_T6("Relaxing","D�tente"),
	DEF_ROBJ_T7("Point of Saturation Evaporation","Point de Saturation Evaporation"),
	DEF_ROBJ_T8_T7("Overheating","Surchauffe"),
	DEF_ROBJ_H2_H1("Work of the H2-H1 Compressor","Travail du Compresseur H2-H1"),
	DEF_ROBJ_H2_H5("Calorific Power","Puissance Calorifique"),
	DEF_COPCOLD("COP Cold","COP froid"),

	;	
	// --------------------------------------------------------------------
	// DEFINITION OF VARIABLES USED IN ENUMERATION
	// --------------------------------------------------------------------
	private String englich;
	private String french;

	TResultObject( String venglich, String vfrench ) {
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