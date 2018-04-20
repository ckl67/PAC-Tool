/*
 * - PAC-Tool - 
 * Tool for understanding basics and computation of PAC (Pompe à Chaleur)
 * Copyright (C) 2016 christian.klugesherz@gmail.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (version 2)
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
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
	private IsoBaricCurve isoBaricCurve;

	private String rfgName;
	private double rfgP;
	private double rfgT;
	private double rfgH;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Refrigerant(String fileNameGasSatCurve, String fileNameGasIsoThermCurve) {
		String gasName1 = null;
		String gasName2 = null;

		this.satCurve = new SatCurve(fileNameGasSatCurve);
		this.isoThermCurve = new IsoThermCurve(fileNameGasIsoThermCurve,satCurve);
		this.isoBaricCurve = new IsoBaricCurve(satCurve,isoThermCurve);

		gasName1 = satCurve.getGasName();
		gasName2 = isoThermCurve.getGasName();

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

		this.satCurve = new SatCurve("./ressources/R22/R22 Saturation Table.txt");
		this.isoThermCurve = new IsoThermCurve("./ressources/R22/R22 IsoTherm Table.txt",satCurve);
		this.isoBaricCurve = new IsoBaricCurve(satCurve,isoThermCurve);

		gasName1 = satCurve.getGasName();
		gasName2 = isoThermCurve.getGasName();

		if (gasName1.equals(gasName2)) {
			this.rfgName = gasName1;
			this.rfgP = 0.0;
			this.rfgT = 0.0; 
			this.rfgH = 0.0; 	
		} else {
			logger.error("(Refrigerant):: Saturation Curve and IsoTherm curve don't return the same Gas name {} <> {}! ",gasName1,gasName2 );
		}			
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public boolean loadNewRefrigerant(String fileNameGasSatCurve, String fileNameGasIsoThermCurve) {
		boolean out;
		String gasName1 = null;
		String gasName2 = null;

		gasName1 = satCurve.loadGasSaturationData(fileNameGasSatCurve);
		gasName2 = isoThermCurve.loadGasIsoThermData(fileNameGasIsoThermCurve);

		if (gasName1.equals(gasName2)) {
			this.rfgName = gasName1;
			this.rfgP = 0.0;
			this.rfgT = 0.0; 
			this.rfgH = 0.0;
			out = true;
		} else {
			logger.error("(Refrigerant):: Saturation Curve and IsoTherm curve don't return the same Gas name {} <> {}! ",gasName1,gasName2 );
			out = false;
		}
		return out;
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
		jsonObj.put("RefrigerantSatCurveGasFileName", satCurve.getGasFileName());
		jsonObj.put("RefrigerantIsoThermCurveGasFileName", isoThermCurve.getGasFileName());
		return jsonObj ;
	}

	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		String fileNameGasSatCurve = null;
		String fileNameGasIsoThermCurve = null;
		String gasName1 = null;
		String gasName2 = null;

		fileNameGasSatCurve = (String) jsonObj.get("RefrigerantSatCurveGasFileName");
		fileNameGasIsoThermCurve = (String) jsonObj.get("RefrigerantIsoThermCurveGasFileName");

		gasName1 = satCurve.loadGasSaturationData(fileNameGasSatCurve);
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

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

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

	// -----------------------------------------------------------------------------------------
	// 										Isobaric 
	// -----------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------
	// 										IsoThermCurve 
	// -----------------------------------------------------------------------------------------
	// -----------------------------------------------------------------------------------------
	// 										Intersection with Isobar 
	// -----------------------------------------------------------------------------------------

	@Override
	public double getPIsotherm(double H, double T, double P) {
		return isoThermCurve.getPIsotherm(H, T, P);
	}

	@Override
	public TSat getTSatFromP(double pressure) {
		return satCurve.getTSatFromP(pressure);
	}

	@Override
	public PSat getPSatFromT(double temp) {
		return satCurve.getPSatFromT(temp);
	}

	@Override
	public HSat getHSatFromP(double pressure) {
		return satCurve.getHSatFromP(pressure);
	}

	@Override
	public HSat getHSatFromT(double temp) {
		return satCurve.getHSatFromT(temp);
	}

	@Override
	public double getHIsotherm(double H, double T) {
		return isoThermCurve.getHIsotherm(H, T);
	}

	@Override
	public double getPIsotherm(double H, double T) {
		return isoThermCurve.getPIsotherm(H, T);
	}

	@Override
	public double getT_SatCurve_FromH(double vH) {
		return satCurve.getT_SatCurve_FromH(vH);
	}

	@Override
	public double getT_SatCurve_FromH(double vH, double vP) {
		return satCurve.getT_SatCurve_FromH(vH, vP);
	}

	@Override
	public double getHGasInterIsobarIsotherm(double PRef, double T) {
		return isoThermCurve.getHGasInterIsobarIsotherm(PRef, T);
	}

	@Override
	public double getIsoTherm_H0_T(double T) {
		return isoThermCurve.getIsoTherm_H0_T(T);
	}

	@Override
	public int getSatTableSize() {
		return satCurve.getSatTableSize();
	}

	@Override
	public double getHSat_Liquid(int n) {
		return satCurve.getHSat_Liquid(n);
	}

	@Override
	public double getPSat_Liquid(int n) {
		return satCurve.getPSat_Liquid(n);
	}

	@Override
	public double getHSat_Gas(int n) {
		return satCurve.getHSat_Gas(n);
	}

	@Override
	public double getPSat_Gas(int n) {
		return satCurve.getPSat_Gas(n);
	}

	@Override
	public double getP_SatCurve_FromH(double vH) {
		return satCurve.getP_SatCurve_FromH(vH);
	}

	@Override
	public double getP_SatCurve_FromH(double vH, double vP) {
		return satCurve.getP_SatCurve_FromH(vH, vP);
	}

	@Override
	public double getIsobaricT(double refP, double H) {
		return isoBaricCurve.getIsobaricT(refP, H);
	}

	@Override
	public double getTSat(int n) {
		return satCurve.getTSat(n);
	}

	@Override
	public double getIsobaricP(double refP, double H) {
		return isoBaricCurve.getIsobaricP(refP, H);
	}

	@Override
	public String getIsobaricState(double refP, double H) {
		return isoBaricCurve.getIsobaricState(refP, H);
	}

	@Override
	public String getGasFileNameSatCurve() {
		return satCurve.getGasFileName();
	}

	@Override
	public String getGasFileNameIsoThermCurve() {
		return isoThermCurve.getGasFileName();
	}

}
