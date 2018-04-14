package com.qiushengming.service;

import com.qiushengming.entity.User;

import java.util.List;

/**
 * @author MinMin
 * @date 18年03月31日
 */
public interface UserService extends BaseService {

    List<User> findAllUser();

    User findUserName(String name);
}
