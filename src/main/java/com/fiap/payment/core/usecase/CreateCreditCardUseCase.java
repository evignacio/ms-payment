package com.fiap.payment.core.usecase;

import com.fiap.payment.core.dto.CreateCreditCardDTO;
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

    public String execute(CreateCreditCardDTO input) {
        log.info("Creating credit card for customer: {}", input.custumerId());
        var creditCard = new CreditCard(
                input.custumerId(),
                input.custumerId(),
                input.number(),
                input.expirationDate()
        );
        var creditCardSave = creditCardGateway.save(creditCard);
        log.info("Credit card created successfully with token: {}", creditCardSave.getToken());
        return creditCardSave.getToken();
    }
}
