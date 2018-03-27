package testing.mPoints;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.junit.Test;
import mpoints.EloMeasurePoint;
import mpoints.EloMeasureResult;
import mpoints.MeasurePoint;
import mpoints.MeasureResult;
import pac.Pac;
import translation.TLanguage;

public class JUnitMeasurePointResult {

	@Test
	public void test() {

		Pac pac = new Pac();

		List<MeasurePoint> lMeasurePoints;
		lMeasurePoints = new ArrayList<MeasurePoint>(); 

		// Create the List of Measure POints
		for (EloMeasurePoint p : EloMeasurePoint.values()) {
			lMeasurePoints.add(new MeasurePoint(p));
		}

		// Fill the list of Measure POints
		MeasurePoint mp1 = lMeasurePoints.get(EloMeasurePoint.P1.id());
		MeasurePoint mp2 = lMeasurePoints.get(EloMeasurePoint.P2.id());
		MeasurePoint mp3 = lMeasurePoints.get(EloMeasurePoint.P3.id());
		MeasurePoint mp4 = lMeasurePoints.get(EloMeasurePoint.P4.id());
		MeasurePoint mp5 = lMeasurePoints.get(EloMeasurePoint.P5.id());
		MeasurePoint mp6 = lMeasurePoints.get(EloMeasurePoint.P6.id());
		MeasurePoint mp7 = lMeasurePoints.get(EloMeasurePoint.P7.id());
		MeasurePoint mp8 = lMeasurePoints.get(EloMeasurePoint.P8.id());

		mp3.setValue(15, pac, lMeasurePoints);
		mp7.setValue(4, pac, lMeasurePoints);
		mp4.setValue(15, pac, lMeasurePoints);

		//mp1.setValue(0, pac, lMeasurePoints);
		mp2.setValue(69, pac, lMeasurePoints);
		mp5.setValue(30, pac, lMeasurePoints);
		mp6.setValue(-10, pac, lMeasurePoints);
		mp8.setValue(0, pac, lMeasurePoints);
		// Read the list
		for (int i = 0; i < lMeasurePoints.size(); i++) {

			MeasurePoint mp = lMeasurePoints.get(i);  

			System.out.println(mp.getMPObject().name());
			System.out.println(mp.getMPObject().getDefinition(TLanguage.FRENCH));
			System.out.println("    Group " + mp.getMPObject().getGroup());
			System.out.println("    Val = " + mp.getValue());
			System.out.println("    Selected = " + mp.getMPObjectSelection());
			System.out.println("    P = " + mp.getMP_P());
			System.out.println("    T = " + mp.getMP_T());
			System.out.println("    H = " + mp.getMP_H());
			System.out.println("    PO/PK = " + mp.getMP_P0PK(lMeasurePoints));
			System.out.println("\n");   
		}

		
		// Create the List of Measure Results
		List<MeasureResult> lMeasureResults;
		lMeasureResults = new ArrayList<MeasureResult>(); 

		for (EloMeasureResult p : EloMeasureResult.values()) {
			lMeasureResults.add(new MeasureResult(p));
		}

		// Fill the list of Measure Results
		for (EloMeasureResult p : EloMeasureResult.values()) {
			lMeasureResults.get(p.id()).setValue(lMeasurePoints,pac);
		}

		for (int i = 0; i < lMeasureResults.size(); i++) {

			MeasureResult mr = lMeasureResults.get(i);  
			System.out.println(mr.getMRObject().name());
			System.out.println(mr.getMRObject().getDefinition(TLanguage.FRENCH));
			System.out.println("    Val = " + mr.getValue());
			System.out.println(" ");   

		}		
		
		// JSON
		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = mp1.getJsonObject();
		System.out.println(jsonObj);

		System.out.println(
				lMeasureResults.get(EloMeasureResult.T1_T8.id()).getMRObject().name()  + 
				" = " + 
				lMeasureResults.get(EloMeasureResult.T1_T8.id()).getValue());

		
	}

}
