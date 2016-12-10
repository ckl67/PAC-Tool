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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelEnthalpie extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;	
	private ConfEnthalpy confEnthalpy;
	private BufferedImage image;
	private Point xymouse = new Point();
	private Point offset = new Point();
	private Point dragStart = new Point();
	private double zoom = 1;

	/**
	 * Constructor for GEnthalpie class
	 */
	public PanelEnthalpie(ConfEnthalpy vconfEnthalpy, MouseAdapter ma) {
		confEnthalpy = vconfEnthalpy;
		openEnthalpyImageFile();
		setBackground(Color.WHITE);

		addMouseWheelListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseMotionListener(ma);
	}

	@Override
	public void paintComponent(Graphics g)	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		AffineTransform t = new AffineTransform();
		// First Zoom, then translate to be sure to not pollute the offset with zoom operation 
		t.scale(zoom, zoom);
		t.translate(offset.x, offset.y);
		g2d.drawImage(image, t, null);
	}

	// ========================================================================================
	//                            SETTER & GETTER & VISIBLE FUNCTIONS
	// ========================================================================================
	// EnthalpyImageFile
	public void openEnthalpyImageFile() {
		try {
			File file = new File(confEnthalpy.getEnthalpyImageFile());
			image = ImageIO.read(file);	
		} catch (IOException e) {
			System.out.println("Image non trouvée !");
			e.printStackTrace(); 
		}
	}
		
	// Clean
	public void Clean() {
		repaint();
	}

	// Compute Entalpy H / mouse coordinate
	public double getH(int x) {
		//  H = iHOrigine  + (iHFinal-iHOrigine ) * (X/zoom-offset.x -mHOrigine)  / (mHFinal-mHOrigine)
		double xh = confEnthalpy.getiHOrigine() + (confEnthalpy.getiHFinal()-confEnthalpy.getiHOrigine()) * ((double)x/zoom - offset.x - confEnthalpy.getmHOrigine())/(double)(confEnthalpy.getmHFinal()-confEnthalpy.getmHOrigine()) ;
		return xh;
	}

	// Compute Pression P / mouse coordinate
	public double getP(int y) {
		// yP = mPOrigine  - y/zoom
		// yP = Log(iPOrigine)  + (Log(iPFinal)-Log(iPOrigine) ) * (Y/zoom-offset.y -mPOrigine)  / (mPFinal-mPOrigine)
		// yP = exp(yP*ln(10)

		double yP;
		yP = confEnthalpy.getmPOrigine()  - y/zoom;
		yP = Math.log10(confEnthalpy.getiPOrigine())  + (Math.log10(confEnthalpy.getiPFinal())-Math.log10(confEnthalpy.getiPOrigine()) ) * (y/zoom-offset.y -confEnthalpy.getmPOrigine())  / (confEnthalpy.getmPFinal()-confEnthalpy.getmPOrigine());
		yP = Math.exp(yP*Math.log(10));			
		return yP;
	}
	// ===============================================================================================================
	// 													EVENT LISTNER
	// ===============================================================================================================
	@Override
	public void mousePressed(MouseEvent evt) {
		int xMouse = evt.getX();
		int yMouse = evt.getY();
		
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		if ((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
			dragStart.x = xMouse-offset.x;
			dragStart.y = yMouse-offset.y;
		}

		if ( confEnthalpy.islocateOrigineH()) {
			confEnthalpy.setmHOrigine(xMouse/zoom - offset.x);
			confEnthalpy.setlocateOrigineH(false);
		}

		if ( confEnthalpy.islocateFinalH()) {
			confEnthalpy.setmHFinal(xMouse/zoom - offset.x);
			confEnthalpy.setlocateFinalH(false);
		}
		
		if ( confEnthalpy.islocateOrigineP()) {
			confEnthalpy.setmPOrigine(yMouse/zoom - offset.y);
			confEnthalpy.setlocateOrigineP(false);
		}

		if ( confEnthalpy.islocateFinalP()) {
			confEnthalpy.setmPFinal(yMouse/zoom - offset.y);
			confEnthalpy.setlocateFinalP(false);
		}
		
		
		Graphics g = getGraphics();
		Graphics2D g2 = (Graphics2D)g;
		
		//Point
		g2.setColor(Color.RED);
		g2.fillOval( xMouse-5, yMouse-5, 10, 10 );
		g2.setColor(Color.BLACK);
		g2.drawOval( xMouse-5, yMouse-5, 10, 10 );
		g2.setColor(Color.RED);
		g2.fillOval( xMouse-5, yMouse-5, 10, 10 );
		g2.setColor(Color.BLACK);
		g2.drawOval( xMouse-5, yMouse-5, 10, 10 );	
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		if ((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
			offset.x = (evt.getX() - dragStart.x);
			offset.y = (evt.getY() - dragStart.y);
			this.repaint();
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent evt) {
		zoom -= evt.getPreciseWheelRotation() * .03;
		if (zoom < 0) zoom = 0;
		this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		if ( confEnthalpy.islocateOrigineH()) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
		if ( confEnthalpy.islocateFinalH()) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));			
		}
		if ( confEnthalpy.islocateOrigineP()) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
		if ( confEnthalpy.islocateFinalP()) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));			
		}
		xymouse.x= evt.getX();
		xymouse.y= evt.getY();					    
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
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	
	}

};


