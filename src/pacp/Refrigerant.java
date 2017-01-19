package pacp;

import org.json.simple.JSONObject;

public class Refrigerant extends Enthalpy {

	private String name;
	private double P;
	private double T;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	public Refrigerant() {
		setName("R22");
		setP(0);
		setT(0);
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------


	// -------------------------------------------------------
	// 							JSON
	// -------------------------------------------------------

	/**
	 * Return the JSON data
	 * @return : JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getJsonObject() {
		JSONObject ObjComp = new JSONObject();  
		ObjComp.put("Name", this.name);
		ObjComp.put("T", this.T);	
		ObjComp.put("P", this.P);	
		return ObjComp ;
	}

	/**
	 * Set Class with the element coming from a the JSON object
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.name = (String) jsonObj.get("Name");
		this.T = (double) jsonObj.get("T");
		this.P = (double) jsonObj.get("P");
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

	public void setP(double p) {
		P = p;
	}

	public double getT() {
		return T;
	}

	public void setT(double t) {
		T = t;
	}

}
