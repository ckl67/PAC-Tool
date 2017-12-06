package refrigerant;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SatCurve {

	// -------------------------------------------------------
	// 					CONSTANT
	// -------------------------------------------------------
	private static final Logger logger = LogManager.getLogger(SatCurve.class.getName());

	private final int id_Temp = 0;
	private final int id_P_Liquid = 1;
	private final int id_P_Gas = 2;
	private final int id_Density_Liquid = 3;
	private final int id_Density_Gas = 4;
	private final int id_H_Liquid = 5;
	private final int id_H_Latent = 6;
	private final int id_H_Gas = 7;
	private final int id_Entropy_Liquid = 8;
	private final int id_Entropy_Gas = 9;

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	
	private String gasName;
	private List<List<Double>> gasSaturationTable;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public SatCurve() {
		gasName = "empty";
		gasSaturationTable = new ArrayList<List<Double>>();

	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	/**
	 * ===============================================================================================
	 * Read Saturation data file containing, and will complete the 
	 * gasSaturationTable Table
	 *   # Temp.   Press       Press     Density  Density          Enthalpy           Entropy  Entropy
	 *	 #  °C      kPa         kPa       kg/m3    kg/m3     kJ/kg   kJ/kg  kJ/kg    kJ/kg K  kJ/kg K
	 *	 #        liquid        gas      liquid     gas      Liquid  latent  gas      liquid    gas
	 * 
	 * @param  : File Name
	 * @return : Name of Gas declared in the File.
	 * ===============================================================================================
	 */
	protected String loadGasSaturationData(String fileNameGas) {
		String unityP="kPa";

		File file = new File (fileNameGas);
		logger.info("Read File: {}", fileNameGas);

		Scanner sken = null;
		try {
			sken = new Scanner (file);
		} catch (FileNotFoundException e) {
			logger.error("Oups! (loadGasSaturationData)", e);
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

				// Create 1 row, and add all elemnts
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

				// Create a new line in the gasSaturationTable and Add the row 
				gasSaturationTable.add(row);

				// LOGGER
				int r = gasSaturationTable.size()-1;
				logger.trace("Temp={} P_Liquid={} P_Gas={} Density_Liquid={}  Density_Gas={}  H_Liquid={}  H_Latent={}  H_Gas={}  Entropy_Liquid={}  Entropy_Gas={} ",
						gasSaturationTable.get(r).get(id_Temp),
						gasSaturationTable.get(r).get(id_P_Liquid),
						gasSaturationTable.get(r).get(id_P_Gas),
						gasSaturationTable.get(r).get(id_Density_Liquid),
						gasSaturationTable.get(r).get(id_Density_Gas),
						gasSaturationTable.get(r).get(id_H_Liquid),
						gasSaturationTable.get(r).get(id_H_Latent),
						gasSaturationTable.get(r).get(id_H_Gas),
						gasSaturationTable.get(r).get(id_Entropy_Liquid),
						gasSaturationTable.get(r).get(id_Entropy_Gas)	);
			}
		}
		// Close scanner to avoid memory leak
		sken.close();
		return(gasName);
	}

	/**
	 * ===============================================================================================
	 * Convert Saturation Temperature(°C) to Pressure (bar) 
	 * @param temp: temperature (°C)
	 * @return : Pressure (bar absolute) --> (pGas,pLiquid)
	 * 
	 * gasSaturationTable Table
	 *   # Temp.   Press       Press     Density  Density          Enthalpy           Entropy  Entropy
	 *	 #  °C      kPa         kPa       kg/m3    kg/m3     kJ/kg   kJ/kg  kJ/kg    kJ/kg K  kJ/kg K
	 *	 #        liquid        gas      liquid     gas      Liquid  latent  gas      liquid    gas
	 *
	 * ===============================================================================================
	 */
	public PSat convSatT2P(double temp){
		double pGas=0;
		double pLiquid=0;
		PSat pOut = new PSat();
		double min = Double.MAX_VALUE;
		int id=0;

		 //List<List<Double>> gasSaturationTable;
		int nbRow = gasSaturationTable.size();
		int nbColumn = gasSaturationTable.get(0).size();
		
		logger.trace("Nb Row={}  Nb Column={}",nbRow,nbColumn);
		
		for(int n = 0; n < gasSaturationTable.size(); n++){
			double diff = Math.abs(gasSaturationTable.get(n).get(id_Temp) - temp);
			if (diff < min) {
				min = diff;
				id = n;
			}
		}
		if (id == gasSaturationTable.size()-1) {
			id = id-1;
		} 

		double x,y0,y1,x0,x1;
		x  = temp;
		x0 = gasSaturationTable.get(id).get(id_Temp);
		x1 = gasSaturationTable.get(id+1).get(id_Temp);
		if (x1==x0) {
			logger.error("(convSatT2P )");
			logger.error("  2 same valeurs of temperature will cause and issue and must be removed ");
		}

		y0 = gasSaturationTable.get(id).get(id_P_Liquid);
		y1 = gasSaturationTable.get(id+1).get(id_P_Liquid);
		pLiquid = (x-x0)*(y1-y0)/(x1-x0)+ y0;

		y0 = gasSaturationTable.get(id).get(id_P_Gas);
		y1 = gasSaturationTable.get(id+1).get(id_P_Gas);
		pGas = (x-x0)*(y1-y0)/(x1-x0)+ y0;

		pOut.setPGas(pGas);
		pOut.setPLiquid(pLiquid);

		return pOut;
	}

	/**
	 * ===============================================================================================
	 * Convert Saturation Pressure (bar) to Temperature(°C)
	 * @param P : Pression (bar)
	 * @return T : Temperature --> (TGas,TLiquid)
	 * 
	 * gasSaturationTable Table
	 *   # Temp.   Press       Press     Density  Density          Enthalpy           Entropy  Entropy
	 *	 #  °C      kPa         kPa       kg/m3    kg/m3     kJ/kg   kJ/kg  kJ/kg    kJ/kg K  kJ/kg K
	 *	 #        liquid        gas      liquid     gas      Liquid  latent  gas      liquid    gas
	 *	 #   
	 *	 #  11		800,04		665,98	
	 *	 #	17		953,26		803,81	
	 * ===============================================================================================
	 */
	public TSat convSatP2T(double pressure){
		double tGas=0;
		double tLiquid=0;
		TSat tOut = new TSat();
		double min = Double.MAX_VALUE;
		int id=0;

		// Liquid
		for(int n = 0; n < gasSaturationTable.size(); n++){
			double diff = Math.abs(gasSaturationTable.get(n).get(id_P_Liquid) - pressure);
			if (diff < min) {
				min = diff;
				id = n;
			}
		}
		if (id == gasSaturationTable.size()-1) {
			id = id-1;
		} 

		double x,y0,y1,x0,x1;
		x  = pressure;
		x0 = gasSaturationTable.get(id).get(id_P_Liquid);
		x1 = gasSaturationTable.get(id+1).get(id_P_Liquid);
		if (x1==x0) {
			logger.error("(convSatP2T )");
			logger.error("  2 same valeurs of pressure will cause and issue and must be removed ");
		}
		y0 = gasSaturationTable.get(id).get(id_Temp);
		y1 = gasSaturationTable.get(id+1).get(id_Temp);
		tLiquid = (x-x0)*(y1-y0)/(x1-x0)+ y0;

		// Gas
		for(int n = 0; n < gasSaturationTable.size(); n++){
			double diff = Math.abs(gasSaturationTable.get(n).get(id_P_Gas) - pressure);
			if (diff < min) {
				min = diff;
				id = n;
			}
		}
		if (id == gasSaturationTable.size()-1) {
			id = id-1;
		} 

		x  = pressure;
		x0 = gasSaturationTable.get(id).get(id_P_Gas);
		x1 = gasSaturationTable.get(id+1).get(id_P_Gas);
		if (x1==x0) {
			logger.error("(convSatP2T )");
			logger.error("  2 same valeurs of pressure will cause and issue and must be removed ");
		}
		y0 = gasSaturationTable.get(id).get(id_Temp);
		y1 = gasSaturationTable.get(id+1).get(id_Temp);
		tGas = (x-x0)*(y1-y0)/(x1-x0)+ y0;

		
		tOut.setTGas(tGas);
		tOut.setTLiquid(tLiquid);

		return tOut;
		
	}

	/**
	 * ===============================================================================================
	 * Convert Saturation Temperature(°C) to Enthalpy  (kJ/kg)
	 * @param temp: temperature (°C)
	 * @return : Enthalpy (kJ/kg) --> (hGas,hLiquid)
	 * 
	 * gasSaturationTable Table
	 *   # Temp.   Press       Press     Density  Density          Enthalpy           Entropy  Entropy
	 *	 #  °C      kPa         kPa       kg/m3    kg/m3     kJ/kg   kJ/kg  kJ/kg    kJ/kg K  kJ/kg K
	 *	 #        liquid        gas      liquid     gas      Liquid  latent  gas      liquid    gas
	 *
	 * ===============================================================================================
	 */
	public HSat convSatT2H(double temp){
		double hGas=0;
		double hLiquid=0;
		HSat hOut = new HSat();
		double min = Double.MAX_VALUE;
		int id=0;

		for(int n = 0; n < gasSaturationTable.size(); n++){
			double diff = Math.abs(gasSaturationTable.get(n).get(id_Temp) - temp);
			if (diff < min) {
				min = diff;
				id = n;
			}
		}
		if (id == gasSaturationTable.size()-1) {
			id = id-1;
		} 

		double x,y0,y1,x0,x1;
		x  = temp;
		x0 = gasSaturationTable.get(id).get(id_Temp);
		x1 = gasSaturationTable.get(id+1).get(id_Temp);
		if (x1==x0) {
			logger.error("(convSatT2P )");
			logger.error("  2 same valeurs of temperature will cause and issue and must be removed ");
		}

		y0 = gasSaturationTable.get(id).get(id_H_Liquid);
		y1 = gasSaturationTable.get(id+1).get(id_H_Liquid);
		hLiquid = (x-x0)*(y1-y0)/(x1-x0)+ y0;

		y0 = gasSaturationTable.get(id).get(id_H_Gas);
		y1 = gasSaturationTable.get(id+1).get(id_H_Gas);
		hGas = (x-x0)*(y1-y0)/(x1-x0)+ y0;

		hOut.setHGas(hGas);
		hOut.setHLiquid(hLiquid);

		return hOut;
	}

		
}
