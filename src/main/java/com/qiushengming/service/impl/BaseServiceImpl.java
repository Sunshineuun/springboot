package com.qiushengming.service.impl;

import com.qiushengming.dao.MinnieDao;
import com.qiushengming.entity.BaseEntity;
import com.qiushengming.service.BaseService;

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


    /**
     * @param clazz class
     * @return 结果集
     */
    @Override
    public List getAll(Class clazz) {
        return dao.getAll(clazz);
    }

    /**
     * 持久化对象
     *
     * @param o 被持久化的对象
     * @return 失败返回0;成功返回1
     */
    @Override
    public int add(BaseEntity o) {
        return dao.add(o);
    }
}
