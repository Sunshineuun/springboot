package com.qiushengming.entity;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Table;
import org.apache.ibatis.type.JdbcType;

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
    private Date createDate = new Date();

    /**
     * 操作人
     */
    private String createBy;

    /**
     * 在操作
     */
    private String type;

    /**
     * 请求的IP地址
     */
    private String ip;

    /**
     * 执行的方法
     */
    private String method;

    public DataLog(String entity, String targetId,
        Class<? extends BaseEntity> targetClass, String by, String type,
        String ip, String method) {
        this.entity = entity;
        this.targetId = targetId;
        this.targetClass = targetClass.getName();
        this.createBy = by;
        this.type = type;
        this.ip = ip;
        this.method = method;
    }

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

    @Column(value = "CREATE_DATE",
        isUpdate = false,
        jdbcType = JdbcType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column("CREATE_BY")
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
