package com.aviv871.edu.Lang871.References;

import com.aviv871.edu.Lang871.Commands.*;

public enum LangKeyWords
{
    PRINT(new Print(), "הדפס"),
    VARIABLE(new Variable(), "הגדר"),
    VARIABLE_POST(new VariableUpdate(), "הצב"),
    IF(new If(), "אם"),
    FUNCTION(new Function(), "פונקציה");

    private ICommand command;
    private String code871;
    private Class aClass;

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
