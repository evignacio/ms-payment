package com.fiap.payment.infrastructure.controller;

import com.fiap.payment.core.dto.CreatePaymentDTO;
import com.fiap.payment.core.entity.Payment;
import com.fiap.payment.core.usecase.CreatePaymentUseCase;
import com.fiap.payment.core.usecase.FindPaymentUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final CreatePaymentUseCase createPaymentUseCase;
    private final FindPaymentUseCase findPaymentUseCase;

    public PaymentController(CreatePaymentUseCase createPaymentUseCase, FindPaymentUseCase findPaymentUseCase) {
        this.createPaymentUseCase = createPaymentUseCase;
        this.findPaymentUseCase = findPaymentUseCase;
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody CreatePaymentDTO request) {
        var createdPayment = createPaymentUseCase.execute(request);
        return ResponseEntity.ok(createdPayment);
    }

    @GetMapping("{id}")
    public ResponseEntity<Payment> findPayment(@PathVariable String id) {
        var payment = findPaymentUseCase.execute(id);
        return ResponseEntity.ok(payment);
    }
}
