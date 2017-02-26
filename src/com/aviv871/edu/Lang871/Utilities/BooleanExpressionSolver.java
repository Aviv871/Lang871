package com.aviv871.edu.Lang871.Utilities;

import com.aviv871.edu.Lang871.Commands.Variable;
import com.aviv871.edu.Lang871.UI.UIManager;

public class BooleanExpressionSolver extends ExpressionSolver
{
    private boolean tempValueHolder;

    public BooleanExpressionSolver(String str, int lineNumber)
    {
        super(str, lineNumber);
    }

    @Override
    public Object getResult()
    {
        nextChar();
        boolean x = parseExpression_Boolean();
        if(currentPosition < str.length()) UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, תו לא במקום: " + (char) currentChar + " - בשורה " + lineNumber, lineNumber);
        return x;
    }

    // The Grammar:
    // Expression = term || expression `&&` term || expression `||` term
    // Term = factor || term `==` factor || term `<` factor ....
    // Factor = (` expression `)` || number || string || boolean || globalVariables || true\false

    private boolean parseExpression_Boolean()
    {
        boolean x = parseTerm_Boolean();
        while(true)
        {
            if(dealWithDoubleChar('|', '|')) // Or
            {
                boolean a = parseTerm_Boolean(); // If the first side is true he will not check the second and it will trow an error because not all the string was considered
                x = x || a;
            }
            else if(dealWithDoubleChar('&', '&')) // And
            {
                boolean a = parseTerm_Boolean(); // If the first side is false he will not check the second and it will trow an error because not all the string was considered
                x = x && a;
            }
            else return x;
        }
    }

    private boolean parseTerm_Boolean()
    {
        if(parseBooleanFactor()) return tempValueHolder; // Check for boolean globalVariables, word and parentheses
        if(parseStringComparison()) return tempValueHolder; // Check for string == string or !=

        double x = parseExpression_Numbers();
        if(dealWithDoubleChar('=', '=')) return x == parseExpression_Numbers(); // Equal
        else if(dealWithDoubleChar('!', '=')) return x != parseExpression_Numbers(); // Not-Equal
        else if(dealWithDoubleChar('<', '=')) return x <= parseExpression_Numbers(); // Smaller or equal
        else if(dealWithDoubleChar('>', '=')) return x >= parseExpression_Numbers(); // Bigger or equal
        else if(dealWithChar('<')) return x < parseExpression_Numbers(); // Smaller
        else if(dealWithChar('>')) return x > parseExpression_Numbers(); // Bigger
        else UIManager.consoleInstance.printErrorMessage("שגיאה עם הפרמטרים של הפקודה, השוואה לא חוקית" + (char) currentChar + " - בשורה " + lineNumber, lineNumber);
        return false;
    }

    private boolean parseBooleanFactor() // Return true if parsing succeed - the parsing itself is stored in the tempValueHolder
    {
        int startPos = this.currentPosition;
        if(dealWithChar('(')) // Parentheses
        {
            tempValueHolder = parseExpression_Boolean();
            dealWithChar(')');
        }
        else if((currentChar >= 'א' && currentChar <= 'ת')) // Words and Variables
        {
            while ((currentChar >= 'א' && currentChar <= 'ת') || (currentChar >= '0' && currentChar <= '9')) nextChar();
            String word = str.substring(startPos, this.currentPosition);

            for(String varName: Variable.getVariablesNames()) // Variables
            {
                if(word.equals(varName))
                {
                    if(Variable.getAVariableValue(word) instanceof Double || Variable.getAVariableValue(word) instanceof String)
                    {
                        currentPosition = startPos;
                        currentChar = str.charAt(currentPosition);
                        return false;
                    }

                    tempValueHolder = (boolean) Variable.getAVariableValue(word);
                    return true;
                }
            }

            // Words
            if (word.equals("אמת")) tempValueHolder = true;
            else if (word.equals("שקר")) tempValueHolder = false;
            else
            {
                currentPosition = startPos;
                currentChar = str.charAt(currentPosition);
                return false;
            }
        }
        else
        {
            currentPosition = startPos;
            currentChar = str.charAt(currentPosition);
            return false;
        }

        return true;
    }

    private boolean parseStringComparison() // Return true if parsing succeed - the parsing itself is stored in the tempValueHolder
    {
        int startPos = this.currentPosition;
        boolean comparisonType; // false == , true !=

        String a = getAStringForComparison(this.currentPosition);
        if(!tempValueHolder) return false;

        if(dealWithDoubleChar('=', '=')) comparisonType = false;
        else if(dealWithDoubleChar('!', '=')) comparisonType = true;
        else
        {
            currentPosition = startPos;
            currentChar = str.charAt(currentPosition);
            return false;
        }

        String b = getAStringForComparison(this.currentPosition);
        if(!tempValueHolder) return false;

        if(!comparisonType) tempValueHolder = a.equals(b);
        else tempValueHolder = !a.equals(b);
        return true;
    }

    private String getAStringForComparison(int startPos) // tempValueHolder store here succeed or failure
    {
        if(dealWithChar('"')) // Quote
        {
            while (currentChar != '"') nextChar(); // TODO: make sure there is second " and that the quote is not empty
            tempValueHolder = true;
            nextChar();
            return str.substring(startPos + 1, this.currentPosition - 1);
        }
        else if((currentChar >= 'א' && currentChar <= 'ת')) // Variables
        {
            while ((currentChar >= 'א' && currentChar <= 'ת') || (currentChar >= '0' && currentChar <= '9')) nextChar();
            String word = str.substring(startPos, this.currentPosition);

            for(String varName: Variable.getVariablesNames())
            {
                if(word.equals(varName))
                {
                    if(Variable.getAVariableValue(word) instanceof Double || Variable.getAVariableValue(word) instanceof Boolean)
                    {
                        currentPosition = startPos;
                        currentChar = str.charAt(currentPosition);
                        tempValueHolder = false;
                        return "";
                    }

                    tempValueHolder = true;
                    return (String) Variable.getAVariableValue(word);
                }
            }
        }

        return "";
    }
}
