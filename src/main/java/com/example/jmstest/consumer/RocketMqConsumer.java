package com.example.jmstest.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

/**
 * @Author: yzf
 * @Desccription:
 * @Date: created in 17:19 20201/1/02
 * @Modified by:
 * @version: 1.0
 */
@Component
public class RocketMqConsumer {

    private DefaultMQPushConsumer consumer;

    private String consumerGroup = "pay_consumer_group";

    /**
     * serverName
     */
    @Value("${rocketMqTest.serverName}")
    private String nameServer;

    @Value("${rocketMqTest.topicName}")
    private String topicName;

    @PostConstruct
    public void initRocketMqConsumer()  throws MQClientException {
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(nameServer);
        // 设置消费地点,从最后一个进行消费(其实就是消费策略)
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 订阅主题的哪些标签
        consumer.subscribe(topicName, "*");
        // 批量方式消费
        consumer.setConsumeMessageBatchMaxSize(5);
        // 提高单个consume的消费并行度
        consumer.setConsumeThreadMin(1);
        consumer.setConsumeThreadMax(5);


        // 注册监听器
        consumer.registerMessageListener((MessageListenerConcurrently)
                (msgs, context) -> {
                    try {
                        // 获取Message
                        Message msg = msgs.get(0);
                        System.out.printf("%s Receive New Messages: %s %n",
                                Thread.currentThread().getName(), new String(msgs.get(0).getBody()));
                        String topic = msg.getTopic();
                        String body = new String(msg.getBody(), "utf-8");
                        // 标签
                        String tags = msg.getTags();
                        String keys = msg.getKeys();

                        long offSet =  msgs.get(0).getQueueOffset();
                        String maxOffSet = msgs.get(0).getProperty(MessageConst.PROPERTY_MAX_OFFSET);
                        long diff = Long.parseLong(maxOffSet) - offSet;
                        if(diff > 1000L) {
                            // 跳过非重要消息

                            // 优化每条消息的处理过程
                        }

                        System.out.println("topic=" + topic + ", tags=" + tags + ",keys=" + keys + ", msg=" + body);
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                });
        consumer.start();
        System.out.println("Consumer Listener");
    }
}
