package com.fiap.payment.core.usecase;

import com.fiap.payment.core.dto.CreatePaymentDTO;
import com.fiap.payment.core.entity.Payment;
import com.fiap.payment.core.gateway.CreditCardGateway;
import com.fiap.payment.core.gateway.PaymentGateway;
import com.fiap.payment.core.gateway.TransactionGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreatePaymentUseCase {

    private final TransactionGateway transactionGateway;
    private final PaymentGateway paymentGateway;
    private final CreditCardGateway creditCardGateway;

    public CreatePaymentUseCase(TransactionGateway transactionGateway, PaymentGateway paymentGateway, CreditCardGateway creditCardGateway) {
        this.transactionGateway = transactionGateway;
        this.paymentGateway = paymentGateway;
        this.creditCardGateway = creditCardGateway;
    }

    public void execute(CreatePaymentDTO input) throws InterruptedException {
        log.info("Creating payment for orderId: {}", input.orderId());
        var creditCardOpt = creditCardGateway.findByToken(input.tokenCreditCard().token());
        if (creditCardOpt.isEmpty()) {
            log.error("Credit card not found for token: {}", input.tokenCreditCard().token());
            throw new IllegalArgumentException("Credit card not found");
        }
        var transactionId = transactionGateway.request(creditCardOpt.get(), input.tokenCreditCard().cvv(), input.amount());
        var payment = new Payment(
                transactionId,
                input.orderId(),
                input.amount()
        );
        paymentGateway.save(payment);
        log.info("Payment create successfully for orderId: {}", input.orderId());
    }
}
