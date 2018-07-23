package com.qiushengming.enums;

public enum MySqlDefault {
    /**
     * 空白
     */
    BLANK("''"),
    /**
     * 日期
     */
    CURRENT_TIMESTAMP("CURRENT_TIMESTAMP");

    private String value;

    MySqlDefault(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
