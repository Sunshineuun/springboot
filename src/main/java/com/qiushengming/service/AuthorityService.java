package com.qiushengming.service;

import com.qiushengming.core.service.ManagementService;
import com.qiushengming.entity.Authority;

import java.util.List;

/**
 * @author qiushengming
 * @date 2018/7/2
 */
public interface AuthorityService
    extends ManagementService<Authority> {

    /**
     * 获取当前用户的所有权限
     * @param userId 用户ID
     * @return 权限列表
     */
    List<Authority> listAuthorityByUserId(String userId);
}

