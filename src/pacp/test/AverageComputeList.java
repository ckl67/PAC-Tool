package pacp.test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class AverageComputeList {

	private List<Point2D.Double> listTempPress;

	public List<Point2D.Double> getListTempPress() {
		return listTempPress;
	}


	public void setListTempPress(List<Point2D.Double> listTempPress) {
		this.listTempPress = listTempPress;
	}


	public AverageComputeList() {

		setListTempPress(new ArrayList<Point2D.Double>());
		listTempPress.add( new Point2D.Double(-26, 0.93));
		listTempPress.add( new Point2D.Double(-24, 1.09));
		listTempPress.add( new Point2D.Double(-22, 1.26));
		listTempPress.add( new Point2D.Double(-20, 1.45));
		listTempPress.add( new Point2D.Double(-18, 1.64));
		listTempPress.add( new Point2D.Double(-16, 1.85));
		listTempPress.add( new Point2D.Double(-14, 2.07));
		listTempPress.add( new Point2D.Double(-12, 2.31));
		listTempPress.add( new Point2D.Double(-10, 2.54));
		listTempPress.add( new Point2D.Double(-8, 2.80));
		listTempPress.add( new Point2D.Double(-6, 3.07));
		listTempPress.add( new Point2D.Double(-4, 3.36));
		listTempPress.add( new Point2D.Double(-2, 3.66));
		listTempPress.add( new Point2D.Double(26, 9.72));
		listTempPress.add( new Point2D.Double(28, 10.31));
		listTempPress.add( new Point2D.Double(0, 3.97));
		listTempPress.add( new Point2D.Double(2, 4.31));
		listTempPress.add( new Point2D.Double(4, 4.66));
		listTempPress.add( new Point2D.Double(6, 5.02));
		listTempPress.add( new Point2D.Double(8, 5.40));
		listTempPress.add( new Point2D.Double(10, 5.80));
		listTempPress.add( new Point2D.Double(12, 6.22));
		listTempPress.add( new Point2D.Double(14, 6.66));
		listTempPress.add( new Point2D.Double(16, 7.12));
		listTempPress.add( new Point2D.Double(18, 7.60));
		listTempPress.add( new Point2D.Double(20, 8.10));
		listTempPress.add( new Point2D.Double(22, 8.62));
		listTempPress.add( new Point2D.Double(24, 9.16));
		listTempPress.add( new Point2D.Double(26, 9.72));
	}


	public static void main(String[] args) {  
		AverageComputeList vlist = new AverageComputeList();

		/**
		 * 
		vlist.getPressFromTemp(-50);
		vlist.getPressFromTemp(-26);
		vlist.getPressFromTemp(-23);
		vlist.getPressFromTemp(0);
		vlist.getPressFromTemp(2);
		vlist.getPressFromTemp(19);
		vlist.getPressFromTemp(25);
		vlist.getPressFromTemp(26);
		vlist.getPressFromTemp(50);
		 */
		
		vlist.getTempFromPress(4);
		vlist.getTempFromPress(82);

	}

	public double getTempFromPress(double press){
		System.out.println("press= "+ press );
		double x,tempo;
		int idx=0;
		for(int c = 0; c < listTempPress.size(); c++){
			if((press) >= listTempPress.get(c).getY() ){
				idx = c;
			}
		}
		if (idx == listTempPress.size()-1) {
			idx = idx-1;
		} 

		double y0,y1,x0,x1;
		x  = press;
		x0 = listTempPress.get(idx).getY();
		x1 = listTempPress.get(idx+1).getY();
		y0 = listTempPress.get(idx).getX();
		y1 = listTempPress.get(idx+1).getX();
		tempo = (x-x0)*(y1-y0)/(x1-x0)+ y0;
		System.out.println("press= "+ press + "    temp=" + tempo );


		return tempo;
	}


	public double getPressFromTemp(double temp){
		double x,presso;
		int idx=0;
		for(int c = 0; c < listTempPress.size(); c++){

			if(temp >= listTempPress.get(c).getX() ){
				//System.out.println("temp=" + temp + "  c=" + c + "  -->" + listTempPress.get(c).getX() );
				idx = c;
			}
		}
		if (idx == listTempPress.size()-1) {
			//System.out.print("     idx = Limit --> " + idx);
			idx = idx-1;
			//System.out.println(" (-1) ---> idx " + idx);
		} 

		double y0,y1,x0,x1;
		x  = temp;
		x0 = listTempPress.get(idx).getX();
		x1 = listTempPress.get(idx+1).getX();
		y0 = listTempPress.get(idx).getY();
		y1 = listTempPress.get(idx+1).getY();
		presso = (x-x0)*(y1-y0)/(x1-x0)+ y0;

		//System.out.println("    temp=" + temp + "  (idx=" + idx + ")  -->  " + listTempPress.get(idx).getX() + " <= " + temp + " < " +listTempPress.get(idx+1).getX() );
		String s;
		s = String.format("temp = %6.2f",temp);
		System.out.println(s);
		s = String.format("   x0=%6.2f  y0=%6.2f",x0,y0);
		System.out.println(s);
		s = String.format("   x1=%6.2f  y1=%6.2f",x1,y1);
		System.out.println(s);
		s = String.format("   --> P(%.2f) = %6.2f",temp,presso);
		System.out.println(s);

		return presso;
	}

}
