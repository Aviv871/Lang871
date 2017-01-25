package com.aviv871.edu.Lang871.UI;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeEditor
{
    private static JTextArea code;
    private static File codeFile;

    public static void setTextArea(JTextArea textArea)
    {
        code = textArea;
    }

    public static void loadCodeFile(File file)
    {
        codeFile = file;

        List<String> codeList = readFile(codeFile);
        for(String line: codeList)
        {
            code.append(line + "\n");
        }
    }

    public static List<String> getTheCode()
    {
        String[] lines = code.getText().split("\\r?\\n"); // Split by new lines
        return Arrays.asList(lines);
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

    public static void saveFile()
    {
        if(codeFile != null)
        {
            try
            {
                PrintWriter writer = new PrintWriter(codeFile);
                for(String line: getTheCode())
                {
                    writer.println(line);
                }
                writer.close();
            }
            catch(IOException e)
            {
                // Do something  TODO: Display error massage
            }
        }
        else
        {
            saveFileAs();
        }
    }

    public static void saveFileAs()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\גלעד\\Desktop\\אביב\\מסמכים\\תכנות\\Java\\src\\com\\aviv871\\edu\\Lang871")); // TODO: change to desktop and save the last location the user used

        if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                PrintWriter writer = new PrintWriter(fileChooser.getSelectedFile());
                for(String line: getTheCode())
                {
                    writer.println(line);
                }
                writer.close();
            }
            catch(IOException e)
            {
                // Do something  TODO: Display error massage
            }
        }
    }
}
