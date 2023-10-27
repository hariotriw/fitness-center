package dev.wowovan.fitness.center.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import io.vertx.core.json.JsonObject;

public class PaymentDataModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "payment_data_id", length = 36, nullable = false)
    public String paymentDataId;

    @Column(name = "card_number_masking", length = 36, nullable = false)
    public String cardNumberMasking;

    @Column(name = "card_holder_name", length = 50, nullable = false)
    public String cardHolderName;

    @Column(name = "token_data", nullable = false)
    public String tokenData;

    
    @Column(name = "created_at", nullable = false)
	public Timestamp createdAt = Timestamp.valueOf("1900-01-01 00:00:00");

    @Column(name = "created_by", length = 40, nullable = false)
    public String createdBy;

    @Column(name = "updated_at", nullable = false)
	public Timestamp updatedAt = Timestamp.valueOf("1900-01-01 00:00:00");

    @Column(name = "updated_by", length = 40, nullable = false)
    public String updatedBy;

}
