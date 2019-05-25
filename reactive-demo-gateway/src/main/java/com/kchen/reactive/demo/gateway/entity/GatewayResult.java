package com.kchen.reactive.demo.gateway.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * @author chenkunyun
 * @date 2019-05-02
 */
@Data
@JsonPropertyOrder({"code", "msg", "sub_code", "sub_msg", "data"})
public class GatewayResult {

    private Long code;
    private String msg;

    @JsonProperty("sub_code")
    private Long subCode;

    @JsonProperty("sub_msg")
    private String subMsg;

    private Object data;
}
