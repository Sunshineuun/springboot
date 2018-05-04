package com.qiushengming.controller;

import com.qiushengming.dao.MinnieDao;
import com.qiushengming.mq.MQProducer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 *
 * @author qiushengming
 * @date 2018/3/29
 */
@Controller(value = "hello")
public class HelloWorld {

    @Resource
    private MQProducer producer;

    @Resource
    private MinnieDao dao;

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

    @RequestMapping("/book")
    public String books() {
        return "/book/index";
    }

    @RequestMapping("/book/history")
    public String bookhistory() {
        return "/book/history";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
