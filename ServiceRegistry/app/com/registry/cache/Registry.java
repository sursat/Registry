package com.registry.cache;

import com.google.inject.Singleton;
import com.registry.entities.ResourceName;
import com.registry.entities.ResourceProperties;

import java.util.HashMap;

/**
 * Created by prasad on 14/10/16.
 */
@Singleton
public class Registry {

    private HashMap<ResourceName,ResourceProperties> registeredResources;


    public HashMap<ResourceName, ResourceProperties> getRegisteredResources() {
        return registeredResources;
    }

    public void setRegisteredResources(HashMap<ResourceName, ResourceProperties> registeredResources) {
        this.registeredResources = registeredResources;
    }

    public ResourceProperties registerNewResource(ResourceName resourceName, ResourceProperties resourceProperties){
        return registeredResources.put(resourceName,resourceProperties);
    }

    public ResourceProperties getResourceProperties(ResourceName resourceName){
        return registeredResources.get(resourceName);
    }

    public ResourceProperties updateResourceProperties(ResourceName resourceName, ResourceProperties resourceNewProperties){
        return registeredResources.replace(resourceName,resourceNewProperties);
    }

    public ResourceProperties deRegisterResource(ResourceName resourceName){
        return registeredResources.remove(resourceName);
    }

    public boolean isResourceNotRegistered(ResourceName resourceName){
        return (registeredResources.get(resourceName) == null);
    }
}
