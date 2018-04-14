package com.qiushengming.controller;

import com.qiushengming.dao.MinnieDao;
import com.qiushengming.entity.City;
import com.qiushengming.mq.MQProducer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author qiushengming
 * @date 2018/3/29
 */
@RestController(value = "hello")
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

    @RequestMapping("/findAllCity")
    public List<City> findAllCity() {
        return dao.getAll(City.class);
    }
}
