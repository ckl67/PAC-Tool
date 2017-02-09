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

public class PanelEnthRepaintAction {

	/**
	 * A RepaintAction object calls the repaint method of panel each
	 * time its PanelEnthRepaintAction() method is called. An object of this
	 * type is used as an action listener for a Timer that generates an
	 * ActionEvent every n seconds. The result is that the panel is
	 * redrawn every n seconds.
	 */
	private class RepaintAction implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			WinEnthalpy.panelEnthalpyDrawArea.repaint(); 
		}
	}

	/**
	 * The constructor creates a timer with a delay time of four seconds
	 * (n milliseconds), and with a RepaintAction object as its
	 * ActionListener. It also starts the timer running.
	 */
	public PanelEnthRepaintAction() {
		RepaintAction action = new RepaintAction();
		Timer timer = new Timer(200, action);
		timer.start();
	}

}
