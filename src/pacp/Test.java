package pacp;

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
					+ "  (1)-> COP Calcul\n"
					+ "  (2)-> Circulateur\n"
					+ "  (3)-> Compresseur\n"
					+ "  (4)-> Condensateur\n"
					+ "  (5)-> Object graphique pour Diagramme Enthalpy (Element Draw)\n"
					+ "  (6)-> Enthalpie\n"
					+ "  (7)-> Evaporateur\n"
					+ "  (8)-> Détendeur\n"
					+ "  (9)-> Distribution Chaleur --> Chauffage\n"
					+ "  (10)-> Source de Chaleur --> Captage\n"
					+ "  (11)-> Fluide Caloporteur\n"
					+ "  (12)-> Fonctions diverses\n"
					+ "  (13)-> PAC\n"
					+ "  (14)-> Fluide Réfrigerant\n"
					+ "  (X)-> Lanceur (Main)\n"
					+ "  (X)-> Test\n"
					+ "  (X)-> Fenêtre (Au sujet de)\n"
					+ "  (X)-> Fenêtre (Configuration Enthalpie)\n"
					+ "  (X)-> Fenêtre (Affichage Enthalpie)\n"
					+ "  (X)-> Fenêtre (Affichage Pression Température)\n"
					+ "  (X)-> Fenêtre (Principale)\n"
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
				testElDraw();
				loop=false;
				break;        
			case "6":
				testEnthalpy();
				loop=false;
				break;        
			case "7":
				testEvaporator();
				loop=false;
				break;        
			case "8":
				testExpansionValve();
				loop=false;
				break;
			case "9":
				testHeatDistribution();
				loop=false;
				break;        	
			case "10":
				testHeatSource();
				loop=false;
				break;        	
			case "11":
				testHeatTransferFluid();
				loop=false;
				break;        	
			case "12":
				testMiscellaneous();
				loop=false;
				break;        	
			case "13":
				testPAC();
				loop=false;
				break;        	
			case "14":
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

		System.out.println("Héritage de PAC");
		System.out.println("Nom du compressuer associé: " +vCOP.getCompressor().getName());
	}

	// ===================================================================================================================
	// 													TEST CIRCULATOR
	// ===================================================================================================================

	private static void testCirculator() {  
		System.out.println("TEST CIRCULATOR");

		Circulator vCirlculator = new Circulator();

		// Display Name
		System.out.println("Name = " + vCirlculator.getName());

		// Display features
		for (int i=0;i<vCirlculator.getCurrentL().size();i++) {
			System.out.println(" Feature (" + i + ")");
			System.out.println("    Current =" + vCirlculator.getCurrentL().get(i));
			System.out.println("    Power  =" + vCirlculator.getPowerL().get(i));
		}

		// ADD 1 feature + Display features
		System.out.println("\n---> ADD feature");
		vCirlculator.addFeatures(50, 5000, 500);
		for (int i=0;i<vCirlculator.getCurrentL().size();i++) {
			System.out.println(" Feature (" + i + ")");
			System.out.println("    Current =" + vCirlculator.getCurrentL().get(i));
			System.out.println("    Power  =" + vCirlculator.getPowerL().get(i));
		}

		// get Circulator-JSON object
		JSONObject jsonObjCirculator = new JSONObject();
		jsonObjCirculator = vCirlculator.getJsonObject();

		// Create a new instance + clear Features 
		System.out.println("\n---> New Instance + Clear features");
		Circulator vCirlculatorn = new Circulator();
		vCirlculatorn.clearFeatures();
		vCirlculatorn.setName("Toto");
		System.out.println("Name = " + vCirlculatorn.getName());
		if (vCirlculatorn.getCurrentL().size() == 0)
			System.out.println("No features");

		// set Class with the Circulator-JSON object
		System.out.println("\n---> Set the Instance with the data of Circulator-JSON object");
		vCirlculatorn.setJsonObject(jsonObjCirculator);
		System.out.println("Name = " + vCirlculatorn.getName());
		System.out.println("Voltage = " + vCirlculatorn.getVoltage());
		for (int i=0;i<vCirlculatorn.getCurrentL().size();i++) {
			System.out.println(" Feature (" + i + ")");
			System.out.println("    Current =" + vCirlculatorn.getCurrentL().get(i));
			System.out.println("    Power  =" + vCirlculatorn.getPowerL().get(i));
			System.out.println("    RotatePerMinutes  =" + vCirlculatorn.getRotatePerMinutesL().get(i));
		}

		HeatTransferFluid vFluid = new HeatTransferFluid() ;
		vFluid.setT(25);
		System.out.println("Input --> Output");
		System.out.println(vFluid.getT()+"°C-->"+vCirlculator.transfer(vFluid).getT()+"°C");		
	}

	// ===================================================================================================================
	// 													TEST COMPRESSOR
	// ===================================================================================================================

	private static void testCompressor(){
		System.out.println("TEST COMPRESSOR");

		Compressor vCompressor = new Compressor();
		System.out.println(vCompressor.getName());

		JSONObject jsonObjCompressor = new JSONObject();
		jsonObjCompressor = vCompressor.getJsonObject();
		System.out.println(jsonObjCompressor);
		
		Refrigerant vRefrigeranR22 =  new Refrigerant();
		vRefrigeranR22.setP(25);
		vCompressor.setDeltaP(40);
		System.out.println("Input --> Output");
		System.out.println(vRefrigeranR22.getP() + "-->" + vCompressor.transfer(vRefrigeranR22).getP());
	}

	// ===================================================================================================================
	// 													TEST CONDENSER
	// ===================================================================================================================
	private static void testCondenser() {
		System.out.println("TEST CONDENSER");

		Condenser vCondenser = new Condenser();
		Refrigerant vGas = new Refrigerant();
		
		vCondenser.setDeltaT(50.0);
		vGas.setT(10);
		
		System.out.println("Input --> Output");
		System.out.println(vGas.getT()+"°C-->"+vCondenser.transfer(vGas).getT()+"°C");
		
		JSONObject jsonObjCondenser = new JSONObject();
		jsonObjCondenser = vCondenser.getJsonObject();
		System.out.println(jsonObjCondenser);
	}

	// ===================================================================================================================
	// 												TEST DRAW ELEMENTS
	// ===================================================================================================================
	private static void testElDraw() {
		System.out.println("TEST DRAW ELEMENTS");
		
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
		System.out.println("Convert H to P saturation");

		double h = 360;
		double pNear = 2;
		System.out.println("  h=" + h + "  pNear=" + pNear + "  P_Sat="+enthalpy.convSatH2P(h,pNear)+"\n");
	
		h = 360;
		pNear = 50;
		System.out.println("  h=" + h + "  pNear=" + pNear + "  P_Sat="+enthalpy.convSatH2P(h,pNear)+"\n");
	
		h = 377;
		pNear = 50;
		System.out.println("  h=" + h + "  pNear=" + pNear + "  P_Sat="+enthalpy.convSatH2P(h,pNear)+"\n");

		h = 408;
		pNear = 50;
		System.out.println("  h=" + h + "  pNear=" + pNear + "  P_Sat="+enthalpy.convSatH2P(h,pNear)+"\n");

		h = 401;
		pNear = 18;
		System.out.println("  h=" + h + "  pNear=" + pNear + "  P_Sat="+enthalpy.convSatH2P(h,pNear)+"\n");

		h = 341;
		pNear = 46;
		System.out.println("  h=" + h + "  pNear=" + pNear + "  P_Sat="+enthalpy.convSatH2P(h,pNear)+"\n");

		h = 417;
		pNear = 23;
		System.out.println("  h=" + h + "  pNear=" + pNear + "  P_Sat="+enthalpy.convSatH2P(h,pNear)+"\n");

	}
		
	// ===================================================================================================================
	// 													TEST EVAPORATOR
	// ===================================================================================================================
	private static void testEvaporator() {
		System.out.println("TEST EVAPORATOR");

		Evaporator vEvaporator = new Evaporator();
		Refrigerant vGas = new Refrigerant();
		
		vEvaporator.setDeltaT(-50.0);
		vGas.setT(10);
		
		System.out.println("Input --> Output");
		System.out.println(vGas.getT()+"°C-->"+vEvaporator.transfer(vGas).getT()+"°C");		
		
		JSONObject jsonObjEvaporator = new JSONObject();
		jsonObjEvaporator = vEvaporator.getJsonObject();
		System.out.println(jsonObjEvaporator);
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
		System.out.println("Input --> Output");
		System.out.println("P:" + vRefrigeranR22.getP() + "-->" + vExpansionValve.transfer(vRefrigeranR22).getP());
		System.out.println("T:" + vRefrigeranR22.getT() + "-->" + vExpansionValve.transfer(vRefrigeranR22).getT());
		
		JSONObject jsonObjExpansionValve = new JSONObject();
		jsonObjExpansionValve = vExpansionValve.getJsonObject();
		System.out.println(jsonObjExpansionValve);
	}

	// ===================================================================================================================
	// 													TEST HEATDISTRIBUTION
	// ===================================================================================================================
	private static void testHeatDistribution() {
		System.out.println("TEST HEATDISTRIBUTION");

		HeatSrcDistrCircuit vHeatDistribution = new HeatSrcDistrCircuit(-50);
		System.out.println(vHeatDistribution.getName());
		
		HeatTransferFluid vFluid = new HeatTransferFluid(); 
		
		vFluid.setT(25);
		System.out.println("Input --> Output");
		System.out.println(vFluid.getT()+"°C-->"+vHeatDistribution.transfer(vFluid).getT()+"°C");
		
		JSONObject jsonObj = new JSONObject();
		jsonObj = vHeatDistribution.getJsonObject();
		System.out.println(jsonObj);
	}

	// ===================================================================================================================
	// 													TEST HEATSOURCE
	// ===================================================================================================================
	private static void testHeatSource() {
		System.out.println("TEST HEATSOURCE");

		HeatSrcDistrCircuit vHeatSource = new HeatSrcDistrCircuit(50);
		
		HeatTransferFluid vFluid = new HeatTransferFluid(); 
		
		vFluid.setT(-25);
		System.out.println("Input --> Output");
		System.out.println(vFluid.getT()+"°C-->"+vHeatSource.transfer(vFluid).getT()+"°C");
		
		JSONObject jsonObj = new JSONObject();
		jsonObj = vHeatSource.getJsonObject();
		System.out.println(jsonObj);
	}

	// ===================================================================================================================
	// 													TEST HEATTRANSFERFLUID
	// ===================================================================================================================
	private static void testHeatTransferFluid() {
		System.out.println("TEST HEATTRANSFERFLUID");

		HeatTransferFluid vHeatTransferFluid = new HeatTransferFluid();
		System.out.println(vHeatTransferFluid.getName());	
		System.out.println("Temp. / Pression");
		System.out.println(vHeatTransferFluid.getT()+"°C -- "+vHeatTransferFluid.getP()+"bar");		
		
		JSONObject jsonObj = new JSONObject();
		jsonObj = vHeatTransferFluid.getJsonObject();
		System.out.println(jsonObj);
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
		
		// Could be fine to create auto-test
		//assertThat(Misc.closestInL(-4, list), is(-2));
		
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
		pac.getCircuitS().setdeltaT(5);
		
		System.out.println("Fluid Distribution: T=25, P=0.5");
		pac.getFluidCaloD().setT(25);
		pac.getFluidCaloD().setP(0.5);
		System.out.println("Circuit Distribution : deltaT=23");
		pac.getCircuitD().setdeltaT(23);

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

		JSONObject jsonObj = new JSONObject();
		jsonObj = pac.getJsonObject();
		System.out.println(jsonObj);
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
		
		JSONObject jsonObjRefrigerant = new JSONObject();
		jsonObjRefrigerant = vRefrigerant.getJsonObject();
		System.out.println(jsonObjRefrigerant);
	}

} 
