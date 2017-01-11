package pacp.test;

import java.applet.*; 
import java.awt.*; 

public class Animation extends Applet implements Runnable {  

	private static final long serialVersionUID = 1L;
	Thread runner = null;         //variables de classe 
	double t; 


	public void init() { 
		setBackground (Color.lightGray);
	} 

	public void paint(Graphics g) {
		int X=140+(int)(120*Math.sin(t));   //calcul de la position 
		t+=0.05;              				//d�placement lors de l'appel suivant 
		g.fillOval(X,20,50,100);
	} 

	public void start() {      //surcharge de la m�thode 
		if (runner == null)  {   //test d'existence 
			runner = new Thread(this);   //cr�ation , this d�signe l'applet 
			runner.start(); // lancement de la m�thode run( )
		}
	}    

	public void stop()  	{ 
		if (runner != null) 	{ 
			runner.interrupt(); 
			runner = null;
		}
	}


	public void run() 	{ 
		
		while(!Thread.currentThread().isInterrupted()){
			try{       
				repaint();
				Thread.sleep(10);
			}
			catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}
		}
		

	}    
}