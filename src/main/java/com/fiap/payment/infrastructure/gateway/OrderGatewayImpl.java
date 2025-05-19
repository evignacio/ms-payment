package com.fiap.payment.infrastructure.gateway;

import com.fiap.payment.core.entity.PaymentStatus;
import com.fiap.payment.core.gateway.OrderGateway;
import com.fiap.payment.infrastructure.integration.rest.OrderRestClient;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderGatewayImpl implements OrderGateway {

    private final OrderRestClient orderRestClient;

    public OrderGatewayImpl(OrderRestClient orderRestClient) {
        this.orderRestClient = orderRestClient;
    }

    @Override
    public void notifyPaymentStatus(String orderId, PaymentStatus paymentStatus) {
        try {
            log.info("Notifying order service about payment status, orderId: {}, paymentStatus: {}", orderId, paymentStatus);
            orderRestClient.notifyPaymentStatus(orderId, paymentStatus);
            log.info("Order service notified successfully, orderId: {}, paymentStatus: {}", orderId, paymentStatus);
        } catch (FeignException exception) {
            log.error("Error notifying order service about payment status, orderId: {}, paymentStatus: {}", orderId, paymentStatus, exception);
            throw new IllegalStateException("Error notifying order service about payment status", exception);
        }
    }
}
