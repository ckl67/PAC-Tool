package testing.refrigerant;

import static org.junit.Assert.*;

import org.junit.Test;

import refrigerant.SaturationTable;

public class JUnitRefrigant {

	@Test
	public void test() {

		new SaturationTable("D:/Users/kluges1/workspace/pac-tool/ressources/R407/R407C/Saturation Table R407C.txt");
	}

}
