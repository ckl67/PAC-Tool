package testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import pac.Pac;

public class JTPac {

	@Test
	public void test() {

		System.out.println("TEST COMPUTE PAC");

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

	}

}
