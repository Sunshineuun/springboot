package com.qiushengming.entity.extjs;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Table;
import com.qiushengming.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * @author qiushengming
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

    /**
     * 指定为 true 来约束拖动列不能拖出当前的专栏. <br>
     * 请注意，此配置只适用于列标题包含子列标题的情况下, <br>
     * 值：true or false
     */
    private boolean sealed = false;

    /**
     * 是否隐藏 <br>
     * 值： true of false
     */
    private boolean hidden = false;

    /**
     * 设置为false则用户无法隐藏该列。<br>
     * 前端，在用户选择列的功能<是否展示当前列>，进行屏蔽。<br>
     * 值： true of false
     */
    private boolean hideable = true;

    /**
     * 是否具备排序功能 <br>
     * 值： true of false
     */
    private boolean sortable = true;

    /**
     * 是否具备分组功能<br>
     * 值： true of false
     */
    private boolean groupable = true;

    /**
     * 列的宽度<br>
     */
    private int width = 125;

    /**
     * 列自定义渲染，值是一个函数。
     */
    private String rendererFun = "\"#\"";

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

    public boolean isSealed() {
        return sealed;
    }

    public void setSealed(boolean sealed) {
        this.sealed = sealed;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHideable() {
        return hideable;
    }

    public void setHideable(boolean hideable) {
        this.hideable = hideable;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public boolean isGroupable() {
        return groupable;
    }

    public void setGroupable(boolean groupable) {
        this.groupable = groupable;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Column("RENDERER_FUN")
    public String getRendererFun() {
        return rendererFun;
    }

    /**
     * 理论上，传入的值是怎么渲染的JS代码
     *
     * @param rendererFun String
     */
    public void setRendererFun(String rendererFun) {
        if (StringUtils.isNotEmpty(rendererFun)) {
            this.rendererFun = rendererFun;
        }
    }
}
