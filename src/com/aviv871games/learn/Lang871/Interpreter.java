package com.aviv871games.learn.Lang871;

import com.aviv871games.learn.Lang871.Commands.Variable;
import com.aviv871games.learn.Lang871.References.LangKeyWords;

import java.util.List;

public class Interpreter
{
    public static void sendCodeToInterpret(List<String> code)
    {
        int currentLineNumber = 0;

        newLine:
        for(String line: code)
        {
            currentLineNumber++;
            if(isLineEmpty(line)) continue;

            for(LangKeyWords keyWord: LangKeyWords.values()) // Commands
            {
                if (line.startsWith(keyWord.get871Command().get871Code() + " "))
                {
                    line = line.substring(keyWord.get871Command().get871Code().length() + 1); // Cutting the command part from the line and leaving only the parameters
                    keyWord.get871Command().sendParameters(line, currentLineNumber);
                    continue newLine;
                }
            }

            for(String varName: Variable.getVariablesNames()) // Variables
            {
                if (line.startsWith(varName))
                {
                    LangKeyWords.VARIABLE_POST.get871Command().sendParameters(line, currentLineNumber);
                    continue newLine;
                }
            }

            throw new RuntimeException("Error with command in line: " + currentLineNumber);
        }

        System.out.println(Variable.variables); // For debugging only!
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
}
