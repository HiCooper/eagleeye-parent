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
 * @date 2019-06-23 00:44
 * fileName：NotFound
 * Use：
 */
@Data
@Accessors(chain = true)
@JacksonXmlRootElement(localName = "ERROR")
public class NotFound implements XmlErrorInfo {

    @JacksonXmlProperty(localName = "Code")
    private String code = "NotFound";

    @JacksonXmlProperty(localName = "Message")
    private String message = "The request object does not exist.";

}
