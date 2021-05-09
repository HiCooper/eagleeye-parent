package com.berry.eagleeye.bridge.quartz.dynamic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.berry.eagleeye.bridge.common.constant.EnumConstant;
import com.berry.eagleeye.bridge.common.exceptions.BaseException;
import com.berry.eagleeye.bridge.common.utils.NoticeClockUtil;
import com.berry.eagleeye.bridge.dao.entity.AppConfigInfo;
import com.berry.eagleeye.bridge.dao.entity.RecordDetail;
import com.berry.eagleeye.bridge.dao.service.IAppConfigInfoDaoService;
import com.berry.eagleeye.bridge.dao.service.IRecordDetailDaoService;
import com.berry.eagleeye.bridge.mq.AlgMsgRecvHandler;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2020/12/21 13:57
 * fileName：WormCheckJob
 * Use：
 */
public class CallBackNoticeJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(CallBackNoticeJob.class);

    private static final int maxNoticeTimes = NoticeClockUtil.NOTIFICATION_MAX_TIMES;

    private static final int SUCCESS_HTTP_CODE = 200;
    private static final String NOTICE_SUCCESS_MSG = "success";

    @Autowired
    private IRecordDetailDaoService recordDetailDaoService;

    @Autowired
    private IAppConfigInfoDaoService appConfigInfoDaoService;

    @Resource
    private AlgMsgRecvHandler algMsgRecvHandler;

    @Resource
    private RestTemplate restTemplate;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        String jobName = jobDetail.getKey().getName();
        String recordDetailId = jobDetail.getJobDataMap().getString("recordDetailId");
        logger.info("=== 回调通知任务开始执行:【{}】 ===", jobName);
        RecordDetail recordDetail = recordDetailDaoService.getById(recordDetailId);
        if (recordDetail == null) {
            throw new BaseException("404", "记录不存在：" + recordDetailId);
        }
        // 状态检查
        if (EnumConstant.ExecuteState.userCancel.name().equals(recordDetail.getProcessStatus())) {
            logger.warn("该记录：【{}】 用户已取消预审，忽略该Fail消息", recordDetail.getRequestRecordId());
            return;
        }

        AppConfigInfo userConfigInfo = appConfigInfoDaoService.getOne(new QueryWrapper<AppConfigInfo>().eq("app_name", recordDetail.getAppName()));
        if (userConfigInfo == null) {
            throw new BaseException("406", recordDetail.getAppName() + "未配置回调URL");
        }
        String callbackUrl = userConfigInfo.getCallbackUrl();

        // 根据用户配置回调URL 进行通知
        Map<String, String> params = new HashMap<>(2);
        params.put("id", recordDetailId);
        params.put("recordId", recordDetail.getRequestRecordId());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(callbackUrl, generatePostJson(params), String.class);
        if (SUCCESS_HTTP_CODE == responseEntity.getStatusCodeValue()) {
            String body = responseEntity.getBody();
            logger.debug("response body: {}", body);
            if (NOTICE_SUCCESS_MSG.equalsIgnoreCase(body)) {
                recordDetail.setNoticeStatus(EnumConstant.NoticeStatus.SUCCESS.name());
                recordDetailDaoService.updateById(recordDetail);
                logger.info("通知成功:【{}】", jobName);
                return;
            }
        }

        // 通知失败: 再次尝试触发下一次通知
        algMsgRecvHandler.triggerCallBackNotice(recordDetail);
        Integer noticeTimes = recordDetail.getNoticeTimes();
        logger.info("本次：{}/{} 通知失败，message: {}, 稍后尝试第: {}/{} 次通知", noticeTimes, maxNoticeTimes, "", noticeTimes + 1, maxNoticeTimes);
    }

    public HttpEntity<Map<String, String>> generatePostJson(Map<String, String> jsonMap) {

        //如果需要其它的请求头信息、都可以在这里追加
        HttpHeaders httpHeaders = new HttpHeaders();

        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");

        httpHeaders.setContentType(type);

        return new HttpEntity<>(jsonMap, httpHeaders);
    }
}
