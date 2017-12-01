package testing.pac;

//import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import coolant.HeatTransferFluid;

public class JUnitHeatTransferFluid {

	@Test
	public void test() {
		System.out.println("TEST HEATTRANSFER FLUID");

		HeatTransferFluid vHeatTransferFluid = new HeatTransferFluid();
		vHeatTransferFluid.setP(44);
		vHeatTransferFluid.setT(55);

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vHeatTransferFluid.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance ");
		vHeatTransferFluid.setName("Toto");
		System.out.println("    Name ="+vHeatTransferFluid.getName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		vHeatTransferFluid.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vHeatTransferFluid.getName());

		System.out.println(vHeatTransferFluid.getName());	
		System.out.println("Temp. / Pression");
		System.out.println(vHeatTransferFluid.getT()+"°C -- "+vHeatTransferFluid.getP()+"bar");		
	}

}
