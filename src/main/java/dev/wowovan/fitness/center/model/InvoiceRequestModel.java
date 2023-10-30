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
@Table(name = "invoice_request")
public class InvoiceRequestModel extends PanacheEntityBase{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
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

    @Column(name = "duration", nullable = false)
    public int duration = 0;

    @Column(name = "created_at", nullable = false)
	public Timestamp createdAt = GlobalFunction.defaultTimestamp();

    @Column(name = "created_by", length = 40, nullable = false)
    public String createdBy;

    @Column(name = "updated_at", nullable = false)
	public Timestamp updatedAt = GlobalFunction.defaultTimestamp();

    @Column(name = "updated_by", length = 40, nullable = false)
    public String updatedBy;

}
