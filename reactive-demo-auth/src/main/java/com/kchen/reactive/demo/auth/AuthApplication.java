package com.kchen.reactive.demo.auth;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author chenkunyun
 * @date 2019-05-02
 */
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AuthApplication.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }
}
