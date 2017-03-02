package testing.pac;

//import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import pac.Refrigerant;

public class JUnitRefrigerant {

	@Test
	public void test() {
		
		System.out.println("TEST REFRIGERANT");

		Refrigerant vRefrigerant = new Refrigerant();

		vRefrigerant.setT(45);
		vRefrigerant.setP(25);
		System.out.println("Temp. / Pression");
		System.out.println(vRefrigerant.getT()+"°C -- "+vRefrigerant.getP()+"bar");

		JSONObject jsonObj = new JSONObject();
		jsonObj = vRefrigerant.getJsonObject();
		System.out.println(jsonObj);
	}

}
