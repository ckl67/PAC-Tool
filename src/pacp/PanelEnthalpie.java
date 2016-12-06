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
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelEnthalpie extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;

	private BufferedImage image;

	private Point xymouse = new Point();
	private Point offset = new Point();
	private Point dragStart = new Point();

	private Point2D.Double OrigineH = new Point2D.Double(85,570); // (h0,h1)

	private boolean setOrigineH0=false;
	private boolean setOrigineH1=false;

	double zoom = 1;

	/**
	 * Constructor for GEnthalpie class
	 */
	public PanelEnthalpie(String dirImgEnth, MouseAdapter ma) {

		try {
			image = ImageIO.read(PanelEnthalpie.class.getResource(dirImgEnth));	
		} catch (IOException e) {
			System.out.println("Image non trouvée !");
			e.printStackTrace(); 
		}
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

	public void SetOrigineH() {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));		
		setOrigineH0 = true;
	}

	public void SetFinalH() {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));		
		setOrigineH1 = true;
	}

	public void Clean() {
		repaint();
	}

	public double getH(int x) {
		//XH = 140 + (240-140) * (X/Zoom - offset(x) -xOrigineH) /  (xFinalH-xOrigineH)
		double xh = 140.0 + (240.0-140.0) * ((double)x/zoom - offset.x - OrigineH.x)/(double)(OrigineH.y-OrigineH.x) ;
		//System.out.println(	"  x="+x +	"  offset.x="+offset.x +"  xOrigineH=" + OrigineH.x +	"  xFinalH="+OrigineH.y +"  Zoom="+zoom+"  x/zoom="+ (double)x/zoom );
		return xh;
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

		if ( setOrigineH0) {
			OrigineH.x =  xMouse/zoom - offset.x;
			//System.out.println("  x="+xMouse +	"  offset.x="+offset.x + "  Zoom="+zoom +  "  ph0(140)=" +OrigineH.x + "  pt_h1(240)=" + OrigineH.y);
			setOrigineH0 = false;
		}

		if ( setOrigineH1) {
			OrigineH.y = xMouse/zoom - offset.x;
			//System.out.println("  x="+xMouse +	"  offset.x="+offset.x +"  Zoom="+zoom + "  ph0(140)=" +OrigineH.x + "  pt_h1(240)=" + OrigineH.y);
			setOrigineH1 = false;
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


