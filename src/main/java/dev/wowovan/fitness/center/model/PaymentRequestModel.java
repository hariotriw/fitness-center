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
@Table(name = "payment_request")
public class PaymentRequestModel extends PanacheEntityBase{
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
	public Timestamp createdAt = GlobalFunction.defaultTimestamp();

    @Column(name = "created_by", length = 40, nullable = false)
    public String createdBy;

    @Column(name = "updated_at", nullable = false)
	public Timestamp updatedAt = GlobalFunction.defaultTimestamp();

    @Column(name = "updated_by", length = 40, nullable = false)
    public String updatedBy;

}
