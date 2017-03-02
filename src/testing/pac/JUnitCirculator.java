package testing.pac;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import pac.Circulator;
import pac.HeatTransferFluid;

public class JUnitCirculator {

	@Test
	public void test() {
		
		System.out.println("TEST CIRCULATOR");

		Circulator vCirlculator = new Circulator();

		assertEquals("DAB A 80/180XM",vCirlculator.getName());
		assertEquals(220.0,vCirlculator.getVoltage(),0.001);

		int n = 0;
		System.out.println("     Feature (" + n + ")");
		assertEquals(0.91,vCirlculator.getCurrentL().get(n),0.001);
		assertEquals(1620.0,vCirlculator.getRotatePerMinutesL().get(n),0.001);
		assertEquals(190.0,vCirlculator.getPowerL().get(n),0.001);


		// ADD 1 feature + Display features
		System.out.println("\n---> ADD feature");
		vCirlculator.addFeatures(50, 5000, 500);
		for (int i=0;i<vCirlculator.getCurrentL().size();i++) {
			System.out.println(" Feature (" + i + ")");
			System.out.println("    Current =" + vCirlculator.getCurrentL().get(i));
			System.out.println("    RotatePerMinutes  =" + vCirlculator.getRotatePerMinutesL().get(i));
			System.out.println("    Power  =" + vCirlculator.getPowerL().get(i));
		}

		n=3;
		assertEquals(50.0,vCirlculator.getCurrentL().get(n),0.001);
		assertEquals(5000.0,vCirlculator.getRotatePerMinutesL().get(n),0.001);
		assertEquals(500.0,vCirlculator.getPowerL().get(n),0.001);

		// construct JSON object
		System.out.println("\n---> Construct JSON data (with the new element !)");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vCirlculator.getJsonObject();
		System.out.println(jsonObj);

		// Create a new instance + clear Features 
		System.out.println("\n---> New Instance + Clear  all features");
		Circulator vCirlculatorNew = new Circulator();
		vCirlculatorNew.clearFeatures();
		vCirlculatorNew.setName("Toto");
		System.out.println("    Name = " + vCirlculatorNew.getName());
		if (vCirlculatorNew.getCurrentL().size() == 0)
			System.out.println("    --> No features");

		// set Class with the JSON object
		System.out.println("\n---> Set the Instance Class with the JSON data");
		vCirlculatorNew.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		// Display the features afterwards 
		System.out.println("\n---> Class Instance");
		System.out.println("    Name = " + vCirlculator.getName());
		System.out.println("    Voltage = " + vCirlculator.getVoltage());
		System.out.println("    --> Display features");
		for (int i=0;i<vCirlculator.getCurrentL().size();i++) {
			System.out.println("     Feature (" + i + ")");
			System.out.println("        Current =" + vCirlculator.getCurrentL().get(i));
			System.out.println("        RotatePerMinutes  =" + vCirlculator.getRotatePerMinutesL().get(i));
			System.out.println("        Power  =" + vCirlculator.getPowerL().get(i));
		}

		System.out.println("\n--->Transfer Function");
		HeatTransferFluid vFluid = new HeatTransferFluid() ;
		vFluid.setT(25);
		System.out.println("    Input --> Output");
		System.out.println("    " + vFluid.getT()+"°C-->"+vCirlculator.transfer(vFluid).getT()+"°C");		

	}
}
