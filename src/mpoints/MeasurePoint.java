/*
 * - PAC-Tool - 
 * Tool for understanding basics and computation of PAC (Pompe � Chaleur)
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
package mpoints;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import pac.Pac;
import refrigerant.Refrigerant;

public class MeasurePoint {

	// -------------------------------------------------------
	// 					CONSTANT
	// -------------------------------------------------------
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private EloMeasurePoint mPObject;					// P1,P2,P3,... + Definition
	private double value;								// Pressure, Temperature or ..
	private double P;									// Pressure (can be different than P0 or PK)
	private double T;									// Temperature
	private double H;									// Value Enthalpy approximation or real=moved manually
	private EloMeasurePointSelection mPObjectSelection;	// Measure Point to be considered Selected, Not Selected, in reference to the section picture 

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	// Create 1 MeasurePoint with the object belonging to enumeration
	// Exemple
	//			MeasurePoint pm_P1 = new MeasurePoint("P1")
	//			pm_P1.setValue(18.34)
	public MeasurePoint(EloMeasurePoint vmPObject ) {
		this.mPObject = vmPObject;
		this.value = 0.0;
		this.P = 0.0;
		this.T = 0.0;
		this.H = 0.0;
		this.mPObjectSelection = EloMeasurePointSelection.NotSelected;
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	/**
	 * Clear the measure with all its elements
	 */
	public void clearMeasure() {
		this.value = 0;
		this.P = 0;
		this.T = 0;
		this.H = 0;
		this.mPObjectSelection = EloMeasurePointSelection.NotSelected;
	}

	// Will return PR0 or PRK depending of the current object. 
	// The information will be read from Group of EloMeasurePoint
	// Example: P1 belongs to GROUP_BP
	// So the function will return PR0, but only if PR0 has been computed
	// if not -1;
	public double getMP_P0PK( List<MeasurePoint> lMeasurePoints) {
		double out = -1.0;

		double PR0,PRK = 0;

		int idPR0 = EloMeasurePoint._PR0id;
		int idPRK = EloMeasurePoint._PRKid;

		// Check of PR0 has already be computed through the lMeasurePoints
		if (lMeasurePoints.get(idPR0).getMP_P() > 0 )
			PR0 = lMeasurePoints.get(idPR0).getMP_P();
		else
			PR0 = -1.0;


		// Check of PRK has already be computed through the lMeasurePoints
		if (lMeasurePoints.get(idPRK).getMP_P()>0)
			PRK = lMeasurePoints.get(idPRK).getMP_P();
		else
			PRK = -1.0;

		switch (this.mPObject) {

		case P1: case P6: case P7: case P8:
			out = PR0;
			logger.trace("   (getMP_P0PK):: PR0 = {}",out);
			break;
		case P2 : case P3 : case P4 : case P5 :
			out = PRK;
			logger.trace("   (getMP_P0PK):: PRK = {}",out);
			break;
		default:
			logger.trace("   (getMP_P0PK):: PR0/PRK = {}",out);
			out = -1.0;
			break;
		}
		return out;
	}

	private void setValue(double value) {
		this.value = value;
	}

	public void setValue(double value,Pac pac, List<MeasurePoint> lMeasurePoints ) {
		double PR0;
		double PRK;

		logger.trace("(setValue):: {} --> value={}", this.mPObject,  value );
		Refrigerant refrigerant = pac.getRefrigerant(); 

		this.value = value;
		this.mPObjectSelection = EloMeasurePointSelection.Selected;

		//for (int loop=1;loop< 2;loop++) {

		switch (this.mPObject) {

		case P1 :
			//  BP gas temperature after internal overheating and before compression
			// 	Should not be possible to mesure as P1_T = P8_T + overheating, 
			// 	but here, the user has chosen to enter P1
			//  Value Entered = Temperature
			/*
				|         X |                               XX          /
				|        XX |             (PR0)               X         /
				|       XX  +---------------------------------+---+---+
				|       X  (6)                               (7) (8)  (1)
				|      XX                                     X
			 */
			this.T= value;
			// if PR0 > 0 then only H can be computed, and consider
			PR0 = this.getMP_P0PK(lMeasurePoints); 
			if ( PR0 > 0.0) {
				this.P = PR0; 
				this.H = refrigerant.getHGasInterIsobarIsotherm(PR0, T);
				logger.trace("    {} --> P={} T={} H={}", this.mPObject,this.P,this.T,this.H);
			}
			break;
		case P2 :
			// HP gas temperature at the end of compression (Compressor top Bell)
			//  Value Entered = Temperature
			/*
				|                  XXXX           XXXXX         (PK)
				|        (5)+-----(4)-----------------(3)--------------------+ (2)
				|           | XXXX                      XX                   /
				|           |XX                          XX                 /
			 */
			this.T= value;
			// if PRK > 0 then only H can be computed
			PRK = this.getMP_P0PK(lMeasurePoints); 
			if (PRK > 0.0) {
				this.P = PRK; 
				this.H = refrigerant.getHGasInterIsobarIsotherm(PRK, T);
				logger.trace("    {} --> P={} T={} H={}", this.mPObject,this.P,this.T,this.H);
			}
			break;
		case P3 : 
			// Initial condensation pressure (HP Manifold measurement)
			//  Value Entered = Pressure
			/*
				|                  XXXX           XXXXX         (PK)
				|        (5)+-----(4)-----------------(3)--------------------+ (2)
				|           | XXXX                      XX                   /
				|           |XX                          XX                 /
			 */
			// P must be > 0
			if (value > 0 ) {
				this.P = value;
				this.T = refrigerant.getTSatFromP(P).getTGas();
				this.H = refrigerant.getHSatFromP(P).getHGas();
				logger.trace("    {} --> P={} T={} H={}", this.mPObject,this.P,this.T,this.H);
			} else {
				this.mPObjectSelection = EloMeasurePointSelection.NotSelected;
			}
			break;
		case P4 :
			// Condensation end pressure (HP Manifold measurement)	
			//  Value Entered = Pressure
			/*
				|                  XXXX           XXXXX         (PK)
				|        (5)+-----(4)-----------------(3)--------------------+ (2)
				|           | XXXX                      XX                   /
				|           |XX                          XX                 /
			 */
			// P must be > 0
			if (value > 0 ) {
				this.P = value;
				this.T = refrigerant.getTSatFromP(P).getTLiquid();
				this.H = refrigerant.getHSatFromP(P).getHLiquid();
				logger.trace("    {} --> P={} T={} H={}", this.mPObject,this.P,this.T,this.H);

				// Compute P5: HP gas temperature after cooling
				//  Value Entered = Temperature
				/*
						|                  XXXX           XXXXX         (PK)
						|        (5)+-----(4)-----------------(3)--------------------+ (2)
						|           | XXXX                      XX                   /
						|           |XX                          XX                 /
				 */

				// PRK = P
				// T5 is under cooling of T4 
				double ucT = T - Math.round(pac.getCompressor().getUnderCooling());
				logger.trace("    Computation of T5 --> (T4={} - Under Colling ={}) = {}",
						T,
						Math.round(pac.getCompressor().getUnderCooling()),
						ucT);

				MeasurePoint m5 = lMeasurePoints.get(EloMeasurePoint.P5.id());
				m5.setValue(Math.round(ucT*100)/100.0);
				m5.setMP_T(ucT);
				m5.setMP_P(value);
				m5.setMP_H(refrigerant.getHSatFromT(ucT).getHLiquid());
				logger.trace("    {} --> P={} T={} H={}", m5.mPObject,m5.P,m5.T,m5.H);

				// Compute P6 = Output Temperature Regulator / Capillary
				//  Value Entered = Temperature
				/*
						|        XX |             (P0)               X         /
						|       XX  +---------------------------------+---+---+
						|       X  (6)                               (7) (8)  (1)
						|      XX                                     X
				 */
				// if PR0 > 0 then only H can be computed, and considered
				MeasurePoint m6 = lMeasurePoints.get(EloMeasurePoint.P6.id());
				PR0 = m6.getMP_P0PK(lMeasurePoints); 
				if ( PR0 > 0.0) {
					m6.setValue(Math.round(ucT*100)/100.0);
					m6.setMP_T(ucT);
					m6.setMP_P(PR0);
					m6.setMP_H(refrigerant.getHSatFromT(ucT).getHLiquid());
					logger.trace("    {} --> P={} T={} H={}", m6.mPObject,m6.P,m6.T,m6.H);
				}				
			} else {
				this.mPObjectSelection = EloMeasurePointSelection.NotSelected;
			}
			break;
		case P5 :
			// HP gas temperature after cooling
			//  Value Entered = Temperature
			/*
				|                  XXXX           XXXXX         (PK)
				|        (5)+-----(4)-----------------(3)--------------------+ (2)
				|           | XXXX                      XX                   /
				|           |XX                          XX                 /
			 */

			this.T= value;
			// if PRK > 0 then only H can be computed
			PRK = this.getMP_P0PK(lMeasurePoints); 
			if (PRK > 0.0) {
				this.P = PRK; 
				this.H = refrigerant.getHSatFromT(value).getHLiquid();
				logger.trace("    {} --> P={} T={} H={}", this.mPObject,this.P,this.T,this.H);

				// Compute P6 = Output Temperature Regulator / Capillary
				//  Value Entered = Temperature
				/*
						|        XX |             (P0)               X         /
						|       XX  +---------------------------------+---+---+
						|       X  (6)                               (7) (8)  (1)
						|      XX                                     X
				 */
				// if PR0 > 0 then only H can be computed, and considered
				MeasurePoint m6 = lMeasurePoints.get(EloMeasurePoint.P6.id());
				PR0 = m6.getMP_P0PK(lMeasurePoints); 
				if ( PR0 > 0.0) {
					m6.setValue(Math.round(value*100)/100.0);
					m6.setMP_T(value);
					m6.setMP_P(PR0);
					m6.setMP_H(refrigerant.getHSatFromT(value).getHLiquid());
					logger.trace("    {} --> P={} T={} H={}", m6.mPObject,m6.P,m6.T,m6.H);
				}				
			}
			break;

		case P6 :
			// Output Temperature Regulator / Capillary
			//  Value Entered = Temperature
			/*
				|         X |                               XX          /
				|        XX |             (P0)               X         /
				|       XX  +---------------------------------+---+---+
				|       X  (6)                               (7) (8)  (1)
				|      XX                                     X
			 */
			break;

		case P7 :	
			// Evaporation Pressure (BP Manifold Measurement)
			// Valuer entered Pressure
			/*
				|        XX |             (P0)               X         /
				|       XX  +---------------------------------+---+---+
				|       X  (6)                               (7) (8)  (1)
				|      XX                                     X
			 */
			// P must be > 0
			if (value > 0 ) {
				this.P = value;
				this.T = refrigerant.getTSatFromP(P).getTGas();
				this.H = refrigerant.getHSatFromP(P).getHGas();
				logger.trace("    {} --> P={} T={} H={}", this.mPObject,this.P,this.T,this.H);
			}
			break;
		case P8 :	
			// HP gas temperature after external heating
			//  Value Entered = Temperature
			/*
				|         X |                               XX          /
				|        XX |             (P0)               X         /
				|       XX  +---------------------------------+---+---+
				|       X  (6)                               (7) (8)  (1)
				|      XX                                     X
			 */

			this.T= value;
			// if PR0 > 0 then only H can be computed, and consider
			PR0 = this.getMP_P0PK(lMeasurePoints); 
			if ( PR0 > 0.0) {
				this.P = PR0; 
				this.H = refrigerant.getHGasInterIsobarIsotherm(PR0, T);
				logger.trace("    {} --> P={} T={} H={}", this.mPObject,this.P,this.T,this.H);
			}

			// Will Also Compute P1 
			// 	=>	BP gas temperature after internal overheating and before compression
			// 	T1 is Over heated of T8 --> T1 = T8 + OH
			double ohT =this.T + Math.round(pac.getCompressor().getOverheated());
			logger.trace("    Computation of T1 --> (T8={} + Compressor over head={}) = {}",
					T,
					Math.round(pac.getCompressor().getOverheated()),
					ohT);

			MeasurePoint m1 = lMeasurePoints.get(EloMeasurePoint.P1.id());
			m1.setValue(Math.round(ohT*100)/100.0);
			m1.setMP_T(ohT);
			m1.setMP_P(PR0); 
			m1.setMP_H(refrigerant.getHGasInterIsobarIsotherm(PR0, ohT));
			logger.trace("    {} --> P={} T={} H={}", m1.mPObject,m1.P,m1.T,m1.H);
			break;
		default:
			break;
		}

		//}	
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
		jsonObj.put("MeasurePoint", this.mPObject);
		jsonObj.put("Value", this.value);	
		jsonObj.put("P", this.P);	
		jsonObj.put("T", this.T);	
		jsonObj.put("H", this.H);
		jsonObj.put("MeasurePointSelection", this.mPObjectSelection);	
		//jsonObj.put("MeasurePointCompleted", this.mPobjetCompleted);	
		return jsonObj ;
	}

	/**
	 * Set the JSON data, to the Class instance
	 * @param jsonObj : JSON Object
	 */
	public void setJsonObject(JSONObject jsonObj) {
		this.mPObject = (EloMeasurePoint) jsonObj.get("MeasurePoint");
		this.value = ((Number) jsonObj.get("Value")).doubleValue();
		this.P = ((Number) jsonObj.get("P")).intValue();
		this.T = ((Number) jsonObj.get("T")).intValue();
		this.H = ((Number) jsonObj.get("H")).intValue();
		this.mPObjectSelection = (EloMeasurePointSelection) jsonObj.get("MeasurePointSelection");
		//this.mPobjetCompleted = (EloMeasurePointCompleted) jsonObj.get("MeasurePointCompleted");
	}


	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public EloMeasurePoint getMPObject() {
		return mPObject;
	}

	public double getValue() {
		return value;
	}
	public double getMP_P() {
		return P;
	}

	public double getMP_T() {
		return T;
	}	

	public double getMP_H() {
		return H;
	}	

	public void setMP_P(double p) {
		P = p;
	}

	public void setMP_T(double t) {
		T = t;
	}

	public void setMP_H(double h) {
		H = h;
	}

	public EloMeasurePoint getmPObject() {
		return mPObject;
	}

	public EloMeasurePointSelection getmPObjectSelection() {
		return mPObjectSelection;
	}


}
