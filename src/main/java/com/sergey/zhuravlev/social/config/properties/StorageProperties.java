package com.sergey.zhuravlev.social.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("social.storage")
public class StorageProperties {

    private String location = "social-data";

}