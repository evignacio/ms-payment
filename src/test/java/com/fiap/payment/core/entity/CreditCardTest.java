package com.fiap.payment.core.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {

    @Test
    void shouldCreateCreditCardWithValidData() {
        CreditCard creditCard = new CreditCard("123", "4111111111111111", "John Doe", "12/25");

        assertNotNull(creditCard.getToken());
        assertEquals("123", creditCard.getCustomerId());
        assertEquals("4111111111111111", creditCard.getNumber());
        assertEquals("John Doe", creditCard.getHolderName());
        assertEquals("12/25", creditCard.getExpirationDate());
    }

    @Test
    void shouldThrowExceptionForInvalidCustomerId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new CreditCard(null, "4111111111111111", "John Doe", "12/25"));

        assertEquals("customerId null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new CreditCard("123", "123", "John Doe", "12/25"));

        assertEquals("number null or invalid", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidHolderName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new CreditCard("123", "4111111111111111", "Jo", "12/25"));

        assertEquals("holderName null or invalid", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidExpirationDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new CreditCard("123", "4111111111111111", "John Doe", "1225"));

        assertEquals("expirationDate null or invalid", exception.getMessage());
    }

    @Test
    void shouldSetValidToken() {
        CreditCard creditCard = new CreditCard("123", "4111111111111111", "John Doe", "12/25", "custom-token");

        assertEquals("custom-token", creditCard.getToken());
    }

    @Test
    void shouldThrowExceptionForInvalidToken() {
        CreditCard creditCard = new CreditCard("123", "4111111111111111", "John Doe", "12/25");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                creditCard.setToken(""));

        assertEquals("token null or empty", exception.getMessage());
    }
}