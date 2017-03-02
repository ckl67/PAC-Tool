package testing;

import org.json.simple.JSONObject;
import org.junit.Test;

import enthalpy.EnthalpyBkgdImg;

public class JunitEnthalpyBkgdImg {

	@Test
	public void test() {
		System.out.println("TEST ENTHALPYBKGDIMG");

		EnthalpyBkgdImg vEnthalpyBkgdImg = new EnthalpyBkgdImg();

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vEnthalpyBkgdImg.getJsonObject();
		System.out.println(jsonObj);


		// Read value, then modify
		System.out.println("\n---> Perform some minor changes in Enthalpy" );
		System.out.println("    iBgH1x = " +vEnthalpyBkgdImg.getiBgH1x());
		vEnthalpyBkgdImg.setiBgH1x(300);
		System.out.println("    iBgH1x = " +vEnthalpyBkgdImg.getiBgH1x());

		// Set with Json
		System.out.println("\n---> Set the Class Instance with JSON data");
		vEnthalpyBkgdImg.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards");
		System.out.println("    iBgH1x = " +vEnthalpyBkgdImg.getiBgH1x());

	}

}