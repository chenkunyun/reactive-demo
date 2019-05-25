package com.kchen.reactive.demo.gateway.exception;

/**
 * @author chenkunyun
 * @date 2019-05-25
 */
public class GatewayException extends RuntimeException {
    public GatewayException(String message) {
        super(message);
    }
}
