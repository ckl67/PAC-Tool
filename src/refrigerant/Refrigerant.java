package refrigerant;

import org.json.simple.JSONObject;

public class Refrigerant extends SatCurve {

	// --------------------------------------------------------------------
	// Instance variables
	// --------------------------------------------------------------------
	private String rfgName;
	private double rfgP;
	private double rfgT;
	private double rfgH;
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Refrigerant(String fileNameGas) {
		this.rfgName = loadGasSaturationData(fileNameGas);
		this.rfgP = 0.0;
		this.rfgT = 0.0; 
		this.rfgH = 0.0; 	
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
