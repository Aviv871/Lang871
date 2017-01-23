package com.aviv871games.learn.Lang871.References;

import com.aviv871games.learn.Lang871.Commands.*;

public enum LangKeyWords
{
    PRINT(new Print()),
    VARIABLE(new Variable()),
    VARIABLE_POST(new VariableUpdate()),
    IF(new If());

    private ICommand command;

    LangKeyWords(ICommand com)
    {
        this.command = com;
    }

    public ICommand get871Command()
    {
        return this.command;
    }
}
