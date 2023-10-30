package dev.wowovan.fitness.center.service;

import java.sql.Timestamp;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.wowovan.fitness.center.constant.ConstantVariable;
import dev.wowovan.fitness.center.constant.ErrorListConstant;
import dev.wowovan.fitness.center.global.GlobalFunction;
import dev.wowovan.fitness.center.model.InvoiceRequestModel;
import dev.wowovan.fitness.center.model.ProductModel;
import dev.wowovan.fitness.center.model.UserLoginModel;
import dev.wowovan.fitness.center.model.UserModel;
import dev.wowovan.fitness.center.model.UserSubscriptionKey;
import dev.wowovan.fitness.center.model.UserSubscriptionModel;
import dev.wowovan.fitness.center.repository.InvoiceRepository;
import dev.wowovan.fitness.center.repository.PaymentDataRepository;
import dev.wowovan.fitness.center.repository.SubscriptionRepository;
import dev.wowovan.fitness.center.repository.UserLoginRepository;
import dev.wowovan.fitness.center.repository.UserRepository;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class UserSubscriptionService {
    private static final Logger LOG = LoggerFactory.getLogger(DummyService.class);

    @Inject
    UserRepository userRepository;

    @Inject
    UserLoginRepository userLoginRepository;

    @Inject
    PaymentDataRepository paymentDataRepository;

    @Inject
    SubscriptionRepository subscriptionRepository;

    @Inject
    InvoiceRepository invoiceRepository;


    public JsonObject getAllProduct(UriInfo uriInfo){

        List<ProductModel> products = ProductModel.findAll().list();


        // Response Builder
        JsonObject response = new JsonObject();
        response.put("status", "success");
		response.put("message", "success getting the menu.");
        response.put("data", products);

		response.put("httpStatus", 200);
        return response;
    }

    @Transactional
    public JsonObject userSubscribeNewOrExtendProduct(UriInfo uriInfo, JsonObject payload){
        Timestamp subscribeAt = new Timestamp(System.currentTimeMillis()); 
         // Payload Validation
        if(!payload.containsKey("userLoginIdJwt"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD;
        if(!payload.containsKey("productId"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD.copy().put("field", "productId");
        if(!payload.containsKey("duration"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD.copy().put("field", "duration");

        String productId = payload.getString("productId");
        Integer duration = payload.getInteger("duration");

        UserLoginModel userLogin = UserLoginModel.findById(payload.getString("userLoginIdJwt"));
        if(userLogin == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;
            
        UserModel user = UserModel.findById(userLogin.userId);
        if(user == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;

        ProductModel product = ProductModel.findById(productId);
        if(product == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;

        UserSubscriptionModel subscription = UserSubscriptionModel.findById(new UserSubscriptionKey(user.userId, productId));
        if(subscription != null){
            if(subscription.statusSubscription.equalsIgnoreCase(ConstantVariable.SUBSCRIPTION_STATUS_SUBSCRIBED)){
                return ErrorListConstant.ERROR_USER_ALREADY_SUBSCRIBE;
            }
        }

        Double amount = product.price * duration;
        subscription = subscriptionRepository.insertOrUpdateSubscription(user.userId, productId, subscribeAt);
        InvoiceRequestModel invoice = invoiceRepository.createInvoice(user.userId, productId, amount, duration);

        // Response Builder
        JsonObject response = new JsonObject();
        response.put("status", "success");
		response.put("message", "success creating new invoice.");
        response.put("data", invoice);

		response.put("httpStatus", 200);
        return response;
    }

    public JsonObject userCancelledSubscription(UriInfo uriInfo, JsonObject payload){
        Timestamp subscribeAt = new Timestamp(System.currentTimeMillis()); 
         // Payload Validation
        if(!payload.containsKey("userLoginIdJwt"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD;
        if(!payload.containsKey("productId"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD.copy().put("field", "productId");

        String productId = payload.getString("productId");

        UserLoginModel userLogin = UserLoginModel.findById(payload.getString("userLoginIdJwt"));
        if(userLogin == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;
            
        UserModel user = UserModel.findById(userLogin.userId);
        if(user == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;

        ProductModel product = ProductModel.findById(productId);
        if(product == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;

        UserSubscriptionModel subscription = UserSubscriptionModel.findById(new UserSubscriptionKey(user.userId, productId));
        if(subscription == null){
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;
        }

        subscription = subscriptionRepository.cancelSubscription(user.userId, productId);
        // InvoiceRequestModel invoice = createInvoice(user.userId, productId, amount, duration);

        // Response Builder
        JsonObject response = new JsonObject();
        response.put("status", "success");
		response.put("message", "success cancelled the subscription.");
        // response.put("data", invoice);

		response.put("httpStatus", 200);
        return response;
    }
}
