package dev.wowovan.fitness.center.controller;

import java.sql.Timestamp;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.wowovan.fitness.center.constant.ErrorListConstant;
import dev.wowovan.fitness.center.service.AuthService;
import dev.wowovan.fitness.center.service.JwtService;
import dev.wowovan.fitness.center.service.UserService;
import io.vertx.core.json.JsonObject;

@Path("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @Inject
    AuthService authService;

    @Inject
    JwtService jwtService;

    @Inject
    UserService userService;

    @Path("/v1/user-info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "User Info", description = "Showing user info")
    public Response getUserInfo(@Context UriInfo uriInfo, @HeaderParam("X-Authorization") String jwtToken){
        try {
            String newToken= "";
            JsonObject decodedJWT = jwtService.validateToken(jwtToken);
            if (decodedJWT != null) {
                // Token is valid, issue a new token with updated expiration time
                newToken = jwtService.generateToken(decodedJWT.getString("subject"));
            } else {
                // Token is not valid, return unauthorized response
                return Response.status(401).entity(ErrorListConstant.ERROR_UNAUTHORIZED).build();
            }

            JsonObject reply = userService.getUserInfo(uriInfo, decodedJWT.getString("subject"));
            int httpStatus;
            if(reply.containsKey("httpStatus")){
                httpStatus = reply.getInteger("httpStatus");
                reply.remove("httpStatus");
            } else {
                httpStatus = 200;
            }

            return Response.status(httpStatus).entity(reply).build(); 
        } catch (Exception e) {
            // TODO: handle exception
            return Response.status(500).entity(ErrorListConstant.GENERAL_ERROR).build(); 
        }

    }

    @Path("/v1/user-info")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "User Info", description = "Showing user info")
    public Response updateUserInfo(@Context UriInfo uriInfo, @HeaderParam("X-Authorization") String jwtToken, JsonObject payload){
        try {
            String newToken= "";
            JsonObject decodedJWT = jwtService.validateToken(jwtToken);
            if (decodedJWT != null) {
                // Token is valid, issue a new token with updated expiration time
                newToken = jwtService.generateToken(decodedJWT.getString("subject"));
                payload.put("userLoginIdJwt", decodedJWT.getString("subject"));
            } else {
                // Token is not valid, return unauthorized response
                return Response.status(401).entity(ErrorListConstant.ERROR_UNAUTHORIZED).build();
            }

            JsonObject reply = userService.updateUserInfo(uriInfo, payload);
            int httpStatus;
            if(reply.containsKey("httpStatus")){
                httpStatus = reply.getInteger("httpStatus");
                reply.remove("httpStatus");
            } else {
                httpStatus = 200;
            }

            return Response.status(httpStatus).entity(reply).build(); 
        } catch (Exception e) {
            // TODO: handle exception
            return Response.status(500).entity(ErrorListConstant.GENERAL_ERROR).build(); 
        }

    }

}
