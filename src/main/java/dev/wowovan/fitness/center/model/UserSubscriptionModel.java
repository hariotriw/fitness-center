package dev.wowovan.fitness.center.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import dev.wowovan.fitness.center.global.GlobalFunction;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.vertx.core.json.JsonObject;

@Entity
@Table(name = "user_subscription")
@IdClass(UserSubscriptionKey.class)
public class UserSubscriptionModel extends PanacheEntityBase{

    @Id
    @Column(name = "user_id", length = 36, nullable = false)
    public String userId;

    @Id
    @Column(name = "product_id", length = 36, nullable = false)
    public String productId;

    @Column(name = "status_subscription", length = 25, nullable = false)
    public String statusSubscription;

    @Column(name = "subscribe_at")
	public Timestamp subscribeAt;

    @Column(name = "training_remaining", nullable = false)
    public int trainingRemaining = 0;

    @Column(name = "training_duration", nullable = false)
    public int trainingDuration = 0;

    @Column(name = "created_at", nullable = false)
	public Timestamp createdAt = GlobalFunction.defaultTimestamp();

    @Column(name = "created_by", length = 40, nullable = false)
    public String createdBy;

    @Column(name = "updated_at", nullable = false)
	public Timestamp updatedAt = GlobalFunction.defaultTimestamp();

    @Column(name = "updated_by", length = 40, nullable = false)
    public String updatedBy;

}
