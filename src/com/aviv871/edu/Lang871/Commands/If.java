package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.Interpreter;
import com.aviv871.edu.Lang871.Utilities.Math;

public class If implements ICommand
{
    private static final String code871 = "אם";

    @Override
    public String get871Code()
    {
        return code871;
    }

    @Override
    public void sendParameters(String par, int lineNumber)
    {
        if(!par.contains(" אז ")) throw new RuntimeException("Error with command parameters, missing 'אז', in line: " + lineNumber);
        String condition = par.substring(0, par.indexOf(" אז "));
        String command = par.substring(par.indexOf(" אז ") + 4);

        if(checkStringCondition(condition, lineNumber)) Interpreter.initiateLine(command, lineNumber);
    }

    private boolean checkStringCondition(String con, int lineNumber)
    {
        con = con.replaceAll("\\s",""); // Remove all whitespaces
        String par1, par2, compareType;
        double value1, value2;
        char currentChar = con.charAt(0);
        short currentPosition = 0, startPosition = 0;

        if((currentChar >= 'א' && currentChar <= 'ת') || (currentChar >= '0' && currentChar <= '9')) // Finding The First Variable\Number\Function
        {
            while ((currentChar >= 'א' && currentChar <= 'ת') || (currentChar >= '0' && currentChar <= '9'))
            {
                if(++currentPosition < con.length())
                {
                    currentChar = con.charAt(currentPosition);
                }
                else
                {
                    break;
                }
            }
            par1 = con.substring(0, currentPosition);
        }
        else
        {
            throw new RuntimeException("Error with command parameters, non-valid comparison, in line: " + lineNumber);
        }

        startPosition = currentPosition;
        if(con.charAt(currentPosition) == '=' || con.charAt(currentPosition) == '!' || con.charAt(currentPosition) == '<' || con.charAt(currentPosition) == '>') // Finding the comparison type
        {
            currentPosition++;
            if(con.charAt(currentPosition) == '=' || con.charAt(currentPosition) == '>' || con.charAt(currentPosition) == '<')
            {
                currentPosition++;
                compareType = con.substring(startPosition, currentPosition);
            }
            else
            {
                compareType = con.substring(startPosition, currentPosition);
            }
        }
        else
        {
            throw new RuntimeException("Error with command parameters, non-valid comparison, in line: " + lineNumber);
        }

        startPosition = currentPosition;
        currentChar = con.charAt(currentPosition);
        if((currentChar >= 'א' && currentChar <= 'ת') || (currentChar >= '0' && currentChar <= '9')) // Finding The Second Variable\Number\Function
        {
            while ((currentChar >= 'א' && currentChar <= 'ת') || (currentChar >= '0' && currentChar <= '9'))
            {
                if(++currentPosition < con.length())
                {
                    currentChar = con.charAt(currentPosition);
                }
                else
                {
                    break;
                }
            }
            par2 = con.substring(startPosition, currentPosition);
        }
        else
        {
            throw new RuntimeException("Error with command parameters, non-valid comparison, in line: " + lineNumber);
        }

        value1 = Math.evaluateArithmeticFromString(par1, lineNumber);
        value2 = Math.evaluateArithmeticFromString(par2, lineNumber);
        switch (compareType)
        {
            case "==":
                return value1 == value2;
            case "!=":
                return value1 != value2;
            case "<":
                return value1 < value2;
            case ">":
                return value1 > value2;
            case "<=":
                return value1 <= value2;
            case ">=":
                return value1 >= value2;
            default:
                throw new RuntimeException("Error with command parameters, non-valid comparison, in line: " + lineNumber);
        }

        // TODO: Add string and boolean comparison or single boolean variable
    }
}
