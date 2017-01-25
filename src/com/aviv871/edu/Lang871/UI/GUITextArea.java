package com.aviv871.edu.Lang871.UI;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class GUITextArea
{
    protected JTextPane textPane;
    protected Style style;
    protected StyledDocument doc;
    private AttributeSet defaultAttributeSet;

    public void setTextArea(JTextPane textArea)
    {
        textPane = textArea;
        style = textPane.addStyle("Style871", null);
        doc = textPane.getStyledDocument();
        defaultAttributeSet = textPane.getCharacterAttributes();
    }

    public void clearTheTextArea()
    {
        try
        {
            textPane.getDocument().remove(0, textPane.getDocument().getLength());
        }
        catch (BadLocationException ex)
        {
            ex.printStackTrace();
        }
    }

    public void clearTheTextArea(Document doc)
    {
        try
        {
            doc.remove(0, textPane.getDocument().getLength());
        }
        catch (BadLocationException ex)
        {
            ex.printStackTrace();
        }
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

    public AttributeSet getDefaultAttributeSet()
    {
        return defaultAttributeSet;
    }
}
