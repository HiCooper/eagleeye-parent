package com.berry.eagleeye.bridge.mq.impl;

import com.berry.eagleeye.bridge.mq.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2020/5/8 21:24
 * fileName：MessageServiceImpl
 * Use：
 */
@Service
public class MessageServiceImpl implements IMessageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RabbitTemplate rabbitTemplate;

    private static final String ROUTING_MODE_PREFIX = "coffee.";

    private static final String CANCEL_ROUTING_MODE_PREFIX = "cancel.";

    /**
     * 发送消息至算法
     *
     * @param message          message 消息
     * @param routingKeySuffix routeing_key 后缀
     *                         投递到 模式 为 coffee.# 队列 => coffee.[routingKeySuffix]
     */
    @Override
    public void sendMsgToAlg(String message, String routingKeySuffix) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String routingKey = ROUTING_MODE_PREFIX + routingKeySuffix;
        this.rabbitTemplate.convertAndSend("topicExchange", routingKey, message, correlationData);
        logger.debug("send msg to {} successful.", routingKey);
    }

    @Override
    public void stopDealWithRecord(String message, String routingKeySuffix) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String routingKey = CANCEL_ROUTING_MODE_PREFIX + routingKeySuffix;
        this.rabbitTemplate.convertAndSend("topicExchange", routingKey, message, correlationData);
        logger.debug("send cancel msg to {} successful.", routingKey);
    }
}
