package testing.measurePoint;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.junit.Test;

import mpoints.EloMeasurePoint;
import mpoints.MeasurePoint;
import pac.Pac;
import refrigerant.Refrigerant;
import translation.TLanguage;

public class JUnitMeasurePoint {

	@Test
	public void test() {

        Pac pac = new Pac();
        Refrigerant refrigerant = new Refrigerant(); 

		ArrayList<MeasurePoint> lMeasurePoints;
		lMeasurePoints = new ArrayList<MeasurePoint>(); 
		
		// Create the List of measure points
		for (EloMeasurePoint p : EloMeasurePoint.values()) {
        	lMeasurePoints.add(new MeasurePoint(p));
		}

		// P1
        MeasurePoint mp1 = lMeasurePoints.get(EloMeasurePoint.P1.id());
		System.out.println(mp1.getMPObject());
	
		// PR0
        MeasurePoint mpPR0 = lMeasurePoints.get(EloMeasurePoint._PR0id);
		System.out.println(mpPR0.getMPObject());
        mpPR0.setValue(5.0, refrigerant, pac, lMeasurePoints);
        
        	
        // Read the list
        for (int i = 0; i < lMeasurePoints.size(); i++) {

        	MeasurePoint m = lMeasurePoints.get(i);  

	        System.out.println(m.getMPObject().name());
	        System.out.println(m.getMPObject().getDefinition(TLanguage.FRENCH));
	        System.out.println("Group " + m.getMPObject().getGroup());
	        System.out.println("Val = " + m.getValue());
	        System.out.println("Selected = " + m.getMP_ChoiceStatus());
	        System.out.println("\n");   
		}
		        
		// JSON
        System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
	//	jsonObj = mp.getJsonObject();
		System.out.println(jsonObj);

		
	}

}
