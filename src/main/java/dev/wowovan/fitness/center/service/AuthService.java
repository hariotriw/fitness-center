package dev.wowovan.fitness.center.service;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.wowovan.fitness.center.constant.ConstantVariable;
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
public class AuthService {

	private static final Logger LOG = LoggerFactory.getLogger(DummyService.class);
    
	@Inject
	UserRepository userRepository;

	@Inject
	UserLoginRepository userLoginRepository;

	@Inject
	PaymentDataRepository paymentDataRepository;

	@ConfigProperty(name = "link.activation.base.url")
	private String linkActivationBaseUrl;

	@Transactional
	public JsonObject userRegistration(UriInfo uriInfo, JsonObject payload){
		LocalDateTime reqAt = LocalDateTime.now();

		// Payload Validation
		if(!payload.containsKey("password"))
			return ErrorListConstant.ERROR_PASSWORD_VALIDATION;
			
		if(!payload.containsKey("repassword") || !payload.getString("password").equalsIgnoreCase(payload.getString("repassword")))
			return ErrorListConstant.ERROR_PASSWORD_VALIDATION;
			
		if(!payload.containsKey("name"))
			return ErrorListConstant.ERROR_NAME_VALIDATION;

		if(!payload.containsKey("email"))
			return ErrorListConstant.ERROR_EMAIL_VALIDATION;

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

		// EMAIL VALIDATION
		UserLoginModel userLogin = UserLoginModel.find("email = ?1 AND isActive = ?2", payload.getString("email"), true).firstResult();
		if(userLogin != null){
			UserModel user = UserModel.findById(userLogin.userId);
			if(user !=  null ){
				if(user.memberStatus.equalsIgnoreCase(ConstantVariable.MEMBER_NEED_VALIDATION)){
					return ErrorListConstant.ERROR_DATA_NEED_TO_VALIDATE;
				}	
			}
			// else
			// if(user.memberStatus.equalsIgnoreCase(ConstantVariable.MEMBER_REGISTERED) || user.memberStatus.equalsIgnoreCase(ConstantVariable.MEMBER_EXPIRED))
			return ErrorListConstant.ERROR_DATA_ALREADY_EXIST.copy().put("field", "email");
		}

		// Prepare Data
		String password = GlobalFunction.hashPassword(payload.getString("password"));
		String cardNumberMasking = GlobalFunction.maskString(payload.getString("cardNumber"), 6, payload.getString("cardNumber").length()-4, '*');
		JsonObject tokenData = new JsonObject()
				.put("cardNumberToken", GlobalFunction.encryptData(payload.getString("cardNumber")))
				.put("cardIdentifyToken", GlobalFunction.encryptData(payload.getString("cvv").concat("sssssss")))
				.put("cardExpiredDate", payload.getString("expiredDate"));
		
		String tokenDataEncoded = GlobalFunction.encryptData(tokenData.encode());

		// user login =  null => registering new user
		userLogin = newUserRegistration(payload.getString("name"), payload.getString("email"), password, 
							payload.getString("phoneNumber"), cardNumberMasking, tokenDataEncoded, payload.getString("cardholderName"));

		// url / link process
		String urlActivation = linkActivationBaseUrl
									.concat("?userLoginId=")
									.concat(userLogin.userLoginId)
									.concat("&userId=")
									.concat(userLogin.userId)
									.concat("&isActive=true");
		
		// TODO OTP Processing


		// Response Builder
		JsonObject response = new JsonObject();
		response.put("status", "processed");
		response.put("message", "Success registration. Check your email to activate your account.");
		response.put("url", urlActivation);
		response.put("httpStatus", 201);
		return response;
	}

	@Transactional
	public JsonObject userActivation(UriInfo uriInfo, JsonObject payload, Timestamp currentTime){
		LocalDateTime reqAt = LocalDateTime.now();

		// Payload Validation
		if(!payload.containsKey("userLoginId"))
			return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD;
			
		if(!payload.containsKey("userId"))
			return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD;
			
		if(!payload.containsKey("isActive") || !payload.getBoolean("isActive"))
			return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD;

		if(!payload.containsKey("expiredLink"))
			return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD;

		// TODO VALIDATION TIMESTAMP

		// Process  Activation
		String userLoginId = payload.getString("userLoginId");
		String userId = payload.getString("userId");
		UserLoginModel userLogin = UserLoginModel.find("userLoginId = ?1 AND userId = ?2 AND isActive = true", userLoginId, userId).firstResult();
		if(userLogin == null){
			return ErrorListConstant.ERROR_DATA_NOT_FOUND;
		}

		UserModel user = UserModel.findById(userId);

		if(user == null){
			return ErrorListConstant.ERROR_DATA_NOT_FOUND;
		}

		if(user.memberStatus.equalsIgnoreCase(ConstantVariable.MEMBER_REGISTERED) || user.memberStatus.equalsIgnoreCase(ConstantVariable.MEMBER_NOT_REGISTERED)){
			return ErrorListConstant.ERROR_DATA_NOT_FOUND; // TODO better mapping
		}

		userRepository.activateUser(userId);

		// Response Builder
		JsonObject response = new JsonObject();
		response.put("status", "success");
		response.put("message", "Success registration. Account already activated.");
		response.put("httpStatus", 201);
		return response;
	
	}

	@Transactional
	private UserLoginModel newUserRegistration(String name, String email, String password, String phoneNumber, String cardNumberMasking, String tokenData, String cardholderName){
		PaymentDataModel paymentData = paymentDataRepository.insertNewPaymentData(cardNumberMasking, cardholderName, tokenData); // TODO mapping tokenData 
		UserModel user = userRepository.insertNewUser(name, phoneNumber, "", paymentData.paymentDataId);
		UserLoginModel userLogin = userLoginRepository.insertNewUserLogin(user.userId, email, password);
		
		return userLogin;
	}

}
