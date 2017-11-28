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
package enthalpy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Enthalpy {

	private static final Logger logger = LogManager.getLogger(Enthalpy.class.getName());

	/* (http://asciiflow.com/)

					P
					^
					|
					|
					|                     XXXXXXXXXXXXX
					|                  XXXX           XXXXX             
					|                 XX                  XX                          
					|             XXXX                      XX                    
					|            XX                          XX                  
				5,6	|     (T=0)  X---_____                    XX                
					|          XX         ---------- ____     XX              
				4,6	|         XX                           ----X (T=0)             
					|         X                                 XX           
					|        XX                                  X           
					|       XX                                   X         
					|       X                                     X          
					|      XX                                     X
					|      X                                      X
					|
					+------------------------------------------------------------------------> H

						T      P(Liquid)	P(Gas)
						0		5,6787		4,6071	

	 */

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------

	private List<List<Double>> gasSaturationTable;
	private String nameRefrigerant;			
	private double deltaP;							// Delta pressure absolute / relative

	// -------------------------------------------------------
	//	LOCAL VARIABLES
	// -------------------------------------------------------

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	/**
	 * Constructor will load gasSaturationTable.
	 */
	public Enthalpy(String fileNameGas) {
		gasSaturationTable = new ArrayList<List<Double>>();
		nameRefrigerant = loadGasSaturationData(fileNameGas);
		deltaP=0;
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public void ChangeGas(String fileNameGas) {
		gasSaturationTable.clear();
		nameRefrigerant = loadGasSaturationData(fileNameGas);
	}

	/**
	 * Read Saturation data file containing:
	    # Temp.   Press       Press     Density  Density          Enthalpy           Entropy  Entropy
		#  °C      kPa         kPa       kg/m3    kg/m3     kJ/kg   kJ/kg  kJ/kg    kJ/kg K  kJ/kg K
		#        liquid        gas      liquid     gas      Liquid  latent  gas      liquid    gas
	 */
	private String loadGasSaturationData(String fileNameGas) {
		String gasName = null;
		String unityP="kPa";

		File file = new File (fileNameGas);
		logger.info("Read File: {}", fileNameGas);

		Scanner sken = null;
		try {
			sken = new Scanner (file);
		} catch (FileNotFoundException e) {
			logger.error("Ops! (loadGasSaturationData)", e);
			System.exit(0);
		}

		while (sken.hasNext () ){
			String first = sken.nextLine ();
			if (first.startsWith("Name:") ) {
				String[] val = first.split (":");
				gasName = val [1];
				logger.trace("Refrigerant = {}",gasName);
			} else if (first.startsWith("Unity P:") ) {
				String[] val = first.split (":");
				unityP = val [1];
				logger.trace("Unity of Pressure = {}",unityP);

			} else if (!first.startsWith("#") ) {
				String[] val = first.split ("\t");
				List<Double> row = new ArrayList<Double>();
				// Temperature
				row.add(Double.parseDouble(val [0].replace(",", ".")));
				// Pressure Liquid 
				if (unityP.equals("kPa"))
					row.add(Double.parseDouble(val [1].replace(",", "."))*0.01);
				else
					row.add(Double.parseDouble(val [1].replace(",", ".")));

				// Pressure Gas 
				if (unityP.equals("kPa"))
					row.add(Double.parseDouble(val [2].replace(",", "."))*0.01);
				else
					row.add(Double.parseDouble(val [2].replace(",", ".")));

				// Density Liquid
				row.add(Double.parseDouble(val [3].replace(",", ".")));
				// Density Gas
				row.add(Double.parseDouble(val [4].replace(",", ".")));
				// Enthalpy Liquid
				row.add(Double.parseDouble(val [5].replace(",", ".")));
				// Enthalpy Latent
				row.add(Double.parseDouble(val [6].replace(",", ".")));
				// Enthalpy Gas
				row.add(Double.parseDouble(val [7].replace(",", ".")));
				// Entropy Liquid
				row.add(Double.parseDouble(val [8].replace(",", ".")));
				// Entropy Gas
				row.add(Double.parseDouble(val [9].replace(",", ".")));

				gasSaturationTable.add(row);

				int r = gasSaturationTable.size()-1;
				logger.trace("T={} pf={} pg={}",
						gasSaturationTable.get(r).get(SaturationTable.Temperature.col()),
						gasSaturationTable.get(r).get(SaturationTable.PressureL.col()),
						gasSaturationTable.get(r).get(SaturationTable.PressureG.col()));
			}
		}
		// Close scanner to avoid memory leak
		sken.close();
		return(gasName);
	}

	private int rIndex(SaturationTable item, double value) {
		double min = Double.MAX_VALUE;
		int id=0;

		if (gasSaturationTable.size() != 0) {
			for(int r = 0; r < gasSaturationTable.size(); r++){
				double tmp = gasSaturationTable.get(r).get(item.col());
				double diff = Math.abs( tmp - value);
				if (diff < min) {
					min = diff;
					id = r;
				}
			}
		}
		return(id);
	}

	public double findNearestItem(SaturationTable item, double value) {
		int id=rIndex(item, value);
		return gasSaturationTable.get(id).get(item.col());
	}

	public double findNearestItem(SaturationTable itemi, double value, SaturationTable itemo) {
		int id=rIndex(itemi, value);
		return gasSaturationTable.get(id).get(itemo.col());
	}	

	
	public double convT(double T) {
		
		
	}
	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------
	public String getNameRefrigerant() {
		return nameRefrigerant;
	}

}
