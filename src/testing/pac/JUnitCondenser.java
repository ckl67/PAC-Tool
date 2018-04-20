package testing.pac;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import pac.Condenser;
import refrigerant.Refrigerant;

public class JUnitCondenser {

	@Test
	public void test() {
		System.out.println("TEST CONDENSER");

		Condenser vCondenser = new Condenser();

		assertEquals("Condenseur",vCondenser.getName());

		System.out.println("---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vCondenser.getJsonObject();
		System.out.println("jsonObj =" + jsonObj);

		System.out.println("---> Test Modify the instance ");
		vCondenser.setName("Toto");
		assertEquals("Toto",vCondenser.getName());

		System.out.println("--> Set the Class Instance with JSON data");
		vCondenser.setJsonObject(jsonObj);

		System.out.println("---> Read afterwards ");
		assertEquals("Condenseur",vCondenser.getName());

		Refrigerant vGas =  new Refrigerant(
				"D:/Users/kluges1/workspace/pac-tool/ressources/R22/R22 Saturation Table.txt",
				"D:/Users/kluges1/workspace/pac-tool/ressources/R22/R22 IsoTherm Table.txt"	);

		vGas.setRfgT(10);

		System.out.println("--->Transfer Function");
		System.out.println("Input --> Output");
		System.out.println(vGas.getRfgT()+"°C-->"+vCondenser.transfer(vGas).getRfgT()+"°C");
	}

}
