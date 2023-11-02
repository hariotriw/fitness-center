package dev.wowovan.fitness.center.repository;

import java.sql.Timestamp;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import dev.wowovan.fitness.center.constant.ConstantVariable;
import dev.wowovan.fitness.center.global.GlobalFunction;
import dev.wowovan.fitness.center.model.InvoiceRequestModel;
import dev.wowovan.fitness.center.model.PaymentRequestModel;

@ApplicationScoped
public class PaymentRepository {

    @Transactional
    public PaymentRequestModel createNewPayment(String invoiceId, Double amount, String paymentDataId){
        PaymentRequestModel payment = new PaymentRequestModel();
        payment.invoiceId = invoiceId;
        payment.paymentDataId = paymentDataId;
        payment.paymentExternalId = "";
        payment.paymentStatus = ConstantVariable.STATUS_WAITING;
        payment.paymentAmount = amount;
        payment.createdAt = new Timestamp(System.currentTimeMillis());
        payment.createdBy = "customer";
        payment.updatedAt = GlobalFunction.defaultTimestamp();
        payment.updatedBy = "system";

        payment.persist();
        return payment;
    }    

    @Transactional
    public PaymentRequestModel updateSuccessPayment(PaymentRequestModel payment, Timestamp paymentAt){
        payment.paymentExternalId = GlobalFunction.generateRandomString(20);
        payment.paymentStatus = ConstantVariable.STATUS_SUCCESS;
        payment.paymentAt = paymentAt;
        payment.updatedAt = new Timestamp(System.currentTimeMillis());
        payment.updatedBy = "customer";

        payment.persist();
        return payment;
    }    
}
