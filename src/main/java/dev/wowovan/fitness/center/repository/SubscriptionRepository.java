package dev.wowovan.fitness.center.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import dev.wowovan.fitness.center.constant.ConstantVariable;
import dev.wowovan.fitness.center.global.GlobalFunction;
import dev.wowovan.fitness.center.model.UserModel;
import dev.wowovan.fitness.center.model.UserSubscriptionKey;
import dev.wowovan.fitness.center.model.UserSubscriptionModel;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class SubscriptionRepository {
    
    @Transactional
    public UserSubscriptionModel insertOrUpdateSubscription(String userId, String productId, Timestamp subscribeAt){
        UserSubscriptionModel userSubscribe = UserSubscriptionModel.findById(new UserSubscriptionKey(userId, productId));
        if(userSubscribe == null){
            userSubscribe = new UserSubscriptionModel();
            userSubscribe.userId = userId;
            userSubscribe.productId = productId;
            userSubscribe.statusSubscription = ConstantVariable.SUBSCRIPTION_STATUS_NOT_SUBSCRIBED;
        }
		// userSubscribe.subscribeAt = subscribeAt;
        // userSubscribe.trainingRemaining += trainingDuration;
        // userSubscribe.trainingDuration += trainingDuration ;
        userSubscribe.createdAt = new Timestamp(System.currentTimeMillis());
        userSubscribe.createdBy = "system";
        userSubscribe.updatedAt = GlobalFunction.defaultTimestamp();
        userSubscribe.updatedBy = "system";
		userSubscribe.persist();

        return userSubscribe;
    }

    @Transactional
    public UserSubscriptionModel cancelSubscription(String userId, String productId){
        UserSubscriptionModel userSubscribe = UserSubscriptionModel.findById(new UserSubscriptionKey(userId, productId));
        userSubscribe.statusSubscription = ConstantVariable.SUBSCRIPTION_STATUS_CANCELLED;
		// userSubscribe.subscribeAt = subscribeAt;
        userSubscribe.trainingRemaining = 0;
        userSubscribe.trainingDuration = 0 ;
        userSubscribe.updatedAt = new Timestamp(System.currentTimeMillis());
        userSubscribe.updatedBy = "customer";
		userSubscribe.persist();

        return userSubscribe;
    }

    

    // @Transactional
    // public UserModel updateUser(String userId, JsonObject payload,String paymentDataId){
    //     UserModel user = UserModel.findById(userId);
    //     if(payload.containsKey("name")){
    //         user.name = payload.getString("name");
    //     }
    //     if(payload.containsKey("phoneNumber")){
    //         user.phoneNumber = payload.getString("phoneNumber");
    //     }
    //     if(payload.containsKey("dob")){
    //         user.dob = payload.getString("dob");
    //     }
    //     user.paymentDataId = paymentDataId;
    //     user.updatedAt = new Timestamp(System.currentTimeMillis());
    //     user.updatedBy = "customer";
	// 	user.persist();

    //     return user;
    // }
}
