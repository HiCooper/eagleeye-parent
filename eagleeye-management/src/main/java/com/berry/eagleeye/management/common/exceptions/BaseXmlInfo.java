package com.berry.eagleeye.management.common.exceptions;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.berry.eagleeye.management.common.constant.XmlErrorInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/8/29 11:55
 * fileName：BaseXmlInfo
 * Use：
 */
@JacksonXmlRootElement(localName = "ERROR")
public class BaseXmlInfo implements XmlErrorInfo {

    @JacksonXmlProperty(localName = "Code")
    private String code = "400";

    @JacksonXmlProperty(localName = "Message")
    private String message = "操作失败";

    public BaseXmlInfo() {
    }

    public BaseXmlInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
