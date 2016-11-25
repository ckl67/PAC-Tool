/*
 * - PAC-Tool - 
 * Tool for understanding basics and computation of PAC (Pompe à Chaleur)
 * Copyright (C) 2016 christian.klugesherz@gmail.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (version 2)
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package pacp;

public class Ccop extends Pac {

	private double T0;
	private double TK;
	private double H1;
	private double H2;
	private double H3;
	
	// -------- Constructeurs --------
	public Ccop() {
		setT0(0.0);
		setTK(0.0);
		setH1(0.0);
		setH2(0.0);
		setH3(0.0);
	}

	// --------- Getter / Setter
	public double getT0() {
		return T0;
	}

	public void setT0(double t0) {
		T0 = t0;
	}

	public double getTK() {
		return TK;
	}

	public void setTK(double tK) {
		TK = tK;
	}

	public double getH1() {
		return H1;
	}

	public void setH1(double h1) {
		H1 = h1;
	}

	public double getH2() {
		return H2;
	}

	public void setH2(double h2) {
		H2 = h2;
	}

	public double getH3() {
		return H3;
	}

	public void setH3(double h3) {
		H3 = h3;
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
