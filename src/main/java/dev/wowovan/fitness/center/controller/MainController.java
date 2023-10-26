package dev.wowovan.fitness.center.controller;

import io.vertx.core.json.JsonObject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.reactive.RestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @Path("/testing")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Access Token", description = "Get Access Token for Notify")
    public Response testing(JsonObject clientId){
        return Response.status(200).entity(new JsonObject().put("test", "this is test")).build(); 
    }
   
}
