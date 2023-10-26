package dev.wowovan.fitness.center.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.vertx.core.json.JsonObject;

@Entity
@Table(name ="user")
public class UserModel {
	@Id
    @Column(name = "user_id", length = 36, nullable = false)
    public String userId;

}
