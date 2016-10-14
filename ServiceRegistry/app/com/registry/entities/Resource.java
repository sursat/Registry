package com.registry.entities;

import java.util.HashMap;

/**
 * Created by egnaro on 14/10/16.
 */
public class Resource {

    private ResourceName resourceName;

    private HashMap<String,String> resourceProperties;


    public ResourceName getResourceName() {
        return resourceName;
    }

    public void setResourceName(ResourceName resourceName) {
        this.resourceName = resourceName;
    }

    public HashMap<String, String> getResourceProperties() {
        return resourceProperties;
    }

    public void setResourceProperties(HashMap<String, String> resourceProperties) {
        this.resourceProperties = resourceProperties;
    }
}
