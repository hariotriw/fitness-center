package dev.wowovan.fitness.center.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import dev.wowovan.fitness.center.constant.ConstantVariable;
import dev.wowovan.fitness.center.global.GlobalFunction;
import dev.wowovan.fitness.center.model.PaymentDataModel;

@ApplicationScoped
public class PaymentDataRepository {
    
    @Transactional
    public PaymentDataModel insertNewPaymentData(String cardNumberMasking, String cardholderName, String tokenData){
        PaymentDataModel paymentData = PaymentDataModel.find("cardNumberMasking = ?1", cardNumberMasking).firstResult();
        if(paymentData == null){
            paymentData = new PaymentDataModel();
            paymentData.createdAt = new Timestamp(System.currentTimeMillis());
            paymentData.createdBy = "system";
            paymentData.updatedAt = GlobalFunction.defaultTimestamp();
            paymentData.updatedBy = "system";
        } else {
            paymentData.updatedAt = new Timestamp(System.currentTimeMillis());
            paymentData.updatedBy = "customer";
        }
        paymentData.cardNumberMasking = cardNumberMasking;
        paymentData.cardHolderName = cardholderName;
        paymentData.tokenData = tokenData;
		paymentData.persist();

        return paymentData;
    }
}
