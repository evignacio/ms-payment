package com.fiap.payment.core.entity;

import java.math.BigDecimal;
import java.time.Instant;

public class Payment {
    private String id;
    private String transactionId;
    private String orderId;
    private BigDecimal amount;
    private PaymentStatus status;
    private Instant createdAt;


    public Payment(String transactionId, String orderId, BigDecimal amount) {
        this.id = java.util.UUID.randomUUID().toString();
        setTransactionId(transactionId);
        setOrderId(orderId);
        setAmount(amount);
        this.status = PaymentStatus.IN_PROGRESS;
        this.createdAt = Instant.now();
    }

    public Payment(String id, String transactionId, String orderId, BigDecimal amount, PaymentStatus status, Instant createdAt) {
        setId(id);
        setTransactionId(transactionId);
        setOrderId(orderId);
        setAmount(amount);
        setStatus(status);
        setCreatedAt(createdAt);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("ID cannot be null or empty");
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        if (transactionId == null || transactionId.isEmpty())
            throw new IllegalArgumentException("transactionId cannot be null or empty");
        this.transactionId = transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        if (orderId == null || orderId.isEmpty())
            throw new IllegalArgumentException("orderId cannot be null or empty");
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("amount cannot be null or less than or equal to zero");
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        if (status == null)
            throw new IllegalArgumentException("status cannot be null");
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        if (createdAt == null)
            throw new IllegalArgumentException("createdAt cannot be null");
        this.createdAt = createdAt;
    }

    public void defineAsApproved() {
        this.status = PaymentStatus.APPROVED;
    }

    public void defineAsDeclined() {
        this.status = PaymentStatus.DECLINED;
    }
}
