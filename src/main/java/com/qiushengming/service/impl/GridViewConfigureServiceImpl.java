package com.qiushengming.service.impl;

import com.qiushengming.entity.User;
import com.qiushengming.entity.extjs.GridViewConfigure;
import com.qiushengming.service.GridViewConfigureService;
import com.qiushengming.utils.MapUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 1.配置信息缓存 - 0523/TODO <br>
 *
 * @author qiushengming
 * @date 2018/5/23
 */
@Service(value = "gridViewService")
public class GridViewConfigureServiceImpl extends BaseServiceImpl<User>
        implements GridViewConfigureService {
    @Override
    public GridViewConfigure getModuleByConfigure(String name) {
        String sql = "SELECT * FROM GRID_VIEW WHERE MODULE_NAME = #{name, jdbcType=VARCHAR}";
        Map params = MapUtils.newMap("name", name);
        getDao().queryBySql(sql, params);
        // TODO
        return null;
    }
}
