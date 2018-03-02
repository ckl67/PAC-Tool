package testing.misc;

//import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import misc.Misc;

public class JUnitMisc {

	@Test
	public void test() {
		
		System.out.println("MISC !");

		System.out.println("Dergé --> Farenheit : " + 10 + "-->" + Misc.degre2farenheit(10));
		System.out.println("BTU   --> Watt:" + 1 +"-->" + Misc.btuhr2watt(1) );

		System.out.println("Number of . in "+ "12.23.45 = " + Misc.nbCharInString("12.23.45", '.'));

		System.out.println("Test Closest in the list");
		List<Double> list = Arrays.asList(10.0, -23.0, 20.2, 30.3, -40.0, 40.2, 50.0, -2.0);
		System.out.println("  "+list);	
		System.out.println("  "+Misc.closestInL(-4, list));
		System.out.println("  "+Misc.closestInL(15, list));

		System.out.println("Max Intervall (between 2 consecutive elements) in the List");
		list = Arrays.asList(10.0, 11.2, 13.0, 15.0);
		System.out.println("  "+list);
		System.out.println("  "+Misc.maxIntervalL(list));
	}

}
