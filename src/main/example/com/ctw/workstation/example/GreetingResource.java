package com.ctw.workstation.example;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    GreetingConfig config;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return config.message() + " from Quarkus REST";
    }
}
