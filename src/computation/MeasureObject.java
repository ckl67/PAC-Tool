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
package computation;

import translation.Translation;

public enum MeasureObject {
	T1(517,99,Translation.DEF_MOBJ_T1,"°C",MeasureGroup.GROUP_BP),
	T2(549,99,Translation.DEF_MOBJ_T2,"°C",MeasureGroup.GROUP_HP),
	P3(562,144,Translation.DEF_MOBJ_P3,"Bar",MeasureGroup.GROUP_HP),
	P4(562,214,Translation.DEF_MOBJ_P4,"Bar",MeasureGroup.GROUP_HP),
	T5(515,255,Translation.DEF_MOBJ_T5,"°C",MeasureGroup.GROUP_HP),
	T6(437,255,Translation.DEF_MOBJ_T6,"°C",MeasureGroup.GROUP_BP),
	P7(419,178,Translation.DEF_MOBJ_P7,"Bar",MeasureGroup.GROUP_BP ),
	T8(481,98,Translation.DEF_MOBJ_T8,"°C",MeasureGroup.GROUP_BP),
	TMi(663,289,Translation.DEF_MOBJ_TMi,"°C",MeasureGroup.GROUP_HEAT),
	TMo(664,65,Translation.DEF_MOBJ_TMo,"°C",MeasureGroup.GROUP_HEAT),
	TCi(326,290,Translation.DEF_MOBJ_TCi,"°C",MeasureGroup.GROUP_SOURCE),
	TCo(326,66,Translation.DEF_MOBJ_TCo,"°C",MeasureGroup.GROUP_SOURCE);

	;
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
	private Translation definition;
	private String unity;
	private MeasureGroup groupHpBp; 

	// -------------------------------------------------------
	// 					CONSTRUCTOR
	// -------------------------------------------------------
	MeasureObject(int vxm, int vym,Translation vdefinition, String vunity, MeasureGroup vgroupHpBp){
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

	public String getDefinition(int langage) {
		return definition.getLangue(langage);
	}

	public String getUnity() {
		return unity;
	}

	public MeasureGroup getGroupHpBp() {
		return groupHpBp;
	}

}
