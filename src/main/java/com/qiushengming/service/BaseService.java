package com.qiushengming.service;

import com.qiushengming.entity.BaseEntity;

import java.util.List;

/**
 * @author qiushengming
 * @date 2018/4/14
 */
public interface BaseService<T extends BaseEntity> {
    /**
     *
     * @param clazz class
     * @return 结果集
     */
    List<BaseEntity> getAll(Class<T> clazz);

    /**
     * 持久化对象
     * @param o 被持久化的对象
     * @return 失败返回0;成功返回1
     */
    int add(BaseEntity o);
}
