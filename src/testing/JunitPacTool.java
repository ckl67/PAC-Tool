package testing;

import org.junit.Test;

import testing.measurePoint.JUnitMeasurePoint;
import testing.pac.JUnitPac;


public class JunitPacTool {

	@Test
	public void test() {
		
		JUnitPac jtPac = new JUnitPac();
		JUnitEnthalpy jtEnthalpy = new JUnitEnthalpy();
		JunitEnthalpyBkgdImg jtEnthalpyBkgdImg = new JunitEnthalpyBkgdImg();
		JUnitMeasurePoint jtMeasurePoint = new JUnitMeasurePoint();
		JUnitMisc jtMisc = new JUnitMisc();
		JUnitTranslation jtThesaurus = new JUnitTranslation();
		
		jtPac.test();
		jtEnthalpy.test();
		jtEnthalpyBkgdImg.test();
		jtMeasurePoint.test();
		jtMisc.test();
		jtThesaurus.test();
		
	}

}
