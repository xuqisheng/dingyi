package com.zhidianfan.pig.yd.moduler.push.ws.exception;

/**
 * @author sjl
 * 2019-03-01 15:05
 */
public class WSCloseException extends RuntimeException {

    public WSCloseException() {
    }

    public WSCloseException(String message) {
        super(message);
    }

    public WSCloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public WSCloseException(Throwable cause) {
        super(cause);
    }

    public WSCloseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
