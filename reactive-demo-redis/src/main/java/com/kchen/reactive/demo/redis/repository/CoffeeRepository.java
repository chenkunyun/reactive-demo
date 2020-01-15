package com.kchen.reactive.demo.redis.repository;

import com.kchen.reactive.demo.redis.entity.Coffee;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * @author chenkunyun
 * @date 2020/1/15
 */
@Repository
public class CoffeeRepository {

    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, Coffee> coffeeOps;
    private static final String COFFEE_HASH = "coffee";

    public CoffeeRepository(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, Coffee> coffeeOps) {
        this.factory = factory;
        this.coffeeOps = coffeeOps;
    }

    @PostConstruct
    public void loadData() {
        factory.getReactiveConnection().serverCommands().flushDb()
                .thenMany(clearHistory())
                .thenMany(initCoffeeList())
                .thenMany(coffeeOps.opsForHash().entries(COFFEE_HASH))
                .subscribe(entry -> System.out.println(entry));
    }

    private Mono<Boolean> clearHistory() {
        return coffeeOps.opsForHash().delete(COFFEE_HASH);
    }

    private Flux initCoffeeList() {
        return Flux.just("Jet Black Redis", "Darth Redis", "Black Alert Redis")
                .map(name -> new Coffee(UUID.randomUUID().toString(), name))
                .flatMap(coffee -> coffeeOps.opsForHash().put(COFFEE_HASH, coffee.getId(), coffee));
    }
}
