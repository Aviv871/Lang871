package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.UI.Console;
import com.aviv871.edu.Lang871.Utilities.Math;

public class VariableUpdate implements ICommand
{
    private static final String code871 = "הצב";

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
        // Can not happen! //if(!Variable.isVariableExist(varName)) throw new RuntimeException("Error with command parameters, with variable name in line: " + line); // Variable not found



        if(varValue.startsWith("\"") && varValue.endsWith("\"") && varValue.length() != 1) // String
        {
            Variable.variables.replace(varName, varValue.substring(1, varValue.length()-1));
            return;
        }


        else if(varValue.equals("אמת")) // Boolean True
        {
            Variable.variables.replace(varName, true);
            return;
        }
        else if(varValue.equals("שקר")) // Boolean False
        {
            Variable.variables.replace(varName, false);
            return;
        }


        for(String name: Variable.getVariablesNames()) // Other Variable
        {
            if(varValue.equals(name))
            {
                Variable.variables.replace(varName, Variable.getAVariableValue(varValue));
                return;
            }
        }


        Variable.variables.replace(varName, Math.evaluateArithmeticFromString(varValue, line)); // Number including arithmetic or just an error
    }
}
