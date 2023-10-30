package dev.wowovan.fitness.center.constant;

import io.vertx.core.json.JsonObject;

public class ErrorListConstant {
    public static final JsonObject GENERAL_ERROR = gen("GENERAL_ERROR", "System is currently having a problem, please try again in a moment");
    private static JsonObject gen(String errCode, String message) {
        return new JsonObject().put("error_code", errCode).put("message", message);
    }

    // ERROR VALIDATION
    public static final JsonObject ERROR_NAME_VALIDATION = errorValidation("ERROR_NAME_VALIDATION", "please fill the NAME field.");
    public static final JsonObject ERROR_EMAIL_VALIDATION = errorValidation("ERROR_EMAIL_VALIDATION", "please fill the EMAIL field.");
    public static final JsonObject ERROR_PASSWORD_VALIDATION = errorValidation("ERROR_PASSWORD_VALIDATION", "invalid / not match password.");
    public static final JsonObject ERROR_PHONE_NUMBER_VALIDATION = errorValidation("ERROR_PHONE_NUMBER_VALIDATION", "please fill the PHONE NUMBER field.");
    public static final JsonObject ERROR_CARD_NUMBER_VALIDATION = errorValidation("ERROR_CARD_NUMBER_VALIDATION", "please fill the CARD NUMBER field.");
    public static final JsonObject ERROR_ACCOUNT_NUMBER_VALIDATION = errorValidation("ERROR_ACCOUNT_NUMBER_VALIDATION", "please fill the ACCOUNT NUMBER field.");
    public static final JsonObject ERROR_CVV_VALIDATION = errorValidation("ERROR_CVV_VALIDATION", "please fill the CVV field.");
    public static final JsonObject ERROR_EXPIRED_DATE_VALIDATION = errorValidation("ERROR_EXPIRED_DATE_VALIDATION", "please fill the EXPIRED DATE field.");
    public static final JsonObject ERROR_CARDHOLDER_NAME_VALIDATION = errorValidation("ERROR_CARDHOLDER_NAME_VALIDATION", "please fill the CARDHOLDER NAME field.");
    
    
    private static JsonObject errorValidation(String errCode, String message) {
        return new JsonObject().put("error_code", errCode).put("message", message).put("httpStatus", 400);
    }
    
    // ERROR DATA
    public static final JsonObject ERROR_DATA_NEED_TO_VALIDATE = errorDataValidation("ERROR_DATA_NEED_TO_VALIDATE", "data already submitted before and need to validate.");
    public static final JsonObject ERROR_DATA_ALREADY_EXIST = errorDataValidation("ERROR_DATA_ALREADY_EXIST", "data already exist.");
    public static final JsonObject ERROR_DATA_INVALID_PAYLOAD = errorDataValidation("ERROR_DATA_INVALID_PAYLOAD", "invalid payload. please try again later.");
    public static final JsonObject ERROR_DATA_NOT_FOUND = errorDataValidation("ERROR_DATA_NOT_FOUND", "data not found. please check your form or try again later.");
    public static final JsonObject ERROR_EMAIL_NOT_FOUND = errorDataValidation("ERROR_EMAIL_NOT_FOUND", "email not found. please check your form again or register new account.");
    public static final JsonObject ERROR_PASSWORD_INVALID = errorDataValidation("ERROR_PASSWORD_INVALID", "wrong password. please enter the right password.");
    public static final JsonObject ERROR_USER_ALREADY_SUBSCRIBE = errorDataValidation("ERROR_USER_ALREADY_SUBSCRIBE", "user already subscribe the package. please select another package.");
    

    private static JsonObject errorDataValidation(String errCode, String message) {
        return new JsonObject().put("error_code", errCode).put("message", message).put("httpStatus", 400);
    }

    // ERROR OTHER
    public static final JsonObject ERROR_LINK_EXPIRED = errorExpired("ERROR_LINK_EXPIRED", "links already expired.");
    public static final JsonObject ERROR_UNAUTHORIZED = errorExpired("ERROR_UNAUTHORIZED", "Unauthorized. you are unauthorized to do this action, please relogin or contact admin.");

    private static JsonObject errorExpired(String errCode, String message) {
        return new JsonObject().put("error_code", errCode).put("message", message).put("httpStatus", 401);
    }

}
