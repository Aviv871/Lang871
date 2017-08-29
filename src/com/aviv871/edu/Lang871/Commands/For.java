package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.CodeBlocks.CodeBlock;
import com.aviv871.edu.Lang871.Interpreter;
import com.aviv871.edu.Lang871.UI.UIManager;
import com.aviv871.edu.Lang871.Utilities.BooleanExpressionSolver;
import com.aviv871.edu.Lang871.Utilities.NumberExpressionSolver;

import java.util.HashMap;

public class For implements ICommand
{
    private static HashMap<Integer, CodeBlock> loops = new HashMap<>(); // Tagged by the line number

    @Override
    public void sendParameters(String par, int line, boolean preRun)
    {
        while(par.endsWith(" ")) par = par.substring(0, par.length()-1); // Removing whitespaces in the end of the line

        par = par.replaceAll("\\s",""); // Remove all whitespaces
        int commCounter = 0;
        int dotsCounter = 0;
        int equleCounter = 0;
        for(char c: par.toCharArray())
        {
            if(c == ':') dotsCounter++;
            else if(c == ',') commCounter++;
            else if(c == '=') equleCounter++;
        }
        if(dotsCounter != 1) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, חסר ':' בשורה: " + line, line); // Make sure there is one ':'
        if(!par.substring(par.indexOf(":") + 1).isEmpty()) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, קטע לא צפוי לאחר נקודותיים בשורה: " + line, line);
        par = par.substring(0, par.indexOf(":"));

        if(commCounter != 2) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, חסר ',' בשורה: " + line, line); // Make sure there is one ':'
        String[] loopValues = par.split(","); // 0 = variable,  1 = condition,  2 = action

        if(preRun)
        {
            CodeBlock codeBlock = Interpreter.cutCodeBlock(line + 1, false);
            if (codeBlock == null) UIManager.consoleInstance.printErrorMessage("שגיאה עם המבנה של הלולאה, חסר 'סוף', ללולאה בשורה: " + line, line);

            loops.put(line, codeBlock);
        }
        else
        {
            if(equleCounter != 2) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, חסר '=' בשורה: " + line, line); // Make sure there is one ':'
            String[] theVariable = loopValues[0].split("=");
            Variable.globalVariables.put(theVariable[0], new NumberExpressionSolver(theVariable[1], line).getResult()); // If not number this check for boolean as well

            while((boolean) new BooleanExpressionSolver(loopValues[1], line).getResult())
            {
                loops.get(line).initiateCode();
                Interpreter.initiateLine(loopValues[2], line);
            }

            Variable.globalVariables.remove(theVariable[0]);
        }
    }
}
