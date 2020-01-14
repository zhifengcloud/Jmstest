package com.example.jmstest.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @Author: yzf
 * @Desccription:
 * @Date: created in 11:38 2019/12/30
 * @Modified by:
 * @version: 1.0
 */
@Configuration
@EnableJms
public class ActiveMQConfig {
    @Bean
    public Queue queue(){
        return new ActiveMQQueue("springboot.queue");
    }

    @Bean
    public Topic topic(){
        return new ActiveMQTopic("springboot.topic");
    }

    //springboot默认只配置queue类型消息，如果要使用topic类型的消息，则需要配置该bean
    @Bean
    public JmsListenerContainerFactory jmsTopicListenerContainerFactory(ConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //这里必须设置为true，false则表示是queue类型
        factory.setPubSubDomain(true);
        // 是否支持事物
//        factory.setSessionTransacted(true);
        return factory;
    }
}
