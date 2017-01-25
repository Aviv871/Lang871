package com.aviv871.edu.Lang871;

import com.aviv871.edu.Lang871.UI.CodeEditor;
import com.aviv871.edu.Lang871.UI.GUIManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LangMain
{
    public static void main(String[] args)
    {
        GUIManager.openGUI(); // TODO: Replace all runtime exceptions with error log to the gui
    }

    public static void interpretFile()
    {
        Interpreter.sendCodeToInterpret(CodeEditor.getTheCode());
    }
}
