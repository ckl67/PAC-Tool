package refrigerant;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaturationTable {

	private static final Logger logger = LogManager.getLogger(SaturationTable.class.getName());

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------

	private List<List<Double>> gasSaturationTable;

	
	public SaturationTable(String fileNameGas) {
		loadGasSaturationData(fileNameGas);
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

				//List<List<Double>> gasSaturationTable
				gasSaturationTable = new ArrayList<ArrayList<Double>>();
				gasSaturationTable.add(row);
		
				int r = gasSaturationTable.size()-1;
				logger.trace("T={} pf={} pg={}",
						gasSaturationTable.get(r).get(ESaturationTable.Temperature.col()),
						gasSaturationTable.get(r).get(ESaturationTable.PressureL.col()),
						gasSaturationTable.get(r).get(ESaturationTable.PressureG.col()));
			}
		}
		// Close scanner to avoid memory leak
		sken.close();
		return(gasName);
	}

}
