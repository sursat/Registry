package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.registry.common.constants.AppConstants;
import com.registry.common.utils.FileWorker;
import com.registry.common.utils.InputParametersChecker;
import com.registry.common.utils.ResourceNameUtil;
import com.registry.common.utils.WSHelper;
import com.registry.entities.ResourceName;
import com.registry.services.RegistryService;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeController extends Controller {

    @Inject
    private RegistryService registryService;

    @Inject
    private Gson gson;

    @Inject
    private WSHelper wsHelper;

    public Result index() {
        return ok(AppConstants.CURRENT_VERSION);
    }

    public Result registerResource(String resourceNameString){
        if(ResourceNameUtil.isInvalidResourceName(resourceNameString))
            return badRequest();
        JsonNode resourcePropertiesJson = request().body().asJson();
        InputParametersChecker.ifNullThrowNullPointerException(resourceNameString,resourcePropertiesJson);
        ResourceName resourceName = ResourceName.valueOf(resourceNameString);
        HashMap<String,String> resourceProperties = gson.fromJson(resourcePropertiesJson.toString(), new TypeToken<HashMap<String, String>>() {}.getType());
        InputParametersChecker.ifNullThrowNullPointerException(resourceName,resourceProperties);
        registryService.registerResource(resourceName,resourceProperties);
        return ok();
    }

    public Result registerMultipleResource(){
        JsonNode resourcesMapJson = request().body().asJson();
        InputParametersChecker.ifNullThrowNullPointerException(resourcesMapJson);
        HashMap<String,HashMap<String,String>> resourcesMap= gson.fromJson(resourcesMapJson.toString(), new TypeToken<HashMap<String, HashMap<String,String>>>() {}.getType());
        InputParametersChecker.ifNullThrowNullPointerException(resourcesMap);
        List<String> invalidResourceNamesThatNotRegistered = registryService.registerMultipleResource(resourcesMap);
        return ok(gson.toJson(invalidResourceNamesThatNotRegistered));
    }

    public Result getResourceProperties(String resourceNameString){
        if(ResourceNameUtil.isInvalidResourceName(resourceNameString))
            return badRequest();
        ResourceName resourceName = ResourceName.valueOf(resourceNameString);
        HashMap<String,String> resourceProperties = registryService.getResourceProperties(resourceName);
        if(resourceProperties == null)
            return notFound();
        return ok(gson.toJson(resourceProperties));
    }

    public Result getAllRegisteredResources(){
        HashMap<ResourceName, HashMap<String,String>> allRegisteredResources = registryService.getAllRegisteredResources();
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
        HashMap<String,String> resourceNewProperties = gson.fromJson(resourcePropertiesJson.toString(), new TypeToken<HashMap<String, String>>() {}.getType());
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

    //@BodyParser over-rides the default behaviour of play to discard DELETE  REST body
    //This is useful to access DELETE REST call body.
    @BodyParser.Of(BodyParser.Json.class)
    public Result removeMultipleRegisteredResource(){
        JsonNode resourceNamesListJSON = request().body().asJson();
        InputParametersChecker.ifNullThrowNullPointerException(resourceNamesListJSON);
        List<String> resourceNamesList = gson.fromJson(resourceNamesListJSON.toString(),new TypeToken<ArrayList<String>>() {}.getType());
        List<String> resourceNamesThatNotFoundToRemove = registryService.deRegisterMultipleResources(resourceNamesList);
        return ok(gson.toJson(resourceNamesThatNotFoundToRemove));
    }

    public Result doAutomatterSignUP() throws FileNotFoundException {
        String signupURL = "http://AUTOMATTER/signup";
        String str = FileWorker.getJSONStringFromFile("conf/signup");
        JsonNode signupJSON = Json.parse(str);
        wsHelper.postData(signupURL,signupJSON);
        return ok();
    }
}
