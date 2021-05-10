package com.berry.eagleeye.sdk.service.module.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @date 2021/1/6
 * fileName：FailResultDto
 * Use：
 */
@Data
public class FailResultDto {

    private String sid;

    private String recordId;

    private String message;

    private Date createTime;
}
