package com.fiap.payment.infrastructure.mapper;

import com.fiap.payment.core.entity.Payment;
import com.fiap.payment.infrastructure.repository.model.PaymentModel;

public abstract class PaymentMapper {

    private PaymentMapper() {
        // Prevent instantiation
    }

    public static Payment toEntty(PaymentModel paymentModel) {
        return new Payment(
                paymentModel.getId(),
                paymentModel.getTransactionId(),
                paymentModel.getOrderId(),
                paymentModel.getAmount(),
                paymentModel.getStatus(),
                paymentModel.getCreatedAt()
        );
    }

    public static PaymentModel toModel(Payment payment) {
        return new PaymentModel(
                payment.getId(),
                payment.getTransactionId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt()
        );
    }
}
