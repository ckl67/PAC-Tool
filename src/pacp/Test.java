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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pacp.Misc;

// ------------------------------------------------------
// Could be fine to create auto-test function
//	assertThat(Misc.closestInL(-4, list), is(-2));
//------------------------------------------------------

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
					+ "  (4)-> Condensator\n"
					+ "  (5)-> Dehydrator\n"
					+ "  (6)-> Object graphic for Enthalpy (Element elDraw)\n"
					+ "  (7)-> Enthalpy\n"
					+ "  (8)-> EnthalpyBkgdImg Background Image for Enthalpy \n"
					+ "  (9)-> Evaporator\n"
					+ "  (10)-> Expansion Valve\n"
					+ "  (11)-> Head Distribution Circuit \n"
					+ "  (12)-> Head Source Circuit \n"
					+ "  (13)-> Heat Transfert Fluid\n"
					+ "  (14)-> Measure\n"
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
				testMeasure();
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

		System.out.println("Reference Diagram Enthalpy R22 : 2 bar (absolute) <--> -25°C");
		System.out.println("  T= -25°C" + "--> P=" + enthalpy.convT2P(-25));
		System.out.println("  P= 2 bar " + "--> T=" + enthalpy.convP2T(2));
		System.out.println();

		// Read Temperature [degre C] / Enthalpy (kJ/kg) Liquid / Enthalpy (kJ/kg) Vapor file
		System.out.println("Load R22 Saturation file and pick id=2");
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
	// 													TEST HEAT DISTRIBUTION
	// ===================================================================================================================
	private static void testHeatDistribution() {
		System.out.println("TEST HEAT DISTRIBUTION");

		HeatSrcDistrCircuit vHeatDistribution = new HeatSrcDistrCircuit();
		vHeatDistribution.setDeltaT(-50);
		vHeatDistribution.setName("Chauffage Maison");
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
	// 													TEST HEAT SOURCE
	// ===================================================================================================================
	private static void testHeatSource() {
		System.out.println("TEST HEAT SOURCE");

		HeatSrcDistrCircuit vHeatSource = new HeatSrcDistrCircuit();
		vHeatSource.setDeltaT(+20);
		vHeatSource.setName("Captage");
		System.out.println(vHeatSource.getName());

		HeatTransferFluid vFluid = new HeatTransferFluid(); 

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vHeatSource.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance ");
		vHeatSource.setName("Toto");
		System.out.println("    Name ="+vHeatSource.getName());

		System.out.println("\n---> Set the Class Instance with JSON data");
		vHeatSource.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vHeatSource.getName());

		vFluid.setT(25);
		System.out.println("Input --> Output");
		System.out.println(vFluid.getT()+"°C-->"+vHeatSource.transfer(vFluid).getT()+"°C");

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
	// 													TEST MEASURE
	// ===================================================================================================================

	private static void testMeasure () { 
		System.out.println("TEST MEASURE");
		
		List<Measure> measurePL1;
		measurePL1 = new ArrayList<Measure>(); 
        for (MeasureObject p : MeasureObject.values())
        	measurePL1.add(new Measure(p));

        Measure ma = measurePL1.get(0);
        
        if (ma.getMeasureObject().equals(MeasureObject.T1)) {
			System.out.println(ma.getMeasureObject());				// = T1 from MeasureObject.T1 (First element of array)
			System.out.println(ma.getMeasureObject().toString());	// = "T1"
			System.out.println(ma.getMeasureObject().getDefinition());
        }
        
        Measure mb = measurePL1.get(MeasureObject.T2.ordinal());
        mb.setValue(12);
        System.out.println(mb.getMeasureObject());
		System.out.println(mb.getValue() + " " + mb.getMeasureObject().getUnity());
		System.out.println(mb.getMP() + " bars");
		

		Measure vMeasure = new Measure(MeasureObject.T1);
		vMeasure.setValue(23.457);
		System.out.println(vMeasure.getMeasureObject());

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vMeasure.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Set the Class Instance with JSON data");
		vMeasure.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vMeasure.getMeasureObject());

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
		int[] itemFor = new int[12];
		itemFor[Pac._COMP]=0;
		itemFor[Pac._COND]=0;
		itemFor[Pac._DEHY]=0;
		itemFor[Pac._EPVA]=0;
		itemFor[Pac._EVAP]=0;
		itemFor[Pac._FLFRG]=0;
		itemFor[Pac._CRCLS]=0;
		itemFor[Pac._CIRTS]=0;
		itemFor[Pac._FLCAS]=0;
		itemFor[Pac._CRCLD]=0;
		itemFor[Pac._CIRTD]=0;
		itemFor[Pac._FLCAD]=0;
		System.out.println("------------------------------------------------------------------");
		System.out.println("Element which will be simulated --> All First elements" );
		
		
		for (int i=0;i<4;i++) {
			System.out.println("------------------------------------------------------------------");
			System.out.println("Run cycle:"+i+" by ");
			System.out.println("     Injecting R22 (T="+pac.getFluidRefriL().get(0).getT()+";P="+pac.getFluidRefriL().get(0).getP()+") in Compressor");
			System.out.println("     Injecting Fluid Source (T="+pac.getFluidCaloSrcL().get(0).getT()+";P="+pac.getFluidCaloSrcL().get(0).getP()+") in Circulateur");
			System.out.println("     Injecting Fluid Distribution(T="+pac.getFluidCaloDistrL().get(0).getT()+";P="+pac.getFluidCaloDistrL().get(0).getP()+") in Circulateur");

			pac.PacCycle(Pac._INPUT_COMPRESSOR,itemFor);

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

		
		itemFor[Pac._COMP]=1;
		itemFor[Pac._COND]=0;
		itemFor[Pac._DEHY]=0;
		itemFor[Pac._EPVA]=0;
		itemFor[Pac._EVAP]=0;
		itemFor[Pac._FLFRG]=0;
		itemFor[Pac._CRCLS]=0;
		itemFor[Pac._CIRTS]=0;
		itemFor[Pac._FLCAS]=0;
		itemFor[Pac._CRCLD]=0;
		itemFor[Pac._CIRTD]=0;
		itemFor[Pac._FLCAD]=0;
		
		System.out.println("------------------------------------------------------------------");
		System.out.println("Element which will be simulated --> All First elements EXCEPT COMPRESSOR 1" );

		for (int i=0;i<4;i++) {
			System.out.println("------------------------------------------------------------------");
			System.out.println("Run cycle:"+i+" by ");
			System.out.println("     Injecting R22 (T="+pac.getFluidRefriL().get(0).getT()+";P="+pac.getFluidRefriL().get(0).getP()+") in Compressor");
			System.out.println("     Injecting Fluid Source (T="+pac.getFluidCaloSrcL().get(0).getT()+";P="+pac.getFluidCaloSrcL().get(0).getP()+") in Circulateur");
			System.out.println("     Injecting Fluid Distribution(T="+pac.getFluidCaloDistrL().get(0).getT()+";P="+pac.getFluidCaloDistrL().get(0).getP()+") in Circulateur");

			pac.PacCycle(Pac._INPUT_COMPRESSOR,itemFor);

			System.out.println("Output result for cycle:"+i+" ");
			System.out.println("     R22 T=" + pac.getFluidRefriL().get(0).getT() + "  P="+ pac.getFluidRefriL().get(0).getP());
			System.out.println("     Fluid S T=" + pac.getFluidCaloSrcL().get(0).getT() + "  P="+ pac.getFluidCaloSrcL().get(0).getP());
			System.out.println("     Fluid D T=" + pac.getFluidCaloDistrL().get(0).getT() + "  P="+ pac.getFluidCaloDistrL().get(0).getP());
		}

	}

	// ===================================================================================================================
	// 													TEST PAC-Tool CONFIGURATION
	// ===================================================================================================================
	private static void testPACToolConfig() {
		System.out.println("TEST PAC-Tool CONFIGURATION");

		Scanner sken = null;
		try {
			sken = new Scanner (new File ("D:/Users/kluges1/workspace/pac-tool/config/PAC-Tool-Test.cfg"));
		} catch (FileNotFoundException e) {
			System.err.println("Unable to read the file: fileName");
		}

		String strLineJSON = "";
		while (sken.hasNext () ){
			String strLine  = sken.nextLine ();
			if (!strLine .startsWith("#") ) {
				strLineJSON = strLineJSON + strLine;
			}
		}

		// Parse to JSON Object
		JSONParser parser = new JSONParser();  
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) parser.parse(strLineJSON);
		} catch (ParseException e) {
			System.err.println("Unable to Parse JSON read the file: fileName");
		}  

		// PrimeConfig: Set the Class Instance with JSON data
		PrimeConfig primeConfig = new PrimeConfig();
		JSONObject jsonObjPrimeConfig = (JSONObject) jsonObj.get("PrimeConfig") ;
		System.out.println(jsonObjPrimeConfig);
		primeConfig.setJsonObject(jsonObjPrimeConfig);

		System.out.println("   BTU=" +	primeConfig.getUnitCompBTU());
		System.out.println("   Pound=" +	primeConfig.getUnitCompPound());
		System.out.println("   Faren=" +	primeConfig.getUnitCompFaren());

		// Enthalpy (containing also EnthalpyBkgdImg)
		Enthalpy enthalpy = new Enthalpy();
		JSONObject jsonObjEnthalpy = (JSONObject) jsonObj.get("Enthalpy");
		System.out.println(jsonObjEnthalpy);
		enthalpy.setJsonObject(jsonObjEnthalpy);

		// Create PAC List
		List<Pac> pacl = new ArrayList<Pac>();
		Pac pac = new Pac();
		pacl.add(pac);

		for(int i=1;i<pacl.size();i++) {
			pacl.remove(i);
		}
		JSONArray jsonObjectPacL = (JSONArray) jsonObj.get("PacList");
		for(int i=0; i< jsonObjectPacL.size();i++) {
			JSONObject jsonObjectPac = (JSONObject) jsonObjectPacL.get(i);
			System.out.println(jsonObjectPac);
			if(i==0) {
				pacl.get(i).setJsonObject(jsonObjectPac);				
			} else {
				pacl.add(i, new Pac());
				pacl.get(i).setJsonObject(jsonObjectPac);
			}
		}



	}

	// ===================================================================================================================
	// 													TEST PRIME CONFIGURATION
	// ===================================================================================================================
	private static void testPrimeConfig() {
		System.out.println("TEST PAC-Tool CONFIGURATION");

		PrimeConfig primeConfig = new PrimeConfig();

		System.out.println(primeConfig.TranslateText("Capacity","eng"));
		System.out.println(primeConfig.TranslateText("Power","eng"));

		System.out.println(primeConfig.TranslateText("Capacity","fr"));
		System.out.println(primeConfig.TranslateText("Power","fr"));
		System.out.println(primeConfig.TranslateText("Capacitydd","fr"));


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
