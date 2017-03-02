package testing.pac;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import pac.Dehydrator;
import pac.Refrigerant;

public class JUnitDehydrator {

	@Test
	public void test() {
		System.out.println("TEST DEHYDRATOR");
		Dehydrator vDehydrator = new Dehydrator();
		Refrigerant vGas = new Refrigerant();

		assertEquals("Dehydrator",vDehydrator.getName());
		
		vDehydrator.setDeltaT(50.0);
		vGas.setT(10);

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vDehydrator.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance ");
		vDehydrator.setName("Toto");
		System.out.println("    Name ="+vDehydrator.getName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		vDehydrator.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vDehydrator.getName());

		System.out.println("\n--->Transfer Function");
		System.out.println("Input --> Output");
		System.out.println(vGas.getT()+"°C-->"+vDehydrator.transfer(vGas).getT()+"°C");
	}

}
