package com.fiap.payment.infrastructure.repository;

import com.fiap.payment.infrastructure.repository.model.PaymentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends MongoRepository<PaymentModel, String> {
    Optional<PaymentModel> findByTransactionId(String transactionId);
}
