package com.registry.common.utils;

import java.util.Objects;

/**
 * Created by prasad on 14/10/16.
 */
public class InputParametersChecker {

    public static boolean isNullObject(Object ...objects){
        for (Object currentObject:objects) {
            if(objects == null)
                return true;
        }
        return false;
    }

    public static void ifNullThrowNullPointerException(Object ...objects){
        if(isNullObject(objects))
            throw new NullPointerException();
    }
}
