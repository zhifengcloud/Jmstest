package com.example.jmstest.product;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: yzf
 * @Desccription:
 * @Date: created in 17:14 2020/1/2
 * @Modified by:
 * @version: 1.0
 */
@Component
public class RocketMqProducer {
    /**
     * 生产组,生产者必须在生产组内
     */
    private String producerGroup = "pay_group";

    /**
     * serverName
     */
    @Value("${rocketMqTest.serverName}")
    private String nameServer;

    private DefaultMQProducer producer;

    @PostConstruct
    public void initMqProducer(){
        producer = new DefaultMQProducer(producerGroup);
        // 指定nameServer地址,多个地址之间以 ; 隔开
        System.out.println("nameServer:"+nameServer);
        producer.setNamesrvAddr(nameServer);
        start();
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }

    /**
     * 对象在使用之前必须调用一次,并且只能初始化一次
     */
    public void start() {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一般在应用上下文,使用上下文监听器,进行关闭
     */
    public void shutdown() {
        producer.shutdown();
    }

}
