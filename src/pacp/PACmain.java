package pacp;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;


public class PACmain {

	@SuppressWarnings("unchecked")
	public static void main(String[] args){

		PACcop pac = new PACcop();

		//step1 : first create array of 10 elements that holds object addresses.
		Scroll [] scroll_list = new Scroll[2];
		//step2 : now create objects in a loop.
		for(int i=0; i<scroll_list.length; i++){
			scroll_list[i] = new Scroll();//this will call constructor.
		}

		scroll_list[0].setName("ZR40K3-PFG (1)");
		scroll_list[0].setEvap(45);
		scroll_list[0].setCond(130);
		scroll_list[0].setRG(65);
		scroll_list[0].setLiq(115);
		scroll_list[0].setCapacity(33300);
		scroll_list[0].setPower(3000);
		scroll_list[0].setCurrent(14.7);
		scroll_list[0].setMassFlow(488);		
		scroll_list[0].setVoltage(220);

		scroll_list[1].setName("ZR40K3-PFG (2)");
		scroll_list[1].setEvap(245);
		scroll_list[1].setCond(230);
		scroll_list[1].setRG(265);
		scroll_list[1].setLiq(215);
		scroll_list[1].setCapacity(23300);
		scroll_list[1].setPower(2000);
		scroll_list[1].setCurrent(24.7);
		scroll_list[1].setMassFlow(288);		
		scroll_list[1].setVoltage(220);


		@SuppressWarnings("unused")
		PACwin window = new PACwin(pac);

		/*
		System.out.format("TO = %.2f°C\n",pac.getT0());
		System.out.format("TK = %.2f°C\n",pac.getTK());
		System.out.format("H1 = %.2fkJ/kg\n",pac.getH1());
		System.out.format("H2 = %.2fkJ/kg\n",pac.getH2());
		System.out.format("H3=H4 = %.2fkJ/kg\n",pac.getH3());
		System.out.println();
		System.out.format("COP Carnot Froid = %.2f \n",pac.cop_carnot_froid());
		System.out.format("COP Carnot Chaud = %.2f \n",pac.cop_carnot_chaud());
		System.out.format("COP Froid = %.2f \n",pac.cop_froid());
		System.out.format("COP Chaud = %.2f \n",pac.cop_chaud());
		System.out.format("COP effectif foid = %.2f \n",pac.cop_rendement_froid());
		System.out.format("COP effectif chaud= %.2f \n",pac.cop_rendement_chaud());
		 */
	}


	// ========================================================================================
	//                                 FUNCTIONS
	// ========================================================================================


	/**
	 * Convert Degre in Farenheit
	 * @param degre : Value in Degre
	 * @return : Value in Farenheit
	 */
	public static double degre2farenheit(double degre){
		double faren;
		faren = degre * 1.8 + 32.0;
		return faren;	
	}

	/**
	 * Convert Farenheit in Degre
	 * @param faren : Value in Farenheit
	 * @return : Value in Degre
	 */
	public static double farenheit2degre(double faren){
		double degre;	
		degre = (faren- 32.0)/1.8;
		return degre;	
	}


	/**
	 * Convert (BUT) British Thermal Unit in Watt 
	 * 1btu/hr = 0,29307107 watt
	 * @param btu
	 * @return watt
	 */
	public static double buthr2watt(double btu) {
		double watt;
		watt = btu * 0.29307107;
		return watt;	
	}

	/**
	 * Convert Watt in (BUT) British Thermal Unit  
	 * 1btu/hr = 0,29307107 watt
	 * @param watt
	 * @return btu
	 */
	public static double watt2btuhr(double watt) {
		double btu;
		btu = watt / 0.29307107;
		return btu;	
	}

	/**
	 * Convert pound to Kg
	 * @param lbs
	 * @return kg
	 * 
	 */
	public static double pound2kg(double lbs) {
		double kg;
		kg = lbs / 2.20462262185 / 3600;
		return kg;
	}

	/**
	 * Convert Kg to Pound
	 * @param kg
	 * @return
	 */
	public static double kg2pound(double kg) {
		double lbs;
		lbs = kg * 2.20462262185 * 3600;
		return lbs;
	}

	public static double cosphi(double P, double U, double I) {
		double cosphi;
		cosphi = P/(U*I);
		return cosphi;
	}
}
