package dev.wowovan.fitness.center.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import io.vertx.core.json.JsonObject;

public class OtpHandlerModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "otp_id", length = 36, nullable = false)
    public String otpId;

    @Column(name = "otp_type", length = 25, nullable = false)
    public String otpType;

    @Column(name = "otp_code", length = 30, nullable = false)
    public String otpCode;

    @Column(name = "otp_at")
	public Timestamp otpdAt;

    @Column(name = "otp_status", length = 20, nullable = false)
    public String otpStatus;

    @Column(name = "service_id", length = 50, nullable = false)
    public String serviceId;

    @Column(name = "service_type", length = 50, nullable = false)
    public String serviceType;


    @Column(name = "created_at", nullable = false)
	public Timestamp createdAt = Timestamp.valueOf("1900-01-01 00:00:00");

    @Column(name = "created_by", length = 40, nullable = false)
    public String createdBy;

    @Column(name = "updated_at", nullable = false)
	public Timestamp updatedAt = Timestamp.valueOf("1900-01-01 00:00:00");

    @Column(name = "updated_by", length = 40, nullable = false)
    public String updatedBy;

}
