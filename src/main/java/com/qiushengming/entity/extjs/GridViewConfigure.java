package com.qiushengming.entity.extjs;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Exclude;
import com.qiushengming.annotation.Table;
import com.qiushengming.entity.BaseEntity;

import java.util.List;

/**
 *
 * @author qiushengming
 * @date 2018/5/23
 */
@Table(value = "GRID_VIEW_CONFIGURE", resultMapId = "GridViewConfigureMap")
public class GridViewConfigure
    extends BaseEntity {

    /**
     * 模块名称，为唯一标识，外部通过该标识获取模块的配置信息。 <br>
     * 理论上一个实体对应一个模块，故此应该用实体的简单名称或者全限定名称作为模块名称。 <br>
     */
    private String moduleName;

    /**
     * 实体中的columns的配置信息。详见{@link ExtColumn}
     */
    private List<ExtColumn> columns;

    @Exclude
    public List<ExtColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<ExtColumn> columns) {
        this.columns = columns;
    }

    @Column(value = "MODULE_NAME")
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
