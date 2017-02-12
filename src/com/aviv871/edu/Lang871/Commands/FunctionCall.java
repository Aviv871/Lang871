package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.UI.UIManager;

public class FunctionCall implements ICommand
{
    @Override
    public void sendParameters(String par, int line)
    {
        par = par.replaceAll("\\s",""); // Remove all whitespaces
        if(!Function.doesFunctionExist(par)) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, פונקציה שלא קיימת או טעות כתיבה בשורה: " + line, line); // Function not found

        Function.functions.get(par).initiateCode();
    }
}
