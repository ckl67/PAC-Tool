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

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GEnthalpie extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {

	/**
	 * This main routine allows this class to be run as a program.
	 */
	public static void main(String[] args)
	{
		JFrame window = new JFrame();
		GEnthalpie content = new GEnthalpie(640,480, "/pacp/images/diagrammes enthalpie/R22.png");	
		window.setLocation(80,10);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(content);
		window.pack();
		
		window.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		 
		window.setVisible(true);		
	}

	/**
	 * Some constants & variables
	 */
	private static final long serialVersionUID = 1L;
	private float zoom = 1f;
	private int xMouse = 0;
	private int yMouse = 0;
	private BufferedImage img;
	private Graphics graphicsForDrawing; 

	/**
	 * Constructor for GEnthalpie class sets the background + sets it to listen for mouse events on itself.
	 */
	public GEnthalpie(int width, int height, String digenth) {
		setPreferredSize(new Dimension(width, height));
		try {
			img = ImageIO.read(GEnthalpie.class.getResource(digenth));	
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		setBackground(Color.WHITE);

		addMouseWheelListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}


	/**
	 * Draw the contents of the panel.  Since no information issaved about what the user has drawn, the user's drawing
	 * is erased + background image whenever this routine is called.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		int width = getWidth();
		int height = getHeight();
		float z = zoom*zoom;
		float currentImgWidth = img.getWidth()*z;
		float currentImgHeight = img.getHeight()*z;

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
       
        
		AffineTransform t = new AffineTransform();
		t.translate(width/2-currentImgWidth/2, height/2-currentImgHeight/2);
		t.scale(z, z);

		g2.drawImage(img, t, null);


        g2.setColor(Color.GRAY);
        g2.drawRect(0, height-50, width-1, 50);
        g2.setColor(Color.BLUE);
        g2.fillRect(1,height-49, width-2, 49);
        
		// g.dispose() will release any operating system resources that might be held by g.
		g2.dispose();

	}

	// ===============================================================================================================
	// 													EVENT LISTNER
	// ===============================================================================================================
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoom = Math.max(0, zoom - 0.03f * e.getWheelRotation());
		//The system will call the component’s paintComponent() method later, as soon as it gets a chance to do so
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent evt) {
		if ( evt.isShiftDown() ) {
			// The user was holding down the Shift key. Just repaint the panel.
			repaint();
			return;
		}
		xMouse = evt.getX();
		yMouse = evt.getY();

		Graphics g = getGraphics();
		//The system will call the component’s paintComponent() method later, as soon as it gets a chance to do so
		Graphics2D g2 = (Graphics2D)g;

		//Point
		g2.setColor(Color.RED);
		g2.fillOval( xMouse, yMouse, 10, 10 );
		g2.setColor(Color.BLACK);
		g2.drawOval( xMouse, yMouse, 10, 10 );

		g.dispose();
		//Point
		g2.setColor(Color.RED);
		g2.fillOval( xMouse, yMouse, 10, 10 );
		g2.setColor(Color.BLACK);
		g2.drawOval( xMouse, yMouse, 10, 10 );

		g.dispose();

	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseDragged(MouseEvent evt) {

	}


	@Override
	public void mouseMoved(MouseEvent evt) {
		// TODO Auto-generated method stub
		graphicsForDrawing = getGraphics();
		repaint();

		int x = evt.getX();
		int y = evt.getY();

		String s = x + ", " + y;
		graphicsForDrawing.setColor(Color.red);
		graphicsForDrawing.drawString(s, x, y);


	}

}
