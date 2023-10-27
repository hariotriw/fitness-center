package dev.wowovan.fitness.center.constant;

public class ConstantVariable {
    private ConstantVariable() {
        //do nothing
    }

    // DEFAULT STATUS
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_WAITING = "waiting";
    public static final String STATUS_FAILED = "failed";

    // MEMBER STATUS
    public static final String MEMBER_REGISTERED = "registered";
    public static final String MEMBER_EXPIRED = "expired";
    public static final String MEMBER_NOT_REGISTERED = "not-registered";
    public static final String MEMBER_NEED_VALIDATION = "need-validation";

	public static final String DATETIME_FULL_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

}
