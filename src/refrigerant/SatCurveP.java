package refrigerant;

public class SatCurveP {

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private double PGas;
	private double PLiquid;
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public SatCurveP() {
		PGas = 0.0;
		PLiquid = 0.0;
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	public double getPGas() {
		return PGas;
	}

	public void setPGas(double PGas) {
		this.PGas = PGas;
	}

	public double getPLiquid() {
		return PLiquid;
	}

	public void setPLiquid(double PLiquid) {
		this.PLiquid = PLiquid;
	}

	
}
