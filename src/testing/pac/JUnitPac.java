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
		
		pac.addNewCompressor(1);
		pac.selectCurrentCompressor(1);

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = pac.getJsonObject();
		System.out.println(jsonObj);
		
		pac.removeCompressor(1);
		System.out.println(pac.getNbOfCompressorNb());
		
		pac.setJsonObject(jsonObj);
		System.out.println(jsonObj);
		System.out.println(pac.getNbOfCompressorNb());
		
		pac.getCurrentCircuitDistr().getName();

		System.out.println("\n---> Read PAC");
		System.out.println("    Name CirculatorSrc =" + pac.getCurrentCircuitDistr().getName());
		System.out.println("    Name CirculatorDistr =" + pac.getCurrentCirculatorDistr().getName());
		System.out.println("    Name Compressor =" + pac.getCurrentCompressor().getName());
		System.out.println("    Name Condenser =" + pac.getCurrentCondenser().getName());
		System.out.println("    Name Dehydrator =" + pac.getCurrentDehydrator().getName());
		System.out.println("    Name Evaporator =" + pac.getCurrentEvaporator().getName());
		System.out.println("    Name ExpansionVa =" + pac.getCurrentExpansionValve().getName());
		System.out.println("    Name CircuitSrc =" + pac.getCurrentCircuitSrc().getName());
		System.out.println("    Name CircuitDistr =" + pac.getCurrentCircuitDistr().getName());
	System.out.println("    Name FluidCaloSrc =" + pac.getCurrentFluidCaloSrc().getName());
		System.out.println("    Name FluidRefri =" + pac.getCurrentFluidRefri().getRfgName());

		System.out.println("\n---> Construct JSON data");
		//JSONObject jsonObj = new JSONObject();
		jsonObj = pac.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify all instances ");
		pac.getCurrentCirculatorSrc().setName("Toto1");
		pac.getCurrentCirculatorDistr().setName("Toto2");
		pac.getCurrentCompressor().setName("Toto3");
		pac.getCurrentCondenser().setName("Toto4");
		pac.getCurrentDehydrator().setName("Toto5");
		pac.getCurrentEvaporator().setName("Toto6");
		pac.getCurrentExpansionValve().setName("Toto7");
		pac.getCurrentCircuitSrc().setName("Toto8");
		pac.getCurrentCircuitDistr().setName("Toto9");

		System.out.println("    Name CirculatorSrc =" + pac.getCurrentCirculatorSrc().getName());
		System.out.println("    Name CirculatorDistr =" + pac.getCurrentCirculatorDistr().getName());
		System.out.println("    Name Compressor =" + pac.getCurrentCompressor().getName());
		System.out.println("    Name Condenser =" + pac.getCurrentCondenser().getName());
		System.out.println("    Name Dehydrator =" + pac.getCurrentDehydrator().getName());
		System.out.println("    Name Evaporator =" + pac.getCurrentEvaporator().getName());
		System.out.println("    Name ExpansionVa =" + pac.getCurrentExpansionValve().getName());
		System.out.println("    Name CircuitSrc =" + pac.getCurrentCircuitSrc().getName());
		System.out.println("    Name CircuitDistr =" + pac.getCurrentCircuitDistr().getName());
		System.out.println("    Name FluidCaloDistr =" + pac.getCurrentFluidCaloDistr().getName());
		System.out.println("    Name FluidCaloSrc =" + pac.getCurrentFluidCaloSrc().getName());
		System.out.println("    Name FluidRefri =" + pac.getCurrentFluidRefri().getRfgName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		pac.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name CirculatorSrc =" + pac.getCurrentCirculatorSrc().getName());
		System.out.println("    Name CirculatorDistr =" + pac.getCurrentCirculatorDistr().getName());
		System.out.println("    Name Compressor =" + pac.getCurrentCompressor().getName());
		System.out.println("    Name Condenser =" + pac.getCurrentCondenser().getName());
		System.out.println("    Name Dehydrator =" + pac.getCurrentDehydrator().getName());
		System.out.println("    Name Evaporator =" + pac.getCurrentEvaporator().getName());
		System.out.println("    Name ExpansionVa =" + pac.getCurrentExpansionValve().getName());
		System.out.println("    Name CircuitSrc =" + pac.getCurrentCircuitSrc().getName());
		System.out.println("    Name CircuitDistr =" + pac.getCurrentCircuitDistr().getName());
		System.out.println("    Name FluidCaloDistr =" + pac.getCurrentFluidCaloDistr().getName());
		System.out.println("    Name FluidCaloSrc =" + pac.getCurrentFluidCaloSrc().getName());
		System.out.println("    Name FluidRefri =" + pac.getCurrentFluidRefri().getRfgName());

		System.out.println("Very very simple simulation of the PAC Cycle");
		System.out.println("We start to configure the different elements");
		System.out.println("Compressor : deltaP=+52, deltaT=+25");
		pac.getCurrentCompressor().setDeltaP(52);
		pac.getCurrentCompressor().setDeltaT(25);
		System.out.println("Condenser : deltaT=-10");
		pac.getCurrentCondenser().setDeltaT(-10);
		System.out.println("Expansion Valve : deltaT=-7 , deltaP=-33");
		pac.getCurrentExpansionValve().setDeltaP(-33);
		pac.getCurrentExpansionValve().setDeltaT(-7);
		System.out.println("Evaporator : deltaT=22");
		pac.getCurrentEvaporator().setDeltaT(+22);

		System.out.println("R22 feature : T=-5, P=5");
		pac.getCurrentFluidRefri().setRfgT(-5);
		pac.getCurrentFluidRefri().setRfgP(5);

		System.out.println("Fluid Source : T=2, P=0.5");
		pac.getCurrentFluidCaloSrc().setT(2);
		pac.getCurrentFluidCaloSrc().setP(0.5);
		System.out.println("Circuit Source : deltaT=5");
		pac.getCurrentCircuitSrc().setDeltaT(5);

		System.out.println("Fluid Distribution: T=25, P=0.5");
		pac.getCurrentFluidCaloDistr().setT(25);
		pac.getCurrentFluidCaloDistr().setP(0.5);
		System.out.println("Circuit Distribution : deltaT=23");
		pac.getCurrentCircuitDistr().setDeltaT(23);


		System.out.println();
		System.out.println();
		System.out.println("------------------------------------------------------------------");
		System.out.println("Element which will be simulated --> All First elements" );
		pac.getPacComponentId();
		
		
		for (int i=0;i<4;i++) {
			System.out.println("------------------------------------------------------------------");
			System.out.println("PacTool cycle:"+i+" by ");
			System.out.println("     Injecting R22 (T="+pac.getCurrentFluidRefri().getRfgT()+";P="+pac.getCurrentFluidRefri().getRfgP()+") in Compressor");
			System.out.println("     Injecting Fluid Source (T="+pac.getCurrentFluidCaloSrc().getT()+";P="+pac.getCurrentFluidCaloSrc().getP()+") in Circulateur");
			System.out.println("     Injecting Fluid Distribution(T="+pac.getCurrentFluidCaloDistr().getT()+";P="+pac.getCurrentFluidCaloDistr().getP()+") in Circulateur");

			pac.PacCycle(PacGasInjected.COMPRESSOR);

			System.out.println("Output result for cycle:"+i+" ");
			System.out.println("     R22 T=" + pac.getCurrentFluidRefri().getRfgT() + "  P="+ pac.getCurrentFluidRefri().getRfgP());
			System.out.println("     Fluid S T=" + pac.getCurrentFluidCaloSrc().getT() + "  P="+ pac.getCurrentFluidCaloSrc().getP());
			System.out.println("     Fluid D T=" + pac.getCurrentFluidCaloDistr().getT() + "  P="+ pac.getCurrentFluidCaloDistr().getP());
		}

		System.out.println("------------------------------------------------------------------");
		System.out.println("Add a compressor");
		System.out.println("------------------------------------------------------------------");
		pac.addNewCompressor(1);
		System.out.println("\n---> Construct JSON data");
		jsonObj = new JSONObject();
		jsonObj = pac.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("Very very simple simulation of the PAC Cycle");
		System.out.println("We start to configure COMPRESSOR 1 ");
		System.out.println("Compressor : deltaP=+10000, deltaT=+100");
		pac.getCompressorNb(1).setDeltaP(10000);
		pac.getCompressorNb(1).setDeltaT(100);
		System.out.println("Condenser : deltaT=-10");
		pac.getCurrentCondenser().setDeltaT(-10);
		System.out.println("Expansion Valve : deltaT=-7 , deltaP=-33");
		pac.getCurrentExpansionValve().setDeltaP(-33);
		pac.getCurrentExpansionValve().setDeltaT(-7);
		System.out.println("Evaporator : deltaT=22");
		pac.getCurrentEvaporator().setDeltaT(+22);

		System.out.println("R22 feature : T=-5, P=5");
		pac.getCurrentFluidRefri().setRfgT(-5);
		pac.getCurrentFluidRefri().setRfgP(5);

		System.out.println("Fluid Source : T=2, P=0.5");
		pac.getCurrentFluidCaloSrc().setT(2);
		pac.getCurrentFluidCaloSrc().setP(0.5);
		System.out.println("Circuit Source : deltaT=5");
		pac.getCurrentCircuitSrc().setDeltaT(5);

		System.out.println("Fluid Distribution: T=25, P=0.5");
		pac.getCurrentFluidCaloDistr().setT(25);
		pac.getCurrentFluidCaloDistr().setP(0.5);
		System.out.println("Circuit Distribution : deltaT=23");
		pac.getCurrentCircuitDistr().setDeltaT(23);

		pac.selectCurrentCompressor(1);
		pac.getPacComponentId();
		
		System.out.println("------------------------------------------------------------------");
		System.out.println("Element which will be simulated --> All First elements EXCEPT COMPRESSOR 1" );

		for (int i=0;i<4;i++) {
			System.out.println("------------------------------------------------------------------");
			System.out.println("PacTool cycle:"+i+" by ");
			System.out.println("     Injecting R22 (T="+pac.getCurrentFluidRefri().getRfgT()+";P="+pac.getCurrentFluidRefri().getRfgP()+") in Compressor");
			System.out.println("     Injecting Fluid Source (T="+pac.getCurrentFluidCaloSrc().getT()+";P="+pac.getCurrentFluidCaloSrc().getP()+") in Circulateur");
			System.out.println("     Injecting Fluid Distribution(T="+pac.getCurrentFluidCaloDistr().getT()+";P="+pac.getCurrentFluidCaloDistr().getP()+") in Circulateur");

			pac.PacCycle(PacGasInjected.COMPRESSOR);

			System.out.println("Output result for cycle:"+i+" ");
			System.out.println("     R22 T=" + pac.getCurrentFluidRefri().getRfgT() + "  P="+ pac.getCurrentFluidRefri().getRfgP());
			System.out.println("     Fluid S T=" + pac.getCurrentFluidCaloSrc().getT() + "  P="+ pac.getCurrentFluidCaloSrc().getP());
			System.out.println("     Fluid D T=" + pac.getCurrentFluidCaloDistr().getT() + "  P="+ pac.getCurrentFluidCaloDistr().getP());
		}
		
		

	}

}
