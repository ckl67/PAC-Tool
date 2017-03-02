package testing;

import org.json.simple.JSONObject;
import org.junit.Test;

import enthalpy.Enthalpy;

public class JUnitEnthalpy {

	@Test
	public void test() {
		
		System.out.println("TEST ENTHALPY");

		Enthalpy enthalpy = new Enthalpy();

		// Read conversion Temp/Press. file
		System.out.println("Load R22 (T/P) file");

		System.out.println("Reference Diagram Enthalpy R22 : 2 bar (absolute) <--> -25°C");
		System.out.println("  T= -25°C" + "--> P=" + enthalpy.convT2P(-25));
		System.out.println("  P= 2 bar " + "--> T=" + enthalpy.convP2T(2));
		System.out.println();

		// Read Temperature [degre C] / Enthalpy (kJ/kg) Liquid / Enthalpy (kJ/kg) Vapor file
		System.out.println("Load R22 Saturation file and pick id=2");
		System.out.println();
		System.out.println("  Pression [2] = "+ enthalpy.getSatP(2));
		System.out.println("  Enthalpy Liquid [2] = "+ enthalpy.getSatHl(2));
		System.out.println("  Enthalpy Vapor [2] = "+ enthalpy.getSatHv(2));
		System.out.println();

		// Recover the corresponding P Saturation for a specific H  
		System.out.println("Convert H to P saturation");
		double h = 360;
		double pNear = 2;
		System.out.println("  h=" + h + "  pNear=" + pNear + "  P_Sat="+enthalpy.convSatH2P(h,pNear)+"\n");


		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = enthalpy.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Perform some minor changes in Enthalpy" );
		System.out.println("    setxHmin (before)=" + enthalpy.getxHmin());
		enthalpy.setxHmin(333);
		System.out.println("    setxHmin (after)=" + enthalpy.getxHmin());


		System.out.println("\n---> Perform some minor changes in EnthalpyBkgImage" );
		System.out.println("    iBgH1x (EnthalpyBkgImage) (before) = " +enthalpy.getEnthalpyBkgImage().getiBgH1x());
		enthalpy.getEnthalpyBkgImage().setiBgH1x(300);
		System.out.println("    iBgH1x (EnthalpyBkgImage) (after) = " +enthalpy.getEnthalpyBkgImage().getiBgH1x());

		System.out.println("\n--->Rembered Chances");
		System.out.println("    setxHmin=" + enthalpy.getxHmin());
		System.out.println("    iBgH1x (EnthalpyBkgImage) = " +enthalpy.getEnthalpyBkgImage().getiBgH1x());

		System.out.println("\n---> Set the Class Instance with JSON data");
		System.out.println(jsonObj);
		enthalpy.setJsonObject(jsonObj);

		System.out.println("\n---> Read afterwards");
		System.out.println("    setxHmin=" + enthalpy.getxHmin());
		System.out.println("    iBgH1x (EnthalpyBkgImage) = " +enthalpy.getEnthalpyBkgImage().getiBgH1x());
	}

}
