package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.registry.common.utils.InputParametersChecker;
import com.registry.entities.User;
import com.registry.services.UserService;
import play.mvc.Controller;
import play.mvc.Result;


public class HomeController extends Controller {

    @Inject
    private Gson gson;

    @Inject
    private UserService userService;

    public Result putUser(){
        JsonNode userNode = request().body().asJson();
        InputParametersChecker.ifNullThrowNullPointerException(userNode);
        User user = gson.fromJson(userNode.toString(),User.class);
        user = userService.putUser(user);
        return ok(gson.toJson(user));
    }

    public Result getUser(String userId){
        InputParametersChecker.ifNullThrowNullPointerException(userId);
        User user = userService.getUser(userId);
        return ok(gson.toJson(user));
    }

    public Result getUserList(){
        return ok(gson.toJson(userService.getUserList()));
    }
}
