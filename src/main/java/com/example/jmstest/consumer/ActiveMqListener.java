package com.example.jmstest.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author: yzf
 * @Desccription:
 * @Date: created in 11:28 2019/12/30
 * @Modified by:
 * @version: 1.0
 */
@Slf4j
@Component
public class ActiveMqListener {

    @JmsListener(destination = "activetest")
    public void getMessage(String message){
        log.info("收到的消息是：" + message);
    }

    @JmsListener(destination = "activetest_topic")
    public void getMessage2(String message){
        log.info("订阅者1_收到topic的消息是：" + message);
    }
    @JmsListener(destination = "activetest_topic")
    public void getMessage3(String message){
        log.info("订阅者2_收到topic的消息是：" + message);
    }
}
