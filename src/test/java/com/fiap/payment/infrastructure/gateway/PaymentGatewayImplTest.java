package com.fiap.payment.infrastructure.gateway;

import com.fiap.payment.core.entity.Payment;
import com.fiap.payment.core.entity.PaymentStatus;
import com.fiap.payment.infrastructure.mapper.PaymentMapper;
import com.fiap.payment.infrastructure.repository.PaymentRepository;
import com.fiap.payment.infrastructure.repository.model.PaymentModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentGatewayImplTest {

    private PaymentRepository paymentRepository;
    private PaymentGatewayImpl paymentGateway;

    @BeforeEach
    void setUp() {
        paymentRepository = mock(PaymentRepository.class);
        paymentGateway = new PaymentGatewayImpl(paymentRepository);
    }

    @Test
    void shouldSavePaymentSuccessfully() {
        Payment payment = new Payment("txn123", "order123", BigDecimal.valueOf(100.50));
        PaymentModel paymentModel = PaymentMapper.toModel(payment);
        when(paymentRepository.save(paymentModel)).thenReturn(paymentModel);

        Payment result = paymentGateway.save(payment);

        assertNotNull(result);
        assertEquals("txn123", result.getTransactionId());
        assertEquals("order123", result.getOrderId());
        assertEquals(BigDecimal.valueOf(100.50), result.getAmount());
        verify(paymentRepository, times(1)).save(paymentModel);
    }

    @Test
    void shouldFindPaymentByIdSuccessfully() {
        String paymentId = "payment123";
        PaymentModel paymentModel = new PaymentModel("payment123", "txn123", "order123", BigDecimal.valueOf(100.50), PaymentStatus.APPROVED, Instant.now());
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(paymentModel));

        Optional<Payment> result = paymentGateway.findById(paymentId);

        assertTrue(result.isPresent());
        assertEquals("txn123", result.get().getTransactionId());
        assertEquals("order123", result.get().getOrderId());
        assertEquals(BigDecimal.valueOf(100.50), result.get().getAmount());
        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void shouldReturnEmptyWhenPaymentNotFoundById() {
        String paymentId = "invalidPaymentId";
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        Optional<Payment> result = paymentGateway.findById(paymentId);

        assertFalse(result.isPresent());
        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void shouldFindPaymentByTransactionIdSuccessfully() {
        String transactionId = "txn123";
        PaymentModel paymentModel = new PaymentModel("payment123", "txn123", "order123", BigDecimal.valueOf(100.50), PaymentStatus.APPROVED, Instant.now());
        when(paymentRepository.findByTransactionId(transactionId)).thenReturn(Optional.of(paymentModel));

        Optional<Payment> result = paymentGateway.findByTransactionId(transactionId);

        assertTrue(result.isPresent());
        assertEquals("txn123", result.get().getTransactionId());
        assertEquals("order123", result.get().getOrderId());
        assertEquals(BigDecimal.valueOf(100.50), result.get().getAmount());
        verify(paymentRepository, times(1)).findByTransactionId(transactionId);
    }

    @Test
    void shouldReturnEmptyWhenPaymentNotFoundByTransactionId() {
        String transactionId = "invalidTxn";
        when(paymentRepository.findByTransactionId(transactionId)).thenReturn(Optional.empty());

        Optional<Payment> result = paymentGateway.findByTransactionId(transactionId);

        assertFalse(result.isPresent());
        verify(paymentRepository, times(1)).findByTransactionId(transactionId);
    }
}