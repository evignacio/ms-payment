package com.fiap.payment.infrastructure.gateway;

import com.fiap.payment.core.entity.CreditCard;
import com.fiap.payment.infrastructure.integration.rest.ExternalPaymentGatewayMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionGatewayImplTest {

    private ExternalPaymentGatewayMock externalPaymentGatewayMock;
    private TransactionGatewayImpl transactionGateway;

    @BeforeEach
    void setUp() {
        externalPaymentGatewayMock = mock(ExternalPaymentGatewayMock.class);
        transactionGateway = new TransactionGatewayImpl(externalPaymentGatewayMock);
    }

    @Test
    void shouldRequestTransactionSuccessfully() {
        CreditCard creditCard = new CreditCard("123", "4111111111111111", "John Doe", "12/25", "token123");
        String cvv = "123";
        BigDecimal amount = BigDecimal.valueOf(100.50);
        String expectedTransactionId = "txn123";

        when(externalPaymentGatewayMock.requestTransaction(creditCard, cvv, amount.toString()))
                .thenReturn(expectedTransactionId);

        String transactionId = transactionGateway.request(creditCard, cvv, amount);

        assertNotNull(transactionId);
        assertEquals(expectedTransactionId, transactionId);
        verify(externalPaymentGatewayMock, times(1)).requestTransaction(creditCard, cvv, amount.toString());
    }

    @Test
    void shouldThrowExceptionWhenExternalGatewayFails() {
        CreditCard creditCard = new CreditCard("123", "4111111111111111", "John Doe", "12/25", "token123");
        String cvv = "123";
        BigDecimal amount = BigDecimal.valueOf(100.50);

        when(externalPaymentGatewayMock.requestTransaction(creditCard, cvv, amount.toString()))
                .thenThrow(new RuntimeException("External gateway error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                transactionGateway.request(creditCard, cvv, amount));

        assertEquals("External gateway error", exception.getMessage());
        verify(externalPaymentGatewayMock, times(1)).requestTransaction(creditCard, cvv, amount.toString());
    }
}