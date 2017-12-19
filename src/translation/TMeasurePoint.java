package translation;

public enum TMeasurePoint {
	// Measure Objects
	DEF_MOBJ_P1( "BP gas temperature \n after internal overheating \n and before compression","Temp�rature des gaz BP\n apr�s surchauffe interne\n et avant compression"),
	DEF_MOBJ_P2( "HP gas temperature at the end of compression \n (Compressor top)", "Temp�rature des gaz HP\n en fin de compression\n (Cloche du compresseur)"),
	DEF_MOBJ_P3( "Initial condensation pressure \n (HP Manifod measurement)","Pression du d�but de condensation\n (Mesure HP Manifod)"),
	DEF_MOBJ_P4( "Condensation end pressure \n (HP Manifod measurement)", "Pression de fin de condensation\n (Mesure HP Manifod)"),
	DEF_MOBJ_P5( "HP gas temperature after cooling", "Temp�rature des gaz HP\n apr�s sous refroidissement"),
	DEF_MOBJ_P6( "Output Temperature Regulator / Capillary", "Temp�rature sortie D�tendeur / Capillaire"),
	DEF_MOBJ_P7( "Evaporation Pressure \n (BP Manifold Measurement", "Pression �vaporation\n (Mesure BP Manifold)" ),
	DEF_MOBJ_P8( "HP gas temperature \n after external overheating", "Temp�rature des gaz HP\napr�s surchauffe externe"),
	DEF_MOBJ_P9( "Departure Temperature Water Heating", "Temp�rature D�part Eau Chauffage"),
	DEF_MOBJ_P10( "Return Water Heating Temperature", "Temp�rature Retour Eau Chauffage"),
	DEF_MOBJ_P11( "Departure Water Temperature Capture", "Temp�rature D�part Eau Captage"),
	DEF_MOBJ_P12( "Return Water Temperature Capture", "Temp�rature Retour Eau Captage"),
	;
	// --------------------------------------------------------------------
	// DEFINITION OF VARIABLES USED IN ENUMERATION
	// --------------------------------------------------------------------
	private String englich;
	private String french;

	TMeasurePoint( String venglich, String vfrench ) {
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
