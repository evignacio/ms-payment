package com.fiap.payment.core.entity;

import java.util.UUID;

public class CreditCard {
    private String customerId;
    private String number;
    private String holderName;
    private String expirationDate;
    private String token;

    public CreditCard(String customerId, String number, String holderName, String expirationDate) {
        setCustomerId(customerId);
        setNumber(number);
        setHolderName(holderName);
        setExpirationDate(expirationDate);
        this.token = UUID.randomUUID().toString();
    }

    public CreditCard(String customerId, String number, String holderName, String expirationDate, String token) {
        setCustomerId(customerId);
        setNumber(number);
        setHolderName(holderName);
        setExpirationDate(expirationDate);
        setToken(token);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            throw new IllegalArgumentException("customerId null or empty");
        }
        this.customerId = customerId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        if (number == null || number.length() < 13 || number.length() > 16) {
            throw new IllegalArgumentException("number null or invalid");
        }
        this.number = number;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        if (holderName == null || holderName.length() < 3) {
            throw new IllegalArgumentException("holderName null or invalid");
        }
        this.holderName = holderName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        if (expirationDate == null || !expirationDate.matches("\\d{2}/\\d{2}")) {
            throw new IllegalArgumentException("expirationDate null or invalid");
        }
        this.expirationDate = expirationDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("token null or empty");
        }
        this.token = token;
    }
}
