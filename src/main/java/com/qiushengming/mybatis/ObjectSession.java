package com.qiushengming.mybatis;

import com.qiushengming.mybatis.support.Criteria;

import java.io.Serializable;
import java.util.List;

public interface ObjectSession
    extends DynamicSqlDao {
    <T> T getById(Serializable id, Class<T> clazz);

    <T> List<T> getAll(Class<T> clazz);

    <T> List<T> queryByCriteria(Criteria criteria, Class<T> clazz);

    <T> List<T> queryBySql(String sql, Class<T> clazz, Object params);

    <T> int countByCriteria(Criteria criteria, Class<T> clazz);

    <T> int insert(T obj);

    <T> int deleteById(Serializable id, Class<T> clazz);

    <T> void deleteByCriteria(Criteria criteria, Class<?> clazz);

    <T> int update(T obj);
}
