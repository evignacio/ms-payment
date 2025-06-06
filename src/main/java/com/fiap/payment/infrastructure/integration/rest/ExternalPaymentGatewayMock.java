package com.fiap.payment.infrastructure.integration.rest;

import com.fiap.payment.core.entity.CreditCard;
import com.fiap.payment.core.usecase.UpdatePaymentStatusUseCase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class ExternalPaymentGatewayMock {

    private final UpdatePaymentStatusUseCase updatePaymentStatusUseCase;

    public ExternalPaymentGatewayMock(UpdatePaymentStatusUseCase updatePaymentStatusUseCase) {
        this.updatePaymentStatusUseCase = updatePaymentStatusUseCase;
    }

    public String requestTransaction(CreditCard creditCard, String cvv, String amount) {
        log.info("Requesting transaction for credit card: {}", creditCard.getToken());
        var transactionId = UUID.randomUUID().toString();
        CompletableFuture.runAsync(() -> callbackTransaction(transactionId));
        log.info("Transaction request created successfully for credit card: {}", creditCard.getToken());
        return transactionId;
    }

    @SneakyThrows
    public void callbackTransaction(String orderId) {
        log.info("Callback transaction for orderId: {}", orderId);
        Thread.sleep(20000);
        updatePaymentStatusUseCase.execute(orderId, "GRANTED");
    }
}
