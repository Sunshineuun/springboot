package com.qiushengming.entity.code;

import com.qiushengming.annotation.Table;
import com.qiushengming.entity.BaseEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(value = "SYS_FILE_INFO", resultMapId = "fileInfoMap")
public class FileInfo extends BaseEntity {

    /**
     * 文件的名称
     */
    @NotNull
    private String name;

    /**
     * 文件的大小，已字节为单位
     */
    private Long size = 0L;

    /**
     * 创建时间
     */
    @NotNull
    private Date createTime = new Date();

    /**
     * 存储在系统里的路径，便于做下载功能
     * 存储相对路径，也可以考虑绝对路径，待定。 TODO
     * 存储相对路径处理比较麻烦，存储绝对路径不便于迁移。
     */
    @NotNull
    private String systemPath;

    /**
     * 文件描述字段
     */
    private String dsc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSystemPath() {
        return systemPath;
    }

    public void setSystemPath(String systemPath) {
        this.systemPath = systemPath;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }
}
