package pacp;

import org.json.simple.JSONObject;

public class Scroll {

	private String Name;
	private double Evap;
	private double Cond;
	private double RG;
	private double Liq;
	private double Capacity;
	private double Power;
	private double Current;
	private double MassFlow;
	private double Voltage;
	
	// ------------ Constructor Default
	Scroll() {
			setName("ZR40K3-PFG");
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
	
	@SuppressWarnings("unchecked")
	public JSONObject getJsonObject() {
		JSONObject ObjScroll = new JSONObject();  
		ObjScroll.put("Name", Name);
		ObjScroll.put("Evap", Evap);	
		ObjScroll.put("Cond", Cond);	
//		ObjScroll.put("", );	
		return(ObjScroll);
	}
	
	// ----------------------------------------------------------------------------
	// Setter
	// ----------------------------------------------------------------------------
	public void setName(String v) {
		this.Name = v;
	}

	public void setEvap(double v) {
		this.Evap = v;
	}
	
	public void setCond(double v) {
		this.Cond = v;
	}

	public void setRG(double v) {
		this.RG = v;
	}

	public void setLiq(double v) {
		this.Liq = v;
	}

	public void setCapacity(double v) {
		this.Capacity = v;
	}

	public void setPower(double v) {
		this.Power = v;
	}
	
	public void setCurrent(double v) {
		this.Current = v;
	}
	
	public void setMassFlow(double v) {
		this.MassFlow = v;
	}
	
	public void setVoltage(double v) {
		this.Voltage = v;
	}
	
	// ----------------------------------------------------------------------------
	// Getter
	// ----------------------------------------------------------------------------
	public String getName() {
		return Name;
	}
	
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
