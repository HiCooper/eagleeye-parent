package com.berry.eagleeye.bridge.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2020/5/30 13:58
 * fileName：RabbitReturnCallback
 * Use：
 */
@Component
public class RabbitReturnCallback implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback {
    private final static Logger logger = LoggerFactory.getLogger(RabbitReturnCallback.class);

    private static final String ROUTING_MODE_DEFAULT = "coffee.default";

    private static final String CANCEL_ROUTING_MODE_DEFAULT = "cancel.default";

    private final RabbitTemplate rabbitTemplate;

    public RabbitReturnCallback(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        //指定 ReturnCallback
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback(this);
    }


    /**
     * 投递到队列失败
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String correlationId = message.getMessageProperties().getHeader(PublisherCallbackChannel.RETURNED_MESSAGE_CORRELATION_KEY);
        // 算法消费者未启动
        logger.warn("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);

        String defaultMode = ROUTING_MODE_DEFAULT;
        if (routingKey.startsWith("cacnel.")) {
            defaultMode = CANCEL_ROUTING_MODE_DEFAULT;
        }
        // 将消息投递到 担保队列 coffeeBabyQueue coffee.default / cancelDealWithQueue cancel.default
        this.rabbitTemplate.convertAndSend(exchange, defaultMode, message.getBody(), new CorrelationData(correlationId));
        logger.info("requeue msg to {} successful.", defaultMode);
    }

    /**
     * 投递确认：包含失败和成功两种情况
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            logger.debug("消息发送到exchange失败,原因: {}", cause);
        }
    }
}
