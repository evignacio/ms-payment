package com.fiap.payment.infrastructure.gateway;

import com.fiap.payment.core.entity.CreditCard;
import com.fiap.payment.core.gateway.CreditCardGateway;
import com.fiap.payment.infrastructure.mapper.CreditCardMapper;
import com.fiap.payment.infrastructure.repository.CreditCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class CreditCardGatewayImpl implements CreditCardGateway {

    private final CreditCardRepository creditCardRepository;

    public CreditCardGatewayImpl(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public Optional<CreditCard> findByToken(String token) {
        return creditCardRepository.findByToken(token)
                .map(CreditCardMapper::toEntity);
    }

    @Override
    public CreditCard save(CreditCard creditCard) {
        return CreditCardMapper.toEntity(creditCardRepository.save(CreditCardMapper.toModel(creditCard)));
    }
}
