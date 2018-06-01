package com.qiushengming.core.service.impl;

import com.qiushengming.dao.MinnieDao;
import com.qiushengming.entity.BaseEntity;
import com.qiushengming.core.service.QueryService;
import com.qiushengming.mybatis.support.Criteria;
import com.qiushengming.utils.GenericsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author qiushengming
 * @date 2018/4/14
 */
public abstract class AbstractQueryService<T extends BaseEntity>
        implements QueryService<T> {

    /**
     * 日志记录
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private MinnieDao dao;

    private Class entityClass;

    protected MinnieDao getDao() {
        return dao;
    }

    Class<T> getEntityClass() {
        if (entityClass == null) {
            entityClass =
                    GenericsUtils.getSuperClassGenricType(this.getClass());
        }
        return entityClass;
    }


    /**
     * @return T的集合
     */
    @Override
    public List<T> getAll() {
        return getDao().getAll(getEntityClass());
    }

    /**
     * 通过ID进行查询
     *
     * @param id 实体ID
     * @return 实体
     */
    @Override
    public T getById(Serializable id) {
        return getDao().getById(id, getEntityClass());
    }

    @Override
    public List<T> queryByCriteria(Criteria criteria) {
        return getDao().queryByCriteria(criteria, getEntityClass());
    }

    @Override
    public T queryOneByCriteria(Criteria criteria) {
        return getDao().queryOneByCriteria(criteria, getEntityClass());
    }

    @Override
    public int countByCriteria(Criteria criteria) {
        return getDao().countByCriteria(criteria, getEntityClass());
    }
}
