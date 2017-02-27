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
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class DataSynchroUpdateTimer {

	private static Timer timer;

	private WinEnthalpy winEnthalpy;
	
	/**
	 * Update action to perform
	 */
	private class ActionsToBePerformed implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			//winEnthalpy.updateAllTextField();
			winEnthalpy.getPanelEnthalpyDrawArea().repaint();
		}
	}

	/**
	 * The constructor creates a timer with a delay time of four seconds
	 * (n milliseconds), and with a ActionsToBePerformed object as its
	 * ActionListener. It also starts the timer running.
	 */
	public DataSynchroUpdateTimer( WinEnthalpy vwinEnthalpy ) {
		winEnthalpy = vwinEnthalpy;
		ActionsToBePerformed action = new ActionsToBePerformed();
		
		timer = new Timer(200, action);
		timer.start();
	}

	public static void DataSynchroUpdateTimerStop () {
		timer.stop();

	}
}
