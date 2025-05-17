package com.fiap.payment.core.dto;

import java.math.BigDecimal;

public record CreatePaymentDTO(TokenCreditCardDTO tokenCreditCard, String orderId, BigDecimal amount) {
    @Override
    public String toString() {
        return "CreatePaymentDTO{" +
                "tokenCreditCard=" + tokenCreditCard +
                ", orderId='" + orderId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
