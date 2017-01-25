package com.aviv871.edu.Lang871;

import com.aviv871.edu.Lang871.Commands.Variable;
import com.aviv871.edu.Lang871.References.LangKeyWords;
import com.aviv871.edu.Lang871.UI.Console;
import com.aviv871.edu.Lang871.UI.GUIManager;

import java.util.List;

public class Interpreter
{
    public static void sendCodeToInterpret(List<String> code)
    {
        int currentLineNumber = 0;

        for(String line: code)
        {
            currentLineNumber++;
            if(isLineEmpty(line)) continue;

            initiateLine(line, currentLineNumber);
        }

        GUIManager.consoleInstance.printLogMessage("\n" + "משתנים:" + "\n" + Variable.variables.toString()); // For debugging only!
    }

    private static boolean isLineEmpty(String line)
    {
        if(line.equals("")) return true;
        for(char c: line.toCharArray())
        {
            if(!Character.isWhitespace(c)) return false;
        }

        return true;
    }

    public static void initiateLine(String line, int lineNumber)
    {
        for(LangKeyWords keyWord: LangKeyWords.values()) // Commands
        {
            if (line.startsWith(keyWord.get871Command().get871Code() + " "))
            {
                line = line.substring(keyWord.get871Command().get871Code().length() + 1); // Cutting the command part from the line and leaving only the parameters
                keyWord.get871Command().sendParameters(line, lineNumber);
                return;
            }
        }

        for(String varName: Variable.getVariablesNames()) // Variables
        {
            if (line.startsWith(varName))
            {
                LangKeyWords.VARIABLE_POST.get871Command().sendParameters(line, lineNumber);
                return;
            }
        }

        GUIManager.consoleInstance.printErrorMessage("שגיאה עם הפקודה בשורה: " + lineNumber);
    }
}
