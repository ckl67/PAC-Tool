package pacp;

public class PACcompr {

	private double Evap;
	private double Cond;
	private double RG;
	private double Liq;
	private double Capacity;
	private double Power;
	private double Current;
	private double MassFlow;
	private double Voltage;
	
	// ------------ Constructor
	PACcompr() {
		// ZR40K3-PFG
		Evap = 45;
		Cond = 130;
		RG = 65;
		Liq = 115;
		Capacity = 33300;
		Power = 3000;
		Current = 14.7;
		MassFlow = 488;		
		Voltage = 220;		
	}
	
	// ----------------------------------------------------------------------------
	// Setter
	// ----------------------------------------------------------------------------
	public void setEvap(double v) {
		Evap = v;
	}
	
	public void setCond(double v) {
		Cond = v;
	}

	public void setRG(double v) {
		RG = v;
	}

	public void setLiq(double v) {
		Liq = v;
	}

	public void setCapacity(double v) {
		Capacity = v;
	}

	public void setPower(double v) {
		Power = v;
	}
	
	public void setCurrent(double v) {
		Current = v;
	}
	
	public void setMassFlow(double v) {
		MassFlow = v;
	}
	
	public void setVoltage(double v) {
		Voltage = v;
	}
	
	// ----------------------------------------------------------------------------
	// Getter
	// ----------------------------------------------------------------------------
	public double getEvap() {
		return Evap;		
	}
	
	public double getCond() {
		return Cond;		
	}

	public double getRG() {
		return RG;		
	}

	public double getLiq() {
		return Liq;		
	}

	public double getCapacity() {
		return Capacity;		
	}

	public double getPower() {
		return Power;		
	}
	
	public double getCurrent() {
		return Current;		
	}
	
	public double getMassFlow() {
		return MassFlow;		
	}

	public double getVoltage() {
		return Voltage;		
	}

}
