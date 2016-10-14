package com.registry.common.utils;

import com.registry.entities.ResourceName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prasad on 14/10/16.
 */
public class ResourceNameUtil {

    private static List<String> resourceNameList = null;

    private static void initResourceNameList(){
        if(resourceNameList == null){
            resourceNameList = new ArrayList<>();
            for (ResourceName resourceName : ResourceName.values()) {
                resourceNameList.add(resourceName.toString().toUpperCase());
            }
        }
    }

    public static boolean isValidResourceName(String resourceName){
        InputParametersChecker.ifNullThrowNullPointerException(resourceName);
        initResourceNameList();
        return resourceNameList.contains(resourceName.toUpperCase());
    }

    public static boolean isInvalidResourceName(String resourceName){
        return !(isValidResourceName(resourceName));
    }
}
