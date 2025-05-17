package com.fiap.payment.infrastructure.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document("creditCards")
public class CreditCardModel {
    private String customerId;
    private String number;
    private String holderName;
    private String expirationDate;
    private String token;
}
