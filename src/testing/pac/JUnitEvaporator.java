package testing.pac;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import pac.Evaporator;
import refrigerant.Refrigerant;

public class JUnitEvaporator {

	@Test
	public void test() {
		System.out.println("TEST Evaporator");

		Evaporator vEvaporator = new Evaporator();

		assertEquals("Evaporateur",vEvaporator.getName());

		System.out.println("---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vEvaporator.getJsonObject();
		System.out.println("jsonObj =" + jsonObj);

		System.out.println("---> Test Modify the instance ");
		vEvaporator.setName("Toto");
		assertEquals("Toto",vEvaporator.getName());

		System.out.println("--> Set the Class Instance with JSON data");
		vEvaporator.setJsonObject(jsonObj);

		System.out.println("---> Read afterwards ");
		assertEquals("Evaporateur",vEvaporator.getName());

		Refrigerant vGas = new Refrigerant();
		vGas.setT(10);

		System.out.println("--->Transfer Function");
		System.out.println("Input --> Output");
		System.out.println(vGas.getT()+"°C-->"+vEvaporator.transfer(vGas).getT()+"°C");
	}

}
