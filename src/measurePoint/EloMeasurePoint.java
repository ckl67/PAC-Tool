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
package measurePoint;

import translation.TLanguage;
import translation.TMeasurePoint;

public enum EloMeasurePoint {
	// Value possible for 
	P1(517,99,TMeasurePoint.DEF_MOBJ_P1,EloMeasurePointType.TEMP,"°C",EloMeasurePointGroup.BP),
	P2(549,99,TMeasurePoint.DEF_MOBJ_P2,EloMeasurePointType.TEMP,"°C",EloMeasurePointGroup.HP),
	P3(562,144,TMeasurePoint.DEF_MOBJ_P3,EloMeasurePointType.PRESS,"Bar",EloMeasurePointGroup.HP),
	P4(562,214,TMeasurePoint.DEF_MOBJ_P4,EloMeasurePointType.PRESS,"Bar",EloMeasurePointGroup.HP),
	P5(515,255,TMeasurePoint.DEF_MOBJ_P5,EloMeasurePointType.TEMP,"°C",EloMeasurePointGroup.HP),
	P6(437,255,TMeasurePoint.DEF_MOBJ_P6,EloMeasurePointType.TEMP,"°C",EloMeasurePointGroup.BP),
	P7(419,178,TMeasurePoint.DEF_MOBJ_P7,EloMeasurePointType.PRESS,"Bar",EloMeasurePointGroup.BP),
	P8(481,98,TMeasurePoint.DEF_MOBJ_P8,EloMeasurePointType.TEMP,"°C",EloMeasurePointGroup.BP),
	P9(664,65,TMeasurePoint.DEF_MOBJ_P9,EloMeasurePointType.TEMP,"°C",EloMeasurePointGroup.HEAT_DISTRIB),
	P10(663,289,TMeasurePoint.DEF_MOBJ_P10,EloMeasurePointType.TEMP,"°C",EloMeasurePointGroup.HEAT_DISTRIB),
	P11(326,66,TMeasurePoint.DEF_MOBJ_P11,EloMeasurePointType.TEMP,"°C",EloMeasurePointGroup.HEAT_SOURCE),
	P12(326,290,TMeasurePoint.DEF_MOBJ_P12,EloMeasurePointType.TEMP,"°C",EloMeasurePointGroup.HEAT_SOURCE),
	;

	// --------------------------------------------------------------------
	// DEFINITION OF VARIABLES USED IN ENUMERATION
	// --------------------------------------------------------------------

	private int xm;
	private int ym;
	private TMeasurePoint definition;
	private EloMeasurePointType typeMeasure;
	private String unity;
	private EloMeasurePointGroup group; 

	// -------------------------------------------------------
	// 					CONSTRUCTOR
	// -------------------------------------------------------
	EloMeasurePoint(int vxm, int vym,TMeasurePoint vdefinition,EloMeasurePointType vtypeMeasure, String vunity, EloMeasurePointGroup vgroup){
		this.xm = vxm;
		this.ym = vym;
		this.definition = vdefinition;
		this.typeMeasure = vtypeMeasure;
		this.unity =vunity;
		this.group = vgroup;
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

	public String getDefinition(TLanguage langage) {
		return definition.getLangue(langage);
	}

	public void setUnity(String unity) {
		this.unity = unity;
	}

	public String getUnity() {
		return unity;
	}

	public EloMeasurePointGroup getGroup() {
		return group;
	}

	public EloMeasurePointType getTypeMeasure() {
		return typeMeasure;
	}


}
