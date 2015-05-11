STP = require "StackTracePlus"

function proxy(functionName,...)
  local status,response,args
  args={...}
  response={xpcall(function() return _G[functionName](table.unpack(args)) end,STP.stacktrace)}
  status=response[1]
  table.remove(response,1)
  if(status==false) then
   error(table.unpack(response))
  else
    return table.unpack(response)
  end
end
