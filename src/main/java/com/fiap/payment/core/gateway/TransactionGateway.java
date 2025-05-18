package com.fiap.payment.core.gateway;

import com.fiap.payment.core.entity.CreditCard;

import java.math.BigDecimal;

public interface TransactionGateway {
    String request(CreditCard creditCard, String cvv, BigDecimal amount);
}
