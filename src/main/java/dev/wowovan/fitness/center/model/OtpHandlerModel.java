package dev.wowovan.fitness.center.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import dev.wowovan.fitness.center.global.GlobalFunction;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.vertx.core.json.JsonObject;

@Entity
@Table(name = "otp_handler")
public class OtpHandlerModel extends PanacheEntityBase{
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

    @Column(name = "otp_service_id", length = 50, nullable = false)
    public String otpServiceId;

    @Column(name = "otp_service_type", length = 50, nullable = false)
    public String otpServiceType;

    @Column(name = "created_at", nullable = false)
	public Timestamp createdAt = GlobalFunction.defaultTimestamp();

    @Column(name = "created_by", length = 40, nullable = false)
    public String createdBy;

    @Column(name = "updated_at", nullable = false)
	public Timestamp updatedAt = GlobalFunction.defaultTimestamp();

    @Column(name = "updated_by", length = 40, nullable = false)
    public String updatedBy;

}
