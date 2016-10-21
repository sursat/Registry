package com.registry.cache;

import com.google.inject.Singleton;
import com.registry.entities.ResourceName;

import java.util.HashMap;

/**
 * Created by prasad on 14/10/16.
 */
@Singleton
public class Registry {

    private HashMap<ResourceName,HashMap<String,String>> registeredResources = new HashMap<>();


    public HashMap<ResourceName, HashMap<String,String>> getRegisteredResources() {
        return registeredResources;
    }

    public void setRegisteredResources(HashMap<ResourceName, HashMap<String,String>> registeredResources) {
        this.registeredResources = registeredResources;
    }

    public HashMap<String,String> registerNewResource(ResourceName resourceName, HashMap<String,String> resourceProperties){
        return registeredResources.put(resourceName,resourceProperties);
    }

    public HashMap<String,String> getResourceProperties(ResourceName resourceName){
        return registeredResources.get(resourceName);
    }

    public HashMap<String,String> updateResourceProperties(ResourceName resourceName, HashMap<String,String> resourceNewProperties){
        return registeredResources.replace(resourceName,resourceNewProperties);
    }

    public HashMap<String,String> deRegisterResource(ResourceName resourceName){
        return registeredResources.remove(resourceName);
    }

    public boolean isResourceNotRegistered(ResourceName resourceName){
        return (registeredResources.get(resourceName) == null);
    }
}
