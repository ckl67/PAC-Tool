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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelEnthalpie extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {

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
	public PanelEnthalpie(int width, int height, String digenth, MouseMotionListener ml) {
		setPreferredSize(new Dimension(width, height));
		try {
			img = ImageIO.read(PanelEnthalpie.class.getResource(digenth));	
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		setBackground(Color.WHITE);

		addMouseWheelListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseMotionListener(ml);
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
		//float currentImgWidth = img.getMinX()*z;
		//float currentImgHeight = img.getHeight()*z;
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		AffineTransform t = new AffineTransform();
		//t.translate(width/2-currentImgWidth/2, height/2-currentImgHeight/2);
		t.translate(xMouse-200, yMouse-200);
		t.scale(z, z);

		g2.drawImage(img, t, null);

		// g.dispose() will release any operating system resources that might be held by g.
		// dispose() is intended for closing the frame.
		//g2.dispose();

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
		int xMouse = evt.getX();
		int yMouse = evt.getY();

		Graphics g = getGraphics();
		//The system will call the component’s paintComponent() method later, as soon as it gets a chance to do so
		Graphics2D g2 = (Graphics2D)g;

		//Point
		g2.setColor(Color.RED);
		g2.fillOval( xMouse, yMouse, 10, 10 );
		g2.setColor(Color.BLACK);
		g2.drawOval( xMouse, yMouse, 10, 10 );


		//Point
		g2.setColor(Color.RED);
		g2.fillOval( xMouse, yMouse, 10, 10 );
		g2.setColor(Color.BLACK);
		g2.drawOval( xMouse, yMouse, 10, 10 );


	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseDragged(MouseEvent evt) {
		if ((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
			xMouse = evt.getX();
			yMouse = evt.getY();
			repaint();
		}
	}


	@Override
	public void mouseMoved(MouseEvent evt) {

		int x = evt.getX();
		int y = evt.getY();
		
		//Component source=(Component)evt.getSource();
		//source.getParent()

		// TODO Auto-generated method stub
		//graphicsForDrawing = getGraphics();
		//repaint();

	//	String s = x + ", " + y;
		//System.out.println(s);
		//graphicsForDrawing.setColor(Color.red);
		//graphicsForDrawing.drawString(s, x, y);

	}



}
