package com.berry.eagleeye.bridge.module.dto;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2021/2/13 18:14
 * fileName：RecordDetailWithUserConfig
 * Use：
 */
@Data
public class NoticeMessageRequest {

    private Long recordId;

    private String username;

    private Object message;
}
