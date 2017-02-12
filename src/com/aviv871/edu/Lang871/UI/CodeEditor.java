package com.aviv871.edu.Lang871.UI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeEditor extends UITextArea
{
    private File codeFile;

    public boolean displayingError = false;

    public void loadCodeFile(File file)
    {
        if(file == null) return;
        clearTheTextArea();
        codeFile = file;

        List<String> codeList = readFile(codeFile);
        for(String line: codeList)
        {
            appendToPane(line + "\n", Color.BLACK);
        }
    }

    public List<String> getTheCode()
    {
        String[] lines = textPane.getText().split("\\r?\\n"); // Split by new lines
        return Arrays.asList(lines);
    }

    public String getTheText()
    {
        return textPane.getText();
    }

    private List<String> readFile(File file)
    {
        List<String> records = new ArrayList<>();
        try
        {
            FileInputStream inputStream = new FileInputStream(file); // Open a file reader object
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF8"); // Wrap the file reader with InputStreamReader with UTF8 encoding
            BufferedReader reader = new BufferedReader(inputStreamReader); // Wrap it with buffered reader

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
            UIManager.consoleInstance.printErrorMessage("שגיאה במהלך הניסיון לקרוא את הקובץ קוד");
            e.printStackTrace();
            return null;
        }
    }

    public void saveFile()
    {
        if(codeFile != null)
        {
            writeToFile(codeFile);
        }
        else
        {
            saveFileAs();
        }
    }

    public void saveFileAs()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Lang871 Code Files", "871")); // Setting file type filter
        fileChooser.setSelectedFile(new File("קוד.871")); // Setting suggested file name
        fileChooser.setCurrentDirectory(new File("C:\\Users\\גלעד\\Desktop\\אביב\\מסמכים\\תכנות\\Java\\src\\com\\aviv871\\edu\\Lang871")); // TODO: change to desktop and save the last location the user used

        if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            File file;
            if(!fileChooser.getSelectedFile().getName().endsWith(".871")) file = new File(fileChooser.getSelectedFile() + ".871");
            else file = fileChooser.getSelectedFile();

            writeToFile(file);
            codeFile = file;
        }
    }

    private void writeToFile(File file)
    {
        try
        {
            FileOutputStream outputStream = new FileOutputStream(file); // Open a file writer object
            OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream, "UTF8"); // Wrap the file writer with OutputStreamWriter with UTF8 encoding
            BufferedWriter writer = new BufferedWriter(streamWriter); // Wrap it with buffered writer

            for(String line: getTheCode())
            {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        }
        catch(IOException e)
        {
            UIManager.consoleInstance.printErrorMessage("שגיאה במהלך הניסיון לשמור את הקובץ קוד");
            e.printStackTrace();
        }
    }

    public void rewriteCodeTextWithErrorHighlight(int line)
    {
        List<String> oldCode = getTheCode();
        clearTheTextArea();

        String str;
        for(int i = 0; i < oldCode.size(); i++)
        {
            str = oldCode.get(i);

            if(i == line - 1) appendToPane(str + "\n", Color.RED);
            else appendToPane(str + "\n", Color.BLACK);
        }
        displayingError = true;
    }

    public void rewriteCleanCodeText()
    {
        displayingError = false;
        List<String> oldCode = getTheCode();
        int caretPosition = textPane.getCaretPosition();

        clearTheTextArea();

        for(String str: oldCode)
        {
            appendToPane(str + "\n", Color.BLACK);
        }
        textPane.setCaretPosition(caretPosition);
    }
}
