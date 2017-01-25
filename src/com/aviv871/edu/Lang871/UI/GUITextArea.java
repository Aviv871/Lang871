package com.aviv871.edu.Lang871.UI;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class GUITextArea
{
    protected JTextPane textPane;
    protected Style style;
    protected StyledDocument doc;

    public void setTextArea(JTextPane textArea)
    {
        textPane = textArea;
        style = textPane.addStyle("Style871", null);
        doc = textPane.getStyledDocument();
    }

    protected void appendToPane(String msg, Color c)
    {
        StyleConstants.setForeground(style, c);

        try
        {
            doc.insertString(doc.getLength(), msg, style);
        }
        catch (BadLocationException e)
        {
            GUIManager.consoleInstance.printErrorMessage("שגיאה במהלך הניסיון להדפיס למסך");
        }
    }
}
