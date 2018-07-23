package com.qiushengming.annotation;

import com.qiushengming.enums.MySqlDefault;
import org.apache.ibatis.type.JdbcType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Column {
    String value();

    JdbcType jdbcType() default JdbcType.VARCHAR;

    boolean isAdd() default true;

    boolean isUpdate() default true;

    String resultHandler() default "";

    String chineseName() default "";

    MySqlDefault defaultValue() default MySqlDefault.BLANK;
}
