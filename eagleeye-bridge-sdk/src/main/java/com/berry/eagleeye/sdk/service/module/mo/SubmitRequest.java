package com.berry.eagleeye.sdk.service.module.mo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @since 2021/5/10
 * fileName：SubmitRequest
 * Use：
 */
@Data
public class SubmitRequest {

    private String routingKeySuffix;

    private String recordId;

    private String version;

}
