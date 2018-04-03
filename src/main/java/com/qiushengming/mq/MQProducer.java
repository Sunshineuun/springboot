package com.qiushengming.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author qiushengming
 * @date 2018/4/3
 */
@Service
public class MQProducer {

    private final static Logger logger =
        LoggerFactory.getLogger(MQProducer.class);

    @Resource
    private JmsMessagingTemplate jms;

    public void send(String destination, final String msg) {
        logger.info("发送消息");
        jms.convertAndSend(destination, msg);
    }
}
