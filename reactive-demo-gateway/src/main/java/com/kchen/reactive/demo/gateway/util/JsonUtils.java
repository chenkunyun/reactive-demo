package com.kchen.reactive.demo.gateway.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kchen.reactive.demo.common.entity.ApiResult;
import com.kchen.reactive.demo.gateway.entity.GatewayResult;

import java.io.IOException;

/**
 * @author chenkunyun
 * @date 2019-05-02
 */
public final class JsonUtils {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private final static ObjectReader OBJECT_READER = OBJECT_MAPPER.readerFor(ApiResult.class);
    private final static ObjectWriter OBJECT_WRITER = OBJECT_MAPPER.writerFor(GatewayResult.class);

    public static ApiResult read(byte[] bytes) throws IOException {
        return OBJECT_READER.readValue(bytes);
    }

    public static byte[] write(GatewayResult gatewayResult) throws JsonProcessingException {
        return OBJECT_WRITER.writeValueAsBytes(gatewayResult);
    }
}
