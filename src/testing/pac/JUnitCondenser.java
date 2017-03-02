package testing.pac;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import pac.Condenser;
import pac.Refrigerant;

public class JUnitCondenser {

	@Test
	public void test() {
		System.out.println("TEST CONDENSER");

		Condenser vCondenser = new Condenser();

		assertEquals("Condenseur",vCondenser.getName());

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vCondenser.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance ");
		vCondenser.setName("Toto");
		System.out.println("    Name ="+vCondenser.getName());


		System.out.println("\n---> Set the Class Instance with JSON data");
		vCondenser.setJsonObject(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vCondenser.getName());

		Refrigerant vGas = new Refrigerant();
		vGas.setT(10);

		System.out.println("\n--->Transfer Function");
		System.out.println("Input --> Output");
		System.out.println(vGas.getT()+"°C-->"+vCondenser.transfer(vGas).getT()+"°C");
	}

}
