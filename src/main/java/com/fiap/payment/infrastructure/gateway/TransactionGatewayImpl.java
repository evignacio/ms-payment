package com.fiap.payment.infrastructure.gateway;

import com.fiap.payment.core.entity.CreditCard;
import com.fiap.payment.core.gateway.TransactionGateway;
import com.fiap.payment.infrastructure.integration.rest.ExternalPaymentGatewayMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class TransactionGatewayImpl implements TransactionGateway {

    private final ExternalPaymentGatewayMock externalPaymentGatewayMock;

    public TransactionGatewayImpl(ExternalPaymentGatewayMock externalPaymentGatewayMock) {
        this.externalPaymentGatewayMock = externalPaymentGatewayMock;
    }

    @Override
    public String request(CreditCard creditCard, String cvv, BigDecimal amount)  {
        return externalPaymentGatewayMock.requestTransaction(creditCard, cvv, amount.toString());
    }
}
