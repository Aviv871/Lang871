package com.aviv871.edu.Lang871.UI;

import javax.swing.*;

public class Console
{
    private static JTextArea console;

    public Console(JTextArea ta)
    {
        console = ta;
    }

    public static void print(String str)
    {
        console.append(str);
    }

    public static void println(String str)
    {
        console.append(str + "\n");
    }

    public static void println()
    {
        console.append("\n");
    }
}
