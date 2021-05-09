package com.berry.eagleeye.management.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Title RedisStringOperationService
 * Description
 * Copyright (c) 2019
 * Company  上海思贤信息技术股份有限公司
 *
 * @author berry_cooper
 * @version 1.0
 * @date 2019/8/13 12:36
 */
@Service
public class RedisSetOperationService {

    /**
     * requestId 有效时间 单位 秒
     */
    private static final long TIMEOUT = 10;

    @Resource
    private StringRedisTemplate stringTemplate;

    public void setWithExpire(String key, String value, Long timeout, TimeUnit timeUnit) {
        stringTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public Boolean setIfAbsent(String key, String value) {
        return stringTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 检查请求id 是否已存在，不存在 则保存到redis，返回0，否则 返回 requestId 的过期时间
     *
     * @param requestId 请求id
     * @return 0 or expireTime 单位 秒
     */
    public long checkRequestIdExist(String requestId) {
        Boolean exist = stringTemplate.opsForValue().setIfAbsent(requestId, requestId, TIMEOUT, TimeUnit.SECONDS);
        if (exist == null || exist) {
            return 0;
        }
        // redis 已存在 该请求id
        Long expire = stringTemplate.getExpire(requestId);
        return expire == null ? 0 : expire;
    }

}
