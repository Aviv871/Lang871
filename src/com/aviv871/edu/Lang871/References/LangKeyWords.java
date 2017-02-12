package com.aviv871.edu.Lang871.References;

import com.aviv871.edu.Lang871.Commands.*;

public enum LangKeyWords
{
    PRINT(new Print(), "הדפס"),
    VARIABLE(new Variable(), "הגדר"),
    VARIABLE_POST(new VariableUpdate(), "הצב"),
    IF(new If(), "אם"),
    FUNCTION(new Function(), "פונקציה"),
    FUNCTION_CALL(new FunctionCall(), "הפעל");

    private ICommand command;
    private String code871;

    LangKeyWords(ICommand com, String code)
    {
        this.command = com;
        this.code871 = code;
    }

    public String get871Code()
    {
        return this.code871;
    }

    public ICommand getCommand()
    {
        return this.command;
    }
}
