package com.fiap.payment.core.usecase;

import com.fiap.payment.core.dto.CreatePaymentDTO;
import com.fiap.payment.core.dto.TokenCreditCardDTO;
import com.fiap.payment.core.entity.CreditCard;
import com.fiap.payment.core.entity.Payment;
import com.fiap.payment.core.gateway.CreditCardGateway;
import com.fiap.payment.core.gateway.PaymentGateway;
import com.fiap.payment.core.gateway.TransactionGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreatePaymentUseCaseTest {

    private TransactionGateway transactionGateway;
    private PaymentGateway paymentGateway;
    private CreditCardGateway creditCardGateway;
    private CreatePaymentUseCase createPaymentUseCase;

    @BeforeEach
    void setUp() {
        transactionGateway = mock(TransactionGateway.class);
        paymentGateway = mock(PaymentGateway.class);
        creditCardGateway = mock(CreditCardGateway.class);
        createPaymentUseCase = new CreatePaymentUseCase(transactionGateway, paymentGateway, creditCardGateway);
    }

    @Test
    void shouldCreatePaymentSuccessfully() {
        TokenCreditCardDTO tokenCreditCard = new TokenCreditCardDTO("token123", "123");
        CreatePaymentDTO input = new CreatePaymentDTO(tokenCreditCard, "order123", BigDecimal.valueOf(100.50));
        CreditCard creditCard = new CreditCard("123", "4111111111111111", "John Doe", "12/25", "token123");
        Payment payment = new Payment("txn123", "order123", BigDecimal.valueOf(100.50));

        when(creditCardGateway.findByToken("token123")).thenReturn(Optional.of(creditCard));
        when(transactionGateway.request(any(CreditCard.class), eq("123"), eq(BigDecimal.valueOf(100.50)))).thenReturn("txn123");
        when(paymentGateway.save(any(Payment.class))).thenReturn(payment);

        Payment result = createPaymentUseCase.execute(input);

        assertNotNull(result);
        assertEquals("txn123", result.getTransactionId());
        assertEquals("order123", result.getOrderId());
        assertEquals(BigDecimal.valueOf(100.50), result.getAmount());
        verify(creditCardGateway, times(1)).findByToken("token123");
        verify(transactionGateway, times(1)).request(any(CreditCard.class), eq("123"), eq(BigDecimal.valueOf(100.50)));
        verify(paymentGateway, times(1)).save(any(Payment.class));
    }

    @Test
    void shouldThrowExceptionWhenCreditCardNotFound() {
        TokenCreditCardDTO tokenCreditCard = new TokenCreditCardDTO("invalidToken", "123");
        CreatePaymentDTO input = new CreatePaymentDTO(tokenCreditCard, "order123", BigDecimal.valueOf(100.50));

        when(creditCardGateway.findByToken("invalidToken")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createPaymentUseCase.execute(input));
        assertEquals("Credit card not found", exception.getMessage());
        verify(creditCardGateway, times(1)).findByToken("invalidToken");
        verifyNoInteractions(transactionGateway);
        verifyNoInteractions(paymentGateway);
    }

    @Test
    void shouldThrowExceptionWhenTransactionGatewayFails() {
        TokenCreditCardDTO tokenCreditCard = new TokenCreditCardDTO("token123", "123");
        CreatePaymentDTO input = new CreatePaymentDTO(tokenCreditCard, "order123", BigDecimal.valueOf(100.50));
        CreditCard creditCard = new CreditCard("123", "4111111111111111", "John Doe", "12/25", "token123");

        when(creditCardGateway.findByToken("token123")).thenReturn(Optional.of(creditCard));
        when(transactionGateway.request(any(CreditCard.class), eq("123"), eq(BigDecimal.valueOf(100.50))))
                .thenThrow(new RuntimeException("Transaction failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> createPaymentUseCase.execute(input));
        assertEquals("Transaction failed", exception.getMessage());
        verify(creditCardGateway, times(1)).findByToken("token123");
        verify(transactionGateway, times(1)).request(any(CreditCard.class), eq("123"), eq(BigDecimal.valueOf(100.50)));
        verifyNoInteractions(paymentGateway);
    }
}