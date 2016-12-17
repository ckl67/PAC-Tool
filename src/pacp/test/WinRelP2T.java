package pacp.test;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.border.MatteBorder;

public class WinRelP2T {

	private JFrame frame;
	private JLabel lblTemperature;
	private JLabel lblPression;
	private PDisplay panelDisplay;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinRelP2T window = new WinRelP2T();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public WinRelP2T() throws IOException {
		initialize();
		panelDisplay.loadPressionTemperatureFile();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelValue = new JPanel();
		frame.getContentPane().add(panelValue, BorderLayout.SOUTH);
		panelValue.setLayout(new GridLayout(0, 2, 0, 0));

		lblPression = new JLabel("P");
		lblPression.setBorder(new MatteBorder(2, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		lblPression.setHorizontalAlignment(SwingConstants.CENTER);
		panelValue.add(lblPression);

		lblTemperature = new JLabel("T");
		lblTemperature.setBorder(new MatteBorder(2, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		lblTemperature.setHorizontalAlignment(SwingConstants.CENTER);
		panelValue.add(lblTemperature);

		panelDisplay = new PDisplay();
		frame.getContentPane().add(panelDisplay, BorderLayout.CENTER);
	}

	// ===================================================================================================================
	//												JPANEL Display
	// ===================================================================================================================

	private class PDisplay extends JPanel implements MouseListener, MouseMotionListener {
		private static final long serialVersionUID = 1L;
		private double zoomx,zoomy;

		// Temperature
		private double xmin = 0;  	// Minimum of the range of values displayed.
		private double xmax = 0;     // Maximum of the range of value displayed.

		// Pression 
		private double ymin = 0;  		// Minimum of the range of values displayed.
		private double ymax = 0;     	// Maximum of the range of value displayed.

		// Supplementary margin on both sides of the display
		private double marginx = 10;
		private double marginy = 5;

		// Array
		List<Double> press = new ArrayList<Double>();
		List<Double> temp = new ArrayList<Double>();
		double correctionPression = 1.0;

		int gridUnitX = 5;
		int gridUnitY = 5;
		
		/**
		 * Constructor
		 */
		public PDisplay() {
			addMouseListener(this);
			addMouseMotionListener(this);			
		}

		// Pression Temperature Text file
		public void loadPressionTemperatureFile() throws IOException {
			String p2tfile = "D:/Users/kluges1/workspace/pac-tool/ressources/P2T_R22.txt";
			Pattern p = Pattern.compile("(-?\\d+(,\\d+)?)");
			BufferedReader buff_in;
			String line;
			int i;

			try {
				buff_in = new BufferedReader(new FileReader(p2tfile));
				while ((line = buff_in.readLine()) != null)
				{
					if (!line.startsWith("#") ) {
						Matcher m = p.matcher(line);
						i=0;
						while (m.find()) {
							if (i ==0) {
								//System.out.print ( "   p(" + i + ")=" +  m.group().replace(",", ".") );
								temp.add(Double.parseDouble(m.group().replace(",", ".")));
							} else  {
								//System.out.print ( "   p(" + i + ")=" + m.group().replace(",", ".") );
								press.add(Double.parseDouble(m.group().replace(",", "."))+correctionPression);
							}
							i++;
						}
					}
				}
				buff_in.close();

				// Max Min
				for(i=0;i<temp.size();i++) {
					if (temp.get(i) < xmin)
						xmin = temp.get(i); 
					if (temp.get(i) > xmax)
						xmax = temp.get(i);

					if (press.get(i) < ymin)
						ymin = press.get(i); 
					if (press.get(i) > ymax)
						ymax = press.get(i);
				}
				// Supplementary margin added to min/max
				 xmin = ((int)(xmin/gridUnitX)-2)*gridUnitX;
				 xmax = ((int)(xmax/gridUnitX)+2)*gridUnitX;
				 ymin = ((int)(ymin/gridUnitY)-2)*gridUnitY;
				 ymax = ((int)(ymax/gridUnitY)+2)*gridUnitY;

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * Paint
		 */
		public void paintComponent(Graphics g) {  
			Graphics2D g2 = (Graphics2D)g;
			AffineTransform saveTransform;

			super.paintComponent(g);
			setBackground(Color.WHITE);

			// Apply a translation so that the drawing coordinates on the display are referenced to curve
			zoomx=getWidth()/(xmax-xmin+2*marginx);
			zoomy= getHeight()/(ymax-ymin+2*marginy);
			g2.translate(getWidth()/2,getHeight()/2);
			saveTransform = g2.getTransform();
			g2.scale(zoomx, -zoomy);
			g2.translate(-(xmax+xmin)/2, -(ymax+ymin)/2);

			// -----------------------------------
			// Grid
			// -----------------------------------
			g2.setColor(Color.lightGray);
			g2.setStroke(new BasicStroke(0.05f));

			for (int x = (int) xmin; x <= xmax; x=x+gridUnitX) 
				g2.draw( new Line2D.Double(x,ymin,x,ymax));			

			for (int y = (int) ymin; y <= ymax; y=y+gridUnitY)			
				g2.draw( new Line2D.Double(xmin,y,xmax,y));


			// -----------------------------------
			// Axes
			// -----------------------------------
			g2.setColor(Color.blue);
			g2.setStroke(new BasicStroke(0.5f));

			g2.draw( new Line2D.Double(xmin,0,xmax,0));
			g2.draw( new Line2D.Double(0,ymin,0,ymax));	


			// -----------------------------------
			// Text  Abscissa /Ordinate
			// Be care orientation inverse for Y !!!
			// -----------------------------------
			g2.setTransform(saveTransform);
			g2.scale(zoomx, zoomy);
			g2.translate(-(xmax+xmin)/2, -(ymax+ymin)/2);

			// Text
			Font font = new Font(null, Font.BOLD, 3); 
			g2.setFont(font);
			g2.drawString("T", (int) (xmax+2), (int) (ymin+ymax));
			g2.drawString("P", (int) (0), (int) (ymin)-1);

			// Coordinate
			font = new Font(null, Font.PLAIN, 3); 
			FontMetrics metrics = g.getFontMetrics(font);		
			g2.setFont(font);

			String s;
			int xd;		
			for (int x = (int) xmin; x <= xmax; x=x+2*gridUnitX) {
				s = String.format("%d",x);
				xd = x - metrics.stringWidth(s)/ 2;  			    
				g2.drawString(s, xd, (int) ymax+4);
			}

			for (int y = (int) ymin; y <= ymax; y=y+2*gridUnitY) {
				s = String.format("%d",(int)y);
				g2.drawString(s, (int) (xmin-marginx/2), (int) (-y+ymin+ymax));			
			}

			g2.setTransform(saveTransform);
			g2.scale(zoomx, -zoomy);
			g2.translate(-(xmax+xmin)/2, -(ymax+ymin)/2);

			// -----------------------------------
			// Curve
			// -----------------------------------		
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(0.5f));
			for(int i=1;i<temp.size();i++) {
				g2.draw( new Line2D.Double(temp.get(i-1),press.get(i-1),temp.get(i),press.get(i)));			 
			}

		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent m) {
			String s;
			s = "T: %.2f°C";
			lblTemperature.setText(String.format(s, m.getX()/zoomx+xmin-marginx));
			s = "P: %.2fbar";
			lblPression.setText(String.format(s, -m.getY()/zoomy+marginy+ymax ));
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

}
