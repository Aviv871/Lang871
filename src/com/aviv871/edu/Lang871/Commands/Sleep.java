package com.aviv871.edu.Lang871.Commands;

import java.util.concurrent.TimeUnit;
import com.aviv871.edu.Lang871.UI.UIManager;
import com.aviv871.edu.Lang871.Utilities.NumberExpressionSolver;

public class Sleep implements ICommand
{
    @Override
    public void sendParameters(String par, int lineNumber, boolean preRun)
    {
        if(preRun) UIManager.consoleInstance.printErrorMessage("שגיאה בשורה: " + lineNumber, lineNumber);

        while(par.endsWith(" ")) par = par.substring(0, par.length()-1); // Removing whitespaces in the end of the line
        par = par.replaceAll("\\s",""); // Remove all whitespaces
        Object result = new NumberExpressionSolver(par, lineNumber).getResult();
        if (result instanceof Double)
        {
            try
            {
                TimeUnit.SECONDS.sleep(((Double) result).intValue());
            }
            catch (InterruptedException e)
            {
                UIManager.consoleInstance.printErrorMessage("שגיאה בביצוע המתנה של פקודת חכה, בשורה: " + lineNumber, lineNumber);
            }
        }
        else
        {
            UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, דרוש מספר לביטוי חכה, בשורה: " + lineNumber, lineNumber);
        }
    }
}
