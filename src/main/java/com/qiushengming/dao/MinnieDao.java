package com.qiushengming.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/4/14
 */
public interface MinnieDao {

    /**
     * 依据ID查询
     *
     * @param id    id
     * @param clazz 类
     * @param <T>   泛型
     * @return clazz的实例
     */
    <T> T getById(Serializable id, Class<T> clazz);

    /**
     * @param clazz class
     * @param <T>   泛型
     * @return 结果集
     */
    <T> List<T> getAll(Class<T> clazz);

    /**
     * 返回相关列表信息
     *
     * @param sql 查询sql
     * @return <code>List<Map<K, V>></code>
     */
    <K, V> List<Map<K, V>> queryBySql(String sql);

    /**
     * 返回相关列表信息
     *
     * @param sql 查询sql
     * @param params 参数
     * @return <code>List<Map<K, V>></code>
     */
    <K, V> List<Map<K, V>> queryBySql(String sql, Map<String, Object> params);

    /**
     *
     * @param sql 查询sql
     * @param clazz 实体的Class
     * @param params 查询参数
     * @return <code>List<T></code>
     */
    <T> List<T> queryBySql(String sql, Class<T> clazz,
        Map<String, Object> params);

    /**
     * 持久化对象
     *
     * @param o   被持久化的对象
     * @param <T> 泛型
     * @return 失败返回0;成功返回1
     */
    <T> int add(T o);

    /**
     * 更新对象
     *
     * @param o   被更新的对象
     * @param <T> 泛型
     * @return 失败返回0;成功返回1
     */
    <T> int update(T o);

    /**
     * 删除对象
     *
     * @param o   被删除的对象
     * @param id  id
     * @param <T> 泛型
     * @return 失败返回0;成功返回1
     */
    <T> int delete(Serializable id, T o);
}
