package com.fiap.payment.core.usecase;

import com.fiap.payment.core.entity.Payment;
import com.fiap.payment.core.gateway.PaymentGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FindPaymentUseCase {

    private final PaymentGateway paymentGateway;

    public FindPaymentUseCase(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public Payment execute(String id) {
        log.info("Finding payment, id: {}", id);
        var paymentOpt = paymentGateway.findById(id);
        if (paymentOpt.isEmpty()) {
            log.error("Payment not found, id: {}", id);
            throw new IllegalStateException("Payment not found");
        }
        var payment = paymentOpt.get();
        log.info("Payment found successfully");
        return payment;
    }
}
