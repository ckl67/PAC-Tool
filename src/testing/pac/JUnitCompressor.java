package testing.pac;

import org.json.simple.JSONObject;
import org.junit.Test;

import pac.Compressor;
import refrigerant.Refrigerant;

public class JUnitCompressor {

	@Test
	public void test() {
		
		System.out.println("TEST COMPRESSOR");

		Compressor vCompressor = new Compressor();
		System.out.println("    "+vCompressor.getName());

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vCompressor.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance ");
		vCompressor.setName("Toto");
		System.out.println("    Name ="+vCompressor.getName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		vCompressor.setJsonObject(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vCompressor.getName());

		Refrigerant vRefrigeranR22 =  new Refrigerant(
				"D:/Users/kluges1/workspace/pac-tool/ressources/R22/R22 Saturation Table.txt",
				"D:/Users/kluges1/workspace/pac-tool/ressources/R22/R22 IsoTherm Table.txt"	);
		vRefrigeranR22.setRfgP(25);
		vCompressor.setDeltaP(40);

		System.out.println("\n--->Transfer Function");
		System.out.println("    Input --> Output");
		System.out.println("    "+vRefrigeranR22.getRfgP() + "-->" + vCompressor.transfer(vRefrigeranR22).getRfgP());
		
	}
}
