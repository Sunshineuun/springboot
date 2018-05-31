package com.qiushengming.entity.extjs;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Exclude;
import com.qiushengming.annotation.Table;
import com.qiushengming.entity.BaseEntity;
import com.qiushengming.utils.Json;
import com.qiushengming.utils.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.qiushengming.commons.Constants.DOU_HAO;
import static com.qiushengming.commons.Constants.JIN_HAO;

/**
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
     * 模块请求后台的url地址
     */
    private String url;

    /**
     * 设置为true，则强制使得列自适应成可用宽度
     */
    private boolean forceFit = true;

    /**
     * 内容溢出展示滚动条
     */
    private boolean autoScroll = true;

    /**
     * 是否启用行的框线样式
     * ture-启用；false-禁用
     */
    private boolean rowLines = true;

    /**
     * 是否启用列的框线样式
     * ture-启用；false-禁用
     */
    private boolean columnLines = true;

    /**
     * 每页展示多少数据。
     */
    private int pageSize = 25;

    /**
     * 是否启用表格列隐藏功能，配合{@link ExtColumn#hideable}.<br>
     * 该属性,标识是否启用列隐藏功能<br>
     * {@link ExtColumn#hideable}当前列是否能被隐藏。<br>
     */
    private boolean enableColumnHide = true;

    /**
     * store查询时的排序字段以及正序还是倒序
     */
    private List<Map<Object, Object>> sorters = new ArrayList<>();

    private boolean startLoad = true;

    /**
     * 实体中的columns的配置信息。详见{@link ExtColumn}
     */
    private List<ExtColumn> columns;

    /**
     * 提供插件
     */
    private List<ExtPlugin> plugins = new ArrayList<>();

    /**
     * 行编辑模式中，当前类编辑框的类型
     */
    private Map<String, Object> editor;


    @Column(value = "MODULE_NAME")
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column("FORCE_FIT")
    public boolean isForceFit() {
        return forceFit;
    }

    public void setForceFit(boolean forceFit) {
        this.forceFit = forceFit;
    }

    @Column("AUTO_SCROLL")
    public boolean isAutoScroll() {
        return autoScroll;
    }

    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
    }

    @Column("ROW_LINES")
    public boolean isRowLines() {
        return rowLines;
    }

    public void setRowLines(boolean rowLines) {
        this.rowLines = rowLines;
    }

    @Column("COLUMN_LINES")
    public boolean isColumnLines() {
        return columnLines;
    }

    public void setColumnLines(boolean columnLines) {
        this.columnLines = columnLines;
    }

    @Column("PAGE_SIZE")
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Column("ENABLE_COLUMN_HIDE")
    public boolean isEnableColumnHide() {
        return enableColumnHide;
    }

    public void setEnableColumnHide(boolean enableColumnHide) {
        this.enableColumnHide = enableColumnHide;
    }

    public List<Map<Object, Object>> getSorters() {
        return sorters;
    }

    /**
     * soreter，包含两个属性<code>property/direction</code>。并且包含多组。<br>
     * 故此按<code>逗号[,]<code/>进行安组分隔，属性间用#号分隔。
     *
     * @param sorters String
     */
    public void setSorters(String sorters) {
        for (String s : sorters.split(DOU_HAO)) {
            String[] s1 = s.split(JIN_HAO);
            this.sorters.add(MapUtils.newMap("property",
                s1[0],
                "direction",
                s1[1]));
        }

        if (StringUtils.isEmpty(sorters)
            || CollectionUtils.isEmpty(this.sorters)) {
            this.sorters.add(MapUtils.newMap("property",
                "ID",
                "direction",
                "ASC"));
        }
    }

    public boolean isStartLoad() {
        return startLoad;
    }

    public void setStartLoad(boolean startLoad) {
        this.startLoad = startLoad;
    }

    @Exclude
    public List<ExtColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<ExtColumn> columns) {
        this.columns = columns;
    }

    @Exclude
    public List<ExtPlugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<ExtPlugin> plugins) {
        this.plugins = plugins;
    }

    public String getEditor(){
        return editor.toString();
    }

    @Exclude
    public Map<String, Object> getMapEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = Json.stringToMap(editor);
    }
}
