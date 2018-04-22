package com.qiushengming.service;

import com.qiushengming.entity.BaseEntity;

import java.util.List;

/**
 * @author qiushengming
 * @date 2018/4/14
 */
public interface BaseService<T extends BaseEntity> {
    /**
     * 获取所有数据
     * @return 结果集
     */
    List<T> getAll();

    /**
     * 持久化对象
     * @param o 被持久化的对象
     * @return 失败返回0;成功返回1
     */
    int add(T o);
}
