package pacp;

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
					+ "  (-)-> Enthalpie\n"
					+ "  (6)-> Evaporateur\n"
					+ "  (7)-> Détendeur\n"
					+ "  (8)-> Distribution Chaleur --> Chauffage\n"
					+ "  (9)-> Source de Chaleur --> Captage\n"
					+ "  (10)-> Fluide Caloporteur\n"
					+ "  (11)-> Fonctions diverses\n"
					+ "  (12)-> PAC\n"
					+ "  (13)-> Fluide Réfrigerant\n"
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
			case "6":
				testEvaporator();
				loop=false;
				break;        
			case "7":
				testExpansionValve();
				loop=false;
				break;
			case "8":
				testHeatSource();
				loop=false;
				break;        	
			case "9":
				testHeatDistribution();
				loop=false;
				break;        	
			case "10":
				testHeatTransferFluid();
				loop=false;
				break;        	
			case "11":
				testMiscellaneous();
				loop=false;
				break;        	
			case "12":
				testPAC();
				loop=false;
				break;        	
			case "13":
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

	}

	// ===================================================================================================================
	// 													TEST CONDENSER
	// ===================================================================================================================
	private static void testCondenser() {
		System.out.println("TEST CONDENSER");

		Condenser vCondenser = new Condenser(50);
		System.out.println("Input --> Output");
		System.out.println(vCondenser.getInputT()+"°C-->"+vCondenser.getOutputT()+"°C");		
	}

	// ===================================================================================================================
	// 													TEST EVAPORATOR
	// ===================================================================================================================
	private static void testEvaporator() {
		System.out.println("TEST EVAPORATOR");

		Evaporator vEvaporator = new Evaporator(5);
		System.out.println("Input --> Output");
		System.out.println(vEvaporator.getInputT()+"°C-->"+vEvaporator.getOutputT()+"°C");		
	}

	// ===================================================================================================================
	// 													TEST EXPANSION VALVE
	// ===================================================================================================================
	private static void testExpansionValve(){
		System.out.println("TEST EXPANSION VALVE  = Détendeur !");

		ExpansionValve vExpansionValve = new ExpansionValve(5);
		System.out.println(vExpansionValve.getName());

		System.out.println("Input --> Output");
		System.out.println(vExpansionValve.getInputP()+" bar-->"+vExpansionValve.getOutputP()+" bar");		

	}

	// ===================================================================================================================
	// 													TEST HEATDISTRIBUTION
	// ===================================================================================================================
	private static void testHeatDistribution() {
		System.out.println("TEST HEATDISTRIBUTION");

		HeatDistribution vHeatDistribution = new HeatDistribution(50);
		System.out.println(vHeatDistribution.getName());
		System.out.println("Input --> Output");
		System.out.println(vHeatDistribution.getInputT()+"°C-->"+vHeatDistribution.getOutputT()+"°C");		
	}

	// ===================================================================================================================
	// 													TEST HEATSOURCE
	// ===================================================================================================================
	private static void testHeatSource() {
		System.out.println("TEST HEATSOURCE");

		HeatSource vHeatSource = new HeatSource(50);
		System.out.println(vHeatSource.getName());
		System.out.println("Input --> Output");
		System.out.println(vHeatSource.getInputT()+"°C-->"+vHeatSource.getOutputT()+"°C");		
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
	}


	// ===================================================================================================================
	// 													TEST MISCELLANEOUS
	// ===================================================================================================================
	private static void testMiscellaneous() {
		System.out.println("MISC !");

		System.out.println("Dergé --> Farenheit : " + 10 + "-->" + Misc.degre2farenheit(10));
		System.out.println("BTU   --> Watt:" + 1 +"-->" + Misc.btuhr2watt(1) );
	}

	// ===================================================================================================================
	// 													TEST PAC
	// ===================================================================================================================
	private static void testPAC() {
		System.out.println("TEST PAC");
		
	}

	// ===================================================================================================================
	// 													TEST REFRIGERANT
	// ===================================================================================================================
	private static void testRefrigerant() {
		System.out.println("TEST REFRIGERANT");

		Refrigerant vRefrigerant = new Refrigerant();
		System.out.println("Temp. / Pression");
		System.out.println(vRefrigerant.getT()+"°C -- "+vRefrigerant.getP()+"bar");		
	}

} 
