package dev.wowovan.fitness.center.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import io.vertx.core.json.JsonObject;

@Entity
@Table(name ="user")
public class UserModel {
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
    

    @Column(name = "created_at", nullable = false)
	public Timestamp createdAt = Timestamp.valueOf("1900-01-01 00:00:00");

    @Column(name = "created_by", length = 40, nullable = false)
    public String createdBy;

    @Column(name = "updated_at", nullable = false)
	public Timestamp updatedAt = Timestamp.valueOf("1900-01-01 00:00:00");

    @Column(name = "updated_by", length = 40, nullable = false)
    public String updatedBy;
}
