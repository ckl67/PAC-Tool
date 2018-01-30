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
package gui.helpaboutdef;

import java.awt.EventQueue;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.UIManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JScrollPane;

public class WinDefinition extends JFrame  {

	private static final long serialVersionUID = 1L;
	//private static final Logger logger = LogManager.getLogger(WinDefinition.class.getName());
	private static final Logger logger = LogManager.getLogger(new Throwable().getStackTrace()[0].getClassName());

	// -------------------------------------------------------
	// 					INSTANCE VARIABLES
	// -------------------------------------------------------
	private String htmlcontent;
	// -------------------------------------------------------
	// 				TEST THE APPLICATION STANDALONE 
	// -------------------------------------------------------
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					WinDefinition window1 = new WinDefinition("Définition","/gui/helpaboutdef/Definitions.html");
					window1.setVisible(true);

					WinDefinition window2 = new WinDefinition("Abréviation","/gui/helpaboutdef/Abreviation.html");
					window2.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// -------------------------------------------------------
	// 						CONSTRUCTOR
	// -------------------------------------------------------

	/**
	 * Create the application.
	 */
	public WinDefinition(String vtitle, String vfile) {
		StringBuilder contentBuilder = new StringBuilder();

		try {
			
			// Reading resource
			// A resource in a jar file is not a File, so you can't treat it as one.
			// Use getResourceAsStream and use InputStreamReader instead of FileReader. 

			InputStream i = WinDefinition.class.getResourceAsStream(vfile);
			BufferedReader in = new BufferedReader(new InputStreamReader(i));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
			logger.error("Ops! (WinDefinition)", e);
		}
		htmlcontent = contentBuilder.toString();
		
		initialize(vtitle);
	}

	// -------------------------------------------------------
	// 							METHOD
	// -------------------------------------------------------
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String vtitle) {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			logger.info(e);
		}

		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setIconImage(Toolkit.getDefaultToolkit().getImage(WinDefinition.class.getResource("/gui/images/PAC-Tool_16.png")));
		setTitle(vtitle);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JEditorPane txtdef = new JEditorPane();
		txtdef.setEditable(false);
		txtdef.setContentType("text/html");
		txtdef.setText(htmlcontent);
		scrollPane.setViewportView(txtdef);
		
	}

}
