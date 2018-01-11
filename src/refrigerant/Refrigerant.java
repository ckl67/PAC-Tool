package refrigerant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

public class Refrigerant extends SatCurve {

	// -------------------------------------------------------
	// 					CONSTANT
	// -------------------------------------------------------
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	// --------------------------------------------------------------------
	// Instance variables
	// --------------------------------------------------------------------
	private String rfgName;
	private double rfgP;
	private double rfgT;
	private double rfgH;

	//private double coefIsotherm;
	//private double expIsotherm;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Refrigerant(String fileNameGas) {
		this.rfgName = loadGasSaturationData(fileNameGas);
		this.rfgP = 0.0;
		this.rfgT = 0.0; 
		this.rfgH = 0.0; 	

		//this.coefIsotherm = 1;
		//this.expIsotherm = 2;
	}

	/*
	 * Will load default Refrigerant R22
	 */
	public Refrigerant() {
		this.rfgName = loadGasSaturationData("./ressources/R22/Saturation Table R22.txt");
		this.rfgP = 0.0;
		this.rfgT = 0.0; 
		this.rfgH = 0.0; 	
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------


	// -----------------------------------------------------------------------------------------
	// 										Isobaric 
	// -----------------------------------------------------------------------------------------

	/**
	 * Get Pressure of Isobaric 
	 * No sense because == refP, but for the principle
	 * @param refP
	 * @param H
	 * @return
	 */
	public double getIsobaricP(double refP, double H) {
		// Whatever H
		double outP=refP;			
		return outP;
	}

	/**
	 * Get State of Isobaric 
	 * @param refP
	 * @param H
	 * @return
	 */
	public String getIsobaricState(double refP, double H) {
		String outState="Empty";

		double satHLiquid = this.getHSatFromP(refP).getHLiquid();
		double satHGas = this.getHSatFromP(refP).getHGas();

		if (H< satHLiquid) 
			outState = "Liquid";
		else if (H> satHGas)
			outState = "Gas";
		else
			outState = "Liquid+Gas";

		return outState;
	}

	// -----------------------------------------------------------------------------------------
	// 										IsoTherm 
	// -----------------------------------------------------------------------------------------

	public double getPIsotherm(double H, double T) {
		return getPIsotherm(H, T, this.getPSatFromT(T).getPLiquid());

	}


	public double getPIsotherm(double H, double T, double P) {
		double outP = 0;

		double satHLiquid = this.getHSatFromT(T).getHLiquid();
		double satHGas = this.getHSatFromT(T).getHGas();

		double satPLiquid  = this.getPSatFromT(T).getPLiquid();
		double satPGas = this.getPSatFromT(T).getPGas();

		//logger.info("  (getPIsotherm):: satHLiquid={} satHGas={}",satHLiquid,satHGas);
		//logger.info("  (getPIsotherm):: satPLiquid={} satPGas={}",satPLiquid,satPGas);

		if (H< satHLiquid) { 
			if (P <satPLiquid )
				outP = satPLiquid;
			else
				outP = P;
		}
		else if (H> satHGas) {
			//
			// PIsotherm(H,T,P)=  -( 1/c * (H-Ha) )^n + Pa;  
			// with 
			//    c = coefIsotherm ;  n = expIsotherm
			//    This value depend of P !!!
			double Pa = satPGas;
			double Ha = satHGas;
			double n = 0;
			double c = 0;
			nnn
			outP = -Math.pow((H-Ha)/c,n) + Pa;
		}
		else {
			// satHLiquid < H < satHGas
			double x,y0,y1,x0,x1;
			x  = H;
			x0 = satHLiquid;
			x1 = satHGas;
			if (x1==x0) {
				logger.error("(getPIsotherm):: 2 same value will cause and issue and must be removed ");
			}
			y0 = satPLiquid;
			y1 = satPGas;
			outP = (x-x0)*(y1-y0)/(x1-x0)+ y0;
		}


		return outP;
	}

	public double getHGasInterIsobarIsotherm(double PRef, double T) {
		double outH = 0;

		double Pa = this.getPSatFromT(T).getPGas();
		double Ha = this.getHSatFromT(T).getHGas();
		double n = 0;
		double c = 0;
		if (Pa < 10) {
			n = 2;
			c = 1;
		}
		else {
			n = 4;
			c = 1/(Pa/10);
		}

		outH = c*Math.pow(Pa-PRef,(1/n)) + Ha;
		return outH;
	}

	// -------------------------------------------------------
	// 							JSON
	// -------------------------------------------------------
	//	Squiggly brackets {} act as containers  
	//	Names and values are separated by a colon(:) 	--> put
	//  Square brackets[] represents arrays.			--> add
	//  {  "Planet": "Earth" , "Countries": [  { "Name": "India", "Capital": "Delhi"}, { "Name": "France", "Major": "Paris" } ]  }  
	// -------------------------------------------------------

	/**
	 * Construct the JSON data
	 * @return : JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getJsonObject() {
		JSONObject jsonObj = new JSONObject();  
		jsonObj.put("RefrigerantGasFileName", this.getGasFileNameSat());
		//jsonObj.put("CoefIsotherm", this.coefIsotherm);
		//jsonObj.put("ExpIsotherm", this.expIsotherm);
		return jsonObj ;
	}

	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		String gasFileName;

		gasFileName = (String) jsonObj.get("RefrigerantGasFileName");
		this.rfgName = loadGasSaturationData(gasFileName);
		this.rfgP = 0.0;
		this.rfgT = 0.0; 
		this.rfgH = 0.0; 	
		//this.coefIsotherm = ((Number) jsonObj.get("CoefIsotherm")).doubleValue(); 	
		//this.expIsotherm = ((Number) jsonObj.get("ExpIsotherm")).doubleValue(); 	

	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public String loadRfgGasSaturationData(String fileNameGas) {
		this.rfgName = loadGasSaturationData(fileNameGas);
		return(rfgName);
	}
	public String getRfgName() {
		return rfgName;
	}

	public double getRfgP() {
		return rfgP;
	}

	public double getRfgT() {
		return rfgT;
	}

	public double getRfgH() {
		return rfgH;
	}

	public void setRfgP(double P) {
		this.rfgP = P;
	}

	public void setRfgT(double T) {
		this.rfgT = T;
	}

}
