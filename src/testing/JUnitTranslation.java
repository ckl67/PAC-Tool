package testing;

//import static org.junit.Assert.*;

import org.junit.Test;

import translation.Translation;

public class JUnitTranslation {

	@Test
	public void test() {
		
		System.out.println("TEST PAC-Tool CONFIGURATION");

		System.out.println(Translation.Capacity.getLangue(Translation._ENGLICH));
		System.out.println(Translation.Capacity.getLangue(Translation._FRENCH));

	}

}
