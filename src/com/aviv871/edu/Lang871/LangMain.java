package com.aviv871.edu.Lang871;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LangMain
{
    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter the 871-code file name: ");
        String fileName = s.nextLine();
        System.out.println();
        if(fileName.endsWith(".871"))
            Interpreter.sendCodeToInterpret(readFile(new File(fileName)));
        else
            Interpreter.sendCodeToInterpret(readFile(new File(fileName + ".871")));
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
}
