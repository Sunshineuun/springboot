package com.qiushengming.utils;

import com.google.gson.Gson;
import com.qiushengming.entity.BaseEntity;

/**
 * @author qiushengming
 * @date 2018/4/16
 */
public class Json {
    private static final Gson GSON = new Gson();

    public static String toJson(BaseEntity o) {
        return GSON.toJson(o);
    }
}
