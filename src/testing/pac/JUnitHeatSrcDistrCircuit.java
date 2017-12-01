package testing.pac;

//import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import coolant.HeatTransferFluid;
import pac.HeatSrcDistrCircuit;

public class JUnitHeatSrcDistrCircuit {

	@Test
	public void test() {
		
		System.out.println("TEST HEAT SOURCE and DISTRIBUTION CIRCUIT");

		HeatSrcDistrCircuit vHeatCircuit = new HeatSrcDistrCircuit();
		vHeatCircuit.setDeltaT(-50);
		vHeatCircuit.setName("Chauffage Maison");
		System.out.println(vHeatCircuit.getName());

		HeatTransferFluid vFluid = new HeatTransferFluid(); 

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vHeatCircuit.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance ");
		vHeatCircuit.setName("Toto");
		System.out.println("    Name ="+vHeatCircuit.getName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		vHeatCircuit.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vHeatCircuit.getName());

		vFluid.setT(25);
		System.out.println("Input --> Output");
		System.out.println(vFluid.getT()+"°C-->"+vHeatCircuit.transfer(vFluid).getT()+"°C");
	}

}
