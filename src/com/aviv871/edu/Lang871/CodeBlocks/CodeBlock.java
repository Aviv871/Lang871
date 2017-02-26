package com.aviv871.edu.Lang871.CodeBlocks;

import com.aviv871.edu.Lang871.Commands.Variable;
import com.aviv871.edu.Lang871.Interpreter;
import com.aviv871.edu.Lang871.References.LangKeyWords;
import com.aviv871.edu.Lang871.UI.UIManager;

import java.util.ArrayList;

public class CodeBlock
{
    private String[] code;
    private int startLineNumber;

    private ArrayList<String> localVariables; // Stores the names of the local variables

    public CodeBlock(String[] code, int startLineNumber)
    {
        this.code = code;
        this.startLineNumber = startLineNumber;
        localVariables = new ArrayList<>();

        checkForInvalidCode();
    }

    public void initiateCode()
    {
        Interpreter.runningCodeBlock = this;
        ///

        for(int i = 0; i < this.code.length; i++)
        {
            Interpreter.initiateLine(code[i], startLineNumber + i);
        }

        cleanLocalVariables(); // Deleting the local variable from the "globalVariables" hashMap
        localVariables.clear();

        ///
        Interpreter.runningCodeBlock = null;
    }

    public void reportLocalVariable(String varName)
    {
        localVariables.add(varName);
    }

    private void cleanLocalVariables()
    {
        for(String varName: localVariables)
        {
            Variable.globalVariables.remove(varName);
        }
    }

    private void checkForInvalidCode()
    {
        for(int i = 0; i < this.code.length; i++)
        {
            if(this.code[i].startsWith(LangKeyWords.FUNCTION.get871Code() + " ")) UIManager.consoleInstance.printErrorMessage("אסור להגדיר פונקציה בתוך פונקציה - שורה: " + startLineNumber + i, startLineNumber + i);
        }
    }

    public String[] getCode()
    {
        return code;
    }

    public int getStartLineNumber()
    {
        return startLineNumber;
    }
}
