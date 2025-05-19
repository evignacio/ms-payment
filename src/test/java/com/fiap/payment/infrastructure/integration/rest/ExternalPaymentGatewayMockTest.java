package com.fiap.payment.infrastructure.integration.rest;

import com.fiap.payment.core.entity.CreditCard;
import com.fiap.payment.core.usecase.UpdatePaymentStatusUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExternalPaymentGatewayMockTest {

    private UpdatePaymentStatusUseCase updatePaymentStatusUseCase;
    private ExternalPaymentGatewayMock externalPaymentGatewayMock;

    @BeforeEach
    void setUp() {
        updatePaymentStatusUseCase = mock(UpdatePaymentStatusUseCase.class);
        externalPaymentGatewayMock = new ExternalPaymentGatewayMock(updatePaymentStatusUseCase);
    }

    @Test
    void shouldRequestTransactionSuccessfully() {
        CreditCard creditCard = new CreditCard("123", "4111111111111111", "John Doe", "12/25", "token123");
        String cvv = "123";
        String amount = "100.50";

        String transactionId = externalPaymentGatewayMock.requestTransaction(creditCard, cvv, amount);

        assertNotNull(transactionId);
        assertDoesNotThrow(() -> UUID.fromString(transactionId));
    }

    @Test
    void shouldExecuteCallbackTransactionSuccessfully() throws InterruptedException {
        String orderId = "order123";

        externalPaymentGatewayMock.callbackTransaction(orderId);

        verify(updatePaymentStatusUseCase, times(1)).execute(orderId, "GRANTED");
    }
}