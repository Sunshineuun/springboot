package com.qiushengming.core.service;

import com.qiushengming.entity.extjs.ExtFeature;

import java.util.List;

/**
 * @author qiushengming
 * @date 2018/5/30
 */
public interface ExtFeatureService
    extends ManagementService<ExtFeature> {

    /**
     * {@link ExtFeature#moduleId}与{@link com.qiushengming.entity.extjs.GridViewConfigure#id}进行关联
     *
     * @param moduleId 模块ID{@link com.qiushengming.entity.extjs.GridViewConfigure#id}
     * @return {@link ExtFeature}列表
     */
    List<ExtFeature> findModuleIdByFeatures(String moduleId);

}
