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

		//Refrigerant refrigerant = new Refrigerant("./ressources/R407/R407C/Saturation Table R407C Dupont-Suva.txt");
		//Refrigerant refrigerant = new Refrigerant("./ressources/R22/Saturation Table R22.txt");
		Refrigerant refrigerant = new Refrigerant("./ressources/R22/Saturation Table R22.txt");
		System.out.println(refrigerant.getRfgName());

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

		P = 2.0;
		T = -40.0;

		//T=80;
		System.out.println(	"T= " + T + "°C " +  
				" --> P Saturation (Liquid)= " + refrigerant.getPSatFromT(T).getPLiquid() + "(bar)" +
				" --> P Saturation(gas)= "     + refrigerant.getPSatFromT(T).getPGas()    + "(bar)" 
				);


		//P=39.808;
		System.out.println(	"P= " +P + "(bar) " +  
				" --> T Saturation (Liquid)= " + refrigerant.getTSatFromP(P).getTLiquid() + "(°C)" +
				" --> T Saturation(gas)= "     + refrigerant.getTSatFromP(P).getTGas()    + "(°C)" 
				);

		System.out.println(	"T= " + T + "°C " +  
				" --> H Saturation (Liquid)= " + refrigerant.getHSatFromT(T).getHLiquid() + "(kJ/kg)" +
				" --> H Saturation(gas)= "     + refrigerant.getHSatFromT(T).getHGas()    + "(kJ/kg)" 
				);


		System.out.println(	"P= " +P + "(bar) " +  
				" --> H Saturation (Liquid)= " + refrigerant.getHSatFromP(P).getHLiquid() + "(kJ/kg)" +
				" --> H Saturation(gas)= "     + refrigerant.getHSatFromP(P).getHGas()    + "(kJ/kg)" 
				);

		System.out.println("\n\n ISOBAR");
		H = 100.0;
		for(int n=0;n<15;n++) {
			H = H +30.0;

			System.out.println(	"P = " +P + "(bar) " +
					"H= " +H + "(kJ/kg) " +
					" --> Isobar P =  " + refrigerant.getIsobaricP(P, H) + "(bar)" +
					" --> Isobar T =  " + refrigerant.getIsobaricT(P, H) + "(°C)" +
					" --> Isobar P State = "     + refrigerant.getIsobaricState(P, H) 	
					);
		}

		System.out.println("\n\n ISOTHERM");
		H = 100.0;
		for(int n=0;n<15;n++) {
			H = H +30.0;
			System.out.println(	"T = " +T + "(°C) " +
					"H= " +H + "(kJ/kg) " +
					" --> IsoTherme P =  " + refrigerant.getIsoThermalP(T, H, 1.0, -0.001) + "(bar)" +
					" --> IsoTherme  State = "     + refrigerant.getIsoThermalState(T, H) 	
					);
		}

	}

}
