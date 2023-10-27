package dev.wowovan.fitness.center.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.wowovan.fitness.center.service.AuthService;
import io.vertx.core.json.JsonObject;

@Path("/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @Inject
    AuthService authService;

    @Path("/v1/registration")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "User Registration", description = "new user registration to system")
    public Response testing(@Context UriInfo uriInfo, JsonObject payload){
        // try {
            JsonObject reply = authService.userRegistration(uriInfo, payload);
            int httpStatus;
            if(reply.containsKey("httpStatus")){
                httpStatus = reply.getInteger("httpStatus");
                reply.remove("httpStatus");
            } else {
                httpStatus = 200;
            }

            return Response.status(httpStatus).entity(reply).build(); 
        // } catch (Exception e) {
            // TODO: handle exception
            // return Response.status(500).entity(new JsonObject().put("msg", e.getMessage())).build(); 
        // }

    }
    
}
