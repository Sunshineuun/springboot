package com.qiushengming.mybatis.builder;


import com.qiushengming.annotation.Table;

/**
 * @author MinMin
 */
public class StatementKeyGenerator {

    public static String generateSelectStatementKey(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) { return null; }
        return table.resultMapId() + "Select";
    }
}
