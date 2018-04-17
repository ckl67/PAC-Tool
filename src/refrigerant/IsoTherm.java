package refrigerant;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IsoTherm {

	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	private String gasFileName;

	private double IsoTherm_P0_Ref;
	private double IsoTherm_T0_Ref;
	private double IsoTherm_T0_Delta;
	private double IsoTherm_H0_Ref;
	private double IsoTherm_H0_Delta;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public IsoTherm() {

		this.gasFileName = "empty";

		// Default value based on R22
		this.IsoTherm_P0_Ref = 0.5; 
		this.IsoTherm_T0_Ref = -40;
		this.IsoTherm_T0_Delta = 10;
		this.IsoTherm_H0_Ref = 390;
		this.IsoTherm_H0_Delta = 8;

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

}
