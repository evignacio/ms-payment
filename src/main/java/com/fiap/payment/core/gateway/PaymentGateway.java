package com.fiap.payment.core.gateway;

import com.fiap.payment.core.entity.Payment;

import java.util.Optional;

public interface PaymentGateway {
    Payment save(Payment payment);

    Optional<Payment> findByTransactionId(String transactionId);
}
