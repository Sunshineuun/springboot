package com.qiushengming.core.service;

import com.qiushengming.entity.extjs.GridViewConfigure;

/**
 * @author qiushengming
 * @date 2018/5/23.
 */
public interface GridViewConfigureService
        extends ManagementService<GridViewConfigure> {

    /**
     * 通过模块名称，获取模块配置信息。 <br>
     * 模块名称为唯一标识
     *
     * @param name 模块名称{@link GridViewConfigure#moduleName}
     * @return {@link GridViewConfigure} 实例
     */
    GridViewConfigure getModuleByConfigure(String name);
}
