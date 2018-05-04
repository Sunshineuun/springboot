package com.qiushengming.entity;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Table;

import java.util.Date;

/**
 * @author qiushengming
 * @date 2018/4/16
 */
@Table(value = "SP_DATA_LOG", resultMapId = "DataLog")
public class DataLog
    extends BaseEntity {

    /**
     * 实体
     */
    private String entity;

    /**
     * 目标类
     */
    private String targetClass;

    /**
     * 目标ID
     */
    private String targetId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 操作人
     */
    private String by;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Column("TARGET_CLASS")
    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    @Column("TARGET_ID")
    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    @Column("CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }
}
