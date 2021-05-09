package com.berry.eagleeye.bridge.mq;//package com.berry.eagleeye.bridge.mq;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2020/5/8 21:24
 * fileName：IMessageService
 * Use：
 */
public interface IMessageService {


    /**
     * 发送消息到算法消费队列
     *
     * @param message          message
     * @param routingKeySuffix 路由模式 后缀
     */
    void sendMsgToAlg(String message, String routingKeySuffix);

    /**
     * 发送消息到算法，停止处理记录
     * @param msg 胸袭
     * @param routingKeySuffix  路由模式 后缀
     */
    void stopDealWithRecord(String msg, String routingKeySuffix);
}
