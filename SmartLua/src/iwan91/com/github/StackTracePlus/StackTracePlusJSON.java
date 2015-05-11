package iwan91.com.github.StackTracePlus;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class StackTracePlusJSON {
	public static <T> String getJSONClass(T classToJson) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(classToJson);
		json = json.replace("\n", "\r\n   ");
		json="   "+json;
		return json;
	}
	
	public static void printStackTrace(Exception javaExcepion,String message, Object... objects){
		System.out.println(getStackTrace(javaExcepion,message, objects));
	}
	
	public static String getStackTrace(Exception javaExcepion,String message, Object... objects){
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		
		if(javaExcepion!=null){
			javaExcepion.printStackTrace(printWriter);
		}
		
		if(objects!=null){
			printWriter.write("\nJava Variables:");
			for(int i=0;i<objects.length;i++){
				if(i>0) printWriter.write("\n");
				printWriter.write("\n   Object["+i+"]\n"+"   "+objects[i].getClass().getName()+"\n");
				String tmp=getJSONClass(objects[i]);
				if(tmp!=null) printWriter.write(tmp);		
			}
		}
		
		
		if(message!=null){
			printWriter.write("\n\nMessage:\n");
			printWriter.write(message);
		}
		
		
		
		return result.toString();	
	}

}
