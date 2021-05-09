package com.berry.eagleeye.sdk.common;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019-07-03 21:51
 * fileName：BridgeException
 * Use：BridgeException 桥接服务异常
 */
public class BridgeException extends RuntimeException {

    public BridgeException() {
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
    public BridgeException(String message) {
        super(message);
    }
}
