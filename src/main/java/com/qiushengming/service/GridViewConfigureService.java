package com.qiushengming.service;

import com.qiushengming.entity.extjs.GridViewConfigure;

/**
 * Created by qiushengming on 2018/5/23.
 */
public interface GridViewConfigureService extends BaseService{
    GridViewConfigure getModuleByConfigure(String name);
}
