package com.qiushengming.core.service.impl;

import com.qiushengming.core.service.ExtColumnService;
import com.qiushengming.entity.extjs.ExtColumn;
import com.qiushengming.mybatis.support.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qiushengming
 * @date 2018/5/24.
 */
@Service(value = "extColumnService")
public class ExtColumnServiceImpl
        extends AbstractManagementService<ExtColumn>
        implements ExtColumnService {

    /**
     * {@link ExtColumn#moduleId}与{@link com.qiushengming.entity.extjs.GridViewConfigure#id}进行关联
     * @param moduleId 模块ID{@link com.qiushengming.entity.extjs.GridViewConfigure#id}
     * @return {@link ExtColumn}列表
     */
    @Override
    public List<ExtColumn> findModuleIdByColumns(String moduleId) {
        Criteria criteria = Criteria.create().andEqualTo("moduleId", moduleId);
        return this.queryByCriteria(criteria);
    }
}
