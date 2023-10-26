package dev.wowovan.fitness.center.service;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class AuthService {

	private static final Logger LOG = LoggerFactory.getLogger(DummyService.class);
    

	public JsonObject userRegistration(UriInfo uriInfo, JsonObject payload){
		JsonObject response = new JsonObject();
		String errorCode = "DUPLICATE_REFUND_ERROR";
        response.put("error_code", errorCode);

		return response;
	}

}
