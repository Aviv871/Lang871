package com.aviv871.edu.Lang871.Utilities;

import com.aviv871.edu.Lang871.Interpreter;
import com.aviv871.edu.Lang871.References.LangKeyWords;
import com.aviv871.edu.Lang871.UI.UIManager;

public class CodeBlock
{
    private String[] code;
    private int startLineNumber;

    public CodeBlock(String[] code, int startLineNumber)
    {
        this.code = code;
        this.startLineNumber = startLineNumber;

        checkForInvalidCode();
    }

    public void initiateCode()
    {
        for(int i = 0; i < this.code.length; i++)
        {
            Interpreter.initiateLine(code[i], startLineNumber + i);
        }
    }

    private void checkForInvalidCode()
    {
        for(int i = 0; i < this.code.length; i++)
        {
            if(this.code[i].startsWith(LangKeyWords.FUNCTION.get871Code() + " ")) UIManager.consoleInstance.printErrorMessage("אסור להגדיר פונקציה בתוך פונקציה - שורה: " + startLineNumber + i, startLineNumber + i);
        }
    }
}
