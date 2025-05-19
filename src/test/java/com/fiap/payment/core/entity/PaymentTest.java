package com.fiap.payment.core.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void shouldCreatePaymentWithValidData() {
        Payment payment = new Payment("txn123", "order123", BigDecimal.valueOf(100.50));

        assertNotNull(payment.getId());
        assertEquals("txn123", payment.getTransactionId());
        assertEquals("order123", payment.getOrderId());
        assertEquals(BigDecimal.valueOf(100.50), payment.getAmount());
        assertEquals(PaymentStatus.IN_PROGRESS, payment.getStatus());
        assertNotNull(payment.getCreatedAt());
    }

    @Test
    void shouldThrowExceptionForInvalidTransactionId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Payment(null, "order123", BigDecimal.valueOf(100.50)));

        assertEquals("transactionId cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidOrderId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Payment("txn123", null, BigDecimal.valueOf(100.50)));

        assertEquals("orderId cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Payment("txn123", "order123", BigDecimal.valueOf(-10)));

        assertEquals("amount cannot be null or less than or equal to zero", exception.getMessage());
    }

    @Test
    void shouldDefinePaymentAsApproved() {
        Payment payment = new Payment("txn123", "order123", BigDecimal.valueOf(100.50));
        payment.defineAsApproved();

        assertEquals(PaymentStatus.APPROVED, payment.getStatus());
    }

    @Test
    void shouldDefinePaymentAsDeclined() {
        Payment payment = new Payment("txn123", "order123", BigDecimal.valueOf(100.50));
        payment.defineAsDeclined();

        assertEquals(PaymentStatus.DECLINED, payment.getStatus());
    }

    @Test
    void shouldCreatePaymentWithAllFields() {
        Instant now = Instant.now();
        Payment payment = new Payment("id123", "txn123", "order123", BigDecimal.valueOf(100.50), PaymentStatus.APPROVED, now);

        assertEquals("id123", payment.getId());
        assertEquals("txn123", payment.getTransactionId());
        assertEquals("order123", payment.getOrderId());
        assertEquals(BigDecimal.valueOf(100.50), payment.getAmount());
        assertEquals(PaymentStatus.APPROVED, payment.getStatus());
        assertEquals(now, payment.getCreatedAt());
    }
}