package com.aviv871.edu.Lang871.UI;

import javax.swing.*;

public class UIManager
{
    public static Console consoleInstance =  new Console();
    public static CodeEditor codeEditorInstance = new CodeEditor();

    public static void openUI()
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProgramFrame().setVisible(true);
            }
        });
    }
}
