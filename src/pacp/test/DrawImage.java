package pacp.test;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DrawImage {

	private BufferedImage image;
	private JPanel imagePanel;
	private Point xymouse = new Point();
	private Point offset = new Point();
	private Point dragStart = new Point();

	private Point2D.Double OrigineH = new Point2D.Double(85,570); // (h0,h1)
	
	private boolean setOrigineH0=false;
	private boolean setOrigineH1=false;
	
	double zoom = 1;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(DrawImage::new);
	}

	public DrawImage() {

		try {
			File f = new File("D:/Users/kluges1/workspace/pac-tool/src/pacp/images/diagrammes enthalpie/R22.png");
			image = ImageIO.read(f);
		}
		catch (IOException ioe) {
			System.out.println("Image non trouvée !");
		}

		imagePanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g.create();

				AffineTransform t = new AffineTransform();
				// First Zoom, then translate to be sure to not pollute the offset with zoom operation 
				t.scale(zoom, zoom);
				t.translate(offset.x, offset.y);
				g2d.drawImage(image, t, null);
			    g2d.setColor(Color.red);
			    String s = String.format("H=%.2f",getH(xymouse.x));
			    g2d.drawString(s, xymouse.x, xymouse.y);
			}
		};
		imagePanel.setPreferredSize(new Dimension(640, 300));
		imagePanel.setBackground(Color.WHITE);

		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				imagePanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

				if ((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
					dragStart.x = evt.getX()-offset.x;
					dragStart.y = evt.getY()-offset.y;
				}

				if ( setOrigineH0) {
					OrigineH.x =  evt.getX()/zoom - offset.x;
					System.out.println("  x="+evt.getX() +	"  offset.x="+offset.x + "  Zoom="+zoom +  "  ph0(140)=" +OrigineH.x + "  pt_h1(240)=" + OrigineH.y);
					setOrigineH0 = false;
				}

				if ( setOrigineH1) {
					OrigineH.y = evt.getX()/zoom - offset.x;
					System.out.println("  x="+evt.getX() +	"  offset.x="+offset.x +"  Zoom="+zoom + "  ph0(140)=" +OrigineH.x + "  pt_h1(240)=" + OrigineH.y);
					setOrigineH1 = false;
				}
			}

			@Override
			public void mouseDragged(MouseEvent evt) {
				if ((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
					offset.x = (evt.getX() - dragStart.x);
					offset.y = (evt.getY() - dragStart.y);
					imagePanel.repaint();
				}
			}

			@Override
			public void mouseWheelMoved(MouseWheelEvent evt) {
				zoom -= evt.getPreciseWheelRotation() * .03;
				if (zoom < 0) zoom = 0;
				imagePanel.repaint();
			}
			
			@Override
			public void mouseMoved(MouseEvent evt) {
				xymouse.x= evt.getX();
				xymouse.y= evt.getY();				
			    imagePanel.repaint();			    
			}		
		};

		imagePanel.setFocusable(true);
		imagePanel.requestFocusInWindow();
		imagePanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ( e.isShiftDown()) {
					imagePanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
					setOrigineH0 = true;
					System.out.println("Click on abscissa 140");					
				}
				if ( e.isControlDown()) {
					imagePanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
					setOrigineH1 = true;
					System.out.println("Click on abscissa 240"); 					
				}	
			}
		});
		
		imagePanel.addMouseListener(ma);
		imagePanel.addMouseMotionListener(ma);
		imagePanel.addMouseWheelListener(ma);

		JFrame frame = new JFrame("CKL");
		frame.setContentPane(imagePanel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public double getH(int x) {
		//XH = 140 + (240-140) * (X/Zoom - offset(x) -xOrigineH) /  (xFinalH-xOrigineH)
		double xh = 140.0 + (240.0-140.0) * ((double)x/zoom - offset.x - OrigineH.x)/(double)(OrigineH.y-OrigineH.x) ;
		//System.out.println(	"  x="+x +	"  offset.x="+offset.x +"  xOrigineH=" + OrigineH.x +	"  xFinalH="+OrigineH.y +"  Zoom="+zoom+"  x/zoom="+ (double)x/zoom );
	    return xh;
	}


}
