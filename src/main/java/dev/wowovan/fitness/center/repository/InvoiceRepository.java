package dev.wowovan.fitness.center.repository;

import java.sql.Timestamp;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import dev.wowovan.fitness.center.constant.ConstantVariable;
import dev.wowovan.fitness.center.global.GlobalFunction;
import dev.wowovan.fitness.center.model.InvoiceRequestModel;

@ApplicationScoped
public class InvoiceRepository {

    @Transactional
    public InvoiceRequestModel createInvoice(String userId, String productId, Double amount, Integer duration){
        InvoiceRequestModel invoice = new InvoiceRequestModel();
        invoice.invoiceAt = new Timestamp(System.currentTimeMillis());;
        invoice.userId = userId;
        invoice.productId = productId;
        invoice.invoiceStatus = ConstantVariable.INVOICE_STATUS_NEW;
        invoice.duration = duration;
        invoice.amount = amount;
        invoice.createdAt = new Timestamp(System.currentTimeMillis());
        invoice.createdBy = "system";
        invoice.updatedAt = GlobalFunction.defaultTimestamp();
        invoice.updatedBy = "system";

        invoice.persist();
        return invoice;
    }    
}
