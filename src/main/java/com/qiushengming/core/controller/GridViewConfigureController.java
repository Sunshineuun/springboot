package com.qiushengming.core.controller;

import com.qiushengming.core.service.GridViewConfigureService;
import com.qiushengming.entity.extjs.GridViewConfigure;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author qiushengming
 * @date 2018/5/23.
 */
@RestController
public class GridViewConfigureController {

    @Resource(name = "gridViewService")
    private GridViewConfigureService service;

    /**
     * 通过模块名称，获取模块配置信息。 <br>
     * 模块名称为唯一标识
     *
     * @param name 模块名称{@link GridViewConfigure#moduleName}
     * @return 模块配置信息
     */
    @ResponseBody
    @RequestMapping(value = "/gridview/getModuleNameByConfigure/{name}", method = RequestMethod.GET)
    public GridViewConfigure getModuleNameByConfigure(@PathVariable String name) {
        return service.getModuleByConfigure(name);
    }
}
