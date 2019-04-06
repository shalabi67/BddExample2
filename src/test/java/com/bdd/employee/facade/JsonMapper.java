package com.bdd.employee.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper<T> {
    public String toString(T object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(object);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public T toObject(String content, Class<T> classType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            T object = objectMapper.readValue(content, classType);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
