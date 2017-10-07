package com.aviv871.edu.Lang871;

import com.aviv871.edu.Lang871.Commands.*;
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
        Variable.globalVariables.clear();
        Function.functions.clear();
        While.clearLoopsData();
        For.clearLoopsData();
        ForEach.clearLoopsData();
    }
}
