package testing;

import org.junit.Test;

import testing.pac.JUnitCirculator;
import testing.pac.JUnitCompressor;
import testing.pac.JUnitCondenser;
import testing.pac.JUnitDehydrator;
import testing.pac.JUnitExpansionValve;
import testing.pac.JUnitHeatSrcDistrCircuit;
import testing.pac.JUnitHeatTransferFluid;
import testing.pac.JUnitRefrigerant;

public class JUnitPac {

	@Test
	public void test() {

		System.out.println("TEST COMPUTE PAC");

		JUnitCompressor jtCompressor = new JUnitCompressor();
		JUnitCirculator jtCirculator = new JUnitCirculator();
		JUnitDehydrator jtDehydrator = new JUnitDehydrator();
		JUnitCondenser jtCondenser = new JUnitCondenser();
		JUnitExpansionValve jtExpansionValve = new JUnitExpansionValve();
		JUnitHeatSrcDistrCircuit jtHeatSrcDistrCircuit = new JUnitHeatSrcDistrCircuit();
		JUnitHeatTransferFluid jtHeatTransferFluid = new JUnitHeatTransferFluid();
		JUnitRefrigerant jtRefrigerant = new JUnitRefrigerant();
			
		jtCompressor.test();
		jtCirculator.test();
		jtDehydrator.test();
		jtCondenser.test();
		jtExpansionValve.test();
		jtHeatSrcDistrCircuit.test();
		jtHeatTransferFluid.test();
		jtRefrigerant.test();
		
		
		
		/*
				System.out.println("\n---> Read PAC");
		System.out.println("    Name CirculatorSrc =" + pac.getCirculatorSrcL().get(0).getName());
		System.out.println("    Name CirculatorDistr =" + pac.getCirculatorDistrL().get(0).getName());
		System.out.println("    Name Compressor =" + pac.getCompressorL().get(0).getName());
		System.out.println("    Name Condenser =" + pac.getCondenserL().get(0).getName());
		System.out.println("    Name Dehydrator =" + pac.getDehydratorL().get(0).getName());
		System.out.println("    Name Evaporator =" + pac.getEvaporatorL().get(0).getName());
		System.out.println("    Name ExpansionVa =" + pac.getExpansionValveL().get(0).getName());
		System.out.println("    Name CircuitSrc =" + pac.getCircuitSrcL().get(0).getName());
		System.out.println("    Name CircuitDistr =" + pac.getCircuitDistrL().get(0).getName());
		System.out.println("    Name FluidCaloDistr =" + pac.getFluidCaloDistrL().get(0).getName());
		System.out.println("    Name FluidCaloSrc =" + pac.getFluidCaloSrcL().get(0).getName());
		System.out.println("    Name FluidRefri =" + pac.getFluidRefriL().get(0).getName());

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = pac.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify all instances ");
		pac.getCirculatorSrcL().get(0).setName("Toto1");
		pac.getCirculatorDistrL().get(0).setName("Toto2");
		pac.getCompressorL().get(0).setName("Toto3");
		pac.getCondenserL().get(0).setName("Toto4");
		pac.getDehydratorL().get(0).setName("Toto5");
		pac.getEvaporatorL().get(0).setName("Toto6");
		pac.getExpansionValveL().get(0).setName("Toto7");
		pac.getCircuitSrcL().get(0).setName("Toto8");
		pac.getCircuitDistrL().get(0).setName("Toto9");
		pac.getFluidCaloDistrL().get(0).setName("Toto10");
		pac.getFluidCaloSrcL().get(0).setName("Toto11");
		pac.getFluidRefriL().get(0).setName("Toto12");

		System.out.println("    Name CirculatorSrc =" + pac.getCirculatorSrcL().get(0).getName());
		System.out.println("    Name CirculatorDistr =" + pac.getCirculatorDistrL().get(0).getName());
		System.out.println("    Name Compressor =" + pac.getCompressorL().get(0).getName());
		System.out.println("    Name Condenser =" + pac.getCondenserL().get(0).getName());
		System.out.println("    Name Dehydrator =" + pac.getDehydratorL().get(0).getName());
		System.out.println("    Name Evaporator =" + pac.getEvaporatorL().get(0).getName());
		System.out.println("    Name ExpansionVa =" + pac.getExpansionValveL().get(0).getName());
		System.out.println("    Name CircuitSrc =" + pac.getCircuitSrcL().get(0).getName());
		System.out.println("    Name CircuitDistr =" + pac.getCircuitDistrL().get(0).getName());
		System.out.println("    Name FluidCaloDistr =" + pac.getFluidCaloDistrL().get(0).getName());
		System.out.println("    Name FluidCaloSrc =" + pac.getFluidCaloSrcL().get(0).getName());
		System.out.println("    Name FluidRefri =" + pac.getFluidRefriL().get(0).getName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		pac.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name CirculatorSrc =" + pac.getCirculatorSrcL().get(0).getName());
		System.out.println("    Name CirculatorDistr =" + pac.getCirculatorDistrL().get(0).getName());
		System.out.println("    Name Compressor =" + pac.getCompressorL().get(0).getName());
		System.out.println("    Name Condenser =" + pac.getCondenserL().get(0).getName());
		System.out.println("    Name Dehydrator =" + pac.getDehydratorL().get(0).getName());
		System.out.println("    Name Evaporator =" + pac.getEvaporatorL().get(0).getName());
		System.out.println("    Name ExpansionVa =" + pac.getExpansionValveL().get(0).getName());
		System.out.println("    Name CircuitSrc =" + pac.getCircuitSrcL().get(0).getName());
		System.out.println("    Name CircuitDistr =" + pac.getCircuitDistrL().get(0).getName());
		System.out.println("    Name FluidCaloDistr =" + pac.getFluidCaloDistrL().get(0).getName());
		System.out.println("    Name FluidCaloSrc =" + pac.getFluidCaloSrcL().get(0).getName());
		System.out.println("    Name FluidRefri =" + pac.getFluidRefriL().get(0).getName());

		System.out.println("Very very simple simulation of the PAC Cycle");
		System.out.println("We start to configure the different elements");
		System.out.println("Compressor : deltaP=+52, deltaT=+25");
		pac.getCompressorL().get(0).setDeltaP(52);
		pac.getCompressorL().get(0).setDeltaT(25);
		System.out.println("Condenser : deltaT=-10");
		pac.getCondenserL().get(0).setDeltaT(-10);
		System.out.println("Expansion Valve : deltaT=-7 , deltaP=-33");
		pac.getExpansionValveL().get(0).setDeltaP(-33);
		pac.getExpansionValveL().get(0).setDeltaT(-7);
		System.out.println("Evaporator : deltaT=22");
		pac.getEvaporatorL().get(0).setDeltaT(+22);

		System.out.println("R22 feature : T=-5, P=5");
		pac.getFluidRefriL().get(0).setT(-5);
		pac.getFluidRefriL().get(0).setP(5);

		System.out.println("Fluid Source : T=2, P=0.5");
		pac.getFluidCaloSrcL().get(0).setT(2);
		pac.getFluidCaloSrcL().get(0).setP(0.5);
		System.out.println("Circuit Source : deltaT=5");
		pac.getCircuitSrcL().get(0).setDeltaT(5);

		System.out.println("Fluid Distribution: T=25, P=0.5");
		pac.getFluidCaloDistrL().get(0).setT(25);
		pac.getFluidCaloDistrL().get(0).setP(0.5);
		System.out.println("Circuit Distribution : deltaT=23");
		pac.getCircuitDistrL().get(0).setDeltaT(23);


		System.out.println();
		System.out.println();
		System.out.println("------------------------------------------------------------------");
		System.out.println("Element which will be simulated --> All First elements" );
		pac.getPacComponentId();
		
		
		for (int i=0;i<4;i++) {
			System.out.println("------------------------------------------------------------------");
			System.out.println("PacTool cycle:"+i+" by ");
			System.out.println("     Injecting R22 (T="+pac.getFluidRefriL().get(0).getT()+";P="+pac.getFluidRefriL().get(0).getP()+") in Compressor");
			System.out.println("     Injecting Fluid Source (T="+pac.getFluidCaloSrcL().get(0).getT()+";P="+pac.getFluidCaloSrcL().get(0).getP()+") in Circulateur");
			System.out.println("     Injecting Fluid Distribution(T="+pac.getFluidCaloDistrL().get(0).getT()+";P="+pac.getFluidCaloDistrL().get(0).getP()+") in Circulateur");

			pac.PacCycle(PacGasInjected.COMPRESSOR);

			System.out.println("Output result for cycle:"+i+" ");
			System.out.println("     R22 T=" + pac.getFluidRefriL().get(0).getT() + "  P="+ pac.getFluidRefriL().get(0).getP());
			System.out.println("     Fluid S T=" + pac.getFluidCaloSrcL().get(0).getT() + "  P="+ pac.getFluidCaloSrcL().get(0).getP());
			System.out.println("     Fluid D T=" + pac.getFluidCaloDistrL().get(0).getT() + "  P="+ pac.getFluidCaloDistrL().get(0).getP());
		}

		System.out.println("------------------------------------------------------------------");
		System.out.println("Add a compressor");
		System.out.println("------------------------------------------------------------------");
		pac.getCompressorL().add(1, new Compressor());
		System.out.println("\n---> Construct JSON data");
		jsonObj = new JSONObject();
		jsonObj = pac.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("Very very simple simulation of the PAC Cycle");
		System.out.println("We start to configure COMPRESSOR 1 ");
		System.out.println("Compressor : deltaP=+10000, deltaT=+100");
		pac.getCompressorL().get(1).setDeltaP(10000);
		pac.getCompressorL().get(1).setDeltaT(100);
		System.out.println("Condenser : deltaT=-10");
		pac.getCondenserL().get(0).setDeltaT(-10);
		System.out.println("Expansion Valve : deltaT=-7 , deltaP=-33");
		pac.getExpansionValveL().get(0).setDeltaP(-33);
		pac.getExpansionValveL().get(0).setDeltaT(-7);
		System.out.println("Evaporator : deltaT=22");
		pac.getEvaporatorL().get(0).setDeltaT(+22);

		System.out.println("R22 feature : T=-5, P=5");
		pac.getFluidRefriL().get(0).setT(-5);
		pac.getFluidRefriL().get(0).setP(5);

		System.out.println("Fluid Source : T=2, P=0.5");
		pac.getFluidCaloSrcL().get(0).setT(2);
		pac.getFluidCaloSrcL().get(0).setP(0.5);
		System.out.println("Circuit Source : deltaT=5");
		pac.getCircuitSrcL().get(0).setDeltaT(5);

		System.out.println("Fluid Distribution: T=25, P=0.5");
		pac.getFluidCaloDistrL().get(0).setT(25);
		pac.getFluidCaloDistrL().get(0).setP(0.5);
		System.out.println("Circuit Distribution : deltaT=23");
		pac.getCircuitDistrL().get(0).setDeltaT(23);

		pac.SelectCompressor(1);
		pac.getPacComponentId();
		
		System.out.println("------------------------------------------------------------------");
		System.out.println("Element which will be simulated --> All First elements EXCEPT COMPRESSOR 1" );

		for (int i=0;i<4;i++) {
			System.out.println("------------------------------------------------------------------");
			System.out.println("PacTool cycle:"+i+" by ");
			System.out.println("     Injecting R22 (T="+pac.getFluidRefriL().get(0).getT()+";P="+pac.getFluidRefriL().get(0).getP()+") in Compressor");
			System.out.println("     Injecting Fluid Source (T="+pac.getFluidCaloSrcL().get(0).getT()+";P="+pac.getFluidCaloSrcL().get(0).getP()+") in Circulateur");
			System.out.println("     Injecting Fluid Distribution(T="+pac.getFluidCaloDistrL().get(0).getT()+";P="+pac.getFluidCaloDistrL().get(0).getP()+") in Circulateur");

			pac.PacCycle(PacGasInjected.COMPRESSOR);

			System.out.println("Output result for cycle:"+i+" ");
			System.out.println("     R22 T=" + pac.getFluidRefriL().get(0).getT() + "  P="+ pac.getFluidRefriL().get(0).getP());
			System.out.println("     Fluid S T=" + pac.getFluidCaloSrcL().get(0).getT() + "  P="+ pac.getFluidCaloSrcL().get(0).getP());
			System.out.println("     Fluid D T=" + pac.getFluidCaloDistrL().get(0).getT() + "  P="+ pac.getFluidCaloDistrL().get(0).getP());
		}
		
		
		*/
	}

}
