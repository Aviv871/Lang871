package com.aviv871.edu.Lang871.UI;

import javax.swing.*;

public class GUIManager
{
    public static void openGUI()
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProgramFrame().setVisible(true);
            }
        });
    }
}
