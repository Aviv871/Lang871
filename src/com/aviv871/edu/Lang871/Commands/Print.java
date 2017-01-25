package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.UI.Console;
import com.aviv871.edu.Lang871.UI.GUIManager;

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
        GUIManager.consoleInstance.println(val);
    }

    @Override
    public void sendParameters(String par, int line)
    {
        while(par.endsWith(" ")) par = par.substring(0, par.length()-1); // Removing whitespaces in the end of the line

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

            GUIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה בשורה: " + line);
        }
    }
}
