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

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------
	public Ccop() {
		this.T0= 0.0;
		this.TK= 0.0;
		this.H1= 0.0;
		this.H2= 0.0;
		this.H3= 0.0;
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	/**
	 * Compute COP Carnot Froid
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
	 *  Compute COP Carnot Chaud
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
	 * Compute COP Froid
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
	 * Compute COP Chaud
	 * @return : QK/W = (H2-H3)/(H2-H1)
	 */
	public double cop_chaud() {
		double result = 0;

		if ((H2-H1) != 0.0) {
			result = (H2-H3)/(H2-H1);
		}
		return result;		
	}


	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------

	public void setT0(double t0) {
		this.T0 = t0;
	}

	public void setTK(double tK) {
		this.TK = tK;
	}

	public void setH1(double h1) {
		this.H1 = h1;
	}

	public void setH2(double h2) {
		this.H2 = h2;
	}

	public void setH3(double h3) {
		this.H3 = h3;
	}
}
