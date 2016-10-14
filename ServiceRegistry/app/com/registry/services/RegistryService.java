package com.registry.services;

import com.google.inject.Inject;
import com.registry.cache.Registry;
import com.registry.common.utils.InputParametersChecker;
import com.registry.common.utils.ResourceNameUtil;
import com.registry.entities.ResourceName;
import com.registry.entities.ResourceProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by prasad on 12/10/16.
 */
public class RegistryService {

    @Inject
    private Registry registry;

    public void registerResource(ResourceName resourceName, ResourceProperties resourceProperties){
        InputParametersChecker.ifNullThrowNullPointerException(resourceName,resourceProperties);
        registry.registerNewResource(resourceName,resourceProperties);
    }

    public List<String> registerMultipleResource(HashMap<String,ResourceProperties> resourcesMap){
        InputParametersChecker.ifNullThrowNullPointerException(resourcesMap);
        List<String> invalidResourceNames = new ArrayList<>();
        resourcesMap.forEach((resourceNameStr, resourceProperties) -> {
            if(ResourceNameUtil.isInvalidResourceName(resourceNameStr)) {
                invalidResourceNames.add(resourceNameStr);
                return;
            }

            ResourceName resourceName = ResourceName.valueOf(resourceNameStr);
            if(registry.isResourceNotRegistered(resourceName))
                registry.registerNewResource(resourceName,resourceProperties);
            else
                registry.updateResourceProperties(resourceName,resourceProperties);
        });
        return invalidResourceNames;
    }

    public ResourceProperties getResourceProperties(ResourceName resourceName){
        InputParametersChecker.ifNullThrowNullPointerException(resourceName);
        return registry.getResourceProperties(resourceName);
    }

    public boolean isResourceNotRegistered(ResourceName resourceName){
        return registry.isResourceNotRegistered(resourceName);
    }

    public ResourceProperties updateResource(ResourceName resourceName, ResourceProperties resourceNewProperties){
        InputParametersChecker.ifNullThrowNullPointerException(resourceName,resourceNewProperties);
        if(registry.isResourceNotRegistered(resourceName))
            return null;
        else
            return registry.updateResourceProperties(resourceName,resourceNewProperties);
    }

    public void deRegisterResource(ResourceName resourceName){
        InputParametersChecker.ifNullThrowNullPointerException(resourceName);
        registry.deRegisterResource(resourceName);
    }

    public HashMap<ResourceName, ResourceProperties> getAllRegisteredResources(){
        return registry.getRegisteredResources();
    }

    public List<String> deRegisterMultipleResources(List<String> resourceNamesList){
        List<String> resourceNamesThatNotFoundToRemove = new ArrayList<>();
        for (String resourceNameString:resourceNamesList) {
            if(ResourceNameUtil.isValidResourceName(resourceNameString)){
                ResourceName resourceName = ResourceName.valueOf(resourceNameString);
                if(isResourceNotRegistered(resourceName))
                    resourceNamesThatNotFoundToRemove.add(resourceName.toString());
                else
                    deRegisterResource(resourceName);
            }
            else
                resourceNamesThatNotFoundToRemove.add(resourceNameString);
        }
        return resourceNamesThatNotFoundToRemove;
    }
}
