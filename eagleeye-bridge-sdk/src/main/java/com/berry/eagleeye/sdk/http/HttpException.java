package com.berry.eagleeye.sdk.http;

import java.io.IOException;

/**
 * @author Berry_Cooper.
 * @date 2018-03-26
 * Description 异常基类
 */
public class HttpException extends IOException {

    public HttpException() {
        super();
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public HttpException(String message) {
        super(message);
    }
}
