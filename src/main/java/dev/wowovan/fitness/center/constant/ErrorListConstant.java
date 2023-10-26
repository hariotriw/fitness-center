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
}
