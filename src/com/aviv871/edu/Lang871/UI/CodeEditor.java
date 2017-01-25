package com.aviv871.edu.Lang871.UI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeEditor extends GUITextArea
{
    private File codeFile;

    public void loadCodeFile(File file)
    {
        codeFile = file;

        List<String> codeList = readFile(codeFile);
        for(String line: codeList)
        {
            appendToPane(line + "\n", Color.black);
        }
    }

    public List<String> getTheCode()
    {
        String[] lines = textPane.getText().split("\\r?\\n"); // Split by new lines
        return Arrays.asList(lines);
    }

    private List<String> readFile(File file)
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
            GUIManager.consoleInstance.printErrorMessage("שגיאה במהלך הניסיון לקרוא את הקובץ קוד");
            e.printStackTrace();
            return null;
        }
    }

    public void saveFile()
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
                GUIManager.consoleInstance.printErrorMessage("שגיאה במהלך הניסיון לשמור את קובץ הקוד");
                e.printStackTrace();
            }
        }
        else
        {
            saveFileAs();
        }
    }

    public void saveFileAs()
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
                GUIManager.consoleInstance.printErrorMessage("שגיאה במהלך הניסיון לקרוא את הקובץ קוד");
                e.printStackTrace();
            }
        }
    }
}
