package com.berry.eagleeye.auth.common.exceptions.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.berry.eagleeye.auth.common.constant.XmlErrorInfo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019-06-22 23:46
 * fileName：SignatureDoesNotMatch
 * Use：
 */
@Data
@Accessors(chain = true)
@JacksonXmlRootElement(localName = "response")
public class SignatureDoesNotMatch implements XmlErrorInfo {

    @JacksonXmlProperty(localName = "Code")
    private String code = "SignatureDoesNotMatch";

    @JacksonXmlProperty(localName = "Message")
    private String message = "The request signature we calculated does not match the signature you provided. Check your key and signing method.";

    @JacksonXmlProperty(localName = "OSSAccessKeyId")
    private String ossAccessKeyId;

    @JacksonXmlProperty(localName = "SignatureProvided")
    private String signatureProvided;

    @JacksonXmlProperty(localName = "StringToSign")
    private String stringToSign;

    public SignatureDoesNotMatch(String ossAccessKeyId, String signatureProvided, String stringToSign) {
        this.ossAccessKeyId = ossAccessKeyId;
        this.signatureProvided = signatureProvided;
        this.stringToSign = stringToSign;
    }
}
