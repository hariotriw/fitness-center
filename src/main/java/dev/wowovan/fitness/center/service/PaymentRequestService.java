package dev.wowovan.fitness.center.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import dev.wowovan.fitness.center.model.PaymentDataModel;
import dev.wowovan.fitness.center.model.PaymentRequestModel;
import dev.wowovan.fitness.center.model.ProductModel;
import dev.wowovan.fitness.center.model.UserLoginModel;
import dev.wowovan.fitness.center.model.UserModel;
import dev.wowovan.fitness.center.model.UserSubscriptionKey;
import dev.wowovan.fitness.center.model.UserSubscriptionModel;
import dev.wowovan.fitness.center.repository.InvoiceRepository;
import dev.wowovan.fitness.center.repository.PaymentDataRepository;
import dev.wowovan.fitness.center.repository.PaymentRepository;
import dev.wowovan.fitness.center.repository.SubscriptionRepository;
import dev.wowovan.fitness.center.repository.UserLoginRepository;
import dev.wowovan.fitness.center.repository.UserRepository;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class PaymentRequestService {
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

    @Inject
    PaymentRepository paymentRepository;


    public JsonObject getInvoice(UriInfo uriInfo, JsonObject payload){
        if(!payload.containsKey("userLoginIdJwt"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD;
        if(!payload.containsKey("invoiceId"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD.copy().put("field", "invoiceId");

        UserLoginModel userLogin = UserLoginModel.findById(payload.getString("userLoginIdJwt"));
        if(userLogin == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;
            
        UserModel user = UserModel.findById(userLogin.userId);
        if(user == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;
        
        String invoiceId = payload.getString("invoiceId");
        InvoiceRequestModel invoice = InvoiceRequestModel.findById(invoiceId);

        if(invoice == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;

        if(!user.userId.equals(invoice.userId)){
            return ErrorListConstant.ERROR_ACTION_NOT_AUTHORIZED;
        }

        // Response Builder
        JsonObject response = new JsonObject();
        response.put("status", "success");
		response.put("message", "success inquiry the invoice.");
        response.put("data", invoice);

		response.put("httpStatus", 200);
        return response;
    }

    @Transactional
    public JsonObject doPayment(UriInfo uriInfo, JsonObject payload){
        Timestamp startPaymentAt = new Timestamp(System.currentTimeMillis()); 
         // Payload Validation
        if(!payload.containsKey("userLoginIdJwt"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD;
        if(!payload.containsKey("invoiceId"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD.copy().put("field", "invoiceId");
        if(!payload.containsKey("paymentDataId"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD.copy().put("field", "paymentDataId");
        if(!payload.containsKey("amount"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD.copy().put("field", "amount");
        if(!payload.containsKey("cvv"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD.copy().put("field", "cvv");

        UserLoginModel userLogin = UserLoginModel.findById(payload.getString("userLoginIdJwt"));
        if(userLogin == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;
            
        UserModel user = UserModel.findById(userLogin.userId);
        if(user == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;
        
        String invoiceId = payload.getString("invoiceId");
        InvoiceRequestModel invoice = InvoiceRequestModel.findById(invoiceId);

        if(invoice == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;

        if(!user.userId.equals(invoice.userId)){
            return ErrorListConstant.ERROR_ACTION_NOT_AUTHORIZED;
        }

        if(invoice.invoiceStatus.equals("success")){
            return ErrorListConstant.ERROR_INVOICE_ALREADY_PAID;
        }
        if(invoice.invoiceStatus.equals("failed") || invoice.invoiceStatus.equals("cancelled")){
            return ErrorListConstant.ERROR_INVOICE_ALREADY_FAILED;
        }

        PaymentDataModel paymentData = PaymentDataModel.findById(payload.getString("paymentDataId"));
        if(paymentData == null){
            return ErrorListConstant.ERROR_DATA_NOT_FOUND; // TODO WOW - payment data not found
        }

        String cvvPayloadEncrypt = GlobalFunction.encryptData(payload.getString("cvv").concat("sssssss"));
        String cvvTokenEncrypted = new JsonObject(GlobalFunction.decryptData(paymentData.tokenData)).getString("cardIdentifyToken");
        LOG.info(">>>>> cvvPayloadEncrypt {}\n", cvvPayloadEncrypt);
        LOG.info(">>>>> cvvTokenEncrypted {}\n", cvvTokenEncrypted);
        if(!cvvPayloadEncrypt.equalsIgnoreCase(cvvTokenEncrypted)){
            return ErrorListConstant.ERROR_INVALID_CVV;
        } 

        // Save payment
        PaymentRequestModel payment = paymentRepository.createNewPayment(invoiceId, invoice.amount, paymentData.paymentDataId);
        
        // Response Builder
        String email = userLogin.email;
        String otpCode = GlobalFunction.generateRandomString(6);
        // Timestamp otpExpired 3 minute from now
		Timestamp otpExpiredTimestamp = new Timestamp(System.currentTimeMillis() + 3 * 60 * 1000);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String otpExpired = dateFormat.format(otpExpiredTimestamp);
        JsonObject data = new JsonObject()
                .put("email", email)
                .put("otp", otpCode)
                .put("otpExpired", otpExpired)
                .put("payment", payment);

        JsonObject response = new JsonObject();
        response.put("status", "success");
		response.put("message", "success inquiry the invoice.");
        response.put("data", data);

		response.put("httpStatus", 200);
        return response;
    }

    @Transactional
    public JsonObject verifyPayment(UriInfo uriInfo, JsonObject payload){
        Timestamp paymentAt = new Timestamp(System.currentTimeMillis()); 
         // Payload Validation
        if(!payload.containsKey("userLoginIdJwt"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD;
        if(!payload.containsKey("paymentRequestId"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD.copy().put("field", "paymentRequestId");
        if(!payload.containsKey("otp"))
            return ErrorListConstant.ERROR_DATA_INVALID_PAYLOAD.copy().put("field", "otp");

        UserLoginModel userLogin = UserLoginModel.findById(payload.getString("userLoginIdJwt"));
        if(userLogin == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;
            
        UserModel user = UserModel.findById(userLogin.userId);
        if(user == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;
        
        String paymentId = payload.getString("paymentRequestId");
        PaymentRequestModel payment = PaymentRequestModel.findById(paymentId);

        if(payment == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;

        String invoiceId = payment.invoiceId;
        InvoiceRequestModel invoice = InvoiceRequestModel.findById(invoiceId);

        if(!user.userId.equals(invoice.userId)){
            return ErrorListConstant.ERROR_ACTION_NOT_AUTHORIZED;
        }

        // Hard code otp validation
        if("000000".equals(payload.getString("otp"))){
            return ErrorListConstant.ERROR_OTP_INVALID;
        }
        if("111111".equals(payload.getString("otp"))){
            return ErrorListConstant.ERROR_OTP_EXPIRED;
        }

        UserSubscriptionModel userSubscription = UserSubscriptionModel.findById(new UserSubscriptionKey(invoice.userId, invoice.productId));

        if(userSubscription == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;

        ProductModel product = ProductModel.findById(invoice.productId);

        if(product == null)
            return ErrorListConstant.ERROR_DATA_NOT_FOUND;

        if(!user.userId.equals(invoice.userId)){
            return ErrorListConstant.ERROR_ACTION_NOT_AUTHORIZED;
        }

        // Save payment
        payment = paymentRepository.updateSuccessPayment(payment, paymentAt);
        invoice = invoiceRepository.updateSuccessPayment(invoice, paymentAt);
        userSubscription = subscriptionRepository.updateSuccessPayment(userSubscription, invoice);
        
        // Response Builder
        JsonObject response = new JsonObject();
        response.put("status", "success");
		response.put("message", "congrats you successfully subscribing to fitness center.");
        response.put("data", payment);

		response.put("httpStatus", 200);
        return response;
    }

}
