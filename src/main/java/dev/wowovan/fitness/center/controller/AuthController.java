package dev.wowovan.fitness.center.controller;

import java.sql.Timestamp;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.wowovan.fitness.center.constant.ConstantVariable;
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
    public Response registration(@Context UriInfo uriInfo, JsonObject payload){
        try {
            JsonObject reply = authService.userRegistration(uriInfo, payload);
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
            return Response.status(500).entity(new JsonObject().put("msg", e.getMessage())).build(); 
        }

    }

    @Path("/v1/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "User Login", description = "user login to system")
    public Response login(@Context UriInfo uriInfo, JsonObject payload){
        try {
            JsonObject reply = authService.userLogin(uriInfo, payload);
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
            return Response.status(500).entity(new JsonObject().put("msg", e.getMessage())).build(); 
        }

    }

    @Path("/v1/reset-password-by-email")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "User Request Reset Password", description = "user request reset password, and will get the link reset")
    public Response resetPasswordByEmail(@Context UriInfo uriInfo, JsonObject payload){
        try {
            JsonObject reply = authService.userResetPasswordByEmail(uriInfo, payload);
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
            return Response.status(500).entity(new JsonObject().put("msg", e.getMessage())).build(); 
        }
    }

    @Path("/v1/reset-password")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "User Request Reset Password", description = "user request reset password, and will get the link reset")
    public Response resetPassword(@Context UriInfo uriInfo, JsonObject payload){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        try {
            JsonObject reply = authService.userResetPassword(uriInfo, payload, currentTime);
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
            return Response.status(500).entity(new JsonObject().put("msg", e.getMessage())).build(); 
        }
    }

    @Path("/v1/logout")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "User Logout", description = "user logout from system")
    public Response logout(@Context UriInfo uriInfo, @HeaderParam("Authorization") String jwtToken, JsonObject payload){
        try {
            JsonObject reply = new JsonObject()
                    .put("status", ConstantVariable.STATUS_SUCCESS)
                    .put("message", "Logout successfully");

            return Response.status(200).entity(reply).build(); 
        } catch (Exception e) {
            // TODO: handle exception
            return Response.status(500).entity(new JsonObject().put("msg", e.getMessage())).build(); 
        }
    }

    // @Path("/v1/refresh-token")
    // @POST
    // @Produces(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Operation(summary = "User Logout", description = "user logout from system")
    // public Response refreshToken(@Context UriInfo uriInfo, @HeaderParam("Authorization") String jwtToken, JsonObject payload){
    //     try {
    //         JsonObject reply = new JsonObject()
    //                 .put("status", ConstantVariable.STATUS_SUCCESS)
    //                 .put("message", "Logout successfully");

    //         return Response.status(200).entity(reply).build(); 
    //     } catch (Exception e) {
    //         // TODO: handle exception
    //         return Response.status(500).entity(new JsonObject().put("msg", e.getMessage())).build(); 
    //     }
    // }

    @Path("/links")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "User Registration", description = "new user registration to system")
    public Response activateUser(@Context UriInfo uriInfo, 
                                    @QueryParam("userLoginId") String userLoginId, 
                                    @QueryParam("userId") String userId, 
                                    @QueryParam("expiredLink") String expiredLink, 
                                    @QueryParam("isActive") boolean isActive){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        try {
            JsonObject payload = new JsonObject()
                    .put("userLoginId", userLoginId)
                    .put("userId", userId)
                    .put("expiredLink", expiredLink)
                    .put("isActive", isActive);
                    
            JsonObject reply = authService.userActivation(uriInfo, payload, currentTime);
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
            return Response.status(500).entity(new JsonObject().put("msg", e.getMessage())).build(); 
        }

    }
    
}
