package com.berry.eagleeye.sdk.service;

import com.alibaba.fastjson.JSON;
import com.berry.eagleeye.sdk.common.BridgeException;
import com.berry.eagleeye.sdk.common.Constants;
import com.berry.eagleeye.sdk.http.HttpClient;
import com.berry.eagleeye.sdk.http.HttpException;
import com.berry.eagleeye.sdk.http.Response;
import com.berry.eagleeye.sdk.service.module.dto.SuccessResultDTO;
import com.berry.eagleeye.sdk.service.module.mo.SubmitRequest;
import com.berry.eagleeye.sdk.utils.Auth;
import com.berry.eagleeye.sdk.utils.Json;
import com.berry.eagleeye.sdk.utils.StringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @date 2021/1/6
 * fileName：IBridgeService
 * Use：
 */
public class BridgeService {

    private static final Logger logger = LoggerFactory.getLogger(BridgeService.class);

    private final String errorIMsgTemp = "request fail,stateCode:{}, msg:{}";

    private final Auth auth;
    private final Config config;
    private final HttpClient client;

    public BridgeService(Auth auth, Config config) {
        this.auth = auth;
        this.config = config;
        this.client = new HttpClient(config.getUploadTimeout());
    }

    /**
     * 提交任务处理
     *
     * @param request 请求参数对象
     * @return 提交是否成功
     */
    public Boolean submitApproval(SubmitRequest request) throws HttpException {
        assert request != null : "params must not be null";
        String url = String.format("%s%s", config.getAddress(), UrlFactory.BridgeUrl.submit_approval.getUrl());
        StringMap header = auth.authorization(url);
        logger.debug("request url:{}, header:{}", url, header.map());
        Response response = client.post(url, Json.encode(request), header);
        Result result = null;
        if (response.isSuccessful()) {
            result = response.jsonToObject(Result.class);
            if (result != null
                    && result.getCode().equals(Constants.API_SUCCESS_CODE)
                    && result.getMsg().equals(Constants.API_SUCCESS_MSG)) {
                return true;
            }
        }
        throw new BridgeException(response.getError());
    }

    /**
     * 获取任务处理结果
     *
     * @param recordId 记录ID
     * @return 任务处理结果信息
     */
    public SuccessResultDTO getResult(Long recordId) throws HttpException {
        assert recordId != null : "params must not be null";
        String url = String.format(config.getAddress() + UrlFactory.BridgeUrl.get_result.getUrl(), recordId);
        Response response = getResponse(url);
        Result result = null;
        if (response.isSuccessful()) {
            result = response.jsonToObject(Result.class);
            if (result != null
                    && result.getCode().equals(Constants.API_SUCCESS_CODE)
                    && result.getMsg().equals(Constants.API_SUCCESS_MSG)
                    && result.getData() != null) {
                return JSON.parseObject(JSON.toJSONString(result.getData()), SuccessResultDTO.class);
            }
        }
        if (result == null) {
            String msg = "empty response";
            logger.error("{}. params: {}", msg, recordId);
            throw new BridgeException(msg);
        }
        logger.error(errorIMsgTemp, result.getCode(), result.getMsg());
        throw new BridgeException(result.getMsg());
    }

    private Response getResponse(String url) throws HttpException {
        StringMap header = auth.authorization(url);
        logger.debug("request url:{}, header:{}", url, header.map());
        String withTokenUrl = url + "?token=" + header.get(Auth.BRIDGE_SDK_AUTH_HEAD_NAME);
        return client.get(withTokenUrl, header);
    }
}
