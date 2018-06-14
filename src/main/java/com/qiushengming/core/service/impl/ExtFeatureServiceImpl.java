package com.qiushengming.core.service.impl;

import com.qiushengming.core.service.ExtFeatureService;
import com.qiushengming.entity.extjs.ExtColumn;
import com.qiushengming.entity.extjs.ExtFeature;
import com.qiushengming.mybatis.support.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qiushengming
 * @date 2018/5/30
 */
@Service(value = "extFeatureService")
public class ExtFeatureServiceImpl
    extends AbstractManagementService<ExtFeature>
    implements ExtFeatureService {

    /**
     * {@link ExtColumn#moduleId}与{@link com.qiushengming.entity.extjs.GridViewConfigure#id}进行关联
     * @param moduleId 模块ID{@link com.qiushengming.entity.extjs.GridViewConfigure#id}
     * @return {@link ExtColumn}列表
     */
    @Override
    public List<ExtFeature> findModuleIdByFeatures(String moduleId) {
        Criteria criteria = Criteria.create().andEqualTo("moduleId", moduleId);
        return this.queryByCriteria(criteria);
    }

}
