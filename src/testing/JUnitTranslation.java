package testing;

//import static org.junit.Assert.*;

import org.junit.Test;

import translation.TCompressor;
import translation.TLanguage;

public class JUnitTranslation {

	@Test
	public void test() {
		
		System.out.println("TEST PAC-Tool CONFIGURATION");

		System.out.println(TCompressor.COMP_CAPACITY.getLangue(TLanguage.ENGLICH));
		System.out.println(TCompressor.COMP_CAPACITY.getLangue(TLanguage.FRENCH));
		System.out.println(TCompressor.COMP_CAPACITY.getLangue(TLanguage.GERMAN));

	}

}
