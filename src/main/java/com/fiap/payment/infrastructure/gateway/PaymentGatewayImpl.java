package com.fiap.payment.infrastructure.gateway;

import com.fiap.payment.core.entity.Payment;
import com.fiap.payment.core.gateway.PaymentGateway;
import com.fiap.payment.infrastructure.mapper.PaymentMapper;
import com.fiap.payment.infrastructure.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class PaymentGatewayImpl implements PaymentGateway {

    private final PaymentRepository paymentRepository;

    public PaymentGatewayImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment save(Payment payment) {
        return PaymentMapper.toEntty(paymentRepository.save(PaymentMapper.toModel(payment)));
    }

    @Override
    public Optional<Payment> findByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId)
                .map(PaymentMapper::toEntty);
    }
}
