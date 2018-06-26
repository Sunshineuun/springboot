package com.qiushengming.entity;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Table;

/**
 * @author qiushengming
 * @date 2018/6/26
 */
@Table(value = "(SELECT T.TABLE_NAME,T.COMMENTS FROM USER_TAB_COMMENTS T)", resultMapId = "OracleTable")
public class OracleTable extends BaseEntity {
    private String tableName;

    private String comments;

    @Column("TABLE_NAME")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
