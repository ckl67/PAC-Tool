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
	P1(517,99,Translation.DEF_MOBJ_P1,"°C",MeasureGroup.GROUP_BP),
	P2(549,99,Translation.DEF_MOBJ_P2,"°C",MeasureGroup.GROUP_HP),
	P3(562,144,Translation.DEF_MOBJ_P3,"Bar",MeasureGroup.GROUP_HP),
	P4(562,214,Translation.DEF_MOBJ_P4,"Bar",MeasureGroup.GROUP_HP),
	P5(515,255,Translation.DEF_MOBJ_P5,"°C",MeasureGroup.GROUP_HP),
	P6(437,255,Translation.DEF_MOBJ_P6,"°C",MeasureGroup.GROUP_BP),
	P7(419,178,Translation.DEF_MOBJ_P7,"Bar",MeasureGroup.GROUP_BP ),
	P8(481,98,Translation.DEF_MOBJ_P8,"°C",MeasureGroup.GROUP_BP),
	PMi(663,289,Translation.DEF_MOBJ_PMi,"°C",MeasureGroup.GROUP_HEAT),
	PMo(664,65,Translation.DEF_MOBJ_PMo,"°C",MeasureGroup.GROUP_HEAT),
	PCi(326,290,Translation.DEF_MOBJ_PCi,"°C",MeasureGroup.GROUP_SOURCE),
	PCo(326,66,Translation.DEF_MOBJ_PCo,"°C",MeasureGroup.GROUP_SOURCE);

	;
	// --------------------------------------------------------------------
	// CONSTANTS
	// --------------------------------------------------------------------
	public static int _P0 = 6;	// = P7
	public static int _PK = 2;	// = P3
	public static int _PK_VAPOR  = 2;  	// = P3
	public static int _PK_LIQUID = 3;	// = P4

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
	public int id() {
		return this.ordinal();
	}

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
