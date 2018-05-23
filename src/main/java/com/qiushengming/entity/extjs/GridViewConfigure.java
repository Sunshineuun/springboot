package com.qiushengming.entity.extjs;

import com.qiushengming.annotation.Table;
import com.qiushengming.entity.BaseEntity;

import java.util.List;

/**
 * Created by qiushengming on 2018/5/23.
 */
@Table(value = "GRID_VIEW", resultMapId = "GridViewMap")
public class GridViewConfigure extends BaseEntity {
    private List<ExtColumn> columns;

    public List<ExtColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<ExtColumn> columns) {
        this.columns = columns;
    }
}
