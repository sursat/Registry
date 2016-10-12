package com.registry.entities;

import com.google.inject.Singleton;

import java.util.List;

/**
 * Created by egnaro on 12/10/16.
 */
@Singleton
public class ServiceStore {

    private List<Service> serviceList;

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }
}
