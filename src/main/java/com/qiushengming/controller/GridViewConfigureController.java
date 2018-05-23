package com.qiushengming.controller;

import com.qiushengming.entity.extjs.GridViewConfigure;
import com.qiushengming.service.GridViewConfigureService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by qiushengming on 2018/5/23.
 */
@RestController(value = "gridview")
public class GridViewConfigureController {

    @Resource(name = "gridViewService")
    private GridViewConfigureService service;

    /**
     * 通过模块名称，获取模块配置信息。 \n
     * 模块名称为唯一标识
     * @param name 模块名称
     * @return 模块配置信息
     */
    public GridViewConfigure getModuleByConfigure(String name) {
        return service.getModuleByConfigure(name);
    }
}
