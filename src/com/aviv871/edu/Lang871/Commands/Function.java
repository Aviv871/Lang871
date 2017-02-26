package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.Interpreter;
import com.aviv871.edu.Lang871.UI.UIManager;
import com.aviv871.edu.Lang871.CodeBlocks.CodeBlock;

import java.util.HashMap;
import java.util.Set;

public class Function extends NameAndStorage implements ICommand
{
    public static HashMap<String, CodeBlock> functions = new HashMap<>();

    @Override
    public void sendParameters(String par, int line)
    {
        par = par.replaceAll("\\s",""); // Remove all whitespaces
        int dotsCounter = 0;
        for(char c: par.toCharArray())
        {
            if(c == ':') dotsCounter++;
        }
        if(dotsCounter != 1) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, חסר ':' בשורה: " + line, line); // Make sure there is one ':'
        String fucName = par.substring(0, par.indexOf(":"));
        if(!par.substring(par.indexOf(":") + 1).isEmpty()) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, קטע לא צפוי לאחר נקודותיים בשורה: " + line, line);
        if(!isNameValid(fucName)) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, שם הפונקציה לא חוקי בשורה: " + line, line); // Make sure there is at least one '='

        CodeBlock codeBlock = Interpreter.cutCodeBlock(line + 1);
        if(codeBlock == null) UIManager.consoleInstance.printErrorMessage("שגיאה עם המבנה של הפונקציה, חסר 'סוף', לפונקציה בשורה: " + line, line);

        functions.put(fucName, codeBlock);
    }

    public static Set<String> getFunctionsNames()
    {
        return functions.keySet();
    }

    public static boolean doesFunctionExist(String name)
    {
        return doesThisNameExistInStorage(functions, name);
    }
}
