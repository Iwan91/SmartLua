package iwan91.com.github.SmartLua.examples;

import org.luaj.vm2.Varargs;

import iwan91.com.github.SmartLua.SmartLua;

public class Example1Main {
	public static void main(String[] args) {
		/*Parameters for function SmartLua.callLuaFunctionWithProxy
		  1st parameter path to lua script. Type String. Cannot be null
		  2nd parameter function name in lua file. Type String. Cannot be null
		  others parameters same as lua arguments function need. Type Object. Can be from 0 to n objects.*/
		
		int a=5,b=9;
		Varargs result=SmartLua.callLuaFunctionWithProxy("MyFunctions.lua", "myFunctionAdd", a, b);
		System.out.println(a+"+"+b+"="+result);
		
		System.out.println();
		
		ClassA classA=new ClassA();
		classA.setValue(10);
		System.out.println("Value before change="+classA.getValue());
		SmartLua.callLuaFunctionWithProxy("MyFunctions.lua","myFunctionChangeObjectValues", classA, 6);
		System.out.println("Value after change="+classA.getValue());
		
		System.out.println();
		
		Varargs result2=SmartLua.callLuaFunctionWithProxy("MyFunctions.lua", "myFunctionWithError", a, b);
		if(result2!=null)
			System.out.println(a+"+"+b+"="+result2);
		else
			System.out.println("Error with lua script");
	}
}
