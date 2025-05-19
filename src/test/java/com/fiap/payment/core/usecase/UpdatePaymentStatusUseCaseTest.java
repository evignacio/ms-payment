package com.fiap.payment.core.usecase;

import com.fiap.payment.core.entity.Payment;
import com.fiap.payment.core.entity.PaymentStatus;
import com.fiap.payment.core.gateway.OrderGateway;
import com.fiap.payment.core.gateway.PaymentGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdatePaymentStatusUseCaseTest {

    private PaymentGateway paymentGateway;
    private OrderGateway orderGateway;
    private UpdatePaymentStatusUseCase updatePaymentStatusUseCase;

    @BeforeEach
    void setUp() {
        paymentGateway = mock(PaymentGateway.class);
        orderGateway = mock(OrderGateway.class);
        updatePaymentStatusUseCase = new UpdatePaymentStatusUseCase(paymentGateway, orderGateway);
    }

    @Test
    void shouldUpdatePaymentStatusToApproved() {
        String transactionId = "txn123";
        String transactionStatus = "GRANTED";
        Payment payment = new Payment("txn123", "order123", BigDecimal.valueOf(100.50));

        when(paymentGateway.findByTransactionId(transactionId)).thenReturn(Optional.of(payment));

        updatePaymentStatusUseCase.execute(transactionId, transactionStatus);

        assertEquals(PaymentStatus.APPROVED, payment.getStatus());
        verify(orderGateway, times(1)).notifyPaymentStatus("order123", PaymentStatus.APPROVED);
        verify(paymentGateway, times(1)).save(payment);
    }

    @Test
    void shouldUpdatePaymentStatusToDeclined() {
        String transactionId = "txn123";
        String transactionStatus = "DECLINED";
        Payment payment = new Payment("txn123", "order123", BigDecimal.valueOf(100.50));

        when(paymentGateway.findByTransactionId(transactionId)).thenReturn(Optional.of(payment));

        updatePaymentStatusUseCase.execute(transactionId, transactionStatus);

        assertEquals(PaymentStatus.DECLINED, payment.getStatus());
        verify(orderGateway, times(1)).notifyPaymentStatus("order123", PaymentStatus.DECLINED);
        verify(paymentGateway, times(1)).save(payment);
    }

    @Test
    void shouldThrowExceptionWhenPaymentNotFound() {
        String transactionId = "invalidTxn";
        String transactionStatus = "GRANTED";

        when(paymentGateway.findByTransactionId(transactionId)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                updatePaymentStatusUseCase.execute(transactionId, transactionStatus));

        assertEquals("Payment not found, transactionId: invalidTxn", exception.getMessage());
        verify(paymentGateway, times(1)).findByTransactionId(transactionId);
        verifyNoInteractions(orderGateway);
    }
}