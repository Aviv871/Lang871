package com.aviv871.edu.Lang871.UI;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class CodeFilter extends DocumentFilter
{
    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException
    {
        if (GUIManager.codeEditorInstance.displayingError)
        {
            codeChanged();
        }

        super.remove(fb, offset, length);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException
    {
        if (GUIManager.codeEditorInstance.displayingError)
        {
            codeChanged();
        }

        super.replace(fb, offset, length, text, GUIManager.codeEditorInstance.getDefaultAttributeSet());
    }

    private void codeChanged()
    {
        if(GUIManager.codeEditorInstance.displayingError)
        {
            GUIManager.codeEditorInstance.rewriteCleanCodeText();
        }
    }
}
