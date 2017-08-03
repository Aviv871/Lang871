package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.Interpreter;
import com.aviv871.edu.Lang871.UI.UIManager;
import com.aviv871.edu.Lang871.Utilities.NumberExpressionSolver;

import java.util.HashMap;
import java.util.Set;

public class Variable extends NameAndStorage implements ICommand
{
    public static HashMap<String, Object> globalVariables = new HashMap<>();

    @Override
    public void sendParameters(String par, int line, boolean preRun)
    {
        if(preRun) UIManager.consoleInstance.printErrorMessage("שגיאה בשורה: " + line, line);

        par = par.replaceAll("\\s",""); // Remove all whitespaces
        int eqCounter = 0;
        for(char c: par.toCharArray())
        {
            if(c == '=') eqCounter++;
        }
        if(eqCounter == 0) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, חסר '=' בשורה: " + line, line); // Make sure there is at least one '='
        String varName = par.substring(0, par.indexOf("="));
        String varValue = par.substring(par.indexOf("=") + 1);
        if(!isNameValid(varName)) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, שם משתנה לא חוקי בשורה: " + line, line); // Make sure there is at least one '='

        if(varValue.startsWith("\"") && varValue.endsWith("\"") && varValue.length() != 1) // String
        {
            globalVariables.put(varName, varValue.substring(1, varValue.length()-1));
            if(Interpreter.runningCodeBlock != null) Interpreter.runningCodeBlock.reportLocalVariable(varName); // In case of local variables
            return;
        }

        Variable.globalVariables.put(varName, new NumberExpressionSolver(varValue, line).getResult()); // If not number this check for boolean as well
        if(Interpreter.runningCodeBlock != null) Interpreter.runningCodeBlock.reportLocalVariable(varName); // In case of local variables
    }

    public static boolean doesVariableExist(String name)
    {
        return doesThisNameExistInStorage(globalVariables, name);
    }

    public static Set<String> getVariablesNames()
    {
        return globalVariables.keySet();
    }

    public static Object getAVariableValue(String varName)
    {
        return globalVariables.get(varName);
    }
}