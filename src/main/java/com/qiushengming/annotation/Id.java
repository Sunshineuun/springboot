package com.qiushengming.annotation;

import org.apache.ibatis.type.JdbcType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Id {
    String value();

    JdbcType jdbcType() default JdbcType.VARCHAR;
}
