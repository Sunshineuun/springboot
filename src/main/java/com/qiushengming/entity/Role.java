package com.qiushengming.entity;

import com.qiushengming.annotation.Table;

/**
 * 关联用户，确定用户有哪些权限
 *
 * @author qiushengming
 * @date 2018/7/2
 */
@Table(value = "SYS_ROLE", resultMapId = "RoleMap")
public class Role
    extends BaseEntity {


}
