package testing;

//import static org.junit.Assert.*;

import org.junit.Test;

import translation.Thesaurus;

public class JUnitThesaurus {

	@Test
	public void test() {
		
		System.out.println("TEST PAC-Tool CONFIGURATION");

		Thesaurus thesaurus = new Thesaurus();

		System.out.println(thesaurus.TranslateText("Capacity","eng"));
		System.out.println(thesaurus.TranslateText("Power","eng"));

		System.out.println(thesaurus.TranslateText("Capacity","fr"));
		System.out.println(thesaurus.TranslateText("Power","fr"));
		System.out.println(thesaurus.TranslateText("Capacitydd","fr"));

	}

}
