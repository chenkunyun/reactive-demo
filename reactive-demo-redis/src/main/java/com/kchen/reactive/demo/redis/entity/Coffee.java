package com.kchen.reactive.demo.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenkunyun
 * @date 2020/1/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {
    private String id;
    private String name;
}
