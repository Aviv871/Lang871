package com.aviv871.edu.Lang871.References;

import com.aviv871.edu.Lang871.Commands.*;

public enum LangKeyWords
{
    // Class instance, the command key word
    PRINT(new Print(), "הדפס"),
    PRINT_LINE(new PrintLine(), "הדפסש"),
    VARIABLE(new Variable(), "הגדר"),
    VARIABLE_POST(new VariableUpdate(), "הצב"),
    IF(new If(), "אם"),
    FUNCTION(new Function(), "פונקציה"),
    FUNCTION_CALL(new FunctionCall(), "הפעל"),
    WHILE(new While(), "בעוד"),
    FOR(new For(), "עבור");

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
