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
public class AbstractManagementService<T extends BaseEntity>
        extends AbstractQueryServiceImpl<T>
        implements ManagementService<T> {

    /**
     * 日志记录
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int add(BaseEntity baseDomain) {
        return getDao().add(baseDomain);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int update(BaseEntity baseDomain) {
        return getDao().update(baseDomain);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int deleteById(String id) {
        return getDao().delete(id, getEntityClass());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void deleteByCriteria(Criteria criteria) {

    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int add(List baseDomains) {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int update(List baseDomains) {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int delete(List baseDomains) {
        return 0;
    }
}
