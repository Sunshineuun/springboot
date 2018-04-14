package com.qiushengming.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author qiushengming
 * @date 2018/4/14
 */
public interface MinnieDao {

    /**
     * 依据ID查询
     * @param id id
     * @param clazz 类
     * @param <T> 泛型
     * @return clazz的实例
     */
    <T> T getById(Serializable id, Class<T> clazz);

    /**
     *
     * @param clazz class
     * @param <T> 泛型
     * @return 结果集
     */
    <T> List<T> getAll(Class<T> clazz);

    /**
     * 持久化对象
     * @param o 被持久化的对象
     * @param <T> 泛型
     * @return 失败返回0;成功返回1
     */
    <T> int add(T o);

    /**
     * 更新对象
     * @param o 被更新的对象
     * @param <T> 泛型
     * @return 失败返回0;成功返回1
     */
    <T> int update(T o);

    /**
     * 删除对象
     * @param o 被删除的对象
     * @param id id
     * @param <T> 泛型
     * @return 失败返回0;成功返回1
     */
    <T> int delete(Serializable id, T o);
}
