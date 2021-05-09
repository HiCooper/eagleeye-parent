package com.berry.eagleeye.management.common;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.berry.eagleeye.management.common.constant.IMessageEnum;
import com.berry.eagleeye.management.common.exceptions.BaseException;
import com.berry.eagleeye.management.common.exceptions.UploadException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * @author HiCooper&seassoon
 * 返回数据封装对象
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<M> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 未知异常
     */
    private static final String ERROR_UNKNOWN = "ERROR_UNKNOWN";

    private String code;

    private String msg;

    private M data;

    public Result() {
    }

    public Result(M data, String code, String msg) {
        if (StringUtils.isBlank(code)) {
            code = ERROR_UNKNOWN;
        }
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Result(BaseException ex) {
        String exCode = ex.getCode();
        if (StringUtils.isBlank(exCode)) {
            exCode = ERROR_UNKNOWN;
        }
        this.code = exCode;
        this.msg = ex.getMessage();
    }

    public Result(UploadException ex) {
        String exCode = ex.getCode();
        if (StringUtils.isBlank(exCode)) {
            exCode = ERROR_UNKNOWN;
        }
        this.code = exCode;
        this.msg = ex.getMessage();
    }

    public Result(IllegalAccessException ex) {
        this.code = "403";
        this.msg = ex.getLocalizedMessage();
    }

    public Result(AccessDeniedException ex) {
        this.code = "403";
        this.msg = ex.getLocalizedMessage();
    }

    public Result(Exception ex) {
        this.code = "500";
        this.msg = ex.getLocalizedMessage();
    }

    public Result(M data, IMessageEnum msgDefined) {
        if (data != null) {
            this.data = data;
        }
        this.code = msgDefined.getCode();
        this.msg = msgDefined.getMeg();
    }

    public Result(M data, String msg) {
        if (data != null) {
            this.data = data;
        }
        this.code = "200";
        this.msg = msg;
    }

    public Result(IMessageEnum msgDefined) {
        this(msgDefined, null);
    }

    public Result(IMessageEnum msgDefined, String info) {
        this.code = msgDefined.getCode();
        this.msg = MessageFormat.format(msgDefined.getMeg(), info);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public M getData() {
        return data;
    }

    public void setData(M data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
