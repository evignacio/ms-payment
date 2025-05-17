package com.fiap.payment.infrastructure.repository.model;

import com.fiap.payment.core.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@Document("payments")
public class PaymentModel {
    private String id;
    private String transactionId;
    private String orderId;
    private BigDecimal amount;
    private PaymentStatus status;
    private Instant createdAt;
}
