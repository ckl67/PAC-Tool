package pacp;

public class PACcop extends Scroll {

	private double T0;
	private double TK;
	private double H1;
	private double H2;
	private double H3;
	
	// -------- Constructeurs --------
	public PACcop() {
		T0 = 0.0;
		TK = 0.0;
		H1 = 0.0;
		H2 = 0.0;
		H3 = 0.0;
	}
		
	// ---------- Mutateurs ----------  
	public void setT0(double vT0) {
		T0 = vT0;				
	}

	public void setTK(double vTK) {
		TK = vTK;				
	}

	public void setH1(double vH1) {
		H1 = vH1;				
	}
	
	public void setH2(double vH2) {
		H2 = vH2;				
	}
	
	public void setH3(double vH3) {
		H3 = vH3;				
	}

	// ---------- Assesseurs ----------
	public double getT0() {
		return T0;
	}

	public double getTK() {
		return TK;
	}
	
	public double getH1() {
		return H1;
	}

	public double getH2() {
		return H2;
	}

	public double getH3() {
		return H3;
	}

	// ---------- Fonctions -----------
	/**
	 * Calcul COP Carnot Froid
	 * @return : T0/(TK-T0)
	 */
	public double cop_carnot_froid() {
		double result = 0;
		
		if ((TK-T0) != 0.0) {
			result = (T0+273)/(TK-T0);
		}
		return result;		
	}
	/**
	 *  Calcul COP Carnot Chaud
	 *  Coefficient de performance thermique d'une machine de Carnot
	*   Pour une pompe à chaleur, on s'intéresse au rapport entre la chaleur fournie à la source chaude 
	*   et le travail nécessaire à cette opération. 
	 * @return : TK/(TK-T0)
	 */
	public double cop_carnot_chaud() {
		double result = 0;
		
		if ((TK-T0) != 0.0) {
			result = (TK+273)/(TK-T0);
		}
		return result;		
	}

	/**
	 * Calcul COP Froid
	 * @return : Q0/W = (H1-H3)/(H2-H1)
	 */
	public double cop_froid() {
		double result = 0;
		
		if ((H2-H1) != 0.0) {
			result = (H1-H3)/(H2-H1);
		}
		return result;		
	}

	/**
	 * Calcul COP Chaud
	 * @return : QK/W = (H2-H3)/(H2-H1)
	 */
	public double cop_chaud() {
		double result = 0;
		
		if ((H2-H1) != 0.0) {
			result = (H2-H3)/(H2-H1);
		}
		return result;		
	}

	/**
	 * Calcul le rendement Chaud
	 * @return : COP Chaud / COP Carnot Chaud
	 */
	public double cop_rendement_chaud() {
		double result = 0;
		
		if ((cop_carnot_chaud()) != 0.0) {
			result = (cop_chaud())/(cop_carnot_chaud());
		}
		return result;		
	}
	
	/**
	 * Calcul le rendement Froid
	 * @return : COP Froid / COP Carnot Froid
	 */
	public double cop_rendement_froid() {
		double result = 0;
		
		if ((cop_carnot_froid()) != 0.0) {
			result = (cop_froid())/(cop_carnot_froid());
		}
		return result;		
	}

}
