package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.CodeBlocks.CodeBlock;
import com.aviv871.edu.Lang871.UI.UIManager;

public class FunctionCall implements ICommand
{
    @Override
    public void sendParameters(String par, int line, boolean preRun)
    {
        if(preRun) UIManager.consoleInstance.printErrorMessage("שגיאה בשורה: " + line, line);

        par = par.replaceAll("\\s",""); // Remove all whitespaces
        if(!Function.doesFunctionExist(par)) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, פונקציה שלא קיימת או טעות כתיבה בשורה: " + line, line); // Function not found

        /* CodeBlock function = new CodeBlock(Function.functions.get(par).getCode(), Function.functions.get(par).getStartLineNumber()); // Creates another instance - necessary for recursion
        function.initiateCode(); */
        Function.functions.get(par).duplicate().initiateCode();
    }
}
