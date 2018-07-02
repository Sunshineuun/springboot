package com.qiushengming.service.impl;

import com.qiushengming.core.service.impl.AbstractManagementService;
import com.qiushengming.entity.Permission;
import com.qiushengming.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * @author qiushengming
 * @date 2018/7/1
 */
@Service("permissionService")
public class PermissionServiceImpl
    extends AbstractManagementService<Permission>
    implements PermissionService{
}
