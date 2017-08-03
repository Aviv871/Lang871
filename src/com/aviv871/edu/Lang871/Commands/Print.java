package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.UI.UIManager;

public class Print implements ICommand
{
    protected void initiateCommand(String val)
    {
        UIManager.consoleInstance.print(val);
    }

    @Override
    public void sendParameters(String par, int line, boolean preRun)
    {
        if(preRun) UIManager.consoleInstance.printErrorMessage("שגיאה בשורה: " + line, line);

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

            UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה בשורה: " + line, line);
        }
    }
}
