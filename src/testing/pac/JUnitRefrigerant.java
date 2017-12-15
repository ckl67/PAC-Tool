package testing.pac;

//import static org.junit.Assert.*;

import org.junit.Test;

import refrigerant.Refrigerant;

public class JUnitRefrigerant {

	@Test
	public void test() {
		
		System.out.println("TEST REFRIGERANT");

		Refrigerant vRefrigerant = new Refrigerant();

		vRefrigerant.setRfgT(45);
		vRefrigerant.setRfgP(25);
		System.out.println("Temp. / Pression");
		System.out.println(vRefrigerant.getRfgT()+"°C -- "+vRefrigerant.getRfgP()+"bar");

	}

}
