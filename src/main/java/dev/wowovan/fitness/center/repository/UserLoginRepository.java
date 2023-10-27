package dev.wowovan.fitness.center.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import dev.wowovan.fitness.center.constant.ConstantVariable;
import dev.wowovan.fitness.center.global.GlobalFunction;
import dev.wowovan.fitness.center.model.UserLoginModel;
import dev.wowovan.fitness.center.model.UserModel;

@ApplicationScoped
public class UserLoginRepository {
    
    @Transactional
    public UserLoginModel insertNewUserLogin(String userId, String email, String password){
        UserLoginModel userLogin = new UserLoginModel();
        userLogin.userId = userId;
        userLogin.email = email;
		userLogin.password = password;
		userLogin.isActive = true;
        userLogin.createdAt = new Timestamp(System.currentTimeMillis());
        userLogin.createdBy = "system";
        userLogin.updatedAt = GlobalFunction.defaultTimestamp();
        userLogin.updatedBy = "system";
		userLogin.persist();

        return userLogin;
    }
}
