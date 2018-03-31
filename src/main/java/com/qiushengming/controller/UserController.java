package com.qiushengming.controller;

import com.qiushengming.entity.User;
import com.qiushengming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>作者: qiushengming</p>
 * <p>日期: 2018/3/31</p>
 */
@RestController
public class UserController {

    @Autowired
    private UserService service;

    @ResponseBody
    @RequestMapping("/addUser")
    public int add(String username, String sex, int age) {
        User user = new User();
        user.setName(username);
        user.setSex(sex);
        user.setAge(age);
        return service.add(user);
    }

    @ResponseBody
    @RequestMapping("/findAllUser")
    public List<User> findAllUser() {
        return service.findAllUser();
    }
}
