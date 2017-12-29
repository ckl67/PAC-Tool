package testing.measurePoint;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.junit.Test;
import mpoints.EloMeasurePoint;
import mpoints.MeasurePoint;
import pac.Pac;
import translation.TLanguage;

public class JUnitMeasurePoint {

	@Test
	public void test() {

        Pac pac = new Pac();

        List<MeasurePoint> lMeasurePoints;
		lMeasurePoints = new ArrayList<MeasurePoint>(); 
		
		// Create the List of measure points
		for (EloMeasurePoint p : EloMeasurePoint.values()) {
        	lMeasurePoints.add(new MeasurePoint(p));
		}

		// PR0
        MeasurePoint mpPR0 = lMeasurePoints.get(EloMeasurePoint._PR0id);
        mpPR0.setP(5.0);
		System.out.println("PR0 - P = " + mpPR0.getMP_P());

        // Entre P1
        MeasurePoint mp1 = lMeasurePoints.get(EloMeasurePoint.P1.id());
        mp1.setValue(-40, pac, lMeasurePoints);
		System.out.println("P1 - P = " + mp1.getMP_P());
		System.out.println("P1 - T = " + mp1.getMP_T());
		System.out.println("P1 - H = " + mp1.getMP_H());
		System.out.println("P1 - P0PK = " + mp1.getMP_P0PK(lMeasurePoints));

       // System.exit(0);
        
        // Read the list
        for (int i = 0; i < lMeasurePoints.size(); i++) {

        	MeasurePoint mp = lMeasurePoints.get(i);  

	        System.out.println(mp.getMPObject().name());
	        System.out.println(mp.getMPObject().getDefinition(TLanguage.FRENCH));
	        System.out.println("    Group " + mp.getMPObject().getGroup());
	        System.out.println("    Val = " + mp.getValue());
	        System.out.println("    Selected = " + mp.getMP_ChoiceStatus());
			System.out.println("    P = " + mp.getMP_P());
			System.out.println("    T = " + mp.getMP_T());
			System.out.println("    H = " + mp.getMP_H());
	        System.out.println("\n");   
		}
		        
		// JSON
        System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = mp1.getJsonObject();
		System.out.println(jsonObj);

		
	}

}
