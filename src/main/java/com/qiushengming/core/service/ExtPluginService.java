package com.qiushengming.core.service;

import com.qiushengming.entity.extjs.ExtPlugin;

import java.util.List;

/**
 * @author qiushengming
 * @date 2018/5/30
 */
public interface ExtPluginService
    extends ManagementService<ExtPlugin> {

    /**
     * {@link ExtPlugin#moduleId}与{@link com.qiushengming.entity.extjs.GridViewConfigure#id}进行关联
     * @param moduleId 模块ID{@link com.qiushengming.entity.extjs.GridViewConfigure#id}
     * @return {@link ExtPlugin}列表
     */
    List<ExtPlugin> findModuleIdByColumns(String moduleId);

}
