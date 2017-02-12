package com.aviv871.edu.Lang871;

import com.aviv871.edu.Lang871.Commands.Variable;
import com.aviv871.edu.Lang871.References.LangKeyWords;
import com.aviv871.edu.Lang871.UI.UIManager;

import java.util.List;

public class Interpreter
{
    public static void sendCodeToInterpret(List<String> code)
    {
        int currentLineNumber = 0;

        for(String line: code)
        {
            currentLineNumber++;
            if(line.isEmpty()) continue;

            initiateLine(line, currentLineNumber);
        }

        UIManager.consoleInstance.printLogMessage("\n" + "משתנים:" + "\n" + Variable.variables.toString()); // For debugging only!
        Variable.variables.clear();
    }

    public static void initiateLine(String line, int lineNumber)
    {
        for(LangKeyWords keyWord: LangKeyWords.values()) // Commands
        {
            if (line.startsWith(keyWord.get871Code() + " "))
            {
                line = line.substring(keyWord.get871Code().length() + 1); // Cutting the command part from the line and leaving only the parameters
                keyWord.getCommand().sendParameters(line, lineNumber);
                return;
            }
        }

        for(String varName: Variable.getVariablesNames()) // Variables
        {
            if (line.startsWith(varName))
            {
                LangKeyWords.VARIABLE_POST.getCommand().sendParameters(line, lineNumber);
                return;
            }
        }

        UIManager.consoleInstance.printErrorMessage("שגיאה עם הפקודה בשורה: " + lineNumber, lineNumber);
    }
}
