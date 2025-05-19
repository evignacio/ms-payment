package com.fiap.payment.core.usecase;

import com.fiap.payment.core.entity.Payment;
import com.fiap.payment.core.gateway.PaymentGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindPaymentUseCaseTest {

    private PaymentGateway paymentGateway;
    private FindPaymentUseCase findPaymentUseCase;

    @BeforeEach
    void setUp() {
        paymentGateway = mock(PaymentGateway.class);
        findPaymentUseCase = new FindPaymentUseCase(paymentGateway);
    }

    @Test
    void shouldFindPaymentSuccessfully() {
        String paymentId = "payment123";
        Payment payment = new Payment("txn123", "order123", BigDecimal.valueOf(100.50));
        when(paymentGateway.findById(paymentId)).thenReturn(Optional.of(payment));

        Payment result = findPaymentUseCase.execute(paymentId);

        assertNotNull(result);
        assertEquals("txn123", result.getTransactionId());
        assertEquals("order123", result.getOrderId());
        assertEquals(BigDecimal.valueOf(100.50), result.getAmount());
        verify(paymentGateway, times(1)).findById(paymentId);
    }

    @Test
    void shouldThrowExceptionWhenPaymentNotFound() {
        String paymentId = "invalidPaymentId";
        when(paymentGateway.findById(paymentId)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> findPaymentUseCase.execute(paymentId));
        assertEquals("Payment not found", exception.getMessage());
        verify(paymentGateway, times(1)).findById(paymentId);
    }
}