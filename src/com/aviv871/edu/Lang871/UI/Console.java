package com.aviv871.edu.Lang871.UI;

import java.awt.*;

public class Console extends UITextArea
{
    public void print(String str)
    {
        appendToPane(str, Color.BLACK);
    }

    public void println(String str)
    {
        appendToPane(str + "\n", Color.BLACK);
    }

    public void println()
    {
        appendToPane("\n", Color.BLACK);
    }

    public void printErrorMessage(String error, int line) throws RuntimeException
    {
        appendToPane(error + "\n", Color.RED);

        UIManager.codeEditorInstance.rewriteCodeTextWithErrorHighlight(line);

        throw new RuntimeException();
    }

    public void printErrorMessage(String error) throws RuntimeException
    {
        appendToPane(error + "\n", Color.RED);

        throw new RuntimeException();
    }

    public void printLogMessage(String error) throws RuntimeException
    {
        appendToPane(error + "\n", Color.BLUE);
    }
}
