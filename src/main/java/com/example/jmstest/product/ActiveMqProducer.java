package com.example.jmstest.product;


import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * @Author: yzf
 * @Desccription:
 * @Date: created in 11:16 2019/12/30
 * @Modified by:
 * @version: 1.0
 */
@Service
public class ActiveMqProducer implements CommandLineRunner {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     *  发送消息，destination是发送到的队列，message是待发送的消息
     * @param destination
     * @param message
     */
    public void sendMessage(Destination destination, final String message){
        jmsMessagingTemplate.convertAndSend(destination, message);
    }

    @Override
    public void run(String... args) throws Exception {
        Destination destination = new ActiveMQQueue("activetest");
        String info = "测试activeMQ Queue消息！";
        this.sendMessage(destination, info);

        Destination destinationTopic = new ActiveMQTopic("activetest_topic");
        this.sendMessage(destinationTopic, "2020年馬上就要來了");
    }
}
