package com.sergey.zhuravlev.social.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("social.storage")
public class StorageProperties {

    private String location = "social-data";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}