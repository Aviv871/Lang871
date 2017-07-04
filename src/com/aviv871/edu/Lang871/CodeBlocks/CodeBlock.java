package com.aviv871.edu.Lang871.CodeBlocks;

import com.aviv871.edu.Lang871.Commands.Variable;
import com.aviv871.edu.Lang871.Interpreter;
import com.aviv871.edu.Lang871.References.LangKeyWords;
import com.aviv871.edu.Lang871.UI.UIManager;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeBlock
{
    private String[] code;
    private int startLineNumber;
    private boolean inRecursion;

    private ArrayList<String> localVariables; // Stores the names of the local variables
    private HashMap<String, Object> variablesSnapshotForRecursion;
    private CodeBlock blockSnapshotForRecursion;

    public CodeBlock(String[] code, int startLineNumber)
    {
        this.code = code;
        this.startLineNumber = startLineNumber;
        localVariables = new ArrayList<>();

        checkForInvalidCode();
    }

    public void initiateCode()
    {
        inRecursion = false;
        if(Interpreter.runningCodeBlock != null)
        {
            createVariablesSnapshot(Interpreter.runningCodeBlock.localVariables); // Save last code block variables
            cleanLocalVariables(Interpreter.runningCodeBlock.localVariables); // Delete last code block variables from access
            blockSnapshotForRecursion = Interpreter.runningCodeBlock; // Save last code block pointer
            inRecursion = true;
        }

        Interpreter.runningCodeBlock = this;
        ///

        for(int i = 0; i < this.code.length; i++)
        {
            Interpreter.initiateLine(code[i], startLineNumber + i);
        }

        cleanLocalVariables(this.localVariables); // Deleting the local variable from the "globalVariables" hashMap
        localVariables.clear();

        ///
        if(!inRecursion) Interpreter.runningCodeBlock = null;
        else
        {
            loadVariablesSnapshot(); // Reload the saved variables of the last code block
            Interpreter.runningCodeBlock = blockSnapshotForRecursion; // Reset the pointer to the code block
        }
    }

    public void reportLocalVariable(String varName)
    {
        localVariables.add(varName);
    }

    private void cleanLocalVariables(ArrayList<String> list)
    {
        for(String varName: list)
        {
            Variable.globalVariables.remove(varName);
        }
    }

    private void checkForInvalidCode()
    {
        for(int i = 0; i < this.code.length; i++)
        {
            if(this.code[i].startsWith(LangKeyWords.FUNCTION.get871Code() + " ")) UIManager.consoleInstance.printErrorMessage("אסור להגדיר פונקציה בתוך פונקציה - שורה: " + startLineNumber + i, startLineNumber + i);
        }
    }

    private void createVariablesSnapshot(ArrayList<String> localVariablesList)
    {
        variablesSnapshotForRecursion = new HashMap<>(); // Also reset previous values

        for(String varName: localVariablesList)
        {
            variablesSnapshotForRecursion.put(varName, Variable.globalVariables.get(varName));
        }
    }

    private void loadVariablesSnapshot()
    {
        for(String varName: variablesSnapshotForRecursion.keySet())
        {
            Variable.globalVariables.put(varName, variablesSnapshotForRecursion.get(varName));
        }
    }

    public String[] getCode()
    {
        return code;
    }

    public int getStartLineNumber()
    {
        return startLineNumber;
    }

    public CodeBlock duplicate()
    {
        return new CodeBlock(this.code, this.startLineNumber);
    }
}
