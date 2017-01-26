package com.aviv871.edu.Lang871;

import com.aviv871.edu.Lang871.UI.GUIManager;

public class LangMain
{
    public static void main(String[] args)
    {
        GUIManager.openGUI();
    }

    public static void interpretFile()
    {
        Interpreter.sendCodeToInterpret(GUIManager.codeEditorInstance.getTheCode());
    }
}
