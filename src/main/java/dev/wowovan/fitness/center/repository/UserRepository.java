package dev.wowovan.fitness.center.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import dev.wowovan.fitness.center.constant.ConstantVariable;
import dev.wowovan.fitness.center.global.GlobalFunction;
import dev.wowovan.fitness.center.model.UserModel;

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
}
