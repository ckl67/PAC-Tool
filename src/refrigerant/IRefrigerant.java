package refrigerant;

public interface IRefrigerant {

	public double getPIsotherm(double H, double T, double P);
	public double getPIsotherm(double H, double T);
	public double getHIsotherm(double H, double T);
	public TSat getTSatFromP(double pressure);
	public PSat getPSatFromT(double temp);
	public HSat getHSatFromP(double pressure);
	public HSat getHSatFromT(double temp);
	public double getT_SatCurve_FromH(double vH);
	public double getT_SatCurve_FromH(double vH, double vP);
	public double getHGasInterIsobarIsotherm(double PRef, double T);
	public double getIsoTherm_H0_T(double T);
	public int getSatTableSize();
	public double getHSat_Liquid(int n);
	public double getPSat_Liquid(int n);
	public double getHSat_Gas(int n);
	public double getPSat_Gas(int n);
	public double getP_SatCurve_FromH(double vH);
	public double getP_SatCurve_FromH(double vH, double vP);
	public double getIsobaricT(double refP, double H);
	public double getTSat(int n);
	
	
}
