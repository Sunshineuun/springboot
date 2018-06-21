package com.qiushengming.entity.extjs;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Exclude;
import com.qiushengming.annotation.Table;
import com.qiushengming.entity.BaseEntity;
import com.qiushengming.exception.SystemException;
import com.qiushengming.utils.Json;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/5/23.
 */
@Table(value = "EXT_COULUMN",
        resultMapId = "ExtColumnMap",
        selectSql = "SELECT * FROM (SELECT * FROM EXT_COULUMN ORDER BY ORDER_INDEX) T1")
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
     * 组件类型
     */
    private String xtype = "textfield";

    /**
     * 根据指定的格式将对象格式化。
     */
    private String format;

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
     * 是否锁定
     */
    private boolean locked = true;

    /**
     * 列的宽度<br>
     */
    private int width = 125;

    /**
     * 列自定义渲染，值是一个函数。
     */
    private String rendererFun = "return value;";

    /**
     * 行编辑模式中，当前类编辑框的类型
     */
    private Map<String, Object> editor;

    /**
     * 列顺序标识
     */
    private int orderIndex = 0;

    private Map<String, Object> filter = new HashMap<>();

    private String filterType = "string";

    private String filterValue = "";

    /**
     * 多个属性用`,`分隔，属性与值之间用`#`分隔
     */
    private String filterItemDefaults;

    /**
     * 字典
     */
    private String dictionary;

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

    public String getXtype() {
        return xtype;
    }

    public void setXtype(String xtype) {
        this.xtype = xtype;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
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

    public Map<String, Object> getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = Json.stringToMap(editor);
    }

    @Column("ORDER_INDEX")
    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Exclude
    public Map<String, Object> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }

    @Column("FILTER_TYPE")
    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
        if(StringUtils.isNotEmpty(filterType)){
            filter.put("type", filterType);
        }else{
            throw new SystemException("缺失过滤器类型");
        }
    }

    @Column("FILTER_VALUE")
    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
        if (StringUtils.isNotEmpty(filterValue)) {
            filter.put("value", filterValue);
        }
    }

    @Column("FILTER_ITEM_DEFAULTS")
    public String getFilterItemDefaults() {
        return filterItemDefaults;
    }

    public void setFilterItemDefaults(String filterItemDefaults) {
        this.filterItemDefaults = filterItemDefaults;
        if (StringUtils.isNotEmpty(filterItemDefaults)) {
            filter.put("itemDefaults", filterItemDefaults);
        }
    }

    public String getDictionary() {
        return dictionary;
    }

    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }
}
