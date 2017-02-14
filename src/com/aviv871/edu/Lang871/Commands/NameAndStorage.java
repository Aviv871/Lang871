package com.aviv871.edu.Lang871.Commands;

import com.aviv871.edu.Lang871.References.LangKeyWords;

import java.util.HashMap;

public abstract class NameAndStorage
{
    protected static boolean isNameValid(String name)
    {
        if(name.charAt(0) < 'א' || name.charAt(0) > 'ת') return false;
        for(char c: name.toCharArray())
        {
            if((c < 'א' || c > 'ת') && (c < '0' || c > '9')) return false;
        }

        for(LangKeyWords keyWord: LangKeyWords.values()) // Command names that are already taken
        {
            if(name.equals(keyWord.get871Code()))
            {
                return false;
            }
        }

        for(String varName: Variable.getVariablesNames()) // Variable names that are already taken
        {
            if(name.equals(varName))
            {
                return false;
            }
        }

        for(String funcName: Function.getFunctionsNames()) // Functions names that are already taken
        {
            if(name.equals(funcName))
            {
                return false;
            }
        }

        return true;
    }

    protected static boolean doesThisNameExistInStorage(HashMap<String, ?> storage, String name)
    {
        for(String funcName: storage.keySet())
        {
            if(name.equals(funcName)) return true;
        }
        return false;
    }
}
