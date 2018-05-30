package com.qiushengming.utils;

import com.google.gson.Gson;
import com.qiushengming.entity.BaseEntity;

import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/4/16
 */
public class Json {
    private static final Gson GSON = new Gson();

    public static String objectToJson(BaseEntity o) {
        return GSON.toJson(o);
    }

    public static Map stringToMap(String s) {
        return GSON.fromJson(s, Map.class);
    }
}
