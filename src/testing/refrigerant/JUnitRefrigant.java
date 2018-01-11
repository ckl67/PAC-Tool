package testing.refrigerant;

import org.json.simple.JSONObject;
import org.junit.Test;
import refrigerant.Refrigerant;

public class JUnitRefrigant {

	@Test
	public void test() {
		double T;
		double P;
		double H;

		Refrigerant refrigerant = new Refrigerant("./ressources/R407/R407C/Saturation Table R407C Dupont-Suva.txt");
		//Refrigerant refrigerant = new Refrigerant("./ressources/R22/Saturation Table R22.txt");
		System.out.println(refrigerant.getRfgName());

		System.out.println(refrigerant.getIsoTherm_H0_Ref());

		P = 2.0;
		T = -40.0;
		H = 150;

		// getPSatFromT(-40)
		System.out.println("getPSatFromT(T)");
		System.out.println(	"T= " + T + "°C " +  
				" --> P Saturation (Liquid)= " + refrigerant.getPSatFromT(T).getPLiquid() + "(bar)" +
				" --> P Saturation(gas)= "     + refrigerant.getPSatFromT(T).getPGas()    + "(bar)" 
				);

		//getTSatFromP(2)
		System.out.println("getTSatFromP(P)");
		System.out.println(	"P= " +P + "(bar) " +  
				" --> T Saturation (Liquid)= " + refrigerant.getTSatFromP(P).getTLiquid() + "(°C)" +
				" --> T Saturation(gas)= "     + refrigerant.getTSatFromP(P).getTGas()    + "(°C)" 
				);

		
		// getHSatFromT(-40)
		System.out.println("getHSatFromT(T)");
		System.out.println(	"T= " + T + "°C " +  
				" --> H Saturation (Liquid)= " + refrigerant.getHSatFromT(T).getHLiquid() + "(kJ/kg)" +
				" --> H Saturation(gas)= "     + refrigerant.getHSatFromT(T).getHGas()    + "(kJ/kg)" 
				);

		// getHSatFromP(2)
		System.out.println("getHSatFromP(P)");
		System.out.println(	"P= " +P + "(bar) " +  
				" --> H Saturation (Liquid)= " + refrigerant.getHSatFromP(P).getHLiquid() + "(kJ/kg)" +
				" --> H Saturation(gas)= "     + refrigerant.getHSatFromP(P).getHGas()    + "(kJ/kg)" 
				);

		// getTSatLiquidFromH()
		System.out.println("getTSatLiquidFromH(H)");
		H = 150;
		System.out.println(	"H= " + H + "(kJ/kg)" +  
				" --> T Saturation = " + refrigerant.getTSatLiquidFromH(H) + "°C"
				);

		
		H = 380;
		// Will return error !
		System.out.println(	"H= " + H + "(kJ/kg)" +  
				" --> T Saturation = " + refrigerant.getTSatLiquidFromH(H) + "°C"
				);
		
		System.out.println("\n\n ISOBAR");
		H = 100.0;
		for(int n=0;n<15;n++) {
			H = H +30.0;

			System.out.println(	"P = " +P + "(bar) " +
					"H= " +H + "(kJ/kg) " +
					" --> Isobar P =  " + refrigerant.getIsobaricP(P, H) + "(bar)" +
					" --> Isobar P State = "     + refrigerant.getIsobaricState(P, H) 	
					);
		}

		
		// getPIsotherm(double H, double T, double P)
		System.out.println("getPIsotherm( H,  T, P)");
		T = 30;
		P = 2;
		H = 430.0;
		for(int n=0;n<15;n++) {
			H = H +1.0;
					
			System.out.println(	"P/T = " +P + "/" + T +
					" H= " +H + "(kJ/kg) " +
					" --> Isotherm P =  " + refrigerant.getPIsotherm( H,T,P) + " = " + + refrigerant.getPIsotherm( H,T)	
					);
		}
	
		// getHGasInterIsobarIsotherm(PRef,T)
		System.out.println("getHGasInterIsobarIsotherm");
		System.out.println("P=2 T=30 --> HInter = " +  refrigerant.getHGasInterIsobarIsotherm(2.0, 30.0));
		
		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = refrigerant.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Modify the instance by loading new gas ");
		refrigerant.loadRfgGasSaturationData("./ressources/R407/R407C/Saturation Table R407C Dupont-Suva.txt");
		System.out.println("   Gaz Name ="+ refrigerant.getRfgName());
	
		System.out.println("\n---> Read JSON data");
		System.out.println("---> Set the Class Instance with JSON data");
		refrigerant.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n");

		
	}

}
