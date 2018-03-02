package mpoints;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import misc.Misc;
import pac.Compressor;
import pac.Pac;


public class MeasureResult {

	// -------------------------------------------------------
	// 					CONSTANT
	// -------------------------------------------------------
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private EloMeasureResult mRObject;			// T1-T2,H1-H3,.. + Definition
	private double value;						// 

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	// Will create 1 object MeasureResult T1-T2,H1-H3,.. (of the EloMeasureResult), and based on
	// all measured points, will return the result
	// Exemple
	//			 MeasureResult result_T1_T2 = new MeasureResult("T1-T2"; lMeasurePoints)
	//
	public MeasureResult (EloMeasureResult vmRObject, List<MeasurePoint> vmeasurePointL, Pac vPac) {
		mRObject = vmRObject;
		value = 0;
		
		double Ta=0,Tb=0,Ha=0,Hb=0,result=0;

		switch (vmRObject) {
		case T1_T8: 
			Ta = vmeasurePointL.get(EloMeasurePoint.P1.id()).getMP_T();
			Tb = vmeasurePointL.get(EloMeasurePoint.P8.id()).getMP_T();
			result = Ta-Tb;
			this.value = Math.round(result*100.0)/100.0; 
			break;
		case T2_T1: 
			Ta = vmeasurePointL.get(EloMeasurePoint.P2.id()).getMP_T();
			Tb = vmeasurePointL.get(EloMeasurePoint.P1.id()).getMP_T();
			result = Ta-Tb;
			this.value = Math.round(result*100.0)/100.0; 
			break;
		case T3_T2:
			Ta = vmeasurePointL.get(EloMeasurePoint.P3.id()).getMP_T();
			Tb = vmeasurePointL.get(EloMeasurePoint.P2.id()).getMP_T();
			result = Ta-Tb;
			this.value = Math.round(result*100.0)/100.0; 
			break;
		case T3T4:
			Ta = vmeasurePointL.get(EloMeasurePoint.P3.id()).getMP_T();
			result = Ta;
			this.value = Math.round(result*100.0)/100.0; 
			break;
		case T4_T5:
			Ta = vmeasurePointL.get(EloMeasurePoint.P4.id()).getMP_T();
			Tb = vmeasurePointL.get(EloMeasurePoint.P5.id()).getMP_T();
			result = Ta-Tb;
			this.value = Math.round(result*100.0)/100.0; 
			break;
		case T5_T6:
			Ta = vmeasurePointL.get(EloMeasurePoint.P5.id()).getMP_T();
			Tb = vmeasurePointL.get(EloMeasurePoint.P6.id()).getMP_T();
			result = Ta-Tb;
			this.value = Math.round(result*100.0)/100.0; 
			break;
		case T7:
			Ta = vmeasurePointL.get(EloMeasurePoint.P7.id()).getMP_T();
			result = Ta;
			this.value = Math.round(result*100.0)/100.0; 
			break;
		case T8_T7:
			Ta = vmeasurePointL.get(EloMeasurePoint.P8.id()).getMP_T();
			Tb = vmeasurePointL.get(EloMeasurePoint.P7.id()).getMP_T();
			result = Ta-Tb;
			this.value = Math.round(result*100.0)/100.0; 
			break;
		case H2_H1:
			Ha = vmeasurePointL.get(EloMeasurePoint.P2.id()).getMP_H();
			Hb = vmeasurePointL.get(EloMeasurePoint.P1.id()).getMP_H();
			result = Ha-Hb;
			this.value = Math.round(result*100.0)/100.0; 
			break;
		case H2_H5:
			Ha = vmeasurePointL.get(EloMeasurePoint.P2.id()).getMP_H();
			Hb = vmeasurePointL.get(EloMeasurePoint.P5.id()).getMP_H();
			result = Ha-Hb;
			this.value = Math.round(result*100.0)/100.0; 
			break;
		case COPCOLD:
			result = cop_carnot_cold(vmeasurePointL);  
			this.value = Math.round(result*100.0)/100.0; 
			break;
		case COPHOT:
			result = cop_carnot_hot(vmeasurePointL);  
			this.value = Math.round(result*100.0)/100.0; 
			break;
		case COP:
			result = cop(vmeasurePointL,vPac);  
			this.value = Math.round(result*100.0)/100.0; 
			break;
		default :
			break;

		} 
		logger.trace("(MeasureResult):: {}= {} {}",this.mRObject.getDisplayTxt(),this.value,this.mRObject.getUnity());

		
	}
	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	public EloMeasureResult getMRObject() {
		return mRObject;
	}

	public double getValue() {
		return value;
	}

	/**
	 * Compute Cop Carnot Froid
	 * @return : T0/(TK-T0)
	 */
	private double cop_carnot_cold(List<MeasurePoint> measurePointL) {
		double result = 0;

		double T0 = measurePointL.get(EloMeasurePoint._PR0id).getMP_T();
		double TK = measurePointL.get(EloMeasurePoint._PRKid).getMP_T();

		if ((TK-T0) != 0.0) {
			result = (T0+273)/(TK-T0);
		}
		return result;		
	}

	/**
	 *  Compute Cop Carnot Chaud
	 * @return : TK/(TK-T0)
	 */
	private double cop_carnot_hot(List<MeasurePoint> measurePointL) {
		double result = 0;

		double T0 = measurePointL.get(EloMeasurePoint._PR0id).getMP_T();
		double TK = measurePointL.get(EloMeasurePoint._PRKid).getMP_T();

		if ((TK-T0) != 0.0) {
			result = (TK+273)/(TK-T0);
		}
		return result;		
	}

	/**
	 * Compute Cop 
	 */
	private double cop(List<MeasurePoint> measurePointL, Pac pac) {
		double COP = 0;
		logger.trace("(cop)::");
		double travailCompresseur = measurePointL.get(EloMeasurePoint.P2.id()).getMP_H() - 
				measurePointL.get(EloMeasurePoint.P1.id()).getMP_H();
		logger.trace("    travailCompresseur: {} = delta d’enthalpie entre les points 2-->({}) et 1-->({})",
				travailCompresseur,
				measurePointL.get(EloMeasurePoint.P2.id()).getMP_H(),
				measurePointL.get(EloMeasurePoint.P1.id()).getMP_H()
				);

		double puissanceCalorifique = measurePointL.get(EloMeasurePoint.P2.id()).getMP_H() - 
				measurePointL.get(EloMeasurePoint.P5.id()).getMP_H();
		logger.trace("    puissanceCalorifique: {} = delta d’enthalpie entre les points 2-->({}) et 5-->({})",
				puissanceCalorifique,
				measurePointL.get(EloMeasurePoint.P2.id()).getMP_H(),
				measurePointL.get(EloMeasurePoint.P5.id()).getMP_H()
				);

		double rapportPcalSurTravComp = puissanceCalorifique/travailCompresseur;
		logger.trace("    Rapport entre puissance calorifique et travail compresseur = {}",
				rapportPcalSurTravComp
				);
		
		Compressor compressor = pac.getCompressor();
		double cosPhi = Math.round(
				Misc.cosphi(
						compressor.getPower(), 
						compressor.getVoltage(), 
						compressor.getCurrent())
				);
		logger.trace("    cos phi = {}",
				cosPhi
				);

		double puissanceAbsorbee = compressor.getCurrentMeasure() * 
				compressor.getVoltageMeasure();
		logger.trace("    puissance Absorbée = {}",
				puissanceAbsorbee
				);

		double puissanceUtile = puissanceAbsorbee * cosPhi;
		logger.trace("    puissance Utile = {}",
				puissanceUtile
				);
		

		double puissanceDispoArbreMoteur = puissanceUtile * compressor.getPowerShaftPercent()/100.0;
		logger.trace("    rendement d'un moteur électrique monophasé en % = {}",
				compressor.getPowerShaftPercent()
				);
		logger.trace("    puissance disponible sur l'arbre moteur = {}",
				puissanceDispoArbreMoteur
				);

		
		double puissanceCalorifiqueVrai =  puissanceDispoArbreMoteur * rapportPcalSurTravComp;
		logger.trace("    puissance calorifique = {}",
				puissanceCalorifiqueVrai
				);

		COP = puissanceCalorifiqueVrai / puissanceAbsorbee;
		logger.trace("     COP = {} --> puissanceCalorifiqueVrai({}) / puissanceAbsorbee({})",
				COP,
				puissanceCalorifiqueVrai,
				puissanceAbsorbee
				);

		return COP;		
	}


}
