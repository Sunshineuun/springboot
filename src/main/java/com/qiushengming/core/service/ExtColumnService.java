package com.qiushengming.core.service;

import com.qiushengming.entity.extjs.ExtColumn;

import java.util.List;

/**
 * @author qiushengming
 * @date 2018/5/24.
 */
public interface ExtColumnService
        extends ManagementService<ExtColumn> {

    /**
     * {@link ExtColumn#moduleId}与{@link com.qiushengming.entity.extjs.GridViewConfigure#id}进行关联
     * @param moduleId 模块ID{@link com.qiushengming.entity.extjs.GridViewConfigure#id}
     * @return {@link ExtColumn}列表
     */
    List<ExtColumn> findModuleIdByColumns(String moduleId);
}
