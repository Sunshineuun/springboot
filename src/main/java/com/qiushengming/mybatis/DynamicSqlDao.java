package com.qiushengming.mybatis;

import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public interface DynamicSqlDao {

    int executeSelectCountDynamic(String sql, Object params);

    int executeSelectCountDynamic(String sql);

    <K, V> List<Map<K, V>> executeSelectListDynamic(String sql, Object params);

    <K, V> List<Map<K, V>> executeSelectListDynamic(String sql);

    <K, V> Map<K, V> executeSelectOneDynamic(String sql, Object params);

    <K, V> Map<K, V> executeSelectOneDynamic(String sql);

    int executeUpdateDynamic(String sql, Object params);

    int executeUpdateDynamic(String sql);

    int executeInsertDynamic(String sql, Object params);

    int executeInsertDynamic(String sql);

    int executeDeleteDynamic(String sql, Object params);

    int executeDeleteDynamic(String sql);

    <K, V> List<Map<K, V>> executeSelectListDynamic(String sql, int offset,
        int limit, Object params);

    SqlSession getSqlSession();

}
