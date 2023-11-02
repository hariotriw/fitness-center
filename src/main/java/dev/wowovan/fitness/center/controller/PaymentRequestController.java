package dev.wowovan.fitness.center.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
import dev.wowovan.fitness.center.service.PaymentRequestService;
import dev.wowovan.fitness.center.service.UserService;
import dev.wowovan.fitness.center.service.UserSubscriptionService;
import io.vertx.core.json.JsonObject;

@Path("/payment")
public class PaymentRequestController {
    
     private static final Logger LOG = LoggerFactory.getLogger(PaymentRequestController.class);

    @Inject
    AuthService authService;

    @Inject
    JwtService jwtService;

    @Inject
    UserService userService;

    @Inject
    UserSubscriptionService userSubscriptionService;

    @Inject
    PaymentRequestService paymentRequestService;

    @Path("/v1/invoice/{invoiceId}")
    @GET
    @Operation(summary = "Get inquiry invoice", description = "Get inquiry invoice")
    public Response getAllProduct(@Context UriInfo uriInfo, @HeaderParam("X-Authorization") String jwtToken, @PathParam("invoiceId") String invoiceId){
        try {
            String newToken= "";
            JsonObject decodedJWT = jwtService.validateToken(jwtToken);
            JsonObject payload = new JsonObject();
            payload.put("invoiceId", invoiceId);
            if (decodedJWT != null) {
                // Token is valid, issue a new token with updated expiration time
                newToken = jwtService.generateToken(decodedJWT.getString("subject"));
                payload.put("userLoginIdJwt", decodedJWT.getString("subject"));
            } else {
                // Token is not valid, return unauthorized response
                return Response.status(401).entity(ErrorListConstant.ERROR_UNAUTHORIZED).build();
            }

            JsonObject reply = paymentRequestService.getInvoice(uriInfo, payload);
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

    @Path("/v1/payment")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Do Payment", description = "Do Payment")
    public Response doPayment(@Context UriInfo uriInfo, @HeaderParam("X-Authorization") String jwtToken, JsonObject payload){
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

            JsonObject reply = paymentRequestService.doPayment(uriInfo, payload);
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

    @Path("/v1/verify-payment")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Do Payment", description = "Do Payment")
    public Response verifyPayment(@Context UriInfo uriInfo, @HeaderParam("X-Authorization") String jwtToken, JsonObject payload){
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

            JsonObject reply = paymentRequestService.verifyPayment(uriInfo, payload);
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
