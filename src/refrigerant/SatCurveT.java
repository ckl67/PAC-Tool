package refrigerant;

public class SatCurveT {

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private double TGas;
	private double TLiquid;
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public SatCurveT() {
		TGas = 0.0;
		TLiquid = 0.0;
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	public double getTGas() {
		return TGas;
	}

	public void setTGas(double TGas) {
		this.TGas = TGas;
	}

	public double getTLiquid() {
		return TLiquid;
	}

	public void setTLiquid(double TLiquid) {
		this.TLiquid = TLiquid;
	}

	
}
