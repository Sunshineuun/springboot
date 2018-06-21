package com.qiushengming.dao;

import com.qiushengming.mybatis.support.Criteria;

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
     * 查询所有的list实体
     *
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
     * @param sql    查询sql
     * @param params 参数
     * @return <code>List<Map<K, V>></code>
     */
    <K, V> List<Map<K, V>> queryBySql(String sql, Map<String, Object> params);

    /**
     * 通过失去了，及其指定params，查询list实体
     *
     * @param sql    查询sql
     * @param clazz  实体的Class
     * @param params 查询参数
     * @return <code>List<T></code>
     */
    <T> List<T> queryBySql(String sql, Class<T> clazz,
        Map<String, Object> params);

    /**
     * 分页查询，返回list map
     *
     * @param sql    sql
     * @param params 查询参数
     * @param offset 分页位移
     * @param limit  限定长度
     * @param <K>    other
     * @param <V>    other
     * @return 集合
     */
    <K, V> List<Map<K, V>> queryBySql(String sql, Map<String, Object> params,
        int offset, int limit);

    /**
     * 分页查询，返回list实体
     *
     * @param clazz  实体的Class
     * @param sql    sql
     * @param params 参数
     * @param offset 偏移量
     * @param limit  限定长度
     * @param <T>    实体
     * @return 返回list实体
     */
    <T> List<T> queryBySql(Class clazz, String sql, Map<String, Object> params,
        int offset, int limit);

    /**
     * 带参数，count sql查询
     * @param sql sql
     * @param params 参数
     * @return 统计的数量
     */
    int countBySql(String sql, Map<String, Object> params);

    /**
     * 通过{@link Criteria}组装的条件进行查询
     *
     * @param criteria {@link Criteria}
     * @param clazz    实体的Class
     * @param <T>      实体
     * @return 实体列表
     */
    <T> List<T> queryByCriteria(Criteria criteria, Class<T> clazz);

    /**
     * 通过{@link Criteria}组装的条件进行查询，<br>
     * 调用{@link MinnieDao#queryByCriteria(Criteria, Class)}取结果集的第0条
     *
     * @param criteria {@link Criteria}
     * @param clazz    实体的Class
     * @param <T>      实体
     * @return 实体
     */
    <T> T queryOneByCriteria(Criteria criteria, Class<T> clazz);

    /**
     * 不带参数，通过配置在xml中的id select、insert、del、update标签进行查询
     * @param mybaitsId mybatisId
     * @param <T> 实体
     * @return list实体
     */
    <T> List<T> query(String mybaitsId);

    /**
     * 带参数，通过配置在xml中的id select、insert、del、update标签进行查询
     * @param mybaitsId mybaitsId
     * @param params 参数
     * @param <T> 实体
     * @return list实体
     */
    <T> List<T> query(String mybaitsId, Object params);

    /**
     * 带参数，分页查询，通过配置在xml中的id select、insert、del、update标签进行查询
     * @param mybaitsId mybaitsId
     * @param params 参数
     * @param offset 偏移量
     * @param limit 限定长度
     * @param <T> 实体
     * @return list实体
     */
    <T> List<T> query(String mybaitsId, Object params, int offset, int limit);

    /**
     * 通过{@link Criteria}组装的条件进行查询<br>
     *
     * @param criteria {@link Criteria}
     * @param clazz    实体的Class
     * @param <T>      实体
     * @return 查询数量
     */
    <T> int countByCriteria(Criteria criteria, Class<T> clazz);

    /**
     * 新增
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
     * @param o  被删除的对象
     * @param id id
     * @return 失败返回0;成功返回1
     */
    int delete(Serializable id, Class<?> o);
}
