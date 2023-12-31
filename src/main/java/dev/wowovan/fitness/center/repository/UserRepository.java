package dev.wowovan.fitness.center.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import dev.wowovan.fitness.center.constant.ConstantVariable;
import dev.wowovan.fitness.center.global.GlobalFunction;
import dev.wowovan.fitness.center.model.UserModel;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class UserRepository {
    
    @Transactional
    public UserModel insertNewUser(String name, String phoneNumber, String dob, String paymentDataId){
        UserModel user = new UserModel();
        user.name = name;
		user.phoneNumber = phoneNumber;
		user.dob = dob;
		user.memberStatus = ConstantVariable.MEMBER_NEED_VALIDATION;
        user.paymentDataId = paymentDataId;
        user.createdAt = new Timestamp(System.currentTimeMillis());
        user.createdBy = "system";
        user.updatedAt = GlobalFunction.defaultTimestamp();
        user.updatedBy = "system";
		user.persist();

        return user;
    }

    @Transactional
    public UserModel updateUser(String userId, JsonObject payload,String paymentDataId){
        UserModel user = UserModel.findById(userId);
        if(payload.containsKey("name")){
            user.name = payload.getString("name");
        }
        if(payload.containsKey("phoneNumber")){
            user.phoneNumber = payload.getString("phoneNumber");
        }
        if(payload.containsKey("dob")){
            user.dob = payload.getString("dob");
        }
        user.paymentDataId = paymentDataId;
        user.updatedAt = new Timestamp(System.currentTimeMillis());
        user.updatedBy = "customer";
		user.persist();

        return user;
    }

    @Transactional
    public UserModel activateUser(String userId){
        UserModel user = UserModel.findById(userId);
		user.memberStatus = ConstantVariable.MEMBER_REGISTERED;
        user.updatedAt = new Timestamp(System.currentTimeMillis());
        user.updatedBy = "customer";
		user.persist();

        return user;
    }
}
