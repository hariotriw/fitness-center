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
@Table(name ="user")
public class UserModel extends PanacheEntityBase{
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_id", length = 36, nullable = false)
    public String userId;

    @Column(name = "name", length = 50, nullable = false)
    public String name;

    @Column(name = "phone_number", length = 20, nullable = false)
    public String phoneNumber;

    @Column(name = "dob", length = 12, nullable = false)
    public String dob;

    @Column(name = "member_status", length = 25, nullable = false)
    public String memberStatus;
    
    @Column(name = "payment_data_id", length = 36, nullable = false)
    public String paymentDataId;

    @Column(name = "created_at", nullable = false)
	public Timestamp createdAt = GlobalFunction.defaultTimestamp();

    @Column(name = "created_by", length = 40, nullable = false)
    public String createdBy;

    @Column(name = "updated_at", nullable = false)
	public Timestamp updatedAt = GlobalFunction.defaultTimestamp();

    @Column(name = "updated_by", length = 40, nullable = false)
    public String updatedBy;
}
