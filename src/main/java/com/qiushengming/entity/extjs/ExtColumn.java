package com.qiushengming.entity.extjs;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Table;
import com.qiushengming.entity.BaseEntity;

/**
 * @author  qiushengming
 * @date 2018/5/23.
 */
@Table(value = "EXT_COULUMN", resultMapId = "ExtColumnMap")
public class ExtColumn extends BaseEntity {

    /**
     * 模块ID， 对应{@link GridViewConfigure#id}
     */
    private String moduleId;

    /**
     * 前端列名称
     */
    private String header;

    /**
     * 前端store索引，或者称为前端grid数据索引
     */
    private String dataIndex;

    @Column(value = "MODULE_ID")
    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    @Column(value = "HEADER")
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Column(value = "DATA_INDEX")
    public String getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }
}
