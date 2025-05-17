package com.fiap.payment.core.usecase;

import com.fiap.payment.core.entity.CreditCard;
import com.fiap.payment.core.gateway.CreditCardGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateCreditCardUseCase {

    private final CreditCardGateway creditCardGateway;

    public CreateCreditCardUseCase(CreditCardGateway creditCardGateway) {
        this.creditCardGateway = creditCardGateway;
    }

    public String execute(CreditCard creditCard) {
        log.info("Creating credit card for customer: {}", creditCard.getCustomerId());
        var creditCardSave = creditCardGateway.save(creditCard);
        log.info("Credit card created successfully with token: {}", creditCardSave.getToken());
        return creditCardSave.getToken();
    }
}
