package com.qiushengming.core.service;

import com.qiushengming.entity.BaseEntity;
import com.qiushengming.mybatis.support.Criteria;

import java.io.Serializable;
import java.util.List;

/**
 * @author qiushengming
 * @date 2018/4/14
 */
public interface QueryService<T extends BaseEntity> {
    /**
     * 获取所有数据
     *
     * @return 结果集
     */
    List<T> getAll();

    T getById(Serializable id);

    List<T> queryByCriteria(Criteria criteria);

    T queryOneByCriteria(Criteria criteria);

    int countByCriteria(Criteria criteria);

}
