package com.registry.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.registry.common.constants.StringConstants;
import com.registry.common.constants.URLConstants;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by prasad on 19/10/16.
 */
@Singleton
public class WSHelper {

    @Inject
    private WSClient ws;

    private HashMap<String,JsonNode> serviceDetailsMap = new HashMap<>();

    public WSResponse postData(String url, JsonNode node){
        onNullOrEmptyThrowNullPointerException(url);
        onNullOrEmptyThrowNullPointerException(node);
        String serviceName = getServiceName(url);
        String restAPI = getRESTAPIPart(url,serviceName);
        WSResponse response = doPost(restAPI,serviceName,node);
        if(response == null){
            getPresentServiceDetailsToMap(serviceName);
            response = doPost(restAPI,serviceName,node);
            if(response == null)
                return null;
        }
        return response;
    }

    private WSResponse doPost(String restAPI,String serviceName,JsonNode node)  {
        onNullOrEmptyThrowNullPointerException(restAPI,serviceName);
        onNullOrEmptyThrowNullPointerException(node);

        boolean isServiceDetailsNotPresent = (serviceDetailsMap.get(serviceName) == null);
        if(isServiceDetailsNotPresent)
            getPresentServiceDetailsToMap(serviceName);
        String clientURL = getClientURL(serviceDetailsMap.get(serviceName),restAPI);
        try {
            return post(clientURL,node);
        }catch (ExecutionException | InterruptedException e){
                return null;
        }
    }

    private String getServiceName(String url){
        onNullOrEmptyThrowNullPointerException(url);
        return url.split(URLConstants.HTTP_COLON_DOUBLE_SLASH)[1].split(URLConstants.SLASH)[0].toUpperCase();
    }

    private String getRESTAPIPart(String url,String serviceName){
        onNullOrEmptyThrowNullPointerException(url);
        String restAPI = url.split(URLConstants.HTTP_COLON_DOUBLE_SLASH)[1].split(serviceName)[1];
        if(restAPI.length()==0)
            return URLConstants.SLASH;
        return restAPI;
    }

    private void getPresentServiceDetailsToMap(String serviceName){
        onNullOrEmptyThrowNullPointerException(serviceName);
        JsonNode serviceDetailsJSON = getServiceDetailsFromRegistry(serviceName);
        serviceDetailsMap.put(serviceName,serviceDetailsJSON);
    }

    private String getClientURL(JsonNode node, String restAPI) {
        return URLConstants.HTTP_COLON_DOUBLE_SLASH.concat(node.get(StringConstants.IP).textValue()).concat(StringConstants.COLON).concat(node.get(StringConstants.PORT).textValue()).concat(restAPI);
    }

    public WSResponse post(String url, JsonNode node) throws ExecutionException, InterruptedException {
        return ws.url(url).post(node).toCompletableFuture().get();
    }

    private JsonNode getServiceDetailsFromRegistry(String serviceName){
        onNullOrEmptyThrowNullPointerException(serviceName);
        String rURL = URLConstants.REGISTRY_RESOURCES_URL.concat(serviceName);
        try {
            WSResponse response = ws.url(rURL).get().toCompletableFuture().get();
            int status = response.getStatus();
            JsonNode node = response.asJson();
            return node;
        }catch (Exception e){return null;}
    }

    private void onNullOrEmptyThrowNullPointerException(String ...objects){
        for (String object : objects){
            if(object == null || object.isEmpty() || object.length()==0){
                throw new NullPointerException();
            }
        }
    }

    private void onNullOrEmptyThrowNullPointerException(Object ...objects){
        for (Object object : objects){
            if(object == null){
                throw new NullPointerException();
            }
        }
    }
}
