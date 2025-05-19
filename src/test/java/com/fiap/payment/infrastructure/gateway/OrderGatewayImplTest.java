package com.fiap.payment.infrastructure.gateway;

import com.fiap.payment.core.entity.PaymentStatus;
import com.fiap.payment.infrastructure.integration.rest.OrderRestClient;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderGatewayImplTest {

    private OrderRestClient orderRestClient;
    private OrderGatewayImpl orderGateway;

    @BeforeEach
    void setUp() {
        orderRestClient = mock(OrderRestClient.class);
        orderGateway = new OrderGatewayImpl(orderRestClient);
    }

    @Test
    void shouldNotifyPaymentStatusSuccessfully() {
        String orderId = "order123";
        PaymentStatus paymentStatus = PaymentStatus.APPROVED;

        orderGateway.notifyPaymentStatus(orderId, paymentStatus);

        verify(orderRestClient, times(1)).notifyPaymentStatus(orderId, paymentStatus);
    }

    @Test
    void shouldThrowExceptionWhenNotificationFails() {
        String orderId = "order123";
        PaymentStatus paymentStatus = PaymentStatus.DECLINED;

        doThrow(FeignException.class).when(orderRestClient).notifyPaymentStatus(orderId, paymentStatus);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                orderGateway.notifyPaymentStatus(orderId, paymentStatus));

        verify(orderRestClient, times(1)).notifyPaymentStatus(orderId, paymentStatus);
    }
}