package testing.measurePoint;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import measurePoint.EloMeasurePoint;
import measurePoint.MeasurePoint;
import translation.TLanguage;

public class JUnitMeasurePoint {

	@Test
	public void test() {
		
		List<MeasurePoint> lMeasurePoints;

		lMeasurePoints = new ArrayList<MeasurePoint>(); 
		
		// Create the List of measure points
        for (@SuppressWarnings("unused") EloMeasurePoint p : EloMeasurePoint.values())
        	lMeasurePoints.add(new MeasurePoint());

        // Read the list
		for (EloMeasurePoint p : EloMeasurePoint.values()) {
			MeasurePoint m = lMeasurePoints.get(p.id());  

	        System.out.println(p.name());
	        System.out.println("Group " + p.getGroup());
	        System.out.println(p.getDefinition(TLanguage.FRENCH));
	        System.out.println("Val = " + m.getValue());
	        System.out.println("Selected = " + m.selected());
	        System.out.println("\n");   
		}
			
		for (EloMeasurePoint p : EloMeasurePoint.values()) {
			MeasurePoint m = lMeasurePoints.get(p.id());  
			m.setValue((double)(p.id()));
		}

		for (EloMeasurePoint p : EloMeasurePoint.values()) {
			MeasurePoint m = lMeasurePoints.get(p.id());  

	        System.out.println(p.name());
	        System.out.println("Group " + p.getGroup());
	        System.out.println(p.getDefinition(TLanguage.ENGLICH));
	        System.out.println("Val = " + m.getValue());
	        System.out.println("Selected = " + m.selected());
	        System.out.println("\n");   
		}
		
		
	}

}
