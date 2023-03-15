package com.jojo.independentwebsite.config;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/webapi")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig(){
//        register(ProductController.class);
        packages("com.jojo.independentwebsite.controller");
        register(MultiPartFeature.class);


    }

}
