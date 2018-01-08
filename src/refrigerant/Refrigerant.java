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
	
	private double coefIsotherm;
	private double expIsotherm;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Refrigerant(String fileNameGas) {
		this.rfgName = loadGasSaturationData(fileNameGas);
		this.rfgP = 0.0;
		this.rfgT = 0.0; 
		this.rfgH = 0.0; 	
		
		this.coefIsotherm = 1;
		this.expIsotherm = 1;
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



	public double getP_IsothermFromH(double H) {
		double outP = 0;
		return outP;
	}

	public double getHGasInterIsobarIsotherm(double PRef, double T) {
		double outH = 0;

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
		jsonObj.put("CoefIsotherm", this.coefIsotherm);
		jsonObj.put("ExpIsotherm", this.expIsotherm);
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
		this.coefIsotherm = ((Number) jsonObj.get("CoefIsotherm")).doubleValue(); 	
		this.expIsotherm = ((Number) jsonObj.get("ExpIsotherm")).doubleValue(); 	
		
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

	public double getCoefIsotherm() {
		return coefIsotherm;
	}

	public void setCoefIsotherm(double coefIsotherm) {
		this.coefIsotherm = coefIsotherm;
	}

	public double getExpIsotherm() {
		return expIsotherm;
	}

	public void setExpIsotherm(double expIsotherm) {
		this.expIsotherm = expIsotherm;
	}

}
