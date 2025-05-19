package com.fiap.payment.infrastructure.controller;

import com.fiap.payment.core.dto.CreateCreditCardDTO;
import com.fiap.payment.core.usecase.CreateCreditCardUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreditCardController.class)
class CreditCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateCreditCardUseCase createCreditCardUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCreditCardSuccessfully() throws Exception {
        CreateCreditCardDTO request = new CreateCreditCardDTO("4111111111111111", "John Doe", "12/25", "123");
        String expectedToken = "token123";

        when(createCreditCardUseCase.execute(request)).thenReturn(expectedToken);

        mockMvc.perform(post("/api/v1/credit-card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(expectedToken));

        verify(createCreditCardUseCase, times(1)).execute(request);
    }
}