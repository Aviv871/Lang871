package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.UI.UIManager;
import com.aviv871.edu.Lang871.Utilities.NumberExpressionSolver;

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

        boolean arrayFlag1 = false, arrayFlag2 = false;
        for(char c: par.toCharArray())
        {
            if(c == '[') arrayFlag1 = true;
            else if(c == ']') arrayFlag2 = true;
        }

        if(arrayFlag1 && arrayFlag2) // One value in array
        {
            double index = (double) new NumberExpressionSolver(par.substring(par.indexOf("[")+1, par.indexOf("]")), line).getResult();
            if(index % 1 != 0) UIManager.consoleInstance.printErrorMessage("אינדקס לא חוקי למערך בשורה - " + line, line); // check that it is indeed always int
            Object[] theArray = (Object[]) Variable.getAVariableValue(par.substring(0, par.indexOf("[")));
            initiateCommand(theArray[(int)index].toString());
        }
        else if(par.startsWith("\"") && par.endsWith("\"")) // Quote
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
                    String printValue = "";
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
                    else if(value instanceof Object[]) // All array
                    {
                        for(Object a: (Object[]) value)
                        {
                            if(a instanceof Boolean)
                            {
                                if(a.equals(true))
                                {
                                    printValue += "אמת ";
                                }
                                else
                                {
                                    printValue += "שקר ";
                                }
                            }
                            else
                            {
                                printValue += a.toString() + " ";
                            }
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
