package testing.pac;

import org.json.simple.JSONObject;
import org.junit.Test;

import pac.Pac;
import pac.PacGasInjected;

public class JUnitPac {

	@Test
	public void test() {

		System.out.println("TEST COMPUTE PAC");

		Pac pac = new Pac();
				
		System.out.println("Load R22 Refrigerent feature");
		pac.getRefrigerant().loadRfgGasSaturationData("D:/Users/kluges1/workspace/pac-tool/ressources/R22/Saturation Table R22.txt");

		System.out.println("\n---> Read PAC");
		System.out.println("    Name CirculatorSrc =" + pac.getCirculatorSrc().getName());
		System.out.println("    Name CirculatorDistr =" + pac.getCirculatorDistr().getName());
		System.out.println("    Name Compressor =" + pac.getCompressor().getName());
		System.out.println("    Name Condenser =" + pac.getCondenser().getName());
		System.out.println("    Name Dehydrator =" + pac.getDehydrator().getName());
		System.out.println("    Name Evaporator =" + pac.getEvaporator().getName());
		System.out.println("    Name ExpansionValve =" + pac.getExpansionValve().getName());
		System.out.println("    Name HeatCircuitSrc =" + pac.getHeatSrcCircuit().getName());
		System.out.println("    Name HeatCircuitDistr =" + pac.getHeatDistrCircuit().getName());
		System.out.println("    Name CoolantDistr =" + pac.getCoolantDistr().getName());
		System.out.println("    Name CoolantoSrc =" + pac.getCoolantSrc().getName());
		System.out.println("    Name FluidRefri =" + pac.getRefrigerant().getRfgName());

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = pac.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify all instances ");
		pac.getCirculatorSrc().setName("Toto1");
		pac.getCirculatorDistr().setName("Toto2");
		pac.getCompressor().setName("Toto3");
		pac.getCondenser().setName("Toto4");
		pac.getDehydrator().setName("Toto5");
		pac.getEvaporator().setName("Toto6");
		pac.getExpansionValve().setName("Toto7");
		pac.getHeatSrcCircuit().setName("Toto8");
		pac.getHeatDistrCircuit().setName("Toto9");

		System.out.println("\n---> Set the Class Instance with JSON data");
		pac.setJsonObject(jsonObj);
		System.out.println(jsonObj);


		System.out.println("Very very simple simulation of the PAC Cycle");
		System.out.println("We start to configure the different elements");
		System.out.println("Compressor : deltaP=+52, deltaT=+25");
		pac.getCompressor().setDeltaP(52);
		pac.getCompressor().setDeltaT(25);
		System.out.println("Condenser : deltaT=-10");
		pac.getCondenser().setDeltaT(-10);
		System.out.println("Expansion Valve : deltaT=-7 , deltaP=-33");
		pac.getExpansionValve().setDeltaP(-33);
		pac.getExpansionValve().setDeltaT(-7);
		System.out.println("Evaporator : deltaT=22");
		pac.getEvaporator().setDeltaT(+22);

		System.out.println("R22 feature : T=-5, P=5");
		pac.getRefrigerant().setRfgT(-5);
		pac.getRefrigerant().setRfgP(5);

		System.out.println("Fluid Source : T=2, P=0.5");
		pac.getCoolantSrc().setT(2);
		pac.getCoolantSrc().setP(0.5);
		System.out.println("Circuit Source : deltaT=5");
		pac.getHeatSrcCircuit().setDeltaT(5);

		System.out.println("Fluid Distribution: T=25, P=0.5");
		pac.getCoolantDistr().setT(25);
		pac.getCoolantDistr().setP(0.5);
		System.out.println("Circuit Distribution : deltaT=23");
		pac.getHeatDistrCircuit().setDeltaT(23);


		System.out.println();
		System.out.println();
		System.out.println("------------------------------------------------------------------");
		System.out.println("Element which will be simulated --> All First elements" );

		for (int i=0;i<4;i++) {
			System.out.println("------------------------------------------------------------------");
			System.out.println("PacTool cycle:"+i+" by ");
			System.out.println("     Injecting R22 (T="+pac.getRefrigerant().getRfgT()+";P="+pac.getRefrigerant().getRfgP()+") in Compressor");
			System.out.println("     Injecting Fluid Source (T="+pac.getCoolantSrc().getT()+";P="+pac.getCoolantSrc().getP()+") in Circulateur");
			System.out.println("     Injecting Fluid Distribution(T="+pac.getCoolantDistr().getT()+";P="+pac.getCoolantDistr().getP()+") in Circulateur");

			pac.PacCycle(PacGasInjected.COMPRESSOR);

			System.out.println("Output result for cycle:"+i+" ");
			System.out.println("     R22 T=" + pac.getRefrigerant().getRfgT() + "  P="+ pac.getRefrigerant().getRfgP());
			System.out.println("     Fluid S T=" + pac.getCoolantSrc().getT() + "  P="+ pac.getCoolantSrc().getP());
			System.out.println("     Fluid D T=" + pac.getCoolantDistr().getT() + "  P="+ pac.getCoolantDistr().getP());
		}


	}

}
