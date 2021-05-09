package com.berry.eagleeye.management.common.exceptions;

import com.berry.eagleeye.management.common.constant.IMessageEnum;

/**
 * @author Berry_Cooper.
 * @date 2018-03-26
 * Description 网络请求异常
 */
public class RemoteException extends RuntimeException {

    private String code;
    private String msg;

    public RemoteException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public RemoteException(String code, String msg, Throwable ex) {
        super(msg, ex);
        this.code = code;
    }

    public RemoteException(IMessageEnum msg) {
        super(msg.getMeg());
        this.msg = msg.getMeg();
        this.code = msg.getCode();
    }

    public RemoteException(IMessageEnum msg, Throwable ex) {
        super(msg.getMeg(), ex);
        this.msg = msg.getMeg();
        this.code = msg.getCode();
    }

    public RemoteException(Throwable exception) {
        super(exception);
    }

    public String getCode() {
        return code;
    }

}
