package com.registry.services;

import com.google.inject.Inject;
import com.registry.entities.Service;
import com.registry.entities.ServiceNames;
import com.registry.entities.ServiceProperties;

import java.util.HashMap;

/**
 * Created by egnaro on 12/10/16.
 */
public class ServiceRegistry {

    @Inject
    private Service service;

    public ServiceProperties addServiceDetails(String serviceName, ServiceProperties serviceProperties){
        return service.put(ServiceNames.valueOf(serviceName),serviceProperties);
    }

    public ServiceProperties getServiceDetails(String serviceName){
        return service.getServiceProperties(ServiceNames.valueOf(serviceName));
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
