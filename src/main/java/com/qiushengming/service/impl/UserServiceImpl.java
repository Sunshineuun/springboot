package com.qiushengming.service.impl;

import com.qiushengming.core.service.impl.AbstractManagementService;
import com.qiushengming.core.service.impl.AbstractQueryServiceImpl;
import com.qiushengming.entity.User;
import com.qiushengming.entity.UserExample;
import com.qiushengming.mapper.UserMapper;
import com.qiushengming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MinMin
 * @date 2018/3/31
 */
@Service(value = "userService")
@CacheConfig(cacheNames = "user")
public class UserServiceImpl
    extends AbstractManagementService<User>
    implements UserService {

    @Autowired
    // @Qualifier(value = "userMapper")
    private UserMapper mapper;

    @Override
    public List<User> findAllUser() {
        UserExample example = new UserExample();
        return mapper.selectByExample(example);
    }

    /**
     * 如果查询多个同名称的用户，则返回第一个
     *
     * @param name 用户名称
     * @return 用户实体
     */
    @Override
    @Cacheable(value = "user", key = "#name")
    public User findUserName(String name) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);
        return mapper.selectByExample(example).get(0);
    }

}
