package testing;

//import static org.junit.Assert.*;

import org.junit.Test;

import translation.Translation;

public class JUnitTranslation {

	@Test
	public void test() {
		
		System.out.println("TEST PAC-Tool CONFIGURATION");

		System.out.println(Translation.COMP_CAPACITY.getLangue(Translation._ENGLICH));
		System.out.println(Translation.COMP_CAPACITY.getLangue(Translation._FRENCH));

	}

}
