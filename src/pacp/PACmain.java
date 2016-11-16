package pacp;


public class PACmain {

	private static String PACTool_Version = "Version alpha 0.1";

	public static void main(String[] args){

		Pac pac = new Pac();
		Cop cop = new Cop();

		@SuppressWarnings("unused")
		WinPrime window = new WinPrime(pac,cop);

	}

	// ========================================================================================
	//                                 FUNCTIONS
	// ========================================================================================

	/**
	 * Return Software Version
	 * @return : Version in String
	 */
	public static String getPacToolVersion() {
		return PACTool_Version;
	}

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
	 * @param Pound = lbs
	 * @return kg
	 */
	public static double pound2kg(double lbs) {
		double kg;
		kg = lbs / 2.20462262185 / 3600;
		return kg;
	}

	/**
	 * Convert Kg to Pound
	 * @param kg
	 * @return Pound
	 */
	public static double kg2pound(double kg) {
		double lbs;
		lbs = kg * 2.20462262185 * 3600;
		return lbs;
	}

	/**
	 * Compute Cos(Phi)
	 * @param Power, Voltage, Current
	 * @return Cos(Phi)
	 */
	public static double cosphi(double P, double U, double I) {
		double cosphi;
		cosphi = P/(U*I);
		return cosphi;
	}
}
