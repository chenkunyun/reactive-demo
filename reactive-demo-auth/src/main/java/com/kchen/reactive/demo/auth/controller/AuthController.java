package com.kchen.reactive.demo.auth.controller;

import com.kchen.reactive.demo.common.entity.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;

/**
 * @author chenkunyun
 * @date 2019-05-02
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping("")
    public Mono<ApiResult> auth() {
        ApiResult data = new ApiResult();
        data.setCode(0L);
        data.setMsg("ok");
        HashMap<String, String> map = new HashMap<>(1);
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        data.setData(map);
        return Mono.just(data).delayElement(Duration.ofMillis(5));
    }
}
