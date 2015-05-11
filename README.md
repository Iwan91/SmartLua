# SmartLua
Wrapper around Lua/LuaJ to get more info about errors from lua and java

This wrapper use:
* [StackTracePlus](https://github.com/Iwan91/StackTracePlus) for Java
* [StackTracePlus](https://github.com/ignacio/StackTracePlus) for Lua

## Usage:
You can find more examples [here](https://github.com/Iwan91/SmartLua/tree/master/SmartLua/src/iwan91/com/github/SmartLua/examples)

## Example:
Myfunctions.lua file:
```lua
require 'Proxy'

function myFunctionAdd(num1,num2)
  return num1 + num2
end

function myFunctionChangeObjectValues(object, newValue)
  object:setValue(newValue)
end

function myFunctionWithError(num1,num2)
  local s = "This is string"
  local n = 42
  
  error("My Exception")
  
  return num1+num2
end
```
Run below Java code:
```java
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
```
and you get this:
```java
5+9=14

Value before change=10
Value after change=6

file not found: io error: MyFunctions.lua (Nie można odnaleźć określonego pliku)
file not found: io error: Proxy.lua (Nie można odnaleźć określonego pliku)
org.luaj.vm2.LuaError: @Proxy.lua:10 @MyFunctions.lua:16 My Exception
Stack Traceback
===============
(0) Lua function '?' at file 'MyFunctions.lua:16' (best guess)
	Local variables:
	 num1 = number: 5.0
	 num2 = number: 9.0
	 s = string: "This is string"
	 n = number: 42.0
(1) Lua global 'xpcall' at line -1 of chunk 'xpcall'
(2) Lua function '?' at file 'Proxy.lua:6' (best guess)
	Local variables:
	 functionName = string: "myFunctionWithError"
	 status = nil
	 response = nil
	 args = table: bc6874  {1:5, 2:9}

stack traceback:
	Proxy.lua:10: in function <Proxy.lua:3>
	[Java]: in ?
	at org.luaj.vm2.lib.BaseLib$error.call(Unknown Source)
	at org.luaj.vm2.lib.TwoArgFunction.invoke(Unknown Source)
	at org.luaj.vm2.LuaClosure.execute(Unknown Source)
	at org.luaj.vm2.LuaClosure.onInvoke(Unknown Source)
	at org.luaj.vm2.LuaClosure.invoke(Unknown Source)
	at iwan91.com.github.SmartLua.SmartLua.callLuaFunction(SmartLua.java:35)
	at iwan91.com.github.SmartLua.SmartLua.callLuaFunctionWithProxy(SmartLua.java:19)
	at iwan91.com.github.SmartLua.examples.Example1Main.main(Example1Main.java:28)

Java Variables:
   Object[0]
   java.lang.String
         java.lang.String: MyFunctions.lua

   Object[1]
   java.lang.String
         java.lang.String: proxy

   Object[2]
      Array[0]
      java.lang.String
            java.lang.String: myFunctionWithError
      Array[1]
      java.lang.Integer
            java.lang.Integer: 5
      Array[2]
      java.lang.Integer
            java.lang.Integer: 9

Error with lua script
```
