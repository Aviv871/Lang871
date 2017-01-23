package com.aviv871games.learn.Lang871.Commands;

public class Print implements ICommand
{
    private static final String code871 = "הדפס";

    @Override
    public String get871Code()
    {
        return code871;
    }

    private void initiateCommand(String val)
    {
        System.out.println(val);
    }

    @Override
    public void sendParameters(String par, int line)
    {
        if(par.startsWith("\"") && par.endsWith("\"")) // Quote
        {
            initiateCommand(par.substring(1, par.length()-1));
        }
        else
        {
            for(String varName: Variable.getVariablesNames()) // Variable
            {
                if(par.equals(varName))
                {
                    Object value = Variable.getAVariableValue(varName);
                    String printValue;
                    if(value instanceof Boolean)
                    {
                        if(value.equals(true))
                        {
                            printValue = "אמת";
                        }
                        else
                        {
                            printValue = "שקר";
                        }
                    }
                    else
                    {
                        printValue = value.toString();
                    }

                    initiateCommand(printValue);
                    return;
                }
            }

            throw new RuntimeException("Error with command parameters in line: " + line);
        }
    }
}
