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
 * @date 2019-06-23 00:06
 * fileName：AccessDenied
 * Use：
 */
@Data
@Accessors(chain = true)
@JacksonXmlRootElement(localName = "ERROR")
public class AccessDenied implements XmlErrorInfo {

    @JacksonXmlProperty(localName = "Code")
    private String code = "AccessDenied";

    @JacksonXmlProperty(localName = "Message")
    private String message = "You have no right to access this object because of bucket acl.";

    public AccessDenied() {

    }

    public AccessDenied(String message) {
        this.message = message;
    }
}
