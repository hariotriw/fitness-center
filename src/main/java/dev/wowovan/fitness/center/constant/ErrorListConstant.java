package dev.wowovan.fitness.center.constant;

import io.vertx.core.json.JsonObject;

public class ErrorListConstant {
    public static final JsonObject GENERAL_ERROR_WAITING = gen("GENERAL_ERROR", "Sistem sedang mengalami gangguan, silahkan coba beberapa saat lagi\nSystem is currently having a problem, please try again in a moment");
    public static final JsonObject ERROR_CALL_PARTNER_WAITING = gen("ERROR_CALL_PARTNER", "Kesalahan saat memanggil sistem mitra\nError when calling partner system");
    public static final JsonObject PARTNER_SYSTEM_ERROR_WAITING = gen("PARTNER_SYSTEM_ERROR", "Sistem mitra mengalami gangguan\nPartner system error");

    public static final JsonObject GENERAL_ERROR_FAILED = genFailed("GENERAL_ERROR", "Sistem sedang mengalami gangguan, silahkan coba beberapa saat lagi\nSystem is currently having a problem, please try again in a moment");
    public static final JsonObject ERROR_CALL_PARTNER_FAILED = genFailed("ERROR_CALL_PARTNER", "Kesalahan saat memanggil sistem mitra\nError when calling partner system");
    public static final JsonObject PARTNER_SYSTEM_ERROR_FAILED = genFailed("PARTNER_SYSTEM_ERROR", "Sistem mitra mengalami gangguan\nPartner system error");

    private static JsonObject gen(String errCode, String message) {
        return new JsonObject().put("error_code", errCode).put("message", message);
    }

    private static JsonObject genFailed(String errCode, String message) {
        return new JsonObject().put("error_code", errCode).put("status", errCode).put("message", message).put("chargeStatus", ConstantVariable.STATUS_FAILED).put("bindingStatus", ConstantVariable.STATUS_FAILED);
    }

    // ERROR VALIDATION
    public static final JsonObject ERROR_NAME_VALIDATION = errorValidation("ERROR_NAME_VALIDATION", "please fill the NAME field.");
    public static final JsonObject ERROR_EMAIL_VALIDATION = errorValidation("ERROR_EMAIL_VALIDATION", "please fill the EMAIL field.");
    public static final JsonObject ERROR_PASSWORD_VALIDATION = errorValidation("ERROR_PASSWORD_VALIDATION", "invalid / wrong password.");
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

    private static JsonObject errorDataValidation(String errCode, String message) {
        return new JsonObject().put("error_code", errCode).put("message", message).put("httpStatus", 400);
    }

}
