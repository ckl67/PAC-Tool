package pacp.test;
//http://www.java2blog.com/2013/11/jsonsimple-example-read-and-write-json.html
import java.io.File;  
import java.io.FileWriter;  
import java.io.IOException;  
import org.json.simple.JSONArray;  
import org.json.simple.JSONObject;  


public class JSONWritingFile {  

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {  
		
		JSONObject ObjScroll = new JSONObject();  
		ObjScroll.put("AVR", 10);
		ObjScroll.put("EE", 100);	

		JSONObject ObjCfg = new JSONObject();  
		ObjCfg.put("Lang", "French");
		ObjCfg.put("Empty", 1);
		
		JSONObject ObjMeasure = new JSONObject();  
		JSONArray listOfH = new JSONArray();  
		listOfH.add(100);  
		listOfH.add(200);  
		listOfH.add(300);  
		listOfH.add(400);  
		ObjMeasure.put("H1H2H3H4", listOfH);  

		JSONArray listOfT = new JSONArray();  
		listOfT.add(1);  
		listOfT.add(2);  
		listOfT.add(3);  
		listOfT.add(4);  
		ObjMeasure.put("T1T2T3T4", listOfT);  

		JSONObject ObjPacTool = new JSONObject();  
		ObjPacTool.put("Scroll", ObjScroll);  
		ObjPacTool.put("Cfg", ObjCfg);  
		ObjPacTool.put("Measure", ObjMeasure);

		try {  
			// Writing to a file  
			File file=new File("D:/Users/kluges1/workspace/pac-tool/test/PAC-Tool.cfg");  
			file.createNewFile();  
			FileWriter fileWriter = new FileWriter(file);  
			System.out.println("Writing JSON object to file");  
			System.out.println("-----------------------");  
			System.out.print(ObjPacTool);  

			fileWriter.write(ObjPacTool.toJSONString());  
			fileWriter.flush();  
			fileWriter.close();  

		} catch (IOException e) {  
			e.printStackTrace();  
		}  

	}  
}  

