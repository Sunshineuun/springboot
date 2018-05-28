package com.qiushengming.core.service.impl;

import com.qiushengming.core.service.ExtColumnService;
import com.qiushengming.core.service.GridViewConfigureService;
import com.qiushengming.entity.extjs.ExtColumn;
import com.qiushengming.entity.extjs.GridViewConfigure;
import com.qiushengming.mybatis.support.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 1.配置信息缓存 - 0523/TODO <br>
 *
 * @author qiushengming
 * @date 2018/5/23
 */
@Service(value = "gridViewService")
public class GridViewConfigureServiceImpl
        extends AbstractManagementService<GridViewConfigure>
        implements GridViewConfigureService {

    @Resource(name = "extColumnService")
    private ExtColumnService extColumnService;

    /**
     * 通过模块名称，获取模块配置信息。 <br>
     * 模块名称为唯一标识
     * <code>@Cacheable(value = "gridView", key = "#name")</code>
     * @param name 模块名称{@link GridViewConfigure#moduleName}
     * @return {@link GridViewConfigure}实例
     */
    @Override
    public GridViewConfigure getModuleByConfigure(String name) {
        // 组装条件
        Criteria criteria = Criteria.create().andEqualTo("moduleName", name);
        GridViewConfigure configure = this.queryOneByCriteria(criteria);

        // 查询Column
        List<ExtColumn> columns = extColumnService.findModuleIdByColumns(configure.getId());
        configure.setColumns(columns);

        return configure;
    }
}
