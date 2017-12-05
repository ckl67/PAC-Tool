package testing.enthalpy;

import static org.junit.Assert.*;
import org.junit.Test;
import enthalpy.Enthalpy;
import enthalpy.SaturationTable;

public class JUnitEnthalpy {

	@Test
	public void test() {
		double tmp;
		
		System.out.println("TEST ENTHALPY");

		// Read Saturation Table of R407C
		Enthalpy enthalpy = new Enthalpy("D:/Users/kluges1/workspace/pac-tool/ressources/R407/R407C/Saturation Table R407C.txt");
		
		//Test findNearestItem 
		tmp = enthalpy.findNearestItem(SatTable.Temperature, -189.45);
		System.out.println(tmp);
		assertEquals(tmp, -118.0, 0.001);
		
		tmp = enthalpy.findNearestItem(SatTable.Temperature, 3.45);
		System.out.println(tmp);
		assertEquals(tmp, 3.0, 0.001);

		tmp = enthalpy.findNearestItem(SatTable.Temperature, 75.3);
		System.out.println(tmp);
		assertEquals(tmp, 75.0, 0.001);

		tmp = enthalpy.findNearestItem(SatTable.Temperature, 95.3);
		System.out.println(tmp);
		assertEquals(tmp, 80.0, 0.001);

		tmp = enthalpy.findNearestItem(SatTable.Temperature, 0, SatTable.PressureL);
		System.out.println(tmp);
		tmp = enthalpy.findNearestItem(SatTable.Temperature, 0, SatTable.PressureG);
		System.out.println(tmp);
		
		//enthalpy.ChangeGas("D:/Users/kluges1/workspace/pac-tool/ressources/R22/Saturation Table R22.txt");
			
		
	}

}
