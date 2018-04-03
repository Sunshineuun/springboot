package com.qiushengming.controller;

import com.qiushengming.mq.MQProducer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by qiushengming on 2018/3/29.
 */
@RestController
public class HelloWorld {

    @Resource
    private MQProducer producer;

    @RequestMapping("/helloworld")
    public String helloworld() {
        return "hell minnie!";
    }

    @RequestMapping("/sendMsg")
    public void sendMsg(String d, String msg) {
        for (int i = 0; i < 5; i++) {
            producer.send(d, msg);
        }
    }

}
