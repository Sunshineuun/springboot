package com.qiushengming.service.impl;

import com.qiushengming.dao.MinnieDao;
import com.qiushengming.entity.BaseEntity;
import com.qiushengming.service.BaseService;
import com.qiushengming.utils.GenericsUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author qiushengming
 * @date 2018/4/14
 */
public abstract class BaseServiceImpl<T extends BaseEntity>
    implements BaseService {

    @Resource
    private MinnieDao dao;

    private Class entityClass;

    protected MinnieDao getDao() {
        return dao;
    }

    protected Class getEntityClass() {
        if (entityClass == null) {
            entityClass =
                GenericsUtils.getSuperClassGenricType(this.getClass());
        }
        return entityClass;
    }


    /**
     * @return 结果集
     */
    @Override
    public List getAll() {
        return getDao().getAll(getEntityClass());
    }

    /**
     * 持久化对象
     *
     * @param o 被持久化的对象
     * @return 失败返回0;成功返回1
     */
    @Override
    public int add(BaseEntity o) {
        return getDao().add(o);
    }
}
