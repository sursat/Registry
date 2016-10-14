package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.registry.common.constants.AppConstants;
import com.registry.common.utils.InputParametersChecker;
import com.registry.common.utils.ResourceNameUtil;
import com.registry.entities.ResourceName;
import com.registry.entities.ResourceProperties;
import com.registry.services.RegistryService;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeController extends Controller {

    @Inject
    private RegistryService registryService;

    @Inject
    private Gson gson;

    public Result index() {
        return ok(AppConstants.CURRENT_VERSION);
    }

    public Result registerResource(String resourceNameString){
        if(ResourceNameUtil.isInvalidResourceName(resourceNameString))
            return badRequest();
        JsonNode resourcePropertiesJson = request().body().asJson();
        InputParametersChecker.ifNullThrowNullPointerException(resourceNameString,resourcePropertiesJson);
        ResourceName resourceName = ResourceName.valueOf(resourceNameString);
        ResourceProperties resourceProperties = gson.fromJson(resourcePropertiesJson.toString(), ResourceProperties.class);
        InputParametersChecker.ifNullThrowNullPointerException(resourceName,resourceProperties);
        registryService.registerResource(resourceName,resourceProperties);
        return ok();
    }

    public Result registerMultipleResource(){
        JsonNode resourcesMapJson = request().body().asJson();
        InputParametersChecker.ifNullThrowNullPointerException(resourcesMapJson);
        HashMap<String,ResourceProperties> resourcesMap= gson.fromJson(resourcesMapJson.toString(), HashMap.class);
        InputParametersChecker.ifNullThrowNullPointerException(resourcesMap);
        List<String> invalidResourceNamesThatNotRegistered = registryService.registerMultipleResource(resourcesMap);
        return ok(gson.toJson(invalidResourceNamesThatNotRegistered));
    }

    public Result getResourceProperties(String resourceNameString){
        if(ResourceNameUtil.isInvalidResourceName(resourceNameString))
            return badRequest();
        ResourceName resourceName = ResourceName.valueOf(resourceNameString);
        ResourceProperties resourceProperties = registryService.getResourceProperties(resourceName);
        if(resourceProperties == null)
            return notFound();
        return ok(gson.toJson(resourceProperties));
    }

    public Result getAllRegisteredResources(){
        HashMap<ResourceName, ResourceProperties> allRegisteredResources = registryService.getAllRegisteredResources();
        if(allRegisteredResources == null)
            return ok(Json.newObject());
        return ok(gson.toJson(allRegisteredResources));
    }

    public Result updateregisteredResourceProperties(String resourceNameString){
        if(ResourceNameUtil.isInvalidResourceName(resourceNameString))
            return badRequest();
        JsonNode resourcePropertiesJson = request().body().asJson();
        InputParametersChecker.ifNullThrowNullPointerException(resourceNameString,resourcePropertiesJson);
        ResourceName resourceName = ResourceName.valueOf(resourceNameString);
        ResourceProperties resourceNewProperties = gson.fromJson(resourcePropertiesJson.toString(), ResourceProperties.class);
        InputParametersChecker.ifNullThrowNullPointerException(resourceName,resourceNewProperties);
        if(registryService.isResourceNotRegistered(resourceName))
            return notFound();
        registryService.updateResource(resourceName,resourceNewProperties);
        return ok();
    }

    public Result removeRegisteredResource(String resourceNameString){
        if(ResourceNameUtil.isInvalidResourceName(resourceNameString))
            return badRequest();
        ResourceName resourceName = ResourceName.valueOf(resourceNameString);
        if(registryService.isResourceNotRegistered(resourceName))
            return notFound();
        else
            registryService.deRegisterResource(resourceName);
            return ok();
    }

    public Result removeMultipleRegisteredResource(){
        JsonNode resourceNamesListJSON = request().body().asJson();
        InputParametersChecker.ifNullThrowNullPointerException(resourceNamesListJSON);
        List<String> resourceNamesList = gson.fromJson(resourceNamesListJSON.toString(),ArrayList.class);
        List<String> resourceNamesThatNotFoundToRemove = registryService.deRegisterMultipleResources(resourceNamesList);
        return ok(gson.toJson(resourceNamesThatNotFoundToRemove));
    }
}
