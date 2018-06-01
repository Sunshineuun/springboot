package com.qiushengming.entity.code;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Table;
import com.qiushengming.entity.BaseEntity;

/**
 * @author qiushengming
 * @date 2018/6/1.
 */
@Table(value = "DICTIONARY", resultMapId = "DictionaryMap")
public class Dictionary extends BaseEntity {
    /**
     * 值
     */
    private String value;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 父级代码
     */
    private String typeCode;

    /**
     * 顺序ID
     */
    private int viewOrder;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column("TYPE_CODE")
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Column("VIEW_ORDER")
    public int getViewOrder() {
        return viewOrder;
    }

    public void setViewOrder(int viewOrder) {
        this.viewOrder = viewOrder;
    }
}
