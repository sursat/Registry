package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.registry.entities.Service;
import com.registry.entities.ServiceNames;
import com.registry.entities.ServiceProperties;
import com.registry.services.ServiceRegistry;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.HashMap;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    @Inject
    private ServiceRegistry serviceRegistry;

    @Inject
    private Service service;

    @Inject
    private Gson gson;

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result addServiceDetails(){
        JsonNode node = request().body().asJson();
        service = gson.fromJson(node.toString(),Service.class);
        HashMap map = new HashMap();
        map.forEach((serviceName, serviceProperty) -> {
            serviceRegistry.addServiceDetails(serviceName.toString(),(ServiceProperties) serviceProperty);
        });
        return ok();
    }

    public void getServiceDetails(String serviceName){
        //return service.getServiceProperties(ServiceNames.valueOf(serviceName));
    }

    public ServiceProperties updateServiceDetails(String serviceName, ServiceProperties serviceProperties){
        return service.update(ServiceNames.valueOf(serviceName),serviceProperties);
    }

    public ServiceProperties deleteServiceDetails(String serviceName){
        return service.remove(ServiceNames.valueOf(serviceName));
    }

    public HashMap getAllServicesDetails(){
        return service.getRegistredServicesList();
    }

}
