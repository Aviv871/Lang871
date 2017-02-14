package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.UI.UIManager;

public class PrintLine extends Print
{
    @Override
    protected void initiateCommand(String val)
    {
        UIManager.consoleInstance.println(val);
    }
}
