package com.qiushengming.service;

import com.qiushengming.core.service.ManagementService;
import com.qiushengming.entity.User;

import java.util.List;

/**
 * @author MinMin
 * @date 2018/3/31
 */
public interface UserService
        extends ManagementService<User> {

    List<User> findAllUser();

    User findUserName(String name);
}
