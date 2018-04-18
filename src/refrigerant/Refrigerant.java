package refrigerant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

public class Refrigerant implements IRefrigerant {

	// -------------------------------------------------------
	// 					CONSTANT
	// -------------------------------------------------------
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());
	
	// --------------------------------------------------------------------
	// Instance variables
	// --------------------------------------------------------------------
	private SatCurve satCurve;
	private IsoThermCurve isoThermCurve;
	
	private String rfgName;
	private double rfgP;
	private double rfgT;
	private double rfgH;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Refrigerant(String fileNameGasSat, String fileNameGasIsoThermCurve) {
		String gasName1 = null;
		String gasName2 = null;
		
		this.satCurve = new SatCurve();
		gasName1 = satCurve.loadGasSaturationData(fileNameGasSat);

		this.isoThermCurve = new IsoThermCurve();
		gasName2 = isoThermCurve.loadGasIsoThermData(fileNameGasIsoThermCurve);

		if (gasName1.equals(gasName2)) {
			this.rfgName = gasName1;
			this.rfgP = 0.0;
			this.rfgT = 0.0; 
			this.rfgH = 0.0; 	
		} else {
			logger.error("(Refrigerant):: Saturation Curve and IsoTherm curve don't return the same Gas name {} <> {}! ",gasName1,gasName2 );
		}		
	}

	/*
	 * Will load default Refrigerant R22
	 */
	public Refrigerant() {
		String gasName1 = null;
		String gasName2 = null;

		this.satCurve = new SatCurve();
		gasName1 = satCurve.loadGasSaturationData("./ressources/R22/R22 Saturation Table.txt");

		this.isoThermCurve = new IsoThermCurve();
		gasName2 = isoThermCurve.loadGasIsoThermData("./ressources/R22/R22 IsoTherm Table");

		if (gasName1.equals(gasName2)) {
			this.rfgName = gasName1;
			this.rfgP = 0.0;
			this.rfgT = 0.0; 
			this.rfgH = 0.0; 	
		} else {
			logger.error("(Refrigerant):: Saturation Curve and IsoTherm curve don't return the same Gas name {} <> {}! ",gasName1,gasName2 );
			System.exit(0);
		}		
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public void loadNewRefrigerant(String fileNameGasSat) {
		this.rfgName = satCurve.loadGasSaturationData(fileNameGasSat);
		this.rfgP = 0.0;
		this.rfgT = 0.0; 
		this.rfgH = 0.0; 
	}

	// -----------------------------------------------------------------------------------------
	// 										Isobaric 
	// -----------------------------------------------------------------------------------------

	

	// -----------------------------------------------------------------------------------------
	// 										IsoThermCurve 
	// -----------------------------------------------------------------------------------------


	
	// -----------------------------------------------------------------------------------------
	// 										Intersection with Isobar 
	// -----------------------------------------------------------------------------------------
	


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

	public String loadRfgGasSaturationData(String fileNameGasSat) {
		this.rfgName = loadGasSaturationData(fileNameGasSat);
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

	@Override
	public double getPIsotherm(double H, double T, double P) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TSat getTSatFromP(double pressure) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PSat getPSatFromT(double temp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HSat getHSatFromP(double pressure) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HSat getHSatFromT(double temp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getHIsotherm(double H, double T) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPIsotherm(double H, double T) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getT_SatCurve_FromH(double vH) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getT_SatCurve_FromH(double vH, double vP) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHGasInterIsobarIsotherm(double PRef, double T) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getIsoTherm_H0_T(double T) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSatTableSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHSat_Liquid(int n) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPSat_Liquid(int n) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHSat_Gas(int n) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPSat_Gas(int n) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getP_SatCurve_FromH(double vH) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getP_SatCurve_FromH(double vH, double vP) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getIsobaricT(double refP, double H) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTSat(int n) {
		// TODO Auto-generated method stub
		return 0;
	}

}
