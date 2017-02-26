package com.aviv871.edu.Lang871;

import com.aviv871.edu.Lang871.Commands.Function;
import com.aviv871.edu.Lang871.Commands.Variable;
import com.aviv871.edu.Lang871.References.LangKeyWords;
import com.aviv871.edu.Lang871.UI.UIManager;
import com.aviv871.edu.Lang871.CodeBlocks.CodeBlock;

import java.util.ArrayList;
import java.util.List;

public class Interpreter
{
    private static final String CODE_BLOCK_END_KEY_WORD = "סוף";

    public static CodeBlock runningCodeBlock = null;

    private static List<String> theCurrentCode; // All the code file that we what to interpret in lines
    private static ArrayList<Integer> cutedCodeLines = new ArrayList<>(); // "Black List" - code lines we need to ignore while running the code

    public static void sendCodeToInterpret(List<String> code)
    {
        // Initializing
        cutedCodeLines.clear();
        theCurrentCode = code;

        // Code handling
        scanForFunctions();

        for(int i = 0; i < theCurrentCode.size(); i++)
        {
            if(!cutedCodeLines.contains(i + 1)) initiateLine(theCurrentCode.get(i), i + 1);
        }

        // Logging
        UIManager.consoleInstance.printLogMessage("\n" + "משתנים:" + "\n" + Variable.globalVariables.toString()); // For debugging only!
        UIManager.consoleInstance.printLogMessage("\n" + "פונקציות:" + "\n" + Function.getFunctionsNames()); // For debugging only!
        LangMain.cleanPreviousData();
    }

    public static void initiateLine(String line, int lineNumber)
    {
        if(line.isEmpty()) return;
        while(line.startsWith(" ") || line.startsWith("\t")) line = line.substring(1, line.length()); // Removing whitespaces and tabs in the beginning of the line

        for(LangKeyWords keyWord: LangKeyWords.values()) // Commands
        {
            if(line.startsWith(keyWord.get871Code() + " "))
            {
                line = line.substring(keyWord.get871Code().length() + 1); // Cutting the command part from the line and leaving only the parameters
                keyWord.getCommand().sendParameters(line, lineNumber);
                return;
            }
        }

        for(String varName: Variable.getVariablesNames()) // Variables
        {
            if(line.startsWith(varName))
            {
                LangKeyWords.VARIABLE_POST.getCommand().sendParameters(line, lineNumber);
                return;
            }
        }

        for(String funcName: Function.getFunctionsNames()) // Functions (calls)
        {
            if(line.startsWith(funcName))
            {
                LangKeyWords.FUNCTION_CALL.getCommand().sendParameters(line, lineNumber);
                return;
            }
        }

        UIManager.consoleInstance.printErrorMessage("שגיאה עם הפקודה בשורה: " + lineNumber, lineNumber);
    }

    private static void scanForFunctions()
    {
        int lineNumber = 0;

        for(String line: theCurrentCode)
        {
            lineNumber++;

            if(line.startsWith(LangKeyWords.FUNCTION.get871Code() + " ") && !cutedCodeLines.contains(lineNumber))
            {
                line = line.substring(LangKeyWords.FUNCTION.get871Code().length() + 1); // Cutting the command part from the line and leaving only the parameters
                LangKeyWords.FUNCTION.getCommand().sendParameters(line, lineNumber);
            }
        }
    }

    public static CodeBlock cutCodeBlock(int startLine)
    {
        // startLine and cutedCodeLines number are like the user see, start in 1, while theCurrentCode line numbers are starting from 0
        for(int i = startLine; i < theCurrentCode.size() + 1; i++)
        {
            if(theCurrentCode.get(i - 1).replaceAll("\\s","").equals(CODE_BLOCK_END_KEY_WORD))
            {
                String[] block = new String[i - startLine];
                int j = startLine;
                for(; j < i; j++)
                {
                    block[j - startLine] = theCurrentCode.get(j - 1);
                    cutedCodeLines.add(j);
                }

                cutedCodeLines.add(startLine - 1); // The function name line
                cutedCodeLines.add(j); // The CODE_BLOCK_END_KEY_WORD line
                return new CodeBlock(block, startLine);
            }
        }
        return null;
    }
}
