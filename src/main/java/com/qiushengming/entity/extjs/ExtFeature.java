package com.qiushengming.entity.extjs;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Exclude;
import com.qiushengming.annotation.Table;
import com.qiushengming.entity.BaseEntity;
import com.qiushengming.utils.Json;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/6/14
 */
@Table(value = "EXT_FEATURE", resultMapId = "ExtFeatureMap")
public class ExtFeature
    extends BaseEntity {

    @NotNull(message = "模块ID不为空")
    private String moduleId;

    @NotNull(message = "插件类型不能为空#ftype")
    private String ftype;

    private String config;

    private Map<String, Object> configMap;

    @Column("MODULE_ID")
    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
        if (StringUtils.isEmpty(config)) {
            return;
        }
        setConfigMap((Map<String, Object>) Json.stringToMap(config));
    }

    @Exclude
    public Map<String, Object> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, Object> configMap) {
        this.configMap = configMap;
    }
}
