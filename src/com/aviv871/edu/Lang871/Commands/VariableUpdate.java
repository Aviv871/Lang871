package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.UI.UIManager;
import com.aviv871.edu.Lang871.Utilities.NumberExpressionSolver;

public class VariableUpdate implements ICommand
{
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

        Variable.variables.replace(varName, new NumberExpressionSolver(varValue, line).getResult()); // If not number this check for boolean as well
    }
}
