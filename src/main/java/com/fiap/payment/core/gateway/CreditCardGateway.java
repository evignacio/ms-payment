package com.fiap.payment.core.gateway;

import com.fiap.payment.core.entity.CreditCard;

import java.util.Optional;

public interface CreditCardGateway {
    Optional<CreditCard> findByToken(String token);

    CreditCard save(CreditCard creditCard);
}
