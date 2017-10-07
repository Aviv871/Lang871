package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.CodeBlocks.CodeBlock;
import com.aviv871.edu.Lang871.Interpreter;
import com.aviv871.edu.Lang871.UI.UIManager;
import com.aviv871.edu.Lang871.Utilities.BooleanExpressionSolver;

import java.util.HashMap;

public class While implements ICommand
{
    private static HashMap<Integer, CodeBlock> loops = new HashMap<>(); // Tagged by the line number

    @Override
    public void sendParameters(String par, int line, boolean preRun)
    {
        while(par.endsWith(" ")) par = par.substring(0, par.length()-1); // Removing whitespaces in the end of the line

        par = par.replaceAll("\\s",""); // Remove all whitespaces
        int dotsCounter = 0;
        for(char c: par.toCharArray())
        {
            if(c == ':') dotsCounter++;
        }
        if(dotsCounter != 1) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, חסר ':' בשורה: " + line, line); // Make sure there is one ':'
        String condition = par.substring(0, par.indexOf(":"));
        if(!par.substring(par.indexOf(":") + 1).isEmpty()) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, קטע לא צפוי לאחר נקודותיים בשורה: " + line, line);

        if(preRun)
        {
            CodeBlock codeBlock = Interpreter.cutCodeBlock(line + 1, false);
            if (codeBlock == null) UIManager.consoleInstance.printErrorMessage("שגיאה עם המבנה של הלולאה, חסר 'סוף', ללולאה בשורה: " + line, line);
            loops.put(line, codeBlock);
        }
        else
        {
            while((boolean) new BooleanExpressionSolver(condition, line).getResult())
            {
                loops.get(line).initiateCode();
            }
        }
    }

    public static void clearLoopsData()
    {
        loops.clear();
    }
}
