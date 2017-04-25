package computation;

import translation.TLanguage;
import translation.TResultObject;

public enum ResultObject {
	T1_T8("T1-T8",TResultObject.DEF_ROBJ_T1_T8,"°C"),
	T2_T1("T2-T1",TResultObject.DEF_ROBJ_T2_T1,"°C"),
	T3_T2("T3-T2",TResultObject.DEF_ROBJ_T3_T2,"°C"),
	T3T4("T3=T4",TResultObject.DEF_ROBJ_T3T4,"°C"),
	T4_T5("T4-T5",TResultObject.DEF_ROBJ_T4_T5,"°C"),
	T5_T6("T5-T6",TResultObject.DEF_ROBJ_T5_T6,"°C"),
	T7("T7",TResultObject.DEF_ROBJ_T7,"°C"),
	T8_T7("T8-T7",TResultObject.DEF_ROBJ_T8_T7,"°C"),
	H2_H1("H2-H1",TResultObject.DEF_ROBJ_H2_H1,"kJ/Kg"),
	H2_H5("H2-H5",TResultObject.DEF_ROBJ_H2_H5,"kJ/Kg"),
	COPCOLD("COP",TResultObject.DEF_COPCOLD," "),
	;
	
	// --------------------------------------------------------------------
	// DEFINITION OF VARIABLES USED IN ENUMERATION
	// --------------------------------------------------------------------

	public String displayTxt;
	public TResultObject definition;
	public String unity;

	// -------------------------------------------------------
	// 					CONSTRUCTOR
	// -------------------------------------------------------
	ResultObject(String vdisplayTxt, TResultObject vdefinition, String vunity) {
		displayTxt = vdisplayTxt;
		definition = vdefinition;
		unity = vunity;
	}
	

	public String getDisplayTxt() {
		return displayTxt;
	}

	public String getDefinition(TLanguage langage) {
		return definition.getLangue(langage);
	}

	public String getUnity() {
		return unity;
	}

}
