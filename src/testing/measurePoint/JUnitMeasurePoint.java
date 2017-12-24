package testing.measurePoint;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.junit.Test;

import mpoints.EloMeasurePoint;
import mpoints.MeasurePoint;
import translation.TLanguage;

public class JUnitMeasurePoint {

	@Test
	public void test() {
		
		List<MeasurePoint> lMeasurePoints;

		lMeasurePoints = new ArrayList<MeasurePoint>(); 
		
		// Create the List of measure points
		for (EloMeasurePoint p : EloMeasurePoint.values()) {
        	lMeasurePoints.add(new MeasurePoint(p));
		}

        MeasurePoint mp = lMeasurePoints.get(0);
        
        if (mp.getMPObject().equals(EloMeasurePoint.P1)) {
			System.out.println(mp.getMPObject());				// = P1 (First element of array)
			System.out.println(mp.getMPObject().toString());	// = "P1"
			System.out.println(mp.getMPObject().getDefinition(TLanguage.FRENCH));
        } else {
        	System.out.println("BAD");
        }
	
        	
        // Read the list
        for (int i = 0; i < lMeasurePoints.size(); i++) {

        	MeasurePoint m = lMeasurePoints.get(i);  

	        System.out.println(m.getMPObject().name());
	        System.out.println(m.getMPObject().getDefinition(TLanguage.FRENCH));
	        System.out.println("Group " + m.getMPObject().getGroup());
	        System.out.println("Val = " + m.getValue());
	        System.out.println("Selected = " + m.getMPChoiceStatus());
	        System.out.println("\n");   
		}
		        
		// JSON
        System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = mp.getJsonObject();
		System.out.println(jsonObj);

		
	}

}
