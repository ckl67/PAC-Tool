/*
 * - PAC-Tool - 
 * Tool for understanding basics and computation of PAC (Pompe à Chaleur)
 * Copyright (C) 2016 christian.klugesherz@gmail.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (version 2)
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package pacp;

// ------------------------------------------------------
// Could be fine to create auto-test function
//	assertThat(Misc.closestInL(-4, list), is(-2));
//------------------------------------------------------


import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONObject;
import pacp.Misc;

public class Test {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		boolean loop = true;
		while(loop==true) {
			System.out.println();
			System.out.println(" <==========================================");
			System.out.println("Veuillez saisir la Class à Tester:\n"
					+ "  (1)-> COP Compute\n"
					+ "  (2)-> Circulator\n"
					+ "  (3)-> Compressor\n"
					+ "  (4)-> Condensatorr\n"
					+ "  (5)-> Dehydrator\n"
					+ "  (6)-> Object graphic for Enthalpy (Element elDraw)\n"
					+ "  (7)-> Enthalpy\n"
					+ "  (8)-> EnthalpyBkgdImg Background Image for Enthalpy \n"
					+ "  (9)-> Evaporator\n"
					+ "  (10)-> Expansion Valve\n"
					+ "  (11)-> Head Distribution Circuit \n"
					+ "  (12)-> Head Source Circuit \n"
					+ "  (13)-> Heat Transfert Fluid\n"
					+ "  (14)-> Measure Points\n"
					+ "  (15)-> Miscellaneous\n"
					+ "  (16)-> PAC (Including all elements of a PAC)\n"
					+ "  (17)-> PAC-Tool Configuration information\n"
					+ "  (X)-> Panel Enthalpy\n"
					+ "  (18)-> Prime Windows configuration (Main window) \n"
					+ "  (19)-> Refrigerant Fluid\n"
					+ "  (X)-> Run (Main)\n"
					+ "  (X)-> Test\n"
					+ "  (X)-> Windows (....)\n"
					+ "  (0)->Exit");
			System.out.println(" ===========================================>");
			String rep = sc.nextLine();
			switch (rep)
			{
			case "0":
				System.out.println("Exit");             
				sc.close();
				System.exit(0);
				break;        
			case "1":
				testCcop();
				loop=false;
				break;        
			case "2":
				testCirculator();
				loop=false;
				break;       
			case "3":
				testCompressor();
				loop=false;
				break;        
			case "4":
				testCondenser();
				loop=false;
				break;        
			case "5":
				testDehydrator();
				loop=false;
				break;        
			case "6":
				testElDraw();
				loop=false;
				break;        
			case "7":
				testEnthalpy();
				loop=false;
				break;        
			case "8":
				testEnthalpyBkgdImg();
				loop=false;
				break;        
			case "9":
				testEvaporator();
				loop=false;
				break;        
			case "10":
				testExpansionValve();
				loop=false;
				break;
			case "11":
				testHeatDistribution();
				loop=false;
				break;        	
			case "12":
				testHeatSource();
				loop=false;
				break;        	
			case "13":
				testHeatTransferFluid();
				loop=false;
				break;        	
			case "14":
				testMeasurePoints();
				loop=false;
				break;        	
			case "15":
				testMiscellaneous();
				loop=false;
				break;        	
			case "16":
				testPAC();
				loop=false;
				break;        	
			case "17":
				testPACToolConfig();
				loop=false;
				break;        	
			case "18":
				testPrimeConfig();
				loop=false;
				break;        	
			case "19":
				testRefrigerant();
				loop=false;
				break;        	
			default:
				System.out.println("Mauvais Choix !");             
			}
		}
	}

	// ===================================================================================================================
	// 													TEST COMPUTE COP
	// ===================================================================================================================
	private static void testCcop() {  
		System.out.println("TEST COMPUTE COP");

		Ccop vCOP = new Ccop();

		vCOP.setH1(10);
		vCOP.setH2(20);
		vCOP.setH3(30);
		vCOP.setTK(50);
		vCOP.setT0(25);

		System.out.println("COP Carnot Chaud =" + vCOP.cop_carnot_chaud());

	}

	// ===================================================================================================================
	// 													TEST CIRCULATOR
	// ===================================================================================================================

	private static void testCirculator() {  
		System.out.println("TEST CIRCULATOR");

		Circulator vCirlculator = new Circulator();

		// Class Instance
		System.out.println("\n---> Class Instance");
		System.out.println("    Name = " + vCirlculator.getName());
		System.out.println("    Voltage = " + vCirlculator.getVoltage());
		System.out.println("    ---> Display features");
		for (int i=0;i<vCirlculator.getCurrentL().size();i++) {
			System.out.println("     Feature (" + i + ")");
			System.out.println("        Current =" + vCirlculator.getCurrentL().get(i));
			System.out.println("        RotatePerMinutes  =" + vCirlculator.getRotatePerMinutesL().get(i));
			System.out.println("        Power  =" + vCirlculator.getPowerL().get(i));
		}

		// ADD 1 feature + Display features
		System.out.println("\n---> ADD feature");
		vCirlculator.addFeatures(50, 5000, 500);
		for (int i=0;i<vCirlculator.getCurrentL().size();i++) {
			System.out.println(" Feature (" + i + ")");
			System.out.println("    Current =" + vCirlculator.getCurrentL().get(i));
			System.out.println("    RotatePerMinutes  =" + vCirlculator.getRotatePerMinutesL().get(i));
			System.out.println("    Power  =" + vCirlculator.getPowerL().get(i));
		}

		// construct JSON object
		System.out.println("\n---> Construct JSON data (with the new element !)");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vCirlculator.getJsonObject();
		System.out.println(jsonObj);
		
		// Create a new instance + clear Features 
		System.out.println("\n---> New Instance + Clear  all features");
		Circulator vCirlculatorNew = new Circulator();
		vCirlculatorNew.clearFeatures();
		vCirlculatorNew.setName("Toto");
		System.out.println("    Name = " + vCirlculatorNew.getName());
		if (vCirlculatorNew.getCurrentL().size() == 0)
			System.out.println("    --> No features");

		// set Class with the JSON object
		System.out.println("\n---> Set the Instance Class with the JSON data");
		vCirlculatorNew.setJsonObject(jsonObj);
		System.out.println(jsonObj);
		
		// Display the features afterwards 
		System.out.println("\n---> Class Instance");
		System.out.println("    Name = " + vCirlculator.getName());
		System.out.println("    Voltage = " + vCirlculator.getVoltage());
		System.out.println("    --> Display features");
		for (int i=0;i<vCirlculator.getCurrentL().size();i++) {
			System.out.println("     Feature (" + i + ")");
			System.out.println("        Current =" + vCirlculator.getCurrentL().get(i));
			System.out.println("        RotatePerMinutes  =" + vCirlculator.getRotatePerMinutesL().get(i));
			System.out.println("        Power  =" + vCirlculator.getPowerL().get(i));
		}

		System.out.println("\n--->Transfer Function");
		HeatTransferFluid vFluid = new HeatTransferFluid() ;
		vFluid.setT(25);
		System.out.println("    Input --> Output");
		System.out.println("    " + vFluid.getT()+"°C-->"+vCirlculator.transfer(vFluid).getT()+"°C");		
	}

	// ===================================================================================================================
	// 													TEST COMPRESSOR
	// ===================================================================================================================

	private static void testCompressor(){
		System.out.println("TEST COMPRESSOR");

		Compressor vCompressor = new Compressor();
		System.out.println("    "+vCompressor.getName());

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vCompressor.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance ");
		vCompressor.setName("Toto");
		System.out.println("    Name ="+vCompressor.getName());
		
		System.out.println("\n---> Set the Class Instance with JSON data");
		vCompressor.setJsonObject(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vCompressor.getName());
		
		Refrigerant vRefrigeranR22 =  new Refrigerant();
		vRefrigeranR22.setP(25);
		vCompressor.setDeltaP(40);
		
		System.out.println("\n--->Transfer Function");
		System.out.println("    Input --> Output");
		System.out.println("    "+vRefrigeranR22.getP() + "-->" + vCompressor.transfer(vRefrigeranR22).getP());
	}

	// ===================================================================================================================
	// 													TEST CONDENSER
	// ===================================================================================================================
	private static void testCondenser() {
		System.out.println("TEST CONDENSER");

		Condenser vCondenser = new Condenser();

		System.out.println("    "+vCondenser.getName());

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vCondenser.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance ");
		vCondenser.setName("Toto");
		System.out.println("    Name ="+vCondenser.getName());


		System.out.println("\n---> Set the Class Instance with JSON data");
		vCondenser.setJsonObject(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vCondenser.getName());

		Refrigerant vGas = new Refrigerant();
		vGas.setT(10);

		System.out.println("\n--->Transfer Function");
		System.out.println("Input --> Output");
		System.out.println(vGas.getT()+"°C-->"+vCondenser.transfer(vGas).getT()+"°C");

	}

	// ===================================================================================================================
	// 												TEST DEHYDRATOR
	// ===================================================================================================================

	private static void testDehydrator() {
		System.out.println("TEST DEHYDRATOR");
		Dehydrator vDehydrator = new Dehydrator();
		Refrigerant vGas = new Refrigerant();

		vDehydrator.setDeltaT(50.0);
		vGas.setT(10);

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vDehydrator.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance ");
		vDehydrator.setName("Toto");
		System.out.println("    Name ="+vDehydrator.getName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		vDehydrator.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vDehydrator.getName());

		System.out.println("\n--->Transfer Function");
		System.out.println("Input --> Output");
		System.out.println(vGas.getT()+"°C-->"+vDehydrator.transfer(vGas).getT()+"°C");

	}


	// ===================================================================================================================
	// 												TEST DRAW ELEMENTS
	// ===================================================================================================================
	private static void testElDraw() {
		System.out.println("TEST DRAW ELEMENTS");
		System.out.println("Not implemeted for now");
	}

	// ===================================================================================================================
	// 													TEST ENTHALPY
	// ===================================================================================================================
	private static void testEnthalpy() {
		System.out.println("TEST ENTHALPY");

		Enthalpy enthalpy = new Enthalpy();

		// Read conversion Temp/Press. file
		System.out.println("Load R22 (T/P) file");
		enthalpy.loadPTFile();

		System.out.println("Reference Diagram Enthalpy R22 : 2 bar (absolute) <--> -25°C");
		System.out.println("  T= -25°C" + "--> P=" + enthalpy.convT2P(-25));
		System.out.println("  P= 2 bar " + "--> T=" + enthalpy.convP2T(2));
		System.out.println();

		// Read Temperature [degre C] / Enthalpy (kJ/kg) Liquid / Enthalpy (kJ/kg) Vapor file
		System.out.println("Load R22 Saturation file and pick id=2");
		enthalpy.loadSatFile();
		System.out.println("  Saturation H Min = " + enthalpy.gethSatMin());
		System.out.println("  Saturation H Max = " + enthalpy.gethSatMax());
		System.out.println();
		System.out.println("  Pression [2] = "+ enthalpy.getSatP(2));
		System.out.println("  Enthalpy Liquid [2] = "+ enthalpy.getSatHl(2));
		System.out.println("  Enthalpy Vapor [2] = "+ enthalpy.getSatHv(2));
		System.out.println();

		// Recover the corresponding P Saturation for a specific H  
		System.out.println("Convert H to P saturation");
		double h = 360;
		double pNear = 2;
		System.out.println("  h=" + h + "  pNear=" + pNear + "  P_Sat="+enthalpy.convSatH2P(h,pNear)+"\n");


		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = enthalpy.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Perform some minor changes in Enthalpy" );
		System.out.println("    setxHmin (before)=" + enthalpy.getxHmin());
		enthalpy.setxHmin(333);
		System.out.println("    setxHmin (after)=" + enthalpy.getxHmin());
		
		
		System.out.println("\n---> Perform some minor changes in EnthalpyBkgImage" );
		System.out.println("    iBgH1x (EnthalpyBkgImage) (before) = " +enthalpy.getEnthalpyBkgImage().getiBgH1x());
		enthalpy.getEnthalpyBkgImage().setiBgH1x(300);
		System.out.println("    iBgH1x (EnthalpyBkgImage) (after) = " +enthalpy.getEnthalpyBkgImage().getiBgH1x());
		
		System.out.println("\n--->Rembered Chances");
		System.out.println("    setxHmin=" + enthalpy.getxHmin());
		System.out.println("    iBgH1x (EnthalpyBkgImage) = " +enthalpy.getEnthalpyBkgImage().getiBgH1x());

		System.out.println("\n---> Set the Class Instance with JSON data");
		System.out.println(jsonObj);
		enthalpy.setJsonObject(jsonObj);

		System.out.println("\n---> Read afterwards");
		System.out.println("    setxHmin=" + enthalpy.getxHmin());
		System.out.println("    iBgH1x (EnthalpyBkgImage) = " +enthalpy.getEnthalpyBkgImage().getiBgH1x());
		
	}

	// ===================================================================================================================
	// 													TEST ENTHALPYBKGDIMG
	// ===================================================================================================================

	private static void testEnthalpyBkgdImg () {
		System.out.println("TEST ENTHALPYBKGDIMG");
		
		EnthalpyBkgdImg vEnthalpyBkgdImg = new EnthalpyBkgdImg();
		
		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vEnthalpyBkgdImg.getJsonObject();
		System.out.println(jsonObj);
		
		
		// Read value, then modify
		System.out.println("\n---> Perform some minor changes in Enthalpy" );
		System.out.println("    iBgH1x = " +vEnthalpyBkgdImg.getiBgH1x());
		vEnthalpyBkgdImg.setiBgH1x(300);
		System.out.println("    iBgH1x = " +vEnthalpyBkgdImg.getiBgH1x());
		
		// Set with Json
		System.out.println("\n---> Set the Class Instance with JSON data");
		vEnthalpyBkgdImg.setJsonObject(jsonObj);
		System.out.println(jsonObj);
		
		System.out.println("\n---> Read afterwards");
		System.out.println("    iBgH1x = " +vEnthalpyBkgdImg.getiBgH1x());
		
	}

	// ===================================================================================================================
	// 													TEST EVAPORATOR
	// ===================================================================================================================
	
	private static void testEvaporator() {
		System.out.println("TEST EVAPORATOR");
		Evaporator vEvaporator = new Evaporator();
		Refrigerant vGas = new Refrigerant();

		vEvaporator.setDeltaT(50.0);
		vGas.setT(10);

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vEvaporator.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance ");
		vEvaporator.setName("Toto");
		System.out.println("    Name ="+vEvaporator.getName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		vEvaporator.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vEvaporator.getName());

		System.out.println("\n--->Transfer Function");
		System.out.println("Input --> Output");
		System.out.println(vGas.getT()+"°C-->"+vEvaporator.transfer(vGas).getT()+"°C");

	}

	// ===================================================================================================================
	// 													TEST EXPANSION VALVE
	// ===================================================================================================================
	private static void testExpansionValve(){
		System.out.println("TEST EXPANSION VALVE  = Détendeur !");

		ExpansionValve vExpansionValve = new ExpansionValve();
		System.out.println(vExpansionValve.getName());

		Refrigerant vRefrigeranR22 =  new Refrigerant();
		vRefrigeranR22.setP(25);
		vRefrigeranR22.setT(5);
		
		vExpansionValve.setDeltaP(-40);
		vExpansionValve.setDeltaT(+75);

		JSONObject jsonObj = new JSONObject();
		jsonObj = vExpansionValve.getJsonObject();
		System.out.println(jsonObj);
		
		System.out.println("\n---> Modify the instance ");
		vExpansionValve.setName("Toto");
		System.out.println("    Name ="+vExpansionValve.getName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		vExpansionValve.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vExpansionValve.getName());
		
		System.out.println("Input --> Output");
		System.out.println("P:" + vRefrigeranR22.getP() + "-->" + vExpansionValve.transfer(vRefrigeranR22).getP());
		System.out.println("T:" + vRefrigeranR22.getT() + "-->" + vExpansionValve.transfer(vRefrigeranR22).getT());

	}

	// ===================================================================================================================
	// 													TEST HEATDISTRIBUTION
	// ===================================================================================================================
	private static void testHeatDistribution() {
		System.out.println("TEST HEATDISTRIBUTION");

		HeatSrcDistrCircuit vHeatDistribution = new HeatSrcDistrCircuit();
		vHeatDistribution.setDeltaT(-50);
		System.out.println(vHeatDistribution.getName());

		HeatTransferFluid vFluid = new HeatTransferFluid(); 

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vHeatDistribution.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance ");
		vHeatDistribution.setName("Toto");
		System.out.println("    Name ="+vHeatDistribution.getName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		vHeatDistribution.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vHeatDistribution.getName());

		vFluid.setT(25);
		System.out.println("Input --> Output");
		System.out.println(vFluid.getT()+"°C-->"+vHeatDistribution.transfer(vFluid).getT()+"°C");

	}

	// ===================================================================================================================
	// 													TEST HEATSOURCE
	// ===================================================================================================================
	private static void testHeatSource() {
		System.out.println("TEST HEATSOURCE");

		HeatSrcDistrCircuit vDistrCircuit = new HeatSrcDistrCircuit();
		vDistrCircuit.setDeltaT(+20);
		System.out.println(vDistrCircuit.getName());

		HeatTransferFluid vFluid = new HeatTransferFluid(); 

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vDistrCircuit.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance ");
		vDistrCircuit.setName("Toto");
		System.out.println("    Name ="+vDistrCircuit.getName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		vDistrCircuit.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vDistrCircuit.getName());

		vFluid.setT(25);
		System.out.println("Input --> Output");
		System.out.println(vFluid.getT()+"°C-->"+vDistrCircuit.transfer(vFluid).getT()+"°C");

	}

	// ===================================================================================================================
	// 													TEST HEATTRANSFERFLUID
	// ===================================================================================================================
	private static void testHeatTransferFluid() {
		System.out.println("TEST HEATTRANSFER FLUID");

		HeatTransferFluid vHeatTransferFluid = new HeatTransferFluid();
		vHeatTransferFluid.setP(44);
		vHeatTransferFluid.setT(55);

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vHeatTransferFluid.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance ");
		vHeatTransferFluid.setName("Toto");
		System.out.println("    Name ="+vHeatTransferFluid.getName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		vHeatTransferFluid.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vHeatTransferFluid.getName());

		System.out.println(vHeatTransferFluid.getName());	
		System.out.println("Temp. / Pression");
		System.out.println(vHeatTransferFluid.getT()+"°C -- "+vHeatTransferFluid.getP()+"bar");		

	}

	// ===================================================================================================================
	// 													TEST MEASUREPOINTS
	// ===================================================================================================================

	private static void testMeasurePoints () { 
		System.out.println("TEST MEASUREPOINTS");
		System.out.println("--> Not implemented");

	}

	// ===================================================================================================================
	// 													TEST MISCELLANEOUS
	// ===================================================================================================================
	private static void testMiscellaneous() {
		System.out.println("MISC !");

		System.out.println("Dergé --> Farenheit : " + 10 + "-->" + Misc.degre2farenheit(10));
		System.out.println("BTU   --> Watt:" + 1 +"-->" + Misc.btuhr2watt(1) );

		System.out.println("Number of . in "+ "12.23.45 = " + Misc.nbCharInString("12.23.45", '.'));

		System.out.println("Test Closest in the list");
		List<Double> list = Arrays.asList(10.0, -23.0, 20.2, 30.3, -40.0, 40.2, 50.0, -2.0);
		System.out.println("  "+list);	
		System.out.println("  "+Misc.closestInL(-4, list));
		System.out.println("  "+Misc.closestInL(15, list));

		System.out.println("Max Intervall (between 2 consecutive elements) in the List");
		list = Arrays.asList(10.0, 11.2, 13.0, 15.0);
		System.out.println("  "+list);
		System.out.println("  "+Misc.maxIntervalL(list));
	}

	// ===================================================================================================================
	// 													TEST PAC
	// ===================================================================================================================
	private static void testPAC() {
		System.out.println("TEST PAC");

		Pac pac = new Pac();

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = pac.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify all instances ");
		pac.getCirculatorS().setName("Toto1");
		pac.getCirculatorD().setName("Toto2");
		pac.getCompressor().setName("Toto3");
		pac.getCondenser().setName("Toto4");
		pac.getDehydrator().setName("Toto5");
		pac.getEvaporator().setName("Toto6");
		pac.getExpansionValve().setName("Toto7");
		pac.getCircuitS().setName("Toto8");
		pac.getCircuitD().setName("Toto9");
		pac.getFluidCaloD().setName("Toto10");
		pac.getFluidCaloS().setName("Toto11");
		pac.getFluidRefri().setName("Toto12");
		
		System.out.println("    Name CirculatorS =" + pac.getCirculatorS().getName());
		System.out.println("    Name CirculatorD =" + pac.getCirculatorD().getName());
		System.out.println("    Name Compressor =" + pac.getCompressor().getName());
		System.out.println("    Name Condenser =" + pac.getCondenser().getName());
		System.out.println("    Name Dehydrator =" + pac.getDehydrator().getName());
		System.out.println("    Name Evaporator =" + pac.getEvaporator().getName());
		System.out.println("    Name ExpansionVa =" + pac.getExpansionValve().getName());
		System.out.println("    Name CircuitS =" + pac.getCircuitS().getName());
		System.out.println("    Name CircuitD =" + pac.getCircuitD().getName());
		System.out.println("    Name FluidCaloD =" + pac.getFluidCaloD().getName());
		System.out.println("    Name FluidCaloS =" + pac.getFluidCaloS().getName());
		System.out.println("    Name FluidRefri =" + pac.getFluidRefri().getName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		pac.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name CirculatorS =" + pac.getCirculatorS().getName());
		System.out.println("    Name CirculatorD =" + pac.getCirculatorD().getName());
		System.out.println("    Name Compressor =" + pac.getCompressor().getName());
		System.out.println("    Name Condenser =" + pac.getCondenser().getName());
		System.out.println("    Name Dehydrator =" + pac.getDehydrator().getName());
		System.out.println("    Name Evaporator =" + pac.getEvaporator().getName());
		System.out.println("    Name ExpansionVa =" + pac.getExpansionValve().getName());
		System.out.println("    Name CircuitS =" + pac.getCircuitS().getName());
		System.out.println("    Name CircuitD =" + pac.getCircuitD().getName());
		System.out.println("    Name FluidCaloD =" + pac.getFluidCaloD().getName());
		System.out.println("    Name FluidCaloS =" + pac.getFluidCaloS().getName());
		System.out.println("    Name FluidRefri =" + pac.getFluidRefri().getName());
		
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
		pac.getFluidRefri().setT(-5);
		pac.getFluidRefri().setP(5);

		System.out.println("Fluid Source : T=2, P=0.5");
		pac.getFluidCaloS().setT(2);
		pac.getFluidCaloS().setP(0.5);
		System.out.println("Circuit Source : deltaT=5");
		pac.getCircuitS().setDeltaT(5);

		System.out.println("Fluid Distribution: T=25, P=0.5");
		pac.getFluidCaloD().setT(25);
		pac.getFluidCaloD().setP(0.5);
		System.out.println("Circuit Distribution : deltaT=23");
		pac.getCircuitD().setDeltaT(23);

		System.out.println();
		for (int i=0;i<4;i++) {
			System.out.println("Run cycle:"+i+" by injecting R22 (T="+pac.getFluidRefri().getT()+";P="+pac.getFluidRefri().getP()+") in Compressor");
			System.out.println("     Injecting Fluid Source (T="+pac.getFluidCaloS().getT()+";P="+pac.getFluidCaloS().getP()+") in Circulateur");
			System.out.println("     Injecting Fluid Distribution(T="+pac.getFluidCaloD().getT()+";P="+pac.getFluidCaloD().getP()+") in Circulateur");

			pac.PacCycle(Misc._INPUT_COMPRESSOR);

			System.out.println("R22 T=" + pac.getFluidRefri().getT() + "  P="+ pac.getFluidRefri().getP());
			System.out.println("Fluid S T=" + pac.getFluidCaloS().getT() + "  P="+ pac.getFluidCaloS().getP());
			System.out.println("Fluid D T=" + pac.getFluidCaloD().getT() + "  P="+ pac.getFluidCaloD().getP());
		}

	}

	// ===================================================================================================================
	// 													TES PAC-Tool CONFIGURATION
	// ===================================================================================================================
	private static void testPACToolConfig() {
		System.out.println("TEST PAC-Tool CONFIGURATION");

	}

	// ===================================================================================================================
	// 													TEST PRIME CONFIGURATION
	// ===================================================================================================================
	private static void testPrimeConfig() {
		System.out.println("TEST PAC-Tool CONFIGURATION");
		
	}

		
	// ===================================================================================================================
	// 													TEST REFRIGERANT
	// ===================================================================================================================
	private static void testRefrigerant() {
		System.out.println("TEST REFRIGERANT");

		Refrigerant vRefrigerant = new Refrigerant();

		vRefrigerant.setT(45);
		vRefrigerant.setP(25);
		System.out.println("Temp. / Pression");
		System.out.println(vRefrigerant.getT()+"°C -- "+vRefrigerant.getP()+"bar");

		JSONObject jsonObj = new JSONObject();
		jsonObj = vRefrigerant.getJsonObject();
		System.out.println(jsonObj);
	}

} 
