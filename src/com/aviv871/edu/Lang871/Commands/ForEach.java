package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.CodeBlocks.CodeBlock;
import com.aviv871.edu.Lang871.Interpreter;
import com.aviv871.edu.Lang871.UI.UIManager;

import java.util.HashMap;

public class ForEach implements ICommand
{
    private static HashMap<Integer, CodeBlock> loops = new HashMap<>(); // Tagged by the line number

    @Override
    public void sendParameters(String par, int line, boolean preRun)
    {
        while(par.endsWith(" ")) par = par.substring(0, par.length()-1); // Removing whitespaces in the end of the line

        par = par.replaceAll("\\s",""); // Remove all whitespaces
        int commCounter = 0;
        int dotsCounter = 0;
        for(char c: par.toCharArray())
        {
            if(c == ':') dotsCounter++;
            else if(c == ',') commCounter++;
        }
        if(dotsCounter != 1) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, חסר ':' בשורה: " + line, line); // Make sure there is one ':'
        if(!par.substring(par.indexOf(":") + 1).isEmpty()) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, קטע לא צפוי לאחר נקודותיים בשורה: " + line, line);
        par = par.substring(0, par.indexOf(":"));

        if(commCounter != 1) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, חסר ',' בשורה: " + line, line); // Make sure there is one ':'
        String[] loopValues = par.split(","); // 0 = holder variable,  1 = the array

        if(preRun)
        {
            CodeBlock codeBlock = Interpreter.cutCodeBlock(line + 1, false);
            if (codeBlock == null) UIManager.consoleInstance.printErrorMessage("שגיאה עם המבנה של הלולאה, חסר 'סוף', ללולאה בשורה: " + line, line);

            loops.put(line, codeBlock);
        }
        else
        {
            if(!NameAndStorage.isNameValid(loopValues[0])) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, שם משתנה לא חוקי בשורה: " + line, line); // Make sure there is at least one '='
            Variable.globalVariables.put(loopValues[0], null);

            Object[] theArray;
            if(Variable.globalVariables.get(loopValues[1]) != null)
            {
                theArray = (Object[]) Variable.globalVariables.get(loopValues[1]);
            }
            else
            {
                UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, שם מערך לא חוקי: " + line, line);
                return;
            }

            for(int i = 0; i < theArray.length; i++)
            {
                Variable.globalVariables.replace(loopValues[0], theArray[i]);
                loops.get(line).initiateCode();
            }

            Variable.globalVariables.remove(loopValues[0]);
        }
    }

    public static void clearLoopsData()
    {
        loops.clear();
    }
}
