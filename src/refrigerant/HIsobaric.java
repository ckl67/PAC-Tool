package refrigerant;

public class HIsobaric {

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private int zone;			// zone = 1 ==> HSat [HLiquid, HGas] has to be considered
								// zone = 0 ==> H has to be considered
 	private HSat hSat;
	private double h;
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public HIsobaric() {
		zone = 0;
		hSat = new HSat();
		h = 0;
	}

	public int getZone() {
		return zone;
	}

	public void setZone(int zone) {
		this.zone = zone;
	}

	public HSat getHSat() {
		return hSat;
	}

	public void setHSat(HSat hSat) {
		this.hSat = hSat;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}
	
}
