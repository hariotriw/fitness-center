package dev.wowovan.fitness.center.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.wowovan.fitness.center.constant.ConstantVariable;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class MockupService {
    private static final Logger LOG = LoggerFactory.getLogger(DummyService.class);

    // @Inject
    // @Channel("callback-notification")
    // Emitter<String> emitterPayment;

    public JsonObject mockPayment(UriInfo uriInfo, JsonObject payload){
        // Response Builder
        JsonObject response = new JsonObject();
        response.put("status", "success");
        response.put("paymentStatus", ConstantVariable.STATUS_SUCCESS);
		response.put("message", "Payment Successfully.");

        return response;
    }

    public void mockEmitPayment(UriInfo uriInfo, JsonObject payload, JsonObject respGw){

        // request to kafka
        JsonObject kafkaReq = new JsonObject();
        kafkaReq.put("payload", payload);
        kafkaReq.put("response", respGw);
        // emitterPayment.send(kafkaReq.encode());
    }
}
