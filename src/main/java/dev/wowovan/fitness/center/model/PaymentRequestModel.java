package dev.wowovan.fitness.center.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import io.vertx.core.json.JsonObject;

public class PaymentRequestModel {
    @Id
    @Column(name = "payment_id", length = 36, nullable = false)
    public String paymentId;

    @Column(name = "payment_status", length = 25, nullable = false)
    public String paymentStatus;

    @Column(name = "invoice_id", length = 36, nullable = false)
    public String invoiceId;

    @Column(name = "payment_data_id", length = 36, nullable = false)
    public String paymentDataId;

    @Column(name = "payment_external_id", length = 50, nullable = false)
    public String paymentExternalId;

    @Column(name = "payment_amount", nullable = false)
	public Double paymentAmount = (double) 0.0;

    @Column(name = "payment_at")
	public Timestamp paymentAt;

    @Column(name = "created_at", nullable = false)
	public Timestamp createdAt = Timestamp.valueOf("1900-01-01 00:00:00");

    @Column(name = "created_by", length = 40, nullable = false)
    public String createdBy;

    @Column(name = "updated_at", nullable = false)
	public Timestamp updatedAt = Timestamp.valueOf("1900-01-01 00:00:00");

    @Column(name = "updated_by", length = 40, nullable = false)
    public String updatedBy;

}
