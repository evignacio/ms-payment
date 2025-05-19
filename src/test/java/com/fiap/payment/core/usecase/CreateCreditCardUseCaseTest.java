package com.fiap.payment.core.usecase;

import com.fiap.payment.core.dto.CreateCreditCardDTO;
import com.fiap.payment.core.entity.CreditCard;
import com.fiap.payment.core.gateway.CreditCardGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateCreditCardUseCaseTest {

    private CreditCardGateway creditCardGateway;
    private CreateCreditCardUseCase createCreditCardUseCase;

    @BeforeEach
    void setUp() {
        creditCardGateway = mock(CreditCardGateway.class);
        createCreditCardUseCase = new CreateCreditCardUseCase(creditCardGateway);
    }

    @Test
    void shouldCreateCreditCardSuccessfully() {
        CreateCreditCardDTO input = new CreateCreditCardDTO("123", "4111111111111111", "John Doe", "12/25");
        CreditCard savedCreditCard = new CreditCard("123", "4111111111111111", "John Doe", "12/25", "token123");

        when(creditCardGateway.save(any(CreditCard.class))).thenReturn(savedCreditCard);

        String token = createCreditCardUseCase.execute(input);

        assertEquals("token123", token);
        verify(creditCardGateway, times(1)).save(any(CreditCard.class));
    }

    @Test
    void shouldThrowExceptionWhenGatewayFails() {
        CreateCreditCardDTO input = new CreateCreditCardDTO("123", "4111111111111111", "John Doe", "12/25");

        when(creditCardGateway.save(any(CreditCard.class))).thenThrow(new RuntimeException("Gateway error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> createCreditCardUseCase.execute(input));
        assertEquals("Gateway error", exception.getMessage());
        verify(creditCardGateway, times(1)).save(any(CreditCard.class));
    }
}