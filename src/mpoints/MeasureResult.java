package mpoints;

import java.util.List;

import computation.Comp;

public class MeasureResult {

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
	public MeasureResult (EloMeasureResult vmRObject, List<MeasurePoint> vmeasurePointL) {
		double va,vb,Ta,Tb,Pa,Pb,Ha,Hb,out;
		
		switch (vmRObject) {
		case T1_T8: 
			va = vmeasurePointL.get(EloMeasurePoint.P1.id()).getValue();
			vb = vmeasurePointL.get(EloMeasurePoint.P8.id()).getValue();

			setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
			break;
		case T2_T1: 
			result = vmeasurePointL.get(EloMeasurePoint.P2.ordinal()).getMT()- vmeasurePointL.get(EloMeasurePoint.P1.ordinal()).getMT();  
			setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
			break;
		case T3_T2:
			result = vmeasurePointL.get(EloMeasurePoint.P3.ordinal()).getMT()- vmeasurePointL.get(EloMeasurePoint.P2.ordinal()).getMT();  
			setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
			break;
		case T3T4:
			result = vmeasurePointL.get(EloMeasurePoint.P3.ordinal()).getMT();  
			setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
			break;
		case T4_T5:
			result = vmeasurePointL.get(EloMeasurePoint.P4.ordinal()).getMT()- vmeasurePointL.get(EloMeasurePoint.P5.ordinal()).getMT();  
			setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
			break;
		case T5_T6:
			result = vmeasurePointL.get(EloMeasurePoint.P5.ordinal()).getMT()- vmeasurePointL.get(EloMeasurePoint.P6.ordinal()).getMT();  
			setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
			break;
		case T7:
			result = vmeasurePointL.get(EloMeasurePoint.P7.ordinal()).getMT();  
			setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
			break;
		case T8_T7:
			result = vmeasurePointL.get(EloMeasurePoint.P8.ordinal()).getMT()- vmeasurePointL.get(EloMeasurePoint.P7.ordinal()).getMT();  
			setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
			break;
		case COPCOLD:
			result = Comp.cop_carnot_froid(vmeasurePointL);  
			setValueAt( Math.round(result*100.0)/100.0, p.ordinal(), 2);
			break;
		default :
			break;

		} 
		logger.info(" {}= {} {}",p.getDisplayTxt(),result,p.getUnity());

		
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



}
