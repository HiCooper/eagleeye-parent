package com.berry.eagleeye.bridge.common.exceptions;

import com.berry.eagleeye.bridge.common.constant.XmlErrorInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019-06-22 23:41
 * fileName：XmlResponseException
 * Use：
 */

public class XmlResponseException extends RuntimeException {

    private static BaseXmlInfo baseXmlInfo = new BaseXmlInfo();

    private XmlErrorInfo xmlErrorInfo;

    public XmlResponseException(XmlErrorInfo xmlErrorInfo) {
        this.xmlErrorInfo = xmlErrorInfo;
    }

    public XmlResponseException() {
        this.xmlErrorInfo = baseXmlInfo;
    }

    XmlErrorInfo getXmlErrorInfo() {
        return xmlErrorInfo;
    }

    public void setXmlErrorInfo(XmlErrorInfo xmlErrorInfo) {
        this.xmlErrorInfo = xmlErrorInfo;
    }
}
