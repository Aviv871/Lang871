package com.aviv871.edu.Lang871.UI;

import com.aviv871.edu.Lang871.LangMain;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ProgramFrame extends JFrame
{
    private static JTextPane consoleArea;
    private static JTextPane codeArea;

    private static JButton buttonStart = new JButton("הרץ קוד");
    private static JButton buttonClear = new JButton("נקה פלט");
    private static JButton buttonLoad = new JButton("בחר קובץ");
    private static JButton buttonSave = new JButton("שמור");
    private static JButton buttonSaveAs = new JButton("שמור בשם");

    private static JLabel codeHead = new JLabel("קוד:");
    private static JLabel consoleHead = new JLabel("פלט:");

    public ProgramFrame()
    {
        super("Lang871 Interpreter");

        consoleArea = new JTextPane();
        consoleArea.setEditable(false);
        consoleArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        UIManager.consoleInstance.setTextArea(consoleArea);

        codeArea = new JTextPane();
        codeArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        UIManager.codeEditorInstance.setTextArea(codeArea);

        // Setting the code editor document filter
        AbstractDocument document = (AbstractDocument) codeArea.getDocument();
        document.setDocumentFilter(new CodeFilter());

        // Creates the GUI
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;

        // Buttons
        add(buttonStart, constraints);

        constraints.gridx = 1;
        add(buttonClear, constraints);

        constraints.gridx = 2;
        add(buttonLoad, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(buttonSave, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        add(buttonSaveAs, constraints);

        // Text Areas
        constraints.gridx = 2;
        constraints.gridy = 2;
        add(codeHead, constraints);

        constraints.gridx = 2;
        constraints.gridy = 3;
        add(consoleHead, constraints);

        // Scroll
        JScrollPane scrollPane = new JScrollPane(codeArea);
        scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(scrollPane, constraints);

        scrollPane = new JScrollPane(consoleArea);
        scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        constraints.gridy = 3;
        add(scrollPane, constraints);

        // Adds event handler for the Start button
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                UIManager.consoleInstance.clearTheTextArea();
                LangMain.interpretFile();
            }
        });

        // Adds event handler for the Clear button
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                UIManager.consoleInstance.clearTheTextArea();            }
        });

        // Adds event handler for the Load button
        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                UIManager.codeEditorInstance.loadCodeFile(openFile());
            }
        });

        // Adds event handler for the Save button
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                UIManager.codeEditorInstance.saveFile();
            }
        });

        // Adds event handler for the Save-As button
        buttonSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                UIManager.codeEditorInstance.saveFileAs();
            }
        });

        // Adds handler for drag-and-drop event, for dropping code files on the code editor
        codeArea.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent event)
            {
                try
                {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if(droppedFiles.size() == 1)
                    {
                        if(droppedFiles.get(0).getName().endsWith(".871")) UIManager.codeEditorInstance.loadCodeFile(droppedFiles.get(0));
                        else UIManager.consoleInstance.printErrorMessage("זהו לא קובץ קוד 871. !");
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        // Adds event handler for the code editor
        //codeArea.getDocument().addDocumentListener(new CodeListener());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);    // centers on screen
    }

    private static File openFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Lang871 Code Files", "871")); // Setting file type filter
        fileChooser.setCurrentDirectory(new File("C:\\Users\\גלעד\\Desktop\\אביב\\מסמכים\\תכנות\\Java\\src\\com\\aviv871\\edu\\Lang871")); // TODO: change to desktop and save the last location the user used

        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            if(fileChooser.getSelectedFile().getName().endsWith(".871")) return fileChooser.getSelectedFile();
            else UIManager.consoleInstance.printErrorMessage("זהו לא קובץ קוד 871. !");
        }

        return null;
    }
}
