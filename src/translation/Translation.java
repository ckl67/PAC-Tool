package translation;

public enum Translation {
	// Measure Objects
	DEF_MOBJ_T1( "BP gas temperature \n after internal overheating \n and before compression","Temp�rature des gaz BP\n apr�s surchauffe interne\n et avant compression"),
	DEF_MOBJ_T2( "HP gas temperature at the end of compression \n (Compressor top)", "Temp�rature des gaz HP\n en fin de compression\n (Cloche du compresseur)"),
	DEF_MOBJ_P3( "Initial condensation pressure \n (HP Manifod measurement)","Pression du d�but de condensation\n (Mesure HP Manifod)"),
	DEF_MOBJ_P4( "Condensation end pressure \n (HP Manifod measurement)", "Pression de fin de condensation\n (Mesure HP Manifod)"),
	DEF_MOBJ_T5( "HP gas temperature after cooling", "Temp�rature des gaz HP\n apr�s sous refroidissement"),
	DEF_MOBJ_T6( "Output Temperature Regulator / Capillary", "Temp�rature sortie D�tendeur / Capillaire"),
	DEF_MOBJ_P7( "Evaporation Pressure \n (BP Manifold Measurement", "Pression �vaporation\n (Mesure BP Manifold)" ),
	DEF_MOBJ_T8( "HP gas temperature \n after external overheating", "Temp�rature des gaz HP\napr�s surchauffe externe"),
	DEF_MOBJ_TMi( "Return Water Heating Temperature", "Temp�rature Retour Eau Chauffage"),
	DEF_MOBJ_TMo( "Departure Temperature Water Heating", "Temp�rature D�part Eau Chauffage"),
	DEF_MOBJ_TCi( "Return Water Temperature Capture", "Temp�rature Retour Eau Captage"),
	DEF_MOBJ_TCo( "Departure Water Temperature Capture", "Temp�rature D�part Eau Captage"),
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
	// Circulator
	CIRCUL_POWER("Power: ", "Puissance "),
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
