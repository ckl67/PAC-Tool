package refrigerant;

public class HSat {

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private double HGas;
	private double HLiquid;
	
	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public HSat() {
		HGas = 0.0;
		HLiquid = 0.0;
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	public double getHGas() {
		return HGas;
	}

	public void setHGas(double HGas) {
		this.HGas = HGas;
	}

	public double getHLiquid() {
		return HLiquid;
	}

	public void setHLiquid(double HLiquid) {
		this.HLiquid = HLiquid;
	}

	
}
