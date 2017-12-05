package testing.refrigerant;

import org.junit.Test;
import refrigerant.Refrigerant;

public class JUnitRefrigant {

	@Test
	public void test() {

		Refrigerant refrigerant = new Refrigerant("D:/Users/kluges1/workspace/pac-tool/ressources/R407/R407C/Saturation Table R407C.txt");
		//Refrigerant refrigerant = new Refrigerant("D:/Users/kluges1/workspace/pac-tool/ressources/R22/Saturation Table R22.txt");
		System.out.println(refrigerant.getName());

		double T=80;
		System.out.println(	"T= " + T + "°C " +  
				" --> P Saturation(gas)= "     + refrigerant.convSatT2P(T).getPGas()    + "(bar)" +
				" --> P Saturation (Liquid)= " + refrigerant.convSatT2P(T).getPLiquid() + "(bar)");


		double P=39.808;
		System.out.println(	"P= " +P + "(bar) " +  
				" --> T Saturation(gas)= "     + refrigerant.convSatP2T(P).getTGas()    + "(°C)" +
				" --> T Saturation (Liquid)= " + refrigerant.convSatP2T(P).getTLiquid() + "(°C)");

	}

}
