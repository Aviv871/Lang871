package com.aviv871.edu.Lang871.Utilities;

import com.aviv871.edu.Lang871.Commands.Variable;
import com.aviv871.edu.Lang871.UI.Console;

public class Math
{
    public static double evaluateArithmeticFromString(String str, int lineNumber)
    {
        return new Object()
        {
            int currentPosition = -1, currentChar;

            private void nextChar()
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

            private boolean dealWithChar(int charToEat)
            {
                if(currentChar == charToEat)
                {
                    nextChar();
                    return true;
                }
                return false;
            }

            private double parse()
            {
                nextChar();
                double x = parseExpression();
                if(currentPosition < str.length()) Console.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, תו לא במקום: " + (char) currentChar + " - בשורה " + lineNumber);
                return x;
            }

            // The Grammar:
            // Expression = term || expression `+` term || expression `-` term
            // Term = factor || term `*` factor || term `/` factor
            // Factor = `+` factor || `-` factor || `(` expression `)` || number || functionName factor || factor `^` factor

            private double parseExpression()
            {
                double x = parseTerm();
                while(true)
                {
                    if(dealWithChar('+')) x += parseTerm(); // Addition
                    else if(dealWithChar('-')) x -= parseTerm(); // Subtraction
                    else return x;
                }
            }

            private double parseTerm()
            {
                double x = parseFactor();
                while(true)
                {
                    if(dealWithChar('*')) x *= parseFactor(); // Multiplication
                    else if(dealWithChar('/')) x /= parseFactor(); // Division
                    else return x;
                }
            }

            private double parseFactor()
            {
                if(dealWithChar('+')) return parseFactor(); // Unary plus
                if(dealWithChar('-')) return -parseFactor(); // Unary minus

                double x = 0;
                int startPos = this.currentPosition;
                if(dealWithChar('(')) // Parentheses
                {
                    x = parseExpression();
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

                    for(String varName: Variable.getVariablesNames()) // Variable
                    {
                        if(func.equals(varName))
                        {
                            if(Variable.getAVariableValue(func) instanceof Boolean || Variable.getAVariableValue(func) instanceof String) Console.printErrorMessage("שגיאה בסוג המשתנה במשתנה: " + func + " - בשורה " + lineNumber);
                            x = Double.parseDouble(Variable.getAVariableValue(func).toString());
                            return x;
                        }
                    }

                    x = parseFactor();
                    if (func.equals("שורש")) x = java.lang.Math.sqrt(x);
                    else if (func.equals("סינוס")) x = java.lang.Math.sin(java.lang.Math.toRadians(x));
                    else if (func.equals("קוסינוס")) x = java.lang.Math.cos(java.lang.Math.toRadians(x));
                    else if (func.equals("טנגנס")) x = java.lang.Math.tan(java.lang.Math.toRadians(x));
                    else Console.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, פונקציה לא ידועה: " + func + " - בשורה " + lineNumber);
                }
                else
                {
                    Console.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, תו לא במקום: " + (char) currentChar + " - בשורה " + lineNumber);
                }

                if (dealWithChar('^')) x = java.lang.Math.pow(x, parseFactor()); // Exponentiation

                return x;
            }
        }.parse();
    }
}
