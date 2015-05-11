package iwan91.com.github.StackTracePlus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StackTracePlus {
	private static <T> boolean isPrimitive(T object){
		if(object==null) return true;
		
		if(object instanceof Byte ||
		   object instanceof Short ||
		   object instanceof Integer ||
		   object instanceof Long ||
		   object instanceof Float ||
		   object instanceof Double ||
		   object instanceof Boolean ||
		   object instanceof Character ||
		   object instanceof String ||
		   object instanceof Enum
		  ){
			return true;
		}
		else{
			return false;
		}
	}
	
	private static List<Field> getAllFields (Class<?> type)
	{
		List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) 
        {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;	
	}
	
	private static <T> String getAllFieldsWithValuesAsString(T object, int indent){
		String response="";
		if(object.getClass().isArray()==false){
			response+=indent(indent)+object.getClass().getName()+"\n";
		}
		if(isPrimitive(object)){
			response+=indent(indent+2)+object.getClass().getName()+": "+object.toString()+"\n";
		}
		else{

			List<Field> fields=getAllFields(object.getClass());
			for(int i=0;i<fields.size();i++){
				fields.get(i).setAccessible(true);
				
				if(isArray(fields.get(i),object)){
					String tmp;
					try {
						tmp = getAllFieldsWithValuesAsString(fields.get(i).get(object),indent+1);
						if(tmp!=null){
							response+=tmp;
						}
					} catch (Exception e) {
						return indent(indent)+"!!!Something go wrong!!!\n";
					}	
				}
				else{
					try{
						if(fields.get(i).isSynthetic()==false){
							response+=indent(indent+1)+fields.get(i).getName()+" = "+fields.get(i).getType().getName()+": "+fields.get(i).get(object)+"\n";
	
							if(isPrimitive(fields.get(i).get(object))==false){
									String tmp=getAllFieldsWithValuesAsString(fields.get(i).get(object),indent+1);
									if(tmp!=null){
										response+=tmp;	
								}
							}
						}
					}
					catch(Exception e){
						return indent(indent)+"!!!Something go wrong!!!\n";
					}

				}
			}
			if(object.getClass().isArray()){
				for(int i=0;i<Array.getLength(object);i++){
					String tmp=null;
					if(Array.get(object, i)!=null)
						tmp=getAllFieldsWithValuesAsString(Array.get(object, i),indent+1);
					if(tmp!=null){
						response+=indent(indent+1)+"Array["+i+"]\n"+tmp;
					}
				}
			}

		}

		return response;
	}

	private static <T> boolean isArray(Field field, T object){
		try {
			if(field.get(object).getClass().isArray())
				return true;
			else 
				return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	private static String indent(int indent){
		String r="";
		for(int i=0;i<indent;i++){
			r+="   ";
		}
		return r;
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
				printWriter.write("\n   Object["+i+"]\n");
				String tmp=getAllFieldsWithValuesAsString(objects[i], 1);
				if(tmp!=null) printWriter.write(tmp);		
			}
		}
		
		
		if(message!=null){
			printWriter.write("\nMessage:\n");
			printWriter.write(message);
		}
		
		
		
		return result.toString();		
	}
	
}
