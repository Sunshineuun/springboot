package com.qiushengming.mybatis;

import com.qiushengming.enums.SQLDialect;
import com.qiushengming.mybatis.support.Criteria;

import java.io.Serializable;
import java.util.List;

/**
 * @author qiushengming
 * @date 2018/6/21
 */
public interface ObjectSession
    extends DynamicSqlDao {
    <T> T getById(Serializable id, Class<T> clazz);

    <T> T getById(Serializable id, Class<T> clazz, SQLDialect dialect);

    <T> List<T> getAll(Class<T> clazz);

    <T> List<T> getAll(Class<T> clazz, SQLDialect dialect);

    <T> List<T> queryByCriteria(Criteria criteria, Class<T> clazz);

    <T> List<T> queryBySql(String sql, Class<T> clazz, Object params);

    <T> int countByCriteria(Criteria criteria, Class<T> clazz);

    <T> int insert(T obj);

    <T> int deleteById(Serializable id, Class<T> clazz);

    int deleteByCriteria(Criteria criteria, Class<?> clazz);

    <T> int update(T obj);
}
