package com.qiushengming.service.impl;

import com.qiushengming.core.service.impl.AbstractManagementService;
import com.qiushengming.entity.Authority;
import com.qiushengming.mybatis.support.Criteria;
import com.qiushengming.service.AuthorityService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qiushengming
 * @date 2018/7/2
 */
@Service("authorityService")
public class AuthorityServiceImpl
    extends AbstractManagementService<Authority>
    implements AuthorityService {


    /**
     * 获取当前用户的所有权限
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public List<Authority> listAuthorityByUserId(String userId) {
        Criteria criteria = Criteria.create().andEqualTo("userId", userId);
        return queryByCriteria(criteria);
    }
}
