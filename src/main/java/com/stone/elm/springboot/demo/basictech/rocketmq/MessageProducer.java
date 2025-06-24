package com.stone.elm.springboot.demo.basictech.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * 消息生产者实现
 */
@Service
public class MessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

    @Value("${rocketmq.service.address}")
    private String serviceAddress;

    @Value("${rocketmq.producer.group}")
    private String producerGroup;

    @Value("${rocketmq.message.topic}")
    private String messageTopic;

    private DefaultMQProducer producer;

    @PostConstruct
    public void start() {
        producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(serviceAddress);
        try {
            producer.start();
        } catch (MQClientException e) {
            LOGGER.info("消息生产者启动失败");
            e.printStackTrace();
        }
        LOGGER.info("消息生产者启动成功");
    }

    /***
     * 发轻消息
     */
    public synchronized SendResult sendMessage(String message) throws Exception {

        if (producer == null) {
            producer.start();
        }

        return producer.send(new Message(messageTopic, null, message.getBytes()));
    }

    @PreDestroy
    public void shutdown() {
        producer.shutdown();
        LOGGER.info("消息生产者关闭成功");
    }
}
