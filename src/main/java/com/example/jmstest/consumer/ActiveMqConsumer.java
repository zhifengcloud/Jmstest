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
public class ActiveMqConsumer {

    //接收queue类型消息
    //destination对应配置类中ActiveMQQueue("springboot.queue")设置的名字
    @JmsListener(destination="springboot.queue")
    public void ListenQueue(String msg){
        log.info("接收到queue消息：{}", msg);
    }

    //接收topic类型消息
    //destination对应配置类中ActiveMQTopic("springboot.topic")设置的名字
    //containerFactory对应配置类中注册JmsListenerContainerFactory的bean名称
    @JmsListener(destination="springboot.topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void listenTopic(String msg){
        log.info("订阅者1接收到topic消息：{}" , msg);
    }
    @JmsListener(destination="springboot.topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void listenTopic2(String msg){
        log.info("订阅者2接收到topic消息：{}" , msg);
    }
}
