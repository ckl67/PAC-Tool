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
	public double getIsobaricP(double refP, double H);
	public String getIsobaricState(double refP, double H);
	public String getGasFileNameSatCurve();
	public String getGasFileNameIsoThermCurve();
	
}
