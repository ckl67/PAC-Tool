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

import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.GridLayout;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;

public class WinEnthalpy {

	private ConfEnthalpy confEnthalpy;
	private JFrame frame;
	private PanelEnthalpie panelEnthalpyDrawArea;
	
	private JLabel lblMouseCoordinate;
	private JLabel lblEnthalpyCoord;
	private JLabel lblPressionCoord;
	
		
	/**
	 * Create the application.
	 */
	public WinEnthalpy(ConfEnthalpy vconfEnthalpy) {
		confEnthalpy = vconfEnthalpy;
		initialize();
	}

	
	public void WinEnthalpieVisible() {
		frame.setVisible(true);
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WinEnthalpy.class.getResource("/pacp/images/PAC-Tool_32.png")));
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelEnthalpyBottom = new JPanel();
		frame.getContentPane().add(panelEnthalpyBottom, BorderLayout.SOUTH);

		lblMouseCoordinate = new JLabel("Mouse Coordinate");
		lblMouseCoordinate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMouseCoordinate.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent m) {
				String s = "x: %d   y: %d";
				lblMouseCoordinate.setText(String.format("("+s+")", m.getX(), m.getY()));
				
				double result = panelEnthalpyDrawArea.getH(m.getX());
				lblEnthalpyCoord.setText(String.format("H=%.2f kJ/kg",result));
		    
				result = panelEnthalpyDrawArea.getP(m.getY());
				lblPressionCoord.setText(String.format("P=%.2f bar",result));				
			}
		};
		panelEnthalpyBottom.setLayout(new GridLayout(0, 3, 0, 0));
		
		lblEnthalpyCoord = new JLabel("Enthalpy Coordinate");
		lblEnthalpyCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnthalpyCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.add(lblEnthalpyCoord);
		
		lblPressionCoord = new JLabel("Pression Coordinate");
		lblPressionCoord.setHorizontalAlignment(SwingConstants.CENTER);
		lblPressionCoord.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelEnthalpyBottom.add(lblPressionCoord);

		panelEnthalpyBottom.add(lblMouseCoordinate);

		JPanel panelEnthalpyRight = new JPanel();
		panelEnthalpyRight.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		frame.getContentPane().add(panelEnthalpyRight, BorderLayout.EAST);
		panelEnthalpyRight.setLayout(new GridLayout(0, 1, 0, 0));
		
		panelEnthalpyDrawArea = new PanelEnthalpie(confEnthalpy, ma);	
		panelEnthalpyDrawArea.setBackground(Color.WHITE);
		panelEnthalpyDrawArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		panelEnthalpyDrawArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		frame.getContentPane().add(panelEnthalpyDrawArea, BorderLayout.CENTER);
		
		JPanel panelHight = new JPanel();
		panelEnthalpyRight.add(panelHight);
		panelHight.setLayout(new BoxLayout(panelHight, BoxLayout.Y_AXIS));
		
		JButton btnH0 = new JButton("H0");
		btnH0.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnH0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		panelHight.add(btnH0);
		
		JButton H1 = new JButton("H1");
		H1.setAlignmentX(Component.CENTER_ALIGNMENT);
		H1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		panelHight.add(H1);
		
		JPanel panelBottom = new JPanel();
		panelEnthalpyRight.add(panelBottom);
		panelBottom.setLayout(new BorderLayout(0, 0));
		
		JButton btnClear = new JButton("Effacer");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelEnthalpyDrawArea.Clean();
			}
		});
		panelBottom.add(btnClear, BorderLayout.SOUTH);




	}

}
