package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.Interpreter;
import com.aviv871.edu.Lang871.UI.UIManager;
import com.aviv871.edu.Lang871.Utilities.BooleanExpressionSolver;
import com.aviv871.edu.Lang871.Utilities.Math;

public class If implements ICommand
{
    private static final String code871 = "אם";

    @Override
    public String get871Code()
    {
        return code871;
    }

    @Override
    public void sendParameters(String par, int lineNumber)
    {
        while(par.endsWith(" ")) par = par.substring(0, par.length()-1); // Removing whitespaces in the end of the line

        if(!par.contains(" אז ")) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, חסר 'אז' בשורה: " + lineNumber, lineNumber);
        String condition = par.substring(0, par.indexOf(" אז "));
        String command = par.substring(par.indexOf(" אז ") + 4);

        //if(checkStringCondition(condition, lineNumber)) Interpreter.initiateLine(command, lineNumber);
        condition = condition.replaceAll("\\s",""); // Remove all whitespaces
        if((boolean) new BooleanExpressionSolver(condition, lineNumber).getResult()) Interpreter.initiateLine(command, lineNumber);
    }
}
