package testing;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import computation.COP;

public class JUnitCOP {

	@Test
	public void test() {
		
		System.out.println("TEST COMPUTE COP");
		COP vCOP = new COP();

		vCOP.setH1(10);
		vCOP.setH2(20);
		vCOP.setH3(30);
		vCOP.setTK(50);
		vCOP.setT0(25);
		
		double out = vCOP.cop_carnot_chaud();
		assertEquals(12.92,out,0.001);
	}
}
