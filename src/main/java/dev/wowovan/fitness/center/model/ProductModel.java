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
@Table(name = "product")
public class ProductModel extends PanacheEntityBase{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "product_id", length = 36, nullable = false)
    public String productId;

    @Column(name = "product_name", length = 50, nullable = false)
    public String productName;

    @Column(name = "price", nullable = false)
	public Double price = (double) 0.0;

    @Column(name = "product_detail", nullable = false)
    public String productDetail;

    @Column(name = "created_at", nullable = false)
	public Timestamp createdAt = GlobalFunction.defaultTimestamp();

    @Column(name = "created_by", length = 40, nullable = false)
    public String createdBy;

    @Column(name = "updated_at", nullable = false)
	public Timestamp updatedAt = GlobalFunction.defaultTimestamp();

    @Column(name = "updated_by", length = 40, nullable = false)
    public String updatedBy;

}
