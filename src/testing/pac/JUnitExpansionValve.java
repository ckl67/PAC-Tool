package testing.pac;

//import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import pac.ExpansionValve;
import pac.Refrigerant;

public class JUnitExpansionValve {

	@Test
	public void test() {
		
		System.out.println("TEST EXPANSION VALVE  = Détendeur !");

		ExpansionValve vExpansionValve = new ExpansionValve();
		System.out.println(vExpansionValve.getName());

		Refrigerant vRefrigeranR22 =  new Refrigerant();
		vRefrigeranR22.setP(25);
		vRefrigeranR22.setT(5);

		vExpansionValve.setDeltaP(-40);
		vExpansionValve.setDeltaT(+75);

		JSONObject jsonObj = new JSONObject();
		jsonObj = vExpansionValve.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance ");
		vExpansionValve.setName("Toto");
		System.out.println("    Name ="+vExpansionValve.getName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		vExpansionValve.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vExpansionValve.getName());

		System.out.println("Input --> Output");
		System.out.println("P:" + vRefrigeranR22.getP() + "-->" + vExpansionValve.transfer(vRefrigeranR22).getP());
		System.out.println("T:" + vRefrigeranR22.getT() + "-->" + vExpansionValve.transfer(vRefrigeranR22).getT());

	}

}
