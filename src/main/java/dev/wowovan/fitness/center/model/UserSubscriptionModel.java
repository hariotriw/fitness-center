package dev.wowovan.fitness.center.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import io.vertx.core.json.JsonObject;

@Entity
@Table(name = "user_subscription")
@IdClass(UserSubscriptionKey.class)
public class UserSubscriptionModel {

    @Id
    @Column(name = "user_id", length = 36, nullable = false)
    public String userId;

    @Id
    @Column(name = "product_id", length = 36, nullable = false)
    public String productId;

}
