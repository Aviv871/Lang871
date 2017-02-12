package com.aviv871.edu.Lang871.Utilities;

import com.aviv871.edu.Lang871.Interpreter;

public class CodeBlock
{
    private String[] code;
    private int startLineNumber;

    public CodeBlock(String[] code, int startLineNumber)
    {
        this.code = code;
        this.startLineNumber = startLineNumber;
    }

    public void initiateCode()
    {
        for(int i = 0; i < code.length; i++)
        {
            Interpreter.initiateLine(code[i], startLineNumber + i);
        }
    }
}
