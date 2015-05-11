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