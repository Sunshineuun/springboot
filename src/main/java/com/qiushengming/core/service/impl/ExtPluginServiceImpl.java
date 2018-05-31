package com.qiushengming.core.service.impl;

import com.qiushengming.core.service.ExtPluginService;
import com.qiushengming.entity.extjs.ExtColumn;
import com.qiushengming.entity.extjs.ExtPlugin;
import com.qiushengming.mybatis.support.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qiushengming
 * @date 2018/5/30
 */
@Service(value = "extPluginService")
public class ExtPluginServiceImpl
    extends AbstractManagementService<ExtPlugin>
    implements ExtPluginService {

    /**
     * {@link ExtColumn#moduleId}与{@link com.qiushengming.entity.extjs.GridViewConfigure#id}进行关联
     * @param moduleId 模块ID{@link com.qiushengming.entity.extjs.GridViewConfigure#id}
     * @return {@link ExtColumn}列表
     */
    @Override
    public List<ExtPlugin> findModuleIdByColumns(String moduleId) {
        Criteria criteria = Criteria.create().andEqualTo("moduleId", moduleId);
        return this.queryByCriteria(criteria);
    }

}
