package com.example.jmstest;

import com.example.jmstest.product.ActiveMqProducer;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jms.Destination;

@SpringBootTest
class JmstestApplicationTests {

    @Autowired
    private ActiveMqProducer activeMqProducer;

    @Test
    void contextLoads() {
        Destination destination = new ActiveMQTopic("activetest");
        // Destination destination1 = new ActiveMQQueue("activetest");

        String info = "测试activeMQ 消息2！";
        // String info1 = "测试activeMQ Queue消息！";

        activeMqProducer.sendMessage(destination, info);
    }

}
