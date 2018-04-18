package refrigerant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IsobaricCurve {

	// -------------------------------------------------------
	// 					CONSTANT
	// -------------------------------------------------------
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	
	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------

	// In this approach, we need SatCurve information ton compute Isotherm information
	private SatCurve satCurve;
	private IsoThermCurve isoThermCurve;
	
	
	public IsobaricCurve(SatCurve vSatcurve, IsoThermCurve vIsoThermCurve) {

		satCurve = vSatcurve;
		isoThermCurve = vIsoThermCurve; 
	}
	
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
		double satHLiquid = satCurve.getHSatFromP(refP).getHLiquid();
		double satHGas = satCurve.getHSatFromP(refP).getHGas();

		double satTLiquid = satCurve.getTSatFromP(refP).getTLiquid();
		double satTGas = satCurve.getTSatFromP(refP).getTGas();
		
		double x,y0,y1,x0,x1;


		if (H< satHLiquid) {
			outT = satCurve.getT_SatCurve_FromH(H);
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

		double satHLiquid = satCurve.getHSatFromP(refP).getHLiquid();
		double satHGas = satCurve.getHSatFromP(refP).getHGas();
		
		if (H< satHLiquid) 
			outState = "Liquid";
		else if (H> satHGas)
			outState = "Gas";
		else
			outState = "Liquid+Gas";

		return outState;
	}
	
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
		
		double TA = satCurve.getTSatFromP(PRef).getTGas();
		
		for (double t = TA+0.5; t<satCurve.getTmax(); t=t+0.5  ) {
			outT=t;
			h = isoThermCurve.getHGasInterIsobarIsotherm(PRef,t);
			if (h > H)
				break;
		}
		
		return(outT);
	}
	


}
