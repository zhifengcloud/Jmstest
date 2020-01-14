package com.example.jmstest.controller;

import com.example.jmstest.product.RocketMqProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Author: yzf
 * @Desccription:
 * @Date: created in 17:23 2020/01/02
 * @Modified by:
 * @version: 1.0
 */
@RestController
public class RocketMqController {

    @Autowired
    private RocketMqProducer producer;


    @Value("${rocketMqTest.topicName}")
    private String topicName;

    @GetMapping("/api/rocketMqTest")
    public Object callback(String text) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        // 创建消息  主题   二级分类   消息内容好的字节数组
        Message message = new Message(topicName, "taga", ("hello rocketMQ " + text).getBytes());

        SendResult sendResult = producer.getProducer().send(message);

        System.out.println(sendResult);

        return new HashMap<>();
    }
}
