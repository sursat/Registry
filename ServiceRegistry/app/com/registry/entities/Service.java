package com.registry.entities;

import com.google.inject.Singleton;

import java.util.HashMap;

/**
 * Created by egnaro on 12/10/16.
 */
@Singleton
public abstract class Service {

    private HashMap<ServiceNames,ServiceProperties> map;

    public HashMap getMap(){
        return map;
    }

    public ServiceProperties put(ServiceNames serviceName, ServiceProperties serviceProperties){
        return map.put(serviceName,serviceProperties);
    }

    public ServiceProperties getServiceProperties(ServiceNames serviceName){
        return map.get(serviceName);
    }

    public ServiceProperties update(ServiceNames serviceName, ServiceProperties serviceProperties){
        return map.replace(serviceName,serviceProperties);

    }

    public ServiceProperties remove(ServiceNames serviceName){
        return map.remove(serviceName);
    }

    public int size(){
        return map.size();
    }

    public HashMap getRegistredServicesList(){
        return map;
    }
}
