package testing;

import org.junit.Test;


public class JunitPacTool {

	@Test
	public void test() {
		
		JUnitPac jtPac = new JUnitPac();
		JUnitCOP jtCOP = new JUnitCOP();
		JUnitEnthalpy jtEnthalpy = new JUnitEnthalpy();
		JunitEnthalpyBkgdImg jtEnthalpyBkgdImg = new JunitEnthalpyBkgdImg();
		JUnitMeasurePoint jtMeasurePoint = new JUnitMeasurePoint();
		JUnitMisc jtMisc = new JUnitMisc();
		JUnitTranslation jtThesaurus = new JUnitTranslation();
		
		jtPac.test();
		jtCOP.test();
		jtEnthalpy.test();
		jtEnthalpyBkgdImg.test();
		jtMeasurePoint.test();
		jtMisc.test();
		jtThesaurus.test();
		
	}

}
