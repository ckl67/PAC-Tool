package testing;

import org.junit.Test;
import testing.pac.JUnitCirculator;
import testing.pac.JUnitCompressor;
import testing.pac.JUnitCondenser;
import testing.pac.JUnitDehydrator;
import testing.pac.JUnitEvaporator;
import testing.pac.JUnitExpansionValve;
import testing.pac.JUnitHeatSrcDistrCircuit;
import testing.pac.JUnitHeatTransferFluid;
import testing.pac.JUnitPac;
import testing.refrigerant.JUnitRefrigant;

public class JUnitAll {

	@Test
	public void test() {
		new JUnitRefrigant().test();
		new JUnitCirculator().test() ;
		new JUnitCompressor().test() ;
		new JUnitCondenser().test() ;
		new JUnitDehydrator().test() ;
		new JUnitEvaporator().test() ;
		new JUnitExpansionValve().test() ;
		new JUnitHeatSrcDistrCircuit().test() ;
		new JUnitHeatTransferFluid().test() ;
		new JUnitPac().test();
		
		
	}

}
