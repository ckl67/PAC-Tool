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

        MeasurePoint mp1 = lMeasurePoints.get(EloMeasurePoint.P1.id());
        MeasurePoint mp2 = lMeasurePoints.get(EloMeasurePoint.P2.id());
        MeasurePoint mp3 = lMeasurePoints.get(EloMeasurePoint.P3.id());
        MeasurePoint mp4 = lMeasurePoints.get(EloMeasurePoint.P4.id());
        MeasurePoint mp5 = lMeasurePoints.get(EloMeasurePoint.P5.id());
        MeasurePoint mp6 = lMeasurePoints.get(EloMeasurePoint.P6.id());
        MeasurePoint mp7 = lMeasurePoints.get(EloMeasurePoint.P7.id());
        MeasurePoint mp8 = lMeasurePoints.get(EloMeasurePoint.P8.id());
        
        mp1.getmPObjectSelection();
        mp3.setValue(35, pac, lMeasurePoints);
        
        mp7.setValue(-10, pac, lMeasurePoints);
        mp2.setValue(69, pac, lMeasurePoints);
        
        // Read the list
        for (int i = 0; i < lMeasurePoints.size(); i++) {

        	MeasurePoint mp = lMeasurePoints.get(i);  

	        System.out.println(mp.getMPObject().name());
	        System.out.println(mp.getMPObject().getDefinition(TLanguage.FRENCH));
	        System.out.println("    Group " + mp.getMPObject().getGroup());
	        System.out.println("    Val = " + mp.getValue());
	        System.out.println("    Selected = " + mp.getmPObjectSelection());
			System.out.println("    P = " + mp.getMP_P());
			System.out.println("    T = " + mp.getMP_T());
			System.out.println("    H = " + mp.getMP_H());
			System.out.println("    PO/PK = " + mp.getMP_P0PK(lMeasurePoints));
	        System.out.println("\n");   
		}
		        
		// JSON
        System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = mp1.getJsonObject();
		System.out.println(jsonObj);

		
	}

}
