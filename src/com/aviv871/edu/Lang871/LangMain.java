package com.aviv871.edu.Lang871;

import com.aviv871.edu.Lang871.Commands.Function;
import com.aviv871.edu.Lang871.Commands.Variable;
import com.aviv871.edu.Lang871.UI.UIManager;

public class LangMain
{
    public static void main(String[] args)
    {
        UIManager.openUI();
    }

    public static void interpretFile()
    {
        Interpreter.sendCodeToInterpret(UIManager.codeEditorInstance.getTheCode());
    }

    public static void cleanPreviousData()
    {
        Variable.variables.clear();
        Function.functions.clear();
    }
}
