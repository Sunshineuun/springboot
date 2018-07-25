package com.qiushengming.controller;

import com.qiushengming.dao.MinnieDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qiushengming
 * @date 2018/3/29
 */
@Controller(value = "hello")
public class HelloWorld {

    /*@Resource
    private MQProducer producer;*/

  @Resource
  private MinnieDao dao;

  @RequestMapping("/helloworld")
  public String helloworld() {
    return "hell minnie!";
  }

  /**
   * .测试mq
   *
   * @param d d
   * @param msg msg
   */
  @RequestMapping("/sendMsg")
  public void sendMsg(String d, String msg) {
    /*for (int i = 0; i < 5; i++) {
     *//*producer.send(d, msg);*//*
    }*/
  }

  @RequestMapping("/exception")
  public void exception() {
    throw new NullPointerException("异常测试");
  }
}
