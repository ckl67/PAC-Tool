package pacp.test;
// http://www.java2blog.com/2013/11/jsonsimple-example-read-and-write-json.html
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import java.io.IOException;  
import java.util.Iterator;  
import org.json.simple.JSONArray;  
import org.json.simple.JSONObject;  
import org.json.simple.parser.JSONParser;  
import org.json.simple.parser.ParseException;  


public class JSONReadingFile {  

	public static void main(String[] args) {  

		JSONParser parser = new JSONParser();  

		try {  
			JSONObject jsonObjectL1;
			JSONObject jsonObjectL2;
			JSONArray jsonArrayL1; 
			
			Object obj = parser.parse(new FileReader("D:/Users/kluges1/Downloads/test.cfg"));  
			System.out.println(obj);  

			// Object level 1 (whole)
			jsonObjectL1 = (JSONObject) obj;  
			System.out.println(jsonObjectL1);
			
			// Object level 2 (+1 deeper)
			jsonObjectL2 = (JSONObject) jsonObjectL1.get("Cfg");  
			System.out.println(jsonObjectL2);
			System.out.println(jsonObjectL2.get("Lang"));  
			System.out.println(String.valueOf(jsonObjectL2.get("Empty")));  
			
			// Object level 2 (+1 deeper) is AN Array !!
			JSONArray jsonObjectScrollL = (JSONArray) jsonObjectL1.get("Scroll");
			System.out.println(	"Array -- >"+ jsonObjectScrollL);

			// Read ARRAY List !
			Iterator<?> iteratorscroll = jsonObjectScrollL.iterator();  
			while (iteratorscroll.hasNext()) {  
				jsonObjectL2 = (JSONObject)iteratorscroll.next();
				System.out.println(String.valueOf(jsonObjectL2));
				System.out.println("AVR = " + String.valueOf(jsonObjectL2.get("AVR")));  
			}  
			
			// ARRAY Get 1 element
			jsonObjectL2 = (JSONObject) jsonObjectScrollL.get(0);  
			System.out.println(jsonObjectL2);
			System.out.println(String.valueOf(jsonObjectL2.get("AVR")));  
			System.out.println(String.valueOf(jsonObjectL2.get("EE")));  

			jsonObjectL2 = (JSONObject) jsonObjectL1.get("Measure");  
			System.out.println(jsonObjectL2);

			jsonArrayL1 = (JSONArray) jsonObjectL2.get("T1T2T3T4");
			System.out.println(jsonArrayL1);
			
			Iterator<?> iterator = jsonArrayL1.iterator();  
			while (iterator.hasNext()) {  
				System.out.println(String.valueOf(iterator.next()));  
			}  
			
			jsonArrayL1 = (JSONArray) jsonObjectL2.get("H1H2H3H4");
			System.out.println(jsonArrayL1);
			System.out.println(String.valueOf(jsonArrayL1.get(0)));
			System.out.println(String.valueOf(jsonArrayL1.get(2)));
			

		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		} catch (ParseException e) {  
			e.printStackTrace();  
		}  

	}  
}  
