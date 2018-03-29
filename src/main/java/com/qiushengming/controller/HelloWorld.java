package com.qiushengming.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by qiushengming on 2018/3/29.
 */
@RestController
public class HelloWorld {

    @RequestMapping("/helloworld")
    public String helloworld() {
        return "hell minnie!";
    }
}
