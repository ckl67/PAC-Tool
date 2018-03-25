package refrigerant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

public class Refrigerant extends SatCurve {

	// -------------------------------------------------------
	// 					CONSTANT
	// -------------------------------------------------------
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	// --------------------------------------------------------------------
	// Instance variables
	// --------------------------------------------------------------------
	private String rfgName;
	private double rfgP;
	private double rfgT;
	private double rfgH;

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Refrigerant(String fileNameGas) {
		this.rfgName = loadGasSaturationData(fileNameGas);
		this.rfgP = 0.0;
		this.rfgT = 0.0; 
		this.rfgH = 0.0; 	
	}

	/*
	 * Will load default Refrigerant R22
	 */
	public Refrigerant() {
		this.rfgName = loadGasSaturationData("./ressources/R22/Saturation Table R22.txt");
		this.rfgP = 0.0;
		this.rfgT = 0.0; 
		this.rfgH = 0.0; 	
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public void loadNewRefrigerant(String fileNameGas) {
		this.rfgName = loadGasSaturationData(fileNameGas);
		this.rfgP = 0.0;
		this.rfgT = 0.0; 
		this.rfgH = 0.0; 
	}

	// -----------------------------------------------------------------------------------------
	// 										Isobaric 
	// -----------------------------------------------------------------------------------------

	/**
	 * Get Pressure of Isobaric 
	 * No sense because == refP, but for the principle
	 * @param refP
	 * @param H
	 * @return
	 */
	public double getIsobaricP(double refP, double H) {
		// Whatever H
		double outP=refP;			
		return outP;
	}

	/**
	 * Get Temperature of Isobaric 
	 * No sense because == refP, but for the principle
	 * @param refP
	 * @param H
	 * @return
	 */
	public double getIsobaricT(double refP, double H) {
		double outT=refP;
		double satHLiquid = this.getHSatFromP(refP).getHLiquid();
		double satHGas = this.getHSatFromP(refP).getHGas();

		double satTLiquid = this.getTSatFromP(refP).getTLiquid();
		double satTGas = this.getTSatFromP(refP).getTGas();
		
		double x,y0,y1,x0,x1;


		if (H< satHLiquid) {
			outT = this.getT_SatCurve_FromH(H);
		}
		else if (H> satHGas) {
			outT = getTGasInterIsobarIsotherm(refP,H);
		}
		else {
			x  = H;
			x0 = satHLiquid;
			x1 = satHGas;
			if (x1==x0) {
				logger.error("(getIsobaricT):: 2 same value will cause and issue and must be removed ");
			}
			y0 = satTLiquid;
			y1 = satTGas;
			outT = (x-x0)*(y1-y0)/(x1-x0)+ y0;
		}
		
		return outT;
	}

	
	/**
	 * Get State of Isobaric 
	 * @param refP
	 * @param H
	 * @return
	 */
	public String getIsobaricState(double refP, double H) {
		String outState="Empty";

		double satHLiquid = this.getHSatFromP(refP).getHLiquid();
		double satHGas = this.getHSatFromP(refP).getHGas();
		
		if (H< satHLiquid) 
			outState = "Liquid";
		else if (H> satHGas)
			outState = "Gas";
		else
			outState = "Liquid+Gas";

		return outState;
	}

	// -----------------------------------------------------------------------------------------
	// 										IsoTherm 
	// -----------------------------------------------------------------------------------------

	public double getPIsotherm(double H, double T) {
		return getPIsotherm(H, T, this.getPSatFromT(T).getPLiquid());

	}


	public double getHIsotherm(double H, double T) {
		double outH = 0;
		double satHLiquid = this.getHSatFromT(T).getHLiquid();

		if (H< satHLiquid) { 
			outH = satHLiquid;
		} else {
			outH = H;
		}

		return(outH);
	}

	public double getPIsotherm(double H, double T, double P) {
		double outP = 0;

		double satHLiquid = this.getHSatFromT(T).getHLiquid();
		double satHGas = this.getHSatFromT(T).getHGas();

		double satPLiquid  = this.getPSatFromT(T).getPLiquid();
		double satPGas = this.getPSatFromT(T).getPGas();

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
			double H0 = this.getIsoTherm_H0_Delta() * 
					(T- this.getIsoTherm_T0_Ref())/this.getIsoTherm_T0_Delta() + 
					this.getIsoTherm_H0_Ref();
			double P0 = this.getIsoTherm_P0_Ref();
			logger.debug("  (getPIsotherm):: H={}>satHGas={}  Ha={} Pa={} H0={} P0={}",H,satHGas,Ha,Pa,H0,P0);

			double n = 2;
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

	
	// -----------------------------------------------------------------------------------------
	// 										Intersection with Isobar 
	// -----------------------------------------------------------------------------------------
	
	/**
	 * T Gas Inter Isobar Isotherm --> Will determine T (Approximation !!)
	 * @param PRef
	 * @param H
	 * @return
	 */
	public double getTGasInterIsobarIsotherm(double PRef, double H) {
		double outT = 0;
		double h=0;
		// 	Based of function getHGasInterIsobarIsotherm() 	
		
		double TA = this.getTSatFromP(PRef).getTGas();
		
		for (double t = TA+0.5; t<this.getTmax(); t=t+0.5  ) {
			outT=t;
			h = getHGasInterIsobarIsotherm(PRef,t);
			if (h > H)
				break;
		}
		
		return(outT);
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

		double Pa = this.getPSatFromT(T).getPGas();
		double Ha = this.getHSatFromT(T).getHGas();
		double H0 = this.getIsoTherm_H0_Delta() * 
				(T- this.getIsoTherm_T0_Ref())/this.getIsoTherm_T0_Delta() + 
				this.getIsoTherm_H0_Ref();
		double P0 = this.getIsoTherm_P0_Ref();
		double n = 2;
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

	public String loadRfgGasSaturationData(String fileNameGas) {
		this.rfgName = loadGasSaturationData(fileNameGas);
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

}
