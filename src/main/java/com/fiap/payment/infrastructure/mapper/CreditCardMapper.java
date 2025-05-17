package com.fiap.payment.infrastructure.mapper;

import com.fiap.payment.core.entity.CreditCard;
import com.fiap.payment.infrastructure.repository.model.CreditCardModel;

public abstract class CreditCardMapper {

    private CreditCardMapper() {
        // Private constructor to prevent instantiation
    }

    public static CreditCard toEntity(CreditCardModel model) {
        return new CreditCard(
                model.getCustomerId(),
                model.getNumber(),
                model.getHolderName(),
                model.getExpirationDate(),
                model.getToken()
        );
    }

    public static CreditCardModel toModel(CreditCard entity) {
        return new CreditCardModel(
                entity.getCustomerId(),
                entity.getNumber(),
                entity.getHolderName(),
                entity.getExpirationDate(),
                entity.getToken()
        );
    }
}
