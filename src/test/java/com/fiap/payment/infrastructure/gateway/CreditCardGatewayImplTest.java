package com.fiap.payment.infrastructure.gateway;

import com.fiap.payment.core.entity.CreditCard;
import com.fiap.payment.infrastructure.mapper.CreditCardMapper;
import com.fiap.payment.infrastructure.repository.CreditCardRepository;
import com.fiap.payment.infrastructure.repository.model.CreditCardModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditCardGatewayImplTest {

    private CreditCardRepository creditCardRepository;
    private CreditCardGatewayImpl creditCardGateway;

    @BeforeEach
    void setUp() {
        creditCardRepository = mock(CreditCardRepository.class);
        creditCardGateway = new CreditCardGatewayImpl(creditCardRepository);
    }

    @Test
    void shouldFindCreditCardByTokenSuccessfully() {
        String token = "token123";
        CreditCardModel creditCardModel = new CreditCardModel("123", "4111111111111111", "John Doe", "12/25", "token123");
        when(creditCardRepository.findByToken(token)).thenReturn(Optional.of(creditCardModel));

        Optional<CreditCard> result = creditCardGateway.findByToken(token);

        assertTrue(result.isPresent());
        assertEquals("4111111111111111", result.get().getNumber());
        assertEquals("John Doe", result.get().getHolderName());
        assertEquals("12/25", result.get().getExpirationDate());
        assertEquals("token123", result.get().getToken());
        verify(creditCardRepository, times(1)).findByToken(token);
    }

    @Test
    void shouldReturnEmptyWhenCreditCardNotFoundByToken() {
        String token = "invalidToken";
        when(creditCardRepository.findByToken(token)).thenReturn(Optional.empty());

        Optional<CreditCard> result = creditCardGateway.findByToken(token);

        assertFalse(result.isPresent());
        verify(creditCardRepository, times(1)).findByToken(token);
    }

    @Test
    void shouldSaveCreditCardSuccessfully() {
        CreditCard creditCard = new CreditCard("123", "4111111111111111", "John Doe", "12/25", "token123");
        CreditCardModel creditCardModel = CreditCardMapper.toModel(creditCard);
        when(creditCardRepository.save(creditCardModel)).thenReturn(creditCardModel);

        CreditCard result = creditCardGateway.save(creditCard);

        assertNotNull(result);
        assertEquals("4111111111111111", result.getNumber());
        assertEquals("John Doe", result.getHolderName());
        assertEquals("12/25", result.getExpirationDate());
        assertEquals("token123", result.getToken());
        verify(creditCardRepository, times(1)).save(creditCardModel);
    }
}