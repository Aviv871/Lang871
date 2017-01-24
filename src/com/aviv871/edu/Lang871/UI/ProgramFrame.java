package com.aviv871.edu.Lang871.UI;

import com.aviv871.edu.Lang871.LangMain;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ProgramFrame extends JFrame
{
    private static JTextArea textArea;

    private static JButton buttonStart = new JButton("הרץ");
    private static JButton buttonClear = new JButton("נקה");
    private static JButton buttonLoad = new JButton("בחר קובץ");

    public ProgramFrame()
    {
        super("Lang871 Interpreter");

        textArea = new JTextArea(50, 10);
        textArea.setEditable(false);
        textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        //textArea.setFont(new Font("Dialog", Font.PLAIN, 12));
        Console console = new Console(textArea);

        // Creates the GUI
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;

        add(buttonStart, constraints);

        constraints.gridx = 1;
        add(buttonClear, constraints);

        constraints.gridx = 2;
        add(buttonLoad, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        add(new JScrollPane(textArea), constraints);

        // Adds event handler for the Start button
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                clearTheTextArea(textArea);
                LangMain.interpretFile();
            }
        });

        // Adds event handler for the Clear button
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                clearTheTextArea(textArea);
            }
        });

        // Adds event handler for the Load button
        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                LangMain.setCodeFile(openFile());
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);    // centers on screen
    }

    private static void clearTheTextArea(JTextArea area)
    {
        try
        {
            area.getDocument().remove(0, area.getDocument().getLength());
        }
        catch (BadLocationException ex)
        {
            ex.printStackTrace();
        }
    }

    private static File openFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\גלעד\\Desktop\\אביב\\מסמכים\\תכנות\\Java\\src\\com\\aviv871\\edu\\Lang871")); // TODO: change to desktop and save the last location the user used

        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            return fileChooser.getSelectedFile();
        }

        return null;
    }
}
