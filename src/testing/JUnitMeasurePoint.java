package testing;

//import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.junit.Test;

import computation.MeasureObject;
import computation.MeasurePoint;
import translation.TLanguage;

public class JUnitMeasurePoint {

	@Test
	public void test() {
		
		System.out.println("TEST MEASURE");
		
		List<MeasurePoint> measurePL1;
		measurePL1 = new ArrayList<MeasurePoint>(); 
        for (MeasureObject p : MeasureObject.values())
        	measurePL1.add(new MeasurePoint(p));

        MeasurePoint ma = measurePL1.get(0);
        
        if (ma.getMeasureObject().equals(MeasureObject.P1)) {
			System.out.println(ma.getMeasureObject());				// = P1 from MeasureObject.P1 (First element of array)
			System.out.println(ma.getMeasureObject().toString());	// = "P1"
			System.out.println(ma.getMeasureObject().getDefinition(TLanguage.ENGLICH));
        }
        
        MeasurePoint mb = measurePL1.get(MeasureObject.P2.ordinal());
        mb.setValue(12);
        System.out.println(mb.getMeasureObject());
		System.out.println(mb.getValue() + " " + mb.getMeasureObject().getUnity());
		System.out.println(mb.getMP() + " bars");
		

		MeasurePoint vMeasure = new MeasurePoint(MeasureObject.P1);
		vMeasure.setValue(23.457);
		System.out.println(vMeasure.getMeasureObject());

		System.out.println("\n---> Construct JSON data");
		JSONObject jsonObj = new JSONObject();
		jsonObj = vMeasure.getJsonObject();
		System.out.println(jsonObj);

		System.out.println("\n---> Set the Class Instance with JSON data");
		vMeasure.setJsonObject(jsonObj);
		System.out.println(jsonObj);

		System.out.println("\n---> Read afterwards ");
		System.out.println("    Name="+vMeasure.getMeasureObject());
	}

}
