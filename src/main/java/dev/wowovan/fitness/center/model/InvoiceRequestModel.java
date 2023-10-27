package dev.wowovan.fitness.center.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import io.vertx.core.json.JsonObject;

public class InvoiceRequestModel {
    @Id
    @Column(name = "invoice_id", length = 36, nullable = false)
    public String invoiceId;

    @Column(name = "invoice_at")
	public Timestamp invoiceAt;

    @Column(name = "invoice_status", length = 25, nullable = false)
    public String invoiceStatus;

    @Column(name = "user_id", length = 36, nullable = false)
    public String userId;

    @Column(name = "product_id", length = 36, nullable = false)
    public String productId;

    @Column(name = "amount", nullable = false)
	public Double amount = (double) 0.0;

    @Column(name = "created_at", nullable = false)
	public Timestamp createdAt = Timestamp.valueOf("1900-01-01 00:00:00");

    @Column(name = "created_by", length = 40, nullable = false)
    public String createdBy;

    @Column(name = "updated_at", nullable = false)
	public Timestamp updatedAt = Timestamp.valueOf("1900-01-01 00:00:00");

    @Column(name = "updated_by", length = 40, nullable = false)
    public String updatedBy;

}
