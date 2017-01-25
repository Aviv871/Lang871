package com.aviv871.edu.Lang871.UI;

import javax.swing.*;

public class Console
{
    private static JTextArea console;

    public static void setTextArea(JTextArea textArea)
    {
        console = textArea;
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
