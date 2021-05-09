package com.berry.eagleeye.bridge.retry;

import com.berry.eagleeye.bridge.common.exceptions.RemoteException;
import com.berry.eagleeye.bridge.mq.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2020/12/16 17:59
 * fileName：RetryService
 * Use：
 */
@Service
public class RetryService {

    private static final Logger logger = LoggerFactory.getLogger(RetryService.class);

    private final IMessageService messageService;

    public RetryService(IMessageService messageService) {
        this.messageService = messageService;
    }

    @Retryable(value = RemoteException.class, backoff = @Backoff(delay = 2000L, multiplier = 1.5))
    public void sendMsgToAlgo(String recordDetailId, String msg, String routingKeySuffix) {
        logger.debug("send msg to algo, ID: {}", recordDetailId);
        try {
            messageService.sendMsgToAlg(msg, routingKeySuffix);
        } catch (Exception e) {
            logger.error("send message to mq fail, msg: {}", e.getMessage());
            throw new RemoteException("A500", "Send Message Fail, Retrying");
        }
        logger.debug("ID: {} Done!", recordDetailId);
    }

    @Retryable(value = RemoteException.class, backoff = @Backoff(delay = 2000L, multiplier = 1.5))
    public void stopDealWithRecord(String requestRecordId, String msg, String routingKeySuffix) {
        logger.debug("tell algo stop deal with: {}", requestRecordId);
        try {
            messageService.stopDealWithRecord(msg, routingKeySuffix);
        } catch (Exception e) {
            logger.error("send message to mq fail, msg: {}", e.getMessage());
            throw new RemoteException("A500", "Send Message Fail, Retrying");
        }
    }
}
