package com.fiap.payment.infrastructure.controller;

import com.fiap.payment.core.dto.CreateCreditCardDTO;
import com.fiap.payment.core.usecase.CreateCreditCardUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/credit-card")
public class CreditCardController {

    private final CreateCreditCardUseCase createCreditCardUseCase;

    public CreditCardController(CreateCreditCardUseCase createCreditCardUseCase) {
        this.createCreditCardUseCase = createCreditCardUseCase;
    }

    @PostMapping
    public ResponseEntity<String> createCreditCard(@RequestBody CreateCreditCardDTO request) {
        var token = createCreditCardUseCase.execute(request);
        return ResponseEntity.ok(token);
    }
}
