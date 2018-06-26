package com.qiushengming.enums;

/**
 * @author qiushengming
 * @date 2018/6/26
 */
public enum SQLDialect {
    /**
     * mysql
     */
    MYSQL("MYSQL"),
    /**
     * oracle
     */
    ORACLE("ORACLE");

    private String value;

    SQLDialect(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
