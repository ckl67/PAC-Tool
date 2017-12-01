package testing.pac;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import coolant.HeatTransferFluid;
import pac.Circulator;

public class JUnitCirculator {

	@Test
	public void test() {
		
		System.out.println("TEST CIRCULATOR");

		Circulator vCirlculator = new Circulator();

		assertEquals("DAB A 80/180XM",vCirlculator.getName());
		assertEquals(220.0,vCirlculator.getVoltage(),0.001);

		int n = 0;
		System.out.println("     Feature (" + n + ")");
		vCirlculator.selectActiveFeature(n);
		assertEquals(0.91,vCirlculator.getActiveFeatureCurrent(),0.001);
		assertEquals(1620.0,vCirlculator.getActiveFeatureRotatePerMinutes(),0.001);
		assertEquals(190.0,vCirlculator.getActiveFeaturePower(),0.001);


		// ADD 1 feature + Display features
		System.out.println("\n---> ADD 1 feature at the end of the list");
		vCirlculator.addFeatures(50, 5000, 500);
		for (int i=0;i<vCirlculator.getNbOfFeatures();i++) {
			vCirlculator.selectActiveFeature(i);
			System.out.println(" Feature (" + i + ")");
			System.out.println("    Current =" + vCirlculator.getActiveFeatureCurrent());
			System.out.println("    RotatePerMinutes  =" + vCirlculator.getActiveFeatureRotatePerMinutes());
			System.out.println("    Power  =" + vCirlculator.getActiveFeaturePower());
		}

		n=3;
		vCirlculator.selectActiveFeature(n);
		assertEquals(50.0,vCirlculator.getActiveFeatureCurrent(),0.001);
		assertEquals(5000.0,vCirlculator.getActiveFeatureRotatePerMinutes(),0.001);
		assertEquals(500.0,vCirlculator.getActiveFeaturePower(),0.001);

		// construct JSON object
		System.out.println("\n---> Construct JSON data (with the new element !)");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vCirlculator.getJsonObject();
		System.out.println(jsonObj);

		// Create a new instance + without Features 
		System.out.println("\n---> New Instance + without features");
		Circulator vCirlculatorNew = new Circulator("Toto",2000);
		System.out.println("\n---> Nb features =" + vCirlculatorNew.getNbOfFeatures());
		
		// ADD 1 feature + Display features
		System.out.println("\n---> ADD 1 feature at the end of the list");
		vCirlculatorNew.addFeatures(50, 5000, 500);
		for (int i=0;i<vCirlculatorNew.getNbOfFeatures();i++) {
			vCirlculatorNew.selectActiveFeature(i);
			System.out.println(" Feature (" + i + ")");
			System.out.println("    Current =" + vCirlculatorNew.getActiveFeatureCurrent());
			System.out.println("    RotatePerMinutes  =" + vCirlculatorNew.getActiveFeatureRotatePerMinutes());
			System.out.println("    Power  =" + vCirlculatorNew.getActiveFeaturePower());
		}

		// Modify Feature at position 0
		System.out.println("\n---> Modify Feature at position 0");
		vCirlculatorNew.modifyActiveFeatures(10, 1000, 100);
		for (int i=0;i<vCirlculatorNew.getNbOfFeatures();i++) {
			vCirlculatorNew.selectActiveFeature(i);
			System.out.println(" Feature (" + i + ")");
			System.out.println("    Current =" + vCirlculatorNew.getActiveFeatureCurrent());
			System.out.println("    RotatePerMinutes  =" + vCirlculatorNew.getActiveFeatureRotatePerMinutes());
			System.out.println("    Power  =" + vCirlculatorNew.getActiveFeaturePower());
		}

		// set Class with the JSON object
		System.out.println("\n---> Set the Instance Class with the JSON data");
		vCirlculatorNew.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		// Display the features afterwards 
		System.out.println("\n---> Class Instance");
		System.out.println("    Name = " + vCirlculator.getName());
		System.out.println("    Voltage = " + vCirlculator.getVoltage());
		System.out.println("    --> Display features");
		for (int i=0;i<vCirlculatorNew.getNbOfFeatures();i++) {
			vCirlculatorNew.selectActiveFeature(i);
			System.out.println(" Feature (" + i + ")");
			System.out.println("    Current =" + vCirlculatorNew.getActiveFeatureCurrent());
			System.out.println("    RotatePerMinutes  =" + vCirlculatorNew.getActiveFeatureRotatePerMinutes());
			System.out.println("    Power  =" + vCirlculatorNew.getActiveFeaturePower());
		}

		System.out.println("\n--->Transfer Function");
		HeatTransferFluid vFluid = new HeatTransferFluid() ;
		vFluid.setT(25);
		System.out.println("    Input --> Output");
		System.out.println("    " + vFluid.getT()+"°C-->"+vCirlculator.transfer(vFluid).getT()+"°C");		

	}
}
