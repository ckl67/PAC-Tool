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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IsoThermCurve {

	// -------------------------------------------------------
	// 					CONSTANT
	// -------------------------------------------------------
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	private final int ISOTHERM_POWER = 2;
	
	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------

	// In this approach, we need SatCurve information ton compute Isotherm information
	private SatCurve satCurve;

	// Name mandatory to be displayed in the gui
	private String gasFileName;
	private String gasName;

	private double IsoTherm_P0_Ref;
	private double IsoTherm_T0_Ref;
	private double IsoTherm_T0_Delta;
	private double IsoTherm_H0_Ref;
	private double IsoTherm_H0_Delta;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public IsoThermCurve(SatCurve vSatcurve) {

		satCurve = vSatcurve;
		
		this.gasFileName = "./ressources/R22/R22 IsoTherm Table";

		// Default value based on R22, could have also be initialized to 0 !
		// Doesn't matter
		this.IsoTherm_P0_Ref = 0.5; 
		this.IsoTherm_T0_Ref = -40;
		this.IsoTherm_T0_Delta = 10;
		this.IsoTherm_H0_Ref = 390;
		this.IsoTherm_H0_Delta = 8;
		
		this.gasName = loadGasIsoThermData(gasFileName);
		
	}

	public IsoThermCurve(String fileNameGasIsoTherm, SatCurve vSatcurve) {

		satCurve = vSatcurve;

		this.gasFileName = fileNameGasIsoTherm;

		// Default value based on R22
		this.IsoTherm_P0_Ref = 0.5; 
		this.IsoTherm_T0_Ref = -40;
		this.IsoTherm_T0_Delta = 10;
		this.IsoTherm_H0_Ref = 390;
		this.IsoTherm_H0_Delta = 8;
		
		this.gasName = loadGasIsoThermData(gasFileName);

	}

	/**
	 * 
	 * ===============================================================================================
	 * Read Isotherm data 
		  	#	Name:
			#   IsoTherm_P0_Ref
			#   IsoTherm_T0_Ref
			#   IsoTherm_T0_Delta
			#   IsoTherm_H0_Ref
			#   IsoTherm_H0_Delta
	 * @param  : File Name
	 * @return : Name of Gas declared in the File.
	 * ===============================================================================================
	 */
	protected String loadGasIsoThermData(String fileNameGas) {
		String vgasName="Empty";

		gasFileName = fileNameGas;
		File file = new File (fileNameGas);
		logger.debug("(loadGasIsoThermData):: Read File: {}", fileNameGas);

		Scanner sken = null;
		try {
			sken = new Scanner (file);
		} catch (FileNotFoundException e) {
			logger.error("Oups! (loadGasIsoThermData)", e);
			System.exit(0);
		}

		while (sken.hasNext () ){
			String first = sken.nextLine ();
			if (first.startsWith("Name:") ) {
				String[] val = first.split (":");
				vgasName = val [1];
				logger.info("Refrigerant = {}",vgasName);
			} else if (first.startsWith("IsoTherm_P0_Ref:") ) {
				String[] val = first.split (":");
				IsoTherm_P0_Ref = Double.parseDouble(val [1].replace(",", "."));
				logger.info("IsoTherm_P0_Ref = {}",IsoTherm_P0_Ref);
			} else if (first.startsWith("IsoTherm_T0_Ref:") ) {
				String[] val = first.split (":");
				IsoTherm_T0_Ref = Double.parseDouble(val [1].replace(",", "."));
				logger.info("IsoTherm_T0_Ref = {}",IsoTherm_T0_Ref);
			} else if (first.startsWith("IsoTherm_T0_Delta:") ) {
				String[] val = first.split (":");
				IsoTherm_T0_Delta = Double.parseDouble(val[1].replace(",", "."));
				logger.info("IsoTherm_T0_Delta = {}",IsoTherm_T0_Delta);			
			} else if (first.startsWith("IsoTherm_H0_Ref:") ) {
				String[] val = first.split (":");
				IsoTherm_H0_Ref = Double.parseDouble(val[1].replace(",", "."));
				logger.info("IsoTherm_H0_Ref = {}",IsoTherm_H0_Ref);
			} else if (first.startsWith("IsoTherm_H0_Delta:") ) {
				String[] val = first.split (":");
				IsoTherm_H0_Delta = Double.parseDouble(val[1].replace(",", "."));
				logger.info("IsoTherm_H0_Delta = {}",IsoTherm_H0_Delta);
			} else if (!first.startsWith("#") ) {
			}
		}
		// Close scanner to avoid memory leak
		sken.close();
		return(vgasName);
	}

	public double getPIsotherm(double H, double T) {
		return getPIsotherm(H, T, satCurve.getPSatFromT(T).getPLiquid());
	}


	public double getHIsotherm(double H, double T) {
		double outH = 0;
		double satHLiquid = satCurve.getHSatFromT(T).getHLiquid();

		if (H< satHLiquid) { 
			outH = satHLiquid;
		} else {
			outH = H;
		}

		return(outH);
	}

	public double getPIsotherm(double H, double T, double P) {
		double outP = 0;

		double satHLiquid = satCurve.getHSatFromT(T).getHLiquid();
		double satHGas = satCurve.getHSatFromT(T).getHGas();

		double satPLiquid  = satCurve.getPSatFromT(T).getPLiquid();
		double satPGas = satCurve.getPSatFromT(T).getPGas();

		logger.debug("  (getPIsotherm):: H={} T={} P={}",H,T,P);
		logger.debug("  (getPIsotherm):: satHLiquid={} satHGas={}",satHLiquid,satHGas);
		logger.debug("  (getPIsotherm):: satPLiquid={} satPGas={}",satPLiquid,satPGas);

		if (H< satHLiquid) { 
			if (P < satPLiquid) {
				outP = satPLiquid;
			} else {
				outP = P;
			}
		}
		else if (H> satHGas) {
			/*
			 	PIsotherm(H,T,P)=  -( 1/c * (H-Ha) )^4 + Pa;  
				with 	
	         		c = (H0-Ha)/ (Pa-P0 )^(1/4); 
	               		Pa et Ha : f(T isotherm)
	        			P0 = P0_Ref
	        			H0(T) = H0_Delta * (T-T0_Ref)/T0_Delta + H0_Ref
			 */        
			double Pa = satPGas;
			double Ha = satHGas;
			double H0 = getIsoTherm_H0_Delta() * 
					(T- getIsoTherm_T0_Ref())/getIsoTherm_T0_Delta() + 
					getIsoTherm_H0_Ref();
			double P0 = getIsoTherm_P0_Ref();
			logger.debug("  (getPIsotherm):: H={}>satHGas={}  Ha={} Pa={} H0={} P0={}",H,satHGas,Ha,Pa,H0,P0);

			double n = ISOTHERM_POWER;
			double c = (H0-Ha)/Math.pow(Pa-P0,1/n);
			outP = -Math.pow((H-Ha)/c,n) + Pa;
			if (outP < 0) 
				outP = 0;

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

	
	/**
	 * H Gas Inter Isobar Isotherm
	 * @param PRef
	 * @param T
	 * @return
	 */
	public double getHGasInterIsobarIsotherm(double PRef, double T) {
		double outH = 0;

		/*
	 	Hinter = c * (Pa-PRef)^(1/n) + Ha
		with 	
     		c = (H0-Ha)/ (Pa-P0 )^(1/4); 
           		Pa et Ha : f(T isotherm)
    			P0 = P0_Ref
    			H0(T) = H0_Delta * (T-T0_Ref)/T0_Delta + H0_Ref
		 */        

		double Pa = satCurve.getPSatFromT(T).getPGas();
		double Ha = satCurve.getHSatFromT(T).getHGas();
		double H0 = getIsoTherm_H0_Delta() * 
				(T- getIsoTherm_T0_Ref())/getIsoTherm_T0_Delta() + 
				getIsoTherm_H0_Ref();
		double P0 = getIsoTherm_P0_Ref();
		double n = ISOTHERM_POWER;
		double c = (H0-Ha)/Math.pow(Pa-P0,1/n);

		if (Pa > PRef) {
			outH = c*Math.pow(Pa-PRef,(1/n)) + Ha;
			logger.debug("(getHGasInterIsobarIsotherm):: PRef={} T={}",PRef,T);
			logger.debug("     Pa={} Ha={} H0={} P0={} c={} PRef={} n={}",Pa,Ha,H0,P0,c,PRef,n);
			logger.debug("      ---> outH={}",outH);
		} else {
			outH = 0;
			logger.error("(getHGasInterIsobarIsotherm):: PRef={} T={}",PRef,T);
			logger.error("    Error: condition not respected !! Pa>PRef !!  -->  PRef={}  Pa={}",PRef,Pa);
			logger.error("     Pa={} Ha={} H0={} P0={} c={} PRef={} n={}",Pa,Ha,H0,P0,c,PRef,n);
			logger.error("      ---> outH={}",outH);
		}

		return outH;
	}
	
	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------
	
	public String getGasFileName() {
		return gasFileName;
	}

	public double getIsoTherm_P0_Ref() {
		return IsoTherm_P0_Ref;
	}

	public double getIsoTherm_T0_Ref() {
		return IsoTherm_T0_Ref;
	}

	public double getIsoTherm_T0_Delta() {
		return IsoTherm_T0_Delta;
	}

	public double getIsoTherm_H0_Ref() {
		return IsoTherm_H0_Ref;
	}

	public double getIsoTherm_H0_T(double T) {
		//  H0(T) = H0_Delta * (T-T0_Ref)/T0_Delta + H0_Ref
		return IsoTherm_H0_Delta * (T-IsoTherm_T0_Ref)/IsoTherm_T0_Delta + IsoTherm_H0_Ref ;
	}

	public double getIsoTherm_H0_Delta() {
		return IsoTherm_H0_Delta;
	}

	public String getGasName() {
		return gasName;
	}

	
}
