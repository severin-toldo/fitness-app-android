package com.stoldo.fitness_app_android.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil<T> {
    private ObjectMapper objectMapper = new ObjectMapper();


    public String toJson(T obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    public T fromJson(String json) throws Exception {
        return objectMapper.readValue(json, new TypeReference<T>(){});
    }
}
