package com.aviv871.edu.Lang871;

import com.aviv871.edu.Lang871.UI.GUIManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LangMain
{
    private static File code;

    public static void main(String[] args)
    {
        GUIManager.openGUI(); // TODO: Replace all runtime exceptions with error log to the gui
    }

    private static List<String> readFile(File file)
    {
        List<String> records = new ArrayList<>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null)
            {
                records.add(line);
            }
            reader.close();
            return records;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", file.getName());
            e.printStackTrace();
            return null;
        }
    }

    public static void interpretFile()
    {
        Interpreter.sendCodeToInterpret(readFile(code));
    }

    public static void setCodeFile(File file)
    {
        code = file;
    }
}
