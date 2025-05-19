package com.fiap.payment.infrastructure.controller;

import com.fiap.payment.core.dto.CreatePaymentDTO;
import com.fiap.payment.core.dto.TokenCreditCardDTO;
import com.fiap.payment.core.entity.Payment;
import com.fiap.payment.core.usecase.CreatePaymentUseCase;
import com.fiap.payment.core.usecase.FindPaymentUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreatePaymentUseCase createPaymentUseCase;

    @MockitoBean
    private FindPaymentUseCase findPaymentUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePaymentSuccessfully() throws Exception {
        CreatePaymentDTO request = new CreatePaymentDTO(new TokenCreditCardDTO("txn123",  "123"), "order123", BigDecimal.valueOf(100.50));
        Payment createdPayment = new Payment("txn123", "order123", BigDecimal.valueOf(100.50));

        when(createPaymentUseCase.execute(request)).thenReturn(createdPayment);

        mockMvc.perform(post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("txn123"))
                .andExpect(jsonPath("$.orderId").value("order123"))
                .andExpect(jsonPath("$.amount").value(100.50));

        verify(createPaymentUseCase, times(1)).execute(request);
    }

    @Test
    void shouldFindPaymentByIdSuccessfully() throws Exception {
        String paymentId = "payment123";
        Payment payment = new Payment("txn123", "order123", BigDecimal.valueOf(100.50));

        when(findPaymentUseCase.execute(paymentId)).thenReturn(payment);

        mockMvc.perform(get("/api/v1/payments/{id}", paymentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("txn123"))
                .andExpect(jsonPath("$.orderId").value("order123"))
                .andExpect(jsonPath("$.amount").value(100.50));

        verify(findPaymentUseCase, times(1)).execute(paymentId);
    }
}