package com.qiushengming.service.impl;

import com.qiushengming.entity.User;
import com.qiushengming.entity.UserExample;
import com.qiushengming.mapper.UserMapper;
import com.qiushengming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MinMin
 * @date 18年03月31日
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService{

    @Autowired
    // @Qualifier(value = "userMapper")
    private UserMapper mapper;

    @Override
    public int add(User user) {
        return mapper.insert(user);
    }

    @Override
    public List<User> findAllUser() {
        UserExample example = new UserExample();
        return mapper.selectByExample(example);
    }

    /**
     * 如果查询多个同名称的用户，则返回第一个
     * @param name 用户名称
     * @return 用户实体
     */
    @Override
    public User findUserName(String name) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);
        return mapper.selectByExample(example).get(0);
    }
}
