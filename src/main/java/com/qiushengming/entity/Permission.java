package com.qiushengming.entity;

import com.qiushengming.annotation.Table;

/**
 *
 * @author qiushenming
 * @date 2018/7/1
 */
@Table(value = "SYS_PERMISSION", resultMapId = "PermissionMap")
public class Permission extends BaseEntity {

    /**
     * 权限名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 权限对应的URL
     */
    private String url;

    /**
     * 父节点
     */
    private Long pid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
