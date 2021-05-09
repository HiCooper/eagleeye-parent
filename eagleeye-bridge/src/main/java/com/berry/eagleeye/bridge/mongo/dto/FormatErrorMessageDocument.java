package com.berry.eagleeye.bridge.mongo.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @date 2021/1/6
 * fileName：FormatErrorMessageDocument
 * Use：
 */
@Document("format_error_message")
@Data
public class FormatErrorMessageDocument {
    public FormatErrorMessageDocument (){}

    public FormatErrorMessageDocument (String type, String message) {
        this.type =type;
        this.message = message;
    }

    private String type;

    private String message;
}
