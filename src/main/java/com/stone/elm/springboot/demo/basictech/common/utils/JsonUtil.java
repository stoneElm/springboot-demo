package com.stone.elm.springboot.demo.basictech.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.internal.bind.TimeTypeAdapter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    private static ObjectMapper objectMapper;

    private JsonUtil() {

    }

    public static Map convertJsonToMap(String jsonString) {
        try {
            return (Map)objectMapper.readValue(jsonString, Map.class);
        } catch (Exception var1) {
            throw new RuntimeException(var1);
        }
    }

    public static String convertMapToJson(Map<String, Object> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static <T> T convertJsonToObject(String jsonString, Class<T> modeClass) {
        try {
            return objectMapper.readValue(jsonString, modeClass);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static Map<String, String> getMap4Json(String jsonString) {
        try {
            return (Map)convertJsonToObject(jsonString, new TypeReference<Map<String, String>>(){});
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static Map<String, Object> getMap4JsonObject(String jsonString) {
        try {
            return (Map)convertJsonToObject(jsonString, new TypeReference<Map<String, Object>>(){});
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static String getJsonVale(String jsonStr, String key) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonStr);
            JsonNode keyNode = rootNode.get(key);
            return keyNode != null? keyNode.asText(): null;
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static String convertObjectToJson(Object javaObj) {
        try {
            return objectMapper.writeValueAsString(javaObj);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static <T> List<T> convertObjectToList(String jsonString, Class<T> pojoClass) {
        return JSONArray.parseArray(jsonString, pojoClass);
    }

    public static <T> T convertJsonToObject(String jsonString, TypeReference<?> typeReference) {
        try {
            return (T) objectMapper.readValue(jsonString, typeReference);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    static {
        GsonBuilder gb = new GsonBuilder();
        gb.setPrettyPrinting().disableHtmlEscaping().registerTypeAdapter(Timestamp.class, new TimeTypeAdapter());
        gb.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        gb.setDateFormat("yyyy_mm_dd HH:mm:ss");
        gb.create();
        DefaultSerializerProvider.Impl sp = new DefaultSerializerProvider.Impl();
        objectMapper = new ObjectMapper((JsonFactory) null, sp, (DefaultDeserializationContext) null);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy_mm_dd HH:mm:ss"));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
    }

}
