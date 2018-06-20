package com.qiushengming.core.service.impl;

import com.qiushengming.core.service.ManagementService;
import com.qiushengming.entity.BaseEntity;
import com.qiushengming.mybatis.support.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author qiushengming
 * @date 2018/5/24.
 */
public abstract class AbstractManagementService<T extends BaseEntity>
        extends AbstractQueryService<T>
        implements ManagementService<T> {

    /**
     * 日志记录
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int add(T baseDomain) {
        return getDao().add(baseDomain);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int update(T baseDomain) {
        return getDao().update(baseDomain);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int delete(T baseDomain) {
        return getDao().delete(baseDomain.getId(), baseDomain.getClass());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void deleteByCriteria(Criteria criteria) {

    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int add(List<T> baseDomains) {
        //TODO
        return 0;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int update(List<T> baseDomains) {
        //TODO
        return 0;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int delete(List<T> baseDomains) {
        //TODO
        return 0;
    }
}
