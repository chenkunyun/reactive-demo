package com.kchen.reactive.demo.redis.controller;

import com.kchen.reactive.demo.redis.entity.Coffee;
import com.kchen.reactive.demo.redis.entity.Response;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author chenkunyun
 * @date 2020/1/15
 */
@RestController
public class CoffeeController {

    private final ReactiveRedisOperations<String, Coffee> coffeeOps;

    CoffeeController(ReactiveRedisOperations<String, Coffee> coffeeOps) {
        this.coffeeOps = coffeeOps;
    }

    @GetMapping("/coffees")
    public Mono<Response> all() {
        Flux<Map.Entry<Object, Object>> coffeeFlux = coffeeOps.opsForHash().entries("coffee");
        return Response.success(coffeeFlux);
    }
}
