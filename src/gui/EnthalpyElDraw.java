package gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mpoints.EloMeasurePoint;
import mpoints.MeasurePoint;
import pac.Pac;
import refrigerant.Refrigerant;

public class EnthalpyElDraw {

	// -------------------------------------------------------
	// 					CONSTANT
	// -------------------------------------------------------
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private EloEnthalpyElDraw 	enthElDrawObject;			// Name of the element
	private List<ElDraw> 		lElDraw;					// Ensemble of simple draw element
	private boolean 			movable;					// Can the element be moved
	private String				textDisplay;				// Text to display 
	private boolean 			textDisplayPositionAbove;	// Text Display position: Above or Below

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	/*
	P
	^
	|
	|
	|                     XXXXXXXXXXXXX
	|                  XXXX           XXXXX         (PK)
	|        (5)+-----(4)-----------------(3)--------------------+ (2)
	|           | XXXX                      XX                   /
	|           |XX                          XX                 /
	|           |X                            XX               /
	|          X|                              XX             /
	|         XX|                               X            /
	|         X |                               XX          /
	|        XX |             (P0)               X         /
	|       XX  +---------------------------------+---+---+
	|       X  (6)                               (7) (8)  (1)
	|      XX                                     X
	|      X                                      X
	|
	+------------------------------------------------------------------------> H

	 */

	/**
	 * 
	 * @param venthElDrawObject
	 * @param lMeasurePoints
	 * 
	 * 		EnthalpyElDraw enthalpyElDraw_P1 = new EnthalpyElDraw(EloEnthalpyElDraw.P1,lMeasurePoints);
	 */
	public EnthalpyElDraw(EloEnthalpyElDraw venthElDrawObject, List<MeasurePoint> lMeasurePoints ) {
		Funct_EnthalpyElDraw(venthElDrawObject, lMeasurePoints);	
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------

	private void Funct_EnthalpyElDraw(
			EloEnthalpyElDraw venthElDrawObject, 
			List<MeasurePoint> lMeasurePoints ) {
		MeasurePoint m;

		enthElDrawObject = venthElDrawObject;

		switch (enthElDrawObject) {

		case P1:
			// Get the Mesure Point information of the element to draw
			m = lMeasurePoints.get(EloMeasurePoint.P1.id()); 
			logger.info("Point={} H={} P={}", enthElDrawObject.getText(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));

			lElDraw = new ArrayList<ElDraw>(); 
			lElDraw.add(new ElDraw(EloElDraw.POINT,Color.GREEN,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
			movable = true;
			textDisplay = enthElDrawObject.getText();
			textDisplayPositionAbove = false;
			break;
		case P2:
			// Get the Mesure Point information of the element to draw
			m = lMeasurePoints.get(EloMeasurePoint.P2.id()); 
			logger.info("Point={} H={} P={}", enthElDrawObject.getText(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));

			lElDraw = new ArrayList<ElDraw>(); 
			lElDraw.add(new ElDraw(EloElDraw.POINT,Color.GREEN,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
			movable = true;
			textDisplay = enthElDrawObject.getText();
			textDisplayPositionAbove = true;
			break;
		case P3:
			// Get the Mesure Point information of the element to draw
			m = lMeasurePoints.get(EloMeasurePoint.P3.id()); 
			logger.info("Point={} H={} P={}", enthElDrawObject.getText(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));
			lElDraw = new ArrayList<ElDraw>(); 
			lElDraw.add(new ElDraw(EloElDraw.POINT,Color.RED,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
			movable = false;
			textDisplay = enthElDrawObject.getText();
			textDisplayPositionAbove = true;
			break;
		case P4:
			// Get the Mesure Point information of the element to draw
			m = lMeasurePoints.get(EloMeasurePoint.P4.id()); 
			logger.info("Point={} H={} P={}", enthElDrawObject.getText(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));
			lElDraw = new ArrayList<ElDraw>(); 
			lElDraw.add(new ElDraw(EloElDraw.POINT,Color.RED,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
			movable = false;
			textDisplay = enthElDrawObject.getText();
			textDisplayPositionAbove = true;
			break;
		case P5:
			// Get the Mesure Point information of the element to draw
			m = lMeasurePoints.get(EloMeasurePoint.P5.id()); 
			logger.info("Point={} H={} P={}", enthElDrawObject.getText(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));
			lElDraw = new ArrayList<ElDraw>(); 
			lElDraw.add(new ElDraw(EloElDraw.POINT,Color.GREEN,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
			movable = true;
			textDisplay = enthElDrawObject.getText();
			textDisplayPositionAbove = true;
			break;
		case P6:
			// Get the Mesure Point information of the element to draw
			m = lMeasurePoints.get(EloMeasurePoint.P6.id()); 
			logger.info("Point={} H={} P={}", enthElDrawObject.getText(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));
			lElDraw = new ArrayList<ElDraw>(); 
			lElDraw.add(new ElDraw(EloElDraw.POINT,Color.GREEN,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
			movable = true;
			textDisplay = venthElDrawObject.getText();
			textDisplayPositionAbove = false;
			break;
		case P7:
			// Get the Mesure Point information of the element to draw
			m = lMeasurePoints.get(EloMeasurePoint.P7.id()); 
			logger.info("Point={} H={} P={}", enthElDrawObject.getText(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));
			lElDraw = new ArrayList<ElDraw>(); 
			lElDraw.add(new ElDraw(EloElDraw.POINT,Color.RED,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
			movable = false;
			textDisplay = venthElDrawObject.getText();
			textDisplayPositionAbove = false;
			break;
		case P8:
			// Get the Mesure Point information of the element to draw
			m = lMeasurePoints.get(EloMeasurePoint.P8.id()); 
			logger.info("Point={} H={} P={}", enthElDrawObject.getText(),m.getMP_H(),m.getMP_P0PK(lMeasurePoints));
			lElDraw = new ArrayList<ElDraw>(); 
			lElDraw.add(new ElDraw(EloElDraw.POINT,Color.GREEN,m.getMP_H(),m.getMP_P0PK(lMeasurePoints)));
			movable = true;
			textDisplay = venthElDrawObject.getText();
			textDisplayPositionAbove = false;
			break;
		case LINE_P0:
			m = lMeasurePoints.get(EloMeasurePoint.P7.id()); 
			logger.info("{} Y={}", enthElDrawObject.getText(),m.getMP_P0PK(lMeasurePoints));
			lElDraw = new ArrayList<ElDraw>(); 
			lElDraw.add(new ElDraw(EloElDraw.LINE_HORZ,Color.BLACK,m.getMP_P0PK(lMeasurePoints)));
			movable = false;
			textDisplay = venthElDrawObject.getText();
			textDisplayPositionAbove = false;
			break;
		case LINE_PK:
			m = lMeasurePoints.get(EloMeasurePoint.P3.id()); 
			logger.info("{} Y={}", enthElDrawObject.getText(),m.getMP_P0PK(lMeasurePoints));
			lElDraw = new ArrayList<ElDraw>(); 
			lElDraw.add(new ElDraw(EloElDraw.LINE_HORZ,Color.BLACK,m.getMP_P0PK(lMeasurePoints)));
			movable = false;
			textDisplay = venthElDrawObject.getText();
			textDisplayPositionAbove = false;
			break;
		default:
			lElDraw = new ArrayList<ElDraw>(); 
			lElDraw.add(new ElDraw(EloElDraw.POINT,Color.BLACK,0.0,0.0));
			movable = false;
			textDisplay = "Error";
			textDisplayPositionAbove = false;
			break;
		}
	}


	/**
	 * EnthalpyElDraw isoThDw = new EnthalpyElDraw(EloEnthalpyElDraw.ISOTHERM,lMeasurePoints);
	 * isoThDw.set(ISOTHERM,lMeasurePoints,pac,isoThermT)
	 * 
	 * @param venthElDrawObject
	 * @param lMeasurePoints
	 * @param vPac
	 * @param isoThermT
	 */
	public void set(EloEnthalpyElDraw venthElDrawObject, List<MeasurePoint> lMeasurePoints, Pac vPac, double isoThermT ) {
		lElDraw.clear();
		switch (enthElDrawObject) {
		case ISOTHERM:
			logger.trace("{}", enthElDrawObject.getText());

			Refrigerant refrigerant = vPac.getRefrigerant();
			/*
				+
				|
				|
				|
				|           XXXXX
				|       XXXX     XXXX
				|   XXXX            XX
	PSatLiq     | XX                  XX     PSatGas
	HSatLiq    ++--------------------------+ HSatGas
				XX                      X  +-+
			   XX                       X    +--+
			   X                        X       |
			  X                        XX       +--+
			  X                       XX           |
			  X                      XX            +--+
			  X                      X                +
									XX
										X                HO_T

			 */
			double PSatLiq = refrigerant.getPSatFromT(isoThermT).getPLiquid();
			double HSatLiq = refrigerant.getHSatFromT(isoThermT).getHLiquid();

			double PSatGas = refrigerant.getPSatFromT(isoThermT).getPGas();
			double HSatGas = refrigerant.getHSatFromT(isoThermT).getHGas();

			double H0_T = refrigerant.getIsoTherm_H0_T(isoThermT);

			lElDraw = new ArrayList<ElDraw>(); 
			lElDraw.add(new ElDraw(EloElDraw.LINE_DASHED,Color.RED,HSatLiq,PSatLiq,HSatGas,PSatGas));

			for(int h=0;h<(int)(H0_T-HSatGas);h++) {
				double x1,x2,y1,y2;
				x1 = HSatGas + h;
				x2 = HSatGas + h + 1;
				y1 = refrigerant.getPIsotherm(x1, isoThermT); 
				y2 = refrigerant.getPIsotherm(x2, isoThermT);
				lElDraw.add(new ElDraw(EloElDraw.LINE_DASHED,Color.RED,x1,y1,x2,y2));	
				logger.trace("   x1={} y1={}  x2={} y2={}", x1,y1,x2,y2);

			}
			movable = false;
			textDisplay = null;
			textDisplayPositionAbove = false;
			break;
		default:

			break;
		}
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public EloEnthalpyElDraw getEnthElDrawObject() {
		return enthElDrawObject;
	}

	public void setEnthElDrawObject(EloEnthalpyElDraw enthElDrawObject) {
		this.enthElDrawObject = enthElDrawObject;
	}

	public List<ElDraw> getlElDraw() {
		return lElDraw;
	}

	public void setlElDraw(List<ElDraw> lElDraw) {
		this.lElDraw = lElDraw;
	}

	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	public String getTextDisplay() {
		return textDisplay;
	}

	public void setTextDisplay(String textDisplay) {
		this.textDisplay = textDisplay;
	}

	public boolean isTextDisplayPositionAbove() {
		return textDisplayPositionAbove;
	}

	public void setTextDisplayPositionAbove(boolean textDisplayPositionAbove) {
		this.textDisplayPositionAbove = textDisplayPositionAbove;
	}



}
