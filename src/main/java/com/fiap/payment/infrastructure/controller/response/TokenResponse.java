package com.fiap.payment.infrastructure.controller.response;

public record TokenResponse(String token) {
    @Override
    public String toString() {
        return "TokenResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}
