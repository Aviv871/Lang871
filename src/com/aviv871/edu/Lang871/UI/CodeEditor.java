package com.aviv871.edu.Lang871.UI;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeEditor
{
    private static JTextPane code;
    private static Style style;
    private static StyledDocument doc;

    private static File codeFile;

    public static void setTextArea(JTextPane textArea)
    {
        code = textArea;
        style = code.addStyle("Style871", null);
        doc = code.getStyledDocument();
    }

    public static void loadCodeFile(File file)
    {
        codeFile = file;

        List<String> codeList = readFile(codeFile);
        for(String line: codeList)
        {
            appendToPane(line + "\n", Color.black);
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
            Console.printErrorMessage("שגיאה במהלך הניסיון לקרוא את הקובץ קוד");
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
                Console.printErrorMessage("שגיאה במהלך הניסיון לשמור את קובץ הקוד");
                e.printStackTrace();
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
                Console.printErrorMessage("שגיאה במהלך הניסיון לקרוא את הקובץ קוד");
                e.printStackTrace();
            }
        }
    }

    private static void appendToPane(String msg, Color c)
    {
        StyleConstants.setForeground(style, c);

        try
        {
            doc.insertString(doc.getLength(), msg, style);
        }
        catch (BadLocationException e)
        {
            Console.printErrorMessage("שגיאה במהלך הניסיון להדפיס למסך");
        }
    }
}
