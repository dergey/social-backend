package com.sergey.zhuravlev.social.configuration;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI socialAPI() {
        return new OpenAPI()
                .info(new Info().title("Social API")
                        .description("Basic implementation of the social networking API")
                        .version("v1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Social Wiki Documentation")
                        .url("https://github.com/dergey/social-backend/wiki"));
    }

}
