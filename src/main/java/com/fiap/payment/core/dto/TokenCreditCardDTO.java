package com.fiap.payment.core.dto;

public record TokenCreditCardDTO(String token, String cvv) {
    @Override
    public String toString() {
        return "TokenCreditCardDTO{" +
                "tokenCreditCard='" + token + '\'' +
                ", cvv=***" + '\'' +
                '}';
    }
}
