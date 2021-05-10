package com.berry.eagleeye.bridge.mq;//package com.berry.eagleeye.bridge.mq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.berry.eagleeye.bridge.common.constant.EnumConstant;
import com.berry.eagleeye.bridge.common.utils.DateUtils;
import com.berry.eagleeye.bridge.common.utils.NoticeClockUtil;
import com.berry.eagleeye.bridge.dao.entity.RecordDetail;
import com.berry.eagleeye.bridge.dao.service.IRecordDetailDaoService;
import com.berry.eagleeye.bridge.module.dto.FailResultDto;
import com.berry.eagleeye.bridge.module.dto.StatusUpdateMessageDto;
import com.berry.eagleeye.bridge.module.dto.TaskMessageDTO;
import com.berry.eagleeye.bridge.mongo.MongoService;
import com.berry.eagleeye.bridge.quartz.QuartzJobManagement;
import com.berry.eagleeye.bridge.quartz.dynamic.CallBackNoticeJob;
import com.rabbitmq.client.Channel;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2020/5/8 21:41
 * fileName：ExecutorMsgRecvHandler
 * Use：算法生产的消息，消费处理,监听队列：
 * mustangPassQueue 正确处理
 * mustangFailQueue 处理失败
 * noticeStatusQueue 状态通知
 */
@Component
public class ExecutorMsgRecvHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MongoService mongoService;

    @Resource
    private QuartzJobManagement quartzJobManagement;

    private final IRecordDetailDaoService recordDetailDaoService;

    public ExecutorMsgRecvHandler(IRecordDetailDaoService recordDetailDaoService) {
        this.recordDetailDaoService = recordDetailDaoService;
    }

    @RabbitListener(queues = "mustangPassQueue")
    @RabbitHandler
    public void processPassMsg(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        logger.info("正在处理算法(PASS)结果..., msg: {}", message);
        // process message
        TaskMessageDTO dto = null;
        try {
            dto = JSON.parseObject(message, TaskMessageDTO.class);
        } catch (Exception e) {
            logger.error("pass message format error: {}", e.getMessage());
            mongoService.saveFormatErrorMsg(message, "pass");
        }
        if (dto != null && dto.getRecordId() != null) {
            logger.debug("save success result to mongodb");
            mongoService.saveApprovalResult(dto);
            RecordDetail recordDetail = recordDetailDaoService.getOne(
                    new QueryWrapper<RecordDetail>().eq("request_record_id", dto.getRecordId()));
            if (recordDetail != null) {
                // 状态检查
                if (EnumConstant.ExecuteState.userCancel.name().equals(recordDetail.getProcessStatus())) {
                    logger.warn("该记录：【{}】 用户已取消任务处理，忽略该Pass消息", recordDetail.getRequestRecordId());
                } else {
                    recordDetail.setProcessStatus(EnumConstant.ExecuteState.finished.name());
                    recordDetail.setUpdateTime(new Date());
                    logger.debug("update record status");
                    recordDetailDaoService.updateById(recordDetail);

                    // 触发回调通知
                    triggerCallBackNotice(recordDetail);
                }
            }
        }
        try {
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void triggerCallBackNotice(RecordDetail recordDetail) {
        Integer noticeTimes = recordDetail.getNoticeTimes();
        Map<String, String> dataMap = new HashMap<>(16);
        dataMap.put("recordDetailId", String.valueOf(recordDetail.getId()));
        int nextClockGap = NoticeClockUtil.getNextClockGap(noticeTimes);
        if (nextClockGap == 0) {
            // 通知次数达到上限，通知失败
            recordDetail.setNoticeStatus(EnumConstant.NoticeStatus.FAIL.name());
            recordDetailDaoService.updateById(recordDetail);
            return;
        }
        Date activeTime = DateTime.now().plusSeconds(nextClockGap).toDate();
        quartzJobManagement.addJob(recordDetail.getRequestRecordId(), CallBackNoticeJob.class, DateUtils.getCron(activeTime), dataMap);
        logger.info("记录：{}, 添加第{}次通知任务，{} 开始执行", recordDetail.getId(), noticeTimes + 1, activeTime);
        // 更新通知状态和统计
        recordDetail.setNoticeTimes(++noticeTimes);
        recordDetail.setNoticeStatus(EnumConstant.NoticeStatus.PROCESSING.name());
        recordDetailDaoService.updateById(recordDetail);
    }

    @RabbitListener(queues = "mustangFailQueue")
    @RabbitHandler
    public void processFailMsg(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        logger.info("正在处理算法(FAIL)结果..., msg: {}", message);
        //  process message
        FailResultDto failResultDto = null;
        try {
            failResultDto = JSON.parseObject(message, FailResultDto.class);
        } catch (Exception e) {
            logger.error("fail message format error: {}", message);
            mongoService.saveFormatErrorMsg(message, "fail");
        }
        if (failResultDto != null && failResultDto.getRecordId() != null) {
            mongoService.saveApprovalFailResult(failResultDto);
            logger.debug("save fail message mongodb");
            RecordDetail recordDetail = recordDetailDaoService.getOne(
                    new QueryWrapper<RecordDetail>().eq("request_record_id", failResultDto.getRecordId()));
            if (recordDetail != null) {
                // 状态检查
                if (EnumConstant.ExecuteState.userCancel.name().equals(recordDetail.getProcessStatus())) {
                    logger.warn("该记录：【{}】 用户已取消任务处理，忽略该Fail消息", recordDetail.getRequestRecordId());
                } else {
                    recordDetail.setProcessStatus(EnumConstant.ExecuteState.fail.name());
                    recordDetail.setUpdateTime(new Date());
                    logger.debug("update record status");
                    recordDetailDaoService.updateById(recordDetail);
                }
            }
        }
        try {
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "noticeStatusQueue")
    @RabbitHandler
    public void processNoticeStatusMsg(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        logger.info("正在处理算法(状态通知)..., new status: {}", message);
        // do something... process message
        StatusUpdateMessageDto statusUpdateMessageDto = null;
        try {
            statusUpdateMessageDto = JSON.parseObject(message, StatusUpdateMessageDto.class);
        } catch (Exception e) {
            logger.error("notice message format error: {}", message);
        }
        if (statusUpdateMessageDto != null) {
            String status = statusUpdateMessageDto.getStatus();
            String recordId = statusUpdateMessageDto.getRecordId();
            if (isNoneBlank(recordId, status)) {
                RecordDetail recordDetail = recordDetailDaoService.getById(statusUpdateMessageDto.getRecordId());
                if (recordDetail != null) {
                    // 状态检查
                    if (EnumConstant.ExecuteState.userCancel.name().equals(recordDetail.getProcessStatus())) {
                        logger.warn("该记录：【{}】 用户已取消任务处理，忽略该状态变更消息", recordDetail.getRequestRecordId());
                    } else {
                        recordDetail.setProcessStatus(status);
                        recordDetail.setUpdateTime(new Date());
                        recordDetailDaoService.updateById(recordDetail);
                    }
                }
            }
        }
        try {
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
