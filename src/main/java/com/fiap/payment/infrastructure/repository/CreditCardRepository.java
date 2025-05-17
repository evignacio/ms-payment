package com.fiap.payment.infrastructure.repository;

import com.fiap.payment.infrastructure.repository.model.CreditCardModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends MongoRepository<CreditCardModel, String> {
    Optional<CreditCardModel> findByToken(String token);
}
