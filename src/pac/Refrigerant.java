package pac;

import org.json.simple.JSONObject;

import enthalpy.Enthalpy;

public class Refrigerant extends Enthalpy {

	private String name;
	private double P;
	private double T;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public Refrigerant() {
		this.name = "R22";
		this.P = 0.0;
		this.T = 0.0;
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
		jsonObj.put("Name", this.name);
		jsonObj.put("T", this.T);	
		jsonObj.put("P", this.P);	
		return jsonObj ;
	}

	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.name = (String) jsonObj.get("Name");
		this.T = ((Number) jsonObj.get("T")).doubleValue();
		this.P = ((Number) jsonObj.get("P")).doubleValue();
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getP() {
		return P;
	}

	public void setP(double P) {
		this.P = P;
	}

	public double getT() {
		return T;
	}

	public void setT(double T) {
		this.T = T;
	}

}