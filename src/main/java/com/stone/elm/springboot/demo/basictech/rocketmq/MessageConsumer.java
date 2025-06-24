package com.stone.elm.springboot.demo.basictech.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * 消息消费者实现
 */
@Service
public class MessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    @Value("${rocketmq.service.address}")
    private String serviceAddress;

    @Value("${rocketmq.producer.group}")
    private String producerGroup;

    @Value("${rocketmq.message.topic}")
    private String messageTopic;

    private DefaultMQPushConsumer consumer;

    @PostConstruct
    public void start() throws Exception {
        consumer = new DefaultMQPushConsumer(producerGroup);
        consumer.setNamesrvAddr(serviceAddress);
        // 订阅所有tag的消息
        consumer.subscribe(messageTopic, "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages,
                                                            ConsumeConcurrentlyContext context) {
                for (MessageExt msg : messages) {
                    LOGGER.info("收到站内信: {}, Tag: {}, 内容: {}", msg.getMsgId(), msg.getTags(), new String(msg.getBody()));
                    // 这里可以添加处理站内信的业务逻辑
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        LOGGER.info("消息消费者启动成功");
    }

    @PreDestroy
    public void shutdown() {
        consumer.shutdown();
        LOGGER.info("消息消费者启动成功");
    }
}
