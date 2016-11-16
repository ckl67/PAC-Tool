package pacp.test;

import java.util.ArrayList;
import java.util.List;

import pacp.Pac;
import pacp.Scroll;

public class ListPac {
	
	private static int MAXPacList = 10; 

	
	public static void main(String[] args){
		// Method 1
		Pac pac1 = new Pac();
		System.out.println(pac1.getScroll().getCurrent());

		// Method 2
		// first create array of elements that holds object addresses, then create objects
		Pac[] pactool = new Pac[] { new Pac(), new Pac()};
		System.out.println(pactool[0].getScroll().getCapacity());
		System.out.println(pactool[1].getScroll().getCapacity());

		// Method 3
		// first create array of elements that holds object addresses, then create objects
		Pac[] pac_ar = new Pac[MAXPacList];
		List<Pac> pacl = new ArrayList<Pac>();

		Scroll scroll;
		
		scroll = pac_ar[1].getScroll(); 
		scroll.setCurrent(0000);

		scroll = pac_ar[1].getScroll(); 
		scroll.setCurrent(1000);

		scroll = pac_ar[2].getScroll(); 
		scroll.setCurrent(2000);

		pacl.add(pac_ar[0]);
		pacl.add(pac_ar[1]);
		pacl.add(pac_ar[2]);

		for(int i = 0; i < pacl.size(); i++)
		{
			System.out.println("donnée à l'indice " + i + " = " + pacl.get(i).getScroll().getCurrent());
		}    

		pacl.remove(1);
		pacl.remove(0);

		for(int i = 0; i < pacl.size(); i++)
		{
			System.out.println("donnée après removel'indice " + i + " = " + pacl.get(i).getScroll().getCurrent());
		}    

	}
}
