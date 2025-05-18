package com.fiap.payment.core.dto;

public record CreateCreditCardDTO(String custumerId, String number, String holderName, String expirationDate) {
    @Override
    public String toString() {
        return "CreateCreditCardDTO{" +
                "custumerId='" + custumerId + '\'' +
                ", number=***" + '\'' +
                ", holderName='" + holderName + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }
}
