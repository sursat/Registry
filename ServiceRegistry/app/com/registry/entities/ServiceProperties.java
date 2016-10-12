package com.registry.entities;

/**
 * Created by egnaro on 12/10/16.
 */
public class ServiceProperties {

    private String url;

    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
