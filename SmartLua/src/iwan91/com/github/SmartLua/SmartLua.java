package iwan91.com.github.SmartLua;

import iwan91.com.github.StackTracePlus.StackTracePlus;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.DebugLib;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class SmartLua {
	
	public static Varargs callLuaFunctionWithProxy(String path,String function, Object... objects){
		Object newObjects[]=new Object[objects.length+1];
		newObjects[0]=function;
		for(int i=0;i<objects.length;i++){
			newObjects[i+1]=objects[i];
		}		
		return callLuaFunction(path,"proxy",newObjects);
	}
	
	private static Varargs callLuaFunction(String path,String function, Object... objects){
		Varargs retvals=null;
		try{
			System.setProperty("org.luaj.debug", "true");
	        LuaValue _G = JsePlatform.standardGlobals();
	        _G.load(new DebugLib());
	        _G.get("dofile").call( LuaValue.valueOf(path));
	        LuaValue fun = _G.get(function);
	        LuaValue[] luaObjects=new LuaValue[objects.length];
			for(int i=0;i<objects.length;i++){
				luaObjects[i]=CoerceJavaToLua.coerce(objects[i]);
			}
			Varargs in=LuaValue.varargsOf(luaObjects);
	        retvals = fun.invoke(in);
	        return retvals;
		}
		catch (Exception e){
			StackTracePlus.printStackTrace(e, null, path, function, objects);
			return null;
		}
		
	}
}
