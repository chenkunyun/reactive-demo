package com.kchen.reactive.demo.redis.entity;

import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author chenkunyun
 * @date 2020/1/15
 */
@Data
public class Response {

    private Long code;
    private String msg;
    private Object data;

    public static Mono<Response> success(Flux data) {
        return data.collectList().map(value -> new Response(0L, "success", value));
    }

    public static Mono<Response> success(Mono data) {
        return data.map(value -> new Response(0L, "success", value));
    }

    private Response(Long code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
