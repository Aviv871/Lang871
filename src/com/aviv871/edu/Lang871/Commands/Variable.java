package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.References.LangKeyWords;
import com.aviv871.edu.Lang871.UI.UIManager;
import com.aviv871.edu.Lang871.Utilities.NumberExpressionSolver;

import java.util.HashMap;
import java.util.Set;

public class Variable implements ICommand
{
    public static HashMap<String, Object> variables = new HashMap<>();

    @Override
    public void sendParameters(String par, int line)
    {
        par = par.replaceAll("\\s",""); // Remove all whitespaces
        int eqCounter = 0;
        for(char c: par.toCharArray())
        {
            if(c == '=') eqCounter++;
        }
        if(eqCounter == 0) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, חסר '=' בשורה: " + line, line); // Make sure there is at least one '='
        String varName = par.substring(0, par.indexOf("="));
        String varValue = par.substring(par.indexOf("=") + 1);
        if(!isVariableNameValid(varName)) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, שם משתנה לא חוקי בשורה: " + line, line); // Make sure there is at least one '='

        if(varValue.startsWith("\"") && varValue.endsWith("\"") && varValue.length() != 1) // String
        {
            variables.put(varName, varValue.substring(1, varValue.length()-1));
            return;
        }

        Variable.variables.put(varName, new NumberExpressionSolver(varValue, line).getResult()); // If not number this check for boolean as well
    }

    private boolean isVariableNameValid(String name)
    {
        if(name.charAt(0) < 'א' || name.charAt(0) > 'ת') return false;
        for(char c: name.toCharArray())
        {
            if((c < 'א' || c > 'ת') && (c < '0' || c > '9')) return false;
        }

        for(LangKeyWords keyWord: LangKeyWords.values()) // Command names that are already taken
        {
            if(name.equals(keyWord.get871Code()))
            {
                return false;
            }
        }

        for(String varName: Variable.getVariablesNames()) // Variable names that are already taken
        {
            if(name.equals(varName))
            {
                return false;
            }
        }

        for(String funcName: Function.getFunctionsNames()) // Functions names that are already taken
        {
            if(name.equals(funcName))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean doesVariableExist(String name)
    {
        for(String varName: variables.keySet())
        {
            if(name.equals(varName)) return true;
        }
        return false;
    }

    public static Set<String> getVariablesNames()
    {
        return variables.keySet();
    }

    public static Object getAVariableValue(String varName)
    {
        return variables.get(varName);
    }
}