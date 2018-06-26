package com.qiushengming.mybatis;

import com.qiushengming.enums.SQLDialect;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public interface DynamicSqlDao {

    /**
     * count查询
     *
     * @param sql    sql
     * @param params 参数
     * @return int
     */
    int executeSelectCountDynamic(String sql, Object params);

    /**
     * 无参数的count查询
     *
     * @param sql sql
     * @return int
     */
    int executeSelectCountDynamic(String sql);

    /**
     * 查询返回map集合
     *
     * @param sql    sql
     * @param params 参数
     * @param <K>    key
     * @param <V>    value
     * @return list map集合
     */
    <K, V> List<Map<K, V>> executeSelectListDynamic(String sql, Object params);

    /**
     * 查询返回 list 实体集合
     *
     * @param clazz  实体的Class
     * @param sql    sql
     * @param params 参数
     * @param <T>    实体
     * @return list 实体集合
     */
    <T> List<T> executeSelectListDynamic(Class<?> clazz, String sql,
        Object params);

    /**
     * 不带参数的查询，返回list map集合
     *
     * @param sql sql
     * @param <K> key
     * @param <V> value
     * @return map集合
     */
    <K, V> List<Map<K, V>> executeSelectListDynamic(String sql);

    /**
     * 带参数的，单条数据查询，返回map集合
     *
     * @param sql    查询sql
     * @param params 参数
     * @param <K>    key
     * @param <V>    value
     * @return 返回一个map
     */
    <K, V> Map<K, V> executeSelectOneDynamic(String sql, Object params);

    /**
     * 不带参数的，单条数据查询，返回map集合
     *
     * @param sql 查询sql
     * @param <K> key
     * @param <V> value
     * @return 返回一个map
     */
    <K, V> Map<K, V> executeSelectOneDynamic(String sql);

    /**
     * 带参数的sql更新
     *
     * @param sql    sql
     * @param params 参数
     * @return 更新成功的数量
     */
    int executeUpdateDynamic(String sql, Object params);

    /**
     * 不带参数的sql更新
     *
     * @param sql sql
     * @return 更新成功的数量
     */
    int executeUpdateDynamic(String sql);

    /**
     * 带参数的sql插入
     *
     * @param sql    sql
     * @param params 参数
     * @return 插入成功的数量
     */
    int executeInsertDynamic(String sql, Object params);

    /**
     * 不带参数的sql插入
     *
     * @param sql sql
     * @return 插入成功的数量
     */
    int executeInsertDynamic(String sql);

    /**
     * 带参数的sql删除
     *
     * @param sql    sql
     * @param params 参数
     * @return 删除成功的数量
     */
    int executeDeleteDynamic(String sql, Object params);

    /**
     * 不带参数的sql删除
     *
     * @param sql sql
     * @return 删除成功的数量
     */
    int executeDeleteDynamic(String sql);

    /**
     * 分页查询，返回list map
     *
     * @param sql    sql
     * @param offset 偏移量
     * @param limit  限定长度
     * @param params 参数
     * @param <K>    key
     * @param <V>    value
     * @return list map
     */
    <K, V> List<Map<K, V>> executeSelectListDynamic(String sql, int offset,
        int limit, Object params);

    /**
     * 分页查询，返回list实体
     *
     * @param clazz  实体的class
     * @param sql    sql
     * @param offset 偏移量
     * @param limit  限定长度
     * @param params 参数
     * @param <T>    实体
     * @return 返回list实体
     */
    <T> List<T> executeSelectListDynamic(Class<?> clazz, String sql, int offset,
        int limit, Object params);

    /**
     * 获取sqlsession <br>
     * 多数据源返回哪个session <br>
     * 默认取MYSQL <br>
     * @param param 参数，多数据源返回哪个session。key=SQLSESSION
     * @return sqlSession
     */
    SqlSession getSqlSession(Object param);

    /**
     * {@link DynamicSqlDao#getSqlSession(Object)}
     * @param dialect {@link SQLDialect}
     * @return sqlSession
     */
    SqlSession getSqlSession(SQLDialect dialect);
}
