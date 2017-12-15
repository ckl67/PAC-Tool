package testing.pac;

import static org.junit.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.junit.Test;

import pac.Compressor;
import pac.Pac;
import refrigerant.Refrigerant;

public class JUnitCompressor {

	@Test
	public void test() {
		
		Pac pac = new Pac();
		
		pac.selectCurrentCompressor(0);
		assertEquals("ZR40K3-PFG",pac.getCurrentCompressor().getName());

		pac.getCurrentCompressor().setName("Toto");
		assertEquals("Toto",pac.getCurrentCompressor().getName());

		pac.addNewCompressor(1);
		pac.selectCurrentCompressor(1);
		assertEquals("ZR40K3-PFG",pac.getCurrentCompressor().getName());

		assertEquals("Toto",pac.getCompressorNb(0).getName());

		assertEquals(2,pac.getNbOfCompressorNb());

		pac.removeCompressor(1);
		assertEquals(1,pac.getNbOfCompressorNb());
		
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

		Refrigerant vRefrigeranR22 =  new Refrigerant();
		vRefrigeranR22.setRfgP(25);
		vCompressor.setDeltaP(40);

		System.out.println("\n--->Transfer Function");
		System.out.println("    Input --> Output");
		System.out.println("    "+vRefrigeranR22.getRfgP() + "-->" + vCompressor.transfer(vRefrigeranR22).getRfgP());
		
	}
}
