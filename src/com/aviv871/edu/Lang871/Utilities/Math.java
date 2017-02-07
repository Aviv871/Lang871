package com.aviv871.edu.Lang871.Utilities;

import com.aviv871.edu.Lang871.Commands.Variable;
import com.aviv871.edu.Lang871.UI.UIManager;

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
                if(currentPosition < str.length()) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, תו לא במקום: " + (char) currentChar + " - בשורה " + lineNumber, lineNumber);
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

                    for(String varName: Variable.getVariablesNames()) // Variables
                    {
                        if(func.equals(varName))
                        {
                            if(Variable.getAVariableValue(func) instanceof Boolean || Variable.getAVariableValue(func) instanceof String) UIManager.consoleInstance.printErrorMessage("שגיאה בסוג המשתנה במשתנה: " + func + " - בשורה " + lineNumber, lineNumber);
                            x = Double.parseDouble(Variable.getAVariableValue(func).toString());
                            return x;
                        }
                    }

                    // Functions
                    x = parseFactor();
                    if (func.equals("שורש")) x = java.lang.Math.sqrt(x);
                    else if (func.equals("סינוס")) x = java.lang.Math.sin(java.lang.Math.toRadians(x));
                    else if (func.equals("קוסינוס")) x = java.lang.Math.cos(java.lang.Math.toRadians(x));
                    else if (func.equals("טנגנס")) x = java.lang.Math.tan(java.lang.Math.toRadians(x));
                    else UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, פונקציה לא ידועה: " + func + " - בשורה " + lineNumber, lineNumber);
                }
                else
                {
                    UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, תו לא במקום: " + (char) currentChar + " - בשורה " + lineNumber, lineNumber);
                }

                if (dealWithChar('^')) x = java.lang.Math.pow(x, parseFactor()); // Exponentiation

                return x;
            }
        }.parse();
    }

    public static boolean evaluateBooleanAlgebraFromString(String str, int lineNumber)
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

            private boolean dealWithDoubleChar(int charToEat1, int charToEat2)
            {
                if(currentChar == charToEat1)
                {
                    if(str.charAt(currentPosition + 1) == charToEat2)
                    {
                        nextChar();
                        nextChar();
                        return true;
                    }
                }
                return false;
            }

            private boolean parse()
            {
                nextChar();
                boolean x = parseExpression();
                if(currentPosition < str.length()) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, תו לא במקום: " + (char) currentChar + " - בשורה " + lineNumber, lineNumber);
                return x;
            }

            // The Grammar:
            // Expression = term || expression `&&` term || expression `||` term
            // Term = factor || term `==` factor || term `<` factor ....
            // Factor = (` expression `)` || number || string || boolean || variables || true\false

            private boolean parseExpression()
            {
                boolean x = parseTerm();
                while(true)
                {
                    if(dealWithDoubleChar('|', '|')) // Or
                    {
                        boolean a = parseTerm(); // If the first side is false he will not check the second and it will trow an error because not all the string was considered
                        x = x || a;
                    }
                    else if(dealWithDoubleChar('&', '&')) x = x && parseTerm(); // And
                    else return x;
                }
            }

            private boolean parseTerm()
            {
                double x = parseExpression_Numbers();
                boolean y = false;
                while(true)
                {
                    if(dealWithDoubleChar('=', '=')) y = x == parseExpression_Numbers(); // Equal
                    else if(dealWithDoubleChar('!', '=')) y = x != parseExpression_Numbers(); // Not-Equal
                    else if(dealWithDoubleChar('<', '=')) y = x != parseExpression_Numbers(); // Smaller or equal
                    else if(dealWithDoubleChar('>', '=')) y = x != parseExpression_Numbers(); // Bigger or equal
                    else if(dealWithChar('<')) y = x != parseExpression_Numbers(); // Smaller
                    else if(dealWithChar('>')) y = x != parseExpression_Numbers(); // Bigger
                    else return y;
                }
            }

            private boolean parseBooleanFactor()
            {
                return false;
            }

            private String parseStringFactor()
            {
                return "";
            }

            /////////////////////////////////////////////////////////////////////////////////////

            private double parseExpression_Numbers()
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
                            if(Variable.getAVariableValue(func) instanceof Boolean || Variable.getAVariableValue(func) instanceof String) UIManager.consoleInstance.printErrorMessage("שגיאה בסוג המשתנה במשתנה: " + func + " - בשורה " + lineNumber, lineNumber);
                            x = Double.parseDouble(Variable.getAVariableValue(func).toString());
                            return x;
                        }
                    }

                    // Functions
                    x = parseFactor_Numbers();
                    if (func.equals("שורש")) x = java.lang.Math.sqrt(x);
                    else if (func.equals("סינוס")) x = java.lang.Math.sin(java.lang.Math.toRadians(x));
                    else if (func.equals("קוסינוס")) x = java.lang.Math.cos(java.lang.Math.toRadians(x));
                    else if (func.equals("טנגנס")) x = java.lang.Math.tan(java.lang.Math.toRadians(x));
                    else UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, פונקציה לא ידועה: " + func + " - בשורה " + lineNumber, lineNumber);
                }
                else
                {
                    UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, תו לא במקום: " + (char) currentChar + " - בשורה " + lineNumber, lineNumber);
                }

                if (dealWithChar('^')) x = java.lang.Math.pow(x, parseFactor_Numbers()); // Exponentiation

                return x;
            }
        }.parse();
    }
}
