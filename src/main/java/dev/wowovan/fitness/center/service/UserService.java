package dev.wowovan.fitness.center.service;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import dev.wowovan.fitness.center.constant.ErrorListConstant;
import dev.wowovan.fitness.center.global.GlobalFunction;
import dev.wowovan.fitness.center.model.PaymentDataModel;
import dev.wowovan.fitness.center.model.UserLoginModel;
import dev.wowovan.fitness.center.model.UserModel;
import dev.wowovan.fitness.center.repository.PaymentDataRepository;
import dev.wowovan.fitness.center.repository.UserLoginRepository;
import dev.wowovan.fitness.center.repository.UserRepository;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(DummyService.class);

    @Inject
    UserRepository userRepository;

    @Inject
    UserLoginRepository userLoginRepository;

    @Inject
    PaymentDataRepository paymentDataRepository;

    public JsonObject updateUserInfo(UriInfo uriInfo, JsonObject payload){
        // Payload Validation
        if(!payload.containsKey("userLoginIdJwt"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD;

        if(!payload.containsKey("name"))
            return ErrorListConstant.ERROR_NAME_VALIDATION;

        if(!payload.containsKey("phoneNumber"))
            return ErrorListConstant.ERROR_PHONE_NUMBER_VALIDATION;
        
        if(!payload.containsKey("cardNumber"))
            return ErrorListConstant.ERROR_CARD_NUMBER_VALIDATION;

        if(!payload.containsKey("cvv"))
            return ErrorListConstant.ERROR_CVV_VALIDATION;

        if(!payload.containsKey("expiredDate"))
            return ErrorListConstant.ERROR_EXPIRED_DATE_VALIDATION;

        if(!payload.containsKey("cardholderName"))
            return ErrorListConstant.ERROR_CARDHOLDER_NAME_VALIDATION;

        String userLoginId = payload.getString("userLoginIdJwt");
        UserLoginModel userLogin = UserLoginModel.findById(userLoginId);
        if(userLogin == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;
            
        UserModel user = UserModel.findById(userLogin.userId);
        if(user == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;
            
        PaymentDataModel paymentData = PaymentDataModel.findById(user.paymentDataId);
        if(paymentData == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;

        // Process update
        // Prepare Data
		String cardNumberMasking = GlobalFunction.maskString(payload.getString("cardNumber"), 6, payload.getString("cardNumber").length()-4, '*');
		JsonObject tokenData = new JsonObject()
				.put("cardNumberToken", GlobalFunction.encryptData(payload.getString("cardNumber")))
				.put("cardIdentifyToken", GlobalFunction.encryptData(payload.getString("cvv").concat("sssssss")))
				.put("cardExpiredDate", payload.getString("expiredDate"));
		
		String tokenDataEncoded = GlobalFunction.encryptData(tokenData.encode());
        PaymentDataModel newPaymentData = paymentDataRepository.insertNewPaymentData(cardNumberMasking, payload.getString("cardholderName"), tokenDataEncoded); 
        user = userRepository.updateUser(user.userId, payload, newPaymentData.paymentDataId);

        // Response Builder
        JsonObject response = new JsonObject();
        response.put("status", "success");
		response.put("message", "seccess updating data.");
		response.put("httpStatus", 200);
        return response;
    }

    public JsonObject getUserInfo(UriInfo uriInfo, String userLoginId){
        JsonObject response = new JsonObject();

        if(userLoginId.isBlank()){
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD;
        }

        UserLoginModel userLogin = UserLoginModel.findById(userLoginId);
        if(userLogin == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;
            
        UserModel user = UserModel.findById(userLogin.userId);
        if(user == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;

        // Response Builder
        JsonObject userData = new JsonObject()
                .put("name", user.name)
                .put("email", userLogin.email.isBlank() ? "-" : userLogin.email)
                .put("phoneNumber", user.phoneNumber.isBlank() ? "-" : user.phoneNumber)
                .put("dob", user.dob.isBlank() ? "-" : user.dob)
                .put("memberStatus", user.memberStatus.isBlank() ? "-" : user.memberStatus);
        // response.put("userData", userData);

        PaymentDataModel paymentData = PaymentDataModel.findById(user.paymentDataId);
        JsonObject paymentDataObj = new JsonObject();
        if(paymentData != null){
            paymentDataObj.put("cardNumberMasking", paymentData.cardNumberMasking.isBlank() ? "-" : paymentData.cardNumberMasking)
                    .put("cardHolderName", paymentData.cardHolderName.isBlank() ? "-" : paymentData.cardHolderName);
        }
        response.put("data", new JsonObject()
                .put("userData", userData)
                .put("paymentData", paymentDataObj));
        response.put("status", "success");
		response.put("message", "seccess fetching data.");
		response.put("httpStatus", 200);

        return response;
    }
}
