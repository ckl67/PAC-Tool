package testing.refrigerant;

import org.junit.Test;
import refrigerant.Refrigerant;

public class JUnitRefrigant {

	@Test
	public void test() {

		Refrigerant refrigerant = new Refrigerant("D:/Users/kluges1/workspace/pac-tool/ressources/R407/R407C/Saturation Table R407C.txt");
		//Refrigerant refrigerant = new Refrigerant("D:/Users/kluges1/workspace/pac-tool/ressources/R22/Saturation Table R22.txt");
		System.out.println(refrigerant.getName());

		// Conversion T --> P Saturation
		if (refrigerant.getName().equals("R22")) {
			double T=80;
			System.out.println(	"T= " + T + 
					"°C  -->  P Saturation(gas) = " + refrigerant.convSatT2P(T).getPGas() + 
					"(bar)  --> P Saturation (Liquid)= " + refrigerant.convSatT2P(T).getPLiquid() + "(bar)");
		} else if (refrigerant.getName().equals("R407C")) {
			double T=80;
			System.out.println(	"T= " + T + 
					"°C  -->  P Saturation(gas) = " + refrigerant.convSatT2P(T).getPGas() + 
					"(bar)  --> P Saturation (Liquid)= " + refrigerant.convSatT2P(T).getPLiquid() + "(bar)");

		} else {
			System.out.println("Nothing");
		}


		// Conversion P --> T Saturation

	}

}
