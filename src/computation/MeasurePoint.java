package computation;

public enum MeasurePoint {
	T1(515,90,"Température des gaz BP\n après surchauffe interne\n et avant compression","°C",MeasureGroup.GROUP_BP),
	T2(546,90,"Température des gaz HP\n en fin de compression\n (Cloche du compresseur)","°C",MeasureGroup.GROUP_HP),
	P3(582,135,"Pression du début de condensation\n (Mesure HP Manifod)","Bar",MeasureGroup.GROUP_HP),
	P4(583,203,"Pression de fin de condensation\n (Mesure HP Manifod)","Bar",MeasureGroup.GROUP_HP),
	T5(512,247,"Température des gaz HP\n après sous refroidissement","°C",MeasureGroup.GROUP_HP),
	T6(433,248,"Température sortie Détendeur / Capillaire","°C",MeasureGroup.GROUP_BP),
	P7(371,135,"Pression évaporation\n (Mesure BP Manifold)","Bar",MeasureGroup.GROUP_BP ),
	T8(479,89, "Température des gaz HP\naprès surchauffe externe","°C",MeasureGroup.GROUP_BP),
	TMi(663,57,"Température Retour Eau Chauffage","°C",MeasureGroup.GROUP_HEAT),
	TMo(663,282,"Température Départ Eau Chauffage","°C",MeasureGroup.GROUP_HEAT),
	TCi(321,281,"Température Retour Eau Captage","°C",MeasureGroup.GROUP_SOURCE),
	TCo(321,57,"Température Départ Eau Captage","°C",MeasureGroup.GROUP_SOURCE);

	// --------------------------------------------------------------------
	// CONSTANTS
	// --------------------------------------------------------------------
	public static int _P0 = 6;
	public static int _PK = 2;

	public static int _BP_ID = 6;
	public static int _PK1_ID = 2;
	public static int _PK2_ID = 3;

	// --------------------------------------------------------------------
	// DEFINITION OF VARIABLES USED IN ENUMERATION
	// --------------------------------------------------------------------

	private int xm;
	private int ym;
	private String definition;
	private String unity;
	private MeasureGroup groupHpBp; 

	// -------------------------------------------------------
	// 					CONSTRUCTOR
	// -------------------------------------------------------
	MeasurePoint(int vxm, int vym,String vdefinition, String vunity, MeasureGroup vgroupHpBp){
		this.xm = vxm;
		this.ym = vym;
		this.definition = vdefinition;
		this.unity =vunity;
		this.groupHpBp = vgroupHpBp;
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------
	public int getXm() {
		return xm;
	}

	public int getYm() {
		return ym;
	}

	public String getDefinition() {
		return definition;
	}

	public String getUnity() {
		return unity;
	}

	public MeasureGroup getGroupHpBp() {
		return groupHpBp;
	}

}
