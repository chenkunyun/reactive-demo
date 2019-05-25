package com.kchen.reactive.demo.common.entity;

import lombok.Data;

/**
 * @author chenkunyun
 * @date 2019-05-02
 */

@Data
public class ApiResult {
    private Long code;
    private String msg;
    private Object data;
}
