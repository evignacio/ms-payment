package com.fiap.payment.core.gateway;

import com.fiap.payment.core.entity.PaymentStatus;

public interface OrderGateway {
    void notifyPaymentStatus(String orderId, PaymentStatus paymentStatus);
}
