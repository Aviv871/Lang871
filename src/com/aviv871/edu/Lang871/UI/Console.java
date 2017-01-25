package com.aviv871.edu.Lang871.UI;

import java.awt.*;

public class Console extends GUITextArea
{
    public void print(String str)
    {
        appendToPane(str, Color.black);
    }

    public void println(String str)
    {
        appendToPane(str + "\n", Color.black);
    }

    public void println()
    {
        appendToPane("\n", Color.black);
    }

    public void printErrorMessage(String error) throws RuntimeException
    {
        appendToPane(error + "\n", Color.red);

        throw new RuntimeException();
    }

    public void printLogMessage(String error) throws RuntimeException
    {
        appendToPane(error + "\n", Color.blue);
    }
}
