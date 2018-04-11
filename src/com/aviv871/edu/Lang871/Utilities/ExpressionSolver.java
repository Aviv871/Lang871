package com.aviv871.edu.Lang871.Utilities;

import com.aviv871.edu.Lang871.Commands.Variable;
import com.aviv871.edu.Lang871.UI.UIManager;

public abstract class ExpressionSolver
{
    protected int currentPosition = -1, currentChar, lineNumber;
    protected String str;
    protected boolean errorFlag = false;

    protected ExpressionSolver(String str, int lineNumber)
    {
        this.lineNumber = lineNumber;
        this.str = str;
    }

    public abstract Object getResult();

    protected void nextChar()
    {
        if(++currentPosition < str.length())
        {
            currentChar = str.charAt(currentPosition);
        }
        else
        {
            currentChar = -1;
        }
    }

    protected boolean dealWithChar(int charToEat)
    {
        if(currentChar == charToEat)
        {
            nextChar();
            return true;
        }
        return false;
    }

    protected boolean dealWithDoubleChar(int charToEat1, int charToEat2)
    {
        if(this.currentChar == charToEat1)
        {
            if(this.str.charAt(this.currentPosition + 1) == charToEat2)
            {
                nextChar();
                nextChar();
                return true;
            }
        }
        return false;
    }

    // The Grammar:
    // Expression = term || expression `+` term || expression `-` term
    // Term = factor || term `*` factor || term `/` factor
    // Factor = `+` factor || `-` factor || `(` expression `)` || number || functionName factor || factor `^` factor

    protected double parseExpression_Numbers()
    {
        double x = parseTerm_Numbers();
        while(true)
        {
            if(dealWithChar('+')) x += parseTerm_Numbers(); // Addition
            else if(dealWithChar('-')) x -= parseTerm_Numbers(); // Subtraction
            else return x;
        }
    }

    private double parseTerm_Numbers()
    {
        double x = parseFactor_Numbers();
        while(true)
        {
            if(dealWithChar('*')) x *= parseFactor_Numbers(); // Multiplication
            else if(dealWithChar('/')) x /= parseFactor_Numbers(); // Division
            else return x;
        }
    }

    private double parseFactor_Numbers()
    {
        if(dealWithChar('+')) return parseFactor_Numbers(); // Unary plus
        if(dealWithChar('-')) return -parseFactor_Numbers(); // Unary minus

        double x = 0;
        int startPos = this.currentPosition;
        if(dealWithChar('(')) // Parentheses
        {
            x = parseExpression_Numbers();
            dealWithChar(')');
        }
        else if((currentChar >= '0' && currentChar <= '9') || currentChar == '.') // Numbers
        {
            while ((currentChar >= '0' && currentChar <= '9') || currentChar == '.') nextChar();
            x = Double.parseDouble(str.substring(startPos, this.currentPosition));
        }
        else if((currentChar >= 'א' && currentChar <= 'ת')) // Functions and Variables
        {
            while ((currentChar >= 'א' && currentChar <= 'ת') || (currentChar >= '0' && currentChar <= '9')) nextChar();
            String func = str.substring(startPos, this.currentPosition);

            for(String varName: Variable.getVariablesNames()) // Variables
            {
                if(func.equals(varName))
                {
                    if(Variable.getAVariableValue(func) instanceof Boolean || Variable.getAVariableValue(func) instanceof String)
                    {
                        errorFlag = true;
                        return 0;
                    }

                    if(Variable.getAVariableValue(func) instanceof Object[]) // Array variable
                    {
                        Object[] theArray = (Object[]) Variable.getAVariableValue(func);
                        if(dealWithChar('[')) // Parentheses
                        {
                            System.out.println("1");
                            int index = (int) parseExpression_Numbers();
                            if(index > theArray.length - 1 || index < 0) UIManager.consoleInstance.printErrorMessage("מערך חרג מהתחום" + " - בשורה " + lineNumber, lineNumber);
                            if(!dealWithChar(']')) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של המערך" + " - בשורה " + lineNumber, lineNumber);
                            if(theArray[index] instanceof Double)
                            {
                                System.out.println("2");
                                x = (double) theArray[index];
                                return x;
                            }
                            else
                            {
                                errorFlag = true;
                                return 0;
                            }
                        }
                    }

                    x = (double) Variable.getAVariableValue(func);
                    return x;
                }
            }

            // Functions
            if (func.equals("שורש")) x = java.lang.Math.sqrt(getFunctionInput());
            else if (func.equals("סינוס")) x = java.lang.Math.sin(java.lang.Math.toRadians(getFunctionInput()));
            else if (func.equals("קוסינוס")) x = java.lang.Math.cos(java.lang.Math.toRadians(getFunctionInput()));
            else if (func.equals("טנגנס")) x = java.lang.Math.tan(java.lang.Math.toRadians(getFunctionInput()));
            else errorFlag = true;
        }
        else
        {
            errorFlag = true;
            return 0;
        }

        if (dealWithChar('^')) x = java.lang.Math.pow(x, parseFactor_Numbers()); // Exponentiation

        return x;
    }

    private double getFunctionInput()
    {
        if(dealWithChar('('))
        {
            double x = parseExpression_Numbers();
            if(!dealWithChar(')')) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפונקציה המתמטית" + " - בשורה " + lineNumber, lineNumber);

            return x;
        }
        else
        {
            UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפונקציה המתמטית" + " - בשורה " + lineNumber, lineNumber);
            return 0;
        }
    }
}
