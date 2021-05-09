package com.berry.eagleeye.bridge.mq;//package com.berry.eagleeye.bridge.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2020/5/8 21:27
 * fileName：RabbitMqConfig
 * Use：Rabbit mq 队列设置
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 定义一个模式匹配交换器
     * type=topic
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange", true, false);
    }

    /**
     * 算法消费队列 （Java 生产，算法消费 ）
     */
    @Bean
    public Queue coffeeBabyQueue() {
        return new Queue("coffeeBabyQueue", true, false, false);
    }

    /**
     * 停止处理记录队列 （Java 生产，算法消费 ）
     */
    @Bean
    public Queue cancelDealWithQueue() {
        return new Queue("cancelDealWithQueue", true, false, false);
    }

    /**
     * 算法生产队列 （算法生产，Java消费 pass）
     */
    @Bean
    public Queue mustangPassQueue() {
        return new Queue("mustangPassQueue", true, false, false);
    }

    /**
     * 算法生产队列 （算法生产，Java消费 fail）
     */
    @Bean
    public Queue mustangFailQueue() {
        return new Queue("mustangFailQueue", true, false, false);
    }

    /**
     * 处理状态通知队列 （算法生产）
     */
    @Bean
    public Queue noticeStatusQueue() {
        return new Queue("noticeStatusQueue", true, false, false);
    }

    /**
     * 将 coffeeBabyQueue 队列绑定到交互器 exchange
     * 路由key为 "coffee.default" 默认通用队列
     *
     * @param coffeeBabyQueue queue
     * @param topicExchange   exchange
     */
    @Bean
    Binding bindingExchangeCoffeeBabyQueue(Queue coffeeBabyQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(coffeeBabyQueue).to(topicExchange).with("coffee.default");
    }

    /**
     * 将 cancelDealWithQueue 队列绑定到交互器 exchange
     * 路由key为 "cancel.default" 默认通用队列
     *
     * @param cancelDealWithQueue queue
     * @param topicExchange       exchange
     */
    @Bean
    Binding bindingExchangeCancelDealWithQueue(Queue cancelDealWithQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(cancelDealWithQueue).to(topicExchange).with("cancel.default");
    }

    /**
     * 将 mustangPassQueue 队列绑定到交互器 exchange
     * 路由key为 "mustang.pass", 与算法约定，不可修改
     * 处理 算法正确分析的结果
     *
     * @param mustangPassQueue queue
     * @param topicExchange    exchange
     */
    @Bean
    Binding bindingExchangeMustangPassQueue(Queue mustangPassQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(mustangPassQueue).to(topicExchange).with("mustang.pass");
    }

    /**
     * 将 mustangFailQueue 队列绑定到交互器 exchange
     * 路由key为 "mustang.fail", 与算法约定，不可修改
     * 处理 算法错误分析的结果
     *
     * @param mustangFailQueue queue
     * @param topicExchange    exchange
     */
    @Bean
    Binding bindingExchangeMustangFailQueue(Queue mustangFailQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(mustangFailQueue).to(topicExchange).with("mustang.fail");
    }

    @Bean
    Binding bindingExchangeNoticeStatusQueue(Queue noticeStatusQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(noticeStatusQueue).to(topicExchange).with("notice.status");
    }
}
