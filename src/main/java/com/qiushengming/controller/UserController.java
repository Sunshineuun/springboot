package com.qiushengming.controller;

import com.qiushengming.entity.MinNieResponse;
import com.qiushengming.entity.User;
import com.qiushengming.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>作者: qiushengming</p>
 * <p>日期: 2018/3/31</p>
 * @author MinMin
 */
@RestController
public class UserController {

    @Resource(name = "userService")
    private UserService service;

    @ResponseBody
    @RequestMapping("/addUser")
    public MinNieResponse add(String username, String sex, String age) {
        User user = new User();
        user.setName(username);
        user.setSex(sex);
        user.setAge(age);
        Integer result = service.add(user);
        if (result > 0) {
            return new MinNieResponse(true, "新增成功", result);
        }
        return new MinNieResponse(false, "新增失败", result);
    }

    @ResponseBody
    @RequestMapping("/findAllUser")
    public List<User> findAllUser() {
        return service.findAllUser();
    }

    @ResponseBody
    @RequestMapping("/findUserByUserName")
    public User findUserByUserName(String username) {
        return service.findUserName(username);
    }

    @ResponseBody
    @RequestMapping("/update")
    public User update(User user) {
        return new User();
    }
}
