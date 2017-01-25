package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.UI.Console;
import com.aviv871.edu.Lang871.Utilities.Math;

import java.util.HashMap;
import java.util.Set;

public class Variable implements ICommand
{
    private static final String code871 = "הגדר";

    public static HashMap<String, Object> variables = new HashMap<>();

    @Override
    public String get871Code()
    {
        return code871;
    }

    @Override
    public void sendParameters(String par, int line)
    {
        while(par.endsWith(" ")) par = par.substring(0, par.length()-1); // Removing whitespaces in the end of the line

        par = par.replaceAll("\\s",""); // Remove all whitespaces
        int eqCounter = 0;
        for(char c: par.toCharArray())
        {
            if(c == '=') eqCounter++;
        }
        if(eqCounter == 0) Console.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, חסר '=' בשורה: " + line); // Make sure there is at least one '='
        String varName = par.substring(0, par.indexOf("="));
        String varValue = par.substring(par.indexOf("=") + 1);
        if(!isVariableNameValid(varName)) Console.printErrorMessage("שגיאה עם הפרמטקים של הפקודה, שם משתנה לא חוקי בשורה: " + line); // Make sure there is at least one '='



        if(varValue.startsWith("\"") && varValue.endsWith("\"") && varValue.length() != 1) // String
        {
            variables.put(varName, varValue.substring(1, varValue.length()-1));
            return;
        }


        else if(varValue.equals("אמת")) // Boolean True
        {
            variables.put(varName, true);
            return;
        }
        else if(varValue.equals("שקר")) // Boolean False
        {
            variables.put(varName, false);
            return;
        }


        for(String name: Variable.getVariablesNames()) // Other Variable
        {
            if(varValue.equals(name))
            {
                variables.put(varName, Variable.getAVariableValue(varValue));
                return;
            }
        }


        Variable.variables.put(varName, Math.evaluateArithmeticFromString(varValue, line)); // Number including arithmetic or just an error
    }

    private boolean isVariableNameValid(String name)
    {
        if(name.charAt(0) < 'א' || name.charAt(0) > 'ת') return false;
        for(char c: name.toCharArray())
        {
            if((c < 'א' || c > 'ת') && (c < '0' || c > '9')) return false;
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