package pacp;

public class Refrigerant extends Enthalpy {

	private String name;
	private double P;
	private double T;
	
	Refrigerant() {
		setName("R22");
		setP(0);
		setT(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getP() {
		return P;
	}

	public void setP(double p) {
		P = p;
	}

	public double getT() {
		return T;
	}

	public void setT(double t) {
		T = t;
	}
	
}
