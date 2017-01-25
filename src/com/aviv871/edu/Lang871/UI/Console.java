package com.aviv871.edu.Lang871.UI;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class Console
{
    private static JTextPane console;
    private static Style style;
    private static StyledDocument doc;

    public static void setTextArea(JTextPane textArea)
    {
        console = textArea;
        style = console.addStyle("Style871", null);
        doc = console.getStyledDocument();
    }

    public static void print(String str)
    {
        appendToPane(str, Color.black);
    }

    public static void println(String str)
    {
        appendToPane(str + "\n", Color.black);
    }

    public static void println()
    {
        appendToPane("\n", Color.black);
    }

    public static void printErrorMessage(String error) throws RuntimeException
    {
        appendToPane(error + "\n", Color.red);

        throw new RuntimeException();
    }

    public static void printLogMessage(String error) throws RuntimeException
    {
        appendToPane(error + "\n", Color.blue);
    }


    private static void appendToPane(String msg, Color c)
    {
        StyleConstants.setForeground(style, c);

        try
        {
            doc.insertString(doc.getLength(), msg, style);
        }
        catch (BadLocationException e)
        {
            printErrorMessage("שגיאה במהלך הניסיון להדפיס למסך");
        }
    }
}
