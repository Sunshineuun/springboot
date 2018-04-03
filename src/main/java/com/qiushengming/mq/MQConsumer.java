package com.qiushengming.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * @author qiushengming
 * @date 2018/4/3
 */
@Service
public class MQConsumer {

    private final static Logger logger =
        LoggerFactory.getLogger(MQConsumer.class);

    @JmsListener(destination = "mytest.queue")
    public void receiveQueue(String msg) {
        logger.info(msg);
    }
}
