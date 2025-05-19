package com.fiap.payment.infrastructure.integration.rest;


import com.fiap.payment.core.entity.PaymentStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "order-service", url = "${rest-client.order.url}")
public interface OrderRestClient {

    @PostMapping("{orderId}/payment/callback")
    ResponseEntity<Void> notifyPaymentStatus(@PathVariable String orderId, @RequestHeader("payment-status") PaymentStatus paymentStatus);
}
