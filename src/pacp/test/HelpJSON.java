package pacp.test;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HelpJSON {

	// =====================================================================================
	// 											MAIN
	// =====================================================================================

	public static void main(String[] args) {

		Enthalpy enthalpy = new Enthalpy();

		// ======================
		// APPROACH 1
		// ======================
		System.out.println("\n APPROACH  1");
		System.out.println("---> Construct JSON data");
		JSONParser parser = new JSONParser(); 
		JSONObject jsonObjectR= null;
		String dataJSON = "{\"enthalpyBkgdImg\":{\"refCurveH1x\":140,\"enthalpyImageFile\":\"R22_A4.png\"},\"nameRefrigerant\":\"R22\",\"xHmin\":140.0}";
		try {
			jsonObjectR = (JSONObject) parser.parse(dataJSON);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jsonObjectR);
		System.out.println("\n---> Set the Class Instance with JSON data");
		System.out.println(jsonObjectR);
		enthalpy.setJsonObject(jsonObjectR);

		// ======================
		// APPROACH  2
		// ======================
		System.out.println("\n APPROACH  2");
		System.out.println("---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = enthalpy.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Set the Class Instance with JSON data");
		System.out.println(jsonObj);
		enthalpy.setJsonObject(jsonObj);

	}


	// =====================================================================================
	// 										CLASS ENTHALPYBKGIMG
	// =====================================================================================

	public static class EnthalpyBkgdImg {

		private String enthalpyImageFile;		
		private int refCurveH1x; 

		// -------------------------------------------------------
		// 						CONSTRUCTOR
		// -------------------------------------------------------

		public EnthalpyBkgdImg() {

			this.enthalpyImageFile = "R22 A4.png";
			this.refCurveH1x = 140; 
		}

		// -------------------------------------------------------
		// 							JSON
		// -------------------------------------------------------

		/**
		 * Construct the JSON data
		 * @return : JSONObject
		 */
		@SuppressWarnings("unchecked")
		public JSONObject getJsonObject() {
			JSONObject jsonObj = new JSONObject();  
			jsonObj.put("enthalpyImageFile", this.enthalpyImageFile);
			jsonObj.put("refCurveH1x", this.refCurveH1x);	
			return jsonObj ;
		}

		/**
		 * Set the JSON d<ata, to the Class instance
		 * @param jsonObj : JSON Object
		 */
		public void setJsonObject(JSONObject jsonObj) {
			this.enthalpyImageFile = (String) jsonObj.get("enthalpyImageFile");
			this.refCurveH1x = Integer.valueOf(((Long) jsonObj.get("refCurveH1x")).intValue()) ;
			//this.refCurveH1x = (int) jsonObj.get("refCurveH1x");
		}

	}

	// =====================================================================================
	// 										CLASS ENTHALPY
	// =====================================================================================

	public static class Enthalpy {

		private String nameRefrigerant;			
		private double xHmin;  					
		private EnthalpyBkgdImg enthalpyBkgdImg;

		// -------------------------------------------------------
		// 						CONSTRUCTOR
		// -------------------------------------------------------

		public Enthalpy() {
			nameRefrigerant = "R22";
			xHmin = 140;  				
			enthalpyBkgdImg = new EnthalpyBkgdImg();
		}

		// -------------------------------------------------------
		// 							JSON
		// -------------------------------------------------------

		/**
		 * Construct the JSON data
		 * @return : JSONObject
		 */
		@SuppressWarnings("unchecked")
		public JSONObject getJsonObject() {
			JSONObject jsonObj = new JSONObject();  
			jsonObj.put("nameRefrigerant", this.nameRefrigerant);
			jsonObj.put("xHmin", this.xHmin);	
			jsonObj.put("enthalpyBkgdImg", enthalpyBkgdImg.getJsonObject());	
			return jsonObj ;
		}

		/**
		 * Set the JSON data, to the Class instance
		 * @param jsonObj : JSON Object
		 */
		public void setJsonObject(JSONObject jsonObj) {
			this.nameRefrigerant = (String) jsonObj.get("nameRefrigerant");
			this.xHmin = (double) jsonObj.get("xHmin");
			JSONObject jsonObjEImg = (JSONObject) jsonObj.get("enthalpyBkgdImg");
			this.enthalpyBkgdImg.setJsonObject(jsonObjEImg); 
		}

	}

}

