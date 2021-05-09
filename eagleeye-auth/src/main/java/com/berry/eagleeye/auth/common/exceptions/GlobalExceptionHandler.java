package com.berry.eagleeye.auth.common.exceptions;

import com.berry.eagleeye.auth.common.Result;
import com.berry.eagleeye.auth.common.ResultCode;
import com.berry.eagleeye.auth.common.ResultFactory;
import com.berry.eagleeye.auth.common.XmlResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Berry_Cooper.
 * @date 2018-03-26
 * Description 全局统一异常处理
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(value = BaseException.class)
    public Result baseExceptionHandler(HttpServletRequest req, BaseException ex) {
        logger.error("请求接口 [{}] 失败，错误信息：{}", req.getRequestURI(), ex.getLocalizedMessage());
        return ResultFactory.wrapper(ex);
    }

    /**
     * 返回 xml 格式异常
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = XmlResponseException.class)
    public XmlResponse xmlResponseException(HttpServletRequest req, HttpServletResponse response, XmlResponseException ex) {
        logger.error("请求接口 [{}] 发生错误，错误信息：{}", req.getRequestURI(), ex.getXmlErrorInfo());
        response.setContentType(MediaType.APPLICATION_XML_VALUE);
        return new XmlResponse(ex.getXmlErrorInfo());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = IllegalAccessException.class)
    public String illegalAccessException(HttpServletRequest req, IllegalAccessException ex) {
        logger.error("请求接口 [{}] 发生错误，错误信息：{}", req.getRequestURI(), ex.getLocalizedMessage());
        return ex.getLocalizedMessage();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = RequestRejectedException.class)
    public String requestRejectedException(HttpServletRequest req, RequestRejectedException ex) {
        logger.error("请求接口 [{}] 发生错误，错误信息：{}", req.getRequestURI(), ex.getLocalizedMessage());
        return ex.getMessage();
    }

    /**
     * 未知异常处理
     */
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception ex) {
        logger.error("系统异常:{}", ex.getLocalizedMessage());
        ex.printStackTrace();
        return ResultFactory.wrapper(ResultCode.FAIL);
    }

    /**
     * 服务器内部错误 500
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void runtimeExceptionHandler(HttpServletRequest req, HttpServletResponse response, RuntimeException ex) throws IOException {
        logger.error("接口 [{}] 内部错误:{},位置：{}", req.getRequestURI(), ex.getLocalizedMessage(), ex.getStackTrace()[0]);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }


    /**
     * 未授权 401
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void accessDeniedExceptionHandler(HttpServletRequest req, HttpServletResponse response) throws IOException {
        logger.error("IP:[{}] 请求接口 [{}] 失败，未授权访问", req.getRemoteHost(), req.getRequestURI());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    /**
     * 上传异常，406
     */
    @ExceptionHandler(value = UploadException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public void uploadExceptionHandler(HttpServletRequest req, HttpServletResponse response, UploadException ex) throws IOException {
        logger.error("上传接口 [{}] 失败，信息：{}", req.getRequestURI(), ex.getLocalizedMessage());
        response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }
}
