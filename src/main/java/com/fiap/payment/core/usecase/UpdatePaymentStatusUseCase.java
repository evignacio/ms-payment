package com.fiap.payment.core.usecase;

import com.fiap.payment.core.gateway.OrderGateway;
import com.fiap.payment.core.gateway.PaymentGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UpdatePaymentStatusUseCase {

    private static final String TRANSACTION_APPROVED_STATUS = "GRANTED";
    private final PaymentGateway paymentGateway;
    private final OrderGateway orderGateway;

    public UpdatePaymentStatusUseCase(PaymentGateway paymentGateway, OrderGateway orderGateway) {
        this.paymentGateway = paymentGateway;
        this.orderGateway = orderGateway;
    }

    public void execute(String transactionId, String transactionStatus) {
        log.info("Updating payment status, transactionId: {}", transactionId);
        var paymentOpt = paymentGateway.findByTransactionId(transactionId);
        if (paymentOpt.isEmpty()) {
            log.error("Payment not found for transactionId: {}", transactionId);
            throw new IllegalStateException("Payment not found, transactionId: " + transactionId);
        }
        var payment = paymentOpt.get();
        if (TRANSACTION_APPROVED_STATUS.equals(transactionStatus)) {
            payment.defineAsApproved();
        } else {
            payment.defineAsDeclined();
        }
        orderGateway.notifyPaymentStatus(payment.getOrderId(), payment.getStatus());
        paymentGateway.save(payment);
        log.info("Payment status update successfully for orderId: {}", payment.getOrderId());
    }
}
