/*
 * - PAC-Tool - 
 * Tool for understanding basics and computation of PAC (Pompe � Chaleur)
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
package computation;

public enum MeasureObject {
	T1(517,99,"Temp�rature des gaz BP\n apr�s surchauffe interne\n et avant compression","�C",MeasureGroup.GROUP_BP),
	T2(549,99,"Temp�rature des gaz HP\n en fin de compression\n (Cloche du compresseur)","�C",MeasureGroup.GROUP_HP),
	P3(562,144,"Pression du d�but de condensation\n (Mesure HP Manifod)","Bar",MeasureGroup.GROUP_HP),
	P4(562,214,"Pression de fin de condensation\n (Mesure HP Manifod)","Bar",MeasureGroup.GROUP_HP),
	T5(515,255,"Temp�rature des gaz HP\n apr�s sous refroidissement","�C",MeasureGroup.GROUP_HP),
	T6(437,255,"Temp�rature sortie D�tendeur / Capillaire","�C",MeasureGroup.GROUP_BP),
	P7(419,178,"Pression �vaporation\n (Mesure BP Manifold)","Bar",MeasureGroup.GROUP_BP ),
	T8(481,98, "Temp�rature des gaz HP\napr�s surchauffe externe","�C",MeasureGroup.GROUP_BP),
	TMi(663,289,"Temp�rature Retour Eau Chauffage","�C",MeasureGroup.GROUP_HEAT),
	TMo(664,65,"Temp�rature D�part Eau Chauffage","�C",MeasureGroup.GROUP_HEAT),
	TCi(326,290,"Temp�rature Retour Eau Captage","�C",MeasureGroup.GROUP_SOURCE),
	TCo(326,66,"Temp�rature D�part Eau Captage","�C",MeasureGroup.GROUP_SOURCE);

	// --------------------------------------------------------------------
	// CONSTANTS
	// --------------------------------------------------------------------
	public static int _P0 = 6;	// = P7
	public static int _PK = 2;	// = P3

	public static int _BP_ID 		= 6;
	public static int _PK_VAPOR_ID 	= 2;  	// = P3
	public static int _PK_LIQUID_ID = 3;	// = P4

	// --------------------------------------------------------------------
	// DEFINITION OF VARIABLES USED IN ENUMERATION
	// --------------------------------------------------------------------

	private int xm;
	private int ym;
	private String definition;
	private String unity;
	private MeasureGroup groupHpBp; 

	// -------------------------------------------------------
	// 					CONSTRUCTOR
	// -------------------------------------------------------
	MeasureObject(int vxm, int vym,String vdefinition, String vunity, MeasureGroup vgroupHpBp){
		this.xm = vxm;
		this.ym = vym;
		this.definition = vdefinition;
		this.unity =vunity;
		this.groupHpBp = vgroupHpBp;
	}

	// -------------------------------------------------------
	// 					GETTER AND SETTER
	// -------------------------------------------------------
	public int getXm() {
		return xm;
	}

	public int getYm() {
		return ym;
	}

	public String getDefinition() {
		return definition;
	}

	public String getUnity() {
		return unity;
	}

	public MeasureGroup getGroupHpBp() {
		return groupHpBp;
	}

}
